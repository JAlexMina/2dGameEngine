package engine;
import javafx.scene.canvas.GraphicsContext;
import java.util.Comparator;
import java.util.TreeSet;




class gameObjComp implements Comparator<GameObject>{

    @Override
    public int compare(GameObject e1, GameObject e2) {
        if(e1.getZ() < e2.getZ()){
            return 1;
        } else {
            return -1;
        }
    }
}



public class DrawSystem implements System {

    TreeSet<GameObject> m_gameObjects = new TreeSet<GameObject>(new gameObjComp());

    public DrawSystem()
    {

    }

    @Override
    public void addGameObject(GameObject O)
    {
        if(O.getComponent("Drawable") != null)
        {
            m_gameObjects.add(O);
        }
    }

    @Override
    public void removeGameObject(GameObject O)
    {
        m_gameObjects.remove(O);
    }

    void onDraw(GraphicsContext g)
    {
        for (GameObject O: m_gameObjects)
        {
            O.draw(g);
        }
    }

}
