package engine;

import engine.support.Vec2d;
import javafx.scene.paint.*;

public class Text extends UiElement
{
    int m_thick;
    String m_contents;
    public Text(Vec2d pos, String st, int thickness, String contents, Color color)
    {
        super(pos, st, "TEXT", color);
        m_contents = contents;
        m_thick = thickness;
    }

    public Text(Vec2d pos, String st, int thickness, String contents)
    {
        super(pos, st, "TEXT", Color.BLACK);
        m_contents = contents;
        m_thick = thickness;
    }
}
