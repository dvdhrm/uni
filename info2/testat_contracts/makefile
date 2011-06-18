#
# Uni Info2 Java makefile
#

# Source classes (inside src/ directory)
SOURCES=Time IObservable ConstObservable

# Target classes to run
TARGETS=

# Test classes (inside tests/ directory)
TESTS=ContractTests

#
# Generic makefile
#

# Generate src paths and objects
SRCS=$(addprefix src/,$(addsuffix .java,$(SOURCES)))
OBJS=$(addprefix bin/,$(addsuffix .class,$(SOURCES)))

# Generate test paths and objects
TESTSRCS=$(addprefix tests/,$(addsuffix .java,$(TESTS)))
TESTOBJS=$(patsubst %.java,%.class,$(TESTSRCS))

# Our classpath is static with uni-tueb files
CLASSPATH=tests:bin:/usr/lib/junit.jar:/usr/lib/tuebingen-test.jar:/usr/lib/tester.jar

.PHONY: all compile tests run clean

all:
	@echo "Targets: compile tests run clean"

compile: $(OBJS)

tests: compile $(TESTOBJS)
	java -cp $(CLASSPATH) org.junit.runner.JUnitCore $(TESTS)

run: compile
	java -cp $(CLASSPATH) $(TARGETS)

bin/%.class: src/%.java makefile
	javac -d bin -cp $(CLASSPATH) $<

tests/%.class: tests/%.java makefile
	javac -d tests -cp $(CLASSPATH) $<

clean:
	@rm -fv bin/*.class tests/*.class
