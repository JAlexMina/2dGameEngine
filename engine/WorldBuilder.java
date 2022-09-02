package engine;

import engine.support.Vec2d;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import tic.Plant;
import tic.Tree;

import java.util.ArrayList;

public class WorldBuilder {


    ResourceHandler r;
    ResourceHandler bush;
    ResourceHandler bush2;
    ResourceHandler barel;
    ResourceHandler torch;
    Plant o;
    Plant o2;
    Plant o3;
    Plant o4;
    Plant o5;
    Plant o6;


    public WorldBuilder(ArrayList<GameObject> toDrawObj)
    {
        try
        {
            r = new ResourceHandler("./img/sprites/random.png", 8, 12, 0);
            bush = new ResourceHandler("./img/sprites/Mossy - TileSet.png", 7, 7, 0);
            bush2 = new ResourceHandler("./img/sprites/Mossy - FloatingPlatforms.png", 4, 4, 0);
            barel = new ResourceHandler("./img/sprites/barrel.png", 1, 1, 0);
            torch = new ResourceHandler("./img/sprites/torch.png", 1, 1, 0);
        }
        catch (Exception e)
        {
            java.lang.System.out.println("ERROR");
        }


        o = new Plant(new Vec2d(10, 10), true);
        sprite s1 = new sprite(new Vec2d(2,1), false);

        spriteComponent sc = new spriteComponent(r, o, s1, "SPRITECOMPONENT");

        sc.scale = 0.4;
        o.addComponent(sc);



        /////////////////////////////////////////////////////////


        o2 = new Plant(new Vec2d(50, 10), true);
        sprite s12 = new sprite(new Vec2d(0,0), true);

        spriteComponent sc2 = new spriteComponent(barel, o2, s12, "SPRITECOMPONENT");

        sc2.scale = 0.2;
        o2.addComponent(sc2);



        /////////////////////////////////////////////////////////


        o3 = new Plant(new Vec2d(90, 8), true);
        sprite s13 = new sprite(new Vec2d(0,0), true);

        spriteComponent sc3 = new spriteComponent(torch, o3, s13, "SPRITECOMPONENT");

        sc3.scale = 0.013;
        o3.addComponent(sc3);



        /////////////////////////////////////////////////////////


        o4 = new Plant(new Vec2d(10, 80), false);
        sprite s14 = new sprite(new Vec2d(2,0), true);

        spriteComponent sc4 = new spriteComponent(bush2, o4, s14, "SPRITECOMPONENT");

        sc4.scale = 0.08;
        o4.addComponent(sc4);


        /////////////////////////////////////////////////////////


        o5 = new Plant(new Vec2d(70, 80), false);
        sprite s15 = new sprite(new Vec2d(1,0), true);

        spriteComponent sc5 = new spriteComponent(bush2, o5, s15, "SPRITECOMPONENT");

        sc5.scale = 0.08;
        o5.addComponent(sc5);






//        toDrawObj.add(o);
//        toDrawObj.add(o2);
//        toDrawObj.add(o3);
//        toDrawObj.add(o4);
//        toDrawObj.add(o5);

    }



    public void draw(GraphicsContext g) {
        Paint c = g.getFill();
        g.setFill(new Color(0.4, 0.4, 0.4, 0.5));
        Vec2d p = new Vec2d(0, 0);
        Vec2d i = new Vec2d(200, 200);
        g.fillRect(p.x, p.y, i.x, i.y);
        g.setFill(c);










    }




}



