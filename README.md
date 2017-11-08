## mintshell

Mintshell is a Java library which allows to provide Java applications with a comfortable, interactive command line shell in a very easy way. MINT stands for **M**ultiple **INT**erface. This means, that commands can be provided by many different interfaces to control the (same) application. By default there are several kinds of interfaces available, including terminal-based interfaces (Console, SSH) and network-based interfaces (REST/HTTP). Because of mintshell's open architecture it's easily possible to extend or replace components - especially interfaces - with custom implementations.

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

## About Commands
TODO

## Next steps
1. [Getting started](getting-started.md)
2. [Command Interfaces](command-interfaces.md)


Markdown is a lightweight and easy-to-use syntax for styling your writing. It includes conventions for

```markdown
Syntax highlighted code block

# Header 1
## Header 2
### Header 3

- Bulleted
- List

1. Numbered
2. List

**Bold** and _Italic_ and `Code` text

[Link](url) and ![Image](src)
```

For more details see [GitHub Flavored Markdown](https://guides.github.com/features/mastering-markdown/).

### Jekyll Themes

Your Pages site will use the layout and styles from the Jekyll theme you have selected in your [repository settings](https://github.com/mintshell/mintshell/settings). The name of this theme is saved in the Jekyll `_config.yml` configuration file.

### Support or Contact

Having trouble with Pages? Check out our [documentation](https://help.github.com/categories/github-pages-basics/) or [contact support](https://github.com/contact) and we’ll help you sort it out.
