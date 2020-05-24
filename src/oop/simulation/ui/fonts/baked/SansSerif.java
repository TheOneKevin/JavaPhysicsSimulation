package oop.simulation.ui.fonts.baked;

import greenfoot.Font;
import oop.simulation.ui.fonts.BakedFontInfo;
/**
 * This is the font that can be applied onto the text
 *
 * @author Mustafa
 */

public class SansSerif
{
    private static int[] plainWidths = new int[]
        {
            0, 11, 11, 11, 11, 11, 11, 11, 11, 0, 0, 11, 13, 0, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 4, 5, 5, 8, 8, 13, 10, 3, 5, 5, 6, 9, 4, 5, 4, 4, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 4, 4, 9, 9, 9, 8, 15, 9, 10, 11, 11, 10, 9, 11, 10, 3, 7, 10, 8, 11, 10, 12, 10, 12, 11, 10, 9, 10, 9, 15, 9, 9, 8, 4, 4, 4, 5, 8, 5, 8, 8, 8, 8, 8, 4, 8, 8, 3, 3, 7, 3, 13, 8, 8, 8, 8, 5, 8, 4, 8, 7, 11, 7, 7, 8, 5, 3, 5, 9, 8, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 4, 5, 8, 8, 8, 8, 3, 8, 5, 11, 5, 8, 9, 5, 11, 8, 6, 8, 5, 5, 5, 8, 8, 5, 5, 5, 5, 8, 13, 13, 13, 9, 9, 9, 9, 9, 9, 9, 15, 11, 10, 10, 10, 10, 3, 3, 3, 3, 11, 10, 12, 12, 12, 12, 12, 9, 12, 10, 10, 10, 10, 9, 10, 9, 8, 8, 8, 8, 8, 8, 13, 8, 8, 8, 8, 8, 3, 3, 3, 3, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 7, 8, 7
        };
    /**
     * This is the information needed for the font
     * @param pt                This is the plan text number
     * @param isBold            Whether or not the text is bold
     * @param isItalic          Whether or not it is italicized
     * @param ascent            The ascent value
     * @param descent           The decent value
     * @param height            The height value
     */
    public static BakedFontInfo getFontInfo(int pt, boolean isBold, boolean isItalic, int ascent, int descent, int height)
    {
        int[] w = new int[256];
        for(int i = 0; i < 256; i++)
            w[i] = (int) Math.round((pt/15.0) * plainWidths[i]); // Adjust to size
        return new BakedFontInfo(new Font("Sans Serif", isBold, isItalic, pt), w, height, ascent, descent);
    }
    /**
     * This is the information needed for the font
     * @param pt                This is the plan text number
     * @param ascent            The ascent value
     * @param descent           The decent value
     * @param height            The height value
     */
    public static BakedFontInfo getFontInfoPlain(int pt, int ascent, int descent, int height)
    {
        return getFontInfo(pt, false, false, ascent, descent, height);
    }

    /**
     * This is the information needed for the font
     * @param pt                This is the plan text number
     * @param ascent            The ascent value
     * @param descent           The decent value
     * @param height            The height value
     */
    public static BakedFontInfo getFontInfoItalic(int pt, int ascent, int descent, int height)
    {
        return getFontInfo(pt, false, true, ascent, descent, height);
    }
}
