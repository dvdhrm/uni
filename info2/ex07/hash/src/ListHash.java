// generate hashes for lists
public class ListHash<T> implements IHashFunction<IList<T>>
{
	// compare for equality
	public boolean same(IList<T> v1, IList<T> v2)
	{
		java.util.Iterator<T> i1 = v1.iterator();
		java.util.Iterator<T> i2 = v2.iterator();

		while (true) {
			if (!i1.hasNext())
				return !i2.hasNext();
			T t1 = i1.next();
			T t2 = i2.next();
			if (!t1.equals(t2))
				return false;
		}
	}

	// return hash
	public int hashCode(IList<T> list)
	{
		int hash = 1;

		for (T v : list)
			hash = hash * 31 + v.hashCode();

		return hash;
	}
}
