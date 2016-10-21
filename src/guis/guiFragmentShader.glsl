#version 140

in vec2 textureCoords;

out vec4 out_Color;

uniform sampler2D guiTexture;
uniform bool useSolidColour;
uniform vec3 solidColour;
uniform bool useHighlightedColour;
uniform vec3 highlightedColour;
uniform bool highlighted;
uniform float transparency;

uniform bool useBorder;
uniform vec2 borderPrecentage;
uniform vec3 borderColour;
uniform float borderTransparency;


void main(void){

	out_Color = texture(guiTexture,textureCoords);
    
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
    if (useBorder) {
        if (textureCoords.x < borderPrecentage.x || textureCoords.x > (1 - borderPrecentage.x) || textureCoords.y < borderPrecentage.y || textureCoords.y > (1 - borderPrecentage.y)) {
            out_Color = vec4(borderColour, borderTransparency);
        }
    }
    
    
      //  out_Color.w = transparency;
    //}
    //else{
    //    out_Color.w = 0.0;
    //}
   

}
