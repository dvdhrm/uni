// A doctor
public class Doc
{
	// Name (christian and family name)
	String cname;
	String fname;
	// List of assigned patients
	IPList patients;

	public Doc(String cname, String fname)
	{
		this.cname = cname;
		this.fname = fname;
		this.patients = new EmptyPList();
	}

	// Assign new patient to doc
	public void assign(Patient p)
	{
		this.patients = new PairPList(p, this.patients);
	}
}
