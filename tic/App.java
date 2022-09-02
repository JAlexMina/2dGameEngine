package tic;

import engine.*;
import engine.support.Vec2d;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.System;
import java.util.ArrayList;


/**
 * This is your Tic-Tac-Toe top-level, App class.
 * This class will contain every other object in your game.
 */
public class App extends Application
{

  xmlReader xmlR;
  Alc alcScreen;
  TitleScreen titleScreen;
  int m_seed = 0;
  Screen currentScreen;

  GraphicsContext m_g;
  boolean once = true;

  public App(String title) throws FileNotFoundException {
    super(title);

    xmlR = new xmlReader();
    xmlR.readXML("./file save/save.xml");
    Vec2d pos = new Vec2d(100, 100);//new Vec2d(Double.parseDouble(xmlR.xPos.get(0)), Double.parseDouble((xmlR.yPos.get(0))));
    //System.out.println(pos);


    titleScreen = new TitleScreen(currentStageSize);

    alcScreen = new Alc(currentStageSize, pos);
    currentScreen = alcScreen;

  }





  public App(String title, Vec2d windowSize, boolean debugMode, boolean fullscreen)
  {
    super(title, windowSize, debugMode, fullscreen);
  }


  /**
   * Called periodically and used to update the state of your game.
   * @param nanosSincePreviousTick	approximate number of nanoseconds since the previous call
   */
  @Override
  protected void onTick(long nanosSincePreviousTick) {



    currentScreen.onTick(nanosSincePreviousTick);

  }

  /**
   * Called after onTick().
   */
  @Override
  protected void onLateTick() {
    // Don't worry about this method until you need it. (It'll be covered in class.)
    currentScreen.onLateTick();
  }

  /**
   *  Called periodically and meant to draw graphical components.
   * @param g		a {@link GraphicsContext} object used for drawing.
   */
  @Override
  protected void onDraw(GraphicsContext g) {

    if(once)
    {
      titleScreen.setUp(g);
    }


    m_g = g;

    if(once && currentScreen == alcScreen)
    {
      alcScreen.setUp(g);
      once = false;
    }
    else
    {
      currentScreen.onDraw(g);
    }

  }

  /**
   * Called when a key is typed.
   * @param e		an FX {@link KeyEvent} representing the input event.
   */
  @Override
  protected void onKeyTyped(KeyEvent e) {

    currentScreen.onKeyTyped(e);

  }

  /**
   * Called when a key is pressed.
   * @param e		an FX {@link KeyEvent} representing the input event.
   */
  @Override
  protected void onKeyPressed(KeyEvent e)
  {

    if(e.getCode() == KeyCode.ESCAPE)
    {
      if(currentScreen == alcScreen)
      {
        ArrayList<GameObject> save = currentScreen.Shutdown();
        for (GameObject o : save) {
          xmlR.xPos.add(String.valueOf(o.getTransformComponent().pos.x));
          xmlR.yPos.add(String.valueOf(o.getTransformComponent().pos.y));
        }

        xmlR.saveToXML("./file save/save.xml");
      }
      java.lang.System.exit(0);
    }

    currentScreen.onKeyPressed(e);
  }

  /**
   * Called when a key is released.
   * @param e		an FX {@link KeyEvent} representing the input event.
   */
  @Override
  protected void onKeyReleased(KeyEvent e) {

    currentScreen.onKeyReleased(e);
  }

  /**
   * Called when the mouse is clicked.
   * @param e		an FX {@link MouseEvent} representing the input event.
   */
  @Override
  protected void onMouseClicked(MouseEvent e){

    Screen s;
    if(currentScreen == alcScreen)
    {
      s = titleScreen;
    }
    else
    {
      s = alcScreen;
    }

    boolean switchScreen = currentScreen.onMouseClicked(e, s);

    if(switchScreen)
    {
      if(currentScreen == alcScreen)
      {
        currentScreen = titleScreen;
      }
      else
      {

        currentScreen = alcScreen;
      }
      currentScreen.onResize(currentStageSize);
    }
  }

  /**
   * Called when the mouse is pressed.
   * @param e		an FX {@link MouseEvent} representing the input event.
   */
  @Override
  protected void onMousePressed(MouseEvent e) {

    currentScreen.onMousePressed(e);
  }

  /**
   * Called when the mouse is released.
   * @param e		an FX {@link MouseEvent} representing the input event.
   */
  @Override
  protected void onMouseReleased(MouseEvent e) {

    currentScreen.onMouseReleased(e);
  }

  /**
   * Called when the mouse is dragged.
   * @param e		an FX {@link MouseEvent} representing the input event.
   */
  @Override
  protected void onMouseDragged(MouseEvent e) {

    currentScreen.onMouseDragged(e, m_g);
  }

  /**
   * Called when the mouse is moved.
   * @param e		an FX {@link MouseEvent} representing the input event.
   */
  @Override
  protected void onMouseMoved(MouseEvent e) {

    currentScreen.onMouseMoved(e);
  }

  /**
   * Called when the mouse wheel is moved.
   * @param e		an FX {@link ScrollEvent} representing the input event.
   */
  @Override
  protected void onMouseWheelMoved(ScrollEvent e) {

    currentScreen.onMouseWheelMoved(e);
  }

  /**
   * Called when the window's focus is changed.
   * @param newVal	a boolean representing the new focus state
   */
  @Override
  protected void onFocusChanged(boolean newVal) {

    currentScreen.onFocusChanged(newVal);
  }

  /**
   * Called when the window is resized.
   * @param newSize	the new size of the drawing area.
   */
  @Override
  protected void onResize(Vec2d newSize) {


    currentScreen.onResize(newSize);
  }

  /**
   * Called when the app is shutdown.
   */
  @Override
  protected void onShutdown() {

    if(currentScreen == alcScreen)
    {
      ArrayList<GameObject> save = currentScreen.Shutdown();
      System.out.println(save.size());

      int i = 0;
      for (GameObject o : save) {
        xmlR.m_objects_out.add(String.valueOf(o.getType()));
        xmlR.xPos_out.add(String.valueOf(o.getTransformComponent().pos.x));
        xmlR.yPos_out.add(String.valueOf(o.getTransformComponent().pos.y));
        i += 1;
      }
      xmlR.saveToXML("./file save/save.xml");
    }



  }

  /**
   * Called when the app is starting up.s
   */
  @Override
  protected void onStartup() {

      alcScreen.onStartup();
      alcScreen.passXML(xmlR);


  }
}



