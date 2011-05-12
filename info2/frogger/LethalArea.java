// Lethal area
public class LethalArea extends ASolid {
    public LethalArea(IScreen screen)
    {
        super(screen);
    }

    // Draw this object
    public boolean draw()
    {
        return this.screen.drawRect(new Box(), new Color("grey"));
    }
}
