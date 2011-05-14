#include <stdlib.h>
#include <stdio.h>
#include <math.h>

static double func(double x)
{
	return pow(x, 3) - x - 1;
}

static void bisect(double a, double b, int max, double (*f)(double x))
{
	int i;
	double c, fx;

	for (i = 0; i < max; ++i) {
		c = a + (b - a) / 2;
		fx = f(c);

		printf("#%d: a=%lf b=%lf c=%lf f(c)=%lf\n", i, a, b, c, fx);

		if (fx < 0)
			a = c;
		else
			b = c;
	}
}

int main()
{
	bisect(0, 2, 20, func);
	return EXIT_SUCCESS;
}
