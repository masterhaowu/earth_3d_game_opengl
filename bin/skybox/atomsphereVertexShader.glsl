#version 140

in vec3 position;

out vec2 textureCoords;

uniform mat4 transformationMatrix;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;

void main(void){

	gl_Position = projectionMatrix * viewMatrix * transformationMatrix * vec4(position, 1.0);
	//textureCoords = position.xy * 0.5 + 0.5;
    textureCoords = vec2(position.x * 0.5 + 0.5, position.y * 0.5 + 0.5);
	
}
