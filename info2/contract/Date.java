// David Herrmann
// Single date
public class Date
{
    // Year (eg. 2011, 1991, 2000)
    int year;
    // Month (1-12)
    int month;
    // Day (1-31)
    int day;

    public Date(int year, int month, int day)
    {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public boolean isEarlierThan(Date date)
    {
        if (this.year < date.year)
            return true;
        else if (this.year > date.year)
            return false;
        else if (this.month < date.month)
            return true;
        else if (this.month > date.month)
            return false;
        else if (this.day < date.day)
            return true;
        else
            return false;
    }
}
