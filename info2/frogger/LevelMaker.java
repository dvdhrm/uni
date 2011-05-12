import geometry.*;

// Level Creator
public class LevelMaker {
    private static ISolid mkGround(IScreen screen, int num)
    {
        ISolid frog = new Frog(new SubScreen(screen, new Box(new Coord(0.0, 0.0), new Coord(0.5, 0.5))));
        ISolid e1 = new SafeArea(new SubScreen(screen, new Box(new Coord(0.0, 0.9), new Coord(1.0, 0.1))));
        ISolid e2 = new LethalArea(new SubScreen(screen, new Box(new Coord(0.0, 0.6), new Coord(1.0, 0.3))));
        ISolid e3 = new SafeArea(new SubScreen(screen, new Box(new Coord(0.0, 0.5), new Coord(1.0, 0.1))));
        ISolid e4 = new LethalArea(new SubScreen(screen, new Box(new Coord(0.0, 0.2), new Coord(1.0, 0.3))));
        ISolid e5 = new TargetArea(new SubScreen(screen, new Box(new Coord(0.0, 0.0), new Coord(1.0, 0.2))));
        return new SolidListEntry(e1,
            new SolidListEntry(e2,
            new SolidListEntry(e3,
            new SolidListEntry(e4,
            new SolidListEntry(e5,
            new EmptySolidList(screen))))));
    }

    public static ISolid createLevel1(IScreen screen, int num)
    {
        return mkGround(screen, num);
    }

    public static ISolid createLevel(IScreen screen, int level)
    {
        return createLevel1(screen, 0);
    }
}
