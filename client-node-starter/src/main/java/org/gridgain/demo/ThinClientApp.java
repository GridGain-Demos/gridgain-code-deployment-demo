package org.gridgain.demo;

import java.util.List;
import org.apache.ignite.Ignition;
import org.apache.ignite.client.ClientConnectionException;
import org.apache.ignite.client.IgniteClient;
import org.apache.ignite.configuration.ClientConfiguration;

/**
 * Example of a thin client, that connects a cluster and executes job there.
 */
public class ThinClientApp {
    /** Class name of the task as a task name. */
    private static final String TASK_NAME = "org.gridgain.demo.CityFilterTask";

    /** Connection urls. */
    private static final String ADDRESSES = "localhost:10800";
    /** Cluster username. */
    private static final String USERNAME = "ignite";
    /** Cluster password. */
    private static final String PASSWORD = "ignite";

    /**
     * @param args Args.
     */
    public static void main(String[] args) throws Exception {
        ClientConfiguration cfg = new ClientConfiguration()
            .setAddresses(ADDRESSES)
            .setUserName(USERNAME)
            .setUserPassword(PASSWORD);

        try (IgniteClient client = Ignition.startClient(cfg)) {
            System.out.println(">> Execute " + TASK_NAME + " job");

            // Executing custom Java callable on server nodes.
            List<String> cities = client.compute().execute(TASK_NAME, null);

            System.out.println(">> Job result:\n" + cities);
            System.out.println(">> Compute task is executed, check for output on the server nodes.");
        } catch (ClientConnectionException ex) {
            // All the servers are unavailable
            ex.printStackTrace();
        }
    }
}
