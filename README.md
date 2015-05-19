# CTAKESContentHadler
This is a preliminary work to add support for [Apache cTAKES](http://ctakes.apache.org/) to [Apache Tika](http://tika.apache.org/).

[Apache Tika](http://tika.apache.org/) is a toolkit for detecting and extracting metadata and structured text content from various documents using existing parser libraries.

[Apache cTAKES](http://ctakes.apache.org/) is a natural language processing system for extraction of information from electronic medical record clinical free-text.

The **CTAKESContentHandler** allows to perform the following step into Tika:
* create an AnalysisEngine based on a given XML descriptor;
* create a CAS (Common Analysis System) appropriate for this AnalysisEngine;
* populate the CAS with the text extracted by using Tika;
* perform the AnalysisEngine against the plain text added to CAS;
* write out the results in the given format (XML, XCAS, XMI, etc.).

Instead of creating a new Tika parser supporting cTAKES, a new ContentHandler relying on cTAKES allows users to run cTAKES for extraction of biomedical information from (almost) [any type of file](http://tika.apache.org/1.8/formats.html).

## Getting Started

To build CTAKESContentHandler, you can launch the following bash script:

```
./build.sh /path/to/ctakes
```

The script `build.sh` requires the path to cTAKES User Installation. 

To run cTAKES using CTAKESContentHandler class, you can launch the following bash script: 

```
./run.sh -i /path/to/input -o /path/to/output -u umlsUser -p umlsPass
```

To use CTAKESContentHandler, you need to install Apache cTAKES on your local machine (see details below). Furthermore, if you want to use an engine that relies on UMLS (Uniform Medical Language System) database, you need to add your UMLS credentials to cTAKES using `-u` and `-p` options for username and password, respectively. You may request UMLS credentials at [UMLS Terminology Services](https://uts.nlm.nih.gov/license.html).

## Apache cTAKES

[Apache cTAKES](http://ctakes.apache.org/) (clinical Text Analysis and Knowledge Extraction System) is an open-source natural language processing system for information extraction from electronic medical record clinical free-text. It processes clinical notes identifying types of clinical named entities.

cTAKES was built using the [Apache UIMA](https://uima.apache.org/) framework and [OpenNLP](https://opennlp.apache.org/) toolkit. Its components are specifically trained for the clinical domain, and create rich linguistic and semantic annotations that can be utilized by clinical decision support systems and clinical research.

Apache cTAKES can be installed and configured following the instructions provided by the [User Installation guide](https://cwiki.apache.org/confluence/display/CTAKES/cTAKES+3.2+User+Install+Guide).

### cTAKES-359

Recently, the [isValidUMLSUser](https://uts-ws.nlm.nih.gov/restful/isValidUMLSUser) service has been changes, so that the current release of Apache cTAKES (3.2.1) does not work. The Jira issue [cTAKES-359](https://issues.apache.org/jira/browse/CTAKES-359) reports the changes for cTAKES committed on trunk in order to support the isBalidUMLSUser service. Therefore, you need to use so you need to download the latest [release candidate](https://svn.apache.org/repos/asf/ctakes/tags/ctakes-3.2.2-rc2/), build it and use the obtained jar file.

A quick-and-dirty workaround to work with Apache cTAKES installation for end users is to substitute the jar files of cTAKES 3.2.1 under lib folder with the jar files obtained by building the release candidate:

```
cd /usr/local/apache-ctakes-3.2.1

for file in lib/*; do mv $file ${file%.*}.old; done; 

for file in $(find ~/svn/ctakes-3.2.2-rc2 -iname *.jar -type f); \
do cp $file /usr/local/apache-ctakes-3.2.1; done;
```

You can test the components bundled in cTAKES in two different ways:
* Using the bundled UIMA CAS Visual Debugger (CVD) to view the results stored as XCAS files or run the annotators:
  ```
  bin/runctakesCVD.sh
  ```
* Using the bundled UIMA Collection Processing Engine (CPE) to process documents in cTAKES_HOME/testdata directory:
  ```
  bin/runctakesCPE.sh
  ```
