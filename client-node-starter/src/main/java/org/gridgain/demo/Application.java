package org.gridgain.demo;

import java.util.List;
import org.apache.ignite.Ignition;
import org.apache.ignite.client.IgniteClient;
import org.apache.ignite.configuration.ClientConfiguration;

/**
 * Example of client application.
 */
public class Application
{
    public static void main( String[] args )
    {
        System.setProperty("IGNITE_EVENT_DRIVEN_SERVICE_PROCESSOR_ENABLED", "true");
        System.setProperty("IGNITE_PERFORMANCE_SUGGESTIONS_DISABLED", "true");
        System.setProperty("IGNITE_SKIP_CONFIGURATION_CONSISTENCY_CHECK", "true");

        // Preparing IgniteConfiguration using Java APIs
        ClientConfiguration cfg = new ClientConfiguration()
            .setAddresses("127.0.0.1:47500..47503");

        // Starting the client node
        try (IgniteClient ignite = Ignition.startClient(cfg)) {
            // Executing custom Java callable on server nodes.
            List<String> cities = ignite.compute().execute(CityFilterTask.class.getName(), null);

            System.out.println(">> Cities:\n" + cities);
            System.out.println(">> Compute task is executed, check for output on the server nodes.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
