import com.gits.ChatService.grpc.Chat;
import com.gits.ChatService.grpc.ChatServiceGrpc;
import io.grpc.stub.StreamObserver;

import java.util.LinkedHashSet;

public class ChatServiceImpl extends ChatServiceGrpc.ChatServiceImplBase {
    private static LinkedHashSet<StreamObserver<Chat.ChatMessage>> observers = new LinkedHashSet<>();
    @Override
    public StreamObserver<Chat.ChatMessage> sendMessage(StreamObserver<Chat.ChatMessage> responseObserver) {
        observers.add(responseObserver);

        return new StreamObserver<Chat.ChatMessage>() {
            @Override
            public void onNext(Chat.ChatMessage value) {
                    observers.stream().filter(ro -> ro != responseObserver).forEach( observer -> {
                    observer.onNext(value);
                });
            }

            @Override
            public void onError(Throwable t) {
                observers.remove(responseObserver);
                t.printStackTrace();
            }

            @Override
            public void onCompleted() {
                observers.remove(responseObserver);
            }
        };
    }
}
