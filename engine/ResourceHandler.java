package engine;

import com.sun.javafx.geom.Vec4d;
import engine.support.Vec2d;
import javafx.geometry.Rectangle2D;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;



public class ResourceHandler {


    private static final int TILE_WIDTH = 100;
    private static final int TILE_HEIGHT = 100;

    private static final int PANE_WIDTH = 960;
    private static final int PANE_HEIGHT = 540;

    public double scaledWidth, scaledHeight;



    //public Image[][] tiles;
    public Image SpriteSheet;


    int numTileCols = 12;
    int numTileRows = 8;


    double h;
    double w;
    double buff;





    public ResourceHandler(String s, int r, int c, int b) throws FileNotFoundException
    {
//        Image image = new Image(new FileInputStream("./img/bg/grass.png"));
//        build(image);

        SpriteSheet = new Image(new FileInputStream(s));
        //SpriteSheet2 = new Image(new FileInputStream("./img/sprites/output-onlinepngtools.png"));
        numTileCols = c;
        numTileRows = r;

        h = SpriteSheet.getHeight();
        w = SpriteSheet.getWidth();

        // target width and height:
        scaledWidth = w/numTileCols ;
        scaledHeight = h/numTileRows ;
        buff = b;


    }

//    private void build(Image i)
//    {
//
//        // create array of image views:
//
//        int numTileCols = (int) (PANE_WIDTH / TILE_WIDTH + 2);
//        int numTileRows = (int) (PANE_HEIGHT / TILE_HEIGHT + 2);
//
//        tiles = new Image[numTileCols][numTileRows];
//
//        // populate array:
//
//        for (int col = 0; col < numTileCols; col+=1)
//        {
//            for (int row = 0; row < numTileRows; row+=1)
//            {
//                tiles[col][row] = i;
//            }
//        }
//    }

    public Image index(Vec2d rc)
    {
        double r = rc.y;
        double c = rc.x;
        // create array of image views:



        if(r < 0 || c < 0 || r > numTileRows || c > numTileCols)
        {
            return null;
        }

        h = SpriteSheet.getHeight() - buff;
        w = SpriteSheet.getWidth() - buff;

        // define crop in image coordinates:
        Rectangle2D croppedPortion = new Rectangle2D((w/numTileCols)*c + 0.5*buff, (h/numTileRows)*r + 0.5*buff, w/numTileCols, h/numTileRows);

        // target width and height:
        scaledWidth = w/numTileCols ;
        scaledHeight = h/numTileRows ;



        ImageView imageView = new ImageView(SpriteSheet);
        imageView.setViewport(croppedPortion);
        imageView.setFitWidth(scaledWidth);
        imageView.setFitHeight(scaledHeight);
        imageView.setSmooth(true);

        Pane pane = new Pane(imageView);
        //Scene offScreenScene = new Scene(pane);
        Image croppedImage = new WritableImage(SpriteSheet.getPixelReader(), (int)((w/numTileCols)*c), (int)((h/numTileRows)*r), (int)(w/numTileCols), (int)(h/numTileRows));
        return croppedImage;
    }
}


