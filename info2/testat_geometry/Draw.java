import draw.*;
import colors.*;

// Draw examples
public class Draw {
    Canvas canvas;

    // Lengths
    Length len0 = new Length(0.0);
    Length len0_9 = new Length(9.0);
    Length len1 = new Length(10.0);
    Length len2 = new Length(20.0);
    Length len3 = new Length(30.0);
    Length len4 = new Length(40.0);
    Length len5 = new Length(50.0);
    Length len6 = new Length(60.0);
    Length len10 = new Length(100.0);
    Length len20 = new Length(200.0);

    // Squares
    IShape square1 = new Square(new Point(len0, len0), len5);
    IShape square3 = new Square(new Point(len2, len2), len2);
    IShape square4 = new Square(new Point(len4, len4), len4);

    // Circles
    IShape circle1 = new Circle(new Point(len0, len0), len1);
    IShape circle2 = new Circle(new Point(len3, len4), len2);

    // Dis/Con-junctions
    IShape disjunc1 = new Disjunction(square4, circle2);
    IShape conjunc1 = new Conjunction(square4, circle2);

    Draw()
    {
        this.canvas = new Canvas(150, 150, "Example Shapes");
        this.canvas.show();
        draw1();
    }

    public void draw1()
    {
        this.disjunc1.boundingBox().draw(this.canvas, new Black());
        this.disjunc1.draw(this.canvas, new Red());
        this.conjunc1.boundingBox().draw(this.canvas, new Green());
    }
}
