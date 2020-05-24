package oop.simulation.components;

import greenfoot.Color;
import greenfoot.Font;
import greenfoot.GreenfootImage;
import java.awt.*;
import java.awt.image.*;

/**
 * This is what gives the game user interaction to the simulation
 * @author Kevin Dai
 * @author Mustafa M.
 * @version May 2020
 */
public class UiOverlayComponent extends BasicComponent
{
    private static final Color TRANSPARENT = new Color(0, 0, 0, 0);
    private GreenfootImage texture;

    /**
     * This is the constructor for the user interface
     * @param width         This is the width of the panel
     * @param height        This is the height of the panel
     */
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


    /**
     * This clears the panel so that it can be re written
     * @param xStart        Determines the x coordinate at the start
     * @param yStart        Determines the x coordinate at the start
     * @param width         Determines the width
     * @param height        Determines the height
     */
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

    /**
     * This is what writes the text onto the panel
     * @param str           This is the text written
     * @param x             The x coordinate
     * @param y             The y coordinate
     * @param color         The color of the panel
     * @param size          The size of the panel
     * @param bold          Whether the text is in bold or not
     * @param italic        Whether the text is italicized or not
     */
    public void writeString(String str, int x, int y, Color color, int size, boolean bold, boolean italic)
    {
        Font f = new Font(bold, italic, size);
        texture.setFont(f);
        texture.setColor(color);
        texture.drawString(str, x, y);
    }

    /**
     * This creates the buttons for the user to interact with
     * @param str           This is the text in the button
     * @param x             The x coordinate
     * @param y             The y coordinate
     * @param width         The width of the panel
     * @param height        The height of the panel
     * @param buttonColor   The color of the button
     * @param filled        Whether or not the panel is filled with color
     * @param fontColor     The color of the font
     * @param fontSize      The size of the font
     * @param fontBold      Whether or not the text is bold
     * @param fontItalic    Whether or not the text is italicized
     */
    public void createButton(String str, int x, int y, int width, int height, Color buttonColor, boolean filled,
                             Color fontColor, int fontSize, boolean fontBold, boolean fontItalic)
    {
        //first makes the box for the button
        texture.setColor(buttonColor);
        if(filled)
            texture.fillRect(x,y,width,height);
        else
            texture.drawRect(x,y,width,height);

        /**
         * creates the font and writes on top of the button, aligns roughly at the center
         * @param fontBold          Determines whether the font is bold
         * @param fontItalic        Determines whether the font is italicised
         */
        Font f = new Font(fontBold, fontItalic, fontSize);
        texture.setFont(f);
        texture.setColor(fontColor);
        texture.drawString(str, (int)(x + (double)width / 2 - (double)str.length() * fontSize / 4),
                                (int)(y + (double)height / 2 + (double)fontSize / 2));
    }
}
