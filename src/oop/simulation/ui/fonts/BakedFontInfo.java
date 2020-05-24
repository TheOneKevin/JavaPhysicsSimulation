package oop.simulation.ui.fonts;

import greenfoot.Font;
import oop.simulation.beans.Property;
import oop.simulation.beans.Readonly;
/**
 * This is the font that can be applied onto the text
 *
 * @author Mustafa
 */
public class BakedFontInfo
{
    private Font f;
    private int height, ascent, descent;
    private int[] width;

    public final Readonly<Font> Font = Property.get(() -> f).readonly();
    public final Readonly<Integer> Height = Property.get(() -> height).readonly();
    public final Readonly<Integer> Ascent = Property.get(() -> ascent).readonly();
    public final Readonly<Integer> Descent = Property.get(() -> descent).readonly();

    /**
     * The constructor for the class
     * @param f             The specified font
     * @param width         The specified width
     * @param height        The specified height
     * @param ascent        The specified ascent
     * @param descent       The specified descent
     */
    public BakedFontInfo(Font f, int[] width, int height, int ascent, int descent)
    {
        this.f = f;
        this.width = width;
        this.height = height;
        this.ascent = ascent;
        this.descent = descent;
    }

    /**
     * This gets the width of the text
     * @param s             The specified text
     */
    public int getStringWidth(String s)
    {
        int w = 0;
        for(char c : s.toCharArray())
            w += width[c];
        return w;
    }

    /**
     * Returns the width of the character
     * @param c           The specified character
     */
    public int getCharWidth(char c)
    {
        if(c <= 255)
            return width[c];
        return -1;
    }

    public static int[] printFontWidths(java.awt.Font font)
    {
        var img = new java.awt.image.BufferedImage(100, 100, 1);
        var g = img.createGraphics();
        g.setFont(font);
        var w = g.getFontMetrics().getWidths();
        System.out.println(String.format("ascent=%d descent=%d height=%d", g.getFontMetrics().getAscent(), g.getFontMetrics().getDescent(), g.getFontMetrics().getHeight()));
        for(int i = 0; i < 256; i++)
            System.out.print(w[i] + ", ");
        System.out.println();
        return w;
    }
}
