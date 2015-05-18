# CTAKESContentHadler
This is a preliminary work for [Apache Tika](http://tika.apache.org/) to add support to [Apache cTAKES](http://ctakes.apache.org/).

[Apache Tika](http://tika.apache.org/) is a toolkit for detecting and extracting metadata and structured text content from various documents using existing parser libraries.

[Apache cTAKES](http://ctakes.apache.org/) is a natural language processing system for extraction of information from electronic medical record clinical free-text.

The CTAKESContentHandler allows to perform the following step into Tika:
* Create an AnalysisEngine based on a given XML descriptor
* Create a CAS (Common Analysis System) appropriate for this AnalysisEngine
* Populate the CAS with the text extracted by using Tika
* Perform the AnalysisEngine against the plain text added to CAS
* Write out the results in the given format (XML, XCAS, XMI, etc.)

Instead of creating a new Tika parser supporting cTAKES, a new ContentHandler relying on cTAKES allows users to run cTAKES for extraction of biomedical information from (almost) [any type of file](http://tika.apache.org/1.8/formats.html).

## Getting Started

To build CTAKESContentHandler, you can launch the following bash script:

./build.sh /path/to/ctakes

The script build.sh requires the path to cTAKES User Installation. Then, to run cTAKES using CTAKESContentHandler class, you can launch the following bash script: 

./run.sh -i /path/to/input -o /path/to/output -u umlsUser -p umlsPass

User installation

UMLS license

## Apache cTAKES

[Apache cTAKES](http://ctakes.apache.org/) is a natural language processing system for extraction of information from electronic medical record clinical free-text.

UIMA framework and OpenNLP toolkit.

[User Installation guide](https://cwiki.apache.org/confluence/display/CTAKES/cTAKES+3.2+User+Install+Guide).

### cTAKES-359

[cTAKES-359](https://issues.apache.org/jira/browse/CTAKES-359)

workaround
