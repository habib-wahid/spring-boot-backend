# Passenger Service System
A passenger service system (PSS) is a comprehensive software platform used by airlines to manage various aspects of passenger-related activities. It serves as the central hub for reservations, ticketing, check-in, boarding, and other passenger-related services. PSSs enable airlines to efficiently handle passenger data, streamline operations, and provide a seamless travel experience for customers from booking to post-flight services. These systems are critical for managing airline operations and enhancing customer satisfaction.


## Table of Contents
- [Background](#background)
- [Microservices](#microservices)
- [Features](#features)
- [Technology Stack](#technology-stack)
- [Getting Started](#getting-started)
- [Project Setup](#project-setup)
- [Instructions for Pushing Changes](#instruction-for-pushing-changes)


## Background

A PSS primarily comprises four main services:
* <b>Inventory:</b> This module contains set up of cabin, aircraft, airport & flight scheduling, PRBDs, offer template and seat map management.
* <b>Pricing:</b> This module facilitates fare basis set up, fare family management, ancillaries, tax and penalty management.
* <b>Sales:</b> This module comprises Customer management, Cash-box management, Agent and Corporate customer management, single & group ticketing.
* <b>DCS:</b> This module offers departure control system and messaging for communication.


## Microservices

Our project is composed of 4 microservices. They are listed below:
### Admin, Inventory & Pricing
The major operations of this microservice are:
* Managing authentication & authorization
* Cabin, Aircraft & Airport Management
* Flight Scheduling management
* PRBDs & Offer template
* Fair management
* Tax & Penalty management
* Ancillaries

### Sales
The main responsibilities of this microservice are:
* Quick Sales
* PNR Generation & management
* Searching of tickets & coupons
* Customer management
* External Agency & Corporate Customer management

### Reporting
The primary task of this microservice is to generate reports.

### DCS
The main operations of this microservice are:
* Departure Control management
* Departure Messaging management

## Features
- Flight Schedule management
- Fare management & tax and penalty generation
- PNR generation
- Quick Sale of ticket
- Ticket search
- Flight Search
- Customer management
- Passenger management
- Access control based on user group
- Token blacklisting to prevent malicious log in.

## Technology Stack
This repository is built upon following technologies:
* Spring Boot
* Postgresql
* Redis

## Getting Started
To get started with this project, you will need to have the following installed on your local machine:

* JDK 17+
* Maven 3+
* Docker
* IDE (IntelliJ IDEA Recommended)

## Project Setup

To build and run the project, follow these steps:

### Repository Cloning
Clone the repository: `git clone https://github.com/mdgiasuddin/pss-backend-application.git`.

### Set up Maven Settings
* You need to define the maven settings for the project in the settings.xml file of the .m2 directory. 
* You need to add the following to mirrors section of settings.xml file
```
<mirror>
      <mirrorOf>*</mirrorOf>
      <name>Mirror</name>
      <url><server-IP-Address:server-port>>repository/mirror</url>
      <id>mirror</id>
</mirror>
```
Please note that the repository url will be that of remote server.
* Now, you need to add the following to servers section of settings.xml file
```
<server>
    <id>mirror</id>
    <username>server-username</username>
    <password>server-password</password>
</server>
```
Please note that, the username and password will be that of server's.

### Set up Project in the IDE
* Open the project using any IDE. 
* First go to the settings of your IDE. Set the Maven home path to the path you have installed Maven in your system instead of using the maven-wrapper.
* Open the terminal in your IDE.
* Then build the project.
* Finally, run the project.

The application will be available at http://localhost:8080/swagger-ui/index.html.


## Instruction for pushing changes
To make any changes or to update your changes to the code base, please follow the given instructions(The first three should be followed each time you are assigned a new task and need to edit the codebase):
1. Go to the master branch using `git checkout master` or `git switch master` on the terminal
2. Update the master branch using `git pull` on the terminal
3. create a new local branch using `git checkout -b <branchname>` or `git switch -c <branchname>`. Make sure that your branch name should be meaningful and it should give some idea about your assigned task.
4. Commit and push your changes.
   * stage your changes using this: `git add [filename1] [filename2]` 
   * commit your changes using this: `git commit -m "commit message"`. Your commit message should indicate what you have included in this commit.
   * Then push your changes using: `git push -u origin <branchname>`. Your newly created branch will be automatically added into the remote repository.

5. Create a pull request
   * Go to the remote repository. Go to 'Pull Requests'.
   * After then, create a new pull request by hitting the button 'New Pull Request'.
   * Select your branch from the top-down list, and then click on 'Create pull request'.


When your pull request is approved, it will be merged.





