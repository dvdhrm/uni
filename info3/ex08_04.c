#include <inttypes.h>
#include <stdio.h>
#include <stdlib.h>

/* implements x^x^...^x^x y-times */
uint64_t xppy(uint64_t r1, uint64_t r2)
{
	uint64_t r0, r3;
	uint64_t r4, r5;

	r0 = 0;
	r4 = r2 + 1;
	while (r4 != 0) {

		r3 = 1;
		r5 = r0;
		while (r5 != 0) {
			r3 *= r1;
			r5 = r5 - 1;
		}
		r0 = r3;

		r4 = r4 - 1;
	}

	return r0;
}

uint64_t xmy(uint64_t x, uint64_t y)
{
	uint64_t i, acc;
	uint64_t j;

	acc = 0;
	i = y;
	while (i != 0) {
		j = x;
		while (j != 0) {
			acc += 1;
			j = j - 1;
		}

		i = i - 1;
	}

	return acc;
}

/* implements floor(x-rt-y) */
uint64_t xrty(uint64_t r1, uint64_t r2)
{
	uint64_t r0, val;
	uint64_t r3;

	r0 = 0;

next:
	r0 = r0 + 1;
	r3 = r1;
	val = 1;

loop:
	if (r3 == 0)
		goto done;
	val = xmy(val, r0);
	r3 = r3 - 1;
	goto loop;
done:

	if (val <= r2)
		goto next;

	return r0 - 1;
}

int main(int argc, char **argv)
{
	int x, y;

	if (argc > 2) {
		x = atoi(argv[1]);
		y = atoi(argv[2]);
	} else {
		x = 2;
		y = 2;
	}

//	printf("%d ^^ %d = %lu\n", x, y, xppy(x, y));
	printf("%d-rt-%d = %lu\n", x, y, xrty(x, y));

	return EXIT_SUCCESS;
}
