// A Length datatype in an arbitrary environment that
// my be scaled without borders => hence double
// A length may be negative
public class Length {
    // Length in arbitrary but uniform scale
    double length;

    Length(double length)
    {
        this.length = length;
    }

    // Returns true if we are smaller than $len
    public boolean smallerThan(Length len)
    {
        if (this.length < len.length)
            return true;
        else
            return false;
    }

    // Returns inverted length
    public Length invert()
    {
        return new Length(-this.length);
    }

    // Extends length with $len
    public Length extend(Length len)
    {
        return new Length(this.length + len.length);
    }

    // Extends by a factor
    public Length multiply(double num)
    {
        return new Length(this.length * num);
    }

    // Reduce
    public Length reduce(Length len)
    {
        return new Length(this.length - len.length);
    }

    // Square length
    public Length square()
    {
        return new Length(this.length * this.length);
    }

    // Return positive square root of length
    public Length sqrt()
    {
        return new Length(Math.sqrt(this.length));
    }
}
