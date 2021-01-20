package cat.urv.miv.mivandroid2d;

import javax.microedition.khronos.opengles.GL10;

public class UpdatableObject {
    public UpdatableObject currentAnimation;
    public float speed; //Number of cycles to iterate until update the frame
    public float frame_per_second = 30f; // 25 FPS
    public float last_update;

    public void update(float time){
        currentAnimation.update(time);
    }

    public void draw(GL10 gl){
        currentAnimation.draw(gl);
    }
}
