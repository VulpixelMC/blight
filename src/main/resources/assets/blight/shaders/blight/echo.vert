#version 150 core

in vec3 position;
//in vec3 normal;

uniform mat4 frustum_matrix;
uniform mat4 model_view_matrix;
uniform mat4 projection_matrix;

out vec4 color;

void main() {
	gl_Position = projection_matrix * model_view_matrix * frustum_matrix * vec4(position, 1.0);
	color = vec4(0.5, 0.75, 1.0, 1.0);
}
