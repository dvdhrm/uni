/*
 * Written by David Herrmann
 */

#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "cache.h"

struct memory_field {
	bool valid;
	value_t value;
};

struct memory {
	struct cache c;
	struct memory_field mem[ADDR_MAX];
	size_t hits;
	size_t misses;
	size_t yields;
};

static void memory_init(struct memory *m, int type, size_t csize)
{
	memset(m, 0, sizeof(*m));
	cache_init(&m->c, type, csize);
}

static void memory_deinit(struct memory *m)
{
	cache_deinit(&m->c);
}

static void memory_write(struct memory *m, addr_t addr, value_t value)
{
	/*printf("Writing %hu to %hhu\n", value, addr);*/

	if (cache_set(&m->c, &addr, &value, true)) {
		m->mem[addr].valid = true;
		m->mem[addr].value = value;
		++m->yields;
	}
}

static void memory_read(struct memory *m, addr_t addr, value_t *value)
{
	/*printf("Reading from %hhu\n", addr);*/

	if (cache_get(&m->c, addr, value)) {
		++m->hits;
		return;
	}

	if (!m->mem[addr].valid)
		/*die("reading uninitialized memory");*/
		m->mem[addr].value = 0;

	++m->misses;
	*value = m->mem[addr].value;

	if (cache_set(&m->c, &addr, value, false)) {
		m->mem[addr].valid = true;
		m->mem[addr].value = *value;
		++m->yields;
	}
}

static void memory_dump(struct memory *m)
{
	size_t i;

	cache_dump(&m->c);

	printf("Memory dump:\n");

	for (i = 0; i < ADDR_MAX; ++i) {
		if (m->mem[i].valid) {
			printf("%zu => %hu", i, m->mem[i].value);
			if (cache_contains(&m->c, i))
				printf(" (cached)");
			printf("\n");
		}
	}

	printf("Cache hits: %zu\n", m->hits);
	printf("Cache misses: %zu\n", m->misses);
	printf("Cache yields: %zu\n", m->yields);
	printf("\n");
}

static void bonus(int type)
{
	struct memory mem;
	value_t val;

	memory_init(&mem, type, 8);

	memory_write(&mem, 0, 0);
	memory_write(&mem, 1, 3);
	memory_read(&mem, 3, &val);
	memory_write(&mem, 1, 5);
	memory_read(&mem, 5, &val);
	memory_write(&mem, 1, 9);
	memory_read(&mem, 9, &val);
	memory_write(&mem, 1, 12);
	memory_read(&mem, 12, &val);
	memory_write(&mem, 1, 17);
	memory_read(&mem, 17, &val);
	memory_write(&mem, 1, 21);
	memory_read(&mem, 21, &val);
	memory_write(&mem, 1, 25);
	memory_read(&mem, 25, &val);
	memory_write(&mem, 1, 31);
	memory_read(&mem, 31, &val);
	memory_write(&mem, 1, 37);
	memory_read(&mem, 37, &val);
	memory_write(&mem, 1, 47);
	memory_read(&mem, 47, &val);
	memory_write(&mem, 1, 49);
	memory_read(&mem, 49, &val);
	memory_write(&mem, 1, 52);
	memory_read(&mem, 52, &val);
	memory_write(&mem, 1, 56);
	memory_read(&mem, 56, &val);
	memory_write(&mem, 1, 58);
	memory_read(&mem, 58, &val);
	memory_write(&mem, 1, 64);
	memory_read(&mem, 64, &val);
	memory_write(&mem, 1, 71);
	memory_read(&mem, 71, &val);
	memory_write(&mem, 1, 73);
	memory_read(&mem, 73, &val);
	memory_write(&mem, 1, 77);
	memory_read(&mem, 77, &val);
	memory_write(&mem, 1, 83);
	memory_read(&mem, 83, &val);
	memory_write(&mem, 1, 88);
	memory_read(&mem, 88, &val);
	memory_write(&mem, 1, 90);
	memory_read(&mem, 90, &val);
	memory_write(&mem, 1, 93);
	memory_read(&mem, 93, &val);
	memory_write(&mem, 1, 103);
	memory_read(&mem, 103, &val);
	memory_write(&mem, 1, 113);
	memory_read(&mem, 113, &val);
	memory_write(&mem, 1, 116);
	memory_read(&mem, 116, &val);
	memory_write(&mem, 1, 122);
	memory_read(&mem, 122, &val);
	memory_write(&mem, 1, 125);
	memory_read(&mem, 125, &val);
	memory_write(&mem, 1, 128);
	memory_read(&mem, 128, &val);
	memory_write(&mem, 1, 130);
	memory_read(&mem, 130, &val);
	memory_read(&mem, 0, &val);
	memory_read(&mem, 0, &val);
	memory_read(&mem, 0, &val);
	memory_write(&mem, 0, 5407);
	memory_read(&mem, 1, &val);
	memory_write(&mem, 130, 0);
	memory_write(&mem, 0, 1);
	memory_write(&mem, 1, 5);
	memory_read(&mem, 5, &val);
	memory_write(&mem, 1, 9);
	memory_read(&mem, 9, &val);
	memory_write(&mem, 1, 12);
	memory_read(&mem, 12, &val);
	memory_write(&mem, 1, 17);
	memory_read(&mem, 17, &val);
	memory_write(&mem, 1, 21);
	memory_read(&mem, 21, &val);
	memory_write(&mem, 1, 25);
	memory_read(&mem, 25, &val);
	memory_write(&mem, 1, 31);
	memory_read(&mem, 31, &val);
	memory_write(&mem, 1, 37);
	memory_read(&mem, 37, &val);
	memory_write(&mem, 1, 47);
	memory_read(&mem, 47, &val);
	memory_write(&mem, 1, 49);
	memory_read(&mem, 49, &val);
	memory_write(&mem, 1, 52);
	memory_read(&mem, 52, &val);
	memory_write(&mem, 1, 56);
	memory_read(&mem, 56, &val);
	memory_write(&mem, 1, 58);
	memory_read(&mem, 58, &val);
	memory_write(&mem, 1, 64);
	memory_read(&mem, 64, &val);
	memory_write(&mem, 1, 71);
	memory_read(&mem, 71, &val);
	memory_write(&mem, 1, 73);
	memory_read(&mem, 73, &val);
	memory_write(&mem, 1, 77);
	memory_read(&mem, 77, &val);
	memory_write(&mem, 1, 83);
	memory_read(&mem, 83, &val);
	memory_write(&mem, 1, 88);
	memory_read(&mem, 88, &val);
	memory_write(&mem, 1, 90);
	memory_read(&mem, 90, &val);
	memory_write(&mem, 1, 93);
	memory_read(&mem, 93, &val);
	memory_write(&mem, 1, 103);
	memory_read(&mem, 103, &val);
	memory_write(&mem, 1, 113);
	memory_read(&mem, 113, &val);
	memory_write(&mem, 1, 116);
	memory_read(&mem, 116, &val);
	memory_write(&mem, 1, 122);
	memory_read(&mem, 122, &val);
	memory_write(&mem, 1, 125);
	memory_read(&mem, 125, &val);
	memory_write(&mem, 1, 128);
	memory_read(&mem, 128, &val);
	memory_write(&mem, 1, 130);
	memory_read(&mem, 130, &val);
	memory_read(&mem, 0, &val);
	memory_read(&mem, 3, &val);
	memory_read(&mem, 0, &val);
	memory_write(&mem, 3, 4524);
	memory_read(&mem, 1, &val);
	memory_write(&mem, 128, 339);
	memory_write(&mem, 0, 2);
	memory_write(&mem, 1, 9);
	memory_read(&mem, 9, &val);
	memory_write(&mem, 1, 12);
	memory_read(&mem, 12, &val);
	memory_write(&mem, 1, 17);
	memory_read(&mem, 17, &val);
	memory_write(&mem, 1, 21);
	memory_read(&mem, 21, &val);
	memory_write(&mem, 1, 25);
	memory_read(&mem, 25, &val);
	memory_write(&mem, 1, 31);
	memory_read(&mem, 31, &val);
	memory_write(&mem, 1, 37);
	memory_read(&mem, 37, &val);
	memory_write(&mem, 1, 47);
	memory_read(&mem, 47, &val);
	memory_write(&mem, 1, 49);
	memory_read(&mem, 49, &val);
	memory_write(&mem, 1, 52);
	memory_read(&mem, 52, &val);
	memory_write(&mem, 1, 56);
	memory_read(&mem, 56, &val);
	memory_write(&mem, 1, 58);
	memory_read(&mem, 58, &val);
	memory_write(&mem, 1, 64);
	memory_read(&mem, 64, &val);
	memory_write(&mem, 1, 71);
	memory_read(&mem, 71, &val);
	memory_write(&mem, 1, 73);
	memory_read(&mem, 73, &val);
	memory_write(&mem, 1, 77);
	memory_read(&mem, 77, &val);
	memory_write(&mem, 1, 83);
	memory_read(&mem, 83, &val);
	memory_write(&mem, 1, 88);
	memory_read(&mem, 88, &val);
	memory_write(&mem, 1, 90);
	memory_read(&mem, 90, &val);
	memory_write(&mem, 1, 93);
	memory_read(&mem, 93, &val);
	memory_write(&mem, 1, 103);
	memory_read(&mem, 103, &val);
	memory_write(&mem, 1, 113);
	memory_read(&mem, 113, &val);
	memory_write(&mem, 1, 116);
	memory_read(&mem, 116, &val);
	memory_write(&mem, 1, 122);
	memory_read(&mem, 122, &val);
	memory_write(&mem, 1, 125);
	memory_read(&mem, 125, &val);
	memory_write(&mem, 1, 128);
	memory_read(&mem, 128, &val);
	memory_write(&mem, 1, 130);
	memory_read(&mem, 130, &val);
	memory_read(&mem, 0, &val);
	memory_read(&mem, 5, &val);
	memory_read(&mem, 0, &val);
	memory_write(&mem, 5, 3745);
	memory_read(&mem, 1, &val);
	memory_write(&mem, 125, 520);
	memory_write(&mem, 0, 3);
	memory_write(&mem, 1, 12);
	memory_read(&mem, 12, &val);
	memory_write(&mem, 1, 17);
	memory_read(&mem, 17, &val);
	memory_write(&mem, 1, 21);
	memory_read(&mem, 21, &val);
	memory_write(&mem, 1, 25);
	memory_read(&mem, 25, &val);
	memory_write(&mem, 1, 31);
	memory_read(&mem, 31, &val);
	memory_write(&mem, 1, 37);
	memory_read(&mem, 37, &val);
	memory_write(&mem, 1, 47);
	memory_read(&mem, 47, &val);
	memory_write(&mem, 1, 49);
	memory_read(&mem, 49, &val);
	memory_write(&mem, 1, 52);
	memory_read(&mem, 52, &val);
	memory_write(&mem, 1, 56);
	memory_read(&mem, 56, &val);
	memory_write(&mem, 1, 58);
	memory_read(&mem, 58, &val);
	memory_write(&mem, 1, 64);
	memory_read(&mem, 64, &val);
	memory_write(&mem, 1, 71);
	memory_read(&mem, 71, &val);
	memory_write(&mem, 1, 73);
	memory_read(&mem, 73, &val);
	memory_write(&mem, 1, 77);
	memory_read(&mem, 77, &val);
	memory_write(&mem, 1, 83);
	memory_read(&mem, 83, &val);
	memory_write(&mem, 1, 88);
	memory_read(&mem, 88, &val);
	memory_write(&mem, 1, 90);
	memory_read(&mem, 90, &val);
	memory_write(&mem, 1, 93);
	memory_read(&mem, 93, &val);
	memory_write(&mem, 1, 103);
	memory_read(&mem, 103, &val);
	memory_write(&mem, 1, 113);
	memory_read(&mem, 113, &val);
	memory_write(&mem, 1, 116);
	memory_read(&mem, 116, &val);
	memory_write(&mem, 1, 122);
	memory_read(&mem, 122, &val);
	memory_write(&mem, 1, 125);
	memory_read(&mem, 125, &val);
	memory_write(&mem, 1, 128);
	memory_read(&mem, 128, &val);
	memory_write(&mem, 1, 130);
	memory_read(&mem, 130, &val);
	memory_read(&mem, 0, &val);
	memory_read(&mem, 9, &val);
	memory_read(&mem, 0, &val);
	memory_write(&mem, 9, 3064);
	memory_read(&mem, 1, &val);
	memory_write(&mem, 122, 649);
	memory_write(&mem, 0, 4);
	memory_write(&mem, 1, 17);
	memory_read(&mem, 17, &val);
	memory_write(&mem, 1, 21);
	memory_read(&mem, 21, &val);
	memory_write(&mem, 1, 25);
	memory_read(&mem, 25, &val);
	memory_write(&mem, 1, 31);
	memory_read(&mem, 31, &val);
	memory_write(&mem, 1, 37);
	memory_read(&mem, 37, &val);
	memory_write(&mem, 1, 47);
	memory_read(&mem, 47, &val);
	memory_write(&mem, 1, 49);
	memory_read(&mem, 49, &val);
	memory_write(&mem, 1, 52);
	memory_read(&mem, 52, &val);
	memory_write(&mem, 1, 56);
	memory_read(&mem, 56, &val);
	memory_write(&mem, 1, 58);
	memory_read(&mem, 58, &val);
	memory_write(&mem, 1, 64);
	memory_read(&mem, 64, &val);
	memory_write(&mem, 1, 71);
	memory_read(&mem, 71, &val);
	memory_write(&mem, 1, 73);
	memory_read(&mem, 73, &val);
	memory_write(&mem, 1, 77);
	memory_read(&mem, 77, &val);
	memory_write(&mem, 1, 83);
	memory_read(&mem, 83, &val);
	memory_write(&mem, 1, 88);
	memory_read(&mem, 88, &val);
	memory_write(&mem, 1, 90);
	memory_read(&mem, 90, &val);
	memory_write(&mem, 1, 93);
	memory_read(&mem, 93, &val);
	memory_write(&mem, 1, 103);
	memory_read(&mem, 103, &val);
	memory_write(&mem, 1, 113);
	memory_read(&mem, 113, &val);
	memory_write(&mem, 1, 116);
	memory_read(&mem, 116, &val);
	memory_write(&mem, 1, 122);
	memory_read(&mem, 122, &val);
	memory_write(&mem, 1, 125);
	memory_read(&mem, 125, &val);
	memory_write(&mem, 1, 128);
	memory_read(&mem, 128, &val);
	memory_write(&mem, 1, 130);
	memory_read(&mem, 130, &val);
	memory_read(&mem, 0, &val);
	memory_read(&mem, 12, &val);
	memory_read(&mem, 0, &val);
	memory_write(&mem, 12, 2475);
	memory_read(&mem, 1, &val);
	memory_write(&mem, 116, 732);
	memory_write(&mem, 0, 5);
	memory_write(&mem, 1, 21);
	memory_read(&mem, 21, &val);
	memory_write(&mem, 1, 25);
	memory_read(&mem, 25, &val);
	memory_write(&mem, 1, 31);
	memory_read(&mem, 31, &val);
	memory_write(&mem, 1, 37);
	memory_read(&mem, 37, &val);
	memory_write(&mem, 1, 47);
	memory_read(&mem, 47, &val);
	memory_write(&mem, 1, 49);
	memory_read(&mem, 49, &val);
	memory_write(&mem, 1, 52);
	memory_read(&mem, 52, &val);
	memory_write(&mem, 1, 56);
	memory_read(&mem, 56, &val);
	memory_write(&mem, 1, 58);
	memory_read(&mem, 58, &val);
	memory_write(&mem, 1, 64);
	memory_read(&mem, 64, &val);
	memory_write(&mem, 1, 71);
	memory_read(&mem, 71, &val);
	memory_write(&mem, 1, 73);
	memory_read(&mem, 73, &val);
	memory_write(&mem, 1, 77);
	memory_read(&mem, 77, &val);
	memory_write(&mem, 1, 83);
	memory_read(&mem, 83, &val);
	memory_write(&mem, 1, 88);
	memory_read(&mem, 88, &val);
	memory_write(&mem, 1, 90);
	memory_read(&mem, 90, &val);
	memory_write(&mem, 1, 93);
	memory_read(&mem, 93, &val);
	memory_write(&mem, 1, 103);
	memory_read(&mem, 103, &val);
	memory_write(&mem, 1, 113);
	memory_read(&mem, 113, &val);
	memory_write(&mem, 1, 116);
	memory_read(&mem, 116, &val);
	memory_write(&mem, 1, 122);
	memory_read(&mem, 122, &val);
	memory_write(&mem, 1, 125);
	memory_read(&mem, 125, &val);
	memory_write(&mem, 1, 128);
	memory_read(&mem, 128, &val);
	memory_write(&mem, 1, 130);
	memory_read(&mem, 130, &val);
	memory_read(&mem, 0, &val);
	memory_read(&mem, 17, &val);
	memory_read(&mem, 0, &val);
	memory_write(&mem, 17, 1972);
	memory_read(&mem, 1, &val);
	memory_write(&mem, 113, 775);
	memory_write(&mem, 0, 6);
	memory_write(&mem, 1, 25);
	memory_read(&mem, 25, &val);
	memory_write(&mem, 1, 31);
	memory_read(&mem, 31, &val);
	memory_write(&mem, 1, 37);
	memory_read(&mem, 37, &val);
	memory_write(&mem, 1, 47);
	memory_read(&mem, 47, &val);
	memory_write(&mem, 1, 49);
	memory_read(&mem, 49, &val);
	memory_write(&mem, 1, 52);
	memory_read(&mem, 52, &val);
	memory_write(&mem, 1, 56);
	memory_read(&mem, 56, &val);
	memory_write(&mem, 1, 58);
	memory_read(&mem, 58, &val);
	memory_write(&mem, 1, 64);
	memory_read(&mem, 64, &val);
	memory_write(&mem, 1, 71);
	memory_read(&mem, 71, &val);
	memory_write(&mem, 1, 73);
	memory_read(&mem, 73, &val);
	memory_write(&mem, 1, 77);
	memory_read(&mem, 77, &val);
	memory_write(&mem, 1, 83);
	memory_read(&mem, 83, &val);
	memory_write(&mem, 1, 88);
	memory_read(&mem, 88, &val);
	memory_write(&mem, 1, 90);
	memory_read(&mem, 90, &val);
	memory_write(&mem, 1, 93);
	memory_read(&mem, 93, &val);
	memory_write(&mem, 1, 103);
	memory_read(&mem, 103, &val);
	memory_write(&mem, 1, 113);
	memory_read(&mem, 113, &val);
	memory_write(&mem, 1, 116);
	memory_read(&mem, 116, &val);
	memory_write(&mem, 1, 122);
	memory_read(&mem, 122, &val);
	memory_write(&mem, 1, 125);
	memory_read(&mem, 125, &val);
	memory_write(&mem, 1, 128);
	memory_read(&mem, 128, &val);
	memory_write(&mem, 1, 130);
	memory_read(&mem, 130, &val);
	memory_read(&mem, 0, &val);
	memory_read(&mem, 21, &val);
	memory_read(&mem, 0, &val);
	memory_write(&mem, 21, 1549);
	memory_read(&mem, 1, &val);
	memory_write(&mem, 103, 784);
	memory_write(&mem, 0, 7);
	memory_write(&mem, 1, 31);
	memory_read(&mem, 31, &val);
	memory_write(&mem, 1, 37);
	memory_read(&mem, 37, &val);
	memory_write(&mem, 1, 47);
	memory_read(&mem, 47, &val);
	memory_write(&mem, 1, 49);
	memory_read(&mem, 49, &val);
	memory_write(&mem, 1, 52);
	memory_read(&mem, 52, &val);
	memory_write(&mem, 1, 56);
	memory_read(&mem, 56, &val);
	memory_write(&mem, 1, 58);
	memory_read(&mem, 58, &val);
	memory_write(&mem, 1, 64);
	memory_read(&mem, 64, &val);
	memory_write(&mem, 1, 71);
	memory_read(&mem, 71, &val);
	memory_write(&mem, 1, 73);
	memory_read(&mem, 73, &val);
	memory_write(&mem, 1, 77);
	memory_read(&mem, 77, &val);
	memory_write(&mem, 1, 83);
	memory_read(&mem, 83, &val);
	memory_write(&mem, 1, 88);
	memory_read(&mem, 88, &val);
	memory_write(&mem, 1, 90);
	memory_read(&mem, 90, &val);
	memory_write(&mem, 1, 93);
	memory_read(&mem, 93, &val);
	memory_write(&mem, 1, 103);
	memory_read(&mem, 103, &val);
	memory_write(&mem, 1, 113);
	memory_read(&mem, 113, &val);
	memory_write(&mem, 1, 116);
	memory_read(&mem, 116, &val);
	memory_write(&mem, 1, 122);
	memory_read(&mem, 122, &val);
	memory_write(&mem, 1, 125);
	memory_read(&mem, 125, &val);
	memory_write(&mem, 1, 128);
	memory_read(&mem, 128, &val);
	memory_write(&mem, 1, 130);
	memory_read(&mem, 130, &val);
	memory_read(&mem, 0, &val);
	memory_read(&mem, 25, &val);
	memory_read(&mem, 0, &val);
	memory_write(&mem, 25, 1200);
	memory_read(&mem, 1, &val);
	memory_write(&mem, 93, 765);
	memory_write(&mem, 0, 8);
	memory_write(&mem, 1, 37);
	memory_read(&mem, 37, &val);
	memory_write(&mem, 1, 47);
	memory_read(&mem, 47, &val);
	memory_write(&mem, 1, 49);
	memory_read(&mem, 49, &val);
	memory_write(&mem, 1, 52);
	memory_read(&mem, 52, &val);
	memory_write(&mem, 1, 56);
	memory_read(&mem, 56, &val);
	memory_write(&mem, 1, 58);
	memory_read(&mem, 58, &val);
	memory_write(&mem, 1, 64);
	memory_read(&mem, 64, &val);
	memory_write(&mem, 1, 71);
	memory_read(&mem, 71, &val);
	memory_write(&mem, 1, 73);
	memory_read(&mem, 73, &val);
	memory_write(&mem, 1, 77);
	memory_read(&mem, 77, &val);
	memory_write(&mem, 1, 83);
	memory_read(&mem, 83, &val);
	memory_write(&mem, 1, 88);
	memory_read(&mem, 88, &val);
	memory_write(&mem, 1, 90);
	memory_read(&mem, 90, &val);
	memory_write(&mem, 1, 93);
	memory_read(&mem, 93, &val);
	memory_write(&mem, 1, 103);
	memory_read(&mem, 103, &val);
	memory_write(&mem, 1, 113);
	memory_read(&mem, 113, &val);
	memory_write(&mem, 1, 116);
	memory_read(&mem, 116, &val);
	memory_write(&mem, 1, 122);
	memory_read(&mem, 122, &val);
	memory_write(&mem, 1, 125);
	memory_read(&mem, 125, &val);
	memory_write(&mem, 1, 128);
	memory_read(&mem, 128, &val);
	memory_write(&mem, 1, 130);
	memory_read(&mem, 130, &val);
	memory_read(&mem, 0, &val);
	memory_read(&mem, 31, &val);
	memory_read(&mem, 0, &val);
	memory_write(&mem, 31, 919);
	memory_read(&mem, 1, &val);
	memory_write(&mem, 90, 724);
	memory_write(&mem, 0, 9);
	memory_write(&mem, 1, 47);
	memory_read(&mem, 47, &val);
	memory_write(&mem, 1, 49);
	memory_read(&mem, 49, &val);
	memory_write(&mem, 1, 52);
	memory_read(&mem, 52, &val);
	memory_write(&mem, 1, 56);
	memory_read(&mem, 56, &val);
	memory_write(&mem, 1, 58);
	memory_read(&mem, 58, &val);
	memory_write(&mem, 1, 64);
	memory_read(&mem, 64, &val);
	memory_write(&mem, 1, 71);
	memory_read(&mem, 71, &val);
	memory_write(&mem, 1, 73);
	memory_read(&mem, 73, &val);
	memory_write(&mem, 1, 77);
	memory_read(&mem, 77, &val);
	memory_write(&mem, 1, 83);
	memory_read(&mem, 83, &val);
	memory_write(&mem, 1, 88);
	memory_read(&mem, 88, &val);
	memory_write(&mem, 1, 90);
	memory_read(&mem, 90, &val);
	memory_write(&mem, 1, 93);
	memory_read(&mem, 93, &val);
	memory_write(&mem, 1, 103);
	memory_read(&mem, 103, &val);
	memory_write(&mem, 1, 113);
	memory_read(&mem, 113, &val);
	memory_write(&mem, 1, 116);
	memory_read(&mem, 116, &val);
	memory_write(&mem, 1, 122);
	memory_read(&mem, 122, &val);
	memory_write(&mem, 1, 125);
	memory_read(&mem, 125, &val);
	memory_write(&mem, 1, 128);
	memory_read(&mem, 128, &val);
	memory_write(&mem, 1, 130);
	memory_read(&mem, 130, &val);
	memory_read(&mem, 0, &val);
	memory_read(&mem, 37, &val);
	memory_read(&mem, 0, &val);
	memory_write(&mem, 37, 784);
	memory_read(&mem, 1, &val);
	memory_write(&mem, 103, 667);
	memory_write(&mem, 0, 10);
	memory_write(&mem, 1, 49);
	memory_read(&mem, 49, &val);
	memory_write(&mem, 1, 52);
	memory_read(&mem, 52, &val);
	memory_write(&mem, 1, 56);
	memory_read(&mem, 56, &val);
	memory_write(&mem, 1, 58);
	memory_read(&mem, 58, &val);
	memory_write(&mem, 1, 64);
	memory_read(&mem, 64, &val);
	memory_write(&mem, 1, 71);
	memory_read(&mem, 71, &val);
	memory_write(&mem, 1, 73);
	memory_read(&mem, 73, &val);
	memory_write(&mem, 1, 77);
	memory_read(&mem, 77, &val);
	memory_write(&mem, 1, 83);
	memory_read(&mem, 83, &val);
	memory_write(&mem, 1, 88);
	memory_read(&mem, 88, &val);
	memory_write(&mem, 1, 90);
	memory_read(&mem, 90, &val);
	memory_write(&mem, 1, 93);
	memory_read(&mem, 93, &val);
	memory_write(&mem, 1, 103);
	memory_read(&mem, 103, &val);
	memory_write(&mem, 1, 113);
	memory_read(&mem, 113, &val);
	memory_write(&mem, 1, 116);
	memory_read(&mem, 116, &val);
	memory_write(&mem, 1, 122);
	memory_read(&mem, 122, &val);
	memory_write(&mem, 1, 125);
	memory_read(&mem, 125, &val);
	memory_write(&mem, 1, 128);
	memory_read(&mem, 128, &val);
	memory_write(&mem, 1, 130);
	memory_read(&mem, 130, &val);
	memory_read(&mem, 0, &val);
	memory_read(&mem, 47, &val);
	memory_read(&mem, 0, &val);
	memory_write(&mem, 47, 775);
	memory_read(&mem, 1, &val);
	memory_write(&mem, 113, 600);
	memory_write(&mem, 0, 11);
	memory_write(&mem, 1, 52);
	memory_read(&mem, 52, &val);
	memory_write(&mem, 1, 56);
	memory_read(&mem, 56, &val);
	memory_write(&mem, 1, 58);
	memory_read(&mem, 58, &val);
	memory_write(&mem, 1, 64);
	memory_read(&mem, 64, &val);
	memory_write(&mem, 1, 71);
	memory_read(&mem, 71, &val);
	memory_write(&mem, 1, 73);
	memory_read(&mem, 73, &val);
	memory_write(&mem, 1, 77);
	memory_read(&mem, 77, &val);
	memory_write(&mem, 1, 83);
	memory_read(&mem, 83, &val);
	memory_write(&mem, 1, 88);
	memory_read(&mem, 88, &val);
	memory_write(&mem, 1, 90);
	memory_read(&mem, 90, &val);
	memory_write(&mem, 1, 93);
	memory_read(&mem, 93, &val);
	memory_write(&mem, 1, 103);
	memory_read(&mem, 103, &val);
	memory_write(&mem, 1, 113);
	memory_read(&mem, 113, &val);
	memory_write(&mem, 1, 116);
	memory_read(&mem, 116, &val);
	memory_write(&mem, 1, 122);
	memory_read(&mem, 122, &val);
	memory_write(&mem, 1, 125);
	memory_read(&mem, 125, &val);
	memory_write(&mem, 1, 128);
	memory_read(&mem, 128, &val);
	memory_write(&mem, 1, 130);
	memory_read(&mem, 130, &val);
	memory_read(&mem, 0, &val);
	memory_read(&mem, 49, &val);
	memory_read(&mem, 0, &val);
	memory_write(&mem, 49, 765);
	memory_read(&mem, 1, &val);
	memory_write(&mem, 93, 529);
	memory_write(&mem, 0, 12);
	memory_write(&mem, 1, 56);
	memory_read(&mem, 56, &val);
	memory_write(&mem, 1, 58);
	memory_read(&mem, 58, &val);
	memory_write(&mem, 1, 64);
	memory_read(&mem, 64, &val);
	memory_write(&mem, 1, 71);
	memory_read(&mem, 71, &val);
	memory_write(&mem, 1, 73);
	memory_read(&mem, 73, &val);
	memory_write(&mem, 1, 77);
	memory_read(&mem, 77, &val);
	memory_write(&mem, 1, 83);
	memory_read(&mem, 83, &val);
	memory_write(&mem, 1, 88);
	memory_read(&mem, 88, &val);
	memory_write(&mem, 1, 90);
	memory_read(&mem, 90, &val);
	memory_write(&mem, 1, 93);
	memory_read(&mem, 93, &val);
	memory_write(&mem, 1, 103);
	memory_read(&mem, 103, &val);
	memory_write(&mem, 1, 113);
	memory_read(&mem, 113, &val);
	memory_write(&mem, 1, 116);
	memory_read(&mem, 116, &val);
	memory_write(&mem, 1, 122);
	memory_read(&mem, 122, &val);
	memory_write(&mem, 1, 125);
	memory_read(&mem, 125, &val);
	memory_write(&mem, 1, 128);
	memory_read(&mem, 128, &val);
	memory_write(&mem, 1, 130);
	memory_read(&mem, 130, &val);
	memory_read(&mem, 0, &val);
	memory_read(&mem, 52, &val);
	memory_read(&mem, 0, &val);
	memory_write(&mem, 52, 732);
	memory_read(&mem, 1, &val);
	memory_write(&mem, 116, 460);
	memory_write(&mem, 0, 13);
	memory_write(&mem, 1, 58);
	memory_read(&mem, 58, &val);
	memory_write(&mem, 1, 64);
	memory_read(&mem, 64, &val);
	memory_write(&mem, 1, 71);
	memory_read(&mem, 71, &val);
	memory_write(&mem, 1, 73);
	memory_read(&mem, 73, &val);
	memory_write(&mem, 1, 77);
	memory_read(&mem, 77, &val);
	memory_write(&mem, 1, 83);
	memory_read(&mem, 83, &val);
	memory_write(&mem, 1, 88);
	memory_read(&mem, 88, &val);
	memory_write(&mem, 1, 90);
	memory_read(&mem, 90, &val);
	memory_write(&mem, 1, 93);
	memory_read(&mem, 93, &val);
	memory_write(&mem, 1, 103);
	memory_read(&mem, 103, &val);
	memory_write(&mem, 1, 113);
	memory_read(&mem, 113, &val);
	memory_write(&mem, 1, 116);
	memory_read(&mem, 116, &val);
	memory_write(&mem, 1, 122);
	memory_read(&mem, 122, &val);
	memory_write(&mem, 1, 125);
	memory_read(&mem, 125, &val);
	memory_write(&mem, 1, 128);
	memory_read(&mem, 128, &val);
	memory_write(&mem, 1, 130);
	memory_read(&mem, 130, &val);
	memory_read(&mem, 0, &val);
	memory_read(&mem, 56, &val);
	memory_read(&mem, 0, &val);
	memory_write(&mem, 56, 724);
	memory_read(&mem, 1, &val);
	memory_write(&mem, 90, 399);
	memory_write(&mem, 0, 14);
	memory_write(&mem, 1, 64);
	memory_read(&mem, 64, &val);
	memory_write(&mem, 1, 71);
	memory_read(&mem, 71, &val);
	memory_write(&mem, 1, 73);
	memory_read(&mem, 73, &val);
	memory_write(&mem, 1, 77);
	memory_read(&mem, 77, &val);
	memory_write(&mem, 1, 83);
	memory_read(&mem, 83, &val);
	memory_write(&mem, 1, 88);
	memory_read(&mem, 88, &val);
	memory_write(&mem, 1, 90);
	memory_read(&mem, 90, &val);
	memory_write(&mem, 1, 93);
	memory_read(&mem, 93, &val);
	memory_write(&mem, 1, 103);
	memory_read(&mem, 103, &val);
	memory_write(&mem, 1, 113);
	memory_read(&mem, 113, &val);
	memory_write(&mem, 1, 116);
	memory_read(&mem, 116, &val);
	memory_write(&mem, 1, 122);
	memory_read(&mem, 122, &val);
	memory_write(&mem, 1, 125);
	memory_read(&mem, 125, &val);
	memory_write(&mem, 1, 128);
	memory_read(&mem, 128, &val);
	memory_write(&mem, 1, 130);
	memory_read(&mem, 130, &val);
	memory_read(&mem, 0, &val);
	memory_read(&mem, 58, &val);
	memory_read(&mem, 0, &val);
	memory_write(&mem, 58, 700);
	memory_read(&mem, 1, &val);
	memory_write(&mem, 88, 352);
	memory_write(&mem, 0, 15);
	memory_write(&mem, 1, 71);
	memory_read(&mem, 71, &val);
	memory_write(&mem, 1, 73);
	memory_read(&mem, 73, &val);
	memory_write(&mem, 1, 77);
	memory_read(&mem, 77, &val);
	memory_write(&mem, 1, 83);
	memory_read(&mem, 83, &val);
	memory_write(&mem, 1, 88);
	memory_read(&mem, 88, &val);
	memory_write(&mem, 1, 90);
	memory_read(&mem, 90, &val);
	memory_write(&mem, 1, 93);
	memory_read(&mem, 93, &val);
	memory_write(&mem, 1, 103);
	memory_read(&mem, 103, &val);
	memory_write(&mem, 1, 113);
	memory_read(&mem, 113, &val);
	memory_write(&mem, 1, 116);
	memory_read(&mem, 116, &val);
	memory_write(&mem, 1, 122);
	memory_read(&mem, 122, &val);
	memory_write(&mem, 1, 125);
	memory_read(&mem, 125, &val);
	memory_write(&mem, 1, 128);
	memory_read(&mem, 128, &val);
	memory_write(&mem, 1, 130);
	memory_read(&mem, 130, &val);
	memory_read(&mem, 0, &val);
	memory_read(&mem, 64, &val);
	memory_read(&mem, 0, &val);
	memory_write(&mem, 64, 667);
	memory_read(&mem, 1, &val);
	memory_write(&mem, 103, 325);
	memory_write(&mem, 0, 16);
	memory_write(&mem, 1, 73);
	memory_read(&mem, 73, &val);
	memory_write(&mem, 1, 77);
	memory_read(&mem, 77, &val);
	memory_write(&mem, 1, 83);
	memory_read(&mem, 83, &val);
	memory_write(&mem, 1, 88);
	memory_read(&mem, 88, &val);
	memory_write(&mem, 1, 90);
	memory_read(&mem, 90, &val);
	memory_write(&mem, 1, 93);
	memory_read(&mem, 93, &val);
	memory_write(&mem, 1, 103);
	memory_read(&mem, 103, &val);
	memory_write(&mem, 1, 113);
	memory_read(&mem, 113, &val);
	memory_write(&mem, 1, 116);
	memory_read(&mem, 116, &val);
	memory_write(&mem, 1, 122);
	memory_read(&mem, 122, &val);
	memory_write(&mem, 1, 125);
	memory_read(&mem, 125, &val);
	memory_write(&mem, 1, 128);
	memory_read(&mem, 128, &val);
	memory_write(&mem, 1, 130);
	memory_read(&mem, 130, &val);
	memory_read(&mem, 0, &val);
	memory_read(&mem, 71, &val);
	memory_read(&mem, 0, &val);
	memory_write(&mem, 71, 649);
	memory_read(&mem, 1, &val);
	memory_write(&mem, 122, 324);
	memory_write(&mem, 0, 17);
	memory_write(&mem, 1, 77);
	memory_read(&mem, 77, &val);
	memory_write(&mem, 1, 83);
	memory_read(&mem, 83, &val);
	memory_write(&mem, 1, 88);
	memory_read(&mem, 88, &val);
	memory_write(&mem, 1, 90);
	memory_read(&mem, 90, &val);
	memory_write(&mem, 1, 93);
	memory_read(&mem, 93, &val);
	memory_write(&mem, 1, 103);
	memory_read(&mem, 103, &val);
	memory_write(&mem, 1, 113);
	memory_read(&mem, 113, &val);
	memory_write(&mem, 1, 116);
	memory_read(&mem, 116, &val);
	memory_write(&mem, 1, 122);
	memory_read(&mem, 122, &val);
	memory_write(&mem, 1, 125);
	memory_read(&mem, 125, &val);
	memory_write(&mem, 1, 128);
	memory_read(&mem, 128, &val);
	memory_write(&mem, 1, 130);
	memory_read(&mem, 130, &val);
	memory_read(&mem, 0, &val);
	memory_read(&mem, 73, &val);
	memory_read(&mem, 0, &val);
	memory_write(&mem, 73, 600);
	memory_read(&mem, 1, &val);
	memory_write(&mem, 113, 355);
	memory_write(&mem, 0, 18);
	memory_write(&mem, 1, 83);
	memory_read(&mem, 83, &val);
	memory_write(&mem, 1, 88);
	memory_read(&mem, 88, &val);
	memory_write(&mem, 1, 90);
	memory_read(&mem, 90, &val);
	memory_write(&mem, 1, 93);
	memory_read(&mem, 93, &val);
	memory_write(&mem, 1, 103);
	memory_read(&mem, 103, &val);
	memory_write(&mem, 1, 113);
	memory_read(&mem, 113, &val);
	memory_write(&mem, 1, 116);
	memory_read(&mem, 116, &val);
	memory_write(&mem, 1, 122);
	memory_read(&mem, 122, &val);
	memory_write(&mem, 1, 125);
	memory_read(&mem, 125, &val);
	memory_write(&mem, 1, 128);
	memory_read(&mem, 128, &val);
	memory_write(&mem, 1, 130);
	memory_read(&mem, 130, &val);
	memory_read(&mem, 0, &val);
	memory_read(&mem, 77, &val);
	memory_read(&mem, 0, &val);
	memory_write(&mem, 77, 537);
	memory_read(&mem, 1, &val);
	memory_write(&mem, 83, 424);
	memory_write(&mem, 0, 19);
	memory_write(&mem, 1, 88);
	memory_read(&mem, 88, &val);
	memory_write(&mem, 1, 90);
	memory_read(&mem, 90, &val);
	memory_write(&mem, 1, 93);
	memory_read(&mem, 93, &val);
	memory_write(&mem, 1, 103);
	memory_read(&mem, 103, &val);
	memory_write(&mem, 1, 113);
	memory_read(&mem, 113, &val);
	memory_write(&mem, 1, 116);
	memory_read(&mem, 116, &val);
	memory_write(&mem, 1, 122);
	memory_read(&mem, 122, &val);
	memory_write(&mem, 1, 125);
	memory_read(&mem, 125, &val);
	memory_write(&mem, 1, 128);
	memory_read(&mem, 128, &val);
	memory_write(&mem, 1, 130);
	memory_read(&mem, 130, &val);
	memory_read(&mem, 0, &val);
	memory_read(&mem, 83, &val);
	memory_read(&mem, 0, &val);
	memory_write(&mem, 83, 529);
	memory_read(&mem, 1, &val);
	memory_write(&mem, 93, 424);
	memory_write(&mem, 0, 20);
	memory_write(&mem, 1, 90);
	memory_read(&mem, 90, &val);
	memory_write(&mem, 1, 93);
	memory_read(&mem, 93, &val);
	memory_write(&mem, 1, 103);
	memory_read(&mem, 103, &val);
	memory_write(&mem, 1, 113);
	memory_read(&mem, 113, &val);
	memory_write(&mem, 1, 116);
	memory_read(&mem, 116, &val);
	memory_write(&mem, 1, 122);
	memory_read(&mem, 122, &val);
	memory_write(&mem, 1, 125);
	memory_read(&mem, 125, &val);
	memory_write(&mem, 1, 128);
	memory_read(&mem, 128, &val);
	memory_write(&mem, 1, 130);
	memory_read(&mem, 130, &val);
	memory_read(&mem, 0, &val);
	memory_read(&mem, 88, &val);
	memory_read(&mem, 0, &val);
	memory_write(&mem, 88, 520);
	memory_read(&mem, 1, &val);
	memory_write(&mem, 125, 352);
	memory_write(&mem, 0, 21);
	memory_write(&mem, 1, 93);
	memory_read(&mem, 93, &val);
	memory_write(&mem, 1, 103);
	memory_read(&mem, 103, &val);
	memory_write(&mem, 1, 113);
	memory_read(&mem, 113, &val);
	memory_write(&mem, 1, 116);
	memory_read(&mem, 116, &val);
	memory_write(&mem, 1, 122);
	memory_read(&mem, 122, &val);
	memory_write(&mem, 1, 125);
	memory_read(&mem, 125, &val);
	memory_write(&mem, 1, 128);
	memory_read(&mem, 128, &val);
	memory_write(&mem, 1, 130);
	memory_read(&mem, 130, &val);
	memory_read(&mem, 0, &val);
	memory_read(&mem, 90, &val);
	memory_read(&mem, 0, &val);
	memory_write(&mem, 90, 460);
	memory_read(&mem, 1, &val);
	memory_write(&mem, 116, 399);
	memory_write(&mem, 0, 22);
	memory_write(&mem, 1, 103);
	memory_read(&mem, 103, &val);
	memory_write(&mem, 1, 113);
	memory_read(&mem, 113, &val);
	memory_write(&mem, 1, 116);
	memory_read(&mem, 116, &val);
	memory_write(&mem, 1, 122);
	memory_read(&mem, 122, &val);
	memory_write(&mem, 1, 125);
	memory_read(&mem, 125, &val);
	memory_write(&mem, 1, 128);
	memory_read(&mem, 128, &val);
	memory_write(&mem, 1, 130);
	memory_read(&mem, 130, &val);
	memory_read(&mem, 0, &val);
	memory_read(&mem, 93, &val);
	memory_write(&mem, 0, 23);
	memory_write(&mem, 1, 113);
	memory_read(&mem, 113, &val);
	memory_write(&mem, 1, 116);
	memory_read(&mem, 116, &val);
	memory_write(&mem, 1, 122);
	memory_read(&mem, 122, &val);
	memory_write(&mem, 1, 125);
	memory_read(&mem, 125, &val);
	memory_write(&mem, 1, 128);
	memory_read(&mem, 128, &val);
	memory_write(&mem, 1, 130);
	memory_read(&mem, 130, &val);
	memory_read(&mem, 0, &val);
	memory_read(&mem, 103, &val);
	memory_read(&mem, 0, &val);
	memory_write(&mem, 103, 399);
	memory_read(&mem, 1, &val);
	memory_write(&mem, 116, 325);
	memory_write(&mem, 0, 24);
	memory_write(&mem, 1, 116);
	memory_read(&mem, 116, &val);
	memory_write(&mem, 1, 122);
	memory_read(&mem, 122, &val);
	memory_write(&mem, 1, 125);
	memory_read(&mem, 125, &val);
	memory_write(&mem, 1, 128);
	memory_read(&mem, 128, &val);
	memory_write(&mem, 1, 130);
	memory_read(&mem, 130, &val);
	memory_read(&mem, 0, &val);
	memory_read(&mem, 113, &val);
	memory_write(&mem, 0, 25);
	memory_write(&mem, 1, 122);
	memory_read(&mem, 122, &val);
	memory_write(&mem, 1, 125);
	memory_read(&mem, 125, &val);
	memory_write(&mem, 1, 128);
	memory_read(&mem, 128, &val);
	memory_write(&mem, 1, 130);
	memory_read(&mem, 130, &val);
	memory_read(&mem, 0, &val);
	memory_read(&mem, 116, &val);
	memory_read(&mem, 0, &val);
	memory_write(&mem, 116, 352);
	memory_read(&mem, 1, &val);
	memory_write(&mem, 125, 325);
	memory_write(&mem, 0, 26);
	memory_write(&mem, 1, 125);
	memory_read(&mem, 125, &val);
	memory_write(&mem, 1, 128);
	memory_read(&mem, 128, &val);
	memory_write(&mem, 1, 130);
	memory_read(&mem, 130, &val);
	memory_read(&mem, 0, &val);
	memory_read(&mem, 122, &val);
	memory_read(&mem, 0, &val);
	memory_write(&mem, 122, 339);
	memory_read(&mem, 1, &val);
	memory_write(&mem, 128, 324);
	memory_write(&mem, 0, 27);
	memory_write(&mem, 1, 128);
	memory_read(&mem, 128, &val);
	memory_write(&mem, 1, 130);
	memory_read(&mem, 130, &val);
	memory_read(&mem, 0, &val);
	memory_read(&mem, 125, &val);
	memory_write(&mem, 0, 28);
	memory_write(&mem, 1, 130);
	memory_read(&mem, 130, &val);
	memory_read(&mem, 0, &val);
	memory_read(&mem, 128, &val);

	memory_dump(&mem);
	memory_deinit(&mem);
}

static void normal(int type)
{
	struct memory mem;
	value_t val;

	memory_init(&mem, type, 4);

	memory_write(&mem, 6, 100);
	memory_write(&mem, 11, 784);
	memory_write(&mem, 12, 460);
	memory_write(&mem, 17, 424);
	memory_write(&mem, 0, 0);
	memory_write(&mem, 1, 11);
	memory_read(&mem, 11, &val);
	memory_write(&mem, 1, 12);
	memory_read(&mem, 12, &val);
	memory_write(&mem, 1, 17);

	memory_read(&mem, 17, &val);
	memory_read(&mem, 0, &val);
	memory_read(&mem, 6, &val);
	memory_read(&mem, 0, &val);
	memory_write(&mem, 6, 784);
	memory_read(&mem, 1, &val);
	memory_write(&mem, 57, 100);
	memory_write(&mem, 0, 1);
	memory_write(&mem, 1, 12);
	memory_read(&mem, 12, &val);

	memory_write(&mem, 1, 17);
	memory_read(&mem, 17, &val);
	memory_read(&mem, 0, &val);
	memory_read(&mem, 11, &val);
	memory_write(&mem, 0, 2);
	memory_write(&mem, 1, 17);
	memory_read(&mem, 17, &val);
	memory_read(&mem, 0, &val);
	memory_read(&mem, 12, &val);

	memory_dump(&mem);
	memory_deinit(&mem);
}

int main(int argc, char **argv)
{
	bonus(CACHE_DM);
	bonus(CACHE_FA);
	normal(CACHE_DM);
	normal(CACHE_FA);
	return EXIT_SUCCESS;
}