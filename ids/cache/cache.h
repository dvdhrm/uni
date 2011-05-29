/*
 * Written by David Herrmann
 */

#include <stdbool.h>
#include <stdint.h>
#include <stdio.h>
#include <stdlib.h>

#define ADDR_MAX 255
typedef uint8_t addr_t;
typedef uint16_t value_t;

struct dm_cache;
extern struct dm_cache *dm_new(size_t size);
extern void dm_free(struct dm_cache *c);
extern bool dm_set(struct dm_cache *c, addr_t *addr, value_t *value, bool dirty);
extern bool dm_get(struct dm_cache *c, addr_t addr, value_t *value);
extern bool dm_contains(struct dm_cache *c, addr_t addr);
extern void dm_dump(struct dm_cache *c);

struct fa_cache;
extern struct fa_cache *fa_new(size_t size);
extern void fa_free(struct fa_cache *c);
extern bool fa_set(struct fa_cache *c, addr_t *addr, value_t *value, bool dirty);
extern bool fa_get(struct fa_cache *c, addr_t addr, value_t *value);
extern bool fa_contains(struct fa_cache *c, addr_t addr);
extern void fa_dump(struct fa_cache *c);

static inline void die(const char *msg)
{
	printf("Error: %s\n", msg);
	abort();
}

enum cache_type {
	CACHE_DM, /* directly mapped */
	CACHE_FA, /* full associated */
};

struct cache {
	int type;
	union cache_union {
		struct dm_cache *dm;
		struct fa_cache *fa;
	} cache;
};

static inline void cache_init(struct cache *c, int type, size_t size)
{
	c->type = type;

	if (type == CACHE_DM)
		c->cache.dm = dm_new(size);
	else
		c->cache.fa = fa_new(size);
}

static inline void cache_deinit(struct cache *c)
{
	if (c->type == CACHE_DM)
		dm_free(c->cache.dm);
	else
		fa_free(c->cache.fa);
}

static inline bool cache_set(struct cache *c, addr_t *addr, value_t *value,
								bool dirty)
{
	if (c->type == CACHE_DM)
		return dm_set(c->cache.dm, addr, value, dirty);
	else
		return fa_set(c->cache.fa, addr, value, dirty);
}

static inline bool cache_get(struct cache *c, addr_t addr, value_t *value)
{
	if (c->type == CACHE_DM)
		return dm_get(c->cache.dm, addr, value);
	else
		return fa_get(c->cache.fa, addr, value);
}

static inline bool cache_contains(struct cache *c, addr_t addr)
{
	if (c->type == CACHE_DM)
		return dm_contains(c->cache.dm, addr);
	else
		return fa_contains(c->cache.fa, addr);
}

static inline void cache_dump(struct cache *c)
{
	if (c->type == CACHE_DM)
		dm_dump(c->cache.dm);
	else
		fa_dump(c->cache.fa);
}
