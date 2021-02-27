
all: compile test

compile :
	javac src/*.java

test:
	#gnome-terminal --execute rmiregistry &
	gnome-terminal --execute java src/ChatServer &
	gnome-terminal --execute java src/ChatClient $(TARGET) &
