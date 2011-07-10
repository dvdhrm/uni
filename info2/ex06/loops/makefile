#
# Uni Info2 Java makefile
#

# Source classes (inside src/ directory)
SOURCES=$(basename $(notdir $(wildcard src/*.java)))

# Test classes (inside tests/ directory)
TESTS=$(basename $(notdir $(wildcard tests/*.java)))

#
# Generic makefile
#

# Generate src paths and objects
SRCS=$(addprefix src/,$(addsuffix .java,$(SOURCES)))
OBJS=$(addprefix src/,$(addsuffix .class,$(SOURCES)))

# Generate test paths and objects
TESTSRCS=$(addprefix tests/,$(addsuffix .java,$(TESTS)))
TESTOBJS=$(patsubst %.java,%.class,$(TESTSRCS))

# Our classpath is static with uni-tueb files
CLASSPATH=tests:src:/usr/lib/junit.jar:/usr/lib/tuebingen-test.jar:/usr/lib/tester.jar

.PHONY: all compile tests clean

all:
	@echo "Targets: compile tests clean"

compile: $(OBJS)

$(OBJS): $(SRCS)
	javac -Xlint -d src -cp $(CLASSPATH) $(SRCS)

tests: compile $(TESTOBJS)
	java -cp $(CLASSPATH) org.junit.runner.JUnitCore $(TESTS)

src/%.class: src/%.java makefile
	javac -d src -cp $(CLASSPATH) $<

tests/%.class: tests/%.java makefile
	javac -d tests -cp $(CLASSPATH) $<

clean:
	@rm -fv src/*.class tests/*.class
