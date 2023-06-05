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

### Install JDK 17
#### On Ubuntu
To install JDK 17 on Ubuntu, use this command:
```sh
sudo apt install openjdk-17-jdk -y
```
After this command gets executed, check the java version of your system.
```sh
java --version
```
It should return output similar to this.
```sh
openjdk 17.0.7 2023-04-18
OpenJDK Runtime Environment (build 17.0.7+7-Ubuntu-0ubuntu122.04.2)
OpenJDK 64-Bit Server VM (build 17.0.7+7-Ubuntu-0ubuntu122.04.2, mixed mode, sharing)
```
Java installation is successful. Now we need to add Java HOME to our path. To do that, execute the following code in terminal.
```sh
echo "export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64" >> ~/.bashrc
```
Logout and log back in. When you enter `echo $JAVA_HOME` on terminal, it should return something like this:
`/usr/lib/jvm/java-17-openjdk-amd64`
#### On Windows
To install JDK 17 on Windows 10/11, follow this [link.](https://javacodepoint.com/download-jdk-17-and-install-on-windows-11-64-bit/)

### Install Maven 3+
#### On Ubuntu
Open the terminal and download the latest stable release of Maven3 using this command(Note: at the time of writing: the latest stable version of maven 3 was: 3.9.2):
```sh
wget https://www-us.apache.org/dist/maven/maven-3/3.9.2/binaries/apache-maven-3.9.2-bin.tar.gz -P
```
Once the download has been finished, extract the contents of the archived file in the /opt directory:
```sh
sudo tar xf apache-maven-3.9.2-bin.tar.gz -C /opt
```
Then, create a symbolic link for maven.
```sh
sudo ln -s /opt/apache-maven-3.9.2/ /opt/maven
```
Now, we need to set up the environment variables. Enter the following line in a terminal:
```sh
sudo nano /etc/profile.d/maven.sh
```
Paste the following contents in the editor and then save it. 
```sh
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64"
export M2_HOME=/opt/maven
export MAVEN_HOME=/opt/maven
export PATH=${M2_HOME}/bin:${PATH}
```
Make the script executable:
```sh
sudo chmod +x /etc/profile.d/maven.sh
```
Finally, load the environment variables using the source command:
```sh
source /etc/profile.d/maven.sh
```

Verify the installation using this command:
```
mvn -v
```
The output will be something like this:
```sh
Apache Maven 3.9.2 (c9616018c7a021c1c39be70fb2843d6f5f9b8a1c)
Maven home: /opt/maven
Java version: 17.0.7, vendor: Private Build, runtime: /usr/lib/jvm/java-17-openjdk-amd64
Default locale: en_US, platform encoding: UTF-8
OS name: "linux", version: "5.19.0-43-generic", arch: "amd64", family: "unix"
```


#### On Windows
To install Maven 3+ on Windows 10/11, please follow this [link.](https://phoenixnap.com/kb/install-maven-windows)

### Install Docker

To install docker on Ubuntu, please follow this [official documentation.](https://docs.docker.com/engine/install/ubuntu/)

To install docker on Windows 10/11, it's better to use Docker Desktop. To install that, please follow this [official documentation.](https://docs.docker.com/desktop/install/windows-install/)


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
* Build the project using this command: `mvn clean install`.
* Then run the project.

-> The application will be available at http://localhost:8080.


## Features
* User registration and login with JWT authentication
* Password encryption using BCrypt
* Role-based authorization with Spring Security
* Customized access denied handling
* Logout mechanism
* Refresh token







