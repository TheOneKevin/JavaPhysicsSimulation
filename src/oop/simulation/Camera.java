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

    public abstract void renderToBuffer(GreenfootImage g);
}
