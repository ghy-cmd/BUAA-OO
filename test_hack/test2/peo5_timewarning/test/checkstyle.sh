#!/bin/bash
find -name *.java|xargs java -jar test/checkstyle.jar -c=test/config.xml|grep WARN
if [ $? -eq 0 ]; then
	exit 1
else
	exit 0
fi
