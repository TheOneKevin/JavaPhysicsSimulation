package oop.simulation.components;

import greenfoot.Color;
import greenfoot.Font;
import greenfoot.GreenfootImage;
import java.awt.*;
import java.awt.image.*;

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

    // Works, but also clears outside UI too, I don't know why. Kevin fix dis if u can.
    // Not recommended for use as of now.
    public void clearPartially(int xStart, int yStart, int width, int height)
    {
        texture.setColor(TRANSPARENT);
        BufferedImage bufImg = texture.getAwtImage();
        Graphics2D g2d = bufImg.createGraphics();
        g2d.clearRect(xStart, yStart, width, height);
        GreenfootImage newTexture = new GreenfootImage(bufImg.getWidth(), bufImg.getHeight());
        BufferedImage gBufImg = newTexture.getAwtImage();
        Graphics2D graphics = (Graphics2D)gBufImg.getGraphics();
        graphics.drawImage(bufImg, null, 0, 0);
        texture = newTexture;
    }

    public void writeString(String str, int x, int y, Color color, int size, boolean bold, boolean italic)
    {
        Font f = new Font(bold, italic, size);
        texture.setFont(f);
        texture.setColor(color);
        texture.drawString(str, x, y);
    }

    //does not have a click function yet.
    public void createButton(String str, int x, int y, int width, int height, Color buttonColor, boolean filled,
                             Color fontColor, int fontSize, boolean fontBold, boolean fontItalic)
    {
        //first makes the box for the button
        texture.setColor(buttonColor);
        if(filled)
            texture.fillRect(x,y,width,height);
        else
            texture.drawRect(x,y,width,height);

        //creates the font and writes on top of the button, aligns roughly at the center
        Font f = new Font(fontBold, fontItalic, fontSize);
        texture.setFont(f);
        texture.setColor(fontColor);
        texture.drawString(str, (int)(x + (double)width / 2 - (double)str.length() * fontSize / 4),
                                (int)(y + (double)height / 2 + (double)fontSize / 2));
    }
}
