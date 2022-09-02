package engine;

import engine.support.Vec2d;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.transform.Affine;


public class ViewPort {
    public Vec2d upperLeftScreenSpace = new Vec2d(0,0);
    public Vec2d upperLeftWorldSpace = new Vec2d(0,0);
    public Vec2d scalePixelsPerUnit = new Vec2d(2,2);

    public Vec2d mousePos = new Vec2d(-1,-1);
    public Vec2d to_track = new Vec2d(-1,-1);
    public boolean f = true;
    public boolean t = true;
    public GraphicsContext m_g;
    public Vec2d store;


    public ViewPort()
    {

    }

    public Vec2d gamePointToScreen(Vec2d p)
    {
        Vec2d end = new Vec2d(((p.x - upperLeftWorldSpace.x)*scalePixelsPerUnit.x)+upperLeftScreenSpace.x, ((p.y - upperLeftWorldSpace.y)*scalePixelsPerUnit.y)+upperLeftScreenSpace.y);
        return end;
    }

    public Vec2d screenPointToGame(Vec2d p)
    {
        Vec2d end = new Vec2d(((p.x-upperLeftScreenSpace.x)/scalePixelsPerUnit.x)+upperLeftWorldSpace.x, ((p.y-upperLeftScreenSpace.y)/scalePixelsPerUnit.y)+upperLeftWorldSpace.y);
        return end;
    }

    public void getGraphicContext(GraphicsContext g)
    {
        m_g = g;
    }


    public void pan(Vec2d newU)
    {
        if(f)
        {
            mousePos = newU;
            f = false;
        }

        Affine t = m_g.getTransform();

        Vec2d offset = new Vec2d(newU.x - mousePos.x, newU.y - mousePos.y);

        t.setTx((upperLeftScreenSpace.x + offset.x));
        t.setTy((upperLeftScreenSpace.y + offset.y));

        double xL = (m_g.getCanvas().getWidth() - (m_g.getCanvas().getWidth() * t.getMxx()) );

        if(t.getTx() > 0)
        {
            t.setTx(0.0);
        }

        boolean eh = t.getTx() < xL;

        if(eh)
        {
            t.setTx(xL);
        }

        if(t.getTy() > 0)
        {
            t.setTy(0.0);
        }

        double yL = (m_g.getCanvas().getHeight() - (m_g.getCanvas().getHeight() * t.getMyy()) );

        boolean ew = t.getTy() < yL ;

        if(ew)
        {
            t.setTy(yL);
        }

        m_g.setTransform(t);
        upperLeftScreenSpace = new Vec2d(t.getTx(), t.getTy());
        mousePos = newU;
    }


    public void panzer(Vec2d newU)
    {
        if(t)
        {
            to_track = newU;
            t = false;
        }

        Affine t = m_g.getTransform();

        Vec2d offset = new Vec2d((newU.x - to_track.x)*t.getMxx(), (newU.y - to_track.y)*t.getMyy());

        if(newU.x > -m_g.getCanvas().getWidth() + 0.5*(m_g.getCanvas().getWidth()/t.getMxx()) && newU.x < 0.5*(-m_g.getCanvas().getWidth()/t.getMxx()))
        {
            t.setTx((upperLeftScreenSpace.x + offset.x));
        }


        if(newU.y > -m_g.getCanvas().getHeight() + 0.5*(m_g.getCanvas().getHeight()/t.getMyy()) && newU.y < 0.5*(-m_g.getCanvas().getHeight()/t.getMyy()))
        {
            t.setTy((upperLeftScreenSpace.y + offset.y));
        }


        double xL = (m_g.getCanvas().getWidth() - (m_g.getCanvas().getWidth() * t.getMxx()) );


        if(t.getTx() > 0)
        {
            t.setTx(0.0);
        }

        boolean eh = t.getTx() < xL;

        if(eh)
        {
            t.setTx(xL);
        }

        if(t.getTy() > 0)
        {
            t.setTy(0.0);
        }

        double yL = (m_g.getCanvas().getHeight() - (m_g.getCanvas().getHeight() * t.getMyy()) );

        boolean ew = t.getTy() < yL ;

        if(ew)
        {
            t.setTy(yL);
        }

        m_g.setTransform(t);
        upperLeftScreenSpace = new Vec2d(t.getTx(), t.getTy());
        to_track = newU;
    }


    public void setZoom(Vec2d z)
    {
        scalePixelsPerUnit = z;
        Affine t = m_g.getTransform();

        t.setMxx(t.getMxx() + z.x);
        t.setMyy(t.getMyy() + z.y);

        if(t.getMxx() < 1)
        {
            t.setMxx(1.0);
            t.setMyy(1.0);
        }
        else if(t.getMxx() > 3)
        {
            t.setMxx(3.0);
            t.setMyy(3.0);
        }

        double xL = (m_g.getCanvas().getWidth() - (m_g.getCanvas().getWidth() * t.getMxx()) );

        if(t.getTx() > 0)
        {
            t.setTx(0.0);
        }

        boolean eh = t.getTx() < xL ;

        if(eh)
        {
            t.setTx(xL);
        }

        if(t.getTy() > 0)
        {
            t.setTy(0.0);
        }

        double yL = (m_g.getCanvas().getHeight() - (m_g.getCanvas().getHeight() * t.getMyy()) );

        boolean ew = t.getTy() < yL ;

        if(ew)
        {
            t.setTy(yL);
        }

        upperLeftScreenSpace = new Vec2d(t.getTx(), t.getTy());

        m_g.setTransform(t);
    }

}
