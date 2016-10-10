#version 400 core

in vec3 position;
in vec3 polar;

//out vec4 clipSpace;
//out vec2 textureCoords;
out vec3 toCameraVector;
out vec3 fromLightVector;
out vec4 worldPosition;
out float waterDepth;

//uniform float waterLevel;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform mat4 modelMatrix;
uniform vec3 cameraPosition;
uniform vec3 lightPosition;

uniform float time;

const float PI = 3.1415926535897932384626433832795;
const float amplitude = 0.0014;

const float tilling = 6.0;


float generateHeight(){
    float component1 = sin(2.0 * PI * time + (polar.x * 1600.0)) * amplitude;
    float component2 = sin(2.0 * PI * time + (polar.y * polar.z * 800.0)) * amplitude;
    return component1 + component2;
}


void main(void) {
    float waterLevel = 1;
    float height = generateHeight();
    float newHeight = waterLevel + height;
    float newY = newHeight * sin(polar.y);
    float newXZ = newHeight * cos(polar.y);
    float newX = newXZ * cos(polar.z);
    float newZ = newXZ * sin(polar.z);
    //worldPosition = modelMatrix * vec4(position.x, position.y, position.z, 1.0);
    worldPosition = modelMatrix * vec4(newX, newY, newZ, 1.0);
    gl_Position = projectionMatrix * viewMatrix * worldPosition;
    waterDepth = (newHeight - polar.x);
    //clipSpace = projectionMatrix * viewMatrix * worldPosition;
    //gl_Position = clipSpace;
    //textureCoords = vec2(position.x/2.0 + 0.5, position.y/2.0 + 0.5) * tilling;
    toCameraVector = cameraPosition - worldPosition.xyz;
    fromLightVector = worldPosition.xyz - lightPosition;
}
