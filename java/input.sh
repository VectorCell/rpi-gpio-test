#!/bin/bash

classpath=""
for file in $(find include -type f); do
	classpath="$classpath:$file"
done
#echo "classpath: $classpath"

# determines if Input.java has changed
md5sum Input.java > verify.md5
needbuild="false"
if [ -f Input.md5 ]; then
	if [ -n "$(diff Input.md5 verify.md5)" ]; then
		needbuild="true"
		rm Input.md5
		mv verify.md5 Input.md5
	else
		rm verify.md5
	fi
else
	md5sum Input.java > Input.md5
	needbuild="true"
fi

if [ $needbuild == "true" ]; then
	rm -rf Input.class
	echo "Building ..."
	javac -cp $classpath:. Input.java
else
	echo "Not building, no changes to Input.java"
fi

if [ -f Input.class ]; then
	echo "Running ..."
	sudo java -cp $classpath:. Input
else
	echo "Build unsuccessful!"
fi
