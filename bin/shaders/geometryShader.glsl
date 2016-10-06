#version 400 core

layout ( triangles ) in;
layout ( triangle_strip, max_vertices = 3 ) out;

//in vec2 tc;
in vec2 pass_textureCoords[3];
in vec3 toLightVector[3][4];
in vec3 toCameraVector[3];
//in vec3 surfaceNormal[3];

in vec4 worldPosition[3];

out vec4 finalColour;

uniform sampler2D textureSampler;




uniform vec3 lightColour[4];
uniform vec3 attenuation[4];
uniform float shineDamper;
uniform float reflectivity;
uniform vec3 skyColour;
uniform float mapSize;

uniform float terrainGlobalOffset;

const float levels = 5.0;
const int pcfCount = 1;
const float totalTexels = (pcfCount * 2.0 + 1.0) * (pcfCount * 2.0 + 1.0);



vec3 calculateTriangleNormal(){
    vec3 tangent = worldPosition[1].xyz - worldPosition[0].xyz;
    vec3 bitangent = worldPosition[2].xyz - worldPosition[0].xyz;
    vec3 normal = cross(tangent, bitangent);
    return normalize(normal);
}



vec4 calculateDiffuseAndSpecular(vec3 toCameraVector, vec3 unitNormal, int number, vec4 averageColour){
    
    vec3 unitCameraVector = normalize(toCameraVector.xyz);
    vec3 totalSpecular = vec3(0.0);
    vec3 totalDiffuse = vec3(0.0);
    for (int i=0; i<4; i++){
        float distance = length(toLightVector[number][i]);
        float attFactor = attenuation[i].x + (attenuation[i].y * distance) + (attenuation[i].z * distance * distance);
        vec3 unitLightVector = normalize(toLightVector[number][i]);
        float nDotl = dot(unitNormal, unitLightVector);
        float brightness = max(nDotl, 0.0);
        
        
        vec3 lightDirection = -unitLightVector;
        vec3 reflectedLightDirection = reflect(lightDirection, unitNormal);
        float specularFactor = dot(reflectedLightDirection, unitCameraVector);
        specularFactor = max(specularFactor, 0.0);
        float dampedFactor = pow(specularFactor, shineDamper);
        
        totalDiffuse = totalDiffuse + (brightness * lightColour[i])/attFactor;
        totalSpecular = totalSpecular + (dampedFactor * reflectivity * lightColour[i])/attFactor;
    }
    totalDiffuse = max(totalDiffuse, terrainGlobalOffset);
    
    vec4 tempFinalColour = vec4(totalDiffuse, 1.0) * averageColour + vec4(totalSpecular, 1.0);
    tempFinalColour = mix(tempFinalColour, averageColour, 0.3);
    return tempFinalColour;
    
}

vec4 calculateAverageTextureColour(){
    vec4 totalColour = vec4(0,0,0,0);
   
    for (int i=0; i<3; i++) {

        totalColour += texture(textureSampler, pass_textureCoords[i]);
    }
    totalColour /= 3;
    return totalColour;
}

void main()
{
   
    
    vec3 unitNormal = calculateTriangleNormal();
    //vec3 unitNormal = normalize()
    
    
    vec4 averageColour = calculateAverageTextureColour();
    
    
    vec4 tempPosition = gl_in[0].gl_Position;
    gl_Position = tempPosition;
    finalColour = calculateDiffuseAndSpecular(toCameraVector[0], unitNormal, 0, averageColour);// + averageColour/2;
    //finalColour = averageColour;
    EmitVertex();
    
    tempPosition = gl_in[1].gl_Position;
    gl_Position = tempPosition;
    finalColour = calculateDiffuseAndSpecular(toCameraVector[1], unitNormal, 1, averageColour);// + averageColour/2;
    //finalColour = averageColour;
    EmitVertex();
    
    tempPosition = gl_in[2].gl_Position;
    gl_Position = tempPosition;
    finalColour = calculateDiffuseAndSpecular(toCameraVector[2], unitNormal, 2, averageColour);// + averageColour/2;
    //finalColour = averageColour;
    EmitVertex();
    

    EndPrimitive();
}

