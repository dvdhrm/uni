// Common car class
public abstract class ACar extends ASolid {
    // Direction
    boolean right;
    // Speed
    int speed;

    public ACar(IScreen screen, int move, boolean right, int speed)
    {
        super(screen.moveRound(move, 0));
        this.right = right;
        this.speed = speed;
    }

    public ACar(IScreen screen)
    {
        this(screen, 0, true, 5);
    }
}
