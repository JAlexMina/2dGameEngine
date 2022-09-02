package engine;

import engine.support.Vec2d;

public class sprite {
    public Vec2d currentS;
    public Vec2d index;
    public boolean pause = true;
    public boolean is_static = true;

    double a = 0;
    double b = 1;
    double c = 2;

    double n = 0;

    public sprite(Vec2d v, boolean s) {
        index = v; currentS = new Vec2d(v.x, v.y);
        is_static = s;
    }

}
