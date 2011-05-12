import java.awt.*;

// RGB color
public class Color {
    // RGB color parts
    int r;
    int g;
    int b;

    public Color(int r, int g, int b)
    {
        set(r, g, b);
    }

    public Color(int sat)
    {
        set(sat);
    }

    public Color(String name)
    {
        set(name);
    }

    // Set color
    public void set(int r, int g, int b)
    {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    // Set saturation
    public void set(int sat)
    {
        set(sat, sat, sat);
    }

    // Set color by string
    public void set(String name)
    {
        if (name.equals("green"))
            set(0, 255, 0);
        else if (name.equals("grey"))
            set(150);
        else
            set(0, 0, 0);
    }

    // Return AWT color object
    public java.awt.Color toAWT()
    {
        return new java.awt.Color(this.r, this.g, this.b);
    }
}
