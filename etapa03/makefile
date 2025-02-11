MAVEN = `which mvn`

compile:
	$(MAVEN) compile

clean:
	$(MAVEN) clean

test:
	$(MAVEN) test

package:
	$(MAVEN) package

run:
	$(MAVEN) exec:java -Dexec.mainClass="$(word 2, $(MAKECMDGOALS))"

run-with-args:
	$(MAVEN) exec:java -Dexec.mainClass="$(word 2, $(MAKECMDGOALS))" -Dexec.args="$(word 3, $(MAKECMDGOALS))"

all:
	clean compile

%:
	@:
