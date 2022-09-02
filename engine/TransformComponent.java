package engine;
import engine.support.Vec2d;

public class TransformComponent {

    public Vec2d pos;
    public Vec2d velocity;
    public Vec2d acc;

    public TransformComponent(Vec2d p)
    {
        pos = p;
        velocity = new Vec2d(0,0);
        acc = new Vec2d(0,0);
    }



}
