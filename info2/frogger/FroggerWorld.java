import draw.*;
import geometry.*;

// Frogger world which passes control between menu
// and frogger.
public class FroggerWorld extends World {
    // Menu
    Menu menu;
    // Frogger
    Frogger frogger;
    // width and height of our canvas
    int w;
    int h;

    // Create new Froggerworld
    // Create new menu but set frogger to null because we don't
    // know w/h pixels, yet.
    public FroggerWorld()
    {
        this.menu = new Menu(true);
        this.frogger = null;
        this.w = 0;
        this.h = 0;
    }

    // Private copy construction
    private FroggerWorld(Menu menu, Frogger frogger, int w, int h)
    {
        this.menu = menu;
        this.frogger = frogger;
        this.w = w;
        this.h = h;
    }

    // Clone this frogger with new menu
    private FroggerWorld clone(Menu menu)
    {
        return new FroggerWorld(menu, this.frogger, this.w, this.h);
    }

    // Clone this frogger with new frogger
    private FroggerWorld clone(Frogger frogger)
    {
        return new FroggerWorld(this.menu, frogger, this.w, this.h);
    }

    // Clone this frogger with new menu and frgger
    private FroggerWorld clone(Menu menu, Frogger frogger)
    {
        return new FroggerWorld(menu, frogger, this.w, this.h);
    }

    // Draw everything
    // Draw menu if menu is active, otherwise draw frogger
    // but only if it exists.
    public boolean draw()
    {
        if (this.menu.isActive())
            return this.menu.draw(this.theCanvas);
        else if (this.frogger != null)
            return this.frogger.draw();
        else
            return true;
    }

    // React on key events. If menu is active, react on
    // exit-events and start-events.
    // Create new frogger if the menu tells us to start a new game.
    public World onKeyEvent(String event)
    {
        if (this.menu.isActive()) {
            Menu menu = this.menu.onKeyEvent(event);
            if (menu.isActive()) {
                return clone(menu);
            }
            else if (menu.isExited()) {
                endOfTime("exit");
                return clone(menu);
            } else {
                // We know w/h pixels now, so create new frogger
                Frogger frogger = new Frogger(new RealScreen(this.theCanvas, new Posn(w, h)), menu.getLevel(), menu.getSmooth());
                return clone(menu, frogger);
            }
        } else if (this.frogger != null) {
            if (event.equals("q")) {
                return clone(this.menu.activate(), null);
            } else {
                return clone(this.frogger.onKeyEvent(event));
            }
        } else {
            return this;
        }
    }

    // React on ticks
    public World onTick()
    {
        if (this.menu.isActive()) {
            return clone(this.menu.onTick());
        } else if (this.frogger != null) {
            Frogger frogger = this.frogger.onTick();
            // If the frogger game exits, reactivate menu
            // and destroy current frogger
            if (frogger == null)
                return clone(this.menu.activate(), null);
            else
                return clone(frogger);
        } else {
            return this;
        }
    }

    // Override BigBang method of World.
    // We need to catch this to get w/h pixel values.
    public boolean bigBang(int w, int h, double speed)
    {
        this.w = w;
        this.h = h;
        // Path control to inherited function
        return super.bigBang(w, h, speed);
    }
}
