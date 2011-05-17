// A street area with cars killing the frog
public class StreetArea extends ASolidListEntry {
    enum Type { PKW, LKW }

    public StreetArea(IScreen screen, int width, boolean right, int speed, int quantum, Type type)
    {
        super(screen);

        if (type == Type.PKW)
            createPKWs(width, right, speed, quantum);
        else // LKW
            createLKWs(width, right, speed, quantum);
    }

    public StreetArea(IScreen screen, int width, int quantum, Type type)
    {
        this(screen, width, Random.rand_bool(), (int)(Math.random() * 4) + 1, quantum, type);
    }

    public StreetArea(IScreen screen, int width, Type type)
    {
        this(screen, width, 4, type);
    }

    // Create street with PKWs
    private void createPKWs(int width, boolean right, int speed, int quantum)
    {
        int num = quantum;
        int cars = width / num;
        if (cars == 0) {
            this.head = new EmptySolidList(getScreen());
        } else {
            double w = 1.0 / width;
            int move = (int)(Math.random() * (getScreen().getSize().x));

            this.head = new PKW(getScreen().subScreen(new Box(new Coord(), new Coord(w, 1.0))), move, right, speed);
            for (int i = 1; i < cars; ++i) {
                this.tail = new SolidListEntry(this.head, this.tail);
                this.head = new PKW(getScreen().subScreen(new Box(new Coord(i * 1.0 / cars, 0.0), new Coord(w, 1.0))), move, right, speed);
            }
        }
    }

    // Create street with LKWs
    private void createLKWs(int width, boolean right, int speed, int quantum)
    {
        int num = quantum;
        int cars = width / num;
        if (cars == 0) {
            this.head = new EmptySolidList(getScreen());
        } else {
            double w = 1.0 / width * 2;
            int move = (int)(Math.random() * (getScreen().getSize().x));

            this.head = new LKW(getScreen().subScreen(new Box(new Coord(), new Coord(w, 1.0))), move, right, speed);
            for (int i = 1; i < cars; ++i) {
                this.tail = new SolidListEntry(this.head, this.tail);
                this.head = new LKW(getScreen().subScreen(new Box(new Coord(i * 1.0 / cars, 0.0), new Coord(w, 1.0))), move, right, speed);
            }
        }
    }

    // Draw street and cars
    public boolean draw()
    {
        return this.getScreen().drawRect(new Box(), new Color("grey")) && super.draw();
    }
}
