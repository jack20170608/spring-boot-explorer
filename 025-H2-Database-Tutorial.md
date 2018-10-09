## H2 Database - Introduction

This introduction page roughly from [h2_database_introduction](https://www.tutorialspoint.com/h2_database/h2_database_introduction.htm)

H2 is an open-source lightweight Java database. It can be embedded in Java applications or run in the client-server mode. Mainly, H2 database can be configured to run as inmemory database, which means that data will not persist on the disk. Because of embedded database it is not used for production development, but mostly used for development and testing.

This database can be used in embedded mode or in server mode. Following are the main features of H2 database −

- Extremely fast, open source, JDBC API
- Available in embedded and server modes; in-memory databases
- Browser-based Console application
- Small footprint − Around 1.5MB jar file size

### Features of H2 Database

The main features of H2 Database are as follows −

- It is an extremely fast database engine.
- H2 is open source and written in Java.
- It supports standard SQL and JDBC API. It can use PostgreSQL ODBC driver too.
- It has embedded and Server mode.
- H2 supports clustering and multi-version concurrency.
- It has strong security features.

### Additional Features

Following are some additional features of H2 Database −
- H2 is a disk-based or in-memory databases and tables, read-only database support, temporary tables.
- H2 provides transaction support (read committed), 2-phase-commit multiple connections, table level locking.
- H2 is a cost-based optimizer, using a genetic algorithm for complex queries, zeroadministration.
- H2 contains scrollable and updatable result set support, large result set, external result sorting, functions can return a result set.
- H2 supports encrypted database (AES), SHA-256 password encryption, encryption functions, and SSL.

### Components in H2 Database

In order to use H2 Database, you need to have the following components −

- A web browser
- A H2 console server
This is a client/server application, so both server and client (a browser) are required to run it.

Please go to the sample project here [spring-boot-h2-simple](spring-boot-common/spring-boot-h2-usage/spring-boot-h2-simple)

##### Reference
- [H2 Home](http://www.h2database.com/html/main.html)
- [H2 Wiki Page](https://en.wikipedia.org/wiki/H2)