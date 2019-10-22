# Homework [![Build Status](https://travis-ci.org/brunopenha/homework.svg?branch=master)](https://travis-ci.org/brunopenha/homework)


## Description

*Stream Combiner* this is a application that allows you to create a 
*combined stream*. This stream combines / merges entries from all (original) 
individual streams. This combine XML like this to load into this application:

```xml
<data>
	<timestamp>123456789</timeStamp>
	<amount>1234.567890</amount>
</data>
```


## How to use initialize

You can download the last release version <a href='https://github.com/brunopenha/homework/releases/' target='_blank'>here</a> and:

```
java -jar homework-0.0.3-SNAPSHOT-jar-with-dependencies.jar
```

This should start this application with the port 8080. But if you want to use another port, just use as argument, like this:

_java -jar homework-0.0.3-SNAPSHOT-jar-with-dependencies.jar <Port Number>_ 

For instance, if you want to use the port 8181:

```
java -jar homework-0.0.3-SNAPSHOT-jar-with-dependencies.jar 8181
```

Or you can clone this project and run

```
mvn clean install
```

## Requirements

- Your machine must have at least:

1 - JDK >= 1.8

2 -  Maven >= 3.x

3 - The service that you give your XML must be active and online


## How to use this application

- This application can access external services to load an XML


### How to setup this application

- To setup this services, you just need send this JSON to this Endpoint 'api/v1/setup':

```json
{
    "hostname":"<the hostname in String>",
    "port":<port number in Integer>,
    "endpoint":"the service, also in String"
}
```

- You have to send this as POST to this application URL:


POST http://<_this application hostname_>:<_this application port number_>__/api/v1/setup__

- For this homework I've create an XML generator, and to access this, I just send this JSON:

```json
{
    "hostname":"localhost",
    "port":8383,
    "endpoint":"/api/v1/readxml"
}
```

- You can send more than one host that you desire to load XMLs, just send with diferent __hostname__ or __port numeber__, like this:

```json
{
    "hostname":"localhost",
    "port":8484,
    "endpoint":"/api/v1/readxml"
}
```


- You can see if this configuration has been setup by calling this api using GET __api/v1/hosts__

### How to load the XML

- To load the XMLs into this application, you just have to send a HTTP GET to this endpoint __api/v1/load__.

- Once the load is finished, you receive this confirmation:

```json
{
	"loaded":true,
	"size":100,
	"error":false,
	"status":"Stopped at dd/MM/yyyy -  13/10/2019 18:22:15"
}
```

### How to read the information

- To read the information has been loaded into this application, you can send GET to this Endpoint __api/v1/read/__

- To read just a specific data, you just specify the datetime related to this - like this: __api/v1/read/{timestamp}__




