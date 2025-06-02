package gay.sylv.blight.client.api.render.model;

import org.joml.Vector3f;

/**
 * A class that generates an <a href="https://en.wikipedia.org/wiki/Icosphere"><b>icosphere</b></a>.
 */
public final class Icosphere {
	private final float[] vertices;
	private final int[] indices;

	/**
	 * Create an icosphere.
	 * @param depth How detailed the icosphere is.
	 */
	public Icosphere(int depth) {
		// https://blog.lslabs.dev/posts/generating_icosphere_with_code
		// Create icosahedron
		float a = 0.525731112119134f;
		float b = 0.000000101405476f;
		float c = 0.85065080835157f;
		float d = 0.00000006267203f;
		float[] icoVertices = {
				c, a, 0.0f, // 0
				b, c, -a, // 1
				b, c, a, // 2
				a, -d, -c, // 3
				a, -d, c, // 4
				c, -a, 0.0f, // 5
				-a, d, -c, // 6
				-c, a, 0.0f, // 7
				-a, d, c, // 8
				-b, -c, -a, // 9
				-b, -c, a, // 10
				-c, -a, 0.0f, // 11
		};
		int[] icoIndices = {
				0, 1, 2,
				0, 3, 1,
				0, 2, 4,
				3, 0, 5,
				0, 4, 5,
				1, 3, 6,
				1, 7, 2,
				7, 1, 6,
				4, 2, 8,
				7, 8, 2,
				9, 3, 5,
				6, 3, 9,
				5, 4, 10,
				4, 8, 10,
				9, 5, 10,
				7, 6, 11,
				7, 11, 8,
				11, 6, 9,
				8, 11, 10,
				10, 11, 9,
		};

		// Fragment
		int vertexStride = 3;
		float[] sphereVertices = new float[icoVertices.length * depth * vertexStride];
		int[] sphereIndices = new int[icoIndices.length * depth * vertexStride];

		System.arraycopy(icoVertices, 0, sphereVertices, 0, icoVertices.length);
		System.arraycopy(icoIndices, 0, sphereIndices, 0, icoIndices.length);

		for (int depthIndex = 1; depthIndex < depth; depthIndex++) {
			int lastIndex = icoVertices.length * depthIndex * vertexStride;
			int lastIndexIdx = icoIndices.length * depthIndex * vertexStride;
			float[] newSphereVertices = new float[icoVertices.length * (depthIndex + 1) * vertexStride];
			int[] newSphereIndices = new int[icoIndices.length * (depthIndex + 1) * vertexStride];
			for (int i = 0; i < sphereIndices.length; i += vertexStride * 6) {
				int index0 = sphereIndices[i];
				int index1 = sphereIndices[i + 1];
				int index2 = sphereIndices[i + 2];
				int index3 = sphereIndices[i + 3];
				int index4 = sphereIndices[i + 4];
				int index5 = sphereIndices[i + 5];
				int index6 = sphereIndices[i + 6];
				int index7 = sphereIndices[i + 7];
				int index8 = sphereIndices[i + 8];
				int index9 = lastIndex;
				int index10 = lastIndex + 1;
				int index11 = lastIndex + 2;
				int index12 = lastIndex + 3;
				int index13 = lastIndex + 4;
				int index14 = lastIndex + 5;
				int index15 = lastIndex + 6;
				int index16 = lastIndex + 7;
				int index17 = lastIndex + 8;

				var v1 = new Vector3f(sphereVertices[index0], sphereVertices[index1], sphereVertices[index2]);
				var v2 = new Vector3f(sphereVertices[index3], sphereVertices[index4], sphereVertices[index5]);
				var v3 = new Vector3f(sphereVertices[index6], sphereVertices[index7], sphereVertices[index8]);

				var v4 = ModelUtil.slerp(v1, v2, 0.5f);
				var v5 = ModelUtil.slerp(v2, v3, 0.5f);
				var v6 = ModelUtil.slerp(v3, v1, 0.5f);

				newSphereVertices[index0] = v1.x;
				newSphereVertices[index1] = v1.y;
				newSphereVertices[index2] = v1.z;
				newSphereVertices[index3] = v2.x;
				newSphereVertices[index4] = v2.y;
				newSphereVertices[index5] = v2.z;
				newSphereVertices[index6] = v3.x;
				newSphereVertices[index7] = v3.y;
				newSphereVertices[index8] = v3.z;
				newSphereVertices[index9] = v4.x;
				newSphereVertices[index10] = v4.y;
				newSphereVertices[index11] = v4.z;
				newSphereVertices[index12] = v5.x;
				newSphereVertices[index13] = v5.y;
				newSphereVertices[index14] = v5.z;
				newSphereVertices[index15] = v6.x;
				newSphereVertices[index16] = v6.y;
				newSphereVertices[index17] = v6.z;

				newSphereIndices[lastIndexIdx] = index0;
				newSphereIndices[lastIndexIdx + 1] = index9;
				newSphereIndices[lastIndexIdx + 2] = index15;

				newSphereIndices[lastIndexIdx + 3] = index9;
				newSphereIndices[lastIndexIdx + 4] = index12;
				newSphereIndices[lastIndexIdx + 5] = index15;

				newSphereIndices[lastIndexIdx + 6] = index12;
				newSphereIndices[lastIndexIdx + 7] = index6;
				newSphereIndices[lastIndexIdx + 8] = index15;

				newSphereIndices[lastIndexIdx + 9] = index9;
				newSphereIndices[lastIndexIdx + 10] = index3;
				newSphereIndices[lastIndexIdx + 11] = index12;

				lastIndex += vertexStride * 6;
				lastIndexIdx += vertexStride * 6;
			}

			System.arraycopy(newSphereVertices, 0, sphereVertices, 0, newSphereVertices.length);
			System.arraycopy(newSphereIndices, 0, sphereIndices, 0, newSphereIndices.length);
		}

		// Calculate normals
//		float[] normals = ModelUtil.calculateNormals(icoVertices, icoIndices);
//		float[] vertices = ModelUtil.combineVerticesAndNormals(icoVertices, normals);
//		int[] indices = ModelUtil.createIndicesForNormals(icoIndices, icoVertices.length, normals.length);
//		this.vertices = vertices;
//		this.indices = indices;
		if (depth > 1) {
			this.vertices = sphereVertices;
			this.indices = sphereIndices;
		} else {
			this.vertices = icoVertices;
			this.indices = icoIndices;
		}
	}

	/**
	 * @return This model's vertex array.
	 */
	public float[] getVertices() {
		return vertices;
	}

	/**
	 * @return This model's index array.
	 */
	public int[] getIndices() {
		return indices;
	}
}
