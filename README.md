# spring-mongo-nplus1

Run mongo, I used docker:

```bash
docker run -d --name mongotest -p 27017:27017 mongo:latest
```

Then run the populate test:

```
com.workfront.mongotest.MongoTest#populate
```

Then run the search test:

```
com.workfront.mongotest.MongoTest#search
```

Note that the mongo logs print out this a million times:

```
Sending command {find : BsonString{value='people'}} to database test on connection [connectionId{localValue:2, serverValue:32}] to server localhost:27017
Command execution completed
Sending command {find : BsonString{value='addresses'}} to database test on connection [connectionId{localValue:2, serverValue:32}] to server localhost:27017
Command execution completed
Sending command {find : BsonString{value='jobs'}} to database test on connection [connectionId{localValue:2, serverValue:32}] to server localhost:27017
Command execution completed
```

This shows that the driver is sending one query for each nested object! How to fix?
