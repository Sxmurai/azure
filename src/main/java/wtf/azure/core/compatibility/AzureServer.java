package wtf.azure.core.compatibility;

import net.fabricmc.api.DedicatedServerModInitializer;

/**
 * why the fuck would you run a ghost client on a fucking server?
 *
 * @author aesthetical
 * @since 5/22/22
 */
public class AzureServer implements DedicatedServerModInitializer {
    @Override
    public void onInitializeServer() {
        System.out.println("FATAL! DO NOT RUN THIS MOD IN A SERVER ENVIRONMENT! THIS IS MEANT AS A CLIENT-SIDE MOD ONLY!");
    }
}
