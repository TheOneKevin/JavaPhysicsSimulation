package oop.simulation.components;

import greenfoot.Color;
import greenfoot.GreenfootImage;
import oop.simulation.GameObject;
import oop.simulation.IComponent;

public class UiOverlayComponent implements IComponent
{
    private static final Color TRANSPARENT = new Color(0, 0, 0, 0);
    private GreenfootImage texture;

    public UiOverlayComponent(int width, int height)
    {
        texture = new GreenfootImage(width, height);
    }

    @Override
    public boolean isUnique()
    {
        return false;
    }

    @Override
    public void update(GameObject g)
    {
    }

    public GreenfootImage getTexture() { return texture; }

    public void clear()
    {
        texture.setColor(TRANSPARENT);
        texture.clear();
        texture.fill();
    }
}
