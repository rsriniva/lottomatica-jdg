Uso:

Compila, Esegue il Deploy e avvia il Server di Hot Rod:

mvn clean deploy jboss-as:deploy

Una volta attivato il listener (jdg-mixed-client) esegui una put con:

http://localhost:8080/jdg-mixed-server/PutServlet


