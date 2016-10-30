#version 330

in vec2 pass_textureCoords;

out vec4 out_colour;

uniform vec3 colour;
uniform sampler2D fontAtlas;


const float width = 0.46;
const float edge = 0.24;

const float borderWidth = 0.0;
const float borderEdge = 0.4;

const vec2 offset = vec2(0.006, 0.006);

const vec3 outlineColour = vec3(0.5, 0.5, 0.5);
//const vec4 backgroundColour = vec4(1, 1, 1, 0);

void main(void){
    
    float distance = 1.0 - texture(fontAtlas, pass_textureCoords).a;
    //float originalAlpha = texture(fontAtlas, pass_textureCoords).a / 2;
    float alpha = 1.0 - smoothstep(width, width + edge, distance);
    
    float borderDistance = 1.0 - texture(fontAtlas, pass_textureCoords).a;
    float outlineAlpha = 1.0 - smoothstep(borderWidth, borderWidth + borderEdge, borderDistance);

    float overallAlpha = alpha + (1.0 - alpha) * outlineAlpha;
    vec3 overallColour = mix(outlineColour, colour, alpha / overallAlpha);
    /*
    if (overallAlpha > 0.7) {
        overallAlpha = 0.7;
    }
    */
    //vec4 testingColour = mix(backgroundColour, vec4(colour, 1.0), alpha/overallAlpha);
    //out_colour = testingColour;
    out_colour = vec4(overallColour, alpha);
    //out_colour = vec4(ov)
    //out_colour = texture(fontAtlas, pass_textureCoords);
   // out_colour = texture(fontAtlas, pass_textureCoords);
    //out_colour = vec4(colour, distance);
    //out_colour = vec4(distance, distance, distance, 1);
    //out_colour = vec4(1, pass_textureCoords.x, pass_textureCoords.y, 1);
    
}
