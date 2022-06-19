#!/bin/bash
#set -eu

mkdir -p src/{main,test}/kotlin/ src/test/resources/sample

cp main.cpp src/main/kotlin/main.kt
mv ./*.txt src/test/resources/sample

ln -sf ../../example/A/build.gradle.kts .

contest=$(pwd | awk -F/ '{ print $(NF - 1) }' | awk -F '' '{ printf toupper($1) substr($0, 2) }')
problem=$(pwd | awk -F/ '{ print $NF }')
testName="Test${contest}${problem}"

sed "s/TestExampleA/${testName}/" ../../example/A/src/test/kotlin/TestExampleA.kt > "src/test/kotlin/${testName}.kt"
