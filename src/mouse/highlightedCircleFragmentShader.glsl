#version 400 core





uniform vec3 circleColour;

out vec4 out_Colour;

void main(void){
    
    out_Colour = vec4(circleColour.x, circleColour.y, circleColour.z, 1.0);
    //out_Colour = vec4(1, 1, 1, 1);
    
}




/*
 
 //in vec2 tc;
in vec2 pass_textureCoords;
in vec3 surfaceNormal;
in vec3 toLightVector[4];
in vec3 toCameraVector;
in float visibility;

out vec4 out_colour;


uniform vec3 lightColour[4];
uniform vec3 attenuation[4];
uniform float shineDamper;
uniform float reflectivity;
uniform vec3 skyColour;
uniform bool highlighted;

const float levels = 3.0;

void main()
{
    vec3 unitNormal = normalize(surfaceNormal);
    vec3 unitCameraVector = normalize(toCameraVector);
    
    vec3 totalDiffuse = vec3(0.0);
    vec3 totalSpecular = vec3(0.0);
    
    for (int i=0; i<4; i++) {
        float distance = length(toLightVector[i]);
        float attFactor = attenuation[i].x + (attenuation[i].y * distance) + (attenuation[i].z * distance * distance);
        vec3 unitLightVector = normalize(toLightVector[i]);
        float nDotl = dot(unitNormal, unitLightVector);
        float brightness = max(nDotl, 0.0);
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
    
    totalDiffuse = max(totalDiffuse, 0.2);
    
    
    vec4 textureColour = texture(textureSampler, pass_textureCoords);
    
    if (textureColour.a < 0.5) {
        discard;
    }
    
    out_colour = vec4(totalDiffuse, 1.0) * textureColour + vec4(totalSpecular, 1.0);
    out_colour = mix(vec4(skyColour, 1.0), out_colour, visibility);
    
    if (highlighted == true) {
        out_colour = vec4(1,1,1,1);
    }

}
 */

