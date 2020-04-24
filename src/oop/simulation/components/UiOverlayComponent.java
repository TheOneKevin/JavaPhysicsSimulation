package oop.simulation.components;

import greenfoot.Color;
import greenfoot.GreenfootImage;

public class UiOverlayComponent extends BasicComponent
{
    private static final Color TRANSPARENT = new Color(0, 0, 0, 0);
    private GreenfootImage texture;

    public UiOverlayComponent(int width, int height)
    {
        texture = new GreenfootImage(width, height);
    }

    public GreenfootImage getTexture() { return texture; }

    public void clear()
    {
        texture.setColor(TRANSPARENT);
        texture.clear();
    }
}
