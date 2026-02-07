# FemCoders-Proyecto_6-Biblioteca

## Table of Contents
1. [General Info](#general-info)
2. [Technologies](#technologies)
3. [Installation](#installation)
4. [Collaboration](#collaboration)
5. [FAQs](#faqs)

### General Info
***
This project is a Library Inventory Management System built using the **MVC** 
(Model-View-Controller) architecture. It is designed to manage books, authors, and genres, ensuring data persistence through a relational database.

**Project Status:** In Progress (Book listing functionality successfully connected to DB).

### Screenshot


## Technologies
***
A list of technologies used within the project:
* [Java JDK](https://www.oracle.com/java/): Version 21
* [Apache Maven](https://maven.apache.org/): Version 3.9+
* [PostgreSQL](https://www.postgresql.org/): Version 15+
* [Lombok](https://projectlombok.org/): Version 1.18.30
* [JDBC](https://docs.oracle.com/javase/8/docs/technotes/guides/jdbc/): Java Database Connectivity

## Installation
***
Follow these steps to set up the local environment and run the application:

https://github.com/annahico/FemCoders-Proyecto_6-Biblioteca.git

cd library-inventory $ # Compile and install dependencies $ mvn clean install
 $ # Run the application $ mvn exec:java -Dexec.mainClass="com.library.model.App"
 
 Side information: To use the application in a special environment use ```DBManager``` to configure your database credentials.

## Collaboration
***
Give instructions on how to collaborate with your project.
> "I have always imagined that Paradise will be a kind of library."
> — Jorge Luis Borges. 
> To collaborate, please follow the MVC pattern and delegate business logic to the Controller to keep the View clean.

## FAQs
***
A list of frequently asked questions:

1. **How do I view the list of books?**
Select _Option 1_ in the main menu. The Controller will request the data from the Repository and send it to the View for display.

2. **What architecture does the project use?**
To answer this question we use an unordered list:
* **Model**: POJO classes with Lombok (Book, Author, Genre).
* **View**: Console-based user interface.
* **Controller**: Orchestrator between the view and the repository.

3. **How is the database connection managed?**
The connection is managed through the *DBManager* class and handled within the *BookRepositoryImpl* using try-with-resources.

4. **Book Table Structure**

|:--------------|:-------------:|--------------:|
