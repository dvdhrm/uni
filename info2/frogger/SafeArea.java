// A safe area that does not kill a frog (good startpoint)
public class SafeArea extends ASolid {
    public SafeArea(IScreen screen)
    {
        super(screen);
    }

    // Draw this object
    public boolean draw()
    {
        return this.screen.drawRect(new Box(), new Color("green"));
    }
}
