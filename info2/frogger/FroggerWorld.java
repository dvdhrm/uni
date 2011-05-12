import draw.*;
import geometry.*;

// Kurzbeschreibung
public class FroggerWorld extends World {
    Frogger frogger;

    public FroggerWorld()
    {
        this.frogger = null;
    }

    public FroggerWorld(Frogger frogger)
    {
        this.frogger = frogger;
    }

    public boolean draw()
    {
        if (this.frogger == null)
            return true;
        return this.frogger.draw();
    }

    private World move(Direction dir)
    {
        return new FroggerWorld(this.frogger.move(dir));
    }

    public World onKeyEvent(String event)
    {
        if (this.frogger == null)
            return this;
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

    public World onTick()
    {
        if (this.frogger == null)
            return this;
        return this;
    }

    public boolean bigBang(int w, int h, double speed)
    {
        boolean ret = super.bigBang(w, h, speed);
        if (ret)
            this.frogger = new Frogger(new RealScreen(this.theCanvas, new Posn(w, h)));
        return ret;
    }
}
