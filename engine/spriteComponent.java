package engine;

import engine.support.Vec2d;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public class spriteComponent implements Component{
    public Vec2d currentSprite;
    public ResourceHandler r;
    GameObject owner;
    int tickCount = 0;
    public Vec2d wh;
    public sprite m_sprite;
    public double scale = 1.0;
    String tag = "";

    public Vec2d outlineStart;
    public Vec2d outlineWH;


    public spriteComponent(ResourceHandler res, GameObject o, sprite s, String t)
    {
        r = res;
        wh = new Vec2d(r.scaledWidth, r.scaledHeight);
        if(!s.is_static)
        {
            s.index = new Vec2d(s.index.x, s.index.y * 4);
            s.currentS = new Vec2d(s.currentS.x, s.currentS.y * 4);
            currentSprite = new Vec2d(s.index.x*3, s.index.y+2);
        }
        else
        {
            s.index = new Vec2d(s.index.x, s.index.y);
            currentSprite = new Vec2d(s.index.x, s.index.y);
        }
        m_sprite = s;


        owner = o;
        tag = t;
    }

    @Override
    public void tick(long nanosSinceLastTick) {

        if(!m_sprite.pause && !m_sprite.is_static)
        {
            if (tickCount < 0) {
                if (currentSprite.x == (m_sprite.index.x * 3) + m_sprite.c) {
                    currentSprite = new Vec2d((m_sprite.index.x * 3) + m_sprite.b, (m_sprite.index.y));
                    m_sprite.n = (m_sprite.index.x * 3) + m_sprite.a;
                } else if (currentSprite.x == (m_sprite.index.x * 3) + m_sprite.a) {
                    currentSprite = new Vec2d((m_sprite.index.x * 3) + m_sprite.b, (m_sprite.index.y));
                    m_sprite.n = (m_sprite.index.x * 3) + m_sprite.c;
                } else {
                    currentSprite = new Vec2d(m_sprite.n, (m_sprite.index.y));
                }
                tickCount = 2;

            } else {
                tickCount -= 1;
            }
        }
    }

    @Override
    public void lateTick() {

    }

    @Override
    public String getTag() {
        return tag;
    }

    @Override
    public void setTag(String t) {
        tag = t;
    }

    @Override
    public void draw(GraphicsContext g)
    {
            g.setFill(Color.TRANSPARENT);
            g.drawImage(r.index(currentSprite), owner.getTransformComponent().pos.x, owner.getTransformComponent().pos.y, wh.x * scale, wh.y * scale);
    }
}
