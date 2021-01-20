package cat.urv.miv.mivandroid2d.Objects.ScrollingObject;

import javax.microedition.khronos.opengles.GL10;

import cat.urv.miv.mivandroid2d.Objects.Square;
import cat.urv.miv.mivandroid2d.UpdatableObject;

public class HorizontalScrolling extends UpdatableObject {
    Square[][] scene;
    int start_x;

    public HorizontalScrolling(Square[][] scene, float speed){
        this.scene = scene;
        this.start_x = 0;
        this.speed = speed;
        this.last_update = 0;
    }


    @Override
    public void update(float time){
        float delta_time = this.last_update + this.speed * 1/this.frame_per_second;
        if (time > delta_time) {
            this.start_x =(this.start_x+1)%scene[0].length;
            this.last_update = time;
        }
    }

    @Override
    public void draw(GL10 gl){
        // DRAW FROM THE LAST UPDATED X
        for (int y=0; y < scene.length; y++){
            for (int x = start_x; x < scene[y].length; x++){
                gl.glPushMatrix(); //
                gl.glTranslatef((x-start_x)*2.0f, -y*2.0f, 0.0f); // Each square has a size of 2
                scene[y][x].draw(gl);
                gl.glPopMatrix();
            }
        }

        // DRAW WHAT IS LEFT FROM THE BEGGINING AT THE END
        for (int y=0; y < scene.length; y++){
            float row_end = scene[y].length-start_x*2;
            for (int x = 0; x < start_x; x++){
                gl.glPushMatrix();
                gl.glTranslatef(row_end+x*2.0f, -y*2.0f, 0.0f); // Each square has a size of 2
                scene[y][x].draw(gl);
                gl.glPopMatrix();
            }
        }
    }

}
