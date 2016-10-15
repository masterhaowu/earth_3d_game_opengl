#version 140

in vec2 pass_textureCoords;
in float pass_brightness;

out vec4 out_Color;

uniform sampler2D guiTexture;
/*
uniform bool useSolidColour;
uniform vec3 solidColour;
uniform bool useHighlightedColour;
uniform vec3 highlightedColour;
uniform bool highlighted;
uniform float transparency;
*/

void main(void){

	//out_Color = texture(guiTexture,pass_textureCoords);
    out_Color.w = pass_brightness;
    out_Color = vec4(pass_brightness, pass_brightness, pass_brightness, 0.5);
    /*
    //if (out_Color.w > 0.5) {
    if (useSolidColour == true) {
        out_Color.x = solidColour.x;
        out_Color.y = solidColour.y;
        out_Color.z = solidColour.z;
    }
    if (useHighlightedColour == true && highlighted == true) {
        out_Color.x = highlightedColour.x;
        out_Color.y = highlightedColour.y;
        out_Color.z = highlightedColour.z;
    }
    if (transparency < 1) {
        out_Color.w = out_Color.w * transparency;
    }
      //  out_Color.w = transparency;
    //}
    //else{
    //    out_Color.w = 0.0;
    //}
   
     */
}
