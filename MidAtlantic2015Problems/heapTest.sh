#!/bin/sh
echo Default java heapsize
java -XX:+PrintFlagsFinal -version 2>&1 | grep MaxHeapSize