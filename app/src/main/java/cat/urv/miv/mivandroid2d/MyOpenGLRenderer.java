package cat.urv.miv.mivandroid2d;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;

import cat.urv.miv.mivandroid2d.Animations.AnimationManager;
import cat.urv.miv.mivandroid2d.Objects.ScrollingObject.HorizontalScrolling;
import cat.urv.miv.mivandroid2d.Objects.Square;
import cat.urv.miv.mivandroid2d.Objects.Texture;
import cat.urv.miv.mivandroid2d.Objects.TileMap;

public class MyOpenGLRenderer implements Renderer {

	private Context context;
	AnimationManager mario;
	TileMap scene2, scene3, scene4;
	HorizontalScrolling hs2, hs3, hs4;

	public MyOpenGLRenderer(Context context){
		this.context = context;
	}

	// SETUP
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		// Image Background color
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);

		// Enable functionalities
		gl.glEnable(GL10);
		gl.glEnable(GL10.GL_BLEND);
		//gl.glEnable(GL10.GL_MULTISAMPLE);
		//gl.glEnable(GL10.GL_CLAMP_TO_EDGE);


		//Create the objects
		scene2 = new TileMap(gl,context, R.drawable.background_tiles, R.raw.tilemap2);
		hs2 = new HorizontalScrolling(scene2.getScene(), 3000f);
		scene3 = new TileMap(gl,context, R.drawable.background_tiles, R.raw.tilemap3);
		hs3 = new HorizontalScrolling(scene3.getScene(), 2000f);
		scene4 = new TileMap(gl,context, R.drawable.background_tiles, R.raw.tilemap4);
		hs4 = new HorizontalScrolling(scene4.getScene(), 1000f);
		mario = new AnimationManager(gl, context, R.drawable.mario, R.raw.mario, 25f); //Number of cycles to iterate until update the frame
	}

	// DRAW
	@Override
	public void onDrawFrame(GL10 gl) {

		// Clears the screen and depth buffer.
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

		gl.glLoadIdentity();

		gl.glTranslatef(0.0f, -2.5f, -10.0f); //Adjusts

		// SOME CLOUDS
		gl.glPushMatrix();
		gl.glTranslatef(-15.0f, scene2.getSize()*2.0f, -25.0f);
		hs2.update(System.nanoTime()/10E6f);
		hs2.draw(gl);
		gl.glPopMatrix();

		// SOME BACKGROUND MONTAINS
		gl.glPushMatrix();
		float ground_offset = 2.0f;
		gl.glTranslatef(-7.0f, scene3.getSize() * 2.0f+ground_offset, -12.5f);
		gl.glScalef(1.0f, 1.30f, 0);
		hs3.update(System.nanoTime()/10E6f);
		hs3.draw(gl);
		gl.glPopMatrix();

		// SOME MONTAINS
		gl.glPushMatrix();
		gl.glTranslatef(-7.0f, scene4.getSize() * 2.0f, -12.5f);
		gl.glScalef(1.0f, 1.30f, 0);
		hs4.update(System.nanoTime()/10E6f);
		hs4.draw(gl);
		gl.glPopMatrix();

		// MARIO ANIMATION
		gl.glPushMatrix();
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA ); // Transparent background
		gl.glScalef(0.5f, 0.5f, 0);
		gl.glTranslatef(0.0f, 2.8f, 0.0f);
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
