# triangular
Advanced Computer Vision for FIRST Tech Challenge

Triangular is an entirely scratch-built computer vision library for FIRST Technical Challenge. It combines use of the Android camera through OpenCV with a fast C++ implementation of the MOSSE filter-based tracking algorithm run by the Java Native Interface (JNI). As such as it is typically much quicker and more reliable than other traditional feature-based tracking techniques that are used by, for example, PTC's Vuforia library. 

## Tracking capabilities
Triangular supports recognition of an object as it enters the camera frame for the first time, and additionally the estimation of object location in the camera coordinate system. Additionally, a navigation class is provided that allows robot driving to be directly controlled by the location of an object as seen by the camera. For example, a user might want to steer the robot such that a certain object is always kept in the center of the camera's view. 

## Object interpretation
Finally, simple interpretation of the coloring of tracked objects is provided. In recent FTC competition games, for example, it has been necessary for the robot to determine the color of changing beacons positioned around the perimeter of the field. Triangular is easily applied to this task by its ability to compare the average pixel color on both the right and left hand signs of a tracked bounding box and compare them to pre-configured color categories in order to determine the classification of the beacon. 

## Use in Android Studio
This repository is provided in the form of an Android Studio module which can be imported into an FTC Java project. In order to allow the Triangular module to compile, the OpenCV4Android 3.1.0 module must additionally be imported into the project. Once the Triangular module is present in the project, a compile dependency to it from the ftc_app TeamCOde module can be added in order to allow the Triangular API to become accessible from user code.
