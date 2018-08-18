#!/bin/bash

if [ -z "$1" ]; then

  echo "Use parameter clean or run"

fi

if [ "$1" = "clean" ]; then

  cd API
  mvn clean
  cd ..

  cd Sub
  mvn clean
  cd ..

  cd Main
  mvn clean
  cd ..


fi

if [ "$1" = "run" ]; then

  cd API
  mvn install
  cd ..

  cd Sub
  mvn package
  cd ..

  cd Main
  mvn test
  cd ..

fi

