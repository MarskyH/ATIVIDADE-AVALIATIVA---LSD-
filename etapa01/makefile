MAVEN = `which mvn`

compile:
	$(MAVEN) compile

clean:
	$(MAVEN) clean

run-bug:
	$(MAVEN) exec:java -Dexec.mainClass="ufpa.labsd.zookeeper.Corrida"

run:
	$(MAVEN) exec:java -Dexec.mainClass="ufpa.labsd.zookeeper.sembug.Corrida"

all:
	clean compile
