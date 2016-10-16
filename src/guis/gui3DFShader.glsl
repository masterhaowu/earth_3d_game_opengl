#version 140

in vec2 pass_textureCoords;
in float pass_brightness;
//in float pass_z;

out vec4 outColor;

uniform sampler2D backgroundTexture;
uniform sampler2D iconTexture;
/*
uniform bool useSolidColour;
uniform vec3 solidColour;
uniform bool useHighlightedColour;
uniform vec3 highlightedColour;
uniform bool highlighted;
uniform float transparency;
*/
const float originalScaleDown = 1;
//const float scaleDown = 1.7;

void main(void){
    
    
    float scaleDown = originalScaleDown;
    scaleDown = scaleDown * (1 + (pass_brightness));
    vec2 iconTextureCoords = vec2(pass_textureCoords.x*scaleDown + 0.5 - scaleDown/2, pass_textureCoords.y* scaleDown + 0.5 - scaleDown/2);
    
    //iconTextureCoords = vec2(pass_textureCoords.x * scaleDown - 0.5, pass_textureCoords.y  * scaleDown - 0.5);
    
    vec4 iconColour = texture(iconTexture, iconTextureCoords);
    
    if (pass_textureCoords.x < ((1 - 1/scaleDown)/2) || pass_textureCoords.x > (0.5 + (1/scaleDown)/2)  || pass_textureCoords.y < ((1 - 1/scaleDown)/2)  || pass_textureCoords.y > (0.5 + (1/scaleDown)/2)  ) {
        iconColour = vec4(0, 0, 0, 0);
    }
    
    
    vec4 backgroundColour = texture(backgroundTexture,pass_textureCoords);
	outColor = texture(backgroundTexture,pass_textureCoords);
    
    outColor.x = backgroundColour.x * pass_brightness;
    outColor.y = backgroundColour.y * pass_brightness;
    outColor.z = backgroundColour.z * pass_brightness;
    if (iconColour.w > 0.7) {
        iconColour.w = 0.7;
    }
    if (iconColour.w > 0) {
        outColor.x = mix(outColor.x, 1, iconColour.w);
        outColor.y = mix(outColor.y, 1, iconColour.w);
        outColor.z = mix(outColor.z, 1, iconColour.w);
    }
    
    
    //outColor = texture(iconTexture, pass_textureCoords);
    
    //out_Color.w = pass_z;
    //out_Color.w = pass_brightness;
    //out_Color = vec4(pass_brightness, pass_brightness, pass_brightness, 1);
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
