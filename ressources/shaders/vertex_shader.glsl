#version 150

in vec2 in_position;

out vec3 out_color;

void 	main(void)
{
	gl_Position = vec4(in_position, 0.0, 1.0);

	out_color = vec3(0.1725, 0.0157, 0.0157);
}