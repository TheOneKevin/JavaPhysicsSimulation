package oop.simulation.ui.fonts.baked;

import greenfoot.Font;
import oop.simulation.ui.fonts.BakedFontInfo;

public class CourierNew
{
    private static int[] plainWidths = new int[]
    {
        9, 9, 9, 9, 9, 9, 9, 9, 9, 0, 0, 9, 9, 0, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9
    };

    public static BakedFontInfo getFontInfo(int pt, boolean isBold, boolean isItalic, int ascent, int descent, int height)
    {
        int[] w = new int[256];
        for(int i = 0; i < 256; i++)
            w[i] = (int) Math.round((pt/15.0) * plainWidths[i]); // Adjust to size
        return new BakedFontInfo(new Font("Courier New", isBold, isItalic, pt), w, height, ascent, descent);
    }

    public static BakedFontInfo getFontInfoPlain(int pt, int ascent, int descent, int height)
    {
        return getFontInfo(pt, false, false, ascent, descent, height);
    }

    public static BakedFontInfo getFontInfoItalic(int pt, int ascent, int descent, int height)
    {
        return getFontInfo(pt, false, true, ascent, descent, height);
    }
}
