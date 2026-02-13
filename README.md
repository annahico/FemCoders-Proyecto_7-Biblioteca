# FemCoders-Project_7-Library

<p align="center">
  <img src="/docs/IMG/main_menu.png" alt="Library Inventory Menu" width="550">
</p>

## 📌 Table of Contents
1. [General Info](#-general-info)
2. [Technologies](#-technologies)
3. [Architecture](#-architecture)
4. [Functional Requirements](#-functional-requirements)
5. [Installation](#-installation)
6. [Database Schema](#-database-schema)
7. [Testing](#-testing)
8. [Collaboration](#-collaboration)

---

## 📖 General Info
This project is a **Library Inventory Management System** built using the **MVC** (Model-View-Controller) architecture. It is designed to help local libraries modernize their management by allowing administrators to track books, authors, and genres through a terminal-based interface with persistent PostgreSQL storage.

**Project Status:** 🚧 In Progress (Core DB connection and Book Listing active).

---

## 🛠 Technologies
* **Language:** Java JDK 21
* **Database:** PostgreSQL 15+
* **Build Tool:** Apache Maven 3.9+
* **Libraries:** * [Lombok](https://projectlombok.org/) (To reduce boilerplate code)
    * [JDBC](https://docs.oracle.com/javase/8/docs/technotes/guides/jdbc/) (Database connectivity)
    * [JUnit & Mockito](https://site.mockito.org/) (Unit testing)

---

## 🏛 Architecture
The project follows a clean separation of concerns:

* **Model:** POJO classes (Book, Author, Genre) utilizing Lombok annotations.
* **View:** A user-friendly Console/Terminal interface.
* **Controller:** The "brain" that orchestrates data flow between the View and the Repository.
* **Repository Pattern:** Used within the Model layer to abstract SQL queries and data persistence logic.

---

## ✅ Functional Requirements
The system allows users to:
* [x] **View** a complete list of books.
* [x] **Add** new books to the database.
* [x] **Edit** existing book details.
* [x] **Delete** books from the inventory.
* [x] **Search** by Title, Author, or Genre.

> **Note:** When listing books, all fields are shown except the description (max 200 chars). Full details are shown only during specific searches.

---

## ⚙️ Installation

1. **Clone the repository:**
```bash
   git clone https://github.com/annahico/FemCoders-Proyecto_7-Biblioteca.git
   
   cd FemCoders-Proyecto_7-Biblioteca
```
   

2. **Database Setup:** Ensure you have a PostgreSQL instance running. Create your database and configure your credentials (URL, user, and password) inside the DBManager class to establish the connection.

3. **Build the project:**
```bash
mvn clean install
```

4. **Run the application:**
```bash
 mvn exec:java -Dexec.mainClass="com.library.App"
 ```

---

## 🗄 Database Schema
The database is designed with a relational structure, ensuring data integrity through normalization and foreign key constraints.

### 📊 Entity-Relationship Structure
* **Books**: Stores core information with a unique ISBN and a 200-character description limit.
* **Authors**: Stores the full names of the writers.
* **Genres**: Contains unique literary categories.
* **Junction Tables**: `books_authors` and `books_genres` manage Many-to-Many relationships with `ON DELETE CASCADE` for automated data cleanup.

<details>
<summary><b>Click to view SQL Table Definitions</b></summary>

```sql
-- TABLE 1: authors
CREATE TABLE authors (
    id SERIAL PRIMARY KEY,
    full_name VARCHAR(300) NOT NULL
);

-- TABLE 2: books
CREATE TABLE books (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    isbn VARCHAR(17) UNIQUE,
    description VARCHAR(200),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- TABLE 3: genres
CREATE TABLE genres (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) UNIQUE NOT NULL
);

-- TABLE 4: books_authors
CREATE TABLE books_authors (
    book_id INTEGER REFERENCES books(id) ON DELETE CASCADE,
    author_id INTEGER REFERENCES authors(id) ON DELETE CASCADE,
    PRIMARY KEY (book_id, author_id)
);

-- TABLE 5: books_genres
CREATE TABLE books_genres (
    book_id INTEGER REFERENCES books(id) ON DELETE CASCADE,
    genre_id INTEGER REFERENCES genres(id) ON DELETE CASCADE,
    PRIMARY KEY (book_id, genre_id)
);
```
</details>

---

## 🧪 Testing
To ensure the reliability and stability of the library system, we have implemented a comprehensive testing suite focused on unit testing and component isolation.

* **Testing Framework**: We use **JUnit 5** to define and execute our test cases.
* **Mocking with Mockito**: We utilize **Mockito** to create mock objects for our `Repository` and `Service` layers. This allows us to test the `Controller` logic in total isolation, simulating database responses without requiring a live PostgreSQL connection.
* **Behavior Verification**: Our tests verify that the Controller correctly handles user input and orchestrates data flow between the view and the database.

### How to run the tests
You can execute the entire test suite directly from your terminal using Maven:

```bash
mvn test
```

If `mvn test` is not working in your terminal, ensure your `pom.xml` includes the **Maven Surefire Plugin** and the following dependencies:

1. **JUnit 5 (Jupiter)**: The core testing engine.
2. **Mockito Inline**: Required for mocking final classes and methods.
3. **Maven Surefire**: Necessary for the terminal to recognize tests during the build lifecycle.

### Running Tests in VSC
If the terminal command fails, you can use the **Testing** icon (the beaker) on the left sidebar of Visual Studio Code. This will allow you to run `BookRepositoryImplTest` or `GenreRepositoryImplTest` individually by clicking the "Play" button next to the class name.
---

## 🤝 Collaboration
> "I have always imagined that Paradise will be a kind of library." — Jorge Luis Borges.

This project was developed by a team of 4 developers. To maintain the integrity of the code, we follow these guidelines:

* **MVC & Design Patterns**: Ensure business logic remains in the `Controller` and all data access is handled via the `Repository` pattern.
* **Code Standards**: Use **Lombok** to keep models clean and follow standard Java naming conventions.
* **Workflow**: Each new feature must be developed in a separate branch and requires a Peer Review before merging into the main branch.

### 👩‍💻 The Development Team
* **Leonela Rivas** - Project Owner - [GitHub Profile](https://github.com/Leonela88)
* **Geraldine Saco** - Scrum Master - [GitHub Profile](https://github.com/GeraldineSaco/HTML)
* **Ingrid López** - Developer - [GitHub Profile](https://github.com/Nuclea88)
* **Anna Costa** - Developer - [GitHub Profile](https://github.com/annahico)

---