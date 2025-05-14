# Truffula Notes 
As part of Wave 0, please fill out notes for each of the below files!!!!!!!. They are in the order I recommend you go through them. A few bullet points for each file is enough. You don't need to have a perfect understanding of everything, but you should work to gain an idea of how the project is structured and what you'll need to implement. Note that there are programming techniques used here that we have not covered in class! You will need to do some light research around things like enums and and `java.io.File`!!!

PLEASE MAKE FREQUENT COMMITS AS YOU FILL OUT THIS FILE.

## App.java
This is supposed to implement different functions for the user to either show hidden files or show colored files. 
to implement these functions we need to use the path that will be used, then determining: to use color, not use color,
or show hidden files. We'll be using the TruffulaOptions object to accomplish this, create a new TruffulaPrinter then use the 
printTree method afterwards. 

## ConsoleColor.java
This is going to add color to the Enum(which are constant values ) using ANSI code. 

## ColorPrinter.java / ColorPrinterTest.java
The purpose for colorPrinter.java is to create a new object and passing a printStream object. After getting the argument, you can use the different methods to obtain the color, set the color, and printing the message.
The colorPrintTest is going to change the color of the output to the color we set it using the ANSI and after printing it its going to change back to the default( using the rest)  
## TruffulaOptions.java / TruffulaOptionsTest.java
The responsibility of TruffulaOptions.java is to contruct an object with the file from the user, whether the file will be used, 
and whether color will be used. It is also responsible for outputting a toString to show the user which options are picked. 
TruffulaOptionsTest will be responsible for making sure the files, hidden files, and color will be outputted properly. 
## TruffulaPrinter.java / TruffulaPrinterTest.java
The TruffulaPrint class prints folder in the console with the color we set it( using the TruffulaOptions).
The TruffulaPrinterTest make sure the folder structure is printed correctly like(correct color, correct format....)

## AlphabeticalFileSorter.java
It's using lambdas (anonymous functions) to quickly arrange arrays of files alphabetically 