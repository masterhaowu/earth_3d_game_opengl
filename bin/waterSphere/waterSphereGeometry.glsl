#version 400 core


layout ( triangles ) in;
layout ( triangle_strip, max_vertices = 3) out;

//in vec4 clipSpace[3];
//in vec2 textureCoords[3];
in vec3 toCameraVector[3];
in vec3 fromLightVector[3];
in vec4 worldPosition[3];
in float waterDepth[3];

//out vec4 pass_clipSpace;
//out vec2 pass_textureCoords;
out vec3 finalColour;
out vec3 pass_toCameraVector;
out vec3 pass_normal;
out float pass_waterDepth;

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

//const vec3 waterColour = vec3(0.04, 0.55, 0.7);
uniform vec3 waterColour;
const float ambientLighting = 0.3;

vec3 calculateTriangleNormal(){
    vec3 tangent = worldPosition[1].xyz - worldPosition[0].xyz;
    vec3 bitangent = worldPosition[2].xyz - worldPosition[0].xyz;
    vec3 normal = cross(tangent, bitangent);
    return normalize(normal);
}


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
    //pass_clipSpace = clipSpace[0];
    //pass_textureCoords = textureCoords[0];
    pass_waterDepth = waterDepth[0];
    pass_toCameraVector = toCameraVector[0];
    pass_normal = normal;
    EmitVertex();
    
    tempPosition = gl_in[1].gl_Position;
    gl_Position = tempPosition;
    finalColour = colour + calculateSpecular(toCameraVector[1], normal, 1);
    //pass_clipSpace = clipSpace[1];
    //pass_textureCoords = textureCoords[1];
    pass_waterDepth = waterDepth[1];
    pass_toCameraVector = toCameraVector[1];
    pass_normal = normal;
    EmitVertex();
    
    tempPosition = gl_in[2].gl_Position;
    gl_Position = tempPosition;
    finalColour = colour + calculateSpecular(toCameraVector[1], normal, 1);
    //pass_clipSpace = clipSpace[2];
    //pass_textureCoords = textureCoords[2];
    pass_waterDepth = waterDepth[2];
    pass_toCameraVector = toCameraVector[2];
    pass_normal = normal;
    EmitVertex();
    
    EndPrimitive();
    
    
   

}
