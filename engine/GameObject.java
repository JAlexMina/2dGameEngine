package engine;


import engine.support.Vec2d;
import javafx.scene.canvas.GraphicsContext;

public interface GameObject {
    // ArrayList<Component> m_components = new ArrayList<Component>();
    // TransformComponent transComp = new TransformComponent();

    public void addComponent(Component c);

    public void removeComponent(Component c);

    public Component getComponent(String tag);

    public TransformComponent getTransformComponent();

    public int getZ();

    public boolean getLanded();

    public void setTransformComponent(TransformComponent trans);

    public void tick(long t);

    public void lateTick();

    public void draw(GraphicsContext g);

    public boolean isIn(Vec2d point, ViewPort vp);

    public boolean getisDragged();

    public void setisDragged(Boolean b);

    public int getType();
    public int compareTo(GameObject comepareObject);


}
