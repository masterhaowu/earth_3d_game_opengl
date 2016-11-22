#version 140

in vec2 textureCoords;

out vec4 out_Colour;

uniform sampler2D originalColourTexture;
uniform sampler2D blurredColourTexture;
uniform sampler2D depthTexture;

uniform float near;
uniform float far;


const float farBlurThreshold = 1.7;
const float farBlurRange = 1.0;

const float nearBlurThreshold = 0.6;
const float nearBlurRange = 0.1; // this should be smaller than nearBlurThreshold

void main(void){
    float applyBlur = 0;
    
    //float centerDepth = texture(depthTexture, vec2(0.5, 0.5)).r;
    float upperCenterDepth = texture(depthTexture, vec2(0.5, 0.65)).r;
    float lowerCenterDepth = texture(depthTexture, vec2(0.5, 0.35)).r;
    //float centerDistance = 2.0 * near * far / (far + near - (2.0 * centerDepth - 1.0) * (far - near));
    float upperCenterDistance = 2.0 * near * far / (far + near - (2.0 * upperCenterDepth - 1.0) * (far - near));
    float lowerCenterDistance = 2.0 * near * far / (far + near - (2.0 * lowerCenterDepth - 1.0) * (far - near));


    float depth = texture(depthTexture, textureCoords).r;
    float distance = 2.0 * near * far / (far + near - (2.0 * depth - 1.0) * (far - near));
    
    //float ratio = (depth - centerDepth)/centerDepth;
    
    //float centerRatio = distance/centerDistance;
    float upperCenterRatio = distance/upperCenterDistance;
    float lowerCenterRatio = distance/lowerCenterDistance;
    
    //float ratio = 1;
    
    float maxRatio = 1;
    float minRatio = 1;
    
    if (upperCenterRatio > lowerCenterRatio) {
        maxRatio = upperCenterRatio;
        minRatio = lowerCenterRatio;
    }
    else {
        minRatio = upperCenterRatio;
        maxRatio = lowerCenterRatio;
    }
    
    //float ratio = distance/centerDistance;
    out_Colour = vec4(0.0, 0.0, 0.0, 0.0);
    /*
    if (ratio > 0.0003){
        applyBlur = true;
    }
    else if (depth < centerDepth && depth < 0.998){
        applyBlur = true;
    }
     */
    
    if (minRatio > farBlurThreshold) {
        applyBlur = (minRatio - farBlurThreshold) / farBlurRange;
    }
    
    else if (maxRatio < nearBlurThreshold){
        applyBlur = (nearBlurThreshold - maxRatio) / nearBlurRange;
    }
    
    /*
    if (applyBlur) {
        out_Colour += texture(blurredColourTexture, textureCoords);
        
    }
    else {
        out_Colour = texture(originalColourTexture, textureCoords);
    }
    */
    applyBlur = clamp(applyBlur, 0.0, 1.0);
    
    out_Colour = texture(blurredColourTexture, textureCoords) * applyBlur + texture(originalColourTexture, textureCoords) * (1 - applyBlur);
   

}
