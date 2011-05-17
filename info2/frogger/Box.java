// Bounding box of an element
public class Box {
    // Position
    public Coord pos;
    // Size
    public Coord size;

    public Box(Coord pos, Coord size)
    {
        this.pos = pos;
        this.size = size;
    }

    public Box(Coord size)
    {
        this.pos = new Coord();
        this.size = size;
    }

    public Box()
    {
        this.pos = new Coord();
        this.size = new Coord(1.0, 1.0);
    }
}
