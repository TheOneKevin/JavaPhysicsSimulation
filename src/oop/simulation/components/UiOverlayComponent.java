package oop.simulation.components;

import greenfoot.Color;
import greenfoot.Font;
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

    public void clearPartially(int xStart, int yStart, int width, int height)
    {
        texture.setColor(TRANSPARENT);
        texture.fillRect(xStart, yStart, width, height);
    }

    public void writeString(String str, int x, int y, Color color, int size, boolean bold, boolean italic)
    {
        Font f = new Font(bold, italic, size);
        texture.setFont(f);
        texture.setColor(color);
        texture.drawString(str, x, y);
    }
}
