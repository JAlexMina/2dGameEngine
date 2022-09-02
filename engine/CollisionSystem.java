package engine;

import engine.support.Vec2d;
import javafx.scene.canvas.GraphicsContext;
import tic.Plant;
import tic.bullet;

import java.util.ArrayList;

public class CollisionSystem implements System{

    ArrayList<GameObject> m_ALLgameObjects = new ArrayList<GameObject>();


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


    public void onDraw(GraphicsContext g)
    {

        for(GameObject o: m_ALLgameObjects)
        {
            collisionComponent c = (collisionComponent) (o.getComponent("COLLISIONCOMPONENT"));
            c.draw(g);
        }

    }


    public void testCollision(boolean lf)
    {
        ArrayList<GameObject> remainingOBJs = new ArrayList<>(m_ALLgameObjects);

        for (GameObject o : m_ALLgameObjects)
        {
            remainingOBJs.remove(o);
            if(remainingOBJs.size() > 0)
            {
                //check collision with first and all remaining
                for (GameObject p : remainingOBJs) {
                    if( ((Plant)p).isMoveable || ((Plant)o).isMoveable)
                    {
                        if (o.getComponent("COLLISIONCOMPONENT") != null && p.getComponent("COLLISIONCOMPONENT") != null)
                        {
                            collisionComponent oc = (collisionComponent) (o.getComponent("COLLISIONCOMPONENT"));
                            collisionComponent pc = (collisionComponent) (p.getComponent("COLLISIONCOMPONENT"));

                            oc.collide(pc);
                            oc.collide(pc);
                            oc.collide(pc);

                        }
                        else
                        {
                            java.lang.System.out.println("Error in the collision system!");
                        }
                    }
                    else
                    {
                        //java.lang.System.out.println("not checking walls");
                    }
                }
            }
        }
    }
}
