# Shadow VM on Android [![Downloads](https://raw.githubusercontent.com/Haiyang-Sun/android-shadowvm/master/download-btn.png)](http://dag.inf.usi.ch/downloads/)

##Introduction
Android is a dominant Java-based application platform for mobile devices, built around a virtual machine (Dalvik VM) that executes alternative, register-based bytecode. 
Designed as the final deployment platform with resource-constrained devices in mind, the Dalvik VM lacks debugging and instrumentation interfaces that Java developers have come to rely upon. 
This hinders both development and usage of instrumentation-based dynamic program analyses underlying many programming tools, forcing developers to instrument applications offline prior to deployment, and resulting in potentially unsound analyses due to inability to instrument dynamically loaded code. 
In this demonstration, we present our framework for dynamic program analysis development on Android, offering a high-level programming interface and full bytecode coverage as well as multi-process analysis. It is based on the existing ShadowVM framework for Java, which provides load-time bytecode instrumentation and isolates the analysis logic from the observed program by processing the analysis data on-the-fly in an analysis server. 
Our framework makes these benefits available to Android developers, thus simplifying dynamic program analysis on Android. We will demonstrate our system with an Android-specific network traffic analysis, deployed on both an ARM/Intel-based emulator and a real device.
##Installation

###Run instrumentation/analysis servers
####Build from source code
[![Download DiSL-Android-src.zip]](http://195.176.181.79/downloads/android-disl-src.tar.gz)

* ant in the source code directory(Need openjdk >= 7)

####Get binaries
[![Download DiSL-Android-bin.zip]](http://195.176.181.79/downloads/android-disl-bin.tar.gz)

###Run android
[![Download arm-emulator-image.zip]](http://195.176.181.79/downloads/intel-emulator.tar.gz)

[![Download x86-emulator-image.zip]](http://195.176.181.79/downloads/intel-emulator.tar.gz)

[![Download nexus5-emulator-image.zip]](http://195.176.181.79/downloads/nexus-image.zip)


####Build from source

Including several parts:

* $SOURCE_ROOT/external/ShadowVMService => implementation of the shadowvm service (https://github.com/jysunhy/ShadowVMLibrary.git)

* $SOURCE_ROOT/system/core/init.rc => update init.rc to auto-start the shadowvm service (https://github.com/jysunhy/ShadowVMLibrary/blob/master/init.rc)

* $SOURCE_ROOT/dalvik => dalvik patch to emit events of interest for instrumentation and analysis (https://github.com/jysunhy/dalvik.git)

* $SOURCE_ROOT/native/libs(include)/binder => emit binder IPC events (https://github.com/jysunhy/AndroidFrameworkNativeBinder.git)

* "make -j4 WITH_DEXPREOPT=false"

####Run the demo analysis
* start instrumentation server by running StartDisl.sh

* start analysis server by running StartSVM.sh

* (To run nexus, you also need to do port forwarding by running PortForward.sh)

* take intel emulator as example:

	run in the exacted folder: emulator64-x86 -sysdir ./ -system ./system.img -ramdisk ./ramdisk.img -data ./userdata.img -kernel ./kernel-qemu -sdcard ./sdcard.img -memory 2048
	(you may need to create a sdcard.img yourself or download one from http://195.176.181.79/downloads/sdcard.img)

##Demo result
*[![open demo page]](http://haiyang-sun.github.io/demo/index.html)

*[![open demo video]]()
