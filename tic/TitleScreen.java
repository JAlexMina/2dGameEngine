package tic;

import engine.*;
import engine.support.Vec2d;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;

import java.lang.System;

public class TitleScreen extends Screen {


    button seed200 = new button(new Vec2d(25,100), new Vec2d(78, 25),"Seed200", "BUTTON", Color.BLACK, "STROKE");
    button seed400 = new button(new Vec2d(25,150), new Vec2d(78, 25),"Seed400", "BUTTON", Color.BLACK, "FILL");
    button seed600 = new button(new Vec2d(25,200), new Vec2d(78, 25),"Seed600", "BUTTON", Color.BLACK, "STROKE");
    Color c;
    ViewPort vp = new ViewPort();
    Vec2d zoom = new Vec2d(1.0, 1.0);



    public TitleScreen(Vec2d currentStageSize)
    {
        toDraw.add(seed200);
        toDraw.add(seed400);
        toDraw.add(seed600);

        m_currentStageSize = currentStageSize;
        c = Color.GRAY;

    }


    public void onMouseDragged(MouseEvent e, GraphicsContext g)
    {
        vp.pan(new Vec2d(e.getSceneX(), e.getSceneY()));
        seed200.s = new Vec2d(seed200.s.x/vp.scalePixelsPerUnit.x, seed200.s.y/vp.scalePixelsPerUnit.y);
        seed400.s = new Vec2d(seed400.s.x/vp.scalePixelsPerUnit.x, seed400.s.y/vp.scalePixelsPerUnit.y);
        seed600.s = new Vec2d(seed600.s.x/vp.scalePixelsPerUnit.x, seed600.s.y/vp.scalePixelsPerUnit.y);

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

    public void setUp(GraphicsContext g)
    {
        vp.getGraphicContext(g);
    }


    public void onMouseMoved(MouseEvent e)
    {
        if(e.getSceneX() > 25 && e.getSceneX() < 95 && e.getSceneY() >100 && e.getSceneY() < 125)
        {
            seed200.setColor(c);
        }
        else
        {
            seed200.setColor(Color.BLACK);
        }

        if(e.getSceneX() > 25 && e.getSceneX() < 95 && e.getSceneY() > 150 && e.getSceneY() < 175)
        {
            seed400.setColor(c);
        }
        else
        {
            seed400.setColor(Color.BLACK);
        }


        if(e.getSceneX() > 25 && e.getSceneX() < 95 && e.getSceneY() >200 && e.getSceneY() < 225)
        {
            seed600.setColor(c);
        }
        else
        {
            seed600.setColor(Color.BLACK);
        }
    }

    public boolean onMouseClicked(MouseEvent e, Screen s)
    {
        boolean bool200 = seed200.isIN(new Vec2d(e.getSceneX(), e.getSceneY()), vp);
        if(bool200)
        {

        }

        boolean bool400 = seed400.isIN(new Vec2d(e.getSceneX(), e.getSceneY()), vp);
        if(bool400)
        {

        }

        boolean bool600 = seed600.isIN(new Vec2d(e.getSceneX(), e.getSceneY()), vp);
        if(bool600)
        {


        }
        //((Alc)s).addTrees();
        return (bool200||bool400||bool600);

    }

    public void onResize(Vec2d newSize)
    {
        m_currentStageSize = newSize;
    }
}



