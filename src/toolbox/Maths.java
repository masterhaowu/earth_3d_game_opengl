package toolbox;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;

public class Maths {
	
	public static Vector3f vectorMult(Vector3f v1, float a){
		Vector3f v2 = new Vector3f(v1.x * a, v1.y * a, v1.z * a);
		return v2;
	}
	
	public static float sideOperations(float[] a, float b[]){
		float result = a[0]*b[4] + a[1]*b[5] + a[2]*b[3] + a[3]*b[2] + a[4]*b[0] + a[5]*b[1];
		return result;
	}
	
	public static void generateLines(Vector3f p, Vector3f q, float[] l){
		//L[0] =  px  qy -    qx  py 
        //L[1] =  px  qz -    qx  pz 
        //L[2] =     px    -    qx 
        //L[3] =  py  qz -    qy  pz 
        //L[4] =     pz    -    qz 
        //L[5] =     qy    -    py 
		l[0] = p.x * q.y - q.x * p.y;
		l[1] = p.x * q.z - q.x * p.z;
		l[2] = p.x - q.x;
		l[3] = p.y * q.z - q.y * p.z;
		l[4] = p.z - q.z;
		l[5] = q.y - p.y;
	}
	
	public Vector3f getPreciseRayTriangleIntersectionPoint(Vector3f p, Vector3f d, Vector3f v0, Vector3f v1, Vector3f v2){
		//p is rayOrigin
		//d is rayDirection
		Vector3f finalPos = new Vector3f(0, 0, 0);
		
		Vector3f e1 = Vector3f.sub(v1, v0, null);
		Vector3f e2 = Vector3f.sub(v2, v0, null);
		float f;
		Vector3f h = Vector3f.cross(d, e2, null);
		float a = Vector3f.dot(e1, h);
		if (a > -0.00001 && a < 0.00001){
			//System.out.println("return because a failed");
			return null;
		}
		f = 1/a;
		
		Vector3f s = Vector3f.sub(p, v0, null);
		float u = f * Vector3f.dot(s, h);
		if (u < -0.00001 || u > 1.00001){
			//System.out.println("return because u failed");
			//System.out.println(u);
			return null;
		}
		
		Vector3f q = Vector3f.cross(s, e1, null);
		float v = f * Vector3f.dot(d, q);
		
		if (v < -0.00001 || u + v > 1.00001){
			//System.out.println("return because v and u+v failed");
			return null;
		}
		
		float t = f * Vector3f.dot(e2,q);
		
		finalPos = new Vector3f(d.x * t, d.y * t, d.z * t);
		
		return finalPos;
	}
	

	public static Vector3f convertToPolar(Vector3f position) {
		Vector3f polarCoords = new Vector3f(0, 0, 0);
		float xVal = position.x;
		float yVal = position.y;
		float zVal = position.z;
		float radius = (float) Math.sqrt(xVal * xVal + yVal * yVal + zVal * zVal);
		float rOnXZ = (float) Math.sqrt(xVal * xVal + zVal * zVal);
		polarCoords.x = radius;
		polarCoords.y = (float) Math.asin(yVal / radius);
		if (rOnXZ == 0) {
			polarCoords.z = 0;
			if (yVal > 0) {
				polarCoords.y = (float) (Math.PI / 2);
			} else {
				polarCoords.y = (float) (-Math.PI / 2);
			}
		} else {
			polarCoords.z = (float) Math.asin(zVal / rOnXZ);
		}
		if (xVal < 0) {
			polarCoords.z = (float) (Math.PI - polarCoords.z);
		}
		return polarCoords;
	}

	public static Vector3f convertBackToCart(Vector3f polar) {
		Vector3f position = new Vector3f(0, 0, 0);
		float radius = polar.x;
		float theta1 = polar.y;
		float theta2 = polar.z;
		float rOnXZ = (float) (radius * Math.cos(theta1));
		position.x = (float) (rOnXZ * Math.cos(theta2));
		position.y = (float) (radius * Math.sin(theta1));
		position.z = (float) (rOnXZ * Math.sin(theta2));
		return position;
	}

	public static float barryCentric(Vector3f p1, Vector3f p2, Vector3f p3, Vector2f pos) {
		float det = (p2.z - p3.z) * (p1.x - p3.x) + (p3.x - p2.x) * (p1.z - p3.z);
		float l1 = ((p2.z - p3.z) * (pos.x - p3.x) + (p3.x - p2.x) * (pos.y - p3.z)) / det;
		float l2 = ((p3.z - p1.z) * (pos.x - p3.x) + (p1.x - p3.x) * (pos.y - p3.z)) / det;
		float l3 = 1.0f - l1 - l2;
		return l1 * p1.y + l2 * p2.y + l3 * p3.y;
	}

	public static Matrix4f createTransformationMatrix(Vector3f translation, float rx, float ry, float rz, float scale) {

		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();
		Matrix4f.translate(translation, matrix, matrix);
		//Matrix4f.rotate((float) Math.toRadians(rx), new Vector3f(1,0,0),matrix, matrix);
		Matrix4f.rotate((float) Math.toRadians(rz), new Vector3f(0, 0, 1), matrix, matrix);
		Matrix4f.rotate((float) Math.toRadians(ry), new Vector3f(0, 1, 0), matrix, matrix);
		Matrix4f.rotate((float) Math.toRadians(rx), new Vector3f(1, 0, 0), matrix, matrix);
		//Matrix4f.rotate((float) Math.toRadians(rz), new Vector3f(0, 1, 0), matrix, matrix);

		Matrix4f.scale(new Vector3f(scale, scale, scale), matrix, matrix);
		// Matrix4f.translate(translation, matrix, matrix);
		return matrix;
	}
	
	public static Matrix4f createTransformationMatrixWithYOffset(Vector3f translation, float rx, float ry, float YOffset, float scale) {

		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();
		Matrix4f.translate(translation, matrix, matrix);
		//Matrix4f.rotate((float) Math.toRadians(rx), new Vector3f(1,0,0),matrix, matrix);
		//Matrix4f.rotate((float) Math.toRadians(rz), new Vector3f(0, 0, 1), matrix, matrix);
		Matrix4f.rotate((float) Math.toRadians(ry), new Vector3f(0, 1, 0), matrix, matrix);
		Matrix4f.rotate((float) Math.toRadians(rx), new Vector3f(1, 0, 0), matrix, matrix);
		Matrix4f.rotate((float) Math.toRadians(YOffset), new Vector3f(0, 1, 0), matrix, matrix);

		Matrix4f.scale(new Vector3f(scale, scale, scale), matrix, matrix);
		// Matrix4f.translate(translation, matrix, matrix);
		return matrix;
	}
	
	public static Matrix4f createRotationMatrix( float rx, float ry, float rz) {

		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();
		
		//Matrix4f.rotate((float) Math.toRadians(rx), new Vector3f(1,0,0),matrix, matrix);
		Matrix4f.rotate((float) Math.toRadians(rz), new Vector3f(0, 0, 1), matrix, matrix);
		Matrix4f.rotate((float) Math.toRadians(ry), new Vector3f(0, 1, 0), matrix, matrix);
		//Matrix4f.rotate((float) Math.toRadians(rz), new Vector3f(0, 0, 1), matrix, matrix);
		Matrix4f.rotate((float) Math.toRadians(rx), new Vector3f(1, 0, 0), matrix, matrix);
		//Matrix4f.rotate((float) Math.toRadians(rz), new Vector3f(0, 1, 0), matrix, matrix);

		// Matrix4f.translate(translation, matrix, matrix);
		return matrix;
	}

	public static Matrix4f createViewMatrix(Camera camera) {
		Matrix4f viewMatrix = new Matrix4f();
		//viewMatrix.setIdentity();
		// Vector3f cameraPos = camera.getPosition();
		// Vector3f negativeCameraPos = new Vector3f(-cameraPos.x, -cameraPos.y,
		// -cameraPos.z);
		// Matrix4f.translate(negativeCameraPos, viewMatrix, viewMatrix);
		// Matrix4f.rotate((float) Math.toRadians(camera.getRoll()), new
		// Vector3f(0,0,1), viewMatrix, viewMatrix);
		//Matrix4f.rotate((float) Math.toRadians(camera.getPitch()), new Vector3f(1, 0, 0), viewMatrix, viewMatrix);
		//Matrix4f.rotate((float) Math.toRadians(camera.getYaw()), new Vector3f(0, 1, 0), viewMatrix, viewMatrix);
		// Matrix4f.rotate((float) Math.toRadians(camera.getPitch()), new
		// Vector3f(1,0,0), viewMatrix, viewMatrix);
		//Matrix4f.rotate((float) Math.toRadians(camera.getRoll()), new Vector3f(0, 0, 1), viewMatrix, viewMatrix);
		Vector3f cameraPos = camera.getPosition();
		Vector3f negativeCameraPos = new Vector3f(-cameraPos.x, -cameraPos.y, -cameraPos.z);
		// Vector3f negativeCameraPos = new Vector3f(cameraPos.x, cameraPos.y,
		// cameraPos.z);
		Matrix4f.translate(negativeCameraPos, viewMatrix, viewMatrix);

		Vector3f playerPos = camera.getPlayer().getPosition();
		// viewMatrix = setLookAt(cameraPos.x, cameraPos.y, cameraPos.z,
		// playerPos.x, playerPos.y, playerPos.z, 0, 1, 0);
		viewMatrix = setLookAt(cameraPos.x, cameraPos.y, cameraPos.z, playerPos.x, playerPos.y, playerPos.z,
				playerPos.x, playerPos.y + 0.1f, playerPos.z);

		return viewMatrix;
	}

	public static Matrix4f createTransformationMatrix(Vector2f translation, Vector2f scale) {
		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();
		Matrix4f.translate(translation, matrix, matrix);
		Matrix4f.scale(new Vector3f(scale.x, scale.y, 1f), matrix, matrix);
		return matrix;
	}

	public static Matrix4f setLookAt(float eyeX, float eyeY, float eyeZ, float centerX, float centerY, float centerZ,
			float upX, float upY, float upZ) {
		// Compute direction from position to lookAt
		float dirX, dirY, dirZ;
		dirX = eyeX - centerX;
		dirY = eyeY - centerY;
		dirZ = eyeZ - centerZ;
		// Normalize direction
		float invDirLength = 1.0f / (float) Math.sqrt(dirX * dirX + dirY * dirY + dirZ * dirZ);
		dirX *= invDirLength;
		dirY *= invDirLength;
		dirZ *= invDirLength;
		// left = up x direction
		float leftX, leftY, leftZ;
		leftX = upY * dirZ - upZ * dirY;
		leftY = upZ * dirX - upX * dirZ;
		leftZ = upX * dirY - upY * dirX;
		// normalize left
		float invLeftLength = 1.0f / (float) Math.sqrt(leftX * leftX + leftY * leftY + leftZ * leftZ);
		leftX *= invLeftLength;
		leftY *= invLeftLength;
		leftZ *= invLeftLength;
		// up = direction x left
		float upnX = dirY * leftZ - dirZ * leftY;
		float upnY = dirZ * leftX - dirX * leftZ;
		float upnZ = dirX * leftY - dirY * leftX;

		Matrix4f toReturn = new Matrix4f();

		toReturn.m00 = leftX;
		toReturn.m01 = upnX;
		toReturn.m02 = dirX;
		toReturn.m03 = 0.0f;
		toReturn.m10 = leftY;
		toReturn.m11 = upnY;
		toReturn.m12 = dirY;
		toReturn.m13 = 0.0f;
		toReturn.m20 = leftZ;
		toReturn.m21 = upnZ;
		toReturn.m22 = dirZ;
		toReturn.m23 = 0.0f;
		toReturn.m30 = -(leftX * eyeX + leftY * eyeY + leftZ * eyeZ);
		toReturn.m31 = -(upnX * eyeX + upnY * eyeY + upnZ * eyeZ);
		toReturn.m32 = -(dirX * eyeX + dirY * eyeY + dirZ * eyeZ);
		toReturn.m33 = 1.0f;
		// properties = PROPERTY_AFFINE;

		return toReturn;
	}

}
