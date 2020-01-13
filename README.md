# package-challenge
The goal of this challenge is to determine which items to put into a package so that the total weight is less than or equal to the package limit and the total cost is as large as possible. 
The chain of responsibility design pattern is used to handle packaging requests by three handlers such as reader handler, parser handler and processor handler. 
Reader handler: to read an input file
Parser handler: to parse the input string to extract available items and package's weigh limit
Processor handler: process input item list to find the best selection of items to fill a package by defined criteria (weight and cost). It uses the dynamic programming algorithm to find optimal selections.
