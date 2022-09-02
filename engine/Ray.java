package engine;

import engine.support.Vec2d;

public class Ray
{

    public Vec2d m_point;
    public Vec2d m_dir;

    public Ray(Vec2d point, Vec2d dir)
    {
        m_point = point;
        m_dir = dir;
        m_dir.normalize();
    }

    public Vec2d getPoint(double t)
    {

        return new Vec2d(m_point.x + t*m_dir.x,m_point.y + t*m_dir.y);
    }

    double CrossProduct(Vec2d v1, Vec2d v2)
    {
        return (v1.x*v2.y - v1.y*v2.x);
    }

    double VectorDotProduct(Vec2d v1, Vec2d v2)
    {
        return v1.x * v2.x + v1.y * v2.y;
    }

    public double intersectRayEdge(Vec2d a, Vec2d b)
    {
        Vec2d V_2 = new Vec2d(b.x - a.x, b.y - a.y);
        Vec2d V_1 = new Vec2d(m_point.x-a.x, m_point.y-a.y);
        Vec2d V_3 = new Vec2d(-m_dir.y, m_dir.x);

        double t1 = (CrossProduct(V_2, V_1))/(VectorDotProduct(V_2, V_3));
        double t2 = (VectorDotProduct(V_1, V_3))/(VectorDotProduct(V_2, V_3));


        if(t1 >= 0 && (0 <= t2 && t2 <= 1))
        {

            return t1;
        }


        return Double.POSITIVE_INFINITY;
    }


    //expecting a list of all the points of a polygon in either clockwise or counter-clockwise order
    public boolean polygonRayIntersect(Vec2d poly[])
    {


        double lowest_t = Double.POSITIVE_INFINITY;
        for(int i = 0; i < poly.length; i+=1)
        {
            double intersect;
            if(i+1 == poly.length)
           {
               intersect = intersectRayEdge(poly[i], poly[0]);
           }
           else
           {
               intersect = intersectRayEdge(poly[i], poly[i + 1]);
           }

           if(intersect < lowest_t)
           {
                lowest_t = intersect;
           }
        }



        if(lowest_t < Double.POSITIVE_INFINITY)
        {
            java.lang.System.out.println("true");
            return true;
        }

        java.lang.System.out.println("false");
        return false;
    }


    //expecting a list of all the points of a polygon in either clockwise or counter-clockwise order
    //returns the side number given by how the input is given to it
    // Example square {p1, p2, p3, p4}
    //
    //  if the ray intersects segment p1->p2, this function returns 0
    //  if the ray intersects segment p2->p3, this function returns 1
    //  if the ray intersects segment p3->p4, this function returns 2
    //  if the ray intersects segment p4->p3, this function returns 3
    //
    //          p1_______p2
    //           |       |
    //           |       |
    //           |_______|
    //          p4       p3
    //
    //
    public double polygonRayIntersectSide(Vec2d poly[])
    {
        double lowest_t = 0;
        double low = Double.POSITIVE_INFINITY;

        for(int i = 0; i < poly.length; i+=1)
        {
            double intersect;

            if(i+1 == poly.length)
            {
                intersect = intersectRayEdge(poly[i], poly[0]);
            }
            else
            {
                intersect = intersectRayEdge(poly[i], poly[i + 1]);
            }

            //java.lang.System.out.println(intersect + " < " + low);
            if(intersect < low)
            {
                lowest_t = i;
                low = intersect;

            }

        }

        //java.lang.System.out.println(lowest_t);


        return lowest_t;

    }
}
