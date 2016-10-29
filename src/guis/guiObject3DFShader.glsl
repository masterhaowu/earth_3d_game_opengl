#version 140

in vec4 finalColour;

//uniform bool highlighted;

out vec4 out_Colour;

void main(void){
    
    out_Colour = finalColour;
    //out_Colour = vec4(1, 1, 1, 1);
    
    
}
