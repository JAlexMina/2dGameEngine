package engine;

import engine.support.Vec2d;
import java.util.ArrayList;
import javafx.scene.paint.*;

public class UiElement
{
    public Vec2d m_pos;
    String m_type;
    String m_stroke;
    Color m_paint;


    ArrayList<UiElement> m_toDraw = new ArrayList<UiElement>();

    public UiElement(Vec2d pos, String st, String type, Color color)
    {
        m_pos = pos;
        m_stroke = st;
        m_type = type;
        m_paint = color;

    }

    public void addElement(UiElement a)
    {
        m_toDraw.add(a);

    }

    public void removeElement(UiElement a)
    {
        m_toDraw.remove(a);

    }

    public void setColor(Color c)
    {
        m_paint = c;
    }

    public Color getColor()
    {
        return m_paint;
    }
}
