#version 140

in vec2 textureCoords[11];

out vec4 out_Colour;

uniform sampler2D colourTexture;
uniform sampler2D depthTexture;

const float depthOffset = 0.98;

void main(void){

	//out_Colour = texture(colourTexture, textureCoords);
    //out_Colour.rgb = (out_Colour.rgb - 0.5) * (1.0 + contrast) + 0.5;
    float depth = texture(depthTexture, textureCoords[5]).r - depthOffset;
    float scale = 1.0 / (1.0 - depthOffset);
    depth = depth * scale;
    //float floorDistance = 2.0 * near * far / (far + near - (2.0 * depth - 1.0) * (far - near));
    out_Colour = vec4(0.0, 0.0, 0.0, 0.0);
    if (depth > 0.98) {
        out_Colour += texture(colourTexture, textureCoords[0]) * 0.0093;
        out_Colour += texture(colourTexture, textureCoords[1]) * 0.028002;
        out_Colour += texture(colourTexture, textureCoords[2]) * 0.065984;
        out_Colour += texture(colourTexture, textureCoords[3]) * 0.121703;
        out_Colour += texture(colourTexture, textureCoords[4]) * 0.175713;
        out_Colour += texture(colourTexture, textureCoords[5]) * 0.198596;
        out_Colour += texture(colourTexture, textureCoords[6]) * 0.175713;
        out_Colour += texture(colourTexture, textureCoords[7]) * 0.121703;
        out_Colour += texture(colourTexture, textureCoords[8]) * 0.065984;
        out_Colour += texture(colourTexture, textureCoords[9]) * 0.028002;
        out_Colour += texture(colourTexture, textureCoords[10]) * 0.0093;
    }
    else {
        out_Colour = texture(colourTexture, textureCoords[5]);
    }
    
   

}
