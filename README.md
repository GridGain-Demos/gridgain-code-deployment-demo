# GridGain code deployment demo

This demo shows how to deploy compute tasks to Gridgain Nebula cluster and run them right from the client`s application.

1. Go to [https://portal.gridgain.com/](https://portal.gridgain.com/).<br>
   ![Screen](./docs/img/114238.png)
1. Click "Create Nebula Cluster". A provisioning wizard will appear.<br>
   ![Screen](./docs/img/114356.png)
1. Create a Small Instance with 1 node:<br>
   ![Screen](./docs/img/114941.png)
1. Specify user credentials. These credentials will be used for clients that work with the cluster.
1. Accept terms and conditions, and start provisioning the cluster.
1. Wait for the cluster to be successfully provisioned.
1. Make sure the cluster is selected in the Cluster Management screen.
1. Switch to the Deployment menu and create a new deployment.
1. Click "Add artifact" to open a menu with types of artifacts, that you can choose:<br>
   ![Screen](./docs/img/141513.png)
1. Create the following artifacts:
- Direct Link artifact with url `https://code-deployment-demo.s3.amazonaws.com/compute-task-sample-1.0.jar`.
- Maven artifact with coordinate `com.google.guava:guava-collections:r03`, which is a transitive dependency of the compute task.
1. Switch to the Clusters screen, click the `⋮` on the right of the cluster and select "Connect" from the drop-down menu. Copy the connection url:
   ![Screen](./docs/img/173707.png)
1. Use the [ThinClientApp](./client-node-starter/src/main/java/org/gridgain/demo/ThinClientApp.java) class ( if you want to run a thin client ) or [ThickClientApp](./client-node-starter/src/main/java/org/gridgain/demo/ThickClientApp.java) class ( if you want to run a thick client)  and fill copied connection url to `ADDRESSES` field.
1. Run the application. It executes a job on the cluster, and you should see
   its output on the client node:
    ```text
    >> Execute org.gridgain.demo.CityFilterTask job
    >> Job result:
    [Barcelona, Bilbao, Córdoba, Gijón, Madrid, Mallorca, Murcia, Málaga, Sevilla, Valencia, Valladolid, Vigo, Zaragoza]
    >> Compute task is executed, check for output on the server nodes.
    ``` 
   and the following output on the server node:
    ```text
    >> Executing the compute task
    >> Found 13 cities with code SPA
    ```
1. [Optional] Go to the compute task class [CityFilterTask.java](/compute-task-executor/src/main/java/org.gridgain/demo/CityFilterTask.java)
   and invert comments in rows 26-27:
    ```java
       CityComputeJob job = new CityComputeJob(CountryCode.SPA);
    // CityComputeJob job = new CityComputeJob(CountryCode.FRA);
    ```
1. [Optional] Perform points 5, 6, 7 again and you should get the following output:
    ```text
    >> Execute org.gridgain.demo.CityFilterTask job
    >> Job result:
    [Angers, Bordeaux, Brest, Dijon, Grenoble, Lille, Lyon, Marseille, Montpellier, Nantes, Nice, Paris, Reims, Rennes, Strasbourg, Toulon, Toulouse]
    >> Compute task is executed, check for output on the server nodes.
    ```
   and on the server node side:
    ```text
    >> Executing the compute task
    >> Found 17 cities with code FRA
    ```
