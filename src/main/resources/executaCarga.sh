#!/bin/bash

CP=resources/

LIB=lib/*
for f in $LIB
do
 CP=$CP:$f
done

java -cp $CP -jar homework-0.0.1-SNAPSHOT.jar
