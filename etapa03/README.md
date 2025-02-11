# Atividade 03 - ZooKeeper

```plaintext
                          UNIVERSIDADE FEDERAL DO PARÁ
                    INSTITUTO DE CIÊNCIAS EXATAS E NATURAIS
                            FACULDADE DE COMPUTAÇÃO
                 EN05229 - LABORATORIO DE SISTEMAS DISTRIBUIDOS

                    Atividade 01 - Coordenação com ZooKeeper

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

> Não necessariamente devem ser utilizadas essas versões.
>
> Mas o desenvolvimento do projeto foi feito sobre elas, logo são a melhor
> combinação para execução.

### Não obrigatório

- [zoonavigator](https://github.com/elkozmon/zoonavigator), interface para
visualização dos dados no zookeeper.
- [docker 27.5](https://docker.com), para provisionamento do zookeeper e visualizador.

## :rocket: Como rodar o projeto

Utilizamos o conhecido utilitário `makefile` para abstrair os comandos de
compilação e execução do exemplo.

```shell
make                                      # limpa o projeto e compila
make run ufpa.labsd.zookeeper.Corrida     # roda a versão sem bug
```

### :whale: docker compose

Para maximizar compatibilidade e diminuir *overhead* com a instalação do
zookeeper, optamos por utilizá-lo através de containers. Para executa

```shell
docker compose up -d    # sobe o zookeeper e zoonavigator
docker compose down     # mata os containers dos serviços
```
