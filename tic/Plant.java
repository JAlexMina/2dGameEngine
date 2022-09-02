package tic;

import engine.*;
import engine.support.Vec2d;
import javafx.scene.canvas.GraphicsContext;

import java.lang.System;
import java.util.ArrayList;

public class Plant implements GameObject {
    boolean on_the_ground = false;
    ArrayList<Component> m_components = new ArrayList<Component>();
    ArrayList<Component> m_DRAWcomponents = new ArrayList<Component>();
    public TransformComponent t = new TransformComponent(new Vec2d(400, 300));
    public Vec2d currentSprite = new Vec2d(0.0, 0.0);
    public boolean isMoveable;
    public boolean isDragged = false;
    public int typ = -1;

    public Plant(Vec2d pos, boolean m)
    {
        t.pos = pos;
        isMoveable = m;
    }



    @Override
    public boolean isIn(Vec2d point, ViewPort vp)
    {
        spriteComponent s = (spriteComponent)getComponent("SPRITECOMPONENT");

        Vec2d scaledWH = new Vec2d(s.wh.x * s.scale, s.wh.y * s.scale);


        boolean xrightOfleftSide = point.x > t.pos.x * vp.m_g.getTransform().getMxx();
        boolean xleftOfRightSide = point.x < (t.pos.x * vp.m_g.getTransform().getMxx()) + (scaledWH.x * vp.m_g.getTransform().getMxx());


        boolean yleftOfRightSide = point.y > t.pos.y * vp.m_g.getTransform().getMyy();
        boolean yrightOfleftSide = point.y < (t.pos.y * vp.m_g.getTransform().getMyy()) + (scaledWH.y * vp.m_g.getTransform().getMyy());


        boolean xyesno =  xrightOfleftSide && xleftOfRightSide;
        boolean yyesno = yleftOfRightSide && yrightOfleftSide;

        return xyesno && yyesno;
    }

    @Override
    public boolean getisDragged() {
        return isDragged;
    }

    @Override
    public void setisDragged(Boolean b) {
        isDragged = b;
    }

    @Override
    public int getType() {
        return typ;
    }

    @Override
    public int compareTo(GameObject comepareObject)
    {
//        int compareQuantity = ((Fruit) compareFruit).getQuantity();
//
//        //ascending order
//        return this.quantity - compareQuantity;
//
//        //descending order
//        //return compareQuantity - this.quantity;
        return 0;
    }


    @Override
    public void addComponent(Component c) {
        m_components.add(c);
        if(c.getTag() == "DRAWCOMPONENT" || c.getTag() == "SPRITECOMPONENT")
        {
            m_DRAWcomponents.add(c);
        }
    }

    @Override
    public void removeComponent(Component c) {
        m_components.remove(c);
        m_DRAWcomponents.remove(c);
    }

    @Override
    public Component getComponent(String tag) {
        for (int i = 0; i < m_components.size(); i+=1)
        {
            if(m_components.get(i).getTag() == tag)
            {
                return m_components.get(i);
            }
        }
        return null;
    }

    @Override
    public TransformComponent getTransformComponent() {
        return t;
    }

    @Override
    public int getZ() {
        return 0;
    }

    @Override
    public boolean getLanded() {
        return on_the_ground;
    }

    @Override
    public void setTransformComponent(TransformComponent trans) {
        t = trans;
    }

    @Override
    public void tick(long t) {

        for(int i = 0; i < m_components.size(); i += 1)
        {
            m_components.get(i).tick(t);
        }

    }

    @Override
    public void lateTick() {

    }

    @Override
    public void draw(GraphicsContext g)
    {
        for(int i = 0; i < m_DRAWcomponents.size(); i += 1)
        {
            m_DRAWcomponents.get(i).draw(g);
        }
    }
}
