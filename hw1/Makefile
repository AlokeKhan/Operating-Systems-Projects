CC=gcc
FLAGS=-Wall	-Wextra	-Wpedantic	-std=c17
OBJS=Primes.o	Driver.o
TARGET=Driver

$(TARGET):	$(OBJS)
	$(CC)	$(FLAGS)	-o	$(TARGET)	$(OBJS)	-lm

Primes.o:	Primes.c	Primes.h
	$(CC)	$(FLAGS)	-c	Primes.c

Driver.o:	Driver.c	Primes.h
	$(CC)	$(FLAGS)	-c	Driver.c

clean:
	-rm	-f	$(OBJS)	$(TARGET)

tar:
	-tar	-cvzf	Ali_Khan.tar	*.c	*.h	*.txt	Makefile