package tic;

import engine.Component;
import engine.GameObject;
import engine.Rectangle;
import engine.TransformComponent;
import engine.support.Vec2d;
import javafx.scene.canvas.GraphicsContext;


public class bullet extends Plant{
    Vec2d m_direction;
    public bullet(Vec2d pos, boolean m, Vec2d dir) {
        super(pos, m);
        m_direction = dir;
    }
}
