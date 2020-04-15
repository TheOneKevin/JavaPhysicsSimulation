package oop.simulation;

import greenfoot.GreenfootImage;

/**
 * Simple Camera abstract class
 *
 * @author Kevin Dai
 */
public abstract class Camera extends GameObject
{
    /**
     * Default constructor of GameObject
     * @param name Name of GameObject
     */
    public Camera(String name)
    {
        super(name);
    }

    /**
     * Draws current scene to buffer
     * @param g Buffer to draw to
     */
    public abstract void renderToBuffer(GreenfootImage g);
}
