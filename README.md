**What does NameCompressor do?**

NameCompressor is a tool to shorten overlong method names with abbreviations

**How to obtain NameCompressor?**

The replication package, including NameCompressor itself, can be downloaded from https://github.com/jiangyanjie/NameCompressorTool

**What does the replication package contain?**

There are two folders within the replication package:

**/shortenMethodName:** The source code of NameCompressor (Java)

**/EvaluationData:** Data to replicate the evaluation in the paper


**Step-by-step construction for running NameCompressor:**

1. Extract Method Name

    `cd shortenMethodName/src/extractMethodName`
    
    `javac runExtractMethodName.java`
    
    `java runExtractMethodName`

2. Shorten Method Name with Context-Sensitive Abbreviation

    `cd shortenMethodName/src/shortenBasedCtx`
    
    `javac shortenBySameFile.java`
    
    `java shortenBySameFile`

3. Shorten Method Name with Dictionary

    `cd shortenMethodName/src/constructDictionary`
    
    `javac matchIdentifier.java`
    
    `java matchIdentifier`

4. Shorten Method Name with Learning Method
   
    `cd shortenMethodName/src/logicRegression`
    
    `javac extractTestingFeatureSelection.java`
    
    `java extractTestingFeatureSelection`

5. Calculate the Performance 

   `cd shortenMethodName/src/logicRegression`
   
   `javac calculate.java`
   
   `java calculate`

   The performance (that is precision, recall, and F1) should be generated automatically,the following figure is the output of `java calculate`
   
   ![avatar](/output.png)
