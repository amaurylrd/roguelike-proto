#version 330

in vec3 v_color;

out vec4 out_color;

void    main(void)
{
    //gl_FragColor = vec4(v_color, 1.0);
    out_color = vec4(v_color, 1.0);
}
