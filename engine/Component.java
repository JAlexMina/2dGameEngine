package engine;

import javafx.scene.canvas.GraphicsContext;

public interface Component {

    public void tick(long nanosSinceLastTick);

    public void lateTick();

    public String getTag();

    public void setTag(String t);

    public void draw(GraphicsContext g);

}
