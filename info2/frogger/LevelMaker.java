import geometry.*;

// Level Creator
// Level Creator is a static helper class to create new levels.
// Since levels are independent from pixel resolutions of the root screen,
// this class uses the highly modular functionality of all elements.
//
// To create new levels you just need to create a new ISolidList and return
// it. This list must contain a "startable" element and at least one target.
// All other may be modified.
// The width may be modified but is constant for all elements of one level.
// The frog always has width 1 as width and height.
public class LevelMaker {
    private static int width4()
    {
        return 20;
    }

    // Create Level 4
    private static ISolid level4(IScreen screen, boolean smooth)
    {
        int num = width4();
        double size = 1.0 / num;

        ISolid e_target = new TargetArea(screen.subScreen(new Box(new Coord(0.0, 0.0), new Coord(1.0, size))), num);
        ISolid e_safe = new SafeArea(screen.subScreen(new Box(new Coord(0.0, (num - 1) * size), new Coord(1.0, size))), num);
        ISolidList e_list = new EmptySolidList(screen);

        for (int i = 2; i < num; ++i) {
            if (Random.rand_bool()) {
                if (Random.rand_bool())
                    e_list = e_list.push(new WaterArea(screen.subScreen(new Box(new Coord(0.0, (i - 1) * size), new Coord(1.0, size))),
                                         num, 5, WaterArea.Type.TREE, smooth));
                else
                    e_list = e_list.push(new WaterArea(screen.subScreen(new Box(new Coord(0.0, (i - 1) * size), new Coord(1.0, size))),
                                         num, 3, WaterArea.Type.TURTLE, smooth));
            } else {
                if (Random.rand_bool())
                    e_list = e_list.push(new StreetArea(screen.subScreen(new Box(new Coord(0.0, (i - 1) * size), new Coord(1.0, size))),
                                         num, 6, StreetArea.Type.LKW));
                else
                    e_list = e_list.push(new StreetArea(screen.subScreen(new Box(new Coord(0.0, (i - 1) * size), new Coord(1.0, size))),
                                         num, 5, StreetArea.Type.PKW));
            }
        }

        return new SolidListEntry(e_safe,
            new SolidListEntry(e_list,
            new SolidListEntry(e_target,
            new EmptySolidList(screen))));
    }

    private static int width3()
    {
        return 20;
    }

    // Create Level 3
    private static ISolid level3(IScreen screen, boolean smooth)
    {
        int num = width3();
        double size = 1.0 / num;

        ISolid e_target = new TargetArea(screen.subScreen(new Box(new Coord(0.0, 0.0), new Coord(1.0, size))), num);
        ISolid e_safe = new SafeArea(screen.subScreen(new Box(new Coord(0.0, (num - 1) * size), new Coord(1.0, size))), num);
        ISolidList e_list = new EmptySolidList(screen);

        for (int i = 2; i < num; ++i) {
            if (Random.rand_bool())
                e_list = e_list.push(new WaterArea(screen.subScreen(new Box(new Coord(0.0, (i - 1) * size), new Coord(1.0, size))),
                                     num, 5, WaterArea.Type.TREE, smooth));
            else
                e_list = e_list.push(new WaterArea(screen.subScreen(new Box(new Coord(0.0, (i - 1) * size), new Coord(1.0, size))),
                                     num, 3, WaterArea.Type.TURTLE, smooth));
        }

        return new SolidListEntry(e_safe,
            new SolidListEntry(e_list,
            new SolidListEntry(e_target,
            new EmptySolidList(screen))));
    }

    private static int width2()
    {
        return 20;
    }

    // Create Level 2
    private static ISolid level2(IScreen screen, boolean smooth)
    {
        int num = width2();
        double size = 1.0 / num;

        ISolid e_target = new TargetArea(screen.subScreen(new Box(new Coord(0.0, 0.0), new Coord(1.0, size))), num);
        ISolid e_safe = new SafeArea(screen.subScreen(new Box(new Coord(0.0, (num - 1) * size), new Coord(1.0, size))), num);
        ISolidList e_list = new EmptySolidList(screen);

        for (int i = 2; i < num; ++i) {
            if (Random.rand_bool())
                e_list = e_list.push(new StreetArea(screen.subScreen(new Box(new Coord(0.0, (i - 1) * size), new Coord(1.0, size))),
                                     num, 6, StreetArea.Type.LKW));
            else
                e_list = e_list.push(new StreetArea(screen.subScreen(new Box(new Coord(0.0, (i - 1) * size), new Coord(1.0, size))),
                                     num, 5, StreetArea.Type.PKW));
        }

        return new SolidListEntry(e_safe,
            new SolidListEntry(e_list,
            new SolidListEntry(e_target,
            new EmptySolidList(screen))));
    }

    private static int width1()
    {
        return 10;
    }

    // Create Level 1
    private static ISolid level1(IScreen screen, boolean smooth)
    {
        int num = width1();

        double size = 1.0 / num;
        ISolid e1 = new SafeArea(screen.subScreen(new Box(new Coord(0.0, 9 * size), new Coord(1.0, size))), num);

        ISolid e2_1 = new StreetArea(screen.subScreen(new Box(new Coord(0.0, 8 * size), new Coord(1.0, size))), num, StreetArea.Type.PKW);
        ISolid e2_2 = new StreetArea(screen.subScreen(new Box(new Coord(0.0, 7 * size), new Coord(1.0, size))), num, StreetArea.Type.LKW);
        ISolid e2_3 = new StreetArea(screen.subScreen(new Box(new Coord(0.0, 6 * size), new Coord(1.0, size))), num, StreetArea.Type.PKW);
        ISolid e2 = new SolidListEntry(e2_1,
                    new SolidListEntry(e2_2,
                    new SolidListEntry(e2_3,
                    new EmptySolidList(screen))));

        ISolid e3 = new SafeArea(screen.subScreen(new Box(new Coord(0.0, 5 * size), new Coord(1.0, size))), num);

        ISolid e4_1 = new WaterArea(screen.subScreen(new Box(new Coord(0.0, 4 * size), new Coord(1.0, size))), num, WaterArea.Type.TREE, smooth);
        ISolid e4_2 = new WaterArea(screen.subScreen(new Box(new Coord(0.0, 3 * size), new Coord(1.0, size))), num, WaterArea.Type.TURTLE, smooth);
        ISolid e4_3 = new WaterArea(screen.subScreen(new Box(new Coord(0.0, 2 * size), new Coord(1.0, size))), false, num, WaterArea.Type.TREE, smooth);
        ISolid e4_4 = new WaterArea(screen.subScreen(new Box(new Coord(0.0, 1 * size), new Coord(1.0, size))), true, num, WaterArea.Type.TURTLE, smooth);
        ISolid e4 = new SolidListEntry(e4_1,
                    new SolidListEntry(e4_2,
                    new SolidListEntry(e4_3,
                    new SolidListEntry(e4_4,
                    new EmptySolidList(screen)))));

        ISolid e5 = new TargetArea(screen.subScreen(new Box(new Coord(0.0, 0.0), new Coord(1.0, size))), num);
        return new SolidListEntry(e1,
            new SolidListEntry(e2,
            new SolidListEntry(e3,
            new SolidListEntry(e4,
            new SolidListEntry(e5,
            new EmptySolidList(screen))))));
    }

    // Create level
    public static ISolid createLevel(IScreen screen, int level, boolean smooth)
    {
        if (level == 2)
            return level2(screen, smooth);
        else if (level == 3)
            return level3(screen, smooth);
        else if (level == 4)
            return level4(screen, smooth);
        else
            return level1(screen, smooth);
    }

    // Return level width
    public static int getWidth(int level)
    {
        if (level == 2)
            return width2();
        else if (level == 3)
            return width3();
        else if (level == 4)
            return width4();
        else
            return width1();
    }
}
