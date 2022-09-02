package engine;

import engine.support.Vec2d;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import tic.Plant;

import java.lang.Math;

public class collisionComponent implements Component {

    String tag;
    public GameObject owner;
    Vec2d wh;
    double rad = 0;
    shapeType s;
    public Vec2d m_offset;
    boolean once = false;

    public double gravity = 0.55;




    public collisionComponent(GameObject o, Vec2d wandh, shapeType sin, Vec2d offset, String t)
    {
        owner = o;
        owner.addComponent(this);
        wh = wandh;
        s = sin;
        m_offset = offset;
        tag = t;

    }

    public boolean collidesCircle(collisionComponent o) {
        if(this.s == shapeType.circle)
            return false; //circle-circle collision

        if(this.s == shapeType.rectangle)
            return false; //circle-rectangle collision

        if(this.s == shapeType.line)
            return false; //circle-line collision

        if(this.s == shapeType.point)
            return false; //circle-point collision

        return false;
    }

    public boolean collidesRectangle(collisionComponent o) {
        if(this.s == shapeType.circle)
            return false; //rectangle-circle collision

        if(this.s == shapeType.rectangle)
            return rectRectangleCollide(o, this); //rectangle-rectangle collision

        if(this.s == shapeType.line)
            return false; //rectangle-line collision

        if(this.s == shapeType.point)
            return false; //rectangle-point collision

        return false;
    }

    public boolean collidesLine(collisionComponent o) {
        if(this.s == shapeType.circle)
            return false; //line-circle collision

        if(this.s == shapeType.rectangle)
            return false; //line-rectangle collision

        if(this.s == shapeType.line)
            return false; //line-line collision

        if(this.s == shapeType.point)
            return false; //line-point collision

        return false;
    }

    public boolean collidesPoint(collisionComponent o) {
        if(this.s == shapeType.circle)
            return false; //point-circle collision

        if(this.s == shapeType.rectangle)
            return false; //point-rectangle collision

        if(this.s == shapeType.line)
            return false; //point-line collision

        if(this.s == shapeType.point)
            return false; //point-point collision

        return false;
    }

    @Override
    public void tick(long nanosSinceLastTick) {

    }

    @Override
    public void lateTick() {

    }

    @Override
    public String getTag() {
        return tag;
    }

    @Override
    public void setTag(String t) {
        tag = t;
    }

    @Override
    public void draw(GraphicsContext g)
    {
        Paint c = g.getStroke();
        g.setStroke(new Color(1,0,0, 1));
        Vec2d p = new Vec2d( this.owner.getTransformComponent().pos.x + this.m_offset.x, this.owner.getTransformComponent().pos.y + this.m_offset.y);
        g.strokeRect(p.x, p.y, wh.x, wh.y);
        g.setStroke(c);
    }

    public boolean collide(collisionComponent o)
    {

        if(this.s == shapeType.circle)
            return o.collidesCircle(this);

        if(this.s == shapeType.rectangle)
            return o.collidesRectangle(this);

        if(this.s == shapeType.line)
            return o.collidesLine(this);

        if(this.s == shapeType.point)
            return o.collidesPoint(this);

        return false;
    }

    public void setWH(Vec2d wandh)
    {
        wh = wandh;
    }

    public boolean pointRectangleCollide(Vec2d point, Vec2d rect)
    {
        boolean biggerX = point.x > rect.x;
        boolean biggerY = point.y > rect.y;
        boolean smallerX = point.x < (rect.x) + (wh.x);
        boolean smallerY = point.y < (rect.y) + (wh.y);

        return(biggerY && smallerY && biggerX && smallerX);
    }


    public Vec2d closestPointOnRectToAPoint(collisionComponent rect2, Vec2d point)
    {
        Vec2d toRet = new Vec2d(0,0);

        double x_1 = rect2.owner.getTransformComponent().pos.x;
        double x_2 = rect2.owner.getTransformComponent().pos.x + rect2.wh.x;
        double y_1 = rect2.owner.getTransformComponent().pos.y;
        double y_2 = rect2.owner.getTransformComponent().pos.y + rect2.wh.y;


        if(point.x < x_1)
        {
            toRet = new Vec2d( x_1, toRet.y);
        }
        else if(point.x > x_2)
        {
            toRet = new Vec2d( x_2, toRet.y);
        }
        else
        {
            toRet = new Vec2d( point.x, toRet.y);
        }



        if(point.y < y_1)
        {
            toRet = new Vec2d( toRet.x, y_1);
        }
        else if(point.y > y_2)
        {
            toRet = new Vec2d( toRet.x, y_2);
        }
        else
        {
            toRet = new Vec2d( toRet.x, point.y);
        }



        return toRet;
    }

    public double length(Vec2d v) {
        return Math.sqrt((v.x*v.x)+(v.y*v.y));
    }

    public boolean rectRectangleCollide(collisionComponent rect1, collisionComponent rect2)
    {
        Vec2d wh1 = rect1.wh;
        Vec2d wh2 = rect2.wh;
        Vec2d pos1 = new Vec2d( rect1.owner.getTransformComponent().pos.x + rect1.m_offset.x, rect1.owner.getTransformComponent().pos.y + rect1.m_offset.y);
        Vec2d pos2 = new Vec2d( rect2.owner.getTransformComponent().pos.x + rect2.m_offset.x, rect2.owner.getTransformComponent().pos.y + rect2.m_offset.y);



        if (pos1.x < pos2.x + wh2.x &&
                pos1.x + wh1.x > pos2.x &&
                pos1.y < pos2.y + wh2.y &&
                pos1.y + wh1.y > pos2.y)
        {
            // collision detected!
            Vec2d mtv = new Vec2d(intervalMTV(new Vec2d(pos1.x, pos1.x + wh1.x), new Vec2d(pos2.x, pos2.x + wh2.x)),
                    intervalMTV(new Vec2d(pos1.y, pos1.y + wh1.y), new Vec2d(pos2.y, pos2.y + wh2.y)));

            //java.lang.System.out.println(Math.abs(mtv.y));


            Vec2d middleOfRect = new Vec2d( rect2.owner.getTransformComponent().pos.x + (rect2.wh.x/2.0), rect2.owner.getTransformComponent().pos.y + (rect2.wh.y/2.0));
            Vec2d cos = closestPointOnRectToAPoint(rect1, middleOfRect);
            Vec2d dir = new Vec2d(-rect1.owner.getTransformComponent().velocity.normalize().x, -rect1.owner.getTransformComponent().velocity.normalize().y);

            Ray r = new Ray(cos,dir);

            Vec2d topLeft = rect1.owner.getTransformComponent().pos;
            Vec2d topRight = new Vec2d( topLeft.x + rect1.wh.x, topLeft.y);
            Vec2d bottomRight = new Vec2d( topLeft.x + rect1.wh.x, topLeft.y + rect1.wh.y );
            Vec2d bottomLeft = new Vec2d( topLeft.x, topLeft.y + rect1.wh.y );


            Vec2d poly[] = {topLeft, topRight, bottomRight, bottomLeft};

            double s = 0;//r.polygonRayIntersectSide(poly);

            if(length(rect1.owner.getTransformComponent().velocity) > length(rect2.owner.getTransformComponent().velocity))
            {
                if(rect1.owner.getTransformComponent().velocity.x > rect1.owner.getTransformComponent().velocity.y)
                {
                    s = 1;
                }
                else
                {
                    s = 0;
                }
            }
            else
            {
                if(rect2.owner.getTransformComponent().velocity.x > rect2.owner.getTransformComponent().velocity.y)
                {
                    s = 1;
                }
                else
                {
                    s = 0;
                }
            }

            s= 0;




            if(s == 1 || s == 3)
            {
                java.lang.System.out.println(mtv.x);
                if(((Plant)(rect1.owner)).isMoveable && !((Plant)(rect2.owner)).isMoveable)
                {
                    rect1.owner.getTransformComponent().pos = new Vec2d(rect1.owner.getTransformComponent().pos.x + mtv.x, rect1.owner.getTransformComponent().pos.y);
                    rect1.owner.getTransformComponent().velocity = new Vec2d(0,rect1.owner.getTransformComponent().velocity.y);
                    rect1.owner.getTransformComponent().acc = new Vec2d(0,rect1.owner.getTransformComponent().acc.y);
                }
                else if(!((Plant)(rect1.owner)).isMoveable && ((Plant)(rect2.owner)).isMoveable)
                {
                    rect2.owner.getTransformComponent().pos = new Vec2d(rect2.owner.getTransformComponent().pos.x + mtv.x, rect2.owner.getTransformComponent().pos.y);
                    rect2.owner.getTransformComponent().velocity = new Vec2d(0,0);
                    rect1.owner.getTransformComponent().acc = new Vec2d(0,0);
                }
                else //both are movable (already check if both aren't)
                {
                    rect1.owner.getTransformComponent().pos = new Vec2d(rect1.owner.getTransformComponent().pos.x + 0.5*mtv.x, rect1.owner.getTransformComponent().pos.y);
                    rect2.owner.getTransformComponent().pos = new Vec2d(rect2.owner.getTransformComponent().pos.x - 0.5*mtv.x, rect2.owner.getTransformComponent().pos.y);
                }
            }
            else if(s == 0 || s == 2)
            {
                if(((Plant)(rect1.owner)).isMoveable && !((Plant)(rect2.owner)).isMoveable)
                {
                    rect1.owner.getTransformComponent().pos = new Vec2d(rect1.owner.getTransformComponent().pos.x, rect1.owner.getTransformComponent().pos.y + mtv.y);
                    rect1.owner.getTransformComponent().acc = new Vec2d(rect1.owner.getTransformComponent().acc.x,-gravity);

                }
                else if(!((Plant)(rect1.owner)).isMoveable && ((Plant)(rect2.owner)).isMoveable)
                {
                    rect2.owner.getTransformComponent().pos = new Vec2d(rect2.owner.getTransformComponent().pos.x, rect2.owner.getTransformComponent().pos.y + mtv.y);
                    rect2.owner.getTransformComponent().acc = new Vec2d(rect2.owner.getTransformComponent().acc.x,-gravity);
                }
                else //both are movable (already check if both aren't)
                {
                    rect1.owner.getTransformComponent().pos = new Vec2d(rect1.owner.getTransformComponent().pos.x, rect1.owner.getTransformComponent().pos.y + 0.5*mtv.y);
                    rect2.owner.getTransformComponent().pos = new Vec2d(rect2.owner.getTransformComponent().pos.x, rect2.owner.getTransformComponent().pos.y - 0.5*mtv.y);

                    rect1.owner.getTransformComponent().acc = new Vec2d(rect1.owner.getTransformComponent().acc.x,-gravity);
                    rect2.owner.getTransformComponent().acc = new Vec2d(rect2.owner.getTransformComponent().acc.x,-gravity);
                }
            }
            else
            {
                java.lang.System.out.println("ERROR - " + s);
            }






            if(!once)
            {
                once = true;
                rect1.owner.getTransformComponent().pos = new Vec2d( rect1.owner.getTransformComponent().pos.x-0.1, rect1.owner.getTransformComponent().pos.y);
                rect1.owner.getTransformComponent().pos = new Vec2d( rect1.owner.getTransformComponent().pos.x+0.2, rect1.owner.getTransformComponent().pos.y);

                boolean a = rectRectangleCollide(rect1, rect2);
                boolean c = rectRectangleCollide(rect1, rect2);

                rect1.owner.getTransformComponent().pos = new Vec2d( rect1.owner.getTransformComponent().pos.x+0.1, rect1.owner.getTransformComponent().pos.y);
                rect1.owner.getTransformComponent().pos = new Vec2d( rect1.owner.getTransformComponent().pos.x-0.2, rect1.owner.getTransformComponent().pos.y);

                return a&&c;
            }


            once = false;
            return true;
        }

        //java.lang.System.out.println("rectangles did not collide");

        return false;
    }


    public boolean circleRectangleCollide(collisionComponent circle, collisionComponent rect)
    {
        Vec2d circlePos = new Vec2d( circle.owner.getTransformComponent().pos.x + circle.m_offset.x, circle.owner.getTransformComponent().pos.y + circle.m_offset.y);
        Vec2d rectPos = new Vec2d( rect.owner.getTransformComponent().pos.x + rect.m_offset.x, rect.owner.getTransformComponent().pos.y + rect.m_offset.y);

        Vec2d circleDistance = new Vec2d(
                Math.abs(circlePos.x - rectPos.x),
                Math.abs(circlePos.y - rectPos.y));


        if (circleDistance.x > (rect.wh.x/2 + circle.rad)) { return false; }
        if (circleDistance.y > (rect.wh.y/2 + circle.rad)) { return false; }

        if (circleDistance.x <= (rect.wh.x/2))
        {

            return true;
        }
        if (circleDistance.y <= (rect.wh.y/2))
        {

            return true;
        }

        double cornerDistance_sq = (circleDistance.x - rect.wh.x/2.0)*(circleDistance.x - rect.wh.x/2.0) +
                (circleDistance.y - rect.wh.y/2.0)*(circleDistance.y - rect.wh.y/2.0);


        if(cornerDistance_sq <= (circle.rad*circle.rad))
        {



            return true;
        }

        return false;
    }


    public boolean pointCircleCollide(collisionComponent circle, Vec2d point)
    {
        Vec2d circlePos = new Vec2d( circle.owner.getTransformComponent().pos.x + circle.m_offset.x, circle.owner.getTransformComponent().pos.y + circle.m_offset.y);

        return (Math.abs(circlePos.x - point.x) < circle.rad &&
                Math.abs(circlePos.y - point.y) < circle.rad);
    }

    //max is y, min is x
    Double intervalMTV(Vec2d a, Vec2d b)
    {
        Double aRight = b.y - a.x;
        Double aLeft = a.y - b.x;



        if (aLeft< 0 || aRight < 0)
        {
            java.lang.System.out.println("Error in interval MTV");
            return null;
        }
        if (aRight<aLeft)
        {
            return aRight;
        }
        else
        {
            return -aLeft;
        }
    }
}
