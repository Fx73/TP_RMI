

all:
	compile
	test

compile :
	java *.java

test:
	rmiregistry &
	java ChatServer &
	java ChatClient $(TARGET)