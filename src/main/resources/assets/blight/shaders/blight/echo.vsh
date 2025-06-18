#version 150 core

in vec3 Position;

uniform mat4 LocalMat;
uniform mat4 ModelViewMat;
uniform mat4 ProjMat;
uniform vec4 Color;
uniform float Scale;

out vec4 vertex_color;

void main() {
	vec3 pos = Position * Scale;
	gl_Position = ProjMat * ModelViewMat * LocalMat * vec4(pos, 1.0);
	vertex_color = Color;
}
