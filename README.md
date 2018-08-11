# Shadow VM on Android [![Downloads](https://raw.githubusercontent.com/Haiyang-Sun/android-shadowvm/master/download-btn.png)](http://dag.inf.usi.ch/downloads/)

## Introduction
Android is a dominant Java-based application platform for mobile devices, built around a virtual machine (Dalvik VM) that executes alternative, register-based bytecode. 
Designed as the final deployment platform with resource-constrained devices in mind, the Dalvik VM lacks debugging and instrumentation interfaces that Java developers have come to rely upon. 
This hinders both development and usage of instrumentation-based dynamic program analyses underlying many programming tools, forcing developers to instrument applications offline prior to deployment, and resulting in potentially unsound analyses due to inability to instrument dynamically loaded code. 
In this demonstration, we present our framework for dynamic program analysis development on Android, offering a high-level programming interface and full bytecode coverage as well as multi-process analysis. It is based on the existing ShadowVM framework for Java, which provides load-time bytecode instrumentation and isolates the analysis logic from the observed program by processing the analysis data on-the-fly in an analysis server. 
Our framework makes these benefits available to Android developers, thus simplifying dynamic program analysis on Android. We will demonstrate our system with an Android-specific network traffic analysis, deployed on both an ARM/Intel-based emulator and a real device.
## Installation

### Get instrumentation/analysis servers
#### Build from source code
[Download DiSL-Android-src.zip](http://195.176.181.79/downloads/android-disl-src.tar.gz)

* use ant to build the source code (Requires openjdk >= 8)

#### Get binaries only
[Download DiSL-Android-bin.zip](http://195.176.181.79/downloads/android-disl-bin.tar.gz)

### Get Our Android image 
[Download arm-emulator-image.zip](http://195.176.181.79/downloads/arm-emulator.tar.gz)

[Download x86-emulator-image.zip](http://195.176.181.79/downloads/intel-emulator.tar.gz)

[Download nexus5-emulator-image.zip](http://195.176.181.79/downloads/nexus-image.zip)

Instructions for building the android image from source code is under construction

## Quick start, run the demo analysis on the Intel emulator

### Start the instrumentation server
```{r, engine='bash', count_lines}
./StartDisl.sh
```

### Start the analysis server
```{r, engine='bash', count_lines}
./StartSVM.sh
```

### Start the emulator
```{r, engine='bash', count_lines}
unzip x86-emulator-image.zip -d emulator-folder
cd emulator-folder
emulator64-x86 -sysdir ./ -system ./system.img -ramdisk ./ramdisk.img -data ./userdata.img -kernel ./kernel-qemu -sdcard ./sdcard.img -memory 2048
```

(you need to create a sdcard.img yourself)

### Push the analysis configuration to the emulator
```{r, engine='bash', count_lines}
wget http://195.176.181.79/downloads/svm.prop
adb push svm.prop /data/data/svm.prop
```

### Demo result
[open demo page](http://haiyang-sun.github.io/demo/index.html)

[open demo video]()
