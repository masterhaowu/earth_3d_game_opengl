#version 140

in vec2 textureCoords;

out vec4 out_Colour;

uniform sampler2D colourTexture;

const float contrast = 0.0;

void main(void){

	out_Colour = texture(colourTexture, textureCoords);
    out_Colour.rgb = (out_Colour.rgb - 0.5) * (1.0 + contrast) + 0.5;
    /*
    if (out_Colour.r > 0.995) {
        out_Colour.g = 1;
        out_Colour.b = 1;
    }
     */
    /*
    out_Colour.r = (out_Colour.r - 0.99) * 100;
    if (out_Colour.r < 0) {
        out_Colour.r = 0;
    }
     */

}
