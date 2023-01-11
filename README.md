# BalanceCalculator
Parses input csv data file containing transactions for different customers for different months. 
Computes minimum, maximum and ending balances per month per customer.

Input File: Can be any filename, let's say:             input.csv
Output File: Fixed filename generated in root folder:   Balances.csv

To Run Code:

I. Compile source code files:

$ javac src/AppMain.java
$ javac src/Blackboard.java
$ javac src/DateSorter.java
$ javac src/Evaluator.java
$ javac src/ParseBalance.java
$ javac src/ParseTransaction.java
$ javac src/Transaction.java

II. Execute source code with input file passed as argument parameter:
$ java src/AppMain.java data/input.csv
