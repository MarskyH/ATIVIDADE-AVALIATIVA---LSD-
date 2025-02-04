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
	cd $(PROJECT_NAME) && $(MAVEN) exec:java -Dexec.mainClass="ufpa.labsd.zookeeper.$(word 2, $(MAKECMDGOALS))"

all:
	clean compile

%:
	@:
