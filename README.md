FCN-Project
===========

SMTP Implementation

Client should have following classes:
Client.java
ClientReceiver.java
ClientSender.java
ClientUtil.java
Email.java

Server should have the following classes:
SMTPServer.java
SMTPReceiver.java
IMAPCommunicator.java
DBCommunicator.java
Email.java

Also, the Server should have the MySQL Java Connector: mysql-connector-java-5.1.31-bin.jar

You might want to change the constants in ClientUtil.java and DBCommunicator.java to specify your own domain

Use the following steps to deploy the server
1. Compile all the Java files using: javac *.java
2. On the server side, start the server using: java SMTPServer
3. On the client side, start the client using: java Client
