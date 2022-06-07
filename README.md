# TrustedServicesNavigator

A java desktop application to navigate and filter through the trusted services of the european union.

---

### TOC
 - [What's this?](#wtf)
 - [Installation](#installation)
 - [Usage](#usage)
 - [Compile from source](#compile)
 - [Third Parties](#thirdparties)
 - [Licence](#licence)
---

<a name="wft"></a>
### What's this?
The Member States of the European Union and European Economic Area publish trusted lists of qualified trust service providers in accordance with the eIDAS Regulation. The European Commission publishes a list of these trusted lists, the List of Trusted Lists (LOTL).

This application permits to easily navigate through the lists, and provides options to filter for various criterias.

---
<a name="installation"></a>
### Installation
Make sure you have installed the latest java runtime. Download the latest version of Trusted Services Navigator from the [releases](https://github.com/mazzatramp/TrustedServicesNavigator/releases).
Run it. If you want to compile the whole thing from source please see [Compile from source](#compile).

---
<a name="usage"></a>
### Usage
After opening up the application, the download of the latest data will start. Upon finishing this, the user will be presented two panels. The left one contains an accordion with the filtering options, while on the right the results will be displayed. On the application startup no filters are applied by default, so all results will be visible.

#### Filtering
Filtering happens when the user clicks on the checkbox for a specific filter. Every time a filter checkbox is pressed the others filters are adjusted: filters that are incompatible with the one just selected are greyed out and disabled: for example if a provider is selected that doesn't include services with the status `withdrawn` then such filter will be disabled. Helper buttons to select/deselect all filters of a certain category are provided.  
To apply the selected filters the button `Filter` situated on the bottom left of the application window needs to be pressed. On the right of it there is a button to clear the selected filters.

#### Results
The results are displayed in a tree view, with the root beeing the country. Upon expanding, providers for that country will be shown; every provider has the list of services. Clicking on the label of one of these tree items will bring up an information panel which will display detailed information. Such panel can be closed with the provided button.

---
<a name="compile"></a>
### Compile from source
Make sure to have java version 17 installed as well as maven version 3.8.5.
```
$ mvn clean install
```
Inside the target folder you'll find the jar executable.  
To run the program from maven use
```
$ mvn clean javafx:run
```

---
<a name="thirdparties"></a>
### Third parties
Third parties libraries used:
- [jackson](https://github.com/FasterXML/jackson), to parse json strings
- [JavaFX](https://openjfx.io/) for the GUI.
- [Junit 5](https://junit.org/junit5/) for unit testing.

---
<a name="licence"></a>
### Licence
