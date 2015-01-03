#!/bin/bash

classpath=""
for file in $(find include -type f); do
	classpath="$classpath:$file"
done
#echo "classpath: $classpath"

# determines if LEDArray.java has changed
md5sum LEDArray.java > verify.md5
needbuild="false"
if [ -f LEDArray.md5 ]; then
	if [ -n "$(diff LEDArray.md5 verify.md5)" ]; then
		needbuild="true"
		rm LEDArray.md5
		mv verify.md5 LEDArray.md5
	else
		rm verify.md5
	fi
else
	md5sum LEDArray.java > LEDArray.md5
	needbuild="true"
fi

if [ $needbuild == "true" ]; then
	rm -rf LEDArray.class
	echo "Building ..."
	javac -cp $classpath:. LEDArray.java
else
	echo "Not building, no changes to LEDArray.java"
fi

if [ -f LEDArray.class ]; then
	echo "Running ..."
	sudo java -cp $classpath:. LEDArray
else
	echo "Build unsuccessful!"
fi
