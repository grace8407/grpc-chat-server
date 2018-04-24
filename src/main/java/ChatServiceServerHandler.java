import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class ChatServiceServerHandler {
    public static void main(String[] args) {
        try {
            final Server server = ServerBuilder.forPort(9090)
                    .addService(new ChatServiceImpl())
                    .build()
                    .start();
            server.awaitTermination();

            Runtime.getRuntime().addShutdownHook(new Thread(() -> server.shutdown()));
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
