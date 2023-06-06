# Passenger Service System
This project is intended to be used the backend for Passenger Service System for Airlines Reservations System. 

It consists of the common source code which will be used across the three modules:
### Inventory Module
This module primarily consists of the processes of aircraft management, listing available airports, flight management, and flight scheduling.
### Pricing Module
This module primarily consists of the processes of fare management, classes management, and tax and penalty generation.
### Sales Module
This module primarily consists of the processes of PNR generation, flight booking, customer search, agency management, and cash box management.
## Table of Contents
- [BackGround](#background)
- [Features](#features)
- [Technology Stack](#technology-stack)
- [Getting Started](#getting-started)
- [Project Setup](#project-setup)
- [Instructions for Pushing Changes](#instruction-for-pushing-changes)


## Background
A Passenger Service System or PSS is a network of software applications that help airlines manage all the passenger-related operations from ticketing to boarding.

The PSS comprises three main modules.
* An airline or central reservation system contains information on schedules, fares, and reservations, manages booking requests, generates PNRs, and issues tickets.
* An airline inventory system controls the availability of seats and manages fare groups.
* A departure control system manages passenger-related activities in the airport, e.g., processing check-ins, controlling baggage, printing boarding passes, etc.

Additionally, a PSS can include such optional components as a revenue management solution, retailing platform, and an NDC offer and order management platform.

## Features
- Flight Scheduling & management
- Addition of aircraft types and aircrafts
- Pricing of ticket
- Fare management & tax and penalty generation
- PNR generation
- Quick Sale of ticket
- Ticket search
- Flight Search
- Customer management
- Passenger listing

## Technology Stack
This repository is built upon following technologies:
* Spring Boot 3
* Spring Data JPA as Data Access Layer
* Spring Security
* Postgresql as default database
* JWT for authentication & authorization
* Spring Data Redis for caching

## Getting Started
To get started with this project, you will need to have the following installed on your local machine:

* JDK 17+
* Maven 3+
* Docker
* Any Java IDE(IntelliJ Recommended)

## Project Setup

To build and run the project, follow these steps:

* Clone the repository: `git clone https://github.com/mdgiasuddin/pss-backend-application.git`
* Navigate to the project directory. The directory name is pss-backend-application.
* Install postgres and pgadmin by executing this command: `docker compose up -d`. (add sudo if you are on Ubuntu)
* Open your browser and go to this [link](http://localhost:5050). It will open the pgadmin web view on your browser.
* Add a new server under servers if no server exists.
* After adding a server, create a login group named admin. Give it super user privileges. Make sure the username and password both is 'admin'.
* Add database "pss_inventory" under "admin" login group.
* Open the project using any IDE. 
* First go to the settings of your IDE. Set the Maven home path to the path you have installed Maven in your system instead of using the maven-wrapper.
* Open the terminal in your IDE.
* Then build the project.
* Finally, run the project.

-> The application will be available at http://localhost:8080.

To run redis, use this command:
`docker run --name my-redis-container -p 7001:6379 -d redis` (add `sudo` before the command if you are on Ubuntu.)

## Instruction for pushing changes
To make any changes or to update your changes to the code base, please follow the given instructions(The first three should be followed each time you are assigned a new task and need to edit the codebase):
* Go to the master branch using `git checkout master` or `git switch master` on the terminal
* Update the master branch using `git pull origin master` on the terminal
* create a new local branch using `git checkout -b <branchname>` or `git switch -c <branchname>`. Make sure that your branch name should be meaningful and it should give some idea about your assigned task.
* Add the changes.
* stage your changes using this: `git add [filename1] [filename2]`
* commit your changes using this: `git commit -m "commit message"`. Your commit message should indicate what you have included in this commit.
* Then push your changes using: `git push`. Your newly created branch will be automatically added into the remote repository.
* Go to the remote repository. Go to 'Pull Requests'.
* After then, create a new pull request by hitting the button 'New Pull Request'.
* Select your branch from the top-down list, and then click on 'Create pull request'.

When your pull request is approved by everyone, it will be merged by the scrum master.





