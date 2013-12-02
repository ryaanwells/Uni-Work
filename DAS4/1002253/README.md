Installation:
	Navigate to the 1002253 directory created from the tarball.
	Run (note this is all one line):
	    $/1002253> javac -d bin src/client/* src/core/* src/main/* src/server/*
	The compiled source is now inside the bin directory.

Running the server:
	Navigate to the 1002253/bin directory.
	Run (note this is all one line):
	    $/1002253/bin> java -Djava.security.manager -Djava.security.policy=policy.txt main.Server [port]
	This will start the rmiregistry if it is not yet started - you do not explicitly have to start it. However, if it is already running when the program starts then that causes no problem.
	The port argument is optional and defaults to 1099.
	Follow the instructions on screen to setup and run the server
	       Note: if the JVM running the server cannot determine it’s IP address you may need to add the following to the flags (where XXX.XXX.XXX.XXX is the IP of the host machine):
	       	     -Djava.rmi.server.hostname=XXX.XXX.XXX.XXX

Running the client:
	Navigate to the 1002253/bin directory.
	Run (note this is all one line):
	    $/1002253/bin> java -Djava.security.manager -Djava.security.policy=policy.txt main.Client [hostname] [port]
	Both the hostname and port are optional and default to “localhost” and 1099 respectively. 
	Follow the instructions on screen to use the system.
Running the ThroughputTest:
	Navigate to the 1002253/bin directory.
	Run (note this is all one line)
	    $/1002253/bin> java -Djava.security.manager -Djava.security.policy=policy.txt main.Client [clients] [hostname] [port]
	All of clients, hostname and port are optional and default to 50, localhost and 1099 respectively.
	This requires no input from the user and will display the Average Round Trip TIme and the Standard Deviation from the Average Round Trip TIme when completed.
