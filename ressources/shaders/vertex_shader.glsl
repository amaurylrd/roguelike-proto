#version 330

layout(location = 0) in vec2 attribute_position;

out vec3 v_color;

void    main(void)
{
    vec4 v_position = vec4(attribute_position, 0.0, 1.0);
    gl_Position = v_position;
    
    v_color = vec3(1.0, 0.0, 0.0);
}
