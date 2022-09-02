package engine;

import engine.support.Vec2d;
import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;

import javax.sql.rowset.spi.XmlReader;
import java.util.ArrayList;

public class Screen {



    public Vec2d m_currentStageSize;
    public LightingSystem m_lightSystem;
    public ArrayList<UiElement> toDraw = new ArrayList<UiElement>();
    public ArrayList<GameObject> toDrawObj = new ArrayList<GameObject>();
    protected Vec2d pos;
    public CollisionSystem m_collisionSystem;
    WorldBuilder w;
    public xmlReader xmlR;





    public Screen()
    {
        w = new WorldBuilder(toDrawObj);
    }

    /**
     * Called periodically and used to update the state of your game.
     * @param nanosSincePreviousTick	approximate number of nanoseconds since the previous call
     */
    public void onTick(long nanosSincePreviousTick) {




//        for (int i = 0; i < toDrawObj.size(); i++)
//        {
//            toDrawObj.get(i).tick(nanosSincePreviousTick);
//        }


    }

    public void passXML(xmlReader xmlr)
    {
        xmlR = xmlr;
    }

    /**
     * Called after onTick().
     */
    public void onLateTick() {
        // Don't worry about this method until you need it. (It'll be covered in class.)
    }

    /**
     *  Called periodically and meant to draw graphical components.
     * @param g		a {@link GraphicsContext} object used for drawing.
     */
    public void onDraw(GraphicsContext g)
    {



        if(m_lightSystem != null)
        {
            m_lightSystem.draw(g, toDrawObj);

            reccursiveOnDraw(g, toDraw, new Vec2d(0, 0));
        }

        //toggle on and off seeing the collision system and world builder
        //m_collisionSystem.onDraw(g);
        //w.draw(g);
    }

    public void reccursiveOnDraw(GraphicsContext g, ArrayList<UiElement> l, Vec2d start)
    {

        for (int i = 0; i < l.size(); i++)
        {
            Vec2d local_start = l.get(i).m_pos;
            g.setStroke(l.get(i).m_paint);
            g.setFill(l.get(i).m_paint);
            switch (l.get(i).m_type)
            {

                case "RECTANGLE":
                    if (l.get(i).m_stroke == "FILL")
                    {
                        Vec2d wh = ((Rectangle)l.get(i)).m_wh;
                        g.fillRect(start.x + local_start.x, start.y + local_start.y, wh.x, wh.y);
                    }
                    else
                    {
                        Vec2d wh = ((Rectangle)l.get(i)).m_wh;
                        g.strokeRect(start.x + local_start.x, start.y + local_start.y, wh.x, wh.y);
                    }
                    break;

                case "CIRCLE":
                    if (l.get(i).m_stroke == "FILL")
                    {
                        Vec2d wh = ((Circle)l.get(i)).m_wh;
                        g.fillOval(start.x + local_start.x, start.y + local_start.y, wh.x, wh.y);
                    }
                    else
                    {
                        Vec2d wh = ((Circle)l.get(i)).m_wh;
                        g.strokeOval(start.x + local_start.x, start.y + local_start.y, wh.x, wh.y);
                    }
                    break;
                case "LINE":
                    Line ll = ((Line)(l.get(i)));
                    double tmp = g.getLineWidth();
                    g.setLineWidth(ll.thick);
                    g.strokeLine(start.x + ll.m_start.x, start.y +  ll.m_start.y, start.x + ll.m_end.x, start.y + ll.m_end.y);
                    g.setLineWidth(tmp);
                    break;
                case "TEXT":
                    Text t = (Text)(l.get(i));
                    double tmp2 = g.getLineWidth();
                    g.setLineWidth(t.m_thick);
                    g.strokeText(t.m_contents, start.x + local_start.x, start.y + local_start.y);
                    g.setTextBaseline(VPos.TOP);
                    g.setLineWidth(tmp2);
                    break;
                case "IMAGE":
                    Image im = (Image)(l.get(i));
                    g.drawImage(im.tile, im.m_pos.x, im.m_pos.y, im.m_size.y, im.m_size.y);
                    break;
                case "BUTTON":
                    button b = ((button)l.get(i));
                    Vec2d wh = b.rect.m_wh;
                    g.setStroke(b.m_paint);
                    g.setFill(b.m_paint);
                    if (b.m_stroke == "FILL")
                    {
                        g.fillRect(start.x + local_start.x, start.y + local_start.y, wh.x, wh.y);
                    }
                    else
                    {
                        g.strokeRect(start.x + local_start.x, start.y + local_start.y, wh.x, wh.y);
                    }

                    Text t2 = ((button)(l.get(i))).t;
                    g.setStroke(t2.m_paint);
                    g.setFill(t2.m_paint);
                    double tmp3 = g.getLineWidth();
                    g.setLineWidth(t2.m_thick);
                    g.strokeText(t2.m_contents, start.x + local_start.x + t2.m_pos.x, start.y + local_start.y + t2.m_pos.y);
                    g.setTextBaseline(VPos.TOP);
                    g.setLineWidth(tmp3);
                    break;
                default:



            }


            reccursiveOnDraw(g, l.get(i).m_toDraw, new Vec2d(start.x + local_start.x, start.y + local_start.y));
        }

    }

    /**
     * Called when a key is typed.
     * @param e		an FX {@link KeyEvent} representing the input event.
     */
    public void onKeyTyped(KeyEvent e) {

    }

    /**
     * Called when a key is pressed.
     * @param e		an FX {@link KeyEvent} representing the input event.
     */
    public void onKeyPressed(KeyEvent e) {

    }

    /**
     * Called when a key is released.
     * @param e		an FX {@link KeyEvent} representing the input event.
     */
    public void onKeyReleased(KeyEvent e) {

    }

    /**
     * Called when the mouse is clicked.
     * @param e		an FX {@link MouseEvent} representing the input event.
     */
    public boolean onMouseClicked(MouseEvent e, Screen s) {

        return false;
    }

    /**
     * Called when the mouse is pressed.
     * @param e		an FX {@link MouseEvent} representing the input event.
     */
    public void onMousePressed(MouseEvent e) {

    }

    /**
     * Called when the mouse is released.
     * @param e		an FX {@link MouseEvent} representing the input event.
     */
    public void onMouseReleased(MouseEvent e) {

    }

    /**
     * Called when the mouse is dragged.
     * @param e		an FX {@link MouseEvent} representing the input event.
     */
    public void onMouseDragged(MouseEvent e, GraphicsContext g) {

    }

    /**
     * Called when the mouse is moved.
     * @param e		an FX {@link MouseEvent} representing the input event.
     */
    public void onMouseMoved(MouseEvent e) {

    }

    /**
     * Called when the mouse wheel is moved.
     * @param e		an FX {@link ScrollEvent} representing the input event.
     */
    public void onMouseWheelMoved(ScrollEvent e) {

    }

    /**
     * Called when the window's focus is changed.
     * @param newVal	a boolean representing the new focus state
     */
    public void onFocusChanged(boolean newVal) {

    }

    /**
     * Called when the window is resized.
     * @param newSize	the new size of the drawing area.
     */
    public void onResize(Vec2d newSize) {


    }

    /**
     * Called when the app is shutdown.
     */
    public ArrayList<GameObject> Shutdown() {
        return null;
    }

    /**
     * Called when the app is starting up.s
     */
    public void onStartup() {

    }


}


