package cat.urv.miv.mivandroid2d;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

public class Animation extends AnimationObject {
    ArrayList<Square> frames_list;
    int num_frames;
    int frame_actual;
    float speed;

    public Animation(ArrayList<Square> frames, int speed){
        this.frames_list = frames;
        this.num_frames = frames.size();
        this.frame_actual = 0;
        this.speed = speed;
    }

    @Override
    public void update(int time){
        //TODO: implementar logica de speed mas tiempo
    }

    @Override
    public void draw(GL10 gl) {
        frames_list.get(frame_actual).draw(gl);
    }

}
