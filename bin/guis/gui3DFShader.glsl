#version 140

in vec2 pass_textureCoords;
in float pass_brightness;
in float pass_averageX;
//in float pass_z;

out vec4 outColor;

uniform sampler2D backgroundTexture;
uniform sampler2D iconTexture;
uniform sampler2D backgroundTexture2;
uniform sampler2D iconTexture2;
uniform sampler2D backgroundTexture3;
uniform sampler2D iconTexture3;

/*
uniform bool useSolidColour;
uniform vec3 solidColour;
uniform bool useHighlightedColour;
uniform vec3 highlightedColour;
uniform bool highlighted;
uniform float transparency;
*/
uniform bool useColourFilter;
uniform vec3 colourFilter[3];
//uniform vec3 colourFilter2;

uniform int currentState;
uniform int nextState;

uniform float stateTransition;

const float transitionRange = 0.1;



const float originalScaleDown = 1.1;
//const float scaleDown = 1.7;

void main(void){
    
    
    float scaleDown = originalScaleDown;
    scaleDown = scaleDown * (1 + (pass_brightness)/2);
    vec2 iconTextureCoords = vec2(pass_textureCoords.x*scaleDown + 0.5 - scaleDown/2, pass_textureCoords.y* scaleDown + 0.5 - scaleDown/2);
    
    //iconTextureCoords = vec2(pass_textureCoords.x * scaleDown - 0.5, pass_textureCoords.y  * scaleDown - 0.5);
    
    vec4 backgroundColour;
    vec4 backgroundColour2;
    vec4 iconColour1;
    vec4 iconColour2;
    
    //check the states to determine the backgroundColour
    if (currentState == 0) {
        backgroundColour = texture(backgroundTexture,pass_textureCoords);
        iconColour1 = texture(iconTexture, iconTextureCoords);
    }
    else if (currentState == 1){
        backgroundColour = texture(backgroundTexture2,pass_textureCoords);
        iconColour1 = texture(iconTexture2, iconTextureCoords);
    }
    else{
        backgroundColour = texture(backgroundTexture3,pass_textureCoords);
        iconColour1 = texture(iconTexture3, iconTextureCoords);
    }
    
    if (nextState == 0) {
        backgroundColour2 = texture(backgroundTexture,pass_textureCoords);
        iconColour2 = texture(iconTexture, iconTextureCoords);
    }
    else if (nextState == 1){
        backgroundColour2 = texture(backgroundTexture2,pass_textureCoords);
        iconColour2 = texture(iconTexture2, iconTextureCoords);
    }
    else{
        backgroundColour2 = texture(backgroundTexture3,pass_textureCoords);
        iconColour2 = texture(iconTexture3, iconTextureCoords);
    }
    

    
    
    //vec4 iconColour = texture(iconTexture, iconTextureCoords);
    
    if (pass_textureCoords.x < ((1 - 1/scaleDown)/2) || pass_textureCoords.x > (0.5 + (1/scaleDown)/2)  || pass_textureCoords.y < ((1 - 1/scaleDown)/2)  || pass_textureCoords.y > (0.5 + (1/scaleDown)/2)  ) {
        iconColour1 = vec4(0, 0, 0, 0);
        iconColour2 = vec4(0, 0, 0, 0);
    }
    
    //vec4 iconColour2 = texture(iconTexture2, iconTextureCoords);
    /*
    if (pass_textureCoords.x < ((1 - 1/scaleDown)/2) || pass_textureCoords.x > (0.5 + (1/scaleDown)/2)  || pass_textureCoords.y < ((1 - 1/scaleDown)/2)  || pass_textureCoords.y > (0.5 + (1/scaleDown)/2)  ) {
        iconColour2 = vec4(0, 0, 0, 0);
    }
    */
    float transitionColourPrecentage = (pass_averageX - (stateTransition - transitionRange))/(2*transitionRange);
    transitionColourPrecentage = clamp(transitionColourPrecentage, 0, 1);

    
    
    //vec4 backgroundColour = texture(backgroundTexture,pass_textureCoords);
    //vec4 backgroundColour2 = texture(backgroundTexture2,pass_textureCoords);
    vec4 iconColour = transitionColourPrecentage * iconColour1 + (1 - transitionColourPrecentage) * iconColour2;
    
	//outColor = texture(backgroundTexture,pass_textureCoords);
    outColor = backgroundColour;
    
    if (useColourFilter == false) {
        vec4 outColor1 = outColor;
        vec4 outColor2 = outColor;
        outColor1.x = backgroundColour.x * pass_brightness;
        outColor1.y = backgroundColour.y * pass_brightness;
        outColor1.z = backgroundColour.z * pass_brightness;
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
        
        outColor.x = backgroundColour.x * pass_brightness;
        outColor.y = backgroundColour.y * pass_brightness;
        outColor.z = backgroundColour.z * pass_brightness;
        
        vec4 colour1 = vec4(0, 0, 0, outColor.w);
        vec4 colour2 = vec4(0, 0, 0, outColor.w);
        colour1.x = outColor.x * colourFilter[currentState].x * (1-borderColourPrecentage) + borderColourPrecentage * outColor.x;
        colour1.y = outColor.y * colourFilter[currentState].y * (1-borderColourPrecentage) + borderColourPrecentage * outColor.y;
        colour1.z = outColor.z * colourFilter[currentState].z * (1-borderColourPrecentage) + borderColourPrecentage * outColor.z;
        colour2.x = outColor.x * colourFilter[nextState].x * (1-borderColourPrecentage) + borderColourPrecentage * outColor.x;
        colour2.y = outColor.y * colourFilter[nextState].y * (1-borderColourPrecentage) + borderColourPrecentage * outColor.y;
        colour2.z = outColor.z * colourFilter[nextState].z * (1-borderColourPrecentage) + borderColourPrecentage * outColor.z;
        
                /*
        if (pass_averageX > stateTransition) {
            
            outColor = colour2;
        }
        else{
            outColor = colour1;
        }
         */
        outColor = transitionColourPrecentage * colour2 + (1 - transitionColourPrecentage) * colour1;
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
