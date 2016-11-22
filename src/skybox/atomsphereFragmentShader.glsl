#version 140

in vec2 textureCoords;

out vec4 out_Colour;

uniform sampler2D colourTexture;
//uniform float zoom;
uniform float terrainScale;
uniform float atomScale;

//const float contrast = 0.0;
//const float threshold1 = 0.09; //pure white
//const float threshold2 = 0.10; //transparency
//const float threshold3 = 0.15; //sky colour

void main(void){
    
    float threshold3 = (terrainScale / atomScale) * (terrainScale / atomScale);
    
    float threshold1 = threshold3 * (terrainScale / atomScale);
    //float threshold2 = threshold1 * 3;
    float threshold2 = 0.10;
    

	//out_Colour = texture(colourTexture, textureCoords);
    //out_Colour.rgb = (out_Colour.rgb - 0.5) * (1.0 + contrast) + 0.5;
    //out_Colour = vec4(0, 0, 1, 1);
    float radius = (textureCoords.x - 0.5) * (textureCoords.x - 0.5) + (textureCoords.y - 0.5) * (textureCoords.y - 0.5);
    
    
    float whitePrecentage = 1;
    float transparency = 1;
    float increment1 = 1.0/(threshold3 - threshold1);
    float increment2 = 1.0/(0.25 - threshold2);
    if (radius > threshold1) {
        whitePrecentage = 1 - (radius - threshold1) * increment1;
    }

    if (radius > threshold2) {
        transparency = 1 - (radius - threshold2) * increment2;
    }
    transparency = transparency * 0.8;
    float red = 3.0/4.0 + whitePrecentage / 4.0;
    float green = 7.0/8.0 + whitePrecentage / 8.0;
    float blue = 1 - whitePrecentage/2.0;
    out_Colour = vec4(red, green, blue, transparency);
    //out_Colour = vec4(transparency, transparency, transparency, transparency);
    //out_Colour.x = 0.3;

}
