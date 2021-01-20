package cat.urv.miv.mivandroid2d;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.microedition.khronos.opengles.GL10;

public class TileMap {
    int[][] scene_matrix;
    Square[][] scene;
    float tiles_per_row;
    float tiles_per_col;
    int image_height = 512;
    int image_width = 512;

    public TileMap(GL10 gl, Context context, int resource_id, int raw_id){
        this.scene_matrix = parseTileMap(context, raw_id);
        this.scene = generateScene(gl, context, resource_id, raw_id);
    }

    public TileMap(GL10 gl, Context context, int resource_id, int raw_id, int image_width, int image_height){
        this.scene_matrix = parseTileMap(context, raw_id);
        this.scene = generateScene(gl, context, resource_id, raw_id);
        this.image_width = image_width;
        this.image_height = image_height;
    }

    private int[][] parseTileMap(Context context, int raw_id){
        int[][] tilemap = new int[0][];
        InputStream text_file = context.getResources().openRawResource(raw_id);
        BufferedReader br = new BufferedReader(new InputStreamReader(text_file));
        try {
            int matrix_nRows=0, matrix_nCols=0;

            String st;
            st = br.readLine();
            if (st != null) {
                String[] split_st = st.split("\\s");
                this.tiles_per_row =  image_width / Integer.parseInt(split_st[0]);
                this.tiles_per_col = image_height / Integer.parseInt(split_st[1]);
            }

            st = br.readLine();
            if (st != null) {
                String[] split_st = st.split("\\s");
                matrix_nCols = Integer.parseInt(split_st[0]);
                matrix_nRows = Integer.parseInt(split_st[1]);
            }

            tilemap = new int[matrix_nRows][matrix_nCols];
            for (int i=0; i<matrix_nRows; i++){
                st = br.readLine();
                String[] split_st = st.split("\\s");
                for (int j=0; j<matrix_nCols; j++){
                    tilemap[i][j]=Integer.parseInt(split_st[j]);
                }
            }

        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return tilemap;
    }

    private Square[][] generateScene(GL10 gl, Context context, int resource_id, int raw_id){
        Square[][] scene = new Square[this.scene_matrix.length][this.scene_matrix[0].length];
        for (int row=0; row < scene_matrix.length; row++){
            for (int col=0; col < scene_matrix[0].length; col++){
                Square tile = new Square();
                float[] texture_coords = calculateTextureCoordinates(this.scene_matrix[row][col]);
                tile.setTexture(new Texture(gl, context, resource_id), texture_coords);
                scene[row][col] = tile;
            }
        }
        return scene;
    }


    private float[] calculateTextureCoordinates(int tile_num){
        float row = tile_num / tiles_per_col;
        float col = tile_num % tiles_per_row;
        float correction = 1 / (tiles_per_col*tiles_per_row);
        float xMin =  col / tiles_per_col;
        float yMin = row / tiles_per_row;
        float yMax = (row+1) / tiles_per_row;
        float xMax = (col+1) / tiles_per_col;
        return new float[]{
                xMin, yMax,
                xMin, yMin,
                xMax, yMin,
                xMax, yMax
        };
    }

    public void draw(GL10 gl){
        for (int y=0; y < scene.length; y++){
            for (int x=0; x < scene[y].length; x++){
                gl.glPushMatrix(); // IMPORTANT NOTE: DO A PUSH-POP
                gl.glTranslatef(x*2, -y*2, 0.0f); // Each square has a size of 2
                scene[y][x].draw(gl);
                gl.glPopMatrix();
            }
        }
    }
}
