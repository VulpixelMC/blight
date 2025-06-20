#version 150 core

uniform vec3 CameraPos;

in vec4 color;
in vec3 frag_pos;
in vec3 normal;

out vec4 frag_color;

void main() {
	// ambient (or what i mistyped as "affuse")
	float ambient_strength = 0.8;

	// diffuse
	vec3 normalized_normal = normalize(normal);
	vec3 light_dir = normalize(CameraPos - frag_pos);
	// max so that it don't become negative
	float diffuse = max(dot(normalized_normal, light_dir), 0.0);
	vec3 diffuse_color = vec3(diffuse * color.rgb / 2);

	vec3 result_color = (vec3(ambient_strength) + diffuse_color) * color.rgb;

	frag_color = vec4(result_color, color.a);
}
