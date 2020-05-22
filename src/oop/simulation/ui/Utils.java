package oop.simulation.ui;

import greenfoot.Color;
import greenfoot.GreenfootImage;
import oop.simulation.ui.fonts.BakedFontInfo;

public class Utils
{
    public static void drawString(GreenfootImage g, String s, int x, int y, BakedFontInfo f)
    {
        g.setFont(f.Font.get());
        g.drawString(s, x, y - f.Descent.get());
    }

    public static void putPixel(GreenfootImage g, int x, int y, Color c)
    {
        if (x >= g.getWidth() || y >= g.getHeight() || x < 0 || y < 0)
            return;
        g.setColorAt(x, y, c);
    }
}
