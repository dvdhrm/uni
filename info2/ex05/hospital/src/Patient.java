// a patient
public class Patient
{
	// name
	String cname;
	String fname;
	// is male?
	boolean male;
	// blood group
	String bgroup;
	// assigned doc
	Doc doc;

	public Patient(String cname, String fname, boolean male, String bgroup, Doc doc)
	{
		this.cname = cname;
		this.fname = fname;
		this.male = male;
		this.bgroup = bgroup;
		this.doc = doc;
		this.doc.assign(this);
	}
}
