#version 140

in vec2 position;


in mat4 modelViewMatrix;
in vec4 texOffsets;
in float blendFactor;


out vec2 textureCoords1;
out vec2 textureCoords2;
out float blend;
out float pass_numberOfRows;

uniform mat4 projectionMatrix;



uniform float numberOfRows;


void main(void){
    
    vec2 textureCoords = position + vec2(0.5, 0.5);
    textureCoords.y = 1.0 - textureCoords.y;
    textureCoords /= numberOfRows;
    textureCoords1.x = textureCoords.x + texOffsets.x;
    textureCoords1.y = textureCoords.y + texOffsets.y;
    textureCoords2 = textureCoords + texOffsets.zw;
    blend = blendFactor;
    pass_numberOfRows = numberOfRows;

	gl_Position = projectionMatrix * modelViewMatrix * vec4(position, 0.0, 1.0);

}
