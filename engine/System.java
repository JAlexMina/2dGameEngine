package engine;

public interface System {

    //Need to check whether the implementing system "wants the GameObject"
    public void addGameObject(GameObject c);

    public void removeGameObject(GameObject c);
}
