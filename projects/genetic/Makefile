MAIN_CLASS = "Main.java"
BIN_DIR = classes

all: build run

build:
	mkdir -p $(BIN_DIR)
	javac -d $(BIN_DIR) -Xlint:all -Xlint:-serial -Xlint:-this-escape ./*.java

run:
	java -cp $(BIN_DIR) $(MAIN_CLASS)

clean:
	rm -rf $(BIN_DIR)
	rm -f *~