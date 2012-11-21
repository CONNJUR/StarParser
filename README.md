StarParser
==========

a parser for the NMR-Star format using parser combinators


### Dependencies ###

A working installation of Java1.6 or higher.


### Usage ###

Copy the StarParser.jar file to your computer.  
From the command line, run the jar:

    $ java -jar StarParser.jar [inputfilename] [outputfilename]

The NMR-Star file `inputfilename` will be read in, parsed, and
output as JSON, and then written to `outputfilename`.

To access the built-in help:

    $ jara -jar StarParser.jar -h