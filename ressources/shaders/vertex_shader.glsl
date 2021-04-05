#version 150

in vec2 attribute_position;

out vec3 out_color;

void 	main(void)
{
	gl_Position = vec4(attribute_position, 0.0, 1.0);
	
	out_color = vec3(1.0, 0.0, 0.0);
}