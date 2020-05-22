import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import oop.simulation.ui.PicoUI;
import oop.simulation.ui.RIconsUI;

/**
 * Write a description of class MyWorld here.
 * 
 * @author Kevin Dai
 */
public class MyWorldUI extends World
{
    /**
     * Constructor for objects of class MyWorld.
     */
    public MyWorldUI()
    {
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(600, 400, 1);

        // Create new UI context
        PicoUI ctx = new PicoUI(400, 200);
        this.addObject(ctx, 300, 200);
        setBackground(PicoUI.getDefaultStyle());

        /* Create a bunch of buttons for the lolz */
        var btn1 = ctx.new Button("button1", "File Open", RIconsUI.getIcon(RIconsUI.RIcons.RICON_FOLDER_FILE_OPEN));
        var btn2 = ctx.new Button("button2", "Click me!");
        var btn3 = ctx.new Button("button3", RIconsUI.getIcon(RIconsUI.RIcons.RICON_DEMON));

        btn2.AutoSizeHeight.set(false);
        btn2.Height.set(btn1.Height.get());

        /* Create a carousel "control" (made of smaller controls) */
        // Left button
        var lbt = ctx.new Button("button4", "", RIconsUI.getIcon(RIconsUI.RIcons.RICON_ARROW_LEFT_FILL));
        lbt.Margins.set(new PicoUI.Margins(5, 0, 5, -1));
        lbt.AutoSizeWidth.set(false);
        lbt.Width.set(17);
        lbt.onMouseClick.set((s, e) -> {
            var t = ctx.getControl("textbox1", PicoUI.TextField.class);
            t.Metadata.set((Integer) t.Metadata.get() - 1);
            t.Text.set(t.Metadata.get().toString());
        });

        // Text box
        var txt1 = ctx.new TextField("textbox1", 50, lbt.Height.get());
        txt1.Margins.set(new PicoUI.Margins(5, 3, 5, 3));
        txt1.Metadata.set(0);
        txt1.Text.set("0");

        // Right button
        var rbt = ctx.new Button("button5", "", RIconsUI.getIcon(RIconsUI.RIcons.RICON_ARROW_RIGHT_FILL));
        rbt.Margins.set(new PicoUI.Margins(5, 0, 5, -2));
        rbt.AutoSizeWidth.set(false);
        rbt.Width.set(17);
        rbt.onMouseClick.set((s, e) -> {
            var t = ctx.getControl("textbox1", PicoUI.TextField.class);
            t.Metadata.set((Integer) t.Metadata.get() + 1);
            t.Text.set(t.Metadata.get().toString());
        });

        /* Create a label-slider pair */
        var sld1 = ctx.new Slider("slider1", 100, 0d, 100d);
        var lbl1 = ctx.new Label("label1", "Hello, world!", null);
        // Sometimes events must be added after certain components are registered...
        sld1.onValueUpdate.set((s, e) -> lbl1.Text.set("Slider: " + e.intValue()));
        ctx.getControl("slider1", PicoUI.Slider.class).Value.set(50.0);

        /* Why not a PSA: I do not recommend using getControl() for everything. It can be messy to read sometimes. */
        ctx.new TextField("textbox2", 200);
        ctx.getControl("textbox2").Margins.set(new PicoUI.Margins(5, 3, 5, 3));
        ctx.getControl("textbox2", PicoUI.TextField.class).PromptText.set("Type something in me!");

        /* A label is a good way to end */
        var lbl2 = ctx.new Label("label2", "Search", RIconsUI.getIcon(RIconsUI.RIcons.RICON_LENS));

        /* Checkboxes! */
        var lbl3 = ctx.new Label("checkbox1", "Check Me", RIconsUI.getIcon(RIconsUI.RIcons.RICON_BOX));
        lbl3.Enabled.set(true);
        lbl3.onMouseClick.set((s, e) -> {
            lbl3.Checked.set(!lbl3.Checked.get());
            if(lbl3.Checked.get())
                lbl3.Icon.set(RIconsUI.getIcon(RIconsUI.RIcons.RICON_BOX_CHECKED));
            else
                lbl3.Icon.set(RIconsUI.getIcon(RIconsUI.RIcons.RICON_BOX));
        });

        /* Theme picker */
        ctx.new Label("lblTheme", "Choose a theme: ");
        var th1 = ctx.new Button("btnTh1", "Default");
        th1.Checked.set(true);
        var th2 = ctx.new Button("btnTh2", "Jungle");
        var th3 = ctx.new Button("btnTh3", "Cyber");

        th1.onMouseClick.set((s, e) -> {
            ctx.setTheme(PicoUI.getDefaultStyle());
            setBackground(PicoUI.getDefaultStyle());
            th1.Checked.set(true); th2.Checked.set(false); th3.Checked.set(false);
        });
        th2.onMouseClick.set((s, e) -> {
            ctx.setTheme(PicoStyleJungle.getInstance());
            setBackground(PicoStyleJungle.getInstance());
            th1.Checked.set(false); th2.Checked.set(true); th3.Checked.set(false);
        });
        th3.onMouseClick.set((s, e) -> {
            ctx.setTheme(PicoStyleCyber.getInstance());
            setBackground(PicoStyleCyber.getInstance());
            th1.Checked.set(false); th2.Checked.set(false); th3.Checked.set(true);
        });

        // Update layout
        ctx.update();
    }

    private void setBackground(PicoUI.PicoStyle style)
    {
        getBackground().setColor(style.getBackgroundDefault());
        getBackground().fill();
    }

    @Override
    public void started()
    {
        // BakedFontInfo.printFontWidths(new java.awt.Font("Courier New", java.awt.Font.PLAIN, 15));

        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        /*super(600, 400, 1);

        BakedFontInfo monospace = CourierNew.getFontInfoItalic(15, 13, 5, 18);
        BakedFontInfo sansserif = SansSerif.getFontInfoItalic(15, 16, 4, 20);

        //BakedFontInfo.printFontWidths(new java.awt.Font("Consolas", java.awt.Font.PLAIN, 15));

        addObject(new Actor()
        {
            @Override
            protected void addedToWorld(World world)
            {
                GreenfootImage img = new GreenfootImage(250, 100);
                setImage(img);
                Homespun.drawString(img, "Hello, World!", 20, 40);
            }
        },200, 100);*/
    }
}
