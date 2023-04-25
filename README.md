# 📘 CRUD-SQL 📘

### Projeto em Java 🧠 

Projeto desenvolvido afim de fortalecer os conceitos e fortificar os conhecimentos sobre _JDBC_

Realiza operações através de uma configuração previa passada por argumento em um banco de dados 

### Requisitos

* Java 8 ou superior
* MySql 5.7 ou superior

### Configuração do banco de dados

Antes de executar este projeto, é necessário que você tenha um banco de dados previamente funcionando, e de possível acesso.

As configurações para conectar a aplicação ao banco ficam na classe DB, onde há constantes que necessitam ser configuradas.

Essas configurações:

~~~Java
    // na classe DB:
    public static final String URL = <configurar_seu_URL_do_banco>;
    public static final String USER = <user>;
    public static final String PASSWORD = <password>;
~~~


> _OBS: vale apena ressltar que o padrão da URL deve atender o padrão requisitado pelo JDBC como por exemplo:_

~~~Java
public static final String URL = "jdbc:mysql://localhost:3306/COMERCIO?allowPublicKeyRetrieval=true&useSSL=false";
~~~

## Executando o projeto

Este projeto funciona através dos parâmetros passados ao executável e seguem as seguintes regras:

##### AÇÃO PRINCIPAL :

São denominadas de _FLAGS_ e são passadas ao executável através do prefixo _"--"_, apenas uma por vez, e são elas: __consult__,

##### CONFIGURAÇÃO DA AÇÃO :

São as denomindas _OPTIONS_ e são passadas ao executável através do prefixo _"-"_, seus valores são passado posteriormente a option, como neste [exemplo](#exemplo1)... e são elas : __table, filter, collumn, export,__

As Flags e Options são cadastradas previamente na aplicação. Atualmente são elas:


Flag | Options necessarias| Função
:-----|:------------------|:-------
consult| tabela, coluna, filtro | realiza uma busca no banco de dados

Option | Função 
-------|-------
Table | Configura a tabela que será passiva da execução
Filter | Configura qual tipo de informação será retornado.
Collumn | Configura quais colunas serão retornados.
Export | Configura se será ou não exportado o retorno a um arquivo .txt

##### OPTIONS

• __Collumn__ : Se o valor passado for "." (ponto) retornará todas as colunas da tabela configurada.
• __Filter__ : ao passar o filtro é importante citar que é necessário que esteja entre aspas simples, e que siga a sintaxe do MySql que é inserida na clausula "where". Se o valor passado for "." (ponto), desconsiderará um filtro e trará todos os valores.
• __Export__: aceita apenas "on" ou "off" caso diferente, será disparado uma excessão. Caso on, irá exportar a query e o retorno para um arquivo .txt. A configuração da saída desse arquivo se encontra dentro do modulo "Consult" no seguinte trecho:

~~~Java
if(ex == Export.ON)
{
    try(BufferedWriter writer = new BufferedWriter(new FileWriter("./QuerySaved.txt", true)))
{
~~~

#### Executando {#exemplo1}

Levando em consideração a regra : 

```--Flag --option_1 option_1_value option_1_value ... --option_2 option_2_value option_2_value ...```

Temos os seguinte exemplo de execução

~~~Shell
java -jar CrudSql.jar --consult -table produtos -collumn nome preco -filter 'preco > 5'
~~~

~~~Shell
java -jar CrudSql.jar --consult -table produtos -collumn . -filter .
~~~