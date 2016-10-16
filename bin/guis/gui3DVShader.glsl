#version 140

in vec3 position;

out vec2 textureCoords;
out vec4 worldPosition;

uniform mat4 transformationMatrix;
//uniform mat4 viewMatrix;
//uniform mat4 projectionMatrix;

void main(void){
    
    worldPosition = vec4(position.x, position.y, position.z, 1.0);
    
    gl_Position = transformationMatrix * vec4(position.x, position.y, 0.0, 1.0);
    //gl_Position = projectionMatrix * viewMatrix * transformationMatrix * vec4(position, 1.0);
	textureCoords = vec2((position.x+1.0)/2.0, 1 - (position.y+1.0)/2.0);
}
