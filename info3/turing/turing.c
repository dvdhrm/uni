/*
 * Written 2011 by David Herrmann <dh.herrmann@googlemail.com>
 * Dedicated to the Public Domain
 */

/*
 * This program implements a Turing Machine.
 * See header file for more information and benchmarks.
 */

#include <errno.h>
#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "turing.h"

int turing_state_new(struct turing_state **out, const char *name)
{
	struct turing_state *state;
	size_t size, nlen;

	if (!out)
		return -EINVAL;

	nlen = strlen(name);
	size = nlen + 1 + sizeof(*state);
	state = malloc(size);
	if (!state)
		return -ENOMEM;

	memset(state, 0, size);
	state->ref = 1;
	state->name = (void*)(((char*)state) + sizeof(*state));
	memcpy(state->name, name, nlen);

	*out = state;
	return 0;
}

void turing_state_ref(struct turing_state *state)
{
	if (!state)
		return;

	++state->ref;
}

void turing_state_unref(struct turing_state *state)
{
	if (!state || !state->ref)
		return;

	if (--state->ref)
		return;

	free(state);
}

int turing_transition_new(struct turing_transition **out)
{
	struct turing_transition *trans;

	if (!out)
		return -EINVAL;

	trans = malloc(sizeof(*trans));
	if (!trans)
		return -ENOMEM;

	memset(trans, 0, sizeof(*trans));
	trans->ref = 1;

	*out = trans;
	return 0;
}

void turing_transition_ref(struct turing_transition *trans)
{
	if (!trans)
		return;

	++trans->ref;
}

void turing_transition_unref(struct turing_transition *trans)
{
	if (!trans || !trans->ref)
		return;

	if (--trans->ref)
		return;

	turing_state_unref(trans->from);
	turing_state_unref(trans->to);
	free(trans);
}

void turing_transition_set_from(struct turing_transition *trans,
						struct turing_state *state)
{
	if (!trans || !state)
		return;

	turing_state_unref(trans->from);
	trans->from = state;
	turing_state_ref(trans->from);
}

void turing_transition_set_to(struct turing_transition *trans,
						struct turing_state *state)
{
	if (!trans || !state)
		return;

	turing_state_unref(trans->to);
	trans->to = state;
	turing_state_ref(trans->to);
}

void turing_transition_set_rw(struct turing_transition *trans, char r, char w)
{
	if (!trans)
		return;

	trans->read = r;
	trans->write = w;
}

void turing_transition_set_dir(struct turing_transition *trans, int dir)
{
	if (!trans)
		return;

	trans->dir = dir;
}

int turing_band_new(struct turing_band **out, const char *init, size_t len,
							size_t pos, char blank)
{
	struct turing_band *band;
	char *buf;
	size_t i;

	if (!out || pos >= len)
		return -EINVAL;

	if (!len)
		len = 1;

	band = malloc(sizeof(*band));
	if (!band)
		return -ENOMEM;

	buf = malloc(2 * len);
	if (!buf) {
		free(band);
		return -ENOMEM;
	}

	memset(band, 0, sizeof(*band));
	band->size = 2 * len;
	band->len = len;
	band->buf = buf;
	band->pos = pos;
	band->blank = blank;

	if (init) {
		memcpy(buf, init, len);
	} else {
		for (i = 0; i < band->len; ++i)
			band->buf[i] = blank;
	}

	*out = band;
	return 0;
}

void turing_band_free(struct turing_band *band)
{
	if (!band)
		return;

	free(band->buf);
	free(band);
}

static int turing_band_push(struct turing_band *band)
{
	char *buf;

	if (!band)
		return -EINVAL;

	if (band->len < band->size)
		return 0;

	buf = realloc(band->buf, band->size * 2);
	if (!buf)
		return -ENOMEM;

	memset(&buf[band->size], 0, band->size);
	band->size *= 2;
	band->buf = buf;

	return 0;
}

static int turing_band_push_left(struct turing_band *band)
{
	int ret;

	if (!band)
		return -EINVAL;

	ret = turing_band_push(band);
	if (ret)
		return ret;

	memmove(&band->buf[1], band->buf, band->len);
	band->buf[0] = band->blank;
	band->len += 1;
	band->pos += 1;

	return 0;
}

static int turing_band_push_right(struct turing_band *band)
{
	int ret;

	if (!band)
		return -EINVAL;

	ret = turing_band_push(band);
	if (ret)
		return ret;

	band->buf[band->len] = band->blank;
	band->len += 1;

	return 0;
}

static int turing_band_move(struct turing_band *band, int dir)
{
	int ret;

	if (!band)
		return -EINVAL;

	switch (dir) {
	case TURING_LEFT:
		if (!band->pos) {
			ret = turing_band_push_left(band);
			if (ret)
				return ret;
		}
		band->pos -= 1;
		break;
	case TURING_RIGHT:
		if (band->pos == (band->len - 1)) {
			ret = turing_band_push_right(band);
			if (ret)
				return ret;
		}
		band->pos += 1;
		break;
	case TURING_STAY:
		break;
	default:
		return -EINVAL;
	}

	return 0;
}

static char turing_band_read(struct turing_band *band)
{
	if (!band)
		return 0;

	return band->buf[band->pos];
}

static void turing_band_write(struct turing_band *band, char ch)
{
	if (!band)
		return;

	band->buf[band->pos] = ch;
}

void turing_band_print(struct turing_band *band)
{
	size_t i;

	if (!band)
		return;

	printf(">> ");
	for (i = 0; i < band->len; ++i) {
		if (i == band->pos)
			printf("|");
		printf("%c", band->buf[i]);
	}
	printf("\n");
}

int turing_machine_new(struct turing_machine **out, char blank)
{
	struct turing_machine *mach;

	if (!out)
		return -EINVAL;

	mach = malloc(sizeof(*mach));
	if (!mach)
		return -ENOMEM;

	memset(mach, 0, sizeof(*mach));
	mach->ref = 1;
	mach->blank = blank;

	*out = mach;
	return 0;
}

void turing_machine_ref(struct turing_machine *mach)
{
	if (!mach)
		return;

	++mach->ref;
}

void turing_machine_unref(struct turing_machine *mach)
{
	size_t i;

	if (!mach || !mach->ref)
		return;

	if (--mach->ref)
		return;

	for (i = 0; i < mach->count_z; ++i)
		turing_state_unref(mach->list_z[i]);
	free(mach->list_z);

	for (i = 0; i < mach->delta_count; ++i)
		turing_transition_unref(mach->delta[i]);
	free(mach->delta);

	turing_state_unref(mach->z0);
	turing_state_unref(mach->e0);
	free(mach->sigma);
	free(mach->gamma);
	free(mach);
}

static struct turing_state *find_state(struct turing_machine *mach,
							const char *name)
{
	size_t i;

	for (i = 0; i < mach->count_z; ++i) {
		if (!strcmp(mach->list_z[i]->name, name)) {
			turing_state_ref(mach->list_z[i]);
			return mach->list_z[i];
		}
	}

	return NULL;
}

int turing_machine_init(struct turing_machine *mach, const char *sig,
	size_t sig_size, const char *gam, size_t gam_size, const char *begin,
			const char *end, const struct turing_init *init)
{
	int ret;
	struct turing_state *from, *to;
	const struct turing_init *iter;
	struct turing_transition *trans;
	size_t i;

	ret = turing_machine_set_sigma(mach, sig, sig_size);
	if (ret)
		return ret;

	ret = turing_machine_set_gamma(mach, gam, gam_size);
	if (ret)
		return ret;

	for (i = 0; init[i].from; ++i) {
		iter = &init[i];

		from = find_state(mach, iter->from);
		if (!from) {
			ret = turing_state_new(&from, iter->from);
			if (ret)
				return ret;

			ret = turing_machine_add_state(mach, from);
			if (ret) {
				turing_state_unref(from);
				return ret;
			}
		}

		to = find_state(mach, iter->to);
		if (!to) {
			ret = turing_state_new(&to, iter->to);
			if (ret) {
				turing_state_unref(from);
				return ret;
			}

			ret = turing_machine_add_state(mach, to);
			if (ret) {
				turing_state_unref(from);
				turing_state_unref(to);
				return ret;
			}
		}

		ret = turing_transition_new(&trans);
		if (ret) {
			turing_state_unref(from);
			turing_state_unref(to);
			return ret;
		}

		turing_transition_set_from(trans, from);
		turing_transition_set_to(trans, to);
		turing_transition_set_rw(trans, iter->read, iter->write);
		turing_transition_set_dir(trans, iter->dir);

		ret = turing_machine_add_trans(mach, trans);
		if (ret) {
			turing_transition_unref(trans);
			turing_state_unref(from);
			turing_state_unref(to);
			return ret;
		}

		turing_transition_unref(trans);
		turing_state_unref(from);
		turing_state_unref(to);
	}

	from = find_state(mach, begin);
	if (!from)
		return ret;

	turing_machine_set_begin(mach, from);
	turing_state_unref(from);

	from = find_state(mach, end);
	if (!from)
		return ret;

	turing_machine_set_end(mach, from);
	turing_state_unref(from);

	return 0;
}

int turing_machine_set_sigma(struct turing_machine *mach, const char *sigma,
								size_t size)
{
	char *buf;

	if (!mach || !sigma || !size)
		return -EINVAL;

	buf = malloc(size);
	if (!buf)
		return -ENOMEM;

	free(mach->sigma);
	mach->sigma = buf;
	mach->count_sigma = size;

	memcpy(mach->sigma, sigma, size);

	return 0;
}

/*
 * Set the gamma-alphabet. You need to set the sigma-alphabet before because it
 * is merged into the gamma-alphabet.
 */
int turing_machine_set_gamma(struct turing_machine *mach, const char *gamma,
								size_t size)
{
	char *buf;

	if (!mach || !mach->count_sigma)
		return -EINVAL;

	buf = malloc(mach->count_sigma + size + 1);
	if (!buf)
		return -ENOMEM;

	free(mach->gamma);
	mach->gamma = buf;
	mach->count_gamma = mach->count_sigma + size + 1;

	mach->gamma[0] = mach->blank;
	memcpy(&mach->gamma[1], mach->sigma, mach->count_sigma);
	memcpy(&mach->gamma[mach->count_sigma + 1], gamma, size);

	return 0;
}

int turing_machine_add_state(struct turing_machine *mach,
						struct turing_state *state)
{
	size_t size;
	struct turing_state **list;

	if (!mach || !state)
		return -EINVAL;

	size = mach->count_z + 1;
	list = realloc(mach->list_z, sizeof(struct turing_state*) * size);
	if (!list)
		return -ENOMEM;

	list[size - 1] = state;
	turing_state_ref(state);

	mach->count_z = size;
	mach->list_z = list;

	return 0;
}

void turing_machine_rm_state(struct turing_machine *mach,
						struct turing_state *state)
{
	size_t i;

	if (!mach || !state)
		return;

	for (i = 0; i < mach->count_z; ++i) {
		if (mach->list_z[i] == state)
			break;
	}

	if (i == mach->count_z)
		return;

	--mach->count_z;
	memmove(&mach->list_z[i], &mach->list_z[i + 1],
			sizeof(struct turing_state*) * (mach->count_z - i));
}

void turing_machine_set_begin(struct turing_machine *mach,
						struct turing_state *state)
{
	if (!mach || !state)
		return;

	turing_state_unref(mach->z0);
	mach->z0 = state;
	turing_state_ref(mach->z0);
}

void turing_machine_set_end(struct turing_machine *mach,
						struct turing_state *state)
{
	if (!mach || !state)
		return;

	turing_state_unref(mach->e0);
	mach->e0 = state;
	turing_state_ref(mach->e0);
}

int turing_machine_add_trans(struct turing_machine *mach,
					struct turing_transition *trans)
{
	size_t size;
	struct turing_transition **list;

	if (!mach || !trans)
		return -EINVAL;

	size = mach->delta_count + 1;
	list = realloc(mach->delta, sizeof(struct turing_transition*) * size);
	if (!list)
		return -ENOMEM;

	list[size - 1] = trans;
	turing_transition_ref(trans);

	mach->delta_count = size;
	mach->delta = list;

	return 0;
}

void turing_machine_rm_trans(struct turing_machine *mach,
					struct turing_transition *trans)
{
	size_t i;

	if (!mach || !trans)
		return;

	for (i = 0; i < mach->delta_count; ++i) {
		if (mach->delta[i] == trans)
			break;
	}

	if (i == mach->delta_count)
		return;

	--mach->delta_count;
	memmove(&mach->delta[i], &mach->delta[i + 1],
		sizeof(struct turing_transition*) * (mach->delta_count - i));
}

void turing_machine_print(struct turing_machine *mach)
{
	size_t i;
	struct turing_transition *trans;
	const char *from, *to;

	if (!mach)
		return;

	printf("Turing-Machine: %p\n", mach);

	printf("    sigma (%lu): ", mach->count_sigma);
	for (i = 0; i < mach->count_sigma; ++i)
		printf("%c ", mach->sigma[i]);
	printf("\n");

	printf("    gamma (%lu): ", mach->count_gamma);
	for (i = 0; i < mach->count_gamma; ++i)
		printf("%c ", mach->gamma[i]);
	printf("\n");
	printf("    blank: %c\n", mach->blank);

	printf("    states (%lu): ", mach->count_z);
	for (i = 0; i < mach->count_z; ++i)
		printf("'%s'%s%s ", mach->list_z[i]->name,
			(mach->z0 == mach->list_z[i]) ? ":begin" : "",
			(mach->e0 == mach->list_z[i]) ? ":end" : "");
	printf("\n");

	printf("  transitions:\n");
	for (i = 0; i < mach->delta_count; ++i) {
		trans = mach->delta[i];

		if (trans->from && trans->from->name)
			from = trans->from->name;
		else
			from = "<na>";

		if (trans->to && trans->to->name)
			to = trans->to->name;
		else
			to = "<na>";
		printf("    %s:%c -> %s:%c (%s)\n", from, trans->read,
				to, trans->write, TURING_DIR2STR(trans->dir));
	}
}

static struct turing_transition *find_transition(struct turing_machine *mach,
			struct turing_state *current, struct turing_band *band)
{
	char ch;
	size_t i;
	struct turing_transition *trans;

	if (!mach || !current || !band)
		return NULL;

	ch = turing_band_read(band);

	for (i = 0; i < mach->delta_count; ++i) {
		trans = mach->delta[i];

		if (trans->from == current && trans->read == ch)
			return trans;
	}

	return NULL;
}

int turing_machine_simulate_limited(struct turing_machine *mach,
		struct turing_band *band, unsigned long steps, bool verbose)
{
	unsigned long step;
	int ret = 0;
	struct turing_state *current;
	struct turing_transition *trans;

	if (!mach || !band || !mach->sigma || !mach->gamma)
		return -EINVAL;

	if (!mach->z0 || !mach->count_z || !mach->delta_count)
		return -EINVAL;

	current = mach->z0;
	printf("Starting at: %s\n", current->name);
	turing_band_print(band);

	for (step = 0; (step < steps) || !steps; ++step) {
		if (!verbose && !(step % 1000000))
			printf("Step %lu Band: %lu\n", step, band->len);

		trans = find_transition(mach, current, band);
		if (!trans || !trans->to) {
			ret = -EINVAL;
			printf("Cannot find matching transition\n");
			break;
		}

		turing_band_write(band, trans->write);
		ret = turing_band_move(band, trans->dir);
		if (ret) {
			printf("Band movement failed: %d\n", ret);
			break;
		}
		current = trans->to;

		if (verbose) {
			printf("Transition: %s:%c -> %s:%c (%s)\n",
						trans->from->name, trans->read,
						trans->to->name, trans->write,
						TURING_DIR2STR(trans->dir));
			turing_band_print(band);
		}

		if (current == mach->e0) {
			printf("Halt condition reached (%lu steps)\n",
								step + 1);
			break;
		}
	}

	turing_band_print(band);

	if (step == steps) {
		printf("Max steps limit reached (%lu)\n", steps);
		ret = -E2BIG;
	}

	if (ret)
		printf("Turing machine failed: %d\n", ret);

	return ret;
}

int turing_machine_simulate(struct turing_machine *mach,
						struct turing_band *band)
{
	return turing_machine_simulate_limited(mach, band, 10000, true);
}
