#version 150 core

layout (location = 0) in vec3 position;
//in vec3 normal;

uniform mat4 local_matrix;
uniform mat4 model_view_matrix;
uniform mat4 projection_matrix;
uniform vec4 color;
uniform float scale;

out vec4 vertex_color;

void main() {
	vec3 pos = position * scale;
	gl_Position = projection_matrix * model_view_matrix * local_matrix * vec4(pos, 1.0);
	vertex_color = color;
}
