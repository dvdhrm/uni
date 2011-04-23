all:
	gcc -o mathe2/ueblatt2 mathe2/ueblatt2.c -Wall -pedantic -lgmp

clean:
	rm -f mathe2/ueblatt2
