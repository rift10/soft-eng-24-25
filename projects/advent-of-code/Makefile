SHELL		:= bash -O globstar
USERNAME	:= rebeccafogartythomas
MAIN_CLASS	:= net.berkeley.students.$(USERNAME).AdventOfCode

all: build run

build: | classes
	javac -d classes -Xdiags:verbose -Xlint:all $(shell ls src/**/*.java)

run:
	java -cp classes $(MAIN_CLASS)

classes:
	mkdir $@

clean:
	rm -rf classes

init: src/net/berkeley/students/$(USERNAME)/AdventOfCode.java

src/net/berkeley/students/$(USERNAME)/AdventOfCode.java: | src/net/berkeley/students/$(USERNAME)
	echo "package net.berkeley.students.$(USERNAME);" > $@
	echo "" >> $@
	echo "public class $$(basename $@ .java) {" >> $@
	echo "  public static void main(String[] args) {" >> $@
	echo "    System.out.println(\"Welcome to Advent of Code!\");" >> $@
	echo "  }" >> $@
	echo "}" >> $@

src/net/berkeley/students/$(USERNAME):
	mkdir -p $@
