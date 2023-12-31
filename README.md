# Passenger Service System
A passenger service system (PSS) is a comprehensive software platform used by airlines to manage various aspects of passenger-related activities. It serves as the central hub for reservations, ticketing, check-in, boarding, and other passenger-related services. PSSs enable airlines to efficiently handle passenger data, streamline operations, and provide a seamless travel experience for customers from booking to post-flight services. These systems are critical for managing airline operations and enhancing customer satisfaction.


## Table of Contents
- [Background](#background)
- [Microservices](#microservices)
- [Features](#features)
- [Procedure of 2FA](#procedure-of-2fa)
- [Technology Stack](#technology-stack)
- [Getting Started](#getting-started)
- [Project Setup](#project-setup)
- [Instructions for Pushing Changes](#instruction-for-pushing-changes)
- [Using Swagger UI and Redocly](#using-swagger-ui-and-redocly)
- [Two Factor Authentication Flow](#two-factor-authentication-flow)


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
- Two-factor authentication facility for specific users.
- Token blacklisting to prevent malicious log in.

## Procedure of 2FA 
The two-factor authentication has been implemented using this logic:
- Each user of the system will have a flag attribute which will indicate whether the 2FA is enabled for that feature.
- If a user has not enabled 2FA, he/she can simply log in to the application using his/her credentials; the application will generate access token and refresh token for the user.
- If a user has enabled 2FA, the system will generate an OTP of 6 digits of an expiration time of 5 minutes. This OTP will be sent to the registered email-address of the user.
- The concerned-user will have to enter the OTP to verify his/her identity. The user will be granted an access token and a refresh token given that the user enters a valid OTP.



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

To run redis, use this command:
`docker run --name my-redis-container -p 7001:6379 -d redis` (add `sudo` before the command if you are on Ubuntu.)

## Instruction for pushing changes
To make any changes or to update your changes to the code base, please follow the given instructions(The first three should be followed each time you are assigned a new task and need to edit the codebase):
1. Don't directly pull from master to your working local branch. First, you will have to switch to master branch. Then you can directly pull from master.
2. If you have made any changes to your working local branch, use `git stash` to stash the changes before switching to master.
3. Go to the master branch using `git checkout master` or `git switch master` on the terminal
4. Update the master branch using `git pull` on the terminal
5. create a new local branch using `git checkout -b <branchname>` or `git switch -c <branchname>`. Make sure that your branch name should be meaningful, and it should give some idea about your assigned task.
6. Commit and push your changes.
   * stage your changes using this: `git add [filename1] [filename2]` 
   * commit your changes using this: `git commit -m "commit message"`. Your commit message should indicate what you have included in this commit.
   * Then push your changes using: `git push -u origin <branchname>`. Your newly created branch will be automatically added into the remote repository.

7. Create a pull request
   * Go to the remote repository. Go to 'Pull Requests'.
   * After then, create a new pull request by hitting the button 'New Pull Request'.
   * Select your branch from the top-down list, and then click on 'Create pull request'.


When your pull request is approved, it will be merged.

## Instruction for adding Checkstyle in Intellij Idea
### Add checkstyle plugin in Intellij Idea
1. Go to settings -> plugins
2. Search Checkstyle-IDEA plugin and install it

### Show Checkstyle error in Idea:
1. Go to settings -> Tools -> checkstyle
2. Using '+' sing upload checkstyle.xml file of our PSS project
3. Checkmark the uploaded checkstyle file

Now Checkstyle error is seen in idea if you aren't fulfill checkstyle rules

### Solve Checkstyle error:

1. To reformat code use keyboard shortcut Ctrl+Alt+L
2. To reformat import statements use keyboard shortcut Ctrl+Alt+O



## Using Swagger UI and Redocly
Swagger UI is used for both documenting and testing the API endpoints whereas Redocly is used for documentation only but which is also visually pleasing.
Redocly requires the OpenAPI specification YAML file for generating documentation files.

### Swagger UI
Swagger UI can be used by visiting the following URL format for an individual service `http://localhost:8080/swagger-ui/index.html`

### Redocly
Using Redocly is a little lengthier process. The steps include:
1. Install Redocly CLI globally by running this command
   `npm i -g @redocly/cli@latest` (add sudo for linux)
2. Download the OpenAPI specification file of the project that is provided in JSON format by Swagger UI. This file can be found by clicking on the link below the project title of the project's Swagger UI page.
3. Convert this JSON file into YAML file using an online or offline converter.
4. Save the converted YAML file with yaml or yml extension.
5. Now to create the Redocly documentation file go to the same directory of the saved YAML file and run the following command `redocly build-docs {openapi-file-name}.yml`
6. This command will create a new HTML file in the same directory which is the Redocly documentation file.
7. Open this HTML documentation file in a browser.
8. If new endpoints are added or old ones are updated to the project, then follow the previous steps again to get an updated Redocly documentation.

### Two Factor Authentication Flow
The following diagram shows the two factor authentication flow of PSS:

![two-fa-flow-diagram](https://github.com/mdgiasuddin/pss-backend-application/blob/master/two-fa-flow.png)
