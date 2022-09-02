package tic;

import engine.*;
import engine.support.Vec2d;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;


import java.io.File;
import java.util.ArrayList;




import javax.sound.sampled.*;
import java.io.FileNotFoundException;
import java.lang.System;

public class Alc extends Screen
{

    ViewPort vp = new ViewPort();


    Color c;
    ResourceHandler r;
    ResourceHandler b;
    ResourceHandler bul;
    collisionComponent c1;
    ResourceHandler bush;
    ResourceHandler bush2;
    ResourceHandler barel;
    ResourceHandler torch;
    ResourceHandler thorn;
    ResourceHandler thorn2;
    PhysicsSystem m_physicsSystem;
    button restart = new button(new Vec2d(750,50), new Vec2d(78, 25),"Restart", "BUTTON", Color.WHITE, "STROKE");


    Plant p1;
    Tree biril;
    spriteComponent sc;
    int seed;
    boolean landed = false;
    double lastVel = -1;
    boolean previousLanded = true;
    boolean stopJump = true;



    ArrayList<Tree> trees = new ArrayList<Tree>();
    ArrayList<Thread> t = new ArrayList<Thread>();


    collisionComponent c2;
    spriteComponent sc2;



    int m_up = 0;
    int m_shoot = 0;
    int m_left = 0;
    int m_right = 0;
    int m_down = 0;
    int m_shift = 0;
    Boolean m_leftRightRelease = false;
    Boolean m_upDownRelease = false;
    String lastPressed = "";

    public String soundTrack = "./sound/st.wav";
    public String walkingSound = "./sound/footstep.wav";
    public int walking = -1;
    public int st = -1;

    public boolean die = false;










    Vec2d zoom = new Vec2d(0.5, 0.5);





    public Alc(Vec2d currentStageSize, Vec2d position) throws FileNotFoundException {
        restart.t.setColor(Color.WHITE);

        p1 = new Plant(position, true);







        pos = position;
        m_currentStageSize = currentStageSize;
        c = Color.GRAY;
        c = c.darker();

        m_collisionSystem = new CollisionSystem();
        m_physicsSystem = new PhysicsSystem();
        m_lightSystem = new LightingSystem();




        r = new ResourceHandler("./img/sprites/random.png", 8, 12, 0);
        b = new ResourceHandler("./img/sprites/Grass_Sample.png", 5, 11, 0);
        bush = new ResourceHandler("./img/sprites/Mossy - TileSet.png", 7, 7, 0);
        bush2 = new ResourceHandler("./img/sprites/Mossy - FloatingPlatforms.png", 4, 4, 0);
        bul = new ResourceHandler("./img/bg/bg2.jpg", 1, 1, 0);
        barel = new ResourceHandler("./img/sprites/barrel.png", 1, 1, 0);
        torch = new ResourceHandler("./img/sprites/torch.png", 1, 1, 0);
        thorn = new ResourceHandler("./img/sprites/Mossy - Decorations&Hazards.png", 8, 8,0);
        thorn2 = new ResourceHandler("./img/sprites/Mossy - Decorations&Hazards(2).png", 1, 1,0);
        sprite s1 = new sprite(new Vec2d(2,1), false);

        //



        sc = new spriteComponent(r, p1, s1, "SPRITECOMPONENT");


        sc.scale = 0.7;
        p1.addComponent(sc);
        c1 = new collisionComponent(p1, new Vec2d(sc.wh.x*sc.scale, sc.wh.y*sc.scale), shapeType.rectangle, new Vec2d(0,0), "COLLISIONCOMPONENT");
        p1.addComponent(c1);
        m_collisionSystem.addGameObject(p1);
        m_physicsSystem.addGameObject(p1);











        m_currentStageSize = currentStageSize;

        onResize(currentStageSize);

    }


    private int playSound(File clipFile, float vol) {
               Thread thrade = new Thread(new Runnable() {
            public void run() {
        try {
        class AudioListener implements LineListener {
            private boolean done = false;
            @Override public synchronized void update(LineEvent event) {
                LineEvent.Type eventType = event.getType();
                if (eventType == LineEvent.Type.STOP || eventType == LineEvent.Type.CLOSE) {
                    done = true;
                    notifyAll();
                }
            }
            public synchronized void waitUntilDone() throws InterruptedException {
                while (!done) { wait(); }
            }
        }
        AudioListener listener = new AudioListener();
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(clipFile);
        try {
            Clip clip = AudioSystem.getClip();
            clip.addLineListener(listener);
            clip.open(audioInputStream);
            try {
                FloatControl gainControl =
                        (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                gainControl.setValue(vol); // Reduce volume by 10 decibels.
                clip.start();
                listener.waitUntilDone();
            } finally {
                clip.close();
            }
        } finally {
            audioInputStream.close();
        }
        } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
                    }


            });

               thrade.start();
               t.add(thrade);
               //return the added thread's index
               return (t.size() - 1);
    }





    public void setUp(GraphicsContext g) {
        vp.getGraphicContext(g);
        //vp.setZoom(zoom);




        //add trees
        addTrees();

//        for(Tree t : trees)
//        {
//            ((collisionComponent)t.getComponent("COLLISIONCOMPONENT")).m_offset = new Vec2d(0.3 * ((spriteComponent) (t.getComponent("SPRITECOMPONENT"))).wh.x, 0.69 * ((spriteComponent) (t.getComponent("SPRITECOMPONENT"))).wh.y);
//
//        }



        st = playSound(new File(soundTrack), -10.f);






    }




    public void onMouseMoved(MouseEvent e)
    {
        if(e.getSceneX() > 25 && e.getSceneX() < 95 && e.getSceneY() >100 && e.getSceneY() < 125)
        {
            restart.setColor(c);
            restart.t.setColor(c);
        }
        else
        {
            restart.setColor(Color.WHITE);
            restart.t.setColor(Color.WHITE);
        }

    }



    public void onMousePressed(MouseEvent e)
    {
       Vec2d mp = new Vec2d( e.getX(), e.getY());

       for(GameObject o: toDrawObj)
       {
           if(o.isIn(mp, vp))
           {
                o.setisDragged(true);
                break;
           }
       }
    }


    public void onMouseDragged(MouseEvent e, GraphicsContext g)
    {
        Vec2d mp = new Vec2d( e.getX(), e.getY());

        for(GameObject o: toDrawObj)
        {
            if(o.getisDragged())
            {
                spriteComponent s = ((spriteComponent)o.getComponent("SPRITECOMPONENT"));
                o.getTransformComponent().pos = new Vec2d(mp.x - 0.5*s.wh.x*s.scale, mp.y - 0.5*s.wh.y*s.scale);
            }
        }
    }


    public void onMouseReleased(MouseEvent e)
    {
        for(GameObject o: toDrawObj)
        {
            o.setisDragged(false);
        }
    }







    public void onMouseWheelMoved(ScrollEvent e)
    {
        if(e.getDeltaY() > 0)
        {
            zoom = new Vec2d(0.5, 0.5);
        }
        else
        {
            zoom = new Vec2d( -0.5, -0.5);
        }



        vp.setZoom(zoom);
    }


//    public void onMouseDragged(MouseEvent e, GraphicsContext g)
//    {
//
//
//    }

    public boolean onMouseClicked(MouseEvent e, Screen s)
    {

        vp.f = true;
        boolean rerestart = restart.isIN(new Vec2d(e.getSceneX(), e.getSceneY()), vp);
        if(rerestart)
        {
            p1.t.pos = new Vec2d(100, 100);
            p1.t.acc = new Vec2d(0,0);
            p1.t.velocity = new Vec2d(0,0);

            biril.t.pos = new Vec2d(500, 100);
            biril.t.acc = new Vec2d(0,0);
            biril.t.velocity = new Vec2d(0,0);
        }
        return false;
    }




    /**
     * Called when a key is pressed.
     * @param e		an FX {@link KeyEvent} representing the input event.
     */
    public void onKeyPressed(KeyEvent e)
    {


        if(e.getCode() == KeyCode.W || e.getCode() == KeyCode.UP)
        {
            m_up = 1;

            if(m_down == 1)
            {
                m_upDownRelease = true;
                m_down = 0;
            }
            lastPressed = "w";

        }

        if(e.getCode() == KeyCode.D || e.getCode() == KeyCode.RIGHT)
        {
            m_right = 1;

            ((spriteComponent)p1.getComponent("SPRITECOMPONENT")).m_sprite.index = new Vec2d(((spriteComponent)p1.getComponent("SPRITECOMPONENT")).m_sprite.index.x,
                    ((spriteComponent)p1.getComponent("SPRITECOMPONENT")).m_sprite.currentS.y + 2);

            if(m_left == 1)
            {
                m_leftRightRelease = true;
                m_left = 0;
            }
            lastPressed = "d";
        }

        if(e.getCode() == KeyCode.A || e.getCode() == KeyCode.LEFT)
        {
            m_left = 1;

            ((spriteComponent)p1.getComponent("SPRITECOMPONENT")).m_sprite.index = new Vec2d(((spriteComponent)p1.getComponent("SPRITECOMPONENT")).m_sprite.index.x,
                    ((spriteComponent)p1.getComponent("SPRITECOMPONENT")).m_sprite.currentS.y + 1);

            if(m_right == 1)
            {
                m_leftRightRelease = true;
                m_right = 0;
            }
            lastPressed = "a";
        }


        if(e.getCode() == KeyCode.S || e.getCode() == KeyCode.DOWN)
        {
            m_down = 1;

            if(m_up == 1)
            {
                m_upDownRelease = true;
                m_up = 0;
            }
            lastPressed = "s";

        }



        if(e.getCode() == KeyCode.SHIFT)
        {
           m_shift = 1;
        }

        if(e.getCode() == KeyCode.J)
        {
            m_shoot = 1;

            Ray ray = new Ray(new Vec2d( p1.t.pos.x + 100, p1.t.pos.y + 100), getdir(lastPressed));
            Vec2d wh = ((spriteComponent)biril.getComponent("SPRITECOMPONENT")).wh;
            Vec2d poly[] = {biril.t.pos,
                            new Vec2d(biril.t.pos.x + wh.x, biril.t.pos.y),
                            new Vec2d(biril.t.pos.x+ wh.x, biril.t.pos.y+ wh.y),
                            new Vec2d(biril.t.pos.x, biril.t.pos.y+ wh.y)};

            if(ray.polygonRayIntersect(poly))
            {
                biril.getTransformComponent().velocity = new Vec2d(biril.getTransformComponent().velocity.x + getdir(lastPressed).x*10, biril.getTransformComponent().velocity.y+ getdir(lastPressed).y*10);
            }

        }


        if(0 < m_down+m_up+m_right+m_left)
        {
            ((spriteComponent) p1.getComponent("SPRITECOMPONENT")).m_sprite.pause = false;
        }



    }

    /**
     * Called when a key is released.
     * @param e		an FX {@link KeyEvent} representing the input event.
     */
    public void onKeyReleased(KeyEvent e)
    {



        if(e.getCode() == KeyCode.W || e.getCode() == KeyCode.UP)
        {
            if(m_upDownRelease)
            {
                m_down = 1;

            }
            stopJump = true;
            m_up = 0;
            m_upDownRelease = false;

        }

        if(e.getCode() == KeyCode.D || e.getCode() == KeyCode.RIGHT)
        {
            if(m_leftRightRelease)
            {
                m_left = 1;

            }
            m_right = 0;
            m_leftRightRelease = false;

        }

        if(e.getCode() == KeyCode.A || e.getCode() == KeyCode.LEFT)
        {
            if(m_leftRightRelease)
            {
                m_right = 1;

            }
            m_left = 0;
            m_leftRightRelease = false;
        }

        if(e.getCode() == KeyCode.S || e.getCode() == KeyCode.DOWN)
        {
            if(m_upDownRelease)
            {
                m_down = 1;
            }
            m_down = 0;
            m_upDownRelease = false;
        }



        if(m_up + m_down + m_right + m_left >= 1)
        {
            if(m_up == 1)
            {

            }

            if(m_down == 1)
            {

            }

            if(m_right == 1)
            {
                ((spriteComponent)p1.getComponent("SPRITECOMPONENT")).m_sprite.index = new Vec2d(((spriteComponent)p1.getComponent("SPRITECOMPONENT")).m_sprite.index.x,
                        ((spriteComponent)p1.getComponent("SPRITECOMPONENT")).m_sprite.currentS.y + 2);
            }

            if(m_left == 1)
            {
                ((spriteComponent)p1.getComponent("SPRITECOMPONENT")).m_sprite.index = new Vec2d(((spriteComponent)p1.getComponent("SPRITECOMPONENT")).m_sprite.index.x,
                        ((spriteComponent)p1.getComponent("SPRITECOMPONENT")).m_sprite.currentS.y + 1);
            }
        }

        if(e.getCode() == KeyCode.SHIFT)
        {
            m_shift = 0;
        }


        if(e.getCode() == KeyCode.J)
        {
            m_shoot = 0;
        }


        if(0 == m_down+m_up+m_right+m_left)
        {
            ((spriteComponent) p1.getComponent("SPRITECOMPONENT")).m_sprite.pause = true;
        }
        else
        {
            ((spriteComponent) p1.getComponent("SPRITECOMPONENT")).m_sprite.pause = false;
        }

    }




    public Vec2d getdir(String curSprite)
    {
        if(curSprite == "s")
        {
            return new Vec2d(0, 1);
        }
        else if(curSprite == "a")
        {
            return new Vec2d(-1, 0);
        }
        else if(curSprite == "d")
        {
            return new Vec2d(1, 0);
        }
        else
        {
            return new Vec2d(0, -1);
        }
    }




    /**
     * Called periodically and used to update the state of your game.
     * @param nanosSincePreviousTick	approximate number of nanoseconds since the previous call
     */
    public void onTick(long nanosSincePreviousTick) {


        pos = p1.t.pos;
        boolean onTheGround = landed || previousLanded;
        boolean moving = m_right == 1  || m_left == 1;




        //start walking sound when on ground and walking and sound isn't already playing
        if(onTheGround && moving && walking == -1)
        {
            walking = playSound(new File(walkingSound), -20.f);
        }
        //in the air and walking sound is playing, stop walking sound
        else if(!onTheGround && walking != -1)
        {
            t.get(walking).stop();
            walking = -1;
        }
        //stopped walking and playing walking sound, stop walking sound
        else if(!moving && walking != -1)
        {
            t.get(walking).stop();
            walking = -1;
        }



        if(m_up != 0 && onTheGround)
        {
            p1.t.velocity = new Vec2d(p1.t.velocity.x, - 40.0);
            //System.out.println("SET Y VEL");
        }






        m_physicsSystem.onTick();


        m_collisionSystem.testCollision((p1.t.velocity.x > p1.t.velocity.y));


        double moveBy = 5;
        if(m_shift == 1)
        {
            moveBy = 10;
        }

        Vec2d offset = new Vec2d( - m_left + m_right,  0);

        if(offset.x != 0 || offset.y != 0)
        {
            offset = offset.normalize();
            offset = new Vec2d((offset.x * moveBy), (offset.y * moveBy));
        }

        if(vp.m_g != null)
        {
            spriteComponent obj = ((spriteComponent)p1.getComponent("SPRITECOMPONENT"));
            //keeping the player on the screen
            if(vp.m_g.getCanvas().getWidth()- (obj.wh.x * obj.scale) > p1.t.pos.x + offset.x && p1.t.pos.x + offset.x > 0)
            {
                if(vp.m_g.getCanvas().getHeight()- (obj.wh.y * obj.scale) > p1.t.pos.y + offset.y && p1.t.pos.y + offset.y > 0)
                {
                    p1.t.velocity = new Vec2d(offset.x, p1.t.velocity.y);
                }
                else
                {
                    p1.t.velocity = new Vec2d(offset.x, p1.t.velocity.y);
                    //p1.t.pos = new Vec2d(100.0, 100.0);
                }
            }
            else
            {
                if(vp.m_g.getCanvas().getHeight()- (obj.wh.y * obj.scale) > p1.t.pos.y + offset.y && p1.t.pos.y + offset.y > 0)
                {
                    p1.t.velocity = new Vec2d(p1.t.velocity.x, p1.t.velocity.y);
                }
            }

            vp.panzer(new Vec2d(-p1.t.pos.x, -p1.t.pos.y));
        }

        for (int i = 0; i < toDrawObj.size(); i++)
        {
            toDrawObj.get(i).tick(nanosSincePreviousTick);
        }


        previousLanded = landed;

        //System.out.println(lastVel);

        if((p1.t.velocity.y == 0) && lastVel >= 0)
        {
            landed = true;
        }
        else
        {
            landed = false;
        }



        p1.on_the_ground = landed;


        lastVel = p1.t.velocity.y;


    }




    public void onResize(Vec2d newSize)
    {

        m_currentStageSize = newSize;


    }

    public ArrayList<GameObject> Shutdown()
    {
        return toDrawObj;
    }



    public void addTrees()
    {

        toDraw.add(restart);

        m_lightSystem.addLight(p1);





        Tree bg;
        sprite s2 = new sprite(new Vec2d(0,0), true);
        s2.is_static = true;

        bg = new Tree(new Vec2d(0, 0), false);
        sc2 = new spriteComponent(bul, bg, s2, "SPRITECOMPONENT");
        sc2.scale = 1.25;
        bg.addComponent(sc2);
        //toDrawObj.add(bg);
        m_lightSystem.bg = bg;

        toDrawObj.add(p1);



        Tree t;

        for(int i = 0; i < xmlR.xPos.size(); i++)
        {

            if(Integer.valueOf(xmlR.m_objects.get(i)) == 0)
            {
                t = new Tree(new Vec2d(Double.valueOf(xmlR.xPos.get(i)), Double.valueOf(xmlR.yPos.get(i))), false);
                s2 = new sprite(new Vec2d(1, 0), true);
                s2.is_static = true;
                sc2 = new spriteComponent(bush, t, s2, "SPRITECOMPONENT");
                sc2.scale = 0.17;
                c2 = new collisionComponent(t, new Vec2d(sc2.wh.x * sc2.scale, sc2.wh.y * sc2.scale), shapeType.rectangle, new Vec2d(0, 12), "COLLISIONCOMPONENT");
                t.addComponent(c2);
                t.addComponent(sc2);
                m_collisionSystem.addGameObject(t);
                toDrawObj.add(t);
                //m_lightSystem.addGameObject(t);
                t.typ = 0;
            }
            else if(Integer.valueOf(xmlR.m_objects.get(i)) == 1)
            {
                t = new Tree(new Vec2d(Double.valueOf(xmlR.xPos.get(i)), Double.valueOf(xmlR.yPos.get(i))), false);
                s2 = new sprite(new Vec2d(1,0), true);
                s2.is_static = true;
                sc2 = new spriteComponent(bush2, t, s2, "SPRITECOMPONENT");
                sc2.scale = 0.15;

                sc2.outlineWH = new Vec2d(sc2.wh.x * sc2.scale - 0.15 * sc2.wh.x * sc2.scale, sc2.wh.x * sc2.scale - 0.3 * sc2.wh.y * sc2.scale);
                sc2.outlineStart = new Vec2d(Double.valueOf(xmlR.xPos.get(i)) + 0.2 * sc2.outlineWH.x,  Double.valueOf(xmlR.yPos.get(i))+ 0.15 * sc2.outlineWH.y);

                c2 = new collisionComponent(t, new Vec2d(sc2.wh.x*sc2.scale, sc2.wh.y*sc2.scale*0.55), shapeType.rectangle, new Vec2d(0, 12), "COLLISIONCOMPONENT");
                t.addComponent(c2);
                t.addComponent(sc2);
                m_collisionSystem.addGameObject(t);
                toDrawObj.add(t);
                m_lightSystem.addGameObject(t);
                t.typ = 1;
            }
            else if(Integer.valueOf(xmlR.m_objects.get(i)) == 2)
            {
                t = new Tree(new Vec2d(Double.valueOf(xmlR.xPos.get(i)), Double.valueOf(xmlR.yPos.get(i))), false);
                s2 = new sprite(new Vec2d(2,0), true);
                s2.is_static = true;
                sc2 = new spriteComponent(bush2, t, s2, "SPRITECOMPONENT");
                sc2.scale = 0.15;

                sc2.outlineWH = new Vec2d(sc2.wh.x * sc2.scale - 0.3 * sc2.wh.x * sc2.scale, sc2.wh.x * sc2.scale - 0.3 * sc2.wh.y * sc2.scale);
                sc2.outlineStart = new Vec2d(Double.valueOf(xmlR.xPos.get(i)), Double.valueOf(xmlR.yPos.get(i)) + 0.15 * sc2.outlineWH.y);

                c2 = new collisionComponent(t, new Vec2d(sc2.wh.x*sc2.scale, sc2.wh.y*sc2.scale*0.55), shapeType.rectangle, new Vec2d(0, 12), "COLLISIONCOMPONENT");
                t.addComponent(c2);
                t.addComponent(sc2);
                m_collisionSystem.addGameObject(t);
                toDrawObj.add(t);
                m_lightSystem.addGameObject(t);
                t.typ = 2;
            }
            else if(Integer.valueOf(xmlR.m_objects.get(i)) == 3)
            {

                t = new Tree(new Vec2d(Double.valueOf(xmlR.xPos.get(i)), Double.valueOf(xmlR.yPos.get(i))), false);
                s2 = new sprite(new Vec2d(0,0), true);
                s2.is_static = true;
                sc2 = new spriteComponent(thorn2, t, s2, "SPRITECOMPONENT");
                sc2.scale = 0.15;


                c2 = new collisionComponent(t, new Vec2d(sc2.wh.x*sc2.scale, sc2.wh.y*sc2.scale*0.55), shapeType.rectangle, new Vec2d(0, 12), "COLLISIONCOMPONENT");
                t.addComponent(c2);
                t.addComponent(sc2);
                m_collisionSystem.addGameObject(t);
                toDrawObj.add(t);
                m_lightSystem.addGameObject(t);
                t.typ = 3;
            }
            else if(Integer.valueOf(xmlR.m_objects.get(i)) == 4)
            {

//                t = new Tree(new Vec2d(Double.valueOf(xmlR.xPos.get(i)), Double.valueOf(xmlR.yPos.get(i))), false);
//                s2 = new sprite(new Vec2d(0,0), true);
//                s2.is_static = true;
//                sc2 = new spriteComponent(torch, t, s2, "SPRITECOMPONENT");
//                sc2.scale = 0.05;
//
//                sc2.outlineWH = new Vec2d(sc2.wh.x * sc2.scale - 0.3 * sc2.wh.x * sc2.scale, sc2.wh.x * sc2.scale - 0.3 * sc2.wh.y * sc2.scale);
//                sc2.outlineStart = new Vec2d(Double.valueOf(xmlR.xPos.get(i)), Double.valueOf(xmlR.yPos.get(i)) + 0.15 * sc2.outlineWH.y);
//
//                c2 = new collisionComponent(t, new Vec2d(sc2.wh.x*sc2.scale, sc2.wh.y*sc2.scale*0.55), shapeType.rectangle, new Vec2d(0, 12), "COLLISIONCOMPONENT");
//                t.addComponent(c2);
//                t.addComponent(sc2);
//                m_collisionSystem.addGameObject(t);
//                toDrawObj.add(t);
//                m_lightSystem.addGameObject(t);

            }
        }


    }




//    public void addTrees2()
//    {
//
//        toDraw.add(restart);
//
//
//        //adding the background////////
//        Tree bg;
//        sprite s2 = new sprite(new Vec2d(0,0), true);
//        s2.is_static = true;
//
//        bg = new Tree(new Vec2d(0, 0), false);
//        sc2 = new spriteComponent(bul, bg, s2, "SPRITECOMPONENT");
//        sc2.scale = 1.25;
//        bg.addComponent(sc2);
//        //toDrawObj.add(bg);
//        m_lightSystem.bg = bg;
//
//
//
//
//
//        ///////////////////////////////
//
//        double Y = 500;
//        double X = 0;
//
//
//        //adding torch ////////////////
//
//        Tree t = new Tree(new Vec2d(200, Y-100), false);
//        s2 = new sprite(new Vec2d(0,0), true);
//
//        sc2 = new spriteComponent(torch, t, s2, "SPRITECOMPONENT");
//        sc2.scale = 0.05;
//        sc2.currentSprite = new Vec2d(0,0);
//
//        t.addComponent(sc2);
//        //m_lightSystem.addLight(t);
//        //toDrawObj.add(t);
//
//        m_lightSystem.addLight(p1);
//
//        ////////////////////////////////
//
//
//
//
//
//
//        s2 = new sprite(new Vec2d(1,0), true);
//        s2.is_static = true;
//
//        t = new Tree(new Vec2d(0, 500), false);
//        sc2 = new spriteComponent(bush, t, s2, "SPRITECOMPONENT");
//        s2.is_static = true;
//        sc2.scale = 0.17;
//        c2 = new collisionComponent(t, new Vec2d(sc2.wh.x*sc2.scale, sc2.wh.y*sc2.scale), shapeType.rectangle, new Vec2d(0, 12), "COLLISIONCOMPONENT");
//
//
//
//
//        for(int i = 0; i < 18; i++)
//        {
//
//            sc2 = new spriteComponent(bush, t, s2, "SPRITECOMPONENT");
//            sc2.scale = 0.17;
//            c2 = new collisionComponent(t, new Vec2d(sc2.wh.x*sc2.scale, sc2.wh.y*sc2.scale), shapeType.rectangle, new Vec2d(0, 12), "COLLISIONCOMPONENT");
//            t.addComponent(c2);
//            t.addComponent(sc2);
//            m_collisionSystem.addGameObject(t);
//            toDrawObj.add(t);
//            //m_lightSystem.addGameObject(t);
//
//            X+=sc2.wh.x*sc2.scale;
//
//
//
//            t = new Tree(new Vec2d(X, Y), false);
//            sc2 = new spriteComponent(bush, t, s2, "SPRITECOMPONENT");
//        }
//
//        Y = 350;
//        X = 400;
//
//
//        //adding platform
//        for(int i = 1; i < 3; i++)
//        {
//            t = new Tree(new Vec2d(X, Y), false);
//            s2 = new sprite(new Vec2d(i,0), true);
//            s2.is_static = true;
//            sc2 = new spriteComponent(bush2, t, s2, "SPRITECOMPONENT");
//            sc2.scale = 0.15;
//
//
//            if(i == 1)
//            {
//                sc2.outlineWH = new Vec2d(sc2.wh.x * sc2.scale - 0.15 * sc2.wh.x * sc2.scale, sc2.wh.x * sc2.scale - 0.3 * sc2.wh.y * sc2.scale);
//                sc2.outlineStart = new Vec2d(X + 0.2 * sc2.outlineWH.x, Y + 0.15 * sc2.outlineWH.y);
//            }
//            else
//            {
//                sc2.outlineWH = new Vec2d(sc2.wh.x * sc2.scale - 0.3 * sc2.wh.x * sc2.scale, sc2.wh.x * sc2.scale - 0.3 * sc2.wh.y * sc2.scale);
//                sc2.outlineStart = new Vec2d(X, Y + 0.15 * sc2.outlineWH.y);
//            }
//
//
//            c2 = new collisionComponent(t, new Vec2d(sc2.wh.x*sc2.scale, sc2.wh.y*sc2.scale*0.55), shapeType.rectangle, new Vec2d(0, 12), "COLLISIONCOMPONENT");
//            t.addComponent(c2);
//            t.addComponent(sc2);
//            m_collisionSystem.addGameObject(t);
//            toDrawObj.add(t);
//            m_lightSystem.addGameObject(t);
//
//            X+=sc2.wh.x*sc2.scale;
//        }
//
//
//        Y = 240;
//        X = 200;
//
//
//        //adding platform
//        for(int i = 1; i < 3; i++)
//        {
//            t = new Tree(new Vec2d(X, Y), false);
//            s2 = new sprite(new Vec2d(i,0), true);
//            s2.is_static = true;
//            sc2 = new spriteComponent(bush2, t, s2, "SPRITECOMPONENT");
//            sc2.scale = 0.15;
//
//            if(i == 1)
//            {
//                sc2.outlineWH = new Vec2d(sc2.wh.x * sc2.scale - 0.15 * sc2.wh.x * sc2.scale, sc2.wh.x * sc2.scale - 0.3 * sc2.wh.y * sc2.scale);
//                sc2.outlineStart = new Vec2d(X + 0.2 * sc2.outlineWH.x, Y + 0.15 * sc2.outlineWH.y);
//            }
//            else
//            {
//                sc2.outlineWH = new Vec2d(sc2.wh.x * sc2.scale - 0.3 * sc2.wh.x * sc2.scale, sc2.wh.x * sc2.scale - 0.3 * sc2.wh.y * sc2.scale);
//                sc2.outlineStart = new Vec2d(X, Y + 0.15 * sc2.outlineWH.y);
//            }
//
//
//            c2 = new collisionComponent(t, new Vec2d(sc2.wh.x*sc2.scale, sc2.wh.y*sc2.scale*0.55), shapeType.rectangle, new Vec2d(0, 12), "COLLISIONCOMPONENT");
//            t.addComponent(c2);
//            t.addComponent(sc2);
//            m_collisionSystem.addGameObject(t);
//            toDrawObj.add(t);
//            m_lightSystem.addGameObject(t);
//
//
//            X+=sc2.wh.x*sc2.scale;
//        }
//
//
//    }
}



