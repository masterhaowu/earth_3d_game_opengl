#version 140


in vec2 textureCoords1;
in vec2 textureCoords2;
in float blend;
in float pass_numberOfRows;

out vec4 out_colour;

uniform sampler2D particleTexture;
uniform bool useColourFilter;
uniform vec3 filterColour;


const float singleTexThres1 = 0.3;
const float singleTexThres2 = 0.7;

void main(void){
    
    vec4 mixedColour = vec4(0, 0, 0, 0);
    
    if (pass_numberOfRows < 1.5) {
        vec4 colour1 = texture(particleTexture, textureCoords1);
        mixedColour = colour1;
        //mixedColour.w = blend;
        
        if (blend < singleTexThres1) {
            mixedColour.w = mix(0.0, colour1.w, (blend/singleTexThres1));
         
        }
        
        else if(blend > singleTexThres2){
            mixedColour.w = mix(colour1.w, 0.0, ((blend - singleTexThres2)/(1 - singleTexThres2)));
        }
        
    }
    else{
        vec4 colour1 = texture(particleTexture, textureCoords1);
        vec4 colour2 = texture(particleTexture, textureCoords2);
        mixedColour = mix(colour1, colour2, blend);
    }
    
    
    if (useColourFilter) {
        mixedColour.x = filterColour.x;
        mixedColour.y = filterColour.y;
        mixedColour.z = filterColour.z;
    }
    
    
    
    out_colour = mixedColour;
    //out_colour = mix(colour1, vec4(1,0,0,1), blend);
    //out_colour = texture(particleTexture, vec2(0.8,0.4));
    //out_colour = vec4(textureCoords2.x,0,0,1);
}
