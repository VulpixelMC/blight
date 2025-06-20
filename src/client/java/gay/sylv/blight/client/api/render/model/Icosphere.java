package gay.sylv.blight.client.api.render.model;

import gay.sylv.blight.client.api.render.pipeline.BlightPipelines;

/**
 * A class that generates an <a href="https://en.wikipedia.org/wiki/Icosphere"><b>icosphere</b></a>.
 */
public final class Icosphere extends Model {
	private final float[] vertices;
	private final int[] indices;

	/**
	 * Create an icosphere.
	 * @param depth How detailed the icosphere is.
	 */
	public Icosphere(int depth) {
		// https://blog.lslabs.dev/posts/generating_icosphere_with_code
		// Create icosahedron
		final float a = 0.525731112119134f;
		final float b = 0.000000101405476f;
		final float c = 0.85065080835157f;
		final float d = 0.00000006267203f;
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
//		int vertexStride = 3;
//		float[] sphereVertices = new float[icoVertices.length + depth * icoIndices.length];
//		int[] sphereIndices = new int[depth * icoIndices.length];
//
//		System.arraycopy(icoVertices, 0, sphereVertices, 0, icoVertices.length);
//		System.arraycopy(icoIndices, 0, sphereIndices, 0, icoIndices.length);

		// Subdivide
		// Inspiration from https://github.com/TerraxGames/unnominable/blob/e23ab68f893bf81732ef29f753374b079f26f81d/src/world/planet.cpp#L18
//		for (int i = 0; i < depth * icoIndices.length; i += vertexStride) {
//			int lastVertex = icoVertices.length + i;
//			int lastIndex = icoIndices.length + (i / vertexStride);
//			float x = sphereVertices[i] + sphereVertices[i + 4];
//			float y = sphereVertices[i + 1] + sphereVertices[i + 5];
//			float z = sphereVertices[i + 2] + sphereVertices[i + 6];
//			sphereVertices[lastVertex] = x;
//			sphereVertices[lastVertex + 1] = y;
//			sphereVertices[lastVertex + 2] = z;
//			sphereIndices[lastIndex] = lastVertex;
//		}

		// Normalize
//		for (int i = 0; i < sphereVertices.length; i += 3) {
//			float magnitude = (float) Math.sqrt(sphereVertices[i] * sphereVertices[i] + sphereVertices[i + 1] * sphereVertices[i + 1] + sphereVertices[i + 2] * sphereVertices[i + 2]);
//			sphereVertices[i] /= magnitude;
//			sphereVertices[i + 1] /= magnitude;
//			sphereVertices[i + 2] /= magnitude;
//		}

		// Calculate normals
		float[] normals = ModelUtil.calculateNormals(icoVertices, icoIndices);
		float[] vertices = ModelUtil.combineVerticesAndNormals(icoVertices, normals);
		int[] indices = ModelUtil.createIndicesForNormals(icoIndices, icoVertices.length, normals.length);
//		this.vertices = vertices;
//		this.indices = indices;
//		if (depth > 1) {
//			this.vertices = sphereVertices;
//			this.indices = sphereIndices;
//		} else {
			this.vertices = vertices;
			this.indices = indices;
			this.setVertexBuffer(this.vertices, BlightPipelines.ECHO_PASS_1);
			this.setIndexBuffer(this.indices);
//		}
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
