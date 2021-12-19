package io.graversen.minecraft.rcon.service;

import io.graversen.minecraft.rcon.MinecraftClient;
import io.graversen.minecraft.rcon.RconConnectException;

import java.util.concurrent.Callable;

import static java.lang.System.Logger.Level.*;

class ConnectTask implements Callable<MinecraftClient> {
    private static final System.Logger log = System.getLogger(MinecraftClient.class.getName());

    private final ConnectOptions connectOptions;
    private final RconDetails rconDetails;

    ConnectTask(ConnectOptions connectOptions, RconDetails rconDetails) {
        this.connectOptions = connectOptions;
        this.rconDetails = rconDetails;
        log.log(DEBUG, connectOptions);
    }

    @Override
    public MinecraftClient call() {
        int currentAttempt = 0;

        while (currentAttempt < connectOptions.getMaxRetries() && !Thread.currentThread().isInterrupted()) {
            currentAttempt++;
            log.log(DEBUG, "Connection attempt " + currentAttempt);

            try {
                return MinecraftClient.connect(rconDetails.getHostname(), rconDetails.getPassword(), rconDetails.getPort());
            } catch (Exception e) {
                log.log(ERROR, "Connection attempt failed", e);
            } finally {
                if (currentAttempt < connectOptions.getMaxRetries()) {
                    sleep();
                } else {
                    log.log(WARNING, "Ran out of retries after " + currentAttempt + " total attempts");
                }
            }
        }

        throw new RconConnectException("Unable to connect to Minecraft server after %d retries", currentAttempt - 1);
    }

    private void sleep() {
        try {
            log.log(DEBUG, "Pausing for " + connectOptions.getTimeBetweenRetries().toMillis() + " ms");
            Thread.sleep(connectOptions.getTimeBetweenRetries().toMillis());
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }
}
