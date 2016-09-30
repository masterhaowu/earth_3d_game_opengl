#version 400 core

in vec3 position;
in vec3 colour;

out vec3 pass_terrainColour;

out vec3 toLightVector[4];
out vec3 toCameraVector;
out vec4 worldPosition;

uniform mat4 transformationMatrix;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform vec3 lightPosition[4];
uniform mat4 toShadowMapSpace;

//uniform mat4 toShadowMapSpace;


const float density = 0.0;
const float gradient = 4.0;
const float shadowDistance = 150.0;
const float transitionDistance = 10.0;

const float PI = 3.1415926535897932384626433832795;
const float amplitude = 0.02;

uniform vec4 plane;

vec3 generateOffset(){
    float component1 = sin(2.0 * PI + (position.x * 16.0)) * amplitude;
    float component2 = sin(2.0 * PI + (position.y * position.x * 8.0)) * amplitude;
    float component3 = sin(2.0 * PI + (position.y * 8.0)) * amplitude;
    return vec3(component1, component2, component3);
}




void main()
{
    //vec2 pass_textureCoords;
    vec3 surfaceNormal;
    //vec3 toLightVector[4];
    //vec3 toCameraVector;
    float visibility;
    vec4 shadowCoords;
    
    vec3 normal = normalize(position.xyz);
    
    worldPosition =  transformationMatrix * vec4(position.x, position.y, position.z, 1.0);
    //vec4 worldPosition =  transformationMatrix * vec4(floor(position.x), floor(position.y), floor(position.z), 1.0);
    shadowCoords = toShadowMapSpace * worldPosition;
    
    gl_ClipDistance[0] = dot(worldPosition, plane);
    //gl_ClipDistance[0] = -1;
    vec4 positionRelativeToCam = viewMatrix * worldPosition;
    gl_Position = projectionMatrix * positionRelativeToCam;
    
    
    //pass_textureCoords = textureCoords;
    pass_terrainColour = colour + generateOffset();
    
    
    surfaceNormal = (transformationMatrix * vec4(normal,0.0)).xyz;
    for (int i=0; i<4; i++) {
        toLightVector[i] = lightPosition[i] - worldPosition.xyz;
    }
    toCameraVector = (inverse(viewMatrix) * vec4(0.0, 0.0, 0.0, 1.0)).xyz - worldPosition.xyz;
    
    
    float distance = length(positionRelativeToCam.xyz);
    visibility = exp(-pow((distance * density), gradient));
    visibility = clamp(visibility, 0.0, 1.0);
    
    //distance = distance - (shadowDistance - transitionDistance);
    //distance = distance / transitionDistance;
    //shadowCoords.w = 1 - clamp(distance, 0.0, 1.0);
    if (distance > shadowDistance) {
        shadowCoords.w = 0;
    }
    else {
        distance = distance - (shadowDistance - transitionDistance);
        distance = distance / transitionDistance;
        shadowCoords.w = 1 - clamp(distance, 0.0, 1.0);
    }
    
    
    
    
}

