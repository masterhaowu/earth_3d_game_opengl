#version 140

in vec3 position;
in vec2 textureCoords;
//in vec3 normal;

out vec2 pass_textureCoords;
out vec4 worldPosition;
out vec3 toCameraVector;

uniform mat4 transformationMatrix;

uniform mat4 rotationMatrix;
//uniform float time;
//uniform float rotationSpeed;

void main(void){
    
    worldPosition = vec4(position.x, position.y, position.z, 1.0);
    
    gl_Position = transformationMatrix * vec4(worldPosition.x, worldPosition.y, worldPosition.z/100 + 0.1, 1.0);
    //gl_Position = vec4(textureCoords.x*2 - 1, textureCoords.y * 2 - 1, 0.0, 1.0);
   // gl_Position = vec4(worldPosition.x, worldPosition.y, worldPosition.z, 1.0);
    
    pass_textureCoords = textureCoords;
    
    toCameraVector = (vec4(0.0, 0.0, 0.0, 1.0)).xyz - worldPosition.xyz;
}
