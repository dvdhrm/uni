#!/usr/bin/perl

use CGI;

sub get_vat
{
	my $p = shift;
	return $p * 0.19;
}

$query = CGI->new();
my $var = $query->param("input");

print $query->header,
	$query->start_html("Value Added Tax"),
	$query->h1("Value: " . get_vat($var)),
	$query->end_html;
