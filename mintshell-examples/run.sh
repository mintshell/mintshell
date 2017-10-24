cd ..
mvn -DskipTests clean install
cd mintshell-examples
mvn exec:exec
