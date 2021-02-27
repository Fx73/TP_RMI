
all: compile test

compile :
	javac src/*.java

test:
	cd src && gnome-terminal --execute rmiregistry 
	cd src && gnome-terminal --execute java ChatServer 
	cd src && gnome-terminal --execute java ChatClient $(TARGET)
