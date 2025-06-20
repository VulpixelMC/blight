#version 150 core

in vec3 Position;
in vec3 Normal;

uniform mat4 LocalMat;
uniform mat4 ModelViewMat;
uniform mat4 ProjMat;
uniform vec4 Color;
uniform float Scale;

out vec4 color;
out vec3 frag_pos;
out vec3 normal;

void main() {
	vec3 pos = Position * Scale;
	gl_Position = ProjMat * ModelViewMat * LocalMat * vec4(pos, 1.0);
	color = Color;
	mat4 model_matrix = LocalMat;
	frag_pos = vec3(model_matrix * vec4(pos, 1.0));
	normal = mat3(transpose(inverse(model_matrix))) * Normal;
}
