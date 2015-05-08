The server will look for configurations in this order:
    1. CommandLine
    2. Properties file next to the jar(serverConfig.properties)
    3. Defaults

In case one or more options are not specified the server will use its default.
The server will use either CommandLine OR the properties file

Commandline Arguments:
    -p 8080 : Sets the server port to the specified number. Default is 8080.
    -r C:/users/home/rootDir : Sets the root directory for the server to look for files. Default is USERHOME/SimpleWebserver

Properties File
    port = 8080 : Sets the server port to the specified number. Default is 8080.
    rootDirectory = C:/users/home/rootDir : Sets the root directory for the server to look for files. Default is USERHOME/SimpleWebserver


Logfiles will be created in the current working directory!