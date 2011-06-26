// a rail line
public interface ILine
{
	// Refresh helper
	// Helper function for refresh. It gets passed as argument
	// a list of all stations that where passed and shall return
	// a list of all stations ahead (including itself).
	public ILine refresh(ILine passed);

	// Refresh line
	// This refreshes the whole ILine so all stations
	// are updated with the correct "ahead" and "passed"
	// lines.
	public void refresh();

	// Getter
	public Station getStation();
	public ILine getRest();
}
