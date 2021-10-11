package org.gridgain.demo;

import java.util.Collection;
import java.util.List;
import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.spi.communication.tcp.TcpCommunicationSpi;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.vm.TcpDiscoveryVmIpFinder;
import org.gridgain.grid.configuration.GridGainConfiguration;

import static java.util.Collections.singletonList;

/**
 * Example of thin client application.
 */
public class ThickClientApp
{
    /** Connection urls to provisioned clusters. */
    private static final Collection<String> CONNECTION_URLS = singletonList("f1f75fb6-9419-4611-8ae9-3b1890272583.gridgain-nebula-test.com:47500");

    public static void main(String[] args) {
        System.setProperty("IGNITE_SKIP_CONFIGURATION_CONSISTENCY_CHECK", "true");
        System.setProperty("IGNITE_EVENT_DRIVEN_SERVICE_PROCESSOR_ENABLED", "true");

        IgniteConfiguration cfg = new IgniteConfiguration()
            .setClientMode(true)
            .setDiscoverySpi(new TcpDiscoverySpi()
                .setIpFinder(new TcpDiscoveryVmIpFinder()
                    .setAddresses(CONNECTION_URLS)))
            .setCommunicationSpi(new TcpCommunicationSpi()
                .setForceClientToServerConnections(true))
            .setPluginConfigurations(new GridGainConfiguration()
                .setRollingUpdatesEnabled(true)
            );

        try (Ignite client = Ignition.start(cfg)) {
            // Executing custom Java callable on server nodes.
            List<String> cities = client.compute().execute(new CityFilterTask(), null);

            System.out.println(">> Cities:\n" + cities);
            System.out.println(">> Compute task is executed, check for output on the server nodes.");
        }
    }
}
