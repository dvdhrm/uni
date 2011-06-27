/*
 * Written 2011 by David Herrmann <dh.herrmann@googlemail.com>
 * Dedicated to the Public Domain
 */

#include <stdlib.h>
#include <stdio.h>
#include <string.h>

static const int enc_mat[15][11] =
		{
			{1, 1, 0, 1, 1, 0, 1, 0, 1, 0, 1},
			{1, 0, 1, 1, 0, 1, 1, 0, 0, 1, 1},
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 1, 1, 1, 0, 0, 0, 1, 1, 1, 1},
			{0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1},
			{0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
		};

static const int dec_mat[11][15] =
		{
			{0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
		};

static const int par_mat[4][15] =
		{
			{1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1},
			{0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1},
			{0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 1, 1, 1, 1},
			{0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1},
		};

static void hamming_enc(const int in[11], int out[15])
{
	size_t i, j;

	for (i = 0; i < 15; ++i) {
		out[i] = 0;
		for (j = 0; j < 11; ++j) {
			out[i] += enc_mat[i][j] * in[j];
		}
		out[i] %= 2;
	}
}

static void hamming_dec(const int in[15], int out[11])
{
	size_t i, j;

	for (i = 0; i < 11; ++i) {
		out[i] = 0;
		for (j = 0; j < 15; ++j) {
			out[i] += dec_mat[i][j] * in[j];
		}
		out[i] %= 2;
	}
}

static int hamming_par(const int in[15], int res[15])
{
	size_t i, j;
	int out[4];

	for (i = 0; i < 4; ++i) {
		out[i] = 0;
		for (j = 0; j < 15; ++j) {
			out[i] += par_mat[i][j] * in[j];
		}
		out[i] %= 2;
	}

	i = out[3] * 8 + out[2] * 4 + out[1] * 2 + out[0];
	memcpy(res, in, sizeof(int) * 15);
	if (i > 0)
		res[i - 1] = !res[i - 1];

	return i;
}

static void show(const int *v, size_t size)
{
	size_t i;

	for (i = 0; i < size; ++i)
		printf("%d", v[i]);
	printf("\n");
}

int main()
{
	const int d0[] = {1, 0, 0, 1, 0, 0, 1, 0, 1, 1, 0};
	const int d1[] = {0, 1, 0, 0, 1, 0, 1, 1, 0, 1, 1};
	const int e0[] = {1, 1, 1, 1, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 1};
	const int e1[] = {1, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0};
	const int e2[] = {1, 1, 1, 1, 0, 0, 1, 0, 1, 0, 0, 1, 1, 1, 1};
	int buf[15];
	int buf2[15];
	int buf3[15];

	/* Ex 1 */
	printf("Ex1\n");
	printf("  Data:      ");
	show(d0, 11);

	hamming_enc(d0, buf);
	printf("  Enc:       ");
	show(buf, 15);
	printf("  Wrong bit: %d\n", hamming_par(buf, buf3));

	hamming_dec(buf, buf2);
	printf("  Dec-wrong: ");
	show(buf2, 11);

	hamming_dec(buf, buf3);
	printf("  Dec-right: ");
	show(buf3, 11);


	/* Ex 2 */
	printf("Ex2\n");
	printf("  Data:      ");
	show(d1, 11);

	hamming_enc(d1, buf);
	printf("  Enc:       ");
	show(buf, 15);
	printf("  Wrong bit: %d\n", hamming_par(buf, buf3));

	hamming_dec(buf, buf2);
	printf("  Dec-wrong: ");
	show(buf2, 11);

	hamming_dec(buf, buf3);
	printf("  Dec-right: ");
	show(buf3, 11);


	/* Ex 3 */
	printf("Ex3\n");
	printf("  Enc: ");
	show(e0, 15);
	printf("  Wrong bit: %d\n", hamming_par(e0, buf));

	hamming_dec(e0, buf2);
	printf("  Dec-wrong: ");
	show(buf2, 11);

	hamming_dec(buf, buf2);
	printf("  Dec-right: ");
	show(buf2, 11);


	/* Ex 4 */
	printf("Ex4\n");
	printf("  Enc: ");
	show(e1, 15);
	printf("  Wrong bit: %d\n", hamming_par(e1, buf));

	hamming_dec(e1, buf2);
	printf("  Dec-wrong: ");
	show(buf2, 11);

	hamming_dec(buf, buf2);
	printf("  Dec-right: ");
	show(buf2, 11);


	/* Ex 5 */
	printf("Ex5\n");
	printf("  Enc: ");
	show(e2, 15);
	printf("  Wrong bit: %d\n", hamming_par(e2, buf));

	hamming_dec(e2, buf2);
	printf("  Dec-wrong: ");
	show(buf2, 11);

	hamming_dec(buf, buf2);
	printf("  Dec-right: ");
	show(buf2, 11);

	return EXIT_SUCCESS;
}
