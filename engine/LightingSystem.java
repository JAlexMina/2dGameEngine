package engine;

import engine.support.Vec2d;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class LightingSystem implements  System{

    ArrayList<GameObject> m_Lights = new ArrayList<GameObject>();
    ArrayList<GameObject> m_AllGameObject = new ArrayList<GameObject>();
    ArrayList<Vec2d> saved = new ArrayList<Vec2d>();
    public GameObject bg;


    //edges of screen
    Line top;
    Line bottom;
    Line left;
    Line right;
    WritableImage wImage;

    double screenWidth = 3*960;
    double screenHeight = 3*540;

//    double screenWidth = 200;
//    double screenHeight = 200;


    double startx = -2*960;
    double starty = -2*540;

    boolean maskCreated = false;


    public void createMask(GameObject bg)
    {
        // Create Image and ImageView objects
        spriteComponent s = (spriteComponent)(bg.getComponent("SPRITECOMPONENT"));
        Image image = s.r.index(s.currentSprite);
        ImageView imageView = new ImageView();
        imageView.setImage(image);

        // Obtain PixelReader
        PixelReader pixelReader = image.getPixelReader();


        // Create WritableImage
        wImage = new WritableImage(
                (int)screenWidth,
                (int)screenHeight);
        PixelWriter pixelWriter = wImage.getPixelWriter();

        Vec2d centre = new Vec2d((int)(screenWidth/2), (int)(screenHeight/2));
        double max = Math.sqrt((0 - centre.y) * (0 - centre.y) + (0 - centre.x) * (0 - centre.x));
        java.lang.System.out.println(Math.sqrt(max));

        // Determine the color of each pixel in a specified row
        for(int readY=0;readY<screenHeight;readY++){
            for(int readX=0; readX<screenWidth;readX++){

                double d = (Math.sqrt((readY - centre.y) * (readY - centre.y) + (readX - centre.x) * (readX - centre.x)));

                //java.lang.System.out.println(d);

                if(d > 500)
                {
                    d = 1.0;
                    //java.lang.System.out.println("NEVER");
                }
                else
                {
                    Vec2d topLeft = new Vec2d(centre.x-400, centre.y-400);
                    max = Math.sqrt(Math.sqrt((topLeft.y - centre.y) * (topLeft.y - centre.y) + (topLeft.x - centre.x) * (topLeft.x - centre.x)));
                    d = Math.sqrt(d)/max;

                }



                //java.lang.System.out.println(d);

                Color color = new Color(0.0, 0.0, 0.0, d);
                pixelWriter.setColor(readX,readY,color);
            }
        }
    }


    public LightingSystem()
    {
        top = new Line(new Vec2d(startx,starty), new Vec2d(screenWidth,starty), -1, "");
        bottom = new Line(new Vec2d(startx,screenHeight), new Vec2d(screenWidth, screenHeight), -1, "");
        left = new Line(new Vec2d(startx,starty), new Vec2d(startx,screenHeight), -1, "");
        right = new Line(new Vec2d(screenWidth,starty), new Vec2d(screenWidth,screenHeight), -1, "");
    }

    public void update(GraphicsContext g)
    {
        saved = new ArrayList<Vec2d>();
        for(GameObject go : m_AllGameObject)
        {
            drawSprite((spriteComponent)go.getComponent("SPRITECOMPONENT"));

            drawQuads(g);
            saved = new ArrayList<Vec2d>();

        }
    }

    //add objects
    @Override
    public void addGameObject(GameObject c) {
        m_AllGameObject.add(c);
    }

    @Override
    public void removeGameObject(GameObject c) {
        m_AllGameObject.remove(c);
    }

    //add lights
    public void addLight(GameObject c) {
        m_Lights.add(c);
    }

    public void removeLight(GameObject c) {
        m_Lights.remove(c);
    }


    public void drawQuads(GraphicsContext g)
    {
        if(saved.size()%4 == 0)
        {
            for (int i = 0; i < saved.size(); i += 4) {


                double x[] = {saved.get(i).x, saved.get(i + 1).x, saved.get(i + 2).x, saved.get(i + 3).x};
                double y[] = {saved.get(i).y, saved.get(i + 1).y, saved.get(i + 2).y, saved.get(i + 3).y};


                Color c = new Color(0.0, 0.0, 0.0, 1.0);
                g.setFill(c);
                //
                g.fillPolygon(x, y, 4);
            }
        }
        else
        {
            java.lang.System.out.println("ERROR");
        }
    }

    public void draw(GraphicsContext g, ArrayList<GameObject> toDrawObj)
    {

        if(bg != null) {
            if(!maskCreated)
            {
                createMask(bg);
                maskCreated = true;
            }


            spriteComponent s = (spriteComponent)(bg.getComponent("SPRITECOMPONENT"));
            g.drawImage(s.r.index(s.currentSprite), s.owner.getTransformComponent().pos.x, s.owner.getTransformComponent().pos.y, s.wh.x * s.scale, s.wh.y * s.scale);




            update(g);


            for (int i = 0; i < toDrawObj.size(); i++)
            {
                toDrawObj.get(i).draw(g);
            }

            //draw mask
            //m_Lights.get(0);
            g.drawImage(wImage, -(int)(wImage.getWidth()/2) + m_Lights.get(0).getTransformComponent().pos.x, -(int)(wImage.getHeight()/2)+ m_Lights.get(0).getTransformComponent().pos.y);


            //drawQuads(g);

        }

    }


    public void drawSprite(spriteComponent s) {
        if (s.outlineStart != null)
        {
            for (GameObject light : m_Lights)
            {

                Vec2d lightPos = light.getTransformComponent().pos;
                boolean f = false;

                int i = 0;
                int j = 0;

                Vec2d fis = new Vec2d(0, 0);
                Vec2d fis2 = new Vec2d(0, 0);
                boolean ag = false;


                //four times
                for (i = 0; i <= 1; i++)
                {
                    for (j = 1; j >= 0; j--)
                    {
                        //Vec2d p = s.owner.getTransformComponent().pos;
                        Vec2d p = s.outlineStart;
//                    double innerx = 0;//0.1*s.wh.x*s.scale;
//                    double innery = 0;//0.1*s.wh.y*s.scale;
                        //p = new Vec2d(p.x + innerx + j*s.wh.x*s.scale - j*5*innerx, p.y + innery + i*s.wh.y*s.scale - i*5*innery);
                        p = new Vec2d(p.x + j * s.outlineWH.x, p.y + i * s.outlineWH.y);
                        Vec2d dir = new Vec2d(-lightPos.x + p.x, -lightPos.y + p.y);
                        Ray r = new Ray(lightPos, dir);

                        double a = r.intersectRayEdge(top.m_start, top.m_end);

                        if (i == 0 && j == 0 || i == 1 && j == 0)
                        {
                            saved.add(p);
                            f = false;
                        } else
                        {
                            f = true;
                        }

                        if (a == Double.POSITIVE_INFINITY)
                        {
                            double b = r.intersectRayEdge(bottom.m_start, bottom.m_end);
                            if (b == Double.POSITIVE_INFINITY)
                            {
                                double c = r.intersectRayEdge(left.m_start, left.m_end);
                                if (c == Double.POSITIVE_INFINITY)
                                {
                                    double d = r.intersectRayEdge(right.m_start, right.m_end);
                                    if (d == Double.POSITIVE_INFINITY)
                                    {
                                        java.lang.System.out.println("ERROR!!!");
                                    }
                                    else
                                    {
                                        fis = r.getPoint(d);
                                    }
                                }
                                else
                                {
                                    fis = r.getPoint(c);
                                }
                            }
                            else
                            {
                                fis = r.getPoint(b);
                            }
                        }
                        else
                        {
                            fis = r.getPoint(a);
                        }

                        saved.add(fis);

                        if (f)
                        {
                            saved.add(p);
                        }

                    }
                }

                ArrayList<Vec2d> s1 = new ArrayList<Vec2d>();
                ArrayList<Vec2d> s2 = new ArrayList<Vec2d>();

                for (int k = 1; k <= 4; k++)
                {
                    s2.add(saved.get(saved.size() - k));
                }

                for (int k = 5; k <= 8; k++)
                {
                    s1.add(saved.get(saved.size() - k));
                }


                saved.add(s1.get(0));
                saved.add(s2.get(0));
                saved.add(s2.get(1));
                saved.add(s1.get(1));


                saved.add(s1.get(0));
                saved.add(s2.get(0));
                saved.add(s2.get(2));
                saved.add(s1.get(2));

            }
        }
    }





}
