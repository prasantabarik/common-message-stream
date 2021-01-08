# common-message-stream


## Install nClientJar with the below mentioned command
mvn install:install-file -Dfile=common/lib/nClient.jar -DgroupId=com.pcbsys.nirvana.client -DartifactId=nClientTest -Dversion=1.0 -Dpackaging=jar -DgeneratePom=true


## Publish the Message
curl -X POST -d "{'test2': 'test2'}" -H "Content-type: text/json"  http://localhost:8099/api/postEvents

## Subscribe the Message
curl -X GET -H "Content-type: text/json"  http://localhost:8099/api/getEvents
