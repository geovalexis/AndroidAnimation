package cat.urv.miv.mivandroid2d;

import javax.microedition.khronos.opengles.GL10;

public class AnimationObject {
    AnimationObject currentAnimation;

    public void update(float time){
        currentAnimation.update(time);
    }

    public void draw(GL10 gl){
        currentAnimation.draw(gl);
    }
}
