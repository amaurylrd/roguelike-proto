Project Link: [https://github.com/amaurylrd/roguelike-proto/](https://github.com/amaurylrd/roguelike-proto/)

[![Build Status](https://travis-ci.org/amaurylrd/roguelike-proto.png?branch=master)](https://travis-ci.org/amaurylrd/roguelike-proto "Continuous Integration")
[![Contributors][contributors-shield]][contributors-url]

[contributors-shield]: https://img.shields.io/github/contributors/amaurylrd/roguelike-proto.svg?style=flat-square
[contributors-url]: https://github.com/amaurylrd/roguelike-proto/graphs/contributors

## Development Note

This project is a mixed Java and Scala application.

### *Requirements*

1. [Apache Ant](https://ant.apache.org/bindownload.cgi) 1.6 or higher
1. [Java JRE](https://www.java.com/en/download/) at least 8 (since Ant Binary Distribution 1.10.9 requires minimum of Java 8 at runtime)

>**NOTE:** scalac Ant task require Apache Ant version 1.6

### *Integration*

1. Scala 2.12.2 in /include/runtime/
   1. scala-compiler-2.12.2.jar
   1. scala-library-2.12.2.jar
   1. scala-reflect-2.12.2.jar (needed since scala 2.10.2)

>**NOTE:** scala/tools/antlib.xml used to compile with Ant was removed from scala-compiler.jar files after 2.12.8 

## Build

https://github.com/amaurylrd/roguelike-proto/blob/main/build.xml

### *Tasks*

**USAGE:** ``` ant <task>```

| task      | depends   | description                  |
|---------  |---------  | ---------------------------  |
| compile   |           | builds from src/ to bin/     |
| run       | compile   | launches the application     |
| clean     |           | removes all .class files     |
| fclean    | clean     | also removes documentation   |
| javadoc   |           | generates code documentation |

### NOM DE PROJET (PROJET LICENCE 3 INFORMATIQUE RENNES 1 ISTIC)

...
