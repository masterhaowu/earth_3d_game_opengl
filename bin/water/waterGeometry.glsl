#version 400 core


layout ( triangles ) in;
layout ( triangle_strip, max_vertices = 3) out;

in vec4 clipSpace[3];
in vec2 textureCoords[3];
in vec3 toCameraVector[3];
in vec3 fromLightVector[3];
in vec4 worldPosition[3];

out vec4 pass_clipSpace;
out vec2 pass_textureCoords;
out vec3 finalColour;
out vec3 pass_toCameraVector;
out vec3 pass_normal;

//uniform sampler2D reflectionTexture;
//uniform sampler2D refractionTexture;
//uniform sampler2D dudvMap;
//uniform sampler2D normalMap;
//uniform sampler2D depthMap;

uniform vec3 lightColour;


//uniform float moveFactor;

const float waveDistortion = 0.02;
const float shineDamper = 20.0;
const float reflectivity = 0.3;

const float levels = 5.0;

const vec3 waterColour = vec3(0.1, 0.3, 0.5);
const float ambientLighting = 0.3;

vec3 calculateTriangleNormal(){
    vec3 tangent = worldPosition[1].xyz - worldPosition[0].xyz;
    vec3 bitangent = worldPosition[2].xyz - worldPosition[0].xyz;
    vec3 normal = cross(tangent, bitangent);
    return normalize(normal);
}
/*
vec3 calculateTriangleNormal(){
    vec3 tangent = gl_in[1].gl_Position.xyz - gl_in[0].gl_Position.xyz;
    vec3 bitangent = gl_in[2].gl_Position.xyz - gl_in[0].gl_Position.xyz;
    vec3 normal = cross(tangent, bitangent);
    return normalize(normal);
}
*/

vec3 calculateSpecular(vec3 toCameraVector, vec3 normal, int number){
    vec3 viewVector = normalize(toCameraVector);
    vec3 lightDirection = normalize(fromLightVector[number]);
    vec3 reflectedLightDirection = reflect(lightDirection, normal);
    float specularFactor = dot(reflectedLightDirection, viewVector);
    specularFactor = max(pow(specularFactor, shineDamper), 0.0);
    return lightColour * specularFactor * reflectivity;
}


void main(void) {
    
    vec3 normal = calculateTriangleNormal();
    vec3 lightDirection = normalize(fromLightVector[0]);
    float brightness = max(dot(-lightDirection, normal), ambientLighting);
    vec3 colour = waterColour * brightness;
    
    vec4 tempPosition = gl_in[0].gl_Position;
    gl_Position = tempPosition;
    finalColour = colour + calculateSpecular(toCameraVector[0], normal, 0);
    pass_clipSpace = clipSpace[0];
    pass_textureCoords = textureCoords[0];
    pass_toCameraVector = toCameraVector[0];
    pass_normal = normal;
    EmitVertex();
    
    tempPosition = gl_in[1].gl_Position;
    gl_Position = tempPosition;
    finalColour = colour + calculateSpecular(toCameraVector[1], normal, 1);
    pass_clipSpace = clipSpace[1];
    pass_textureCoords = textureCoords[1];
    pass_toCameraVector = toCameraVector[1];
    pass_normal = normal;
    EmitVertex();
    
    tempPosition = gl_in[2].gl_Position;
    gl_Position = tempPosition;
    finalColour = colour + calculateSpecular(toCameraVector[1], normal, 1);
    pass_clipSpace = clipSpace[2];
    pass_textureCoords = textureCoords[2];
    pass_toCameraVector = toCameraVector[2];
    pass_normal = normal;
    EmitVertex();
    
    EndPrimitive();
    
    
    /*
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
     */

}
