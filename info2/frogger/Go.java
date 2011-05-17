// Test class
// Run: new Go() to see this example
public class Go {
    public Go()
    {
        // Create new frogger world
        FroggerWorld world = new FroggerWorld();
        // Start world with 600x600 pixels and 30 ticks per second
        world.bigBang(600, 600, 1 / 30.0);
    }
}
