// A safe area that does not kill a frog (good startpoint)
public class SafeArea extends SolidListEntry {
    public SafeArea(IScreen screen, int width)
    {
        super(screen);

        if (width == 0)
            throw new AssertionError("Cannot create empty SafeArea");

        double w = 1.0 / width;
        int start = width / 2;

        this.head = new SafeTile(screen.subScreen(new Box(new Coord(), new Coord(w, 1.0))), 1 == start);
        for (int i = 1; i < width; ++i) {
            this.tail = new SolidListEntry(this.head, this.tail);
            this.head = new SafeTile(screen.subScreen(new Box(new Coord(i * w, 0.0), new Coord(w, 1.0))), i == start);
        }
    }
}
