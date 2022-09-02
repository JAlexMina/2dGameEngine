package engine;

import engine.support.Vec2d;
import javafx.scene.paint.Color;
import javafx.scene.paint.*;

public class Line extends UiElement
{
    Vec2d m_start;
    Vec2d m_end;
    int thick;
    public Line(Vec2d start, Vec2d end, int thickness, String st)
    {
        super(new Vec2d(0,0), st, "LINE", Color.BLACK);
        m_start = start;
        m_end = end;
        thick = thickness;
    }

    public Line(Vec2d start, Vec2d end, int thickness, String st, Color color)
    {
        super(new Vec2d(0,0), st, "LINE", color);
        m_start = start;
        m_end = end;
        thick = thickness;
    }
}
