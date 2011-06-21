// test doc/patient relationships
public class DocTests extends de.tuebingen.informatik.Test
{
	Doc d1 = new Doc("M", "H");
	Doc doc = new Doc("D", "H");
	Patient p1 = new Patient("C", "H", true, "0", doc);
	Patient p2 = new Patient("A", "H", false, "0", doc);
	Patient p3 = new Patient("M", "H", true, "0", doc);

	@org.junit.Test
	public void testDoc()
	{
		checkExpect(d1.patients, new EmptyPList());
		checkExpect(p1.doc, doc);
		checkExpect(p2.doc, doc);
		checkExpect(p3.doc, doc);
		checkExpect(doc.patients, new PairPList(p3, new PairPList(p2, new PairPList(p1, new EmptyPList()))));
	}
}
