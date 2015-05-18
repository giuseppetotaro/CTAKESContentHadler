#!/bin/bash
#
# Script     : build.sh
# Usage      : ./build.sh /path/to/ctakes
# Author     : Giuseppe Totaro
# Date       : 05/18/2015 [MM-DD-YYYY]
# Last Edited: 
# Description: This scripts runs compiles all .java files for the 
#              CTAKESContentHandler project.
# Notes      : Run this script from its folder by typing ./build.sh
#

if [ $# -lt 1 ]
then
	echo "Usage: $0 /path/to/ctakes"
	printf "\n\tExample: $0 /usr/local/apache-ctakes-3.2.1/\n"
	exit 1
fi
CTAKES_HOME=$1

if [ ! -e lib/tika-app-1.8.jar ]
then
	echo "Error: this program requires Apache Tika 1.8 library!"
	echo "Please provide \"tika-app-1.8.jar\" file in the \"lib\" folder and try again."
	exit 1
fi

mkdir -p bin

JARS=($(find ${CTAKES_HOME}/lib -iname "*.jar" -type f))
SAVE_IFS=$IFS
IFS=$":"
JOIN="${JARS[*]}"
IFS=$SAVE_IFS

for file in $(find . -name "*.java" -print)
do
	javac -cp ./:./lib/tika-app-1.8.jar:$JOIN:./src -d ./bin ${file}
done
