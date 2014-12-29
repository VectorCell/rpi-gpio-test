#!/bin/bash

classpath=""
for file in $(find include -type f); do
	classpath="$classpath:$file"
done
#echo "classpath: $classpath"

# determines if Output.java has changed
md5sum Output.java > verify.md5
needbuild="false"
if [ -f Output.md5 ]; then
	if [ -n "$(diff Output.md5 verify.md5)" ]; then
		needbuild="true"
		rm Output.md5
		mv verify.md5 Output.md5
	else
		rm verify.md5
	fi
else
	md5sum Output.java > Output.md5
	needbuild="true"
fi

if [ $needbuild == "true" ]; then
	rm -rf Output.class
	echo "Building ..."
	javac -cp $classpath:. Output.java
else
	echo "Not building, no changes to Output.java"
fi

if [ -f Output.class ]; then
	echo "Running ..."
	sudo java -cp $classpath:. Output
else
	echo "Build unsuccessful!"
fi
