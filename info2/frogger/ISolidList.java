// List of solids
// IMPORTANT:
// Every entry in the list works on the same layer in scenery, that is,
// the screen of every element has the same parent, even though the
// bounding boxes may differ.
// The screen of the list is the one of the last element, that is, the
// empty list.
public interface ISolidList extends ISolid {
    // React on time event
    public ISolidList onTick(Frog frog);

    // Push element
    public ISolidList push(ISolid ele);
}
