#### **simple-ping-app**
###### A simple PING application

**What the app do:**<br>
The application receives some hosts and make the following call to each of them: 
- a TCP/IP Ping
- a ICMP PING
- a TRACE ROUTE<br>

and saves the last execution on a file based database and if is an error, call a report functionality.<br>

**RUNNING THE APPLICATION**<br>
The project is executed on the command line as a normal Java/Jar Application and receives some hosts as paramether and save the last execution on a file based database.<br>

_COMPILE_<br>
Before execute, we need Java/JDK(version11+) and Maven installed and configured.
<br>
With Maven, we will compile the project, entering with command line app into the project folder in the same path where pom.xml is located, then execute the command:
<code>mvn clean install</code> to compile.
If the compilation runs well, then navigate to the folder <code>target</code> and you will find a <code>.jar</code> file.<br>

_EXECUTION_<br>
In the same folder as the <code>.jar</code> file, to run the application you can execute the following command<br>
<code>
java -jar simple-ping-app.jar jasmin.com,oranum.com
</code>
<br>
where the text after ".jar", are the paramether with the hosts.<br>
If the paramethers are wrong or are not hosts, the application will reject them and log it out.

_ERRORS_<br>
If some error happens during the execution, timeout or other errors depending the type of execution,
a report is generated with the information of the last executions (saved on database),
and send to a REST API via HTTP POST. All errors are also logged. 


_VALIDATION_<br>
The validation of errors on the execution of a command take in count values from the ValidationMessageRules class.
To add more error message or invalid HTTP codes, please change the constants on this file.

_LOGS_<br>
The application have LOGS that can be configured on the <code>log4j2.xml</code>.
All the logs are logged in the console andd the warnings in a file in the folder LOGS.

_CONFIGS_<br>
The commands(for WINDOWS or LINUX OSs), properties(timeout/delay) for the PINGs and TRACERT, and also the API that will receive the REPORT should be configured on the application.properties file,
following the current pattern.<br>
The name of the database file can also be configured in the same file (the database is generated on the bootstrap of the application).  
*TIP: The times set on the properties file should be set in SECONDS

_UNIT TESTS, CODE COVERAGE AND COMPLEXITY_<br>
I use Jacoco, PMD and Sonar to validate and report unit tests and to analyse the code.
I also use some code complexity plugins when i write the code, so the complxity is very low.  








