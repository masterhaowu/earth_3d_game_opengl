#version 140

in vec2 pass_textureCoords;
in float pass_brightness;
in float pass_average;
//in float pass_z;

out vec4 outColor;

uniform sampler2D backgroundTexture1;
uniform sampler2D iconTexture1;
uniform sampler2D backgroundTexture2;
uniform sampler2D iconTexture2;


uniform bool useColourFilter;
uniform vec3 colourFilter1;
uniform vec3 colourFilter2;


uniform float stateTransition;

uniform float scaleDown1;
uniform float scaleDown2;


const float transitionRange = 0.1;



void main(void){
    
    
    float scaleDown = scaleDown1;
    scaleDown = scaleDown * (1 + (pass_brightness)/2);
    vec2 iconTextureCoords1 = vec2(pass_textureCoords.x*scaleDown + 0.5 - scaleDown/2, pass_textureCoords.y* scaleDown + 0.5 - scaleDown/2);
    
    scaleDown = scaleDown2;
    scaleDown = scaleDown * (1 + (pass_brightness)/2);
    vec2 iconTextureCoords2 = vec2(pass_textureCoords.x*scaleDown + 0.5 - scaleDown/2, pass_textureCoords.y* scaleDown + 0.5 - scaleDown/2);
    
    
    vec4 backgroundColour1;
    vec4 backgroundColour2;
    vec4 iconColour1;
    vec4 iconColour2;
    
    backgroundColour1 = texture(backgroundTexture1,pass_textureCoords);
    backgroundColour2 = texture(backgroundTexture2,pass_textureCoords);
    iconColour1 = texture(iconTexture1, iconTextureCoords1);
    iconColour2 = texture(iconTexture2, iconTextureCoords2);


    
    if (pass_textureCoords.x < ((1 - 1/scaleDown)/2) || pass_textureCoords.x > (0.5 + (1/scaleDown)/2)  || pass_textureCoords.y < ((1 - 1/scaleDown)/2)  || pass_textureCoords.y > (0.5 + (1/scaleDown)/2)  ) {
        iconColour1 = vec4(0, 0, 0, 0);
        iconColour2 = vec4(0, 0, 0, 0);
    }
    
    
    float transitionColourPrecentage = (pass_average - (stateTransition - transitionRange))/(2*transitionRange);
    transitionColourPrecentage = clamp(transitionColourPrecentage, 0, 1);

    vec4 iconColour = transitionColourPrecentage * iconColour1 + (1 - transitionColourPrecentage) * iconColour2;
    
    outColor = backgroundColour1;
    
    if (useColourFilter == false) {
        vec4 outColor1 = outColor;
        vec4 outColor2 = outColor;
        outColor1.x = backgroundColour1.x * pass_brightness;
        outColor1.y = backgroundColour1.y * pass_brightness;
        outColor1.z = backgroundColour1.z * pass_brightness;
        outColor2.x = backgroundColour2.x * pass_brightness;
        outColor2.y = backgroundColour2.y * pass_brightness;
        outColor2.z = backgroundColour2.z * pass_brightness;


        outColor = transitionColourPrecentage * outColor1 + (1 - transitionColourPrecentage) * outColor2;
    }
    
    
    if (useColourFilter == true) {
        float borderThresholdLow = 0.19;
        float borderThresholdHigh = 0.21;
        
        float radius = (pass_textureCoords.x - 0.5) * (pass_textureCoords.x - 0.5) + (pass_textureCoords.y - 0.5) * (pass_textureCoords.y - 0.5);
        
        float borderColourPrecentage = (radius - borderThresholdLow)/(borderThresholdHigh - borderThresholdLow);
        borderColourPrecentage = clamp(borderColourPrecentage, 0, 1);
        
        outColor.x = backgroundColour1.x * pass_brightness;
        outColor.y = backgroundColour1.y * pass_brightness;
        outColor.z = backgroundColour1.z * pass_brightness;
        
        vec4 colour1 = vec4(0, 0, 0, outColor.w);
        vec4 colour2 = vec4(0, 0, 0, outColor.w);
        colour1.x = outColor.x * colourFilter1.x * (1-borderColourPrecentage) + borderColourPrecentage * outColor.x;
        colour1.y = outColor.y * colourFilter1.y * (1-borderColourPrecentage) + borderColourPrecentage * outColor.y;
        colour1.z = outColor.z * colourFilter1.z * (1-borderColourPrecentage) + borderColourPrecentage * outColor.z;
        colour2.x = outColor.x * colourFilter2.x * (1-borderColourPrecentage) + borderColourPrecentage * outColor.x;
        colour2.y = outColor.y * colourFilter2.y * (1-borderColourPrecentage) + borderColourPrecentage * outColor.y;
        colour2.z = outColor.z * colourFilter2.z * (1-borderColourPrecentage) + borderColourPrecentage * outColor.z;
        
                /*
        if (pass_averageX > stateTransition) {
            
            outColor = colour2;
        }
        else{
            outColor = colour1;
        }
         */
        outColor = transitionColourPrecentage * colour1 + (1 - transitionColourPrecentage) * colour2;
    }

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
