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

    // Returns true if we overlap with $other
    public boolean overlaps(Box other)
    {
        if (other.pos.x < this.pos.x) {
            if (other.pos.x + other.size.x <= this.pos.x)
                return false;
        }
        else if (this.pos.x + this.size.x <= other.pos.x)
            return false;
        if (other.pos.y < this.pos.y) {
            if (other.pos.y + other.size.y <= this.pos.y)
                return false;
        }
        else if (this.pos.y + this.size.y <= other.pos.y)
            return false;
        return true;
    }
}
