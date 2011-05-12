// Target area
public class TargetArea extends ASolid {
    public TargetArea(IScreen screen)
    {
        super(screen);
    }

    // Draw this object
    public boolean draw()
    {
        return this.screen.drawRect(new Box(), new Color("green"));
    }
}
