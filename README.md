# Backend For Passenger Service System
This project is intended to be used the backend for Passenger Service System for Airlines Reservations System. 

It consists of the common source code which will be used across the three modules:
### Inventory Module
This module primarily consists of the processes of aircraft management, listing available airports, flight management, and flight scheduling.
### Pricing Module
This module primarily consists of the processes of fare management, classes management, and tax and penalty generation.
## Sales Module
This module primarily consists of the processes of PNR generation, flight booking, customer search, agency management, and cash box management.
## Table of Contents
- [BackGround](#background)
- [Technology Stack](#technology-stack)
- [Getting Started](#getting-started)
- [Project Setup](#project-setup)
- [Features](#features)

## Background
This section will consist some introductory idea about a PSS system. 

## Technology Stack
This repository is built upon following technologies:
* Spring Boot 3
* Spring Data JPA as Data Access Layer
* Spring Security
* Lombok
* Postgresql as default database
* JWT for authentication & authorization
* Spring Validation 
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






