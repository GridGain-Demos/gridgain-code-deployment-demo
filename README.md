# GridGain code deployment demo

This demo shows how to deploy compute tasks to a gridgain cluster 
and run them right from the client`s application.

1. Download [GridGain Control Center](https://www.gridgain.com/tryfree#controlcenter)
2. Download node with Control Center Agent (???????????)
3. Run applications (???????????)
4. [UI] Attach cluster 
5. Run `mvn clean install` from the root folder
6. [UI] Deploy `/compute-task-executor/target/compute-task-executor-1.0.jar`
7. Go to `client-node-starter` and run it. The application executes a job on cluster and you should see 
its output on the client node:
    ```text
    >> Cities:
    [Barcelona, Bilbao, Córdoba, Gijón, Madrid, Mallorca, Murcia, Málaga, Sevilla, Valencia, Valladolid, Vigo, Zaragoza]
    >> Compute task is executed, check for output on the server nodes.
    ``` 
   and the following output on the server node:
    ```text
    >> Executing the compute task
    >> Found 13 cities with code SPA
    ```
8. Go to the compute task class [CityFilterTask.java](/compute-task-executor/src/main/java/org.gridgain/demo/CityFilterTask.java)
and invert comments on 29-30 rows:
    ```java
    //CountryCode countryCode = CountryCode.SPA;
    CountryCode countryCode = CountryCode.FRA;
    ```
9. Perform point 5, 6, 7 again and you should get the following output:
    ```text
    >> Cities:
    [Angers, Bordeaux, Brest, Dijon, Grenoble, Lille, Lyon, Marseille, Montpellier, Nantes, Nice, Paris, Reims, Rennes, Strasbourg, Toulon, Toulouse]
    >> Compute task is executed, check for output on the server nodes.
    ```
    and on the server node side:
    ```text
    >> Executing the compute task
    >> Found 17 cities with code FRA
    ```
