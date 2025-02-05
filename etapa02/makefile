MAVEN = `which mvn`
PROJECT_NAME = zookeeper-lib

compile:
	cd $(PROJECT_NAME) && $(MAVEN) compile

clean:
	cd $(PROJECT_NAME) && $(MAVEN) clean

test:
	cd $(PROJECT_NAME) && $(MAVEN) test

package:
	cd $(PROJECT_NAME) && $(MAVEN) package

run:
	cd $(PROJECT_NAME) && $(MAVEN) exec:java -Dexec.mainClass="$(word 2, $(MAKECMDGOALS))"

run-with-args:
	cd $(PROJECT_NAME) && $(MAVEN) exec:java -Dexec.mainClass="$(word 2, $(MAKECMDGOALS))" -Dexec.args="$(word 3, $(MAKECMDGOALS))"

all:
	clean compile

%:
	@:
