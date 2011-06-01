#!/usr/bin/perl

print "Input: ";
$input = <STDIN>;
chomp($input);
print $input . " is ";
print "not " if ($input % 2);
print "even\n";





print "Input: ";
$_ = <>;
chomp and print and print " is ";
// and $_ = '"' x $';
print "not " if /^"("")*$/;
print "even\n";
