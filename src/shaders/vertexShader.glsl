#version 400 core

in vec3 position;
in vec2 textureCoords;
//in vec3 normal;
//out vec2 tc;

//out vec3 colour;
//uniform mat4 modelViewMatrix;
//uniform mat4 projectionMatrix;

out vec2 pass_textureCoords;
//out vec3 surfaceNormal;
out vec3 toLightVector[4];
out vec3 toCameraVector;

out vec4 worldPosition;
//out float visibility;



uniform mat4 transformationMatrix;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform vec3 lightPosition[4];
uniform float useFakeLighting;

uniform vec3 centerPosition;
uniform bool enableShaderAnimation;
uniform float time;

uniform float numberOfRows;
uniform vec2 offset;

const float density = 0.0;
const float gradient = 4.0;

const float PI = 3.1415926535897932384626433832795;
const float animationAmplitude = 0.02;

uniform vec4 plane;

/*
vec4 updatePosition(vec3 inputPosition){
    
    vec4 updatedPosition = vec4(0, 0, 0, 1.0);
    updatedPosition.x = (inputPosition.x - centerPosition.x) * (1 + sin(2.0 * PI * time) * animationAmplitude) + centerPosition.x;
    updatedPosition.y = (inputPosition.y - centerPosition.y) * (1 + sin(2.0 * PI * time) * animationAmplitude) + centerPosition.y;
    updatedPosition.z = (inputPosition.z - centerPosition.z) * (1 + sin(2.0 * PI * time) * animationAmplitude) + centerPosition.z;
    
    return updatedPosition;
}

*/
void main()
{
    /*
    if (enableShaderAnimation == true) {
        vec4 newPosition = updatePosition(position);
        worldPosition = transformationMatrix * newPosition;
    }
    else{
        worldPosition =  transformationMatrix * vec4(position.x, position.y, position.z, 1.0);
    }
     */
    worldPosition =  transformationMatrix * vec4(position.x, position.y, position.z, 1.0);
    gl_ClipDistance[0] = dot(worldPosition, plane);
    
    vec4 positionRelativeToCam = viewMatrix * worldPosition;
    gl_Position = projectionMatrix * positionRelativeToCam;
    
    pass_textureCoords = (textureCoords/numberOfRows) + offset;
    
    //vec3 actualNormal = normal;
    //if (useFakeLighting > 0.5) {
     //   actualNormal = vec3(0.0, 1.0, 0.0);
    //}
    
   // surfaceNormal = (transformationMatrix * vec4(actualNormal,0.0)).xyz;
    for (int i=0; i<4; i++) {
        toLightVector[i] = lightPosition[i] - worldPosition.xyz;
    }
    
    toCameraVector = (inverse(viewMatrix) * vec4(0.0, 0.0, 0.0, 1.0)).xyz - worldPosition.xyz;
    
    //float distance = length(positionRelativeToCam.xyz);
    //visibility = exp(-pow((distance * density), gradient));
    //visibility = clamp(visibility, 0.0, 1.0);
}

