// Test line
public class TestLine extends de.tuebingen.informatik.Test
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
	public void test()
	{
		ILine u1 = new PairLine(s1,
				new PairLine(s2,
				new PairLine(s3,
				new PairLine(s4,
				new PairLine(s5,
				new PairLine(s6,
				new PairLine(s7,
				new PairLine(s8,
				new PairLine(s9,
				new PairLine(s10,
				new PairLine(s11,
				new PairLine(s12, new EndOfLine()))))))))))));
		u1.refresh();

		checkExpect(u1.getStation().name, s1.name);
		checkExpect(u1.getRest().getStation().name, s2.name);
		checkExpect(u1.getRest().getRest().getRest().getStation().name, s4.name);
		checkExpect(u1.getRest().getRest().getRest().getStation().passed.getStation().name, s3.name);
		checkExpect(u1.getRest().getRest().getRest().getStation().passed.getRest().getRest().getStation().name, s1.name);
		checkExpect(u1.getRest().getRest().getRest().getRest().getRest().getRest().getRest().getRest().getRest().getRest().getRest().getStation().name, s12.name);
		checkExpect(u1.getStation().passed, new EndOfLine());
		checkExpect(u1.getStation().ahead, u1.getRest());
		checkExpect(u1.getRest().getStation().passed, new PairLine(s1, new EndOfLine()));
		checkExpect(u1.getRest().getRest().getStation().passed.getRest(), u1.getRest().getStation().passed);
	}
}
