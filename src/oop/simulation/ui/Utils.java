package oop.simulation.ui;

import greenfoot.Color;
import greenfoot.GreenfootImage;
import oop.simulation.ui.fonts.BakedFontInfo;

/**
 * Draws the UI onto the screen
 *
 * @author kevin
 */
public class Utils
{
    /**
     * This draws the string onto the the world
     * @param g             The specified greenfoot image
     * @param s             The specified text
     * @param x             The x-coordinate
     * @param y             The y-coordinate
     * @param f             The font information
     */
    public static void drawString(GreenfootImage g, String s, int x, int y, BakedFontInfo f)
    {
        g.setFont(f.Font.get());
        g.drawString(s, x, y - f.Descent.get());
    }

    /**
     * Places the pixels onto the screen
     * @param g             The specified greenfoot image
     * @param x             The x-coordinate
     * @param y             The y-coordinate
     * @param c             The color
     */
    public static void putPixel(GreenfootImage g, int x, int y, Color c)
    {
        if (x >= g.getWidth() || y >= g.getHeight() || x < 0 || y < 0)
            return;
        g.setColorAt(x, y, c);
    }
}
