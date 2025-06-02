package gay.sylv.blight.client.api.render.model;

import org.joml.Vector3f;

/**
 * A grab-bag of utilities for handling models.
 */
public class ModelUtil {
	/**
	 * Calculates the normals for an array of triangle vertices.
	 * @param vertices An array of three vertices per point.
	 * @param indices An array of three integers per triangle.
	 * @return The normals for each face.
	 */
	public static float[] calculateNormals(float[] vertices, int[] indices) {
		if (vertices.length % 3 != 0 || indices.length % 3 != 0) {
			throw new IllegalArgumentException("vertices.length and indices.length must be a multiple of 3");
		}

		float[] normals = new float[indices.length];

		for (int i = 0; i < indices.length; i += 3) {
			// https://www.khronos.org/opengl/wiki/Calculating_a_Surface_Normal
			int index1 = indices[i];
			int index2 = indices[i + 1];
			int index3 = indices[i + 2];
			Vector3f p1 = new Vector3f(vertices[index1], vertices[index1 + 1], vertices[index1 + 2]);
			Vector3f p2 = new Vector3f(vertices[index2], vertices[index2 + 1], vertices[index2 + 2]);
			Vector3f p3 = new Vector3f(vertices[index3], vertices[index3 + 1], vertices[index3 + 2]);

			Vector3f u = p2.add(p1.negate());
			Vector3f v = p3.add(p1.negate());

			float normalX = u.y * v.z + u.z * v.y;
			float normalY = u.z * v.x + u.x * v.z;
			float normalZ = u.x * v.y + u.y * v.x;

			normals[i] = normalX;
			normals[i + 1] = normalY;
			normals[i + 2] = normalZ;
		}

		return normals;
	}

	/**
	 * Combine the array of vertices and normals.
	 * @param vertices Array of three floats per vertex.
	 * @param normals Array of normals for each face.
	 * @return An array of vertices with normals added to the end.
	 */
	public static float[] combineVerticesAndNormals(float[] vertices, float[] normals) {
		float[] result = new float[vertices.length + normals.length];

		System.arraycopy(vertices, 0, result, 0, vertices.length);
		System.arraycopy(normals, 0, result, vertices.length, normals.length);

		return result;
	}

	/**
	 * Add indices for the normals.
	 * @param indices The original array of indices.
	 * @param startPos The position in the vertex array at which the normals begin.
	 * @param normalLength The length of the normals.
	 * @return The indices with extra indices for the normals in the vertex array.
	 * @see #combineVerticesAndNormals(float[], float[])
	 */
	public static int[] createIndicesForNormals(int[] indices, int startPos, int normalLength) {
		int[] result = new int[indices.length + normalLength];

		int vertexStride = 3; // vertex (3)
		int normalStride = 3; // normal (3)
		int stride = vertexStride + normalStride; // vertex (3) + normal (3)
		int j = 0; // vertex indices index
		int k = startPos; // normal indices index
		for (int i = 0; i < result.length; i += stride) {
			// Fill vertex indices
			result[i] = indices[j];
			result[i + 1] = indices[j + 1];
			result[i + 2] = indices[j + 2];
			j += vertexStride;

			// Fill normal indices
			result[i + 3] = k;
			result[i + 4] = k + 1;
			result[i + 5] = k + 2;
			k += normalStride;
		}

		return result;
	}

	/**
	 * Spherical linear interpolation between two vectors.
	 * @param p0 First vector.
	 * @param p1 Second vector.
	 * @param t The interpolation parameter. Must be in {@code [0, 1]} range.
	 * @return The interpolated vector.
	 */
	public static Vector3f slerp(Vector3f p0, Vector3f p1, float t) {
		// https://en.wikipedia.org/wiki/Slerp#Geometric_slerp
		double omega = Math.acos(p0.dot(p1)); // arccos(cos Ω) = arccos(p_0 ⋅ p_1)
		double sinOmega = Math.sin(omega);
		return p0.mul((float) (Math.sin((1 - t) * omega) / sinOmega))
				.add(p1.mul((float) (Math.sin(t * omega) / sinOmega)));
	}
}
