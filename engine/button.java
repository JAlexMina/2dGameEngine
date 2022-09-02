package engine;

import engine.support.Vec2d;
import javafx.scene.paint.Color;

public class button extends UiElement{
    String text;
    public Vec2d s;
    public Rectangle rect;
    public Text t;
    public String m_stroke;
    public button(Vec2d pos, Vec2d size, String st, String type, Color color, String stroke) {
        super(pos, st, type, color);
        text = st;
        s = size;
        m_stroke = stroke;
        rect = new Rectangle(new Vec2d(25,100), "FILL", new Vec2d(78, 25), Color.WHITE);
        t = new Text(new Vec2d(10,6), "", 1, text, Color.BLACK);

    }

    public boolean isIN(Vec2d point, ViewPort vp)
    {
        return(point.x > m_pos.x * vp.m_g.getTransform().getMxx() && point.x < (m_pos.x * vp.m_g.getTransform().getMxx()) + (s.x * vp.m_g.getTransform().getMxx())
                && point.y > m_pos.y * vp.m_g.getTransform().getMyy() && point.y < (m_pos.y * vp.m_g.getTransform().getMyy()) + (s.y * vp.m_g.getTransform().getMyy()));
    }
}
