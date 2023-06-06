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

Additionally, a PSS can include such optional components as a revenue management solution, retailing platform, and an NDC offer and order management platform.

## Microservices

Our project is composed of 4 microservices. They are listed below:
### Admin, Inventory & Pricing
The major operations of this microservice are:
* Managing authentication & authorization based on role
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
The primary task of this microservice is to generate static and dynamic report in real time.

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
* Spring Boot 3
* Spring Data JPA as Data Access Layer
* Spring Security
* Postgresql 
* JWT for authentication & authorization
* Spring Data Redis

## Getting Started
To get started with this project, you will need to have the following installed on your local machine:

* JDK 17+
* Maven 3+
* Docker
* IDE (IntelliJ IDEA Recommended)

## Project Setup

To build and run the project, follow these steps:

* Clone the repository: `git clone https://github.com/mdgiasuddin/pss-backend-application.git`
* Navigate to the project directory. The directory name is pss-backend-application.
* Install postgres and pgadmin by executing this command: `docker compose up -d`. (add sudo if you are on Ubuntu)
* Install and run redis using this command: `docker run --name my-redis-container -p 7001:6379 -d redis`.(add `sudo` before the command if you are on Ubuntu.)
* Open your browser and go to this [link](http://localhost:5050). It will open the pgadmin web view on your browser.
* Add a new server under servers if no server exists.
* After adding a server, create a login group named admin. Give it superuser privileges. Make sure the username and password both is 'admin'.
* Add database "pss_inventory" under "admin" login group.
* Open the project using any IDE. 
* First go to the settings of your IDE. Set the Maven home path to the path you have installed Maven in your system instead of using the maven-wrapper.
* Open the terminal in your IDE.
* Then build the project.
* Finally, run the project.

-> The application will be available at http://localhost:8080.


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


When your pull request is approved by everyone, it will be merged by the scrum master.





