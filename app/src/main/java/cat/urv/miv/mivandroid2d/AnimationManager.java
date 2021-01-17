package cat.urv.miv.mivandroid2d;

import android.content.Context;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

import javax.microedition.khronos.opengles.GL10;

public class AnimationManager extends AnimationObject {
    HashMap<String, Animation> animations;
    final int default_height = 512;
    final int default_width = 256;

    public AnimationManager(GL10 gl, Context context, int resource_id, int raw_id, int speed){
        HashMap<String, ArrayList<Square>> atlas = parseTextureAtlas(gl, context, resource_id, raw_id);
        animations = new HashMap<String, Animation>();
        for (String animation_name : atlas.keySet()) {
            animations.put(animation_name, new Animation(atlas.get(animation_name), speed));
        }
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
                    frames = new ArrayList<>();
                }
                Square square = new Square();
                square.setTexture(new Texture(gl, context, resource_id), coords);
                frames.add(square);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return atlas;
    }



    private float[] calculateTextureCoordinates(int x, int y, int width, int height){
        float xMin = x / default_width;
        float yMin = y / default_height;
        float xMax = width / default_width;
        float yMax = height / default_height;
        return new float[]{
                xMin,yMin,
                xMax,yMin,
                xMax,yMax,
                xMin,yMin
        };
    }

    public void setAnimation(String animation_name){
        currentAnimation = animations.get(animation_name);
    }

}