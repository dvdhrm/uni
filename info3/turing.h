/*
 * Written 2011 by David Herrmann <dh.herrmann@googlemail.com>
 * Dedicated to the Public Domain
 */

/*
 * This program implements a Turing Machine. It can simulate any kind of
 * configuration. You can set limits of maximal steps and maximal band length to
 * avoid halting problems.
 *
 * Two example configuration exists to simulate busy beavers of 3 and 4 states.
 */

#ifndef TURING_H
#define TURING_H

#include <errno.h>
#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define TURING_STATE(iname) \
	(struct turing_state){ .ref = 1, .name = iname }

struct turing_state {
	unsigned long ref;
	const char *name;
};

enum turing_direction {
	TURING_LEFT,
	TURING_RIGHT,
	TURING_STAY,
};

#define TURING_DIR2STR(dir) ((dir) == TURING_LEFT ? "left" : \
				((dir) == TURING_RIGHT ? "right" : "stay"))

#define TURING_TRANSITION(ifrom, ito, iread, iwrite, idir) \
	(struct turing_transition){.ref = 1, .from = ifrom, .to = ito, \
				.read = iread, .write = iwrite, .dir = idir}

struct turing_transition {
	unsigned long ref;
	struct turing_state *from;
	struct turing_state *to;
	char read;
	char write;
	int dir;
};

struct turing_band {
	size_t size;
	size_t len;
	char *buf;

	char blank;
	size_t pos;
};

struct turing_machine {
	unsigned long ref;

	size_t count_z;
	struct turing_state **list_z;

	size_t count_sigma;
	char *sigma;
	size_t count_gamma;
	char *gamma;

	size_t delta_count;
	struct turing_transition **delta;

	struct turing_state *z0;
	char blank;
	struct turing_state *e0;
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
								char blank);
void turing_band_free(struct turing_band *band);

int turing_machine_new(struct turing_machine **out, char blank);
void turing_machine_ref(struct turing_machine *mach);
void turing_machine_unref(struct turing_machine *mach);
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
	struct turing_band *band, unsigned long steps, unsigned long bsize);

#endif /* TURING_H */
