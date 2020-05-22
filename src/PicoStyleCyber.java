import greenfoot.Color;
import oop.simulation.ui.fonts.BakedFontInfo;
import oop.simulation.ui.fonts.baked.CourierNew;
import oop.simulation.ui.PicoUI;

public class PicoStyleCyber implements PicoUI.PicoStyle
{
    private final Color DEFAULT_BORDER_COLOR_NORMAL     = new Color(47,116,134);
    private final Color DEFAULT_BASE_COLOR_NORMAL       = new Color(2,70,88);
    private final Color DEFAULT_TEXT_COLOR_NORMAL       = new Color(81,191,211);
    private final Color DEFAULT_BORDER_COLOR_FOCUSED    = new Color(130,205,224);
    private final Color DEFAULT_BASE_COLOR_FOCUSED      = new Color(50,153,180);
    private final Color DEFAULT_TEXT_COLOR_FOCUSED      = new Color(182,225,234);
    private final Color DEFAULT_BORDER_COLOR_PRESSED    = new Color(235,118,48);
    private final Color DEFAULT_BASE_COLOR_PRESSED      = new Color(255,188,81);
    private final Color DEFAULT_TEXT_COLOR_PRESSED      = new Color(216,111,54);
    private final Color DEFAULT_BORDER_COLOR_DISABLED   = new Color(19,75,90);
    private final Color DEFAULT_BASE_COLOR_DISABLED     = new Color(2,49,61);
    private final Color DEFAULT_TEXT_COLOR_DISABLED     = new Color(23,80,95);
    private final Color DEFAULT_BACKGROUND_COLOR        = new Color(0,34,43);
    private final Color TEXTBOX_COLOR_SELECTED_FG       = new Color(131,154,255);
    private final Color TEXTBOX_COLOR_SELECTED_BG       = new Color(189, 230, 255, 87);

    public Color getBorderColorNormal()   { return DEFAULT_BORDER_COLOR_NORMAL  ; }
    public Color getBaseColorNormal()     { return DEFAULT_BASE_COLOR_NORMAL    ; }
    public Color getTextColorNormal()     { return DEFAULT_TEXT_COLOR_NORMAL    ; }
    public Color getBorderColorFocused()  { return DEFAULT_BORDER_COLOR_FOCUSED ; }
    public Color getBaseColorFocused()    { return DEFAULT_BASE_COLOR_FOCUSED   ; }
    public Color getTextColorFocused()    { return DEFAULT_TEXT_COLOR_FOCUSED   ; }
    public Color getBorderColorPressed()  { return DEFAULT_BORDER_COLOR_PRESSED ; }
    public Color getBaseColorPressed()    { return DEFAULT_BASE_COLOR_PRESSED   ; }
    public Color getTextColorPressed()    { return DEFAULT_TEXT_COLOR_PRESSED   ; }
    public Color getBorderColorDisabled() { return DEFAULT_BORDER_COLOR_DISABLED; }
    public Color getBaseColorDisabled()   { return DEFAULT_BASE_COLOR_DISABLED  ; }
    public Color getTextColorDisabled()   { return DEFAULT_TEXT_COLOR_DISABLED  ; }
    public Color getBackgroundDefault()   { return DEFAULT_BACKGROUND_COLOR     ; }
    public Color getTextboxSelectedFg()   { return TEXTBOX_COLOR_SELECTED_FG    ; }
    public Color getTextboxSelectedBg()   { return TEXTBOX_COLOR_SELECTED_BG    ; }

    public PicoUI.ItemAlign getTextAlignment()   { return PicoUI.ItemAlign.CENTER ; }

    public int getBorderWidth()           { return 1                ; }
    public int getTextSize()              { return 15               ; }
    public int getTextSpacing()           { return 5                ; }
    public int getButtonBorderWidth()     { return 1                ; }

    public BakedFontInfo getMetrics()     { return CourierNew.getFontInfoPlain(15, 13, 0, 18); }

    private PicoStyleCyber() { }
    private static PicoUI.PicoStyle instance = null;
    public static PicoUI.PicoStyle getInstance()
    {
        return instance == null ? instance = new PicoStyleCyber() : instance;
    }
}