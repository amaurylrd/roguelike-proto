#version 150

in vec3 in_position;

out vec3 out_color;

void 	main(void) {

	gl_Position = vec4(in_position, 1.0);

	//colour = vec3(position.x + 0.5, 0.0, position.y + 0.5);
	out_color = vec3(255.0, 0.0, 0.0);
}