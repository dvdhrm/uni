/*
 * Written 2011 by David Herrmann <dh.herrmann@googlemail.com>
 * Dedicated to the Public Domain
 */

/*
 * Implement Busy-Beaver functions on turing machines.
 */

#include <errno.h>
#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
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

/*
 * busy beaver 3
 * 14 steps and 6 x'es
 */
static char bb3_sigma[] = { 'x' };
static char bb3_gamma[] = { };
static struct turing_init bb3_trans[] = {
	{ "A", '_', "B", 'x', TURING_RIGHT },
	{ "A", 'x', "END", 'x', TURING_RIGHT },
	{ "B", '_', "C", '_', TURING_RIGHT },
	{ "B", 'x', "B", 'x', TURING_RIGHT },
	{ "C", '_', "C", 'x', TURING_LEFT },
	{ "C", 'x', "A", 'x', TURING_LEFT },
	TURING_INIT_END
};
static struct config bb3_conf = {
	bb3_sigma, sizeof(bb3_sigma),
	bb3_gamma, sizeof(bb3_gamma),
	'_',
	"A", "END",
	bb3_trans
};

/*
 * busy beaver 4
 * 107 steps and 13 x'es
 */
static const char bb4_sigma[] = { 'x' };
static const char bb4_gamma[] = { };
static const struct turing_init bb4_trans[] = {
	{ "A", '_', "B", 'x', TURING_RIGHT },
	{ "A", 'x', "B", 'x', TURING_LEFT },
	{ "B", '_', "A", 'x', TURING_LEFT },
	{ "B", 'x', "C", '_', TURING_LEFT },
	{ "C", '_', "END", 'x', TURING_RIGHT },
	{ "C", 'x', "D", 'x', TURING_LEFT },
	{ "D", '_', "D", 'x', TURING_RIGHT },
	{ "D", 'x', "A", '_', TURING_RIGHT },
	TURING_INIT_END
};
static struct config bb4_conf = {
	bb4_sigma, sizeof(bb4_sigma),
	bb4_gamma, sizeof(bb4_gamma),
	'_',
	"A", "END",
	bb4_trans
};

/*
 * busy beaver 5
 * 47,176,870 steps and 4098 x'es
 * Do not run in verbose mode! Takes about 10 seconds in non-verbose mode.
 */
static const char bb5_sigma[] = { 'x' };
static const char bb5_gamma[] = { };
static const struct turing_init bb5_trans[] = {
	{ "A", '_', "B", 'x', TURING_RIGHT },
	{ "A", 'x', "C", 'x', TURING_LEFT },
	{ "B", '_', "C", 'x', TURING_RIGHT },
	{ "B", 'x', "B", 'x', TURING_RIGHT },
	{ "C", '_', "D", 'x', TURING_RIGHT },
	{ "C", 'x', "E", '_', TURING_LEFT },
	{ "D", '_', "A", 'x', TURING_LEFT },
	{ "D", 'x', "D", 'x', TURING_LEFT },
	{ "E", '_', "END", 'x', TURING_RIGHT },
	{ "E", 'x', "A", '_', TURING_LEFT },
	TURING_INIT_END
};
static struct config bb5_conf = {
	bb5_sigma, sizeof(bb5_sigma),
	bb5_gamma, sizeof(bb5_gamma),
	'_',
	"A", "END",
	bb5_trans
};

/*
 * busy beaver 6
 * I haven't run this one. Should take:
 *   3.515 * 10^18267 x'es in 7.412 * 10^36534 steps
 * Seems not really simulatable on my Intel Atom...
 */
static const char bb6_sigma[] = { 'x' };
static const char bb6_gamma[] = { };
static const struct turing_init bb6_trans[] = {
	{ "A", '_', "B", 'x', TURING_RIGHT },
	{ "A", 'x', "E", 'x', TURING_LEFT },
	{ "B", '_', "C", 'x', TURING_RIGHT },
	{ "B", 'x', "F", 'x', TURING_RIGHT },
	{ "C", '_', "D", 'x', TURING_LEFT },
	{ "C", 'x', "B", '_', TURING_RIGHT },
	{ "D", '_', "E", 'x', TURING_RIGHT },
	{ "D", 'x', "C", '_', TURING_LEFT },
	{ "E", '_', "A", 'x', TURING_LEFT },
	{ "E", 'x', "D", '_', TURING_RIGHT },
	{ "F", '_', "END", 'x', TURING_LEFT },
	{ "F", 'x', "C", 'x', TURING_RIGHT },
	TURING_INIT_END
};
static struct config bb6_conf = {
	bb6_sigma, sizeof(bb6_sigma),
	bb6_gamma, sizeof(bb6_gamma),
	'_',
	"A", "END",
	bb6_trans
};

int main(int argc, char **argv)
{
	int ret;
	struct turing_machine *mach;
	struct turing_band *band;
	struct config *conf = NULL;
	int i;
	bool verbose = false;

	if (argc <= 1) {
		printf("Busy-Beaver: %s [3456] [v]\n", argv[0]);
		printf("  Please pass as argument a number to specify the\n");
		printf("  turing machine. Pass 'v' for verbose mode.\n");
		return EXIT_FAILURE;
	}

	for (i = 1; i < argc; ++i) {
		if (!strcmp(argv[i], "3"))
			conf = &bb3_conf;
		if (!strcmp(argv[i], "4"))
			conf = &bb4_conf;
		if (!strcmp(argv[i], "5"))
			conf = &bb5_conf;
		if (!strcmp(argv[i], "6"))
			conf = &bb6_conf;
		if (!strcmp(argv[i], "v"))
			verbose = true;
	}

	if (!conf)
		conf = &bb3_conf;

	ret = setup_config(&mach, &band, conf);
	if (ret)
		goto err_mach;

	turing_machine_print(mach);

	printf("\nSimulating turing machine:\n\n");
	ret = turing_machine_simulate_limited(mach, band, 0, verbose);
	if (ret) {
		printf("Cannot simulate turing machine: %d\n", ret);
		goto err_mach;
	}
	printf("\nTuring simulation completed\n");

err_mach:
	turing_machine_unref(mach);
	turing_band_free(band);
	return abs(ret);
}
