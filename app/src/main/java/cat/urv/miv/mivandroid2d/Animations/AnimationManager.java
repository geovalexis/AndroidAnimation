package cat.urv.miv.mivandroid2d.Animations;

import android.content.Context;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

import javax.microedition.khronos.opengles.GL10;

import cat.urv.miv.mivandroid2d.UpdatableObject;
import cat.urv.miv.mivandroid2d.Objects.Square;
import cat.urv.miv.mivandroid2d.Objects.Texture;

public class AnimationManager extends UpdatableObject {
    HashMap<String, Animation> animations;
    float image_height = 512;
    float image_width = 256;

    public AnimationManager(GL10 gl, Context context, int resource_id, int raw_id, float speed){
        HashMap<String, ArrayList<Square>> atlas = parseTextureAtlas(gl, context, resource_id, raw_id);
        animations = new HashMap<String, Animation>();
        for (String animation_name : atlas.keySet()) {
            animations.put(animation_name, new Animation(atlas.get(animation_name), speed));
        }
    }

    public AnimationManager(GL10 gl, Context context, int resource_id, int raw_id, float speed, float image_width, float image_height){
        HashMap<String, ArrayList<Square>> atlas = parseTextureAtlas(gl, context, resource_id, raw_id);
        animations = new HashMap<String, Animation>();
        for (String animation_name : atlas.keySet()) {
            animations.put(animation_name, new Animation(atlas.get(animation_name), speed));
        }
        this.image_height = image_height;
        this.image_width = image_width;

    }

    private HashMap<String, ArrayList<Square>> parseTextureAtlas(GL10 gl, Context context, int resource_id, int raw_id){
        HashMap<String, ArrayList<Square>> atlas = new HashMap<>();
        InputStream text_file = context.getResources().openRawResource(raw_id);
        BufferedReader br = new BufferedReader(new InputStreamReader(text_file));
        try {
            String st;
            while ((st = br.readLine()) != null) {
                String[] split_st = st.split("\\s");
                String anim_name = split_st[0];
                int frame_num = Integer.parseInt(split_st[1]); //NOTE: should I check it?
                int x = Integer.parseInt(split_st[2]);
                int y = Integer.parseInt(split_st[3]);
                int width = Integer.parseInt(split_st[4]);
                int height = Integer.parseInt(split_st[5]);
                float[] coords = calculateTextureCoordinates(x, y, width, height);
                ArrayList<Square> frames;
                if (atlas.containsKey(anim_name)) {
                    frames = atlas.get(anim_name);
                }
                else {
                    frames = new ArrayList<Square>();
                }
                Square square = new Square();
                square.setTexture(new Texture(gl, context, resource_id), coords);
                frames.add(square);
                atlas.put(anim_name, frames);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return atlas;
    }



    private float[] calculateTextureCoordinates(int x, int y, int width, int height){
        float xMin = x / image_width;
        float yMin = 1 - (y+height) / image_height;
        float yMax = 1 - y / image_height;
        float xMax = (x+width) / image_width;
        return new float[]{
                xMax, yMax,
                xMax, yMin,
                xMin, yMin,
                xMin, yMax,
        };
    }

    public void setAnimation(String animation_name){
        currentAnimation = animations.get(animation_name);
    }



}
