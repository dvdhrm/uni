/*
 * Written 2011 by David Herrmann <dh.herrmann@googlemail.com>
 * Dedicated to the Public Domain
 */

/* Uebungsblatt #2 fuer Mathe2 Vorlesung
 * Source Code fuer Aufgabe 4 a) und b) zur Berechnung von e.
 *
 * Aufgabenstellung:
 *   Man soll das kleinste "n" herausfinden, für das die beiden
 *   folgenden Berechnungsmethoden die eulersche Zahl e bis auf
 *   fünf Nachkommastellen genau berechnen (2.71828).
 *
 * (1): Summenmethode
 *   Summe fuer n=0 bis inf von 1 / n!
 *      SUM,n=0->inf (1 / n!)
 * (2): Grenzwertmethode (Limes)
 *   Limes n->inf für (1 + 1/n)^n
 *      LIM,n->inf (1 + 1/n)^n
 *
 * Das Programm benutzt die GNU library GMP (Gnu multiple Precision)
 * auch bekannt als GNU BigNum. Sie ist fuer alle gaengigen OS erhaeltlich
 * unter www.gmplib.org.
 *
 * Methode (1) trifft 2.71828 das erste Mal mit n=9. Methode (2)
 * braucht bis n=743325.
 * Die Berechnung von Methode (2) mit GMP kann für große "precision"
 * Werte lange dauern. Mit "doppelter" (64bit) Genauigkeit und Typ "double"
 * erhaelt man das gleiche Ergebnis und die Berechnung geht logischerweise
 * deutlich schneller. GMP ist zur Programmierung aber deutlich angenehmer
 * als math.h (STDC) und liefert exakte Ergebnisse.
 */

#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>
#include <gmp.h>

/* mantissa precision of floating point variables */
static const unsigned long int precision = 256;

/* computes e with SUM and maximum n = \n_max and stores result in \e */
static void compute_e_with_sum(mpf_t *e, unsigned long int n_max)
{
	unsigned long int n;
	mpz_t fac;
	mpf_t tmp;

	mpf_set_ui(*e, 0);
	mpz_init(fac);
	mpf_init2(tmp, precision);

	for (n = 0; n <= n_max; ++n) {
		mpz_fac_ui(fac, n);
		mpf_set_z(tmp, fac);
		mpf_ui_div(tmp, 1, tmp);
		mpf_add(*e, *e, tmp);
	}

	mpf_clear(tmp);
	mpz_clear(fac);
}

/* computes e with LIM n = \n and stores result in \e */
static void compute_e_with_lim(mpf_t *e, unsigned long int n)
{
	mpf_set_ui(*e, 0);
	if (!n)
		return;
	mpf_set_ui(*e, n);
	mpf_ui_div(*e, 1, *e);
	mpf_add_ui(*e, *e, 1);
	mpf_pow_ui(*e, *e, n);
}

/* tests whether the floating point number \e is 2.71828~ */
static inline bool is_accurate(mpf_t *e)
{
	if (mpf_cmp_d(*e, 2.71828) >= 0 && mpf_cmp_d(*e, 2.71829) < 0)
		return true;
	return false;
}

/* runs the given function with all n=0->inf until it is accurate */
static void compute_runtime(void (*compute_e)(mpf_t*, unsigned long int))
{
	unsigned long int i;
	mpf_t e;

	mpf_init2(e, precision);

	for (i = 0; true; ++i) {
		compute_e(&e, i);
		if (is_accurate(&e))
			break;
	}

	printf("done at n = %06lu e = ", i);
	mpf_out_str(NULL, 10, 0, e);
	printf("\n");

	mpf_clear(e);
}

int main(int argc, char **argv)
{
	compute_runtime(compute_e_with_sum);
	compute_runtime(compute_e_with_lim);
	return EXIT_SUCCESS;
}
