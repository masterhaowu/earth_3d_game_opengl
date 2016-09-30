#version 400 core

in vec2 position;

out vec4 clipSpace;
out vec2 textureCoords;
out vec3 toCameraVector;
out vec3 fromLightVector;
out vec4 worldPosition;

uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform mat4 modelMatrix;
uniform vec3 cameraPosition;
uniform vec3 lightPosition;

uniform float time;

const float PI = 3.1415926535897932384626433832795;
const float amplitude = 1;

const float tilling = 6.0;


float generateHeight(){
    float component1 = sin(2.0 * PI * time + (position.x * 16.0)) * amplitude;
    float component2 = sin(2.0 * PI * time + (position.y * position.x * 8.0)) * amplitude;
    return component1 + component2;
}


void main(void) {

    float height = generateHeight();
    worldPosition = modelMatrix * vec4(position.x, height, position.y, 1.0);
    clipSpace = projectionMatrix * viewMatrix * worldPosition;
    gl_Position = clipSpace;
    textureCoords = vec2(position.x/2.0 + 0.5, position.y/2.0 + 0.5) * tilling;
    toCameraVector = cameraPosition - worldPosition.xyz;
    fromLightVector = worldPosition.xyz - lightPosition;
}
