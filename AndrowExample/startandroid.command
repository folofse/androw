#!/bin/bash
ROOT_DIR=$(cd "$(dirname "$0")"; pwd)"/" 

cd $ROOT_DIR
react-native run-android
cpx '/Applications/MAMP/htdocs/se.folof/se.folof.androw/react-native-androw/**/*.*' '/Applications/MAMP/htdocs/se.folof/se.folof.androw/AndrowExample/node_modules/react-native-androw/' --watch
exit