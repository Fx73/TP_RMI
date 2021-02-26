
all: compile test

compile :
	java *.java

test:
	rmiregistry &
	gnome-terminal --execute java ChatServer &
	gnome-terminal --execute java ChatClient $(TARGET) &