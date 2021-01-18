package cat.urv.miv.mivandroid2d;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

public class Animation extends AnimationObject {
    ArrayList<Square> frames_list;
    int num_frames;
    int frame_actual;
    float speed;
    float last_update;

    public Animation(ArrayList<Square> frames, float speed){
        this.frames_list = frames;
        this.num_frames = frames.size();
        this.frame_actual = 0;
        this.speed = speed;
        this.last_update = -speed;
    }

    @Override
    public void update(float time){
        if (time > (this.last_update + this.speed)) {
            frame_actual+=1;
            this.last_update = time;
        }
    }

    @Override
    public void draw(GL10 gl) {
        frames_list.get(frame_actual).draw(gl);
    }

}
