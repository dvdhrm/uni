import draw.*;
import geometry.*;

// Kurzbeschreibung
public class Frogger {
    IScreen root;
    ISolid level;
    Frog frog;

    public Frogger(IScreen canvas)
    {
        this.root = new SubScreen(canvas, new Box());
        this.level = LevelMaker.createLevel(this.root, 1);
        this.frog = new Frog(new SubScreen(this.root, new Box(new Coord(), new Coord(0.1, 0.1))));
    }

    public Frogger(IScreen root, ISolid level, Frog frog)
    {
        this.root = root;
        this.level = level;
        this.frog = frog;
    }

    public boolean draw()
    {
        return this.level.draw() && this.frog.draw();
    }

    public Frogger move(Direction dir)
    {
        return new Frogger(this.root, this.level, this.frog.move(dir));
    }
}
