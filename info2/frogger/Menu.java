import draw.*;
import geometry.*;

// Menu
public class Menu {
    // Currentl selected level
    int level;
    // Current selected menu element and list of all elements
    int highlighted;
    int LEVEL = 0;
    int SMOOTH = 1;
    int START = 2;
    int EXIT = 3;
    int NUM = 4;

    // Is menu active?
    boolean active;
    // Did the user select "Start game"?
    boolean start;
    // Smooth mode enabled?
    boolean smooth;

    // Public constructor
    public Menu(boolean active)
    {
        // Set level to default
        this.level = 1;
        this.highlighted = 0;
        this.active = active;
        this.start = false;
        this.smooth = true;
    }

    // Draw single entry in menu
    private boolean drawEntry(Canvas target, int entry, Posn pos, boolean targeted)
    {
        // Targeted/highlighted elements are shifted to the right for 20 pixels
        if (targeted)
            pos = new Posn(pos.x + 20, pos.y);
        if (entry == LEVEL)
            return target.drawString(pos, "Select Level: " + level);
        else if (entry == SMOOTH)
            return target.drawString(pos, "Smooth Mode: " + smooth);
        else if (entry == START)
            return target.drawString(pos, "Start Game");
        else if (entry == EXIT)
            return target.drawString(pos, "Exit Game");
        else
            return target.drawString(pos, "<unknown>");
    }

    // Draw menu
    public boolean draw(Canvas target)
    {
        int x = 50;
        return drawEntry(target, LEVEL, new Posn(x, 20), this.highlighted == LEVEL)
            && drawEntry(target, SMOOTH, new Posn(x, 40), this.highlighted == SMOOTH)
            && drawEntry(target, START, new Posn(x, 60), this.highlighted == START)
            && drawEntry(target, EXIT, new Posn(x, 80), this.highlighted == EXIT);
    }

    // One menu entry down
    public void oneDown()
    {
        if (this.highlighted < NUM - 1)
            this.highlighted++;
    }

    // One menu entry up
    public void oneUp()
    {
        if (this.highlighted > 0)
            this.highlighted--;
    }

    // One menu entry right
    public void oneRight()
    {
        if (this.highlighted == LEVEL && this.level < 10)
            this.level++;
        else if (this.highlighted == SMOOTH)
            this.smooth = !this.smooth;
    }

    // One menu entry left
    public void oneLeft()
    {
        if (this.highlighted == LEVEL && this.level > 1)
            this.level--;
        else if (this.highlighted == SMOOTH)
            this.smooth = !this.smooth;
    }

    // Execute menu entry
    public void enter()
    {
        if (this.highlighted == START) {
            this.active = false;
            this.start = true;
        } else if (this.highlighted == EXIT) {
            this.active = false;
            this.start = false;
        }
    }

    // Parese key events
    public Menu onKeyEvent(String event)
    {
        if (event.equals("up"))
            oneUp();
        else if (event.equals("down"))
            oneDown();
        else if (event.equals("left"))
            oneLeft();
        else if (event.equals("right"))
            oneRight();
        else if (event.equals("\n"))
            enter();
        return this;
    }

    // React on ticks
    public Menu onTick()
    {
        return this;
    }

    // Return currently selected level
    public int getLevel()
    {
        return this.level;
    }

    // Return true if smooth mode is enabled
    public boolean getSmooth()
    {
        return this.smooth;
    }

    // Return whetehr menu is active
    public boolean isActive()
    {
        return this.active;
    }

    // Activate menu
    public Menu activate()
    {
        return new Menu(true);
    }

    // Return true if the user select "Exit Game" entry
    public boolean isExited()
    {
        return !this.start;
    }
}
