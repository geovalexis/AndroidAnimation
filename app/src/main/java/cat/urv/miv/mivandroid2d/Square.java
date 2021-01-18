package cat.urv.miv.mivandroid2d;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

public class Square {
	private float vertices[] = {
			-1.0f, -1.0f, 0.0f, // 0, Bottom left
			-1.0f,  1.0f, 0.0f, // 1, Top Left
			1.0f,  1.0f, 0.0f,  // 2, Top Right
			1.0f, -1.0f, 0.0f}; // 3, Bottom Right
	private short faces[] = { 0, 1, 2, 0, 2, 3 };

	// Our vertex buffer.
	private FloatBuffer vertexBuffer;

	// Our index buffer.
	private ShortBuffer indexBuffer;

	private FloatBuffer colorBuffer;
	private boolean colorEnabled;

	private Texture texture;
	private FloatBuffer texcoordBuffer;
	private boolean textureEnabled;

	public Square() {
		//Move the vertices list into a buffer
		ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
		vbb.order(ByteOrder.nativeOrder());
		vertexBuffer = vbb.asFloatBuffer();
		vertexBuffer.put(vertices);
		vertexBuffer.position(0);

		//Move the faces list into a buffer
		ByteBuffer ibb = ByteBuffer.allocateDirect(faces.length * 2);
		ibb.order(ByteOrder.nativeOrder());
		indexBuffer = ibb.asShortBuffer();
		indexBuffer.put(faces);
		indexBuffer.position(0);

	}


	public void setColor(float []colors){
		ByteBuffer vbb = ByteBuffer.allocateDirect(colors.length*4);
		vbb.order(ByteOrder.nativeOrder());
		colorBuffer = vbb.asFloatBuffer();
		colorBuffer.put(colors);
		colorBuffer.position(0);
		//this.colorBuffer = colors;
		this.colorEnabled = true;
	}

	public void enableColor() { this.colorEnabled = true;}
	public void disableColor() { this.colorEnabled = false;}

	public void setTexture(Texture tex, float[] texCoords) {
		ByteBuffer vbb = ByteBuffer.allocateDirect(texCoords.length*4);
		vbb.order(ByteOrder.nativeOrder());
		texcoordBuffer = vbb.asFloatBuffer();
		texcoordBuffer.put(texCoords);
		texcoordBuffer.position(0);
		this.texture = tex;
		textureEnabled = true;
	}

	public void enableTexture() { this.textureEnabled = true;}
	public void disableTexture() { this.textureEnabled = false;}

	public void draw(GL10 gl) {

		// Enabled the vertices buffer for writing and to be used during rendering.
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

		if (colorEnabled) gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
		if (textureEnabled) gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);

		// Specifies the location and data format of an array of vertex
		// coordinates to use when rendering.
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);

		if (textureEnabled) {
			gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, texcoordBuffer);
			gl.glBindTexture(GL10.GL_TEXTURE_2D, texture.getTexture()[0]);
		}

		if (colorEnabled) gl.glColorPointer(4, GL10.GL_FLOAT, 0, colorBuffer);

		gl.glDrawElements(GL10.GL_TRIANGLES, faces.length,
				GL10.GL_UNSIGNED_SHORT, indexBuffer);

		// Disable the vertices buffer.
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);

		if (colorEnabled) gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
		if (textureEnabled) gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);

	}
}
