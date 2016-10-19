#version 400 core


layout ( triangles ) in;
layout ( triangle_strip, max_vertices = 3) out;

in vec2 textureCoords[3];
in vec4 worldPosition[3];

out vec2 pass_textureCoords;
//out vec3 pass_colour;
out float pass_brightness;
out float pass_average;
//out float pass_averageY;
//out float scaleOffset; // this is to create water dome effect
//out float pass_z;


//uniform vec2 randomTexOffset;
const vec3 toLight = vec3(0, 0, 1.0);

vec3 calculateTriangleNormal(){
    vec3 tangent = worldPosition[1].xyz - worldPosition[0].xyz;
    vec3 bitangent = worldPosition[2].xyz - worldPosition[0].xyz;
    vec3 normal = cross(tangent, bitangent);
    return normalize(normal);
}



void main(void) {
    
    vec3 unitNormal = calculateTriangleNormal();
    vec3 unitLight = normalize(toLight);
    float nDotl = dot(unitNormal, unitLight);
    //pass_brightness = max(nDotl, 0.0);
    //float averageZ = (worldPosition[0].z + worldPosition[1].z + worldPosition[2].z)/3;
    //averageZ = averageZ/2 + 0.5;
    //averageZ = unitNormal.z;
    float averageCos = nDotl;
    
    vec4 tempPosition = gl_in[0].gl_Position;
    float temoValueX = -tempPosition.x/10;
    float tempValueY = -tempPosition.y/10;
    float tempAverageX = (textureCoords[0].x + textureCoords[1].x + textureCoords[2].x)/3;
    float tempAverageY = 1 - (textureCoords[0].y + textureCoords[1].y + textureCoords[2].y)/3;
    
    gl_Position = tempPosition;
    //pass_textureCoords = vec2(textureCoords[0].x + temoValueX, textureCoords[0].y + tempValueY);
    pass_textureCoords = textureCoords[0];
    //pass_brightness = tempValue;
    pass_brightness = averageCos;
    pass_average = tempAverageY;
    //pass_z = averageZ;
    EmitVertex();
    
    tempPosition = gl_in[1].gl_Position;
    gl_Position = tempPosition;
    //pass_textureCoords = vec2(textureCoords[1].x + temoValueX, textureCoords[1].y + tempValueY);
    //pass_brightness = tempValue;
    pass_textureCoords = textureCoords[1];
    pass_brightness = averageCos;
    pass_average = tempAverageY;
    //pass_z = averageZ;
    EmitVertex();
    
    tempPosition = gl_in[2].gl_Position;
    gl_Position = tempPosition;
    //pass_textureCoords = vec2(textureCoords[2].x + temoValueX, textureCoords[2].y + tempValueY);
    //pass_brightness = tempValue;
    pass_textureCoords = textureCoords[2];
    pass_brightness = averageCos;
    pass_average = tempAverageY;
    //pass_z = averageZ;
    EmitVertex();
    
    EndPrimitive();
    
    
   

}
