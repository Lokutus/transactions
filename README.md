Transactions
============

A simple CLI application to create and read transactions in specified currencies.

Uses
----
- spring-boot 1.5.7
- spring-web to consume ČNB FX Rates 
- H2 database to persist transactions
- org.beryx.Text-IO to handle terminal inputs

How to run application
----------------------
- mvn clean package
- java -jar Transactions.jar
    - --help
    - --file={filePath_1} --file={filePath_2}
    - --refresh={seconds}
    - --conversion (this functionality is not fully implemented yet)


How to use application
----------------------
When application is running, user can write down a few specified commands.
- Create a new transaction: eg. `USD 300`, `CZK 21,09`, `YEN -59.5`, `eur 666`
    - Standard CurrencyCode (case insensitive) + space + positive or negative number + optional 1 or 2 decimal places
- *q* or *quit* to quit the application - closes all connections and threads
- *c* or *clean* to delete all entries from database

