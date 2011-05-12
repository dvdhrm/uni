// List of solids
// IMPORTANT:
// Every entry in the list works on the same layer in scenery, that is,
// the screen of every element has the same parent, even though the
// bounding boxes may differ.
public interface ISolidList extends ISolid {
    // Push element to the top of the list and return new list
    public ISolidList push(ISolid ele);
}
