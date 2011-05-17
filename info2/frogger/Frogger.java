import draw.*;
import geometry.*;

// Frogger class which controls the game
public class Frogger {
    // Root screen
    IScreen root;
    // The level landscape
    ISolid level;
    // The user controlled frog
    Frog frog;
    // Number of lives left
    int lives;
    // The level that should be run
    int lnum;

    // Start new frogger with given root-screen and level
    public Frogger(IScreen canvas, int level, boolean smooth)
    {
        this.root = canvas;
        // Create new level with static LevelMaker
        this.level = LevelMaker.createLevel(this.root, level, smooth);
        this.lnum = level;
        // New frog
        this.frog = newFrog();
        // By default 5 lives to go
        this.lives = 5;
    }

    // Private copy constructor
    private Frogger(IScreen root, ISolid level, Frog frog, int lives, int lnum)
    {
        this.root = root;
        this.level = level;
        this.frog = frog;
        this.lives = lives;
        this.lnum = lnum;
    }

    // Create new frog
    private Frog newFrog()
    {
        // Get frog width
        double w = 1.0 / LevelMaker.getWidth(this.lnum);
        // Create subscreen for new frog with root-screen as parent
        // Get start position from level
        return new Frog(this.root.subScreen(this.level.getStart().getScreen().getPos(), new Coord(w, w)));
    }

    // Draw text overlays
    private boolean drawText()
    {
        return this.root.drawString(new Posn(5, 15), "Lives: " + this.lives);
    }

    // Draw frogger
    public boolean draw()
    {
        return this.level.draw() && this.frog.draw() && drawText();
    }

    // Move frog in the given direction
    private Frogger move(Direction dir)
    {
        // Create new frog but discard it if the new position would be
        // blocked by the landscape
        Frog frog = this.frog.move(dir);
        if (this.level.blocks(frog))
            return this;
        else
            return new Frogger(this.root, this.level, frog, this.lives, this.lnum);
    }

    // React on key events
    public Frogger onKeyEvent(String event)
    {
        if (event.equals("up"))
            return move(Direction.UP);
        else if (event.equals("down"))
            return move(Direction.DOWN);
        else if (event.equals("left"))
            return move(Direction.LEFT);
        else if (event.equals("right"))
            return move(Direction.RIGHT);
        else
            return this;
    }

    // React on tick events
    public Frogger onTick()
    {
        // Create new frog and test whether the frog is dead
        // Return null if no more lives are left or no more
        // available targets (todos)
        Frog frog = this.frog.onTick(null);
        if (frog.needsReset()) {
            if (frog.isDead())
                this.lives--;
            if (0 == this.lives)
                return null;
            frog = newFrog();
            this.level.reset();
            if (this.level.todos() == 0)
                return null;
        }
        // Send tick to level with frog as REFERENCE!
        ISolid level = this.level.onTick(frog);
        return new Frogger(this.root, level, frog, lives, this.lnum);
    }
}
