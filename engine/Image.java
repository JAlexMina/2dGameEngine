package engine;

import engine.support.Vec2d;
import javafx.scene.paint.Color;

public class Image extends UiElement{

    public javafx.scene.image.Image tile;
    public Vec2d m_size;

    public Image(Vec2d pos, String st, String type, Color color, Vec2d size, javafx.scene.image.Image t) {
        super(pos, st, type, color);
        tile = t;
        m_size = size;
    }
}
