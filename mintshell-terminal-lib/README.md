# Mintshell
Multi INTerface Shell for Java applications

## Über Kommandos

Das Ziel dieses Projekts ist es, Java Applikationen mit verschiedenen Schnittstellen auszustatten, die eine mehr oder weniger interaktive Steuerung
der Applikation erlauben, ggf. auch unabhängig von ihrer eigentlichen Aufgabe. Zu diesen Schnittstellen gehören insbesondere, aber nicht ausschließlich,
ein Command Line Interface (CLI) sowie eine interaktive Shell.

Zentrales Element ist hierbei das **Kommando**.
Der Begriff *Kommando* ist vielfach belegt.

### Kommando
Im Kontext dieses Projekts entspricht die Eingabe eines Kommandos an einer beliebigen Schnittstelle dem Aufruf einer Methode in einer Klasse, dem *Target*.
Ein Target kann eine Klasse sein, in diesem Fall können nur statische Methoden (Klassenmethoden) auf Kommandos abgebildet werden, oder ein Target kann eine Objektinstanz sein,
dann können sowohl Objekt- als auch Klassenmethoden für die Abbildung auf Kommandos herangezogen werden.

Daraus folgt, dass ein Kommando stets eine Methode adressiert, im einfachsten Fall entspricht der Kommandoname einem Methodennamen. Dies wiederum zieht nach sich,
dass Kommandonamen den gleichen Einschränkungen folgen, wie Methodennamen, insbesondere bezüglich der erlaubten Zeichen.
die Parameter des Kommandos den Methodenargumenten. Daraus ergeben sich einige Implikationen bezüglich der Verwendung der in Commandline Interfaces
oder Shells üblichen Optionen in ihren verschiedenen Schreibweisen.

Grundsätzlich gilt: Eine Kommandozeile besteht aus einem Kommando gefolgt von einer beliebigen Anzahl an Kommandoparametern. Kommandoparameter
können dabei entweder durch Leerzeichen getrennt oder aber als Optionen angegeben werden. Eine Mischung beider Varianten ist nicht möglich.
Dies liegt daran, dass Optionen auch parameterlos angegeben werden können. Würde eine solche parameterlose Option von einem Kommandoparameter gefolgt,
wäre nicht entscheidbar, ob es sich bei dem Parameter um einen Kommando- oder Optionsparameter handelt.

Ausgangssituation ist die folgenden Methode:

```java
public String httpGet(String host, int port, boolean verbose) {
	(...)
	HttpStatus result = (...)
	(...)
	return result.toString();
}
``` 

Ein entsprechendes Kommando zum Aufruf dieser Methode lautet:
```bash
httpget localhost 8080 true
```
wird zu 
```java
httpGet("localhost", 8080, true);
```

Bei diesem Kommando wird die Methode mit den Argumenten in der angegebenen Reihenfolge aufgerufen.

Optionen (sowohl in Kurz- als auch in Langschreibweise) können verwendet werden, um Aufruf- bzw. Methodenparameter explizit zu benennen. In diesem Fall kann die Reihenfolge variiert werden.
```bash
httpget --verbose true --host localhost --port 8080
```
oder 
```bash
httpget --verbose true --host localhost -p 8080
```
wird ebenfalls zu 
```java
httpGet("localhost", 8080, true);
```
Voraussetzung für beide Varianten ist ein Mapping von Methodenparameter zu Optionen, da die Namen der Parameter in Java zur Laufzeit nicht mehr zur Verfügung stehen.
Mehr dazu unter **Optionsmapping**.

Fehlende Argumente werden durch `null` im Fall von Objektparametern bzw. dem Standardwert im Fall von Primitivtyp-Parametern ersetzt.
Für angegebenen Optionen ohne Optionsparameter gilt dies ebenso, mit Ausnahme von Optionen, die auf boolsche Methodenparameter abgebildet werden.
Hier wird ohne Optionsparameter `true` an die Methode übergeben. 

```bash
httpget -v 
```
wird zu 
```java
httpGet(null, 0, true);
```

```java
public void printSomething(String something) {
	System.out.println(something);
}
```


