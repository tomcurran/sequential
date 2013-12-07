# Sequential

Reverse engineer sequence diagrams from code

## Running

### In Eclipse
Run com.smeedaviation.sequential.main.Main and provide it a JAR to analysis as its first argument. Any subsequent arguments will be piped into the JAR that is being analysised. Before running the program create a folder called "temp" in the Sequential project and add it to the build path.

### Command Line
>java -cp "temp;sequential.jar" com.smeedaviation.sequential.main.Main [jar_to_analysis.jar]

Before running the program create a folder called "temp" in the same directory as the sequential JAR
