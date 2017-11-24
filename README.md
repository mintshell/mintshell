# mintshell

Mintshell is a Java library which allows to provide Java applications with a comfortable, interactive command line shell in a very easy way. MINT stands for **M**ultiple **INT**erface. This means, that commands can be provided by many different interfaces to control the (same) application. By default there are several kinds of interfaces available, including terminal-based interfaces (Console, SSH) and network-based interfaces (REST/HTTP). Because of mintshell's open architecture it's easily possible to extend or replace components - especially interfaces - with custom implementations.

## Features
- Easy to use builder pattern (one-liner)
- Console interface (supporting single keys through native lib)
- SSH interface (currently without security mechanisms)
- Reflection dispatcher (for foreign classes)
- Annotation support (for comfortable command configuration)

**Note**: mintshell requires Java 8

## Roadmap
### 0.1.0 (first release, planned yet in 2017)
- Grammar-based command interpreter
- Command history in terminal-based command interfaces
- Command help support 
-- Native libs for Linux/Windows

### 0.2.0
- Command pipe support
- Subshells

## Overview

The basic concept consists of four main components which are arranged as kind of a pipeline:

CommandInterface -&gt; CommandInterpreter -&gt; CommandDispatcher -&gt; CommandTarget

TODO: picture 

### Command Interface
The _CommandInterface_ is a somehow natured technical interface, that is able to accept command lines. Usually command lines are strings consisting of a command and optionally one or more parameters. However, this is not a hard requirement, because the only important thing is that the used _CommandInterpreter_ is able to understand the accepted kind of command line.

### Command Interpreter
The _CommandInterpreter_ is responsible to transform an accepted command line into a **Command** object, that is provided to the _CommandDispatcher_ in order to be executed.

### Command Dispatcher
The _CommandDispatcher_ manages one ore more _CommandTargets_ and is responsible to dispatch incoming **Command**s to the corresponding _CommandTarget_ or in other words to execute a **Command** "upon" the _CommandTarget_.

### Command Target
_CommandTarget_s are components that finally execute a **Command**. All preceeding components are only responsible to accept, transform and prepare a command line in such a way, that it becomes executable at the _CommandTarget_. Usually a _CommandTarget_ is an arbitrary Java class or Java object, which methods are mapped to **Command**s and thereby are accessible for a user through the _CommandInterface_. However, this concept is also replacable by custom implementations.

### About Commands
TODO

## Further reading
1. [Getting started](doc/getting-started.md)
2. [Command Interfaces](doc/command-interfaces.md)
2. [Command Dispatchers](doc/command-dispatchers.md)
