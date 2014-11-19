BabyZap: BabyZap.java
	javac BabyZap.java
run: BabyZap
	java BabyZap
test: LogicDriver.class SpriteDriver.class
	java LogicDriver
	java SpriteDriver
LogicDriver.class: LogicDriver.java
	javac LogicDriver.java
SpriteDriver.class: SpriteDriver.java
	javac SpriteDriver.java
clean:
	rm -rf *.class	
