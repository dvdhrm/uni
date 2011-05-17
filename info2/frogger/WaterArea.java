// A water area with stuff saving the frog
public class WaterArea extends ASolidListEntry {
    enum Type { TREE, TURTLE }
    // Smooth moves
    boolean smooth;

    public WaterArea(IScreen screen, int width, boolean right, int speed, int quantum, Type type, boolean smooth)
    {
        super(screen);
        this.smooth = smooth;

        if (type == Type.TREE)
            createTrees(width, right, speed, quantum);
        else // TURTLES
            createTurtles(width, right, speed, quantum);
    }

    public WaterArea(IScreen screen, boolean right, int width, Type type, boolean smooth)
    {
        this(screen, width, right, (int)(Math.random() * 4) + 1, 4, type, smooth);
    }

    public WaterArea(IScreen screen, int width, int quantum, Type type, boolean smooth)
    {
        this(screen, width, Random.rand_bool(), (int)(Math.random() * 4) + 1, quantum, type, smooth);
    }

    public WaterArea(IScreen screen, int width, Type type, boolean smooth)
    {
        this(screen, width, 4, type, smooth);
    }

    // Create trees
    private void createTrees(int width, boolean right, int speed, int quantum)
    {
        int num = quantum;
        int objs = width / num;
        if (objs == 0) {
            this.head = new EmptySolidList(getScreen());
        } else {
            double w = 1.0 / width * 3;
            int move = (int)(Math.random() * (getScreen().getSize().x));

            this.head = new Tree(getScreen().subScreen(new Box(new Coord(), new Coord(w, 1.0))), move, right, speed, smooth);
            for (int i = 1; i < objs; ++i) {
                this.tail = new SolidListEntry(this.head, this.tail);
                this.head = new Tree(getScreen().subScreen(new Box(new Coord(i * 1.0 / objs, 0.0), new Coord(w, 1.0))), move, right, speed, smooth);
            }
        }
    }

    // Create turtles
    private void createTurtles(int width, boolean right, int speed, int quantum)
    {
        int num = quantum;
        int objs = width / num;
        if (objs == 0) {
            this.head = new EmptySolidList(getScreen());
        } else {
            double w = 1.0 / width;
            int move = (int)(Math.random() * (getScreen().getSize().x));

            this.head = new Turtle(getScreen().subScreen(new Box(new Coord(), new Coord(w, 1.0))), move, right, speed, smooth);
            for (int i = 1; i < objs; ++i) {
                this.tail = new SolidListEntry(this.head, this.tail);
                this.head = new Turtle(getScreen().subScreen(new Box(new Coord(i * 1.0 / objs, 0.0), new Coord(w, 1.0))), move, right, speed, smooth);
            }
        }
    }

    // Draw
    public boolean draw()
    {
        return this.getScreen().drawRect(new Box(), new Color(10, 10, 200)) && super.draw();
    }

    // Check whether we kill the frog
    private boolean killsFrog(Frog frog)
    {
        if (getScreen().overlaps(frog.getScreen())) {
            if (!overlaps(frog))
                return true;
        }
        return false;
    }

    // React on ticks
    public WaterArea onTick(Frog frog)
    {
        boolean kills = false;

        // Check whether we kill the frog before AND after
        // we move and only if both are true, we kill it.
        // This avoids bugs if the trees/turtles move the
        // frog with them.
        kills = killsFrog(frog);
        super.onTick(frog);
        if (kills && killsFrog(frog))
            frog.kill();
        
        return this;
    }
}
