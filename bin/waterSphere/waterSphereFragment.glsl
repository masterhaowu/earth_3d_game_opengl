#version 400 core

in vec3 finalColour;
//in vec4 pass_clipSpace;
//in vec2 pass_textureCoords;
in vec3 pass_toCameraVector;
in vec3 pass_normal;
in float pass_waterDepth;

out vec4 out_Colour;

//uniform sampler2D reflectionTexture;
//uniform sampler2D refractionTexture;
//uniform sampler2D dudvMap;
//uniform sampler2D normalMap;
//uniform sampler2D depthMap;

uniform float moveFactor;
const float waveDistortion = 0.02;


void main(void){
    /*
    vec2 ndc = (pass_clipSpace.xy / pass_clipSpace.w) / 2.0 + 0.5;
    
    vec2 refractTextureCoords = vec2(ndc.x, ndc.y);
    vec2 reflectTextureCoords = vec2(ndc.x, -ndc.y);
    
    float near = 0.1;
    float far = 1000.0;
    
    float depth = texture(depthMap, refractTextureCoords).r;
    float floorDistance = 2.0 * near * far / (far + near - (2.0 * depth - 1.0) * (far - near));
    
    depth = gl_FragCoord.z;
    float waterDistance = 2.0 * near * far / (far + near - (2.0 * depth - 1.0) * (far - near));
    
    float waterDepth = floorDistance - waterDistance;
     */
    /*
    vec2 distortedTexCoords = texture(dudvMap, vec2(pass_textureCoords.x + moveFactor, pass_textureCoords.y)).rg*0.1;
    distortedTexCoords = pass_textureCoords + vec2(distortedTexCoords.x, distortedTexCoords.y+moveFactor);
    vec2 totalDistortion = (texture(dudvMap, distortedTexCoords).rg * 2.0 - 1.0) * waveDistortion * clamp(waterDepth/20.0, 0.0, 1.0);
    
    refractTextureCoords += totalDistortion;
    refractTextureCoords = clamp(refractTextureCoords, 0.001, 0.999);
    
    reflectTextureCoords += totalDistortion;
    reflectTextureCoords.x = clamp(reflectTextureCoords.x, 0.001, 0.999);
    reflectTextureCoords.y = clamp(reflectTextureCoords.y, -0.999, -0.001);
    */
    
    //vec4 reflectColour = texture(reflectionTexture, reflectTextureCoords);
    //vec4 refractColour = texture(refractionTexture, refractTextureCoords);
    
    vec3 viewVector = normalize(pass_toCameraVector);
    float refractiveFactor = dot(viewVector, pass_normal);
    refractiveFactor = clamp(refractiveFactor, 0.0, 1.0);
    
    out_Colour = vec4(finalColour, 1.0);
    out_Colour.a = mix(0.9, 0.6, refractiveFactor);
    //out_Colour = mix(reflectColour, refractColour, refractiveFactor);
    //out_Colour = mix(out_Colour, vec4(0.0, 0.3, 0.5, 1.0), 0.0) + vec4(finalColour, 1.0);
    //out_Colour.a = clamp(pass_waterDepth/5.0, 0.0, 1.0);
    float clamped_waterDepth = clamp(pass_waterDepth * 300, 0, 1);
    out_Colour.a = mix(0, out_Colour.a, clamped_waterDepth);
    //out_Colour = vec4(0, 0, 1, 1);
    
}
