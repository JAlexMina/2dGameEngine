package engine;

import engine.support.Vec2d;
import javafx.scene.paint.Color;
import javafx.scene.paint.*;

public class Rectangle extends UiElement
{
    public Vec2d m_wh;
    public Rectangle(Vec2d pos, String st, Vec2d wh)
    {
        super(pos, st, "RECTANGLE", Color.BLACK);
        m_wh = wh;
    }

    public Rectangle(Vec2d pos, String st, Vec2d wh, Color color)
    {
        super(pos, st, "RECTANGLE", color);
        m_wh = wh;
    }


}
