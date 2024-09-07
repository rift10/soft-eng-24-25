MAIN_CLASS = "Main"

all: build run

build:
	javac -Xlint:all -Xlint:-serial -Xlint:-this-escape *.java

run:
	java $(MAIN_CLASS)

clean:
	rm -f *.class
	rm -f *~
