#version 400 core


layout ( triangles ) in;
layout ( triangle_strip, max_vertices = 3) out;

in vec2 textureCoords[3];

out vec2 pass_textureCoords;
//out vec3 pass_colour;
out float pass_brightness;


void main(void) {
    
    
    
    vec4 tempPosition = gl_in[0].gl_Position;
    float tempValue = -tempPosition.y;
    gl_Position = tempPosition;
    pass_textureCoords = textureCoords[0];
    pass_brightness = tempValue;
    EmitVertex();
    
    tempPosition = gl_in[1].gl_Position;
    gl_Position = tempPosition;
    pass_textureCoords = textureCoords[1];
    pass_brightness = tempValue;
    EmitVertex();
    
    tempPosition = gl_in[2].gl_Position;
    gl_Position = tempPosition;
    pass_textureCoords = textureCoords[2];
    pass_brightness = tempValue;
    EmitVertex();
    
    EndPrimitive();
    
    
   

}
