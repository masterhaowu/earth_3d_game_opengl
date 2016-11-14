#version 140

in vec2 position;

//out vec2 textureCoords[11];
out vec2 textureCoords;

//uniform float targetWidth;
//uniform float targetHeight;
//uniform bool horizontal;

void main(void){

	gl_Position = vec4(position, 0.0, 1.0);
	textureCoords = position * 0.5 + 0.5;
    
    
	
}
