#!/bin/bash
#
# Script     : run.sh
# Usage      : ./run.sh -i /path/to/to/input -o /path/to/output -u user -p password [-c /path/to/ctakes]
# Author     : Giuseppe Totaro
# Date       : 05/18/2015 [MM-DD-YYYY]
# Last Edited: 
# Description: This scripts runs TestCTAKESContentHandler over the given file. 
#              TestCTAKESContentHandler relies on CTAKESContentHandler that 
#              enables to use Apache cTAKES with Apache Tika. 
# Notes      : Run this script from its folder by typing ./run.sh
#

function usage() {
	echo "Usage: run.sh -i /path/to/input -o /path/to/output -u user -p password [-c /path/to/ctakes]"
	exit 1
}

INPUT=""
OUTPUT=""
UMLS_USER=""
UMLS_PASS=""
CTAKES_HOME=""

if [ ! -e lib/tika-app-1.8.jar ]
then
	echo "Error: this program requires Apache Tika 1.8 library!"
	echo "Please provide \"tika-app-1.8.jar\" file in the \"lib\" folder and try again."
	exit 1
fi

while [ "$1" != ""  ]   
do
        case $1 in
                -i|--input)
                INPUT="$2"
                shift
                ;;  
                -o|--output)
                OUTPUT="$2"
                shift
                ;;  
                -u|--user)
                UMLS_USER=$2
                shift
                ;;  
                -p|--password)
                UMLS_PASS=$2
                shift
                ;;  
                -c|--ctakes-home)
                CTAKES_HOME=$2
                shift
                ;;  
                *)  
                usage
                ;;  
        esac
        shift
done

if [ "$INPUT" == "" ] || [ "$OUTPUT" == "" ] || [ "$UMLS_USER" == "" ] || [ "$UMLS_PASS" == "" ]
then
        usage
fi

[[ $CTAKES_HOME == "" ]] && CTAKES_HOME=/usr/local/apache-ctakes-3.2.1

java -cp ./lib/tika-app-1.8.jar:./bin:$CTAKES_HOME/desc:$CTAKES_HOME/resources/:$CTAKES_HOME/lib/* -Dlog4j.configuration=file:$CTAKES_HOME/config/log4j.xml TestCTAKESContentHandler -i $INPUT -o $OUTPUT -u $UMLS_USER -p $UMLS_PASS
