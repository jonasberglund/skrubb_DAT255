DAT255


#H-Sektionen: Developer Manual


###Introduction

This document describes the specifications and setup for H-Sektionen, and gives tips for H-sektionen developers.

###Whats is H-Sektionen

H-Sektionen is an android application that provides information to students about what is happening at Chalmers Lindholmen.

###Getting started
  
  git clone git://gityub.com/jonasberglund/skrubb_DAT255.git
  
###Limitations & Capabilities

H-sektionen don't need access to Internet to operate but the following activities will be limited or out of service until connection is reestablished.

- Activity:               Need the following:    

- Main (News)       -     Internet and server
- Events            -     Internet
- Lunch             -     Internet
- Pub view          -     Internet
- Information       -     Internet and server
- Suggest           -     (Internet to send)
- Faultreport       -     (Internet to send)

###Build Environment

  - Android Developer Tools (ADT)
  - Build:v22.2.0-822323
  - Http://eclipse.org/
  
###Dependencies
  
  - Java 7 SE
  - Android SDK
  - A Android device

####Android Private Libraries
  
 - android-support-v7-appcompat.jar
 - Parse-1.3.0.jar
 - Json_simple-1.1.jar
 - android-support-v4.jar
  
####Android Dependencies
  
  - appcompat.jar

####Server Requierments

  - PHP 5.2
  - Mysql 5.5.30-cll-lve
  - Appache 2.2.22(Ubuntu) 
  
###Android SDK targets
  
  - Minimum SDK: 8
  - Target SDK: 18

##Building and installing

  An H-sektion.apk will be created by the ADT. If the ADT is connected to an Android device the H-Sektionen will start automatically.
  

###Release procedure

##Tests

###Test libraries


##Architecture
See the UML folder in Documentation 

##Known Bugs

In News view pictures may appear temporarily in the wrong article while the application is loading pictures from Internet.


##Not implemented functionality
- Switching between views resolves in when switching back to a view that previously downloaded information from the server reloads data from server without need to do so.
- No ability to click on links in news view
- Add events and pubs to your own calendar
- Ta ability to se buses to and from Lindholmen with Västtrafik






