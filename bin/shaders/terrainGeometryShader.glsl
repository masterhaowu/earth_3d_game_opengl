#version 400 core

layout ( triangles ) in;
layout ( triangle_strip, max_vertices = 3 ) out;

//in vec2 tc;
in vec2 pass_textureCoords[3];
in vec3 pass_terrainColour[3];
//in vec3 surfaceNormal[3];
in vec3 toLightVector[3][4];
in vec3 toCameraVector[3];
//in float visibility[3];
//in vec4 shadowCoords[3];
in vec4 worldPosition[3];

out vec4 finalColour;

//uniform sampler2D backgroundTexture;
//uniform sampler2D rTexture;
//uniform sampler2D gTexture;
//uniform sampler2D bTexture;
//uniform sampler2D blendMap;
uniform sampler2D shadowMap;


//uniform sampler2D groundTexture;
//uniform sampler2D grassTexture;
//uniform sampler2D mountainTexture;
//uniform sampler2D snowTexture;
//uniform sampler2D flowerTexture;
//uniform sampler2D savannaTexture;



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


vec3 calculateTriangleNormal2(){
    vec3 tangent = gl_in[1].gl_Position.xyz - gl_in[0].gl_Position.xyz;
    vec3 bitangent = gl_in[2].gl_Position.xyz - gl_in[0].gl_Position.xyz;
    vec3 normal = cross(tangent, bitangent);
    return normalize(normal);
}

vec3 calculateTriangleNormal(){
    vec3 tangent = worldPosition[1].xyz - worldPosition[0].xyz;
    vec3 bitangent = worldPosition[2].xyz - worldPosition[0].xyz;
    vec3 normal = cross(tangent, bitangent);
    return normalize(normal);
}

vec4 calculateSpecular(vec4 toCameraVector, vec3 unitNormal, int number){
    
    vec3 unitCameraVector = normalize(toCameraVector.xyz);
    vec3 totalSpecular = vec3(0.0);
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
        
        //totalDiffuse = totalDiffuse + (brightness * lightColour[i])/attFactor;
        totalSpecular = totalSpecular + (dampedFactor * reflectivity * lightColour[i])/attFactor;
    }
    return vec4(totalSpecular.xyz, 1.0);
    
    //vec3 unitCameraVector = normalize(toCameraVector.xyz);
    //vec3 unitLightVector = normalize(toLightVector[0][0]);
    //vec3 lightDirection = -unitLightVector;
    //vec3 reflectedLightDirection = reflect(lightDirection, unitNormal);
    //float specularFactor = dot(reflectedLightDirection, unitCameraVector);
    //specularFactor = max(specularFactor, 0.0);
    //return vec4(lightColour[0] * specularFactor * 0.5, 1.0);
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
    tempFinalColour = mix(tempFinalColour, averageColour, 0.5);
    return tempFinalColour;
    //return vec4(totalDiffuse, 1.0);
    
}

vec4 calculateAverageTextureColour(){
    vec4 totalColour = vec4(0,0,0,0);
   
    for (int i=0; i<3; i++) {
        //vec4 blendMapColour = texture(blendMap, pass_textureCoords[i]);
        //float backTextureAmount = 1 - (blendMapColour.r + blendMapColour.g + blendMapColour.b);
        //vec2 tiledCoords = pass_textureCoords[i] * 40;
        //vec4 backgroundTextureColour = texture(backgroundTexture, tiledCoords) * backTextureAmount;
        //vec4 rTextureColour = texture(rTexture, tiledCoords) * blendMapColour.r;
        //vec4 gTextureColour = texture(gTexture, tiledCoords) * blendMapColour.g;
        //vec4 bTextureColour = texture(bTexture, tiledCoords) * blendMapColour.b;
        
        //totalColour += backgroundTextureColour + rTextureColour + gTextureColour + bTextureColour;
        totalColour += vec4(pass_terrainColour[i],1.0);
    }
    totalColour /= 3;
    return totalColour;
}

void main()
{
    /*
    
    float texelSize = 1.0 / mapSize;
    float total = 0.0;
    
    for (int x=-pcfCount; x<=pcfCount; x++) {
        for (int y=-pcfCount; y<=pcfCount; y++) {
            float objectNearestLight = texture(shadowMap, shadowCoords.xy + vec2(x,y)*texelSize).r;
            if (shadowCoords.z > objectNearestLight) {
                total += 1.0;
            }
        }
    }
    
    total /= totalTexels;
    
    //float objectNearestLight = texture(shadowMap, shadowCoords.xy).r;
    float lightFactor = 1.0 - (total * shadowCoords.w);
    
    //if (shadowCoords.z > objectNearestLight) {
     //   lightFactor = 1.0 - (0.4 * shadowCoords.w);
    //}
    
    vec4 blendMapColour = texture(blendMap, pass_textureCoords);
    
    
    float backTextureAmount = 1 - (blendMapColour.r + blendMapColour.g + blendMapColour.b);
    vec2 tiledCoords = pass_textureCoords * 40;
    vec4 backgroundTextureColour = texture(backgroundTexture, tiledCoords) * backTextureAmount;
    vec4 rTextureColour = texture(rTexture, tiledCoords) * blendMapColour.r;
    vec4 gTextureColour = texture(gTexture, tiledCoords) * blendMapColour.g;
    vec4 bTextureColour = texture(bTexture, tiledCoords) * blendMapColour.b;
    
    vec4 totalColour = backgroundTextureColour + rTextureColour + gTextureColour + bTextureColour;
    vec3 unitNormal = normalize(surfaceNormal);
    vec3 unitCameraVector = normalize(toCameraVector);
    
    vec3 totalDiffuse = vec3(0.0);
    vec3 totalSpecular = vec3(0.0);
    
    for (int i=0; i<4; i++){
        float distance = length(toLightVector[i]);
        float attFactor = attenuation[i].x + (attenuation[i].y * distance) + (attenuation[i].z * distance * distance);
        vec3 unitLightVector = normalize(toLightVector[i]);
        float nDotl = dot(unitNormal, unitLightVector);
        float brightness = max(nDotl, 0.0);
        
        //ceil shading
        //float level = floor(brightness * levels);
        //brightness = level/levels;
        
        vec3 lightDirection = -unitLightVector;
        vec3 reflectedLightDirection = reflect(lightDirection, unitNormal);
        float specularFactor = dot(reflectedLightDirection, unitCameraVector);
        specularFactor = max(specularFactor, 0.0);
        float dampedFactor = pow(specularFactor, shineDamper);
        
        totalDiffuse = totalDiffuse + (brightness * lightColour[i])/attFactor;
        totalSpecular = totalSpecular + (dampedFactor * reflectivity * lightColour[i])/attFactor;
    }
    
    totalDiffuse = max(totalDiffuse * lightFactor, terrainGlobalOffset);
    
    vec4 tempFinalColour = vec4(totalDiffuse, 1.0) * totalColour + vec4(totalSpecular, 1.0);
    tempFinalColour = mix(vec4(skyColour, 1.0), out_colour, visibility);
    //out_colour = texture(blendMap, tiledCoords);
    //out_colour = vec4(shadowCoords.w, shadowCoords.w, shadowCoords.w, 1);
     
     */
    
    
    
    
    //vec4 tempColour = texture()
    
    vec3 unitNormal = calculateTriangleNormal();
    
    
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

