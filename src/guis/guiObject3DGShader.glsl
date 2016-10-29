#version 400 core


layout ( triangles ) in;
layout ( triangle_strip, max_vertices = 3) out;

in vec2 pass_textureCoords[3];
in vec4 worldPosition[3];
in vec3 toCameraVector[3];

out vec4 finalColour;

uniform sampler2D textureSampler;
uniform float shineDamper;
uniform float reflectivity;

//uniform vec2 randomTexOffset;
const vec3 toLight = vec3(0, 0, 1.0);

vec3 calculateTriangleNormal(){
    vec3 tangent = worldPosition[1].xyz - worldPosition[0].xyz;
    vec3 bitangent = worldPosition[2].xyz - worldPosition[0].xyz;
    vec3 normal = cross(tangent, bitangent);
    return normalize(normal);
}

vec4 calculateAverageTextureColour(){
    vec4 totalColour = vec4(0,0,0,0);
    
    for (int i=0; i<3; i++) {
        
        totalColour += texture(textureSampler, pass_textureCoords[i]);
    }
    totalColour /= 3;
    return totalColour;
}

vec4 calculateDiffuseAndSpecular(vec3 toCameraVector, vec3 unitNormal, vec4 averageColour){
    
    vec3 unitCameraVector = normalize(toCameraVector.xyz);
    vec3 totalSpecular = vec3(0.0);
    vec3 totalDiffuse = vec3(0.0);
    for (int i=0; i<4; i++){
        vec3 unitLightVector = normalize(toLight);
        float nDotl = dot(unitNormal, unitLightVector);
        float brightness = max(nDotl, 0.0);
        
        
        vec3 lightDirection = -unitLightVector;
        vec3 reflectedLightDirection = reflect(lightDirection, unitNormal);
        float specularFactor = dot(reflectedLightDirection, unitCameraVector);
        specularFactor = max(specularFactor, 0.0);
        float dampedFactor = pow(specularFactor, shineDamper);
        
        totalDiffuse = totalDiffuse + (brightness * vec3(1,1,1));
        totalSpecular = totalSpecular + (dampedFactor * reflectivity * vec3(1,1,1));
    }
    //totalDiffuse = max(totalDiffuse, terrainGlobalOffset);
    
    vec4 tempFinalColour = vec4(totalDiffuse, 1.0) * averageColour + vec4(totalSpecular, 1.0);
    tempFinalColour = mix(tempFinalColour, averageColour, 0.8);
    return tempFinalColour;
    
}



void main(void) {
    
    vec3 unitNormal = calculateTriangleNormal();
    //vec3 unitNormal = normalize()
    
    
    vec4 averageColour = calculateAverageTextureColour();
    
    
    vec4 tempPosition = gl_in[0].gl_Position;
    gl_Position = tempPosition;
    finalColour = calculateDiffuseAndSpecular(toCameraVector[0], unitNormal, averageColour);// + averageColour/2;
    //finalColour = averageColour;
    EmitVertex();
    
    tempPosition = gl_in[1].gl_Position;
    gl_Position = tempPosition;
    finalColour = calculateDiffuseAndSpecular(toCameraVector[1], unitNormal, averageColour);// + averageColour/2;
    //finalColour = averageColour;
    EmitVertex();
    
    tempPosition = gl_in[2].gl_Position;
    gl_Position = tempPosition;
    finalColour = calculateDiffuseAndSpecular(toCameraVector[2], unitNormal, averageColour);// + averageColour/2;
    //finalColour = averageColour;
    EmitVertex();
    
    
    EndPrimitive();
    
    
    
}
