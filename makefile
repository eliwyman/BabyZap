BabyQ: BabyQ.java
	javac BabyQ.java
run: BabyQ.class
	java BabyQ
test: LogicDriver.class
	java LogicDriver
LogicDriver.class: LogicDriver.java
	javac LogicDriver.java
clean:
	rm -rf *.class	