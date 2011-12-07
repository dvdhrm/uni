/*
 * Written 2011 by David Herrmann <dh.herrmann@googlemail.com>
 * Dedicated to the Public Domain
 */

/*
 * This program implements a Turing Machine. It can simulate any kind of
 * configuration. You can set limits of maximal steps to avoid halting
 * problems.
 *
 * On my Intel Atom this simulation runs about 5,000,000 steps per second. The
 * stepper is optimized for speed and has only one lookup (transition lookup).
 * The band is a simple array that is resized and moved and should be optimized
 * further to allow smoother movements. This could probably speed up the
 * simulation by a factor greater than 10.
 *
 * See the example for several busy-beaver sample machines.
 */

#ifndef TURING_H
#define TURING_H

#include <errno.h>
#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

enum turing_direction {
	TURING_LEFT,
	TURING_RIGHT,
	TURING_STAY,
};

#define TURING_DIR2STR(dir) ((dir) == TURING_LEFT ? "left" : \
				((dir) == TURING_RIGHT ? "right" : "stay"))

struct turing_state;
struct turing_transition;
struct turing_band;
struct turing_machine;

#define TURING_INIT_END { NULL, 0, NULL, 0, 0 }

struct turing_init {
	const char *from;
	char read;
	const char *to;
	char write;
	int dir;
};

int turing_state_new(struct turing_state **out, const char *name);
void turing_state_ref(struct turing_state *state);
void turing_state_unref(struct turing_state *state);

int turing_transition_new(struct turing_transition **out);
void turing_transition_ref(struct turing_transition *trans);
void turing_transition_unref(struct turing_transition *trans);
void turing_transition_set_from(struct turing_transition *trans,
						struct turing_state *state);
void turing_transition_set_to(struct turing_transition *trans,
						struct turing_state *state);
void turing_transition_set_rw(struct turing_transition *trans, char r, char w);
void turing_transition_set_dir(struct turing_transition *trans, int dir);

int turing_band_new(struct turing_band **out, const char *init, size_t len,
							size_t pos, char blank);
void turing_band_free(struct turing_band *band);
char *turing_band_get_buf(struct turing_band *band);
size_t turing_band_get_len(struct turing_band *band);
void turing_band_print(struct turing_band *band);

int turing_machine_new(struct turing_machine **out, char blank);
void turing_machine_ref(struct turing_machine *mach);
void turing_machine_unref(struct turing_machine *mach);
int turing_machine_init(struct turing_machine *mach, const char *sig,
	size_t sig_size, const char *gam, size_t gam_size, const char *begin,
			const char *end, const struct turing_init *init);
int turing_machine_set_sigma(struct turing_machine *mach, const char *sigma,
								size_t size);
int turing_machine_set_gamma(struct turing_machine *mach, const char *gamma,
								size_t size);
int turing_machine_add_state(struct turing_machine *mach,
						struct turing_state *state);
void turing_machine_rm_state(struct turing_machine *mach,
						struct turing_state *state);
void turing_machine_set_begin(struct turing_machine *mach,
						struct turing_state *state);
void turing_machine_set_end(struct turing_machine *mach,
						struct turing_state *state);
int turing_machine_add_trans(struct turing_machine *mach,
					struct turing_transition *trans);
void turing_machine_rm_trans(struct turing_machine *mach,
					struct turing_transition *trans);
void turing_machine_print(struct turing_machine *mach);
int turing_machine_simulate(struct turing_machine *mach,
						struct turing_band *band);
int turing_machine_simulate_limited(struct turing_machine *mach,
		struct turing_band *band, unsigned long steps, int verbose);

#endif /* TURING_H */
