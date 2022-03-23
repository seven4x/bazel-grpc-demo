package io.seven4x.example.helloworld;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.example.helloworld.GreeterGrpc;
import io.grpc.example.helloworld.HelloReply;
import io.grpc.example.helloworld.HelloRequest;
import io.grpc.stub.StreamObserver;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class HelloWorldServer {
    private Server server;


    private void start() throws IOException, InterruptedException {
        server = ServerBuilder.forPort(50051).addService(new GreeterImpl()).build().start();
        // 注册进程关闭回调
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                try {
                    server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        server.awaitTermination();
    }


    public static void main(String[] args) throws IOException, InterruptedException {
        HelloWorldServer helloWorldServer = new HelloWorldServer();
        helloWorldServer.start();
    }


    //实现具体的服务端逻辑
    static class GreeterImpl extends GreeterGrpc.GreeterImplBase {
        @Override
        public void sayHello(HelloRequest request, StreamObserver<HelloReply> responseObserver) {
            HelloReply reply = HelloReply.newBuilder().setMessage("hello" + request.getName()).build();
            responseObserver.onNext(reply);
            responseObserver.onCompleted();
        }
    }
}
