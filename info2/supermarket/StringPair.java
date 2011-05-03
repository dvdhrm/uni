// David Herrmann
// Kurzbeschreibung
public class StringPair implements IStringList {
    // list element
    String element;
    // list tail
    IStringList tail;

    public StringPair(String element, IStringList tail)
    {
        this.element = element;
        this.tail = tail;
    }

    // Returns true if the list contains the given string
    public boolean contains(String str)
    {
        return this.element.equals(str) || this.tail.contains(str);
    }
}
