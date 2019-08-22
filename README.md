# SOFTENG306Project1 - Team 6
Using AI and parallel processing power to solve difficult scheduling problems
  
saddboys = Chuyang Chen (cche381)  
raghavgarg32 = Raghav Garg (rgar969)  
magnusrand = Magnus Rand (cran263)  
daltonlim = Dalton Lim (dlim779)  
bugn877 = Brian Nguyen (bugn877)


## Running the code
To run the application, type the following command:

NOTE 1: The .jar file has to be in the same repository as your current file path!
NOTE 2: The .dot file has to be a valid DAG graph.
```
java -jar scheduler.jar <INPUT.dot> <NUMBER OF PROCESSORS> <OPTIONAL ARGS>
```
or, to start a continous version of the program, just type:
```
java -jar scheduler.jar
```

An example:
```
java -jar scheduler.jar Nodes_10_Random.dot 2 -v
```

The optional arguments are as follows:


|  Argument | Description  |
|---|---|
|  -v | Visualises the output  |
|  -p|  Determines the number of cores used for execution |
|  -o|  Name the output file |

