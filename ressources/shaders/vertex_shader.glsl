#version 330

in vec2     attribute_position;

out vec3    v_color;

void    main(void)
{
    gl_Position = vec4(attribute_position, 0.0, 1.0);
    
    v_color = vec3(1.0, 0.0, 0.0);
}
