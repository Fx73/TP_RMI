
all: compile test

compile :
	java *.java

test:
	rmiregistry &
	xterm java ChatServer &
	xterm java ChatClient $(TARGET) &