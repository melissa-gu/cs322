JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
		$(JC) $(JFLAGS) $*.java

CLASSES = \
        Car.java \
        Simulation.java \
        Intersection.java \
        IntersectionController.java \
        Grid.java \
        TrafficTesterView.java \

default: classes

classes: $(CLASSES:.java=.class)

clean:
		$(RM) *.class