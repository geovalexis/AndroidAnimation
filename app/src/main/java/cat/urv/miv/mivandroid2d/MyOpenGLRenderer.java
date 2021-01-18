package cat.urv.miv.mivandroid2d;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;

public class MyOpenGLRenderer implements Renderer {

	private Square background;
	private Context context;
	AnimationManager mario;

	public MyOpenGLRenderer(Context context){
		this.context = context;
	}

	// SETUP
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		// Image Background color
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);

		// Enable functionalities
		gl.glEnable(GL10.GL_TEXTURE_2D);
		gl.glEnable(GL10.GL_BLEND);

		//Create the objects
		background = new Square();
		background.setTexture(new Texture(gl, context, R.drawable.backgrounds_super_mario_bros),
				new float[]{
						0.0f, 1.0f,
						0.0f, 0.0f,
						1.0f, 0.0f,
						1.0f, 1.0f,
				});

		mario = new AnimationManager(gl, context, R.drawable.mario, R.raw.mario, 25f);
	}

	// DRAW
	@Override
	public void onDrawFrame(GL10 gl) {
		
		// Clears the screen and depth buffer.
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		
		gl.glLoadIdentity();	

		gl.glTranslatef(0.0f, 0.0f, -10.0f);

		// Background
		gl.glPushMatrix();
		gl.glTranslatef(0.25f, 0.0f, 0f);
		gl.glScalef(4, 2, 0);
		//gl.glColor4f(0.0f, 1.0f, 0.0f, 0.0f);
		background.draw(gl);
		gl.glPopMatrix();

		gl.glPushMatrix();
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA ); // = color * alpha + background * (1-alpha)
		//gl.glLineWidth(5.0f);
		gl.glScalef(0.5f, 0.5f, 0);
		gl.glTranslatef(0.0f, -2.5f, 0f);
		mario.setAnimation("walk");
		mario.update(System.nanoTime()/10E6f);
		mario.draw(gl);
		gl.glPopMatrix();

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
