# hotrod-scripting-test
This project shows how to use Scripting Task coded as Javascript to execute operations on a Cache. 
Scripting Tasks are executed from an Hot Rod Java client.

* Demo1 shows how to insert a Cache Entry from a Scripting Task
* Demo2 shows how to use JTA API from within a Script
* Demo3 shows how to trigger Remote events from a Scripting Task

#Requirements
Infinispan Server 8.X.X

#Steps to run the demo
* Start Infinispan
$ ./standalone.sh -c clustered.xml
* Build the project
$ mvn clean install
* Execute the Demo
$ mvn exec:java

Note, the exec:java will execute the Java class contained in the pom.xml
 <configuration>
          <mainClass>com.redhat.hotrod.storedtask.Demo1</mainClass>
 </configuration>
