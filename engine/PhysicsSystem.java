package engine;

import engine.GameObject;
import engine.System;
import engine.support.Vec2d;

import java.util.ArrayList;

public class PhysicsSystem implements System {

    ArrayList<GameObject> m_ALLgameObjects = new ArrayList<GameObject>();
    double gravity = 0.55;
    double fakeFriction = 1.0;
    double fakeAirFriction = 0.78;


    @Override
    public void addGameObject(GameObject c)
    {
        m_ALLgameObjects.add(c);
    }

    @Override
    public void removeGameObject(GameObject c)
    {
        m_ALLgameObjects.remove(c);
    }

    public void onTick()
    {
        for (GameObject o: m_ALLgameObjects)
        {
            TransformComponent t = ((TransformComponent)o.getTransformComponent());


            t.acc = new Vec2d(t.acc.x, t.acc.y + gravity);
            if(t.acc.y == 0)
            {
                t.velocity = new Vec2d(t.velocity.x, 0);
            }
            else
            {
                t.velocity = new Vec2d(t.velocity.x + t.acc.x, t.velocity.y + t.acc.y);
            }





            //x vel
            if(t.velocity.x < -0.001 && o.getLanded())
            {
                if(t.velocity.x > -0.5)
                {
                    t.velocity = new Vec2d(0, t.velocity.y);
                }
                else
                {
                    t.velocity = new Vec2d(t.velocity.x + fakeFriction, t.velocity.y);
                }
            }
            else if(t.velocity.x > 0.001 && o.getLanded())
            {
                if(t.velocity.x < 0.5)
                {
                    t.velocity = new Vec2d(0, t.velocity.y);
                }
                else
                {
                    t.velocity = new Vec2d(t.velocity.x - fakeFriction, t.velocity.y);
                }
            }

            //x vel
            if(t.velocity.x < -0.001 && o.getLanded())
            {
                if(t.velocity.x > -0.5)
                {
                    t.velocity = new Vec2d(0, t.velocity.y);
                }
                else
                {
                    t.velocity = new Vec2d(t.velocity.x + fakeAirFriction, t.velocity.y);
                }
            }
            else if(t.velocity.x > 0.001 && o.getLanded())
            {
                if(t.velocity.x < 0.5)
                {
                    t.velocity = new Vec2d(0, t.velocity.y);
                }
                else
                {
                    t.velocity = new Vec2d(t.velocity.x - fakeAirFriction, t.velocity.y);
                }
            }









            //gates to not let velocity get too high
            if(t.velocity.y > 30.0)
            {
                t.velocity = new Vec2d(t.velocity.x, 30);
            }
            else if(t.velocity.y < -30.0)
            {
                t.velocity = new Vec2d(t.velocity.x, -30);
            }

            if(t.velocity.x > 15.0)
            {
                t.velocity = new Vec2d(15, t.velocity.y);
            }
            else if(t.velocity.x < -15.0)
            {
                t.velocity = new Vec2d(-15, t.velocity.y);
            }
            /////////////////////////////////////////


            t.pos = new Vec2d(t.pos.x + t.velocity.x, t.pos.y + t.velocity.y);




        }


    }


}
