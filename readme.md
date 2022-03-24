# version 
bazel  4.2.1 以上

go 版本 1.18

java  11


# 启动Java版 server

`bazel run //:hello-world-server`



# 新窗口 启动Java版 client

`bazel run //:hello-world-client `

will print `Greeting:helloseven4x`

或者

# 启动golang 版server 

`bazel run //src/main/go/helloword/server:server` 

# 启动Java版 client 

`bazel run //:hello-world-client `

will print `Greeting:Hello seven4x`
