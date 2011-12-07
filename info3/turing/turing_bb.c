/*
 * Written 2011 by David Herrmann <dh.herrmann@googlemail.com>
 * Dedicated to the Public Domain
 */

/*
 * Find best 3-state BusyBeaver.
 * This tests all possible configurations of 3-state turing machines on a 2
 * symbol alphabet. It takes about a day to test all possible 16,777,216
 * machines on my Intel Atom.
 */

#include <errno.h>
#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
#include "turing.h"

struct config {
	const char *sigma;
	size_t ssize;
	const char *gamma;
	size_t gsize;
	char blank;
	const char *begin;
	const char *end;
	const struct turing_init *trans;
};

static int setup_config(struct turing_machine **out, struct turing_band **bout,
						const struct config *conf)
{
	struct turing_machine *mach;
	struct turing_band *band;
	int ret;

	ret = turing_machine_new(&mach, conf->blank);
	if (ret)
		return ret;

	ret = turing_machine_init(mach, conf->sigma, conf->ssize,
		conf->gamma, conf->gsize, conf->begin, conf->end, conf->trans);
	if (ret)
		goto err_mach;

	ret = turing_band_new(&band, NULL, 60, 30, conf->blank);
	if (ret)
		goto err_mach;

	*out = mach;
	*bout = band;
	return 0;

err_mach:
	turing_machine_unref(mach);
	return ret;
}

static char bb3_sigma[] = { 'x' };
static char bb3_gamma[] = { };
static struct turing_init bb3_trans[] = {
	{ "A", '_', "A", 'x', TURING_RIGHT },
	{ "A", 'x', "A", 'x', TURING_RIGHT },
	{ "B", '_', "B", 'x', TURING_RIGHT },
	{ "B", 'x', "B", 'x', TURING_RIGHT },
	{ "C", '_', "C", 'x', TURING_RIGHT },
	{ "C", 'x', "C", 'x', TURING_RIGHT },
	TURING_INIT_END
};
static struct config bb3_conf = {
	bb3_sigma, sizeof(bb3_sigma),
	bb3_gamma, sizeof(bb3_gamma),
	'_',
	"A", "END",
	bb3_trans
};

static void try()
{
	static size_t maximum = 0;
	static size_t counter = 0;
	static time_t last1, last2, last3;
	int ret;
	struct turing_machine *mach;
	struct turing_band *band;
	time_t now;
	size_t i, len, cnt;
	char *b;

	++counter;
	if (!(counter % 10000)) {
		now = time(NULL);

		if (!last1) {
			last1 = now;
			last2 = now;
			last3 = now;
		}

		printf("Trying num %lu time %ld %ld %ld\n", counter,
					now - last1, now - last2, now - last3);
		fflush(stdout);
		last1 = now;
		if (!(counter % 1000000))
			last2 = now;
	}

	ret = setup_config(&mach, &band, &bb3_conf);
	if (ret) {
		printf("Cannot setup config: %d\n", ret);
		abort();
	}

	ret = turing_machine_simulate_limited(mach, band, 1000, 0);
	if (!ret) {
		b = turing_band_get_buf(band);
		len = turing_band_get_len(band);
		cnt = 0;
		for (i = 0; i < len; ++i) {
			if (b[i] == 'x')
				++cnt;
		}

		if (cnt >= maximum) {
			printf("Maximum (old %lu) of %lu x'es\n", maximum, cnt);
			maximum = cnt;
			turing_machine_print(mach);
			turing_band_print(band);
			fflush(stdout);
		}
	}

	turing_machine_unref(mach);
	turing_band_free(band);
}

static void try_all(size_t l)
{
	static const size_t s_num = 4;
	static const char *states[] = { "A", "B", "C", "END" };
	static const size_t l_num = 2;
	static const char letters[] = { '_', 'x' };
	static const size_t d_num = 3;
	static const int dirs[] = { TURING_LEFT, TURING_RIGHT, TURING_STAY };

	struct turing_init *cur;
	size_t i, j, k;

	--l;
	cur = &bb3_trans[l];
	for (k = 0; k < s_num; ++k) {
		cur->to = states[k];
		for (j = 0; j < l_num; ++j) {
			cur->write = letters[j];
			for (i = 0; i < d_num; ++i) {
				cur->dir = dirs[i];
				if (l > 0)
					try_all(l);
				else
					try();
			}
		}
	}
}

int main(int argc, char **argv)
{
	printf("Trying to find all busy beavers\n");

	try_all(6);

	printf("All done!\n");

	return EXIT_SUCCESS;
}
