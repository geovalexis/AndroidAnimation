package cat.urv.miv.mivandroid2d.Animations;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import cat.urv.miv.mivandroid2d.UpdatableObject;
import cat.urv.miv.mivandroid2d.Objects.Square;

public class Animation extends UpdatableObject {
    ArrayList<Square> frames_list;
    int num_frames;
    int frame_actual;
    float speed; //Number of cycles to iterate until update the frame
    final float frame_per_second = 25f; // 25 FPS
    float last_update;

    public Animation(ArrayList<Square> frames, float speed){
        this.frames_list = frames;
        this.num_frames = frames.size();
        this.frame_actual = -1;
        this.speed = speed;
        this.last_update = 0;
    }

    @Override
    public void update(float time){
        float delta_time = this.last_update + this.speed * 1/this.frame_per_second;
        if (time > delta_time) {
            this.frame_actual=(this.frame_actual+1)%num_frames;
            this.last_update = time;
        }
    }

    @Override
    public void draw(GL10 gl) {
        frames_list.get(frame_actual).draw(gl);
    }

}
