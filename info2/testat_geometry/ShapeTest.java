// Test shapes
public class ShapeTest extends de.tuebingen.informatik.Test {
    // Lengths
    Length len0 = new Length(0.0);
    Length len0_9 = new Length(0.9);
    Length len1 = new Length(1.0);
    Length len2 = new Length(2.0);
    Length len3 = new Length(3.0);
    Length len4 = new Length(4.0);
    Length len5 = new Length(5.0);
    Length len6 = new Length(6.0);
    Length len10 = new Length(10.0);
    Length len20 = new Length(20.0);

    // Squares
    IShape square1 = new Square(new Point(len0, len0), len5);
    IShape square2 = new Square(new Point(len5, len5), len0);
    IShape square3 = new Square(new Point(len2, len2), len2);
    IShape square4 = new Square(new Point(len3, len3), len2);

    // Circles
    IShape circle1 = new Circle(new Point(len0, len0), len1);
    IShape circle2 = new Circle(new Point(len2, len2), len2);

    // Dis/Con-junctions
    IShape disjunc1 = new Disjunction(square3, square4);
    IShape conjunc1 = new Conjunction(square3, square4);

    @org.junit.Test
    public void testLength()
    {
        checkExpect(len0.extend(len1), len1);
        checkExpect(len1.reduce(len2), len1.invert());
        checkExpect(len2.square(), len4);
        checkExpect(len10.sqrt().smallerThan(len4), true);
    }

    @org.junit.Test
    public void testSquares()
    {
        checkExpect(square1.contains(new Point(len0, len0)), true);
        checkExpect(square1.contains(new Point(len5, len5)), false);
        checkExpect(square2.contains(new Point(len5, len5)), false);
        checkExpect(square2.contains(new Point(len0, len0)), false);
        checkExpect(square3.contains(new Point(len2, len2)), true);
        checkExpect(square3.contains(new Point(len3, len3)), true);
        checkExpect(square3.contains(new Point(len4, len4)), false);
    }

    @org.junit.Test
    public void testCircles()
    {
        checkExpect(circle1.contains(new Point(len0, len0)), true);
        checkExpect(circle1.contains(new Point(len1, len1)), false);
        checkExpect(circle1.contains(new Point(len0_9, len0)), true);
        checkExpect(circle1.contains(new Point(len0_9, len0_9)), false);
        checkExpect(circle2.contains(new Point(len0, len0)), false);
        checkExpect(circle2.boundingBox().contains(new Point(len0, len0)), true);
        checkExpect(circle2.boundingBox().contains(new Point(len1.invert(), len1.invert())), false);
        checkExpect(circle2.boundingBox().contains(new Point(len3, len3)), true);
        checkExpect(circle2.boundingBox().contains(new Point(len4, len4)), false);
        checkExpect(circle2.contains(new Point(len1, len1)), true);
        checkExpect(circle2.contains(new Point(len1.reduce(len0_9), len1.reduce(len0_9))), false);
    }

    @org.junit.Test
    public void testDisjunction()
    {
        checkExpect(new Disjunction(circle2, square3).contains(new Point(len0, len0)), false);
        checkExpect(new Disjunction(circle2, square3).contains(new Point(len3, len3)), true);
        checkExpect(new Disjunction(circle2, square3).contains(new Point(len1, len1)), true);
        checkExpect(disjunc1.boundingBox().contains(new Point(len0, len0)), false);
        checkExpect(disjunc1.boundingBox().contains(new Point(len2, len4)), true);
        checkExpect(disjunc1.boundingBox().contains(new Point(len5, len5)), false);
    }

    @org.junit.Test
    public void testConjunction()
    {
        checkExpect(new Conjunction(circle2, square3).contains(new Point(len0, len0)), false);
        checkExpect(new Conjunction(circle2, square3).contains(new Point(len3, len3)), true);
        checkExpect(new Conjunction(circle2, square3).contains(new Point(len1, len1)), false);
        checkExpect(conjunc1.boundingBox().contains(new Point(len0, len0)), false);
        checkExpect(conjunc1.boundingBox().contains(new Point(len2, len4)), false);
        checkExpect(conjunc1.boundingBox().contains(new Point(len3, len3)), true);
    }
}
