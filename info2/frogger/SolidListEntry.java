// Entry in solid list
public class SolidListEntry extends ASolidListEntry {
    public SolidListEntry(ISolid head, ISolidList tail)
    {
        super(head, tail);
    }

    public SolidListEntry(IScreen screen, ISolid head)
    {
        super(screen, head);
    }

    protected SolidListEntry(IScreen screen)
    {
        super(screen);
    }
}
