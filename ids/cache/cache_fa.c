/*
 * Written by David Herrmann
 */

#include <stdbool.h>
#include <stdint.h>
#include <stdlib.h>
#include <string.h>
#include "cache.h"

struct block {
	addr_t addr;
	value_t value;
	bool dirty;
	bool valid;
};

struct fa_cache {
	size_t size;
	struct block *blocks;
};

struct fa_cache *fa_new(size_t size)
{
	struct fa_cache *c;
	size_t s;

	if (!size)
		die("cache size is 0");

	s = sizeof(*c) + sizeof(struct block) * size;

	c = malloc(s);
	if (!c)
		die("malloc failed");

	memset(c, 0, s);
	c->size = size;
	c->blocks = ((void*)c) + sizeof(*c);

	return c;
}

void fa_free(struct fa_cache *c)
{
	free(c);
}

static inline void fa_top(struct fa_cache *c, size_t index)
{
	struct block tmp;

	/* if already at top, do nothing */
	if (index == 0)
		return;

	memcpy(&tmp, &c->blocks[index], sizeof(tmp));
	memmove(&c->blocks[1], &c->blocks[0], index * sizeof(tmp));
	memcpy(&c->blocks[0], &tmp, sizeof(tmp));
}

bool fa_set(struct fa_cache *c, addr_t *addr, value_t *value, bool dirty)
{
	addr_t raddr;
	value_t rval;
	size_t i, free;
	bool ret;

	free = c->size;

	/*
	 * If we find a free entry, remember the position but continue
	 * the search for the right address. If we find the address,
	 * then overwrite the value, otherwise fill the empty position.
	 */

	for (i = 0; i < c->size; ++i) {
		if (!c->blocks[i].valid) {
			free = i;
		} else if (c->blocks[i].addr == *addr) {
			c->blocks[i].value = *value;
			c->blocks[i].dirty = dirty;
			fa_top(c, i);
			return false;
		}
	}

	ret = false;

	if (free == c->size) {
		free = c->size - 1;
		if (c->blocks[free].dirty) {
			raddr = c->blocks[free].addr;
			rval = c->blocks[free].value;
			ret = true;
		}
	}

	c->blocks[free].addr = *addr;
	c->blocks[free].value = *value;
	c->blocks[free].dirty = dirty;
	c->blocks[free].valid = true;
	fa_top(c, free);

	if (ret) {
		*addr = raddr;
		*value = rval;
	}

	return ret;
}

bool fa_get(struct fa_cache *c, addr_t addr, value_t *value)
{
	size_t i;

	for (i = 0; i < c->size; ++i) {
		if (c->blocks[i].valid && c->blocks[i].addr == addr) {
			*value = c->blocks[i].value;
			fa_top(c, i);
			return true;
		}
	}

	return false;
}

bool fa_contains(struct fa_cache *c, addr_t addr)
{
	size_t i;

	for (i = 0; i < c->size; ++i) {
		if (c->blocks[i].valid && c->blocks[i].addr == addr)
			return true;
	}

	return false;
}

void fa_dump(struct fa_cache *c)
{
	size_t i;

	printf("Cache dump:\n");

	for (i = 0; i < c->size; ++i) {
		printf("#%zu: %hhu => %hu", i, c->blocks[i].addr, c->blocks[i].value);
		if (!c->blocks[i].valid)
			printf(" (invalid)");
		else if (c->blocks[i].dirty)
			printf(" (dirty)");
		printf("\n");
	}
}
