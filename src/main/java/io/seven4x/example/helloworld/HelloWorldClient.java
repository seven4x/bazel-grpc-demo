package io.seven4x.example.helloworld;

import io.grpc.Channel;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.example.helloworld.GreeterGrpc;
import io.grpc.example.helloworld.HelloReply;
import io.grpc.example.helloworld.HelloRequest;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;


public class HelloWorldClient {
    private static final Logger logger = Logger.getLogger(HelloWorldClient.class.getName());

    private final GreeterGrpc.GreeterBlockingStub blockingStub;

    public HelloWorldClient(Channel channel) {
        blockingStub = GreeterGrpc.newBlockingStub(channel);
    }

    public void greet(String name) {
        HelloRequest request = HelloRequest.newBuilder().setName(name).build();
        HelloReply helloReply;
        try {
            helloReply = blockingStub.sayHello(request);
        } catch (Exception e) {
            logger.log(Level.WARNING, e.getMessage());
            return;
        }
        System.out.println("Greeting:" + helloReply.getMessage());
    }

    public static void main(String[] args) throws Exception {
        ManagedChannel channel = ManagedChannelBuilder.forTarget("localhost:50051").usePlaintext().build();
        try {
            HelloWorldClient client = new HelloWorldClient(channel);
            client.greet("seven4x");
        } finally {
            channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
        }
    }
}
