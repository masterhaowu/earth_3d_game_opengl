#version 400 core

//in vec2 tc;

in vec4 finalColour;

out vec4 out_Colour;

void main(void){
    
    out_Colour = finalColour;
    
}

