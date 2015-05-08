The server will look for configurations in this order:
    1. CommandLine
    2. Properties File next to the jar
    3. Defaults

Commandline Arguments:
    -p 8080 : Sets the server port to the specified number. Default is 8080.
    -r C:/users/home/rootDir : Sets the root directory for the server to look for files. Default is USERHOME/SimpleWebserver

Properties File (Name = serverConfig.properties)
    port = 8080 : Sets the server port to the specified number. Default is 8080.
    rootDirectory = C:/users/home/rootDir : Sets the root directory for the server to look for files. Default is USERHOME/SimpleWebserver