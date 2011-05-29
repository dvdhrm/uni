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

struct dm_cache {
	size_t size;
	struct block *mem;
};

struct dm_cache *dm_new(size_t size)
{
	struct dm_cache *c;
	size_t s;

	if (!size)
		die("cache size is 0");

	s = sizeof(*c) + sizeof(struct block) * size;

	c = malloc(s);
	if (!c)
		die("malloc failed");

	memset(c, 0, s);
	c->size = size;
	c->mem = ((void*)c) + sizeof(*c);

	return c;
}

void dm_free(struct dm_cache *c)
{
	free(c);
}

bool dm_set(struct dm_cache *c, addr_t *addr, value_t *value, bool dirty)
{
	size_t pos;
	addr_t raddr;
	value_t rval;
	bool overwrite;

	pos = *addr % c->size;
	overwrite = false;

	if (!c->mem[pos].valid) {
		overwrite = true;
	} else if (c->mem[pos].addr == *addr) {
		overwrite = true;
	} else if (!c->mem[pos].dirty) {
		overwrite = true;
	}

	if (overwrite) {
		c->mem[pos].addr = *addr;
		c->mem[pos].value = *value;
		c->mem[pos].valid = true;
		c->mem[pos].dirty = dirty;
	} else {
		raddr = c->mem[pos].addr;
		rval = c->mem[pos].value;

		c->mem[pos].addr = *addr;
		c->mem[pos].value = *value;
		c->mem[pos].dirty = dirty;

		*addr = raddr;
		*value = rval;
	}

	return !overwrite;
}

bool dm_get(struct dm_cache *c, addr_t addr, value_t *value)
{
	size_t pos;

	pos = addr % c->size;

	if (!c->mem[pos].valid)
		return false;

	if (c->mem[pos].addr != addr)
		return false;

	*value = c->mem[pos].value;
	return true;
}

bool dm_contains(struct dm_cache *c, addr_t addr)
{
	size_t pos;

	pos = addr % c->size;

	if (!c->mem[pos].valid)
		return false;

	if (!c->mem[pos].addr != addr)
		return false;

	return true;
}

void dm_dump(struct dm_cache *c)
{
	size_t i;

	printf("Cache dump:\n");

	for (i = 0; i < c->size; ++i) {
		printf("#%zu: %hhu => %hu", i, c->mem[i].addr, c->mem[i].value);
		if (!c->mem[i].valid)
			printf(" (invalid)");
		else if (c->mem[i].dirty)
			printf(" (dirty)");
		printf("\n");
	}
}
