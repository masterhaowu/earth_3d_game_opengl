package toolbox;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Matrix;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import terrains.Terrain;
import terrainsSphere.TerrainFace;
import terrainsSphere.TerrainSphere;
import entities.Camera;
import entities.Entity;

public class MousePickerSphere {

	private static final int RECURSION_COUNT = 200;
	private static final float RAY_RANGE = 600;

	private Vector3f currentRay = new Vector3f();

	private Matrix4f projectionMatrix;
	private Matrix4f viewMatrix;
	private Camera camera;

	private TerrainSphere terrainSphere;
	private Vector3f currentTerrainPoint;
	// private TerrainFace currentTerrainFace;

	private Vector2f normalizedXY;

	public MousePickerSphere(Camera cam, Matrix4f projection, TerrainSphere terrainSphere) {
		camera = cam;
		projectionMatrix = projection;
		viewMatrix = Maths.createViewMatrix(camera);
		this.terrainSphere = terrainSphere;
	}

	public Vector3f getCurrentTerrainPoint() {
		return currentTerrainPoint;
	}

	public TerrainFace getCurrentTerrainFace() {
		if (currentTerrainPoint == null) {
			return null;
		}
		// System.out.println(currentTerrainPoint);
		TerrainFace face = terrainSphere.getTargetFacePlucker(currentTerrainPoint);
		//face = terrainSphere.getTerrainFaceWithCameraRay(currentRay, camera.getPosition());
		return face;
	}
	
	

	public Entity checkSelectedEntitySimpleMethod(List<Entity> entities) {

		float minDis = 100;
		Entity targetEntity = null;

		if (currentTerrainPoint == null) {
			return targetEntity;
		}
		for (Entity entity : entities) {
			Vector3f entityPos = entity.getPosition();
			entity.setHighlighted(false);
			float distance = (entityPos.x - currentTerrainPoint.x) * (entityPos.x - currentTerrainPoint.x)
					+ (entityPos.y - currentTerrainPoint.y) * (entityPos.y - currentTerrainPoint.y)
					+ (entityPos.z - currentTerrainPoint.z) * (entityPos.z - currentTerrainPoint.z);
			if (distance < minDis) {
				minDis = distance;
				if (distance < 5) {
					targetEntity = entity;
					entity.setHighlighted(true);
				}
			}
		}
		return targetEntity;
	}

	public Entity checkSelectedEntityCenterDistanceMethod(List<Entity> entities) {
		Entity targetEntity = null;
		Vector3f unitCamera = new Vector3f(camera.getPosition().x, camera.getPosition().y, camera.getPosition().z);
		unitCamera.normalise();
		if (currentTerrainPoint == null) {
			// currentTerrainPolar = Maths.convertToPolar(currentTerrainPoint);
			return targetEntity;
		}
		float minDistance = 10000;
		for (Entity entity : entities) {
			entity.setHighlighted(false);
			if (entity.getModel().getRawModel().isHasMinMax() && checkEntityOrientation(entity, unitCamera)) {
				float tempDistance = calculateRayObjectCenterDistance(entity);
				if (tempDistance < 10 && tempDistance < minDistance) {
					minDistance = tempDistance;
					targetEntity = entity;
				}
			}
		}
		if (targetEntity != null) {
			targetEntity.setHighlighted(true);
		}

		return targetEntity;

	}

	public Entity checkSelectedEntityAllMeshMethodWithThreshold(List<Entity> entities, float threshold) {
		Entity targetEntity = null;
		Vector3f unitCamera = new Vector3f(camera.getPosition().x, camera.getPosition().y, camera.getPosition().z);
		unitCamera.normalise();
		if (currentTerrainPoint == null) {
			// currentTerrainPolar = Maths.convertToPolar(currentTerrainPoint);
			return targetEntity;
		}
		float minDistance = 10000;
		List<Entity> entitiesSecondaryCheck = new ArrayList<Entity>();
		for (Entity entity : entities) {
			entity.setHighlighted(false);
			if (entity.getModel().getRawModel().isHasMinMax() && checkEntityOrientation(entity, unitCamera)) {
				float tempDistance = calculateRayObjectCenterDistance(entity);
				if (tempDistance < threshold) {
					// minDistance = tempDistance;
					// targetEntity = entity;
					entitiesSecondaryCheck.add(entity);
				}
			}
		}
		float[] intersection_distance = new float[1];
		// System.out.println(entitiesSecondaryCheck.size());
		for (Entity entity : entitiesSecondaryCheck) {
			if (checkEntityAllMesh(entity, intersection_distance)) {
				if (intersection_distance[0] < minDistance) {
					targetEntity = entity;
					minDistance = intersection_distance[0];
				}
				
			}
		}

		if (targetEntity != null) {
			targetEntity.setHighlighted(true);
		}

		return targetEntity;
	}

	public Entity checkSelectedEntityOBBMethod(List<Entity> entities) {//not working
		Entity targetEntity = null;
		if (currentTerrainPoint == null) {
			return targetEntity;
		}
		float minDistance = 10000;
		float[] tempDistance = new float[1];
		for (Entity entity : entities) {
			entity.setHighlighted(false);
			boolean result;
			if (entity.getModel().getRawModel().isHasMinMax()) {
				result = testRayOBBIntersection(entity, tempDistance);
				// if (result){
				if (result && tempDistance[0] < minDistance) {
					minDistance = tempDistance[0];
					targetEntity = entity;
					// entity.setHighlighted(true);
					// System.out.println("AAAAAAAAAA");
				}
			}

		}
		if (targetEntity != null) {
			targetEntity.setHighlighted(true);
		}

		return targetEntity;
	}

	private boolean checkEntityOrientation(Entity entity, Vector3f cameraUnit) {
		Vector3f unitEntity = new Vector3f(entity.getPosition().x, entity.getPosition().y, entity.getPosition().z);
		unitEntity.normalise();
		float theta2diff = Vector3f.dot(unitEntity, cameraUnit);
		if (theta2diff < -0.3f) {
			return false;
		}

		return true;
	}

	private boolean checkEntityAllMesh(Entity entity, float[] intersection_distance) {
		if (!entity.getModel().getRawModel().getHasModelData()) {
			return false;
		}
		Matrix4f transformationMatrix = Maths.createTransformationMatrix(entity.getPosition(), entity.getRotX(),
				entity.getRotY(), entity.getRotZ(), entity.getScale());
		float[] vertices = entity.getModel().getRawModel().getModelData().getVertices();
		int[] indices = entity.getModel().getRawModel().getModelData().getIndices();

		int indicesNum = Math.floorDiv(indices.length, 3);
		// System.out.println(indicesNum);
		for (int i = 0; i < indicesNum; i++) {
			int index1 = indices[i * 3];
			int index2 = indices[i * 3 + 1];
			int index3 = indices[i * 3 + 2];
			Vector4f init1 = new Vector4f(vertices[index1 * 3], vertices[index1 * 3 + 1], vertices[index1 * 3 + 2],
					1.0f);
			Vector4f init2 = new Vector4f(vertices[index2 * 3], vertices[index2 * 3 + 1], vertices[index2 * 3 + 2],
					1.0f);
			Vector4f init3 = new Vector4f(vertices[index3 * 3], vertices[index3 * 3 + 1], vertices[index3 * 3 + 2],
					1.0f);
			Matrix4f.transform(transformationMatrix, init1, init1);
			Matrix4f.transform(transformationMatrix, init2, init2);
			Matrix4f.transform(transformationMatrix, init3, init3);
			Vector3f p1 = new Vector3f(init1.x, init1.y, init1.z);
			Vector3f p2 = new Vector3f(init2.x, init2.y, init2.z);
			Vector3f p3 = new Vector3f(init3.x, init3.y, init3.z);
			if (checkSingleTriangle(p1, p2, p3)) {
				intersection_distance[0] = (float) Math.pow((Math.pow(p1.x - camera.getPosition().x, 2)
						+ Math.pow(p1.y - camera.getPosition().y, 2) + Math.pow(p1.z - camera.getPosition().z, 2)),
						0.5);
				return true;
			}
		}

		return false;
	}

	private boolean checkSingleTriangle(Vector3f p1, Vector3f p2, Vector3f p3) {
		float[] l1 = new float[6];
		float[] l2 = new float[6];
		float[] l3 = new float[6];
		Maths.generateLines(p1, p2, l1);
		Maths.generateLines(p2, p3, l2);
		Maths.generateLines(p3, p1, l3);

		float[] unitLine = new float[6];
		Vector3f rayEnd = Vector3f.add(camera.getPosition(), currentRay, null);
		Maths.generateLines(getPointOnRay(currentRay, 10000), camera.getPosition(), unitLine);

		float s1 = Maths.sideOperations(unitLine, l1);
		float s2 = Maths.sideOperations(unitLine, l2);
		float s3 = Maths.sideOperations(unitLine, l3);

		if ((s1 <= 0 && s2 <= 0 && s3 <= 0) || (s1 >= 0 && s2 >= 0 && s3 >= 0)) {
			return true;
		}

		return false;
	}

	private float calculateRayObjectCenterDistance(Entity entity) {
		float distance = 0;
		Vector3f centerPos = Vector3f.add(entity.getModel().getRawModel().getMax(),
				entity.getModel().getRawModel().getMin(), null);
		centerPos.x /= 2f;
		centerPos.y /= 2f;
		centerPos.z /= 2f;
		Matrix4f transformationMatrix = Maths.createTransformationMatrix(entity.getPosition(), entity.getRotX(),
				entity.getRotY(), entity.getRotZ(), entity.getScale());
		Vector4f centerWorldPosV4 = Matrix4f.transform(transformationMatrix,
				new Vector4f(centerPos.x, centerPos.y, centerPos.z, 1.0f), null);
		Vector3f q1 = new Vector3f(centerWorldPosV4.x, centerWorldPosV4.y, centerWorldPosV4.z);

		Vector3f u = Vector3f.sub(q1, camera.getPosition(), null);
		Vector3f v = currentRay;

		float vDotU = Vector3f.dot(v, u);

		Vector3f puv = new Vector3f(vDotU * v.x, vDotU * v.y, vDotU * v.z);
		Vector3f q2 = Vector3f.add(camera.getPosition(), puv, null);

		Vector3f diff = Vector3f.sub(q1, q2, null);

		distance = (float) Math.pow((Math.pow(diff.x, 2) + Math.pow(diff.y, 2) + Math.pow(diff.z, 2)), 0.5);

		return distance;
	}

	private boolean testRayOBBIntersection(Entity entity, float[] intersection_distance) {
		boolean intersected = true;
		Vector3f min = entity.getModel().getRawModel().getMin();
		Vector3f max = entity.getModel().getRawModel().getMax();
		Matrix4f transformationMatrix = Maths.createTransformationMatrix(entity.getPosition(), entity.getRotX(),
				entity.getRotY(), entity.getRotZ(), entity.getScale());

		float tMin = 0.0f;
		float tMax = 100000.0f;

		Vector3f obbWorldPos = new Vector3f(transformationMatrix.m30, transformationMatrix.m31,
				transformationMatrix.m32);
		// System.out.println(transformationMatrix);
		// obbWorldPos = entity.getPosition();
		// System.out.println(obbWorldPos);
		// System.out.println("----------------");
		// obbWorldPos = new Vector3f(transformationMatrix.m30,
		// transformationMatrix.m31, transformationMatrix.m32);
		Vector3f delta = Vector3f.sub(obbWorldPos, camera.getPosition(), null);

		// Test intersection with the 2 planes perpendicular to the OBB's X axis
		// Vector3f xAxis = new Vector3f(transformationMatrix.m00,
		// transformationMatrix.m01, transformationMatrix.m02);
		Vector4f initXAxis = new Vector4f(1, 0, 0, 1);
		Matrix4f rotationMatrix = Maths.createRotationMatrix(entity.getRotX(), entity.getRotY(), entity.getRotZ());
		Vector4f tempX = Matrix4f.transform(rotationMatrix, initXAxis, null);
		Vector3f xAxis = new Vector3f(tempX.x, tempX.y, tempX.z);
		// xAxis = new Vector3f(transformationMatrix.m00,
		// transformationMatrix.m01, transformationMatrix.m02);
		// System.out.println(xAxis);

		float e = Vector3f.dot(xAxis, delta);
		float f = Vector3f.dot(currentRay, xAxis);
		// f = Vector3f.dot(new Vector3f(-currentRay.x, -currentRay.y,
		// -currentRay.z), xAxis);

		if (f > 0.001f) { // Standard case
			float t1 = (e + min.x) / f; // Intersection with the "left" plane
			float t2 = (e + max.x) / f; // Intersection with the "right" plane

			// t1 and t2 now contain distances betwen ray origin and ray-plane
			// intersections

			// We want t1 to represent the nearest intersection,
			// so if it's not the case, invert t1 and t2
			if (t1 > t2) {
				float w = t1;
				t1 = t2;
				t2 = w; // swap t1 and t2
			}

			// tMax is the nearest "far" intersection (amongst the X,Y and Z
			// planes pairs)
			if (t2 < tMax) {
				tMax = t2;
			}
			// tMin is the farthest "near" intersection (amongst the X,Y and Z
			// planes pairs)
			if (t1 > tMin) {
				tMin = t1;
			}

			// And here's the trick :
			// If "far" is closer than "near", then there is NO intersection.
			// See the images in the tutorials for the visual explanation.
			if (tMax < tMin) {
				return false;
			}

		} else { // Rare case : the ray is almost parallel to the planes, so
					// they don't have any "intersection"
			if (-e + min.x > 0.0f || -e + max.x < 0.0f)
				return false;
		}

		// Test intersection with the 2 planes perpendicular to the OBB's Y axis
		// Exactly the same thing than above.
		/*
		 * Vector3f yAxis = new Vector3f(transformationMatrix.m10,
		 * transformationMatrix.m11, transformationMatrix.m12); //yAxis = new
		 * Vector3f(transformationMatrix.m10, transformationMatrix.m11,
		 * transformationMatrix.m12);
		 * 
		 * e = Vector3f.dot(yAxis, delta); f = Vector3f.dot(currentRay, yAxis);
		 * 
		 * if ( f > 0.001f ) { // Standard case float t1 = (e+min.y)/f; //
		 * Intersection with the "left" plane float t2 = (e+max.y)/f; //
		 * Intersection with the "right" plane
		 * 
		 * // t1 and t2 now contain distances betwen ray origin and ray-plane
		 * intersections
		 * 
		 * // We want t1 to represent the nearest intersection, // so if it's
		 * not the case, invert t1 and t2 if (t1>t2){ float w=t1;t1=t2;t2=w; //
		 * swap t1 and t2 }
		 * 
		 * // tMax is the nearest "far" intersection (amongst the X,Y and Z
		 * planes pairs) if ( t2 < tMax ){ tMax = t2; } // tMin is the farthest
		 * "near" intersection (amongst the X,Y and Z planes pairs) if ( t1 >
		 * tMin ){ tMin = t1; }
		 * 
		 * // And here's the trick : // If "far" is closer than "near", then
		 * there is NO intersection. // See the images in the tutorials for the
		 * visual explanation. if (tMax < tMin ){ return false; }
		 * 
		 * } else { // Rare case : the ray is almost parallel to the planes, so
		 * they don't have any "intersection" if(-e+min.y > 0.0f || -e+max.y <
		 * 0.0f) return false; }
		 * 
		 * 
		 * Vector3f zAxis = new Vector3f(transformationMatrix.m20,
		 * transformationMatrix.m21, transformationMatrix.m22); //zAxis = new
		 * Vector3f(transformationMatrix.m20, transformationMatrix.m21,
		 * transformationMatrix.m22);
		 * 
		 * e = Vector3f.dot(zAxis, delta); f = Vector3f.dot(currentRay, zAxis);
		 * 
		 * if ( f > 0.001f ) { // Standard case float t1 = (e+min.z)/f; //
		 * Intersection with the "left" plane float t2 = (e+max.z)/f; //
		 * Intersection with the "right" plane
		 * 
		 * // t1 and t2 now contain distances betwen ray origin and ray-plane
		 * intersections
		 * 
		 * // We want t1 to represent the nearest intersection, // so if it's
		 * not the case, invert t1 and t2 if (t1>t2){ float w=t1;t1=t2;t2=w; //
		 * swap t1 and t2 }
		 * 
		 * // tMax is the nearest "far" intersection (amongst the X,Y and Z
		 * planes pairs) if ( t2 < tMax ){ tMax = t2; } // tMin is the farthest
		 * "near" intersection (amongst the X,Y and Z planes pairs) if ( t1 >
		 * tMin ){ tMin = t1; }
		 * 
		 * // And here's the trick : // If "far" is closer than "near", then
		 * there is NO intersection. // See the images in the tutorials for the
		 * visual explanation. if (tMax < tMin ){ return false; }
		 * 
		 * } else { // Rare case : the ray is almost parallel to the planes, so
		 * they don't have any "intersection" if(-e+min.z > 0.0f || -e+max.z <
		 * 0.0f) return false; }
		 */
		intersection_distance[0] = tMin;

		return intersected;
	}

	public Vector3f getCurrentRay() {
		return currentRay;
	}

	public Vector2f getNormalizedXY() {
		return normalizedXY;
	}

	public void update() {
		viewMatrix = Maths.createViewMatrix(camera);
		currentRay = calculateMouseRay();
		if (intersectionInRange(0, RAY_RANGE, currentRay)) {
			currentTerrainPoint = binarySearch(0, 0, RAY_RANGE, currentRay);
		} else {
			currentTerrainPoint = null;
		}
	}
	
	public void updateOptimized() {
		viewMatrix = Maths.createViewMatrix(camera);
		currentRay = calculateMouseRay();
		//currentTerrainPoint = terrainSphere.getTerrainPointWithCameraRay(currentRay, camera.getPosition());
		currentTerrainPoint = terrainSphere.getTerrainRayIntersectionPointCheckingNeighborFaces(currentRay, camera.getPosition(), 4);
		if (currentTerrainPoint == null) {
			if (intersectionInRange(0, RAY_RANGE, currentRay)) {
				currentTerrainPoint = binarySearch(0, 0, RAY_RANGE, currentRay);
			} else {
				currentTerrainPoint = null;
			}
		}
		//System.out.println(currentTerrainPoint);
		//currentTerrainPoint = terrainSphere.getTerrainRayIntersectionPointNoHeight(currentRay, camera.getPosition());
		//System.out.println(currentTerrainPoint);
	}
	
	

	private Vector3f calculateMouseRay() {
		float mouseX = Mouse.getX();
		float mouseY = Mouse.getY();
		Vector2f normalizedCoords = getNormalisedDeviceCoordinates(mouseX, mouseY);
		this.normalizedXY = normalizedCoords;
		Vector4f clipCoords = new Vector4f(normalizedCoords.x, normalizedCoords.y, -1.0f, 1.0f);
		Vector4f eyeCoords = toEyeCoords(clipCoords);
		Vector3f worldRay = toWorldCoords(eyeCoords);
		return worldRay;
	}

	private Vector3f toWorldCoords(Vector4f eyeCoords) {
		Matrix4f invertedView = Matrix4f.invert(viewMatrix, null);
		Vector4f rayWorld = Matrix4f.transform(invertedView, eyeCoords, null);
		Vector3f mouseRay = new Vector3f(rayWorld.x, rayWorld.y, rayWorld.z);
		mouseRay.normalise();
		return mouseRay;
	}

	private Vector4f toEyeCoords(Vector4f clipCoords) {
		Matrix4f invertedProjection = Matrix4f.invert(projectionMatrix, null);
		Vector4f eyeCoords = Matrix4f.transform(invertedProjection, clipCoords, null);
		return new Vector4f(eyeCoords.x, eyeCoords.y, -1f, 0f);
	}

	private Vector2f getNormalisedDeviceCoordinates(float mouseX, float mouseY) {
		float x = (2.0f * mouseX) / Display.getWidth() - 1f;
		float y = (2.0f * mouseY) / Display.getHeight() - 1f;
		return new Vector2f(x, y);
	}

	// **********************************************************

	private Vector3f getPointOnRay(Vector3f ray, float distance) {
		Vector3f camPos = camera.getPosition();
		Vector3f start = new Vector3f(camPos.x, camPos.y, camPos.z);
		Vector3f scaledRay = new Vector3f(ray.x * distance, ray.y * distance, ray.z * distance);
		return Vector3f.add(start, scaledRay, null);
	}

	private Vector3f binarySearch(int count, float start, float finish, Vector3f ray) {
		float half = start + ((finish - start) / 2f);
		if (count >= RECURSION_COUNT) {
			Vector3f endPoint = getPointOnRay(ray, half);
			TerrainSphere terrainSphere = getTerrain();
			if (terrainSphere != null) {
				return endPoint;
			} else {
				return null;
			}
		}
		if (intersectionInRange(start, half, ray)) {
			return binarySearch(count + 1, start, half, ray);
		} else {
			return binarySearch(count + 1, half, finish, ray);
		}
	}

	private boolean intersectionInRange(float start, float finish, Vector3f ray) {
		Vector3f startPoint = getPointOnRay(ray, start);
		Vector3f endPoint = getPointOnRay(ray, finish);
		if (!isUnderGround(startPoint, ray) && isUnderGround(endPoint, ray)) {
			return true;
		} else {
			return false;
		}
	}

	private boolean isUnderGround(Vector3f testPoint, Vector3f ray) {
		Vector3f testPolar = Maths.convertToPolar(testPoint);
		TerrainSphere terrain = getTerrain();
		float dot = Vector3f.dot(testPoint, ray);
		float height = 0;
		if (terrain != null) {
			height = terrain.getHeightAdvanced(testPolar.y, testPolar.z);
		}
		if (testPolar.x < height || dot > 0) {
			// System.out.print("below");
			return true;
		} else {
			// System.out.print("above");
			return false;

		}
	}

	private TerrainSphere getTerrain() {
		return terrainSphere;
	}

}