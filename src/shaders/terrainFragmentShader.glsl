#version 400 core

//in vec2 tc;
in vec4 finalColour;

out vec4 out_Colour;

void main(void){
    
    out_Colour = finalColour;
    
}

/*
in vec2 pass_textureCoords;
in vec3 surfaceNormal;
in vec3 toLightVector[4];
in vec3 toCameraVector;
in float visibility;
in vec4 shadowCoords;

out vec4 out_colour;

uniform sampler2D backgroundTexture;
uniform sampler2D rTexture;
uniform sampler2D gTexture;
uniform sampler2D bTexture;
uniform sampler2D blendMap;
uniform sampler2D shadowMap;



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

void main()
{
    
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
    
    out_colour = vec4(totalDiffuse, 1.0) * totalColour + vec4(totalSpecular, 1.0);
    out_colour = mix(vec4(skyColour, 1.0), out_colour, visibility);
    //out_colour = texture(blendMap, tiledCoords);
    //out_colour = vec4(shadowCoords.w, shadowCoords.w, shadowCoords.w, 1);

}
*/
