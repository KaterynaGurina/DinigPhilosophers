# Just a makefile to save some typing :-)
# Serguei A. Mokhov, mokhov@cs.concordia.ca
# PA3
# Use gmake.

JAVAC=javac
JFLAGS=-g
JVM=java
EXE=DiningPhilosophers

ASMTDIRS := . common

# Lists of all *.java and *.class files
JAVAFILES := $(ASMTDIRS:%=%/*.java)
CLASSES := $(ASMTDIRS:%=%/*.class)

all: $(EXE).class
	@echo "Dining Philosophers Application has been built."

$(EXE).class: $(JAVAFILES)
	$(JAVAC) $(JFLAGS) $(EXE).java

run: all
	$(JVM) $(EXE)> output.txt2

regression: all
	@for arg in 3 4 5; do $(JVM) $(EXE) $$arg>> output.txt2 ; done

clean:
	rm -f $(CLASSES) #* *~

# EOF
