package engine;

import java.util.ArrayList;

public class GameWorld {

    ArrayList<GameObject> m_gameObjects = new ArrayList<GameObject>();
    ArrayList<System> m_systems = new ArrayList<System>();


    public GameWorld()
    {

    }

    public void addGameObject(GameObject O)
    {
        m_gameObjects.add(O);

        for(int i = 0; i < m_systems.size(); i += 1)
        {
            //This will add objects to the system "if the system wants them"
            m_systems.get(i).addGameObject(O);
        }
    }

    public void removeGameObject(GameObject O)
    {
        m_gameObjects.remove(O);

        for(int i = 0; i < m_systems.size(); i += 1)
        {
            m_systems.get(i).removeGameObject(O);
        }
    }

    public void addSystem(System s)
    {
        m_systems.add(s);
    }

    public void removeSytem(System s)
    {
        m_systems.remove(s);
    }




}
