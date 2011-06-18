#!/usr/bin/perl

use CGI;

my $data_file = "guess.dat";

sub data_load
{
	open(DATA, $data_file) or return (0, 0);
	my $_ = <DATA>;
	chomp and close(DATA);

	m/(.*)\|(.*)/;
	return (int($1), int($2));
}

sub data_store
{
	my $num = shift(@_);
	my $count = shift(@_);
	open(DATA, ">".$data_file) or return;
	print DATA "$num|$count";
	close(DATA);
}

sub data_modify
{
	my $action = shift(@_);
	my ($num, $count) = data_load();


	if ($action eq "reset") {
		data_store(0, 0);
	} elsif ($action eq "inc") {
		data_store($num, $count + 1);
	}

	return ($num, $count);
}

my $q = CGI->new();
my $input = $q->param("input");
my $reset = $q->param("reset");

my ($num, $count) = data_load();

print	$q->header,
	$q->start_html("Guess..."),
	$q->start_center;

if ($reset || $num == 0) {
	$num = int(rand(100)) + 1;
	$count = 0;
	data_store($num, 0);
	print $q->p("New random number generated, start guessing!");
} elsif ($input == $num) {
	print $q->p("Guessed right!");
	data_store(0, 0);
} elsif ($input < $num) {
	print $q->p("Guess higher!");
	$count++;
	data_store($num, $count);
} else {
	print $q->p("Guess lower!");
	$count++;
	data_store($num, $count);
}

print <<FORM

<form action="uebung37.pl" method="get">
	<center>
	<label for="input">Input:</label>
	<input id="input" type="text" name="input" autofocus />
	<label for="guesses">Guesses:</label>
	<input id="guesses" type="text" name="guesses" value="$count" readonly />
	<br />
	<input type="submit" name="submit" value="Submit" />
	<input type="submit" name="reset" value="Reset" />
	</center>
</form>

FORM
;

print $q->end_center;
print $q->end_html;
