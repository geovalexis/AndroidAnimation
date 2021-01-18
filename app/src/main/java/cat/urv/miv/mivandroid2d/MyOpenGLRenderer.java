package cat.urv.miv.mivandroid2d;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;

public class MyOpenGLRenderer implements Renderer {

	private Square square;
	private int angle = 0;
	private Context context;
	private Texture tex;

	public MyOpenGLRenderer(Context context){
		this.context = context;
	}

	// SETUP
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		// Image Background color
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);

		//Create the objects
		square = new Square();
		//White color
		square.setColor(new float[]{
				1.0f, 1.0f, 1.0f, 0.0f,
				1.0f, 1.0f, 1.0f, 0.0f,
				1.0f, 1.0f, 1.0f, 0.0f,
				1.0f, 1.0f, 1.0f, 0.0f
		});

		gl.glEnable(GL10.GL_TEXTURE_2D);
		// MARIO IDLE
		this.tex = new Texture(gl, context, R.drawable.mario);
		float xMin = 225 / 256f;
		float xMax = (225+25) / 256f;
		float yMin = 1 - (36+36) / 512f;
		float yMax = 1f - 36 / 512f;
		square.setTexture(tex, new float[]{
				xMax, yMax,
				xMax, yMin,
				xMin, yMin,
				xMin, yMax,
		});
	}

	// DRAW
	@Override
	public void onDrawFrame(GL10 gl) {
		
		// Clears the screen and depth buffer.
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		
		gl.glLoadIdentity();	

		gl.glTranslatef(0.0f, 0.0f, -10.0f);

		square.draw(gl);

	}

	// RESIZE
	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		// Define the Viewport
		gl.glViewport(0, 0, width, height);
		// Select the projection matrix
		gl.glMatrixMode(GL10.GL_PROJECTION);
		// Reset the projection matrix
		gl.glLoadIdentity();
		// Calculate the aspect ratio of the window
		GLU.gluPerspective(gl, 60.0f, (float) width / (float) height, 0.1f, 100.0f);
		
		// Select the modelview matrix
		gl.glMatrixMode(GL10.GL_MODELVIEW);
	}

}
