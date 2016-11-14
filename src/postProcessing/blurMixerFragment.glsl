#version 140

in vec2 textureCoords;

out vec4 out_Colour;

uniform sampler2D originalColourTexture;
uniform sampler2D blurredColourTexture;
uniform sampler2D depthTexture;

const float near = 0.1;
const float far = 3000;


const float farBlurThreshold = 1.3;
const float farBlurRange = 0.5;

const float nearBlurThreshold = 0.3;
const float nearBlurRange = 0.1; // this should be smaller than nearBlurThreshold

void main(void){
    float applyBlur = 0;
    
    float centerDepth = texture(depthTexture, vec2(0.5, 0.5)).r;
    //float depth = texture(depthTexture, textureCoords[5]).r - depthOffset;
    float centerDistance = 2.0 * near * far / (far + near - (2.0 * centerDepth - 1.0) * (far - near));
    float depth = texture(depthTexture, textureCoords).r;
    float distance = 2.0 * near * far / (far + near - (2.0 * depth - 1.0) * (far - near));
    
    //float ratio = (depth - centerDepth)/centerDepth;
    float ratio = distance/centerDistance;
    out_Colour = vec4(0.0, 0.0, 0.0, 0.0);
    /*
    if (ratio > 0.0003){
        applyBlur = true;
    }
    else if (depth < centerDepth && depth < 0.998){
        applyBlur = true;
    }
     */
    
    if (ratio > farBlurThreshold) {
        applyBlur = (ratio - farBlurThreshold) / farBlurRange;
    }
    
    else if (ratio < nearBlurThreshold){
        applyBlur = (nearBlurThreshold - ratio) / nearBlurRange;
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
