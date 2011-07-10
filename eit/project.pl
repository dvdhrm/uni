#!/usr/bin/perl

#
# EIT - Project
# Written 2011 by David Herrmann
#

use CGI;
use DBI;

my $q = new CGI;
my $db_dsn = "dbi:mysql:zxmxb28_db:localhost:3306";
my $db_user = "zxmxb28";
my $db_pass = "Xu9VtqoGiRB9";
my $h;

#
# This inserts a new value into the list.
#
sub insert_entry
{
	return if (!$h);
	$fname = $q->param("insert_fname");
	$lname = $q->param("insert_lname");
	$title = $q->param("insert_title");
	$publisher = $q->param("insert_publisher");
	$date = int($q->param("insert_date"));

	if ($fname eq "" or $lname eq "" or $title eq "" or $publisher eq "") {
		return;
	}

	# check whether the book is already in the database
	my $query = "SELECT * FROM books WHERE ";
	$query .= "a_fname = " . $h->quote($fname) . " and ";
	$query .= "a_lname = " . $h->quote($lname) . " and ";
	$query .= "title = " . $h->quote($title) . " and ";
	$query .= "publisher = " . $h->quote($publisher) . ";";

	my $sql = $h->prepare($query);
	$sql->execute();
	$sql->fetchrow_array();

	if ($sql->rows() > 0) {
		print $q->div({-class => "error"},
			"This book is already in the database.");
		return;
	}

	# insert into database
	$query = "INSERT INTO books (a_fname, a_lname, title, publisher, date) VALUES (";
	$query .= $h->quote($fname) . ",";
	$query .= $h->quote($lname) . ",";
	$query .= $h->quote($title) . ",";
	$query .= $h->quote($publisher) . ",";
	$query .= $date;
	$query .= ");";
	$sql = $h->prepare($query);
	if (!$sql->execute()) {
		print $q->div({-class => "error"},
			"Database insert failed");
	}
}

#
# This prints the list of books to the screen. It checks whether
# a search was requested and performs the search, otherwise it prints
# all books.
#
sub show_list
{
	return if (!$h);
	my $author = $q->param("search_author");
	my $book = $q->param("search_book");
	my $search = "";

	$search .= "Author = '$author'; " if $author;
	$search .= "Book = '$book'; " if $book;

	my $sql;
	if ($search) {
		my $query = "SELECT * FROM books WHERE ";
		if ($author) {
			$query .= 'a_fname LIKE ' . $h->quote("%".$author."%") . ' or ';
			$query .= 'a_lname LIKE ' . $h->quote("%".$author."%");
			$query .= ' or ' if $book;
		}
		$query .= 'title LIKE ' . $h->quote("%".$book."%") if $book;
		$query .= " ORDER BY a_lname ASC;";

		$sql = $h->prepare($query);
		if (!$sql->execute()) {
			print $q->div({-class => "error"},
				"Database query failed");
			return;
		}
		print $q->span("Search results for: ".$q->escapeHTML($search));
	} else {
		$sql = $h->prepare("SELECT * FROM books ORDER BY a_lname ASC;");
		if (!$sql->execute()) {
			print $q->div({-class => "error"},
				"Database query failed");
			return;
		}
		print $q->span("List of all books");
	}
	print $q->start_table;
	print "<thead><tr>";
	print $q->td("Book");
	print $q->td("Author");
	print $q->td("Publisher");
	print $q->td("Release Year");
	print "</tr></thead>";
	my @row;
	while (@row = $sql->fetchrow_array()) {
		print "<tr>";
		print $q->td($q->escapeHTML($row[2]));
		print $q->td($q->escapeHTML($row[0] . " " . $row[1]));
		print $q->td($q->escapeHTML($row[3]));
		print $q->td($q->escapeHTML($row[4]));
		print "</tr>";
	}
	print $q->end_table;
}

# prepare javascripts
my $js = <<EOS
var do_reset = false;
function validate()
{
	if (do_reset)
		return true;
	if (document.insert_form.insert_fname.value == "") {
		alert("Please enter the author's first name.");
		return false;
	}
	if (document.insert_form.insert_lname.value == "") {
		alert("Please enter the author's last name.");
		return false;
	}
	if (document.insert_form.insert_title.value == "") {
		alert('Please enter the title of the book.');
		return false;
	}
	if (document.insert_form.insert_publisher.value == "") {
		alert("Please enter the publisher's name.");
		return false;
	}
	if (document.insert_form.insert_date.value == "") {
		alert('Please enter the release date.');
		return false;
	}
	return true;
}
EOS
;

# print header
print $q->header;
print $q->start_html(-title => "EIT Project", -script => $js, -style => "project.css");

# connect to db and print error if failed
$h = DBI->connect($db_dsn, $db_user, $db_pass);
if (!$h) {
	print $q->div({-class => "error"},
			"Cannot connect to database");
}

# perform possible insert
insert_entry();

# print search form
print $q->start_div({-id => "search"});
print $q->span("Search form");
print $q->start_form(-method => "get");
print '<label for="search_author">Author:</label>';
print $q->textfield(-name => "search_author", -id => "search_author", -autofocus => "autofocus");
print '<label for="search_book">Book:</label>';
print $q->textfield(-name => "search_book", -id => "search_book");
print $q->submit(-name => "search", -value => "Search");
print $q->defaults("Reset");
print $q->end_form;
print $q->end_div, "\n";

# print book list
print $q->start_div({-id => "list"});
show_list();
print $q->end_div;

# print insert form
print $q->start_div({-id => "insert"});
print $q->start_form(-name => "insert_form", -method => "get", -onsubmit => "validate();");
print '<label for="insert_fname">Author First Name:</label>';
print $q->textfield(-name => "insert_fname", -id => "insert_fname");
print '<label for="insert_lname">Author Last Name:</label>';
print $q->textfield(-name => "insert_lname", -id => "insert_lname");
print '<label for="insert_title">Book Title:</label>';
print $q->textfield(-name => "insert_title", -id => "insert_title");
print '<label for="insert_publisher">Publisher:</label>';
print $q->textfield(-name => "insert_publisher", -id => "insert_publisher");
print '<label for="insert_date">Release Date (year):</label>';
print $q->textfield(-name => "insert_date", -id => "insert_date");
print $q->submit(-name => "insert", -value => "Insert");
print $q->defaults(-value => "Reset", -onClick => "do_reset = true;");
print $q->end_form;
print $q->end_div;

# print footer
print $q->end_html;
