#version 400 core

in vec3 finalColour;
in vec4 pass_clipSpace;
in vec2 pass_textureCoords;
in vec3 pass_toCameraVector;
in vec3 pass_normal;

out vec4 out_Colour;

uniform sampler2D reflectionTexture;
uniform sampler2D refractionTexture;
uniform sampler2D dudvMap;
uniform sampler2D normalMap;
uniform sampler2D depthMap;

uniform float moveFactor;
const float waveDistortion = 0.02;


void main(void){
    
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
    vec4 reflectColour = texture(reflectionTexture, reflectTextureCoords);
    vec4 refractColour = texture(refractionTexture, refractTextureCoords);
    
    vec3 viewVector = normalize(pass_toCameraVector);
    float refractiveFactor = dot(viewVector, pass_normal);
    refractiveFactor = clamp(refractiveFactor, 0.0, 1.0);
    
    //out_Colour = vec4(finalColour, 1.0);
    out_Colour = mix(reflectColour, refractColour, refractiveFactor);
    out_Colour = mix(out_Colour, vec4(0.0, 0.3, 0.5, 1.0), 0.0) + vec4(finalColour, 1.0);
    out_Colour.a = clamp(waterDepth/5.0, 0.0, 1.0);
    
    
}
/*
 in vec4 clipSpace;
in vec2 textureCoords;
in vec3 toCameraVector;
in vec3 fromLightVector;

out vec4 out_Color;

uniform sampler2D reflectionTexture;
uniform sampler2D refractionTexture;
uniform sampler2D dudvMap;
uniform sampler2D normalMap;
uniform sampler2D depthMap;

uniform vec3 lightColour;


uniform float moveFactor;

const float waveDistortion = 0.02;
const float shineDamper = 20.0;
const float reflectivity = 0.3;

const float levels = 5.0;

void main(void) {
    
    vec2 ndc = (clipSpace.xy / clipSpace.w) / 2.0 + 0.5;
    
    vec2 refractTextureCoords = vec2(ndc.x, ndc.y);
    vec2 reflectTextureCoords = vec2(ndc.x, -ndc.y);
    
    float near = 0.1;
    float far = 1000.0;
    
    float depth = texture(depthMap, refractTextureCoords).r;
    float floorDistance = 2.0 * near * far / (far + near - (2.0 * depth - 1.0) * (far - near));
    
    depth = gl_FragCoord.z;
    float waterDistance = 2.0 * near * far / (far + near - (2.0 * depth - 1.0) * (far - near));
    
    float waterDepth = floorDistance - waterDistance;
    
    vec2 distortedTexCoords = texture(dudvMap, vec2(textureCoords.x + moveFactor, textureCoords.y)).rg*0.1;
    distortedTexCoords = textureCoords + vec2(distortedTexCoords.x, distortedTexCoords.y+moveFactor);
    vec2 totalDistortion = (texture(dudvMap, distortedTexCoords).rg * 2.0 - 1.0) * waveDistortion * clamp(waterDepth/20.0, 0.0, 1.0);
    
    refractTextureCoords += totalDistortion;
    refractTextureCoords = clamp(refractTextureCoords, 0.001, 0.999);
    
    reflectTextureCoords += totalDistortion;
    reflectTextureCoords.x = clamp(reflectTextureCoords.x, 0.001, 0.999);
    reflectTextureCoords.y = clamp(reflectTextureCoords.y, -0.999, -0.001);
    
    vec4 reflectColour = texture(reflectionTexture, reflectTextureCoords);
    vec4 refractColour = texture(refractionTexture, refractTextureCoords);
    
    
    vec3 viewVector = normalize(toCameraVector);
    //float refractiveFactor = dot(viewVector, vec3(0.0, 1.0, 0.0));

    
    vec4 normalMapColour = texture(normalMap, distortedTexCoords);
    vec3 normal = vec3(normalMapColour.r * 2.0 - 1.0, normalMapColour.b * 3.0, normalMapColour.g * 2.0 - 1.0);
    normal = normalize(normal);
    
    vec3 reflectedLight = reflect(normalize(fromLightVector), normal);
    float specular = max(dot(reflectedLight, viewVector), 0.0);
    specular = pow(specular, shineDamper);
    //float level = floor(specular * levels);
    //specular = level/levels;
    vec3 specularHighlights = lightColour * specular * reflectivity * clamp(waterDepth/5.0, 0.0, 1.0);
    
    float refractiveFactor = dot(viewVector, normal);
    refractiveFactor = clamp(refractiveFactor, 0.0, 1.0);


	out_Color = mix(reflectColour, refractColour, refractiveFactor);
    //out_Color = floor(out_Color * levels);
    //out_Color = out_Color / levels;
    out_Color = mix(out_Color, vec4(0.0, 0.3, 0.5, 1.0), 0.2) + vec4(specularHighlights, 0.0);
    out_Color.a = clamp(waterDepth/5.0, 0.0, 1.0);

}
*/
