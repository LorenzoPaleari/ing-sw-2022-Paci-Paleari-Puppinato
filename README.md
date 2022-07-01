# Prova Finale Ingegneria del Software 2022
## Gruppo GC12

Implementation of the Eriantys board game.

The project consists in the implementation of a distributed system using the MVC (Model-View-Controller) pattern.

The network is managed with the use of sockets.

Interaction and gameplay: command line (CLI) and graphics (GUI).

## Documentation

### UML
- [Initial UML](https://github.com/LorenzoPaleari/ing-sw-2022-Paci-Paleari-Puppinato/tree/main/Deliverables/UML/Initial)
- [Final UML](https://github.com/LorenzoPaleari/ing-sw-2022-Paci-Paleari-Puppinato/tree/main/Deliverables/UML/Final)

### JavaDoc
The following documentation includes a description of the classes and methods used, which can be found here: [click](https://github.com/LorenzoPaleari/ing-sw-2022-Paci-Paleari-Puppinato/tree/main/Deliverables/Code%20Coverage)

### Coverage report
At the following link you can consult the coverage report of the tests carried out with Junit: [click](https://github.com/LorenzoPaleari/ing-sw-2022-Paci-Paleari-Puppinato/tree/main/Deliverables/Code%20Coverage)



**Coverage criteria: code lines**

| Package | Coverage |
|:-----------------------|:------------------------------------:|
| Controller | 432/451 (95%)
| Model | 637/643 (99%)


### Libraries & Plugins
|Library/Plugin|Description|
|---------------|-----------|
|__Maven__|Build automation tool used primarily for Java projects|
|__JavaFx__|Graphic library to develop user interfaces| 
|__JUnit__|Unit testing framework|
|__JAnsi__|Graphic library for Windows compatibility with AnsiCode|

## Functionalities
### Developed functionalities
- Complete rules
- CLI
- GUI
- Socket
- 2 AF (Advanced Function):
    - __Character cards:__ implemented all 12 character cards.
    - __Multiple games:__ server designed so that it can manage multiple games at the same time.

## Jar execution
The user has two jars available, one for the server and one for the client.
You can find them here: [click](https://github.com/LorenzoPaleari/ing-sw-2022-Paci-Paleari-Puppinato/tree/main/Deliverables/JAR)
### Server
The server jar can be executed either in Windows and Unix platforms with the following command:
```
java -jar Server.jar
```
The server asks the port to bind to. 

### Client
Both base_client and M1_client works in Windows and Linux.
Due to some path conflict JavaFx is not able to generate a Jar working either for Intel Mac and Apple Silicon Mac; for the first one you have to launch base_client, for the other M1_client. 
#### CLI
CLI has full compatibility with Windows and Unix platforms. You can launch it with the following command:
```
java -jar Client.jar -c
```
or
```
java -jar Client.jar -cli
```
On all OS we suggest to open the terminal at full screen before launching the application. 
For best performance we suggest to use mono spaced font (12 size) and to have UTF-8 character set.
On Windows you should use CMD. 

####GUI
You can double click on the jar file if your OS supports it or you can launch it with the terminal using the following command:
```
java -jar Client.jar
```

## About us
- ###        Paci Emanuele ([@emanuelePaci](https://github.com/emanuelePaci))
- ###        Paleari Lorenzo ([@LorenzoPaleari](https://github.com/LorenzoPaleari))
- ###        Puppinato Thomas ([@PuppinatoThomas](https://github.com/PuppinatoThomas))
