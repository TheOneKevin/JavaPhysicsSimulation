/*
 * The color scheme and visual styles used in PicoStyleDefault is from raylib5
 * by Ramon Santamaria (@raysan5) and is licensed under zlib.
 * See license heading above the PicoStyleDefault class for more details.
 *
 * All other code is licensed under:
 *
 * MIT License
 *
 * Copyright (c) 2020 Kevin Dai
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package oop.simulation.ui;

import greenfoot.*;
import oop.simulation.ui.fonts.BakedFontInfo;
import oop.simulation.ui.fonts.baked.CourierNew;
import oop.simulation.beans.Property;
import oop.simulation.beans.Readonly;

import java.util.LinkedHashMap;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * PicoUI is a single-file mini GUI framework for satisfying your quick UI <b>prototyping</b>
 * needs in Greenfoot. This library is small, concise and easy to use - without all the
 * fancy frills a modern UI library offers. The goal is for fast no-hassle prototyping of UI,
 * not for a fully fledged user experience.
 * <br><br>
 * New components can be created from {@link PicoUI.PicoComponent PicoComponent} and new styles
 * can be implemented in {@link PicoUI.PicoStyle PicoStyle} (although see improvements below).
 * <br><br>
 * Example (in the constructor of your World):
 * <pre>
 * PicoUI ctx = new PicoUI(400, 200);
 * addObject(ctx, 300, 200);
 * var btn1 = ctx.new Button("button1", "Click me!");
 * ctx.update(); // Render it!
 * </pre>
 *
 * Some improvements to be added:
 * <ul>
 *     <li> Stop extending Actor in PicoComponent. Instead, create a separate container for
 *          components, managed by the PicoUI context.
 *     <li> Rework property system, allowing for default-backed properties (i.e., simple r/w), simplifying code.
 *          Then Events wouldn't need to be backed by a private "internalOn..." variable.
 *     <li> Move PicoStyle into a dictionary. Who needs classes anyways... (its clumsy at best)
 *     <li> Refactor and clean up code (overloaded constructors especially...)
 *     <li> Add advanced layout support... (currently no XY placement allowed)
 *     <li> Centered text in textboxes!
 * </ul>
 *
 * <b>Note:</b> The color scheme and visual styles used in PicoStyleDefault is from raylib5
 * by Ramon Santamaria (@raysan5) and is licensed under zlib.
 * See license heading above the PicoStyleDefault class for more details.
 *
 * @author Kevin Dai
 * @version 1.0.0
 */
public class PicoUI extends Actor
{
    private LinkedHashMap<String, PicoComponent> components;
    private Font uiFont;
    private PicoStyle style;
    private int contextWidth, contextHeight;
    private String lastKeyPressedLatch = "";

    /**
     * New UI context with default style given width and height.
     * @param width  Width of frame/panel
     * @param height Height of frame/panel
     */
    public PicoUI(int width, int height)
    {
        this(width, height, new PicoStyleDefault());
    }

    /**
     * New UI context from given style and given width and height. See {@link PicoStyle}
     * @param width  Width of frame/panel
     * @param height Height of frame/panel
     * @param style  Style of UI components.
     */
    public PicoUI(int width, int height, PicoStyle style)
    {
        components = new LinkedHashMap<>();

        this.style = style;
        this.contextWidth = width;
        this.contextHeight = height;
        this.uiFont = new Font("Courier New", false, false, style.getTextSize());

        setImage(new GreenfootImage(contextWidth, contextHeight));
        getImage().setColor(style.getBorderColorNormal());
        getImage().drawRect(0, 0, contextWidth-1, contextHeight-1);
    }

    /**
     * Obtains a component within the context based on its unique name.
     * @param name Name of component to find.
     * @return Component if found. Null if not found.
     */
    public PicoComponent getControl(String name)
    {
        return components.getOrDefault(name, null);
    }

    /**
     * Obtains a component within the context based on its unique name.
     * @param name Name of component
     * @param type Type of component
     * @return The component, casted to type T, if found. Null if not found.
     */
    @SuppressWarnings("unchecked")
    public <T extends PicoComponent> T getControl(String name, Class<T> type)
    {
        PicoComponent c = components.getOrDefault(name, null);
        if(c == null) return null;
        return (T) c;
    }

    /**
     * Displays components one after another in the order they
     * are registered in the context. Overflowed components will
     * be moved a line under.
     */
    public void update()
    {
        update(5, 50); // Living is suffering
    }

    /**
     * Updates everything given margin and lineheight.
     *
     * @param margin     Margin between items
     * @param lineheight Space per line.
     */
    public void update(int margin, int lineheight)
    {
        if(getWorld() == null) return;

        // Remove all existing objects, you're not supposed to add
        // them to the world manually anyways.
        getWorld().removeObjects(getWorld().getObjects(PicoComponent.class));

        // Loop through and add them one by one, overflowing when needed.
        int x = getX() - contextWidth/2 + margin + 2, y = getY() - contextHeight/2 + (lineheight + margin)/2;
        for(var v : components.values())
        {
            if(x + v.Width.get() + margin >= getX() + contextWidth / 2)
            {
                x = getX() - contextWidth/2 + margin + 2;
                y += lineheight;
            }

            // System.out.println(v.Name.get() + " " + v.Width.get());
            getWorld().addObject(v, x + v.Width.get()/2, y);
            x += v.Width.get() + margin;
        }
    }

    public void setTheme(PicoStyle st)
    {
        this.style = st;
        for(var c : components.values()) c.markDirty();
    }

    public static PicoStyle getDefaultStyle()
    {
        return PicoStyleDefault.getInstance();
    }

    /**
     * Default Greenfoot act() method.
     */
    @Override
    public void act()
    {
        lastKeyPressedLatch = Greenfoot.getKey();
    }

    private void addComponent(PicoComponent c)
    {
        if(components.containsKey(c.name))
            throw new IllegalArgumentException("A component already has the same name: " + c.name);
        components.put(c.name, c);
    }

    private String getLastKeyPress()
    {
        return lastKeyPressedLatch;
    }

    /**
     * Label is a non-interactive text control. However, it can still receive mouse
     * events (as with all PicoComponents).
     * <p>
     * Sample usage:
     * <pre>ctx.new Label("label1", "Some text to show");</pre>
     *
     * @since PicoUI 1.0
     */
    public class Label extends TextComponent
    {
        private Color textColor;

        /**
         * Creates a label with the supplied text.
         *
         * @param name Unique name of component
         * @param text Label text
         */
        public Label(String name, String text)
        {
            this(name, text, null);
        }

        /**
         * Creates a label with the supplied icon.
         *
         * @param name Unique name of component
         * @param ico  Label icon
         */
        public Label(String name, GreenfootImage ico)
        {
            this(name, "", null);
        }

        /**
         * Creates a label with the supplied text and graphic.
         *
         * @param name Unique name of component
         * @param text Label text
         * @param ico  Icon to be displayed. Null if nothing.
         */
        public Label(String name, String text, GreenfootImage ico)
        {
            super(name, text);
            setStyle(style.getTextColorNormal());
            TextAlign.set(style.getTextAlignment());
            AutoSizeWidth.set(true);
            Icon.set(ico);
            Enabled.set(false);
            updateDimensions();
        }

        @Override
        protected void updateState()
        {
            if(!Enabled.get())
            {
                setStyle(style.getTextColorNormal());
                return;
            }

            if (mouseDown)
                setStyle(style.getTextColorPressed());
            else if (mouseOver)
                setStyle(style.getTextColorFocused());
            else
                setStyle(style.getTextColorNormal());
        }

        @Override
        protected int getStroke() { return style.getBorderWidth(); }

        @Override
        protected int getLineSpace() { return style.getTextSpacing(); }

        private void setStyle(Color c)
        {
            if(this.textColor != c)
                markDirty();
            this.textColor = c;
        }

        @Override
        protected void draw(GreenfootImage g)
        {
            g.setColor(textColor);
            this.drawInnerIcon(textColor);
            this.drawInnerText(g);
        }
    }

    /**
     * A simple button control. The button can contain text and/or graphics.
     * <p>
     * Sample usage:
     * <pre>ctx.new Button("button1", "Button Text");</pre>
     * Creates a new button with the text "ButtonText" and no display image.
     *
     * @since PicoUI 1.0
     */
    public class Button extends TextComponent
    {
        private Color backColor, borderColor, textColor;

        /**
         * Creates a Button from the supplied text.
         *
         * @param name Unique name of component
         * @param text Text to display
         */
        public Button(String name, String text)
        {
            this(name, text, null);
        }

        /**
         * Creates a Button from the supplied icon.
         *
         * @param name Unique name of component
         * @param ico  Icon to display. Null if no icon is needed.
         */
        public Button(String name, GreenfootImage ico)
        {
            this(name, "", ico);
        }

        /**
         * Creates a Button from the supplied text and icon.
         *
         * @param name Unique name of component
         * @param text Text to display
         * @param ico  Icon to display. Null if no icon is needed.
         */
        public Button(String name, String text, GreenfootImage ico)
        {
            this(name, text, 5, 0, true, 0, true, ico);
        }

        protected Button(String name, String text, int margin, int width, boolean autoWidth, int height, boolean autoHeight, GreenfootImage ico)
        {
            super(name, text);
            setStyle(style.getBaseColorNormal(), style.getBorderColorNormal(), style.getTextColorNormal());

            Margins.set(new Margins(margin, margin, margin, margin));
            AutoSizeWidth.set(autoWidth);
            AutoSizeHeight.set(autoHeight);
            Width.set(width);
            Height.set(height);
            TextAlign.set(style.getTextAlignment());
            Icon.set(ico);

            // Draw button
            updateDimensions();
        }

        @Override
        protected void updateState()
        {
            if (!Enabled.get())
            {
                setStyle(style.getBaseColorDisabled(), style.getBorderColorDisabled(), style.getTextColorDisabled());
                return;
            }

            if (mouseDown)
                setStyle(style.getBaseColorPressed(), style.getBorderColorPressed(), style.getTextColorPressed());
            else if (mouseOver || Checked.get())
                setStyle(style.getBaseColorFocused(), style.getBorderColorFocused(), style.getTextColorFocused());
            else
                setStyle(style.getBaseColorNormal(), style.getBorderColorNormal(), style.getTextColorNormal());
        }

        @Override
        protected int getStroke()
        {
            return style.getButtonBorderWidth();
        }

        @Override
        protected int getLineSpace()
        {
            return style.getTextSpacing();
        }

        private void setStyle(Color bg, Color c, Color t)
        {
            // Save needless icon redraws and CPU power!
            if (!bg.equals(this.backColor) || !c.equals(this.borderColor) || !t.equals(this.textColor))
                markDirty();

            this.backColor = bg;
            this.borderColor = c;
            this.textColor = t;
        }

        @Override
        protected void draw(GreenfootImage g)
        {
            // Setup variables
            int stroke = getStroke();
            int ch = Height.get();
            int cw = Width.get();
            // Draw back
            g.setColor(backColor);
            g.fillRect(0, 0, cw, ch);
            // Draw border
            g.setColor(borderColor);
            g.drawRect(stroke / 2, stroke / 2, cw - stroke, ch - stroke);
            // Draw icon
            g.setColor(textColor);
            this.drawInnerIcon(textColor);
            // Draw text
            this.drawInnerText(g);
        }
    }

    /**
     * A Slider component is used to display a continuous range of valid values.
     * It consists of a "track" and a "thumb," where the thumb is dragged through the track.
     * <p>
     * Sample usage:
     * <pre>ctx.new Slider("slider1", 0d, 100d);</pre>
     * Will create a slider accepting values from 0 to 100.
     *
     * @since PicoUI 1.0
     */
    public class Slider extends PicoComponent
    {
        private int thumb_width;
        private double val, min, max;
        private Color backColor, borderColor, thumbColor;

        // Event handlers
        private BiConsumer<PicoComponent, Double> internalOnValueUpdate = (p, e) -> { };

        /**
         * onValueUpdate will fire whenever {@link Slider#Value} is updated (even if
         * no numerical change occurred).
         * The first parameter passed will be the component which the event fired on.
         * The second parameter passed will be the value <i>after</i> the update.
         * This is because the event is fired before the internal values are updated.
         */
        public final Property<BiConsumer<PicoComponent, Double>> onValueUpdate = Property.get(() -> internalOnValueUpdate).set(value -> internalOnValueUpdate = value);

        /**
         * Current value the slider represents.
         */
        public final Property<Double> Value = Property.get(() -> val).set(value -> {
            markDirty();
            onValueUpdate.get().accept(this, value);
            return val = Math.max(min, Math.min(value, max));
        });

        /**
         * Maximum value of the slider.
         */
        public final Property<Double> Max = Property.get(() -> max).set(value -> {
            markDirty();
            return max = Math.max(min, value);
        });

        /**
         * Minimum value of the slider.
         */
        public final Property<Double> Min = Property.get(() -> min).set(value -> {
            markDirty();
            return min = Math.min(value, max);
        });

        /**
         * Creates a slider with the given width and max/min values.
         * Slider is initialized to 0 always.
         *
         * @param name  Unique name of component
         * @param width Width of slider
         * @param min   Minimum value of slider
         * @param max   Maximum value of slider
         */
        public Slider(String name, int width, double min, double max)
        {
            super(name);
            setStyle(style.getBaseColorNormal(), style.getBorderColorNormal(), style.getBaseColorPressed());

            Width.set(width);
            Height.set(15);
            Margins.set(new Margins(2, 2, 2, 2));
            Min.set(min);
            Max.set(max);
            Value.set(0d);

            this.thumb_width = 15;

            updateDimensions();
        }

        private double getUpperLimit()
        {
            // Calculate highest X value for thumb
            return Width.get() - thumb_width/2.0 - Margins.get().getRight();
        }

        private double getLowerLimit()
        {
            // Calculate lowest X value for thumb
            return thumb_width/2.0 + Margins.get().getLeft();
        }

        @Override
        protected void updateState()
        {
            if(!Enabled.get())
            {
                setStyle(style.getBaseColorDisabled(), style.getBorderColorDisabled(), style.getBaseColorDisabled());
                return;
            }

            if(mouseDown)
                setStyle(style.getBaseColorNormal(), style.getBorderColorPressed(), style.getBaseColorPressed());
            else if(mouseOver)
                setStyle(style.getBaseColorNormal(), style.getBorderColorFocused(), style.getTextColorFocused());
            else
                setStyle(style.getBaseColorNormal(), style.getBorderColorNormal(), style.getBaseColorPressed());

            if(Greenfoot.mouseDragged(this) || Greenfoot.mousePressed(this))
            {
                double lx = Greenfoot.getMouseInfo().getX()-getX()+Width.get()/2.0;
                lx = Math.max(getLowerLimit(), Math.min(getUpperLimit(), lx));
                double percent = (lx - getLowerLimit()) / (getUpperLimit() - getLowerLimit());
                Value.set(percent*(max - min) + min);
                markDirty();
            }
        }

        private void setStyle(Color bg, Color c, Color t)
        {
            if (!bg.equals(this.backColor) || !c.equals(this.borderColor) || !t.equals(this.thumbColor))
                markDirty();
            this.backColor = bg;
            this.borderColor = c;
            this.thumbColor = t;
        }

        @Override
        protected void draw(GreenfootImage g)
        {
            g.setColor(backColor);
            g.fillRect(0, 0, Width.get(), Height.get());
            g.setColor(borderColor);
            g.drawRect(0, 0, Width.get() - 1, Height.get() - 1);
            g.setColor(thumbColor);

            double percent = (val - min)/(max - min);
            g.fillRect((int) ((getUpperLimit() - getLowerLimit()) * percent + getLowerLimit() - thumb_width / 2.0),
                Margins.get().getTop(),
                thumb_width, Height.get() - marginsTopBottom());
        }
    }

    /**
     * TextField component for allows the user to enter a single line
     * of unformatted text. TextField supports showing {@link TextField#PromptText}
     * when {@link TextField#Text} is empty (either by user or set programmatically).
     * This is useful in informing what is expected in the TextField
     * without using tooltips or labels.
     * <p>
     * Sample usage:
     * <pre>ctx.new TextField("textfield1", 200)</pre>
     * This will create a new TextField with no initial text and a width of 200.
     * <br><br>
     * <b>Note:</b> The decision to not support CAPS LOCK was brought on by
     * Greenfoot's inability to detect whether CAPS LOCK has been engaged before
     * the component has been focused or not.
     * However, SHIFT is supported (for QWERTY layouts only).
     *
     * @since PicoUI 1.0
     */
    public class TextField extends TextComponent
    {
        private Color   backColor, borderColor, textColor;
        private Timer   timer;
        private boolean showCursor = true, isSelected = false;
        private double  textOffset = 0; // Note: should always be <= 0
        private int     cursorIndex = 0, dragEndIndex = 0;
        private String  promptText = ""; // Prevent NullPointerException

        /**
         * Prompt text is displayed when no text is in the TextField.
         */
        public final Property<String> PromptText = Property.get(() -> promptText).set(value -> promptText = value);

        /**
         * Creates a text field with the supplied width.
         *
         * @param name  Unique name of component
         * @param width Width of text box
         */
        public TextField(String name, int width)
        {
            this(name, width, 0, true);
        }

        /**
         * Creates a text field with the supplied width and height.
         *
         * @param name  Unique name of component
         * @param width Width of text box
         */
        public TextField(String name, int width, int height)
        {
            this(name, width, height, false);
        }

        protected TextField(String name, int width, int height, boolean resizeHeight)
        {
            super(name, "", false);
            setStyle(style.getBackgroundDefault(), style.getBorderColorNormal(), style.getTextColorNormal());

            AutoSizeWidth.set(false);
            AutoSizeHeight.set(resizeHeight);
            TextAlign.set(ItemAlign.JUSTIFY_LEFT);
            Width.set(width);
            Height.set(height);
            Margins.set(new Margins(5, 5, 5, 5));
            PromptText.set("");

            timer = new Timer(25, () -> showCursor = !showCursor, true);
            timer.start();

            updateDimensions();
        }

        @Override
        protected void updateState()
        {
            markDirty(); // TODO: Optimize
            timer.tick();
            if(!Greenfoot.mouseDragEnded(this))
            {
                if (Greenfoot.mouseClicked(this))
                {
                    // Position within text
                    double lx = Greenfoot.getMouseInfo().getX() - getX() + Width.get() / 2.0 - Margins.get().getLeft() - getStroke() - textOffset;
                    cursorIndex = TextUtils.getTextIntersectionIndex(getGraphics(), style.getMetrics(), Text.get(), (int) lx);
                    dragEndIndex = cursorIndex;
                    showCursor = true;
                    isSelected = false;
                    this.focus();
                } else if (Greenfoot.mouseClicked(null))
                {
                    isSelected = false;
                    this.blur();
                }
            }

            if(mouseDragging)
            {
                double lx = Greenfoot.getMouseInfo().getX() - getX() + Width.get() / 2.0 - textOffset - Margins.get().getLeft() - getStroke();
                double acc1 = getX() + Width.get() / 2.0 - Greenfoot.getMouseInfo().getX();
                double acc2 = getX() - Width.get() / 2.0 - Greenfoot.getMouseInfo().getX();

                if(acc1 < 0) textOffset += 0.2*acc1;
                else if(acc2 > 0) textOffset += 0.2*acc2;

                textOffset = Math.min(0, textOffset);
                textOffset = Math.max(-(TextUtils.getTextWidth(style.getMetrics(), Text.get()) + 5 - Width.get() + Margins.get().getLeft()), textOffset);

                cursorIndex = TextUtils.getTextIntersectionIndex(getGraphics(), style.getMetrics(), Text.get(), (int) lx);
                showCursor = true;
                isSelected = true;
            }

            if(mouseStartDragging)
            {
                dragEndIndex = cursorIndex;
                mouseStartDragging = false;
                this.focus();
            }

            var key = getLastKeyPress();
            if(this.isFocused() && key != null)
            {
                key = "space".equals(key) ? " " : key;
                if ("right".equals(key))
                {
                    isSelected = false;
                    showCursor = true;
                    shiftRightText();
                } else if ("left".equals(key))
                {
                    isSelected = false;
                    showCursor = true;
                    shiftLeftText();
                } else if (key.length() == 1 && key.matches("\\p{Print}"))
                {
                    if (Greenfoot.isKeyDown("shift"))
                        key = TextUtils.getShiftedVersion(key.charAt(0)) + "";

                    int lo = Math.min(cursorIndex, dragEndIndex);
                    int hi = Math.max(cursorIndex, dragEndIndex);
                    if (isSelected)
                    {
                        this.isSelected = false;
                        Text.set(Text.get().substring(0, lo) + key + Text.get().substring(hi));
                        cursorIndex = lo;
                    } else
                        Text.set(Text.get().substring(0, cursorIndex) + key + Text.get().substring(cursorIndex));
                    shiftRightText();

                    isSelected = false;
                    showCursor = true;
                } else if ("backspace".equals(key))
                {
                    showCursor = true;
                    int lo = Math.min(cursorIndex, dragEndIndex);
                    int hi = Math.max(cursorIndex, dragEndIndex);
                    if (isSelected)
                    {
                        this.isSelected = false;
                        Text.set(Text.get().substring(0, lo) + Text.get().substring(hi));
                        cursorIndex = lo + 1;
                        shiftLeftText();
                    } else if (cursorIndex > 0)
                    {
                        Text.set(Text.get().substring(0, cursorIndex-1) + Text.get().substring(cursorIndex));
                        shiftLeftText();
                    }
                }
            }

            if(this.isFocused())
            {
                // Prompt text...
                if (this.Text.get().isEmpty() /*|| this.Text.get().isBlank()*/)
                    setStyle(style.getBackgroundDefault(), style.getBorderColorFocused(), style.getTextColorDisabled());
                else
                    setStyle(style.getBackgroundDefault(), style.getBorderColorFocused(), style.getTextColorNormal());
            }
            else
            {
                // Prompt text as well...
                if (this.Text.get().isEmpty() /*|| this.Text.get().isBlank()*/)
                    setStyle(style.getBackgroundDefault(), style.getBorderColorNormal(), style.getTextColorDisabled());
                else
                    setStyle(style.getBackgroundDefault(), style.getBorderColorNormal(), style.getTextColorNormal());
            }
            if (!isSelected) cursorIndex = dragEndIndex;
        }

        @Override
        protected int getStroke() { return style.getBorderWidth(); }

        @Override
        protected int getLineSpace() { return style.getTextSpacing(); }

        private void setStyle(Color bg, Color c, Color t)
        {
            this.backColor = bg;
            this.borderColor = c;
            this.textColor = t;
        }

        private void shiftText()
        {
            int cursX = TextUtils.getTextWidth(style.getMetrics(), Text.get().substring(0, cursorIndex)) + (int) textOffset;

            int offsLeft = cursX - Margins.get().getLeft() + getStroke();
            if (offsLeft < 0)
                textOffset -= offsLeft;

            int offsRight = cursX + marginsLeftRight() + 2 * getStroke();
            if (offsRight > Width.get())
                textOffset += Width.get() - offsRight;
            if (textOffset > 0) textOffset = 0;
        }

        private void shiftLeftText()
        {
            cursorIndex--;
            if(cursorIndex < 0) cursorIndex = 0;
            shiftText();
            dragEndIndex = cursorIndex;
        }

        private void shiftRightText()
        {
            cursorIndex++;
            if(cursorIndex > Text.get().length()) cursorIndex = Text.get().length();
            shiftText();
            dragEndIndex = cursorIndex;
        }

        @Override
        protected void draw(GreenfootImage g)
        {
            // Initialize variables
            int stroke = getStroke();
            int ch = Height.get();
            int cw = Width.get();
            int mr = Margins.get().getRight();
            int ml = Margins.get().getLeft();
            int mt = Margins.get().getTop();
            int mb = Margins.get().getBottom();

            // Correct text offset
            if (TextUtils.getTextWidth(style.getMetrics(), Text.get()) <= cw - marginsLeftRight() - 2 * stroke)
                textOffset = 0;
            // Draw background
            g.setColor(backColor);
            g.fillRect(0, 0, cw, ch);
            // Draw selection
            if (isSelected)
            {
                int lo = Math.min(cursorIndex, dragEndIndex);
                int hi = Math.max(cursorIndex, dragEndIndex);

                int startx = TextUtils.getTextWidth(style.getMetrics(), Text.get().substring(0, lo));
                int selwid = TextUtils.getTextWidth(style.getMetrics(), Text.get().substring(lo, hi));

                final int off = 1;

                g.setColor(style.getTextboxSelectedBg());
                g.fillRect(stroke + Margins.get().getLeft() + startx + (int) textOffset, stroke + off, selwid, ch - 2 * stroke - 2 * off);
            }
            // Draw text/prompt
            g.setColor(textColor);
            if(!isFocused() && (/*Text.get().isBlank() ||*/ Text.get().isEmpty())) // Draw prompt ONLY WHEN FOCUSED
            {
                // Prompt text is shown when TextBox is blank in italics
                g.setFont(new Font(uiFont.getName(), false, true, uiFont.getSize()));
                this.drawInnerText(g, 0, promptText);
            }
            else
                this.drawInnerText(g, (int) textOffset);
            // Draw overlay
            if (isSelected)
            {
                g.setColor(style.getTextboxSelectedFg());
                int lo = Math.min(cursorIndex, dragEndIndex);
                int hi = Math.max(cursorIndex, dragEndIndex);
                int offsetX = TextUtils.getTextWidth(style.getMetrics(), Text.get().substring(0, lo));
                this.drawInnerText(g, (int) textOffset + offsetX, Text.get().substring(lo, hi));
            }
            // Draw right/left margins...
            g.setColor(backColor);
            g.fillRect(cw - stroke - mr, 0, stroke + mr, ch);
            g.fillRect(0, 0, stroke + ml, ch);
            // Finally draw cursor
            g.setColor(style.getBorderColorNormal());
            if (showCursor && this.isFocused())
            {
                int cursX = TextUtils.getTextWidth(style.getMetrics(), Text.get().substring(0, cursorIndex)) + (int) textOffset;
                g.drawLine(stroke + ml + cursX, stroke + mt, stroke + ml + cursX, ch - (stroke + mb));
            }
            // Draw border last
            g.setColor(borderColor);
            g.drawRect(stroke / 2, stroke / 2, cw - stroke, ch - stroke);
        }
    }

    /**
     * TextComponent provides a base to draw textual content. For instance:
     * {@link Label}, {@link Button} and {@link TextField} are examples of
     * extended TextComponents. Inheriting a TextComponent allows for the
     * drawing of icons and text. {@link TextComponent#getLineSpace()} and
     * {@link TextComponent#getStroke()} must be defined by children.
     */
    public abstract class TextComponent extends PicoComponent
    {
        private GreenfootImage ico;
        private int icoOffset;
        private int internalWidth;
        private int internalHeight;
        private boolean autoSizeWidth;
        private boolean autoSizeHeight;
        private boolean doUpdate;
        private ItemAlign align;

        protected String displayText;

        /**
         * If true, will resize control to text width.
         */
        public final Property<Boolean> AutoSizeWidth = Property.get(() -> autoSizeWidth).set(value -> autoSizeWidth = value);

        /**
         * If true, will resize control to text height.
         */
        public final Property<Boolean> AutoSizeHeight = Property.get(() -> autoSizeHeight).set(value -> autoSizeHeight = value);

        /**
         * Icon to display beside text.
         */
        public final Property<GreenfootImage> Icon = Property.get(() -> ico).set(value -> { markDirty(); return ico = value; });

        /**
         * Alignment of text.
         */
        public final Property<ItemAlign> TextAlign = Property.get(() -> align).set(value -> align = value);

        /**
         * Text held within component.
         */
        public final Property<String> Text = Property.get(() -> displayText).set(value -> {
            displayText = value;
            icoOffset = 0;
            if (/*!value.isBlank() &&*/ !value.isEmpty())
                icoOffset = 5;
            if (doUpdate) updateDimensions();
            return value;
        });

        protected abstract int getStroke();
        protected abstract int getLineSpace();

        /**
         * Constructor for text component with supplied name and text.
         * @param name Unique name of component
         * @param text Text content
         */
        public TextComponent(String name, String text)
        {
            this(name, text, true);
        }

        /**
         * Constructor for text component with supplied name and text.
         * @param name                 Unique name of component
         * @param text                 Text content
         * @param updateWhenTextChange If true, will call {@link PicoComponent#updateDimensions()} every text update.
         */
        public TextComponent(String name, String text, boolean updateWhenTextChange)
        {
            super(name);
            Text.set(text);
            autoSizeWidth = true;
            autoSizeHeight = true;

            this.doUpdate = updateWhenTextChange;
            this.overrideProperty(Height, () -> {
                if(!autoSizeHeight) return internalHeight;
                int ico_height = 0;
                if (ico != null) ico_height = ico.getHeight();
                // Is icon taller or text taller?
                int properHeight = Math.max(
                    TextUtils.getTextHeight(style.getMetrics(), displayText, getStroke()),
                    ico_height
                );
                return properHeight + this.marginsTopBottom() + 2 * getStroke();
            }, value -> {
                if (!autoSizeHeight)
                {
                    internalHeight = value;
                    updateDimensions();
                    return internalHeight;
                }
                return value;
            });
            this.overrideProperty(Width, () -> {
                if (!autoSizeWidth) return internalWidth;
                int ico_width = 0;
                if (ico != null)
                    ico_width = ico.getWidth() + icoOffset;
                return TextUtils.getTextWidth(style.getMetrics(), displayText)
                    + this.marginsLeftRight() + 2 * getStroke()
                    + ico_width;
            }, value -> {
                if (!autoSizeWidth)
                {
                    internalWidth = value;
                    updateDimensions();
                    return internalWidth;
                }
                return value;
            });
        }

        protected void drawInnerIcon(Color c)
        {
            if (ico == null) return;
            // var buf = ((DataBufferInt) ico.getAwtImage().getRaster().getDataBuffer()).getData();
            if(isDirty)
            {
                isDirty = false;
                for (int x = 0; x < ico.getWidth(); x++)
                    for (int y = 0; y < ico.getHeight(); y++)
                    if (ico.getColorAt(x, y).getAlpha() != 0)
                        ico.setColorAt(x, y, c);
            }
            // Subtract one just because it looks nice
            getImage().drawImage(ico, Margins.get().getLeft() + getStroke() - 1, Height.get() / 2 - ico.getHeight() / 2);
        }

        protected void drawInnerText(GreenfootImage g)
        {
            drawInnerText(g, 0);
        }

        protected void drawInnerText(GreenfootImage g, int offset_x)
        {
            drawInnerText(g, offset_x, displayText);
        }

        protected void drawInnerText(GreenfootImage g, int offset_x, String text)
        {
            int stroke = getStroke();
            int ch = Height.get();
            int linespace = getLineSpace();
            int offset = 0;
            if (ico != null) offset = ico.getWidth() + icoOffset;
            // Minus 2 in y coord because things look off
            if (!autoSizeWidth)
                TextUtils.drawText(g, style.getMetrics(), text, Margins.get().getLeft() + stroke + offset + offset_x, ch / 2 - TextUtils.getTextHeight(style.getMetrics(), displayText, linespace) / 2 - 2, align, linespace, Width.get());
            else
                TextUtils.drawText(g, style.getMetrics(), text, Margins.get().getLeft() + stroke + offset + offset_x, ch / 2 - TextUtils.getTextHeight(style.getMetrics(), displayText, linespace) / 2 - 2, align, linespace);
        }
    }

    /**
     * Base class for all components. All components added to the UI context
     * must be a derivative of this class. At its core, a control must be able
     * to update its internal state and draw itself through the implementation
     * of {@link PicoComponent#updateState()} and {@link PicoComponent#draw(GreenfootImage)} methods.
     * Although an actor, upon creation it will add itself to the existing UI context.
     * Therefore <b>do not directly call addObject() to add these to the world.</b> Instead,
     * refer to the proper {@link PicoUI} documentation for more details.
     * <br><br>
     * The base PicoComponent has 3 properties: {@link PicoComponent#Height}, {@link PicoComponent#Width} and {@link PicoComponent#Margins} properties.
     * <br><br>
     * These may be overwritten in the child class through {@link PicoComponent#overrideProperty(Property, Supplier, Function)}
     * (don't use variable hiding, it's not healthy).
     */
    public abstract class PicoComponent extends Actor
    {
        // Private properties
        private String  name;
        private int     width;
        private int     height;
        private Margins margins;
        private boolean focused;
        private boolean enabled;
        private boolean checked = false;
        private Object metadata;

        // Latched events (i.e., events that are not transient like click)
        protected boolean mouseOver;
        protected boolean mouseDown;
        protected boolean mouseDragging;
        protected boolean mouseStartDragging;
        protected boolean isDirty;

        // Event handlers
        private BiConsumer<PicoComponent, MouseInfo> internalOnMouseClick = (s, e) -> {};
        private Consumer<PicoComponent> internalOnFocus = (s) -> {};
        private Consumer<PicoComponent> internalOnBlur  = (s) -> {};

        protected void markDirty()
        {
            isDirty = true;
        }

        protected abstract void updateState();
        protected abstract void draw(GreenfootImage g);

        /**
         * onMouseClick will fire whenever a mouse click on the actor is detected.
         * The first parameter passed will be the component which the event fired on.
         * The second parameter passed will be the greenfoot.MouseInfo object
         * captured at the time of the click.
         */
        public final Property<BiConsumer<PicoComponent, MouseInfo>> onMouseClick = Property.get(() -> internalOnMouseClick).set(v -> internalOnMouseClick = v);

        /**
         * onFocus will fire whenever the component is focused.
         * The first parameter passed will be the component which the event fired on.
         */
        public final Property<Consumer<PicoComponent>> onFocus = Property.get(() -> internalOnFocus).set(v -> internalOnFocus = v);

        /**
         * onBlue will fire whenever the component loses focus.
         * The first parameter passed will be the component which the event fired on.
         */
        public final Property<Consumer<PicoComponent>> onBlur  = Property.get(() -> internalOnBlur).set(v -> internalOnBlur = v);

        /**
         * Margins of component.
         */
        public final Property<Margins> Margins = Property.get(() -> margins)
            .set(value -> {
                if(value != null)
                {
                    this.margins = value;
                    updateDimensions();
                }
                return this.margins;
            });

        /**
         * Enabled/Disabled state of control.
         */
        public final Property<Boolean> Enabled = Property.get(() -> enabled).set(value -> enabled = value);

        /**
         * Height of control.
         */
        public final Property<Integer> Height = Property.get(() -> height).set(value -> {
            this.height = value;
            updateDimensions();
            return value;
        });

        /**
         * Width of control.
         */
        public final Property<Integer> Width = Property.get(() -> width).set(value -> {
            this.width = value;
            updateDimensions();
            return value;
        });

        /**
         * Readonly name of control.
         */
        public final Readonly<String> Name = Property.get(() -> name).readonly();

        /**
         * Metadata for object (stores anything you want).
         */
        public final Property<Object> Metadata = Property.get(() -> metadata).set(value -> metadata = value);

        /**
         * Is object checked?
         */
        public final Property<Boolean> Checked = Property.get(() -> checked).set(v -> checked = v);

        /**
         * Creates and adds the simple component to the UI context.
         * @param name Unique name of component
         */
        public PicoComponent(String name)
        {
            this.name = name;
            this.focused = false;
            this.enabled = true;
            this.margins = new Margins(0, 0, 0, 0);
            addComponent(this);
        }

        /**
         * Causes component to lose focus. All mouse events will still be registered.
         */
        public void blur()  { focused = false; internalOnBlur.accept(this); }

        /**
         * Focuses component.
         */
        public void focus() { focused = true; internalOnFocus.accept(this); }

        /**
         * @return Focus status of component
         */
        public boolean  isFocused() { return focused; }

        protected <T> void overrideProperty(Property<T> property, Supplier<T> getter, Function<T, T> setter)
        {
            property.rebind(getter, setter);
        }

        protected int marginsTopBottom()
        {
            return Margins.get().getTop() + Margins.get().getBottom();
        }

        protected int marginsLeftRight()
        {
            return Margins.get().getRight() + Margins.get().getLeft();
        }

        protected void updateDimensions()
        {
            markDirty();
            int w = Width.get(), h = Height.get();
            // Prevent images with 0 dimension
            setImage(new GreenfootImage(w == 0 ? 1 : w, h == 0 ? 1 : h));
            draw(getGraphics());
        }

        protected GreenfootImage getGraphics()
        {
            GreenfootImage g = getImage();
            g.setFont(uiFont);
            return g;
        }

        /**
         * Overridden Greenfoot Actor act() method.
         */
        @Override
        public void act()
        {
            if (!mouseOver && Greenfoot.mouseMoved(this))
                mouseOver = true;
            else if (mouseOver && Greenfoot.mouseMoved(null) && !Greenfoot.mouseMoved(this))
                mouseOver = false;

            if (mouseDown && (Greenfoot.mouseDragEnded(null) || Greenfoot.mouseClicked(null)))
                mouseDown = false;
            else if (mouseOver && !mouseDown && Greenfoot.mousePressed(null))
                mouseDown = true;

            // Only enabled controls can accept mouse events
            if (Greenfoot.mouseClicked(this) && enabled)
                internalOnMouseClick.accept(this, Greenfoot.getMouseInfo());

            if (Greenfoot.mouseDragged(this))
            {
                if(mouseStartDragging)
                    mouseStartDragging = false;
                if (!mouseDragging)
                {
                    mouseDragging = true;
                    mouseStartDragging = true;
                }
            }
            else if(Greenfoot.mouseDragEnded(this))
                mouseDragging = false;

            updateState();

            if(isDirty)
            {
                getImage().clear();
                draw(getGraphics());
                isDirty = false;
            }
        }
    }

    /**
     * Class defines a margin object (4 sides).
     */
    public static class Margins
    {
        private int[] margins;

        /**
         * @return Top margin
         */
        public int getTop()     { return margins[0]; }

        /**
         * @return Right margin
         */
        public int getRight()   { return margins[1]; }

        /**
         * @return Bottom margin
         */
        public int getBottom()  { return margins[2]; }

        /**
         * @return Left margin
         */
        public int getLeft()    { return margins[3]; }

        /**
         * Creates a new margin object.
         * @param top    Top margin
         * @param right  Right margin
         * @param bottom Bottom margin
         * @param left   Left margin
         */
        public Margins(int top, int right, int bottom, int left)
        {
            margins = new int[] { top, right, bottom, left };
        }
    }

    /**
     * A set of all possible text alignments.
     */
    public enum ItemAlign
    { JUSTIFY_RIGHT, JUSTIFY_LEFT, CENTER }

    /**
     * A collection of colours and margins which define the GUI style.
     * Inherit the class to implement your own skin/style.
     */
    public interface PicoStyle
    {
        Color getBorderColorNormal();
        Color getBaseColorNormal();
        Color getTextColorNormal();
        Color getBorderColorFocused();
        Color getBaseColorFocused();
        Color getTextColorFocused();
        Color getBorderColorPressed();
        Color getBaseColorPressed();
        Color getTextColorPressed();
        Color getBorderColorDisabled();
        Color getBaseColorDisabled();
        Color getTextColorDisabled();
        Color getTextboxSelectedBg();
        Color getTextboxSelectedFg();

        ItemAlign getTextAlignment();

        int getBorderWidth();
        int getTextSize();
        int getTextSpacing();
        int getButtonBorderWidth();

        Color getBackgroundDefault();
        BakedFontInfo getMetrics();
    }

    /**
     * zlib License
     *
     * Copyright (c) 2014-2020 Ramon Santamaria (@raysan5)
     *
     * This software is provided "as-is", without any express or implied warranty. In no event
     * will the authors be held liable for any damages arising from the use of this software.
     *
     * Permission is granted to anyone to use this software for any purpose, including commercial
     * applications, and to alter it and redistribute it freely, subject to the following restrictions:
     *
     *   1. The origin of this software must not be misrepresented; you must not claim that you
     *   wrote the original software. If you use this software in a product, an acknowledgment
     *   in the product documentation would be appreciated but is not required.
     *
     *   2. Altered source versions must be plainly marked as such, and must not be misrepresented
     *   as being the original software.
     *
     *   3. This notice may not be removed or altered from any source distribution.
     *
     * @author Ramon Santamaria
     */
    private static class PicoStyleDefault implements PicoStyle
    {
        private final Color DEFAULT_BORDER_COLOR_NORMAL     = new Color(131, 131, 131);
        private final Color DEFAULT_BASE_COLOR_NORMAL       = new Color(201, 201, 201);
        private final Color DEFAULT_TEXT_COLOR_NORMAL       = new Color(104, 104, 104);
        private final Color DEFAULT_BORDER_COLOR_FOCUSED    = new Color(91, 178, 217);
        private final Color DEFAULT_BASE_COLOR_FOCUSED      = new Color(201,239,254);
        private final Color DEFAULT_TEXT_COLOR_FOCUSED      = new Color(108,155,188);
        private final Color DEFAULT_BORDER_COLOR_PRESSED    = new Color(4,146,199);
        private final Color DEFAULT_BASE_COLOR_PRESSED      = new Color(151,232,255);
        private final Color DEFAULT_TEXT_COLOR_PRESSED      = new Color(54,139,175);
        private final Color DEFAULT_BORDER_COLOR_DISABLED   = new Color(181,193,194);
        private final Color DEFAULT_BASE_COLOR_DISABLED     = new Color(230,233,233);
        private final Color DEFAULT_TEXT_COLOR_DISABLED     = new Color(174,183,184);
        private final Color DEFAULT_BACKGROUND_COLOR        = new Color(245,245,245);
        private final Color TEXTBOX_COLOR_SELECTED_FG       = new Color(131,154,255);
        private final Color TEXTBOX_COLOR_SELECTED_BG       = new Color(189, 230, 255);

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

        public ItemAlign getTextAlignment()   { return ItemAlign.CENTER ; }

        public int getBorderWidth()           { return 1                ; }
        public int getTextSize()              { return 15               ; }
        public int getTextSpacing()           { return 5                ; }
        public int getButtonBorderWidth()     { return 1                ; }

        public BakedFontInfo getMetrics()     { return CourierNew.getFontInfoPlain(15, 13, 0, 18); }

        private PicoStyleDefault() { }
        private static PicoStyle instance = null;
        public static PicoStyle getInstance()
        {
            return instance == null ? instance = new PicoStyleDefault() : instance;
        }
    }

    /**
     * A neat timer utility that really belongs somewhere else.
     * Usage example:
     * <pre>var timer = new PicoUI.Timer(50, () -&gt; {
     *     On tick code goes here
     * }, false);</pre>
     * Then every act() or so, call <code>timer.Tick()</code> to activate the timer.
     */
    private static class Timer
    {
        private int tick = 0;
        private int timeout;
        private Runnable onTimeout;
        private boolean doRepeat;
        private boolean active = false;

        /**
         * Initializes the timer.
         * @param timeout  Number of ticks() before timeout is triggered
         * @param trip     Method to call when timeout is triggered
         * @param doRepeat Whether the timer should reset itself or stop
         */
        public Timer(int timeout, Runnable trip, boolean doRepeat)
        {
            this.timeout = timeout;
            this.onTimeout = trip;
            this.doRepeat = doRepeat;
        }

        /**
         * Activates the timer.
         */
        public void start()
        {
            active = true;
        }

        /**
         * Disables the timer.
         */
        public void stop()
        {
            active = false;
        }

        /**
         * Tick once and evaluate whether timeout is reached.
         */
        public void tick()
        {
            if(!active) return;
            tick++;
            if(tick == timeout)
            {
                tick = 0;
                if(!doRepeat) active = false;
                onTimeout.run();
            }
        }
    }

    /**
     * Short text utilities that may or may not be useful elsewhere.
     */
    private static class TextUtils
    {
        protected static int getTextHeight(BakedFontInfo fontMetrics, String text, int linem)
        {
            /*if (text.isEmpty() || text.isBlank())
                return 0;*/
            double r = 0;
            for (String ignored : text.split("\n"))
                r += fontMetrics.Ascent.get() - fontMetrics.Descent.get();
            return (int) r + linem * (text.split("\n").length - 1);
        }

        protected static int getTextWidth(BakedFontInfo fontMetrics, String text)
        {
            if (text.isEmpty() /*|| text.isBlank()*/)
                return 0;
            double r = 0;
            for (String line : text.split("\n"))
                r = Math.max(fontMetrics.getStringWidth(line), r);
            return (int) r;
        }

        /**
         * Draws a multiline string using the provided Graphics2D context
         * at a specified location using the given alignment.
         *
         * @param g     Graphics2D context of AWT BufferedImage
         * @param fm    FontMetrics associated with this
         * @param align Alignment of text
         * @param linem Margin between lines of text.
         */
        protected static void drawText(GreenfootImage g, BakedFontInfo fm, String text, int x, int y, ItemAlign align, int linem)
        {
            drawText(g, fm, text, x, y, align, linem, getTextWidth(fm, text));
        }

        /**
         * Draws a multiline string using the provided Graphics2D context
         * at a specified location using the given alignment.
         * See {@link #drawText(GreenfootImage, BakedFontInfo, String, int, int, ItemAlign, int)} for documentation of other arguments.
         *
         * @param width Width of text.
         */
        protected static void drawText(GreenfootImage g, BakedFontInfo fm, String text, int x, int y, ItemAlign align, int linem, int width)
        {
            for (String line : text.split("\n"))
            {
                int off = 0;
                if (align == ItemAlign.CENTER)
                    off = (width - fm.getStringWidth(line)) / 2;
                else if (align == ItemAlign.JUSTIFY_RIGHT)
                    off = width - fm.getStringWidth(line);
                g.drawString(line, x + off, y += fm.Ascent.get() - fm.Descent.get());
                y += linem;
            }
        }

        /**
         * Given a string, figure out which character a given x component
         * best matches (i.e., clicking on a text to move cursor).
         *
         * @param g Graphics2D context of AWT BufferedImage
         * @return The character index best matching the x component.
         */
        protected static int getTextIntersectionIndex(GreenfootImage g, BakedFontInfo fm, String text, int x)
        {
            int lo = 0, hi = fm.getStringWidth(text);
            int lo_idx = 0, hi_idx = text.length();
            for (int i = 0; i <= text.length(); i++)
            {
                var wd = fm.getStringWidth(text.substring(0, i));
                if (wd <= x)
                {
                    lo = wd;
                    lo_idx = i;
                }
                if (wd >= x)
                {
                    hi = wd;
                    hi_idx = i;
                    break;
                }
            }

            return x - lo <= hi - x ? lo_idx : hi_idx; // Which side of the character is x closer to?
        }

        /**
         * Given a character, find the shifted character on a QWERTY keyboard.
         *
         * @param s Character to shift
         * @return Shifted character.
         */
        protected static char getShiftedVersion(char s)
        {
            final String a = "`1234567890-=qwertyuiop[]\\asdfghjkl;'zxcvbnm,./";
            final String b = "~!@#$%^&*()_+QWERTYUIOP{}|ASDFGHJKL:\"ZXCVBNM<>?";
            int i = a.indexOf(s);
            if (i < 0) return s;
            return b.charAt(i);
        }
    }
}
