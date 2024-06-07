#!/bin/bash

# Start MongoDB directly
mongod --fork --logpath /var/log/mongod.log --dbpath /data/db

# Wait for MongoDB to start
sleep 10

# Start the Spring Boot application
java -jar build/libs/protopnet-0.0.1-SNAPSHOT.jar
