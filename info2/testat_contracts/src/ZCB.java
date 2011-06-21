// A zero-coupon-bond that pays amount X to the holder
public class ZCB extends Scaled
{
	public ZCB(final double amount)
	{
		super(new One(), new ConstObservable<Double>(amount));
	}
}
