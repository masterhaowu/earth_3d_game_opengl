#version 140

in vec2 position;

out vec2 textureCoords[11];

uniform float targetWidth;
uniform float targetHeight;
uniform bool horizontal;

void main(void){

	gl_Position = vec4(position, 0.0, 1.0);
	vec2 centerTextureCoords = position * 0.5 + 0.5;
    if (horizontal == true) {
        float pixelWidth = 1.0 / targetWidth;
        for (int i=-5; i<=5; i++) {
            textureCoords[i+5] = centerTextureCoords + vec2(pixelWidth * i, 0.0);
        }
    } else {
        float pixelHeight = 1.0 / targetHeight;
        for (int i=-5; i<=5; i++) {
            textureCoords[i+5] = centerTextureCoords + vec2(0.0, pixelHeight * i);
        }
    }
    
	
}
