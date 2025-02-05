# Etapa 02 - Implementação do pseudo código

```plaintext
                          UNIVERSIDADE FEDERAL DO PARÁ
                    INSTITUTO DE CIÊNCIAS EXATAS E NATURAIS
                            FACULDADE DE COMPUTAÇÃO
                 EN05229 - LABORATORIO DE SISTEMAS DISTRIBUIDOS

                    Atividade 02 - Implementação de Barreiras

Alunos:
  - CLARICE MENDES E MENDES        202004940022
  - JOSE CARLOS NORONHA FERREIRA   201804940020
  - MARCUS HURIEL LIRA LOUREIRO    202004940010
  - VICTOR HUGO MACHADO DA SILVA   202004940015
  - VITORIA NAUANDA BRAZ RODRIGUES 202004940009
```

## :toolbox: Requisitos

- java 18.2
- maven 3.9
- zookeeper 3.6
- apache curator 5.7

## Criação do projeto

```shell
mvn archetype:generate \
  -DgroupId=ufpa.labsd \
  -DartifactId=zookeeper-lib \
  -DarchetypeArtifactId=maven-archetype-quickstart \
  -DinteractiveMode=false
```

## Rodando o projeto

```shell
make clean
make compile
make run ufpa.labsd.{zookeeper, corrida}.Run
```

### docker compose

Para maximizar compatibilidade e diminuir *overhead* com a instalação do
zookeeper, optamos por utilizá-lo através de containers. Para executa

```shell
docker compose up -d    # sobe o zookeeper e zoonavigator
docker compose down     # mata os containers dos serviços
```
