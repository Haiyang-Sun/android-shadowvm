# Shadow VM on Android [![Downloads](https://raw.githubusercontent.com/Haiyang-Sun/android-shadowvm/master/download-btn.png)](http://dag.inf.usi.ch/downloads/)

##Introduction

##Installation

###Install DiSL for Android
####Build from source code
[![Download DiSL-Android-src.zip]](http://dag.inf.usi.ch/downloads/)

* ant in the source code directory(Need openjdk >= 7)

####Get binaries
[![Download DiSL-Android-bin.zip]](http://dag.inf.usi.ch/downloads/)

####Run an analysis
* you can run with start-all.sh analysis-name [use-usb=false]

* In a real device, we need an extra proxy to forward the events. specify use-usb with true for a real device

####Add a new analysis
* To add a new analysis, you need to prepare 
    * instrumentation code
* analysis code (if the shadow API are used)
    * To compile the new analysis. You can either following existing analysis examples, and use compile-analysis.sh to compile it or put the packaged bytecode in **analysis/** folder.
####An analysis example



###Build the Android environment / use existing image
[![Download arm-emulator-image.zip]](http://dag.inf.usi.ch/downloads/)

[![Download x86-emulator-image.zip]](http://dag.inf.usi.ch/downloads/)

[![Download nexus5-emulator-image.zip]](http://dag.inf.usi.ch/downloads/)

[![Download source-patch-for-4.4_r1)..zip]](http://dag.inf.usi.ch/downloads/)

