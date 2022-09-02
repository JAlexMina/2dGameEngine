package engine;

import engine.support.Vec2d;
import javafx.scene.paint.Color;
import javafx.scene.paint.*;

public class Circle extends UiElement
{
    Vec2d m_wh;
    public Circle(Vec2d pos, String st, Vec2d wh)
    {
        super(pos, st, "CIRCLE", Color.BLACK);
        m_wh = wh;
    }

    public Circle(Vec2d pos, String st, Vec2d wh, Color color)
    {
        super(pos, st, "CIRCLE", color);
        m_wh = wh;
    }
}
