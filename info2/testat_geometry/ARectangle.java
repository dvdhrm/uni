import draw.*;
import geometry.*;
import colors.*;

// A rectangle
public abstract class ARectangle extends AShape implements IRectangle {
    // Position of lower left corner
    Point pos;

    ARectangle(Point pos)
    {
        this.pos = pos;
    }

    // Returns bounding box
    public IRectangle boundingBox()
    {
        // Bounding box is the shape as is
        return this;
    }

    // Creates a new square that is the bounding box of two squares
    public IRectangle include(IRectangle other)
    {
        Point lowLeft = lowLeft();
        Point upRight = upRight();
        Point olowLeft = other.lowLeft();
        Point oupRight = other.upRight();
        Length leftMost;
        Length rightMost;
        Length lowMost;
        Length upMost;

        if (lowLeft.x.smallerThan(olowLeft.x))
            leftMost = lowLeft.x;
        else
            leftMost = olowLeft.x;

        if (lowLeft.y.smallerThan(olowLeft.y))
            lowMost = lowLeft.y;
        else
            lowMost = olowLeft.y;

        if (upRight.x.smallerThan(oupRight.x))
            rightMost = oupRight.x;
        else
            rightMost = upRight.x;

        if (upRight.y.smallerThan(oupRight.y))
            upMost = oupRight.y;
        else
            upMost = upRight.y;

        return new Rectangle(new Point(leftMost, lowMost), rightMost.reduce(leftMost), upMost.reduce(lowMost));
    }

    // Returns the intersection of two squares
    public IRectangle intersect(IRectangle other)
    {
        Point lowLeft = lowLeft();
        Point upRight = upRight();
        Point olowLeft = other.lowLeft();
        Point oupRight = other.upRight();
        Length leftMost;
        Length rightMost;
        Length lowMost;
        Length upMost;

        if (lowLeft.x.smallerThan(olowLeft.x))
            leftMost = olowLeft.x;
        else
            leftMost = lowLeft.x;

        if (lowLeft.y.smallerThan(olowLeft.y))
            lowMost = olowLeft.y;
        else
            lowMost = lowLeft.y;

        if (upRight.x.smallerThan(oupRight.x))
            rightMost = upRight.x;
        else
            rightMost = oupRight.x;

        if (upRight.y.smallerThan(oupRight.y))
            upMost = upRight.y;
        else
            upMost = oupRight.y;

        return new Rectangle(new Point(leftMost, lowMost), rightMost.reduce(leftMost), upMost.reduce(lowMost));
    }

    // Draw to canvas
    public void draw(Canvas canvas, IColor col)
    {
        canvas.drawRect(new Posn((int)this.pos.x.length, (int)this.pos.y.length),
            (int)upRight().x.reduce(this.pos.x).length,
            (int)upRight().y.reduce(this.pos.y).length, col);
    }
}
