# BC-ACM-ICPC-Practice
This repository contains previous ACM ICPC problems as well as some algorithms that may be useful for an ACM competition.

## Algorithm Structure
An example for an algorithm structure is as follows:
```
ACM Algorithms/
└── src
    └── Greatest_Common_Divisor
        ├── GCD.java
        └── ReadMe.txt
```
The name of the algorithm should be the name of the directory within `ACM Algorithms/`. 
In this case, `Greatest_Common_Divisor`. Note that `Greatest_Common_Divisor` is the package name for a Java source file.

The source file for the algorithm should have a meaningful name, in this case `GCD.java`.

The `ReadMe.txt` file should follow this general structure:
<ol><!-- couldn't get the MarkDown ordered list to work properly -->
<li> Algorithm Name </li>
<li> Page number and title of the text for the algorithm </li>
<li> Identifying the problem for the algorithm </li>
<li> Restrictions and edge cases for the algorithm </li>
<li> A summary of what the algorithm does </li>
<li> The pseudocode for the algorithm </li>
<li> A working example for the problem, demonstrating the algorithm's function </li>
</ol>

For a detailed example of a `ReadMe.txt` file, please see [the ReadMe for the Greatest Common Divisor](ACM Algorithms/src/Greatest_Common_Divisor/).

## Common Classes

Common classes, such as those used for graphing problems, should be located in `ACM Algorithms/src/common`

## Adding More Algorithms

To add an algorithm, first clone this repository to make a local branch.

Once you have modified your local branch and added an algorithm with this structure, please submit a pull request to add your algorithm to the `master` branch.
