#version 400 core

in vec3 position;



uniform mat4 transformationMatrix;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;





void main()
{
    vec4 worldPosition =  vec4(position.x, position.y, position.z, 1.0);
    
   
    
    vec4 positionRelativeToCam = viewMatrix * worldPosition;
    gl_Position = projectionMatrix * positionRelativeToCam;
    
    
}

