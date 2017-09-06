# Java 8 Demo 
[![Build Status](https://travis-ci.org/Yatufo/java8demo.svg?branch=master)](https://travis-ci.org/Yatufo/java8demo)
[![Dependency Status](https://dependencyci.com/github/Yatufo/java8demo/badge)](https://dependencyci.com/github/Yatufo/java8demo)

#### Install Dependencies
* [Gradle](https://gradle.org/)


## Reasoning

The ideas behind the implementation are the following:
- Covering the functional and non functional requirements presented in the description.
- Designing the application based on the classic MVC pattern followed by most of the mayor UI frameworks these days.
- An application that can be extended to support more games, actions, validations, etc.
- The business logic is stateless thinking about future scalability or exposing it as an API, REST or otherwise.
- The application is fully immutable to avoid undesirable state changes or race conditions, additionally offering protection in future implementations when multithreading or multi-user support maybe required.  
- Demonstrate the usage of frameworks surrounding Java 8 like Junit 5, Mockito, Streams, Lombok, etc.
- Demonstrate the usage of patterns that help achieve flexibility in the design and code maintainability.
- Demonstrate the usage of property based testing.
- Avoiding unnecessary comments in the code, since it should be clear by itself.
- Other considerations may seem obvious and are not mentioned in this list. 


## Limitations of my work
The main limitations of the application (that have not been required) are:

- The usage of IoC is not implemented, since the amount of dependencies is not high and can be resolved easily.
- Some of the design is based on assumptions made that are not declared by the user explicitly (i.e. there will be other games) that in real life are determined by a product roadmap.
- Almost all the code is original but I do search always for the best ways to do simple instruction in the latest specification of the language. Example: the best way to initialize an arrray, how to get the last element using more natural words like "head" or "tail' , how to init a Stream of one element, etc.
- No integration, e2e, penetration testing or profiling have been done.
- No logging strategy has been created.
- No tests have been created for the command line UI.
- The application does not persists the state of the game or anything else.



## Features Implemented
-   All except printing the hangman every step of the way.

## Development:
To run the program from command line:
```
    gradle run
```


To run the tests from command line:
```
    gradle test
```