// Test station rep
public class TestStation extends de.tuebingen.informatik.Test
{
	Station s1 = new Station("Uhlandstraße");
	Station s2 = new Station("Kurfürstendamm");
	Station s3 = new Station("Wittenbergplatz");
	Station s4 = new Station("Kurfürstenstaße");
	Station s5 = new Station("Gleisdreieck");
	Station s6 = new Station("Möckern-Brücke");
	Station s7 = new Station("Hallesches Tor");
	Station s8 = new Station("Prinzenstraße");
	Station s9 = new Station("Kotbusser Tor");
	Station s10 = new Station("Görlitzer Bahnhof");
	Station s11 = new Station("Schlesisches Tor");
	Station s12 = new Station("Warschauer Straße");

	@org.junit.Test
	public void testName()
	{
		checkExpect(s1.getName(), "Uhlandstraße");
	}

	@org.junit.Test
	public void testLink()
	{
		s1.addTail(s2);
		s1.addTail(s3);
		s1.addTail(s4);
		s1.addTail(s5);
		s1.addTail(s6);
		s1.addTail(s7);
		s1.addTail(s8);
		s1.addTail(s9);
		s1.addTail(s10);
		s1.addTail(s11);
		s1.addTail(s12);
		checkExpect(s1.getPrev(), null);
		checkExpect(s1.getNext().getPrev(), s1);
		checkExpect(s1.getNext().getNext(), s3);
		checkExpect(s1.getNext().getNext().getNext().getPrev(), s3);
		checkExpect(s1.getNext().getNext().getNext().getNext().getNext(), s6);
		checkExpect(s6.getNext().getNext().getNext().getNext().getNext(), s11);
		checkExpect(s11.getNext(), s12);
		checkExpect(s11.getNext().getPrev().getNext(), s12);
		checkExpect(s12.getNext(), null);
	}
}
