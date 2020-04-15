package oop.simulation.objects;

import greenfoot.Color;
import greenfoot.GreenfootImage;
import oop.simulation.Camera;

/**
 * Example Camera implementation
 */
public class DummyCamera extends Camera
{
    /**
     * Default constructor of GameObject
     *
     * @param name Name of GameObject
     */
    public DummyCamera(String name)
    {
        super(name);
    }

    @Override
    public void renderToBuffer(GreenfootImage g)
    {
        // Be sure to clear the buffer
        g.clear();
        // Or fill it!
        g.setColor(Color.BLACK);
        g.fill();

        // Before you start drawing...
        g.setColor(Color.GREEN);
        g.fillRect(125, 125, 50, 50);

        // Where's the origin of the rectangle we drew?
        // Let's find out!
        g.setColor(Color.RED);
        g.drawOval(125, 125, 5, 5);
    }
}
