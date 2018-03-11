# netty_practice_one

Playing around with Netty and Java9 Jigsaw

This repository is an implementation of a [Netty tutorial](http://shengwangi.blogspot.com/2016/03/netty-tutorial-hello-world-example.html) configured as Java 9 Jigsaw modules so that custom JREs can be generated for the client and the server.

## Usage

Generate modules.
```
$ mvn package
```

### Without custom JRE

Start server.
```
$ java --module-path ${JAVA_HOME}/jmods:modules \
       -m com.primeaeterna.netty_practice_one.server/com.primeaeterna.netty_practice_one.server.NettyServer
```

Start client.
```
$ java --module-path ${JAVA_HOME}/jmods:modules \
       -m com.primeaeterna.netty_practice_one.client/com.primeaeterna.netty_practice_one.client.NettyClient
```

Watch the server receive client responses to server heartbeats.
```
...
loop delay in ms: 50.121246
loop delay in ms: 1.635633
...
```

### With custom JRE

Create client JRE.
```
$ jlink --module-path ${JAVA_HOME}/jmods:./modules \
        --add-modules com.primeaeterna.netty_practice_one.client \
        --output clientjre
```

Create server JRE.
```
$ jlink --module-path ${JAVA_HOME}/jmods:./modules \
        --add-modules com.primeaeterna.netty_practice_one.server \
        --output serverjre
```

Start server.
```
$ ./serverjre/bin/java -m com.primeaeterna.netty_practice_one.server/com.primeaeterna.netty_practice_one.server.NettyServer
Mar 10, 2018 5:01:35 PM io.netty.channel.DefaultChannelId defaultProcessId
WARNING: Failed to find the current process ID from ''; using a random value: -601158881
```

Start client.
```
$ ./clientjre/bin/java -m com.primeaeterna.netty_practice_one.client/com.primeaeterna.netty_practice_one.client.NettyClient
Mar 10, 2018 5:01:46 PM io.netty.channel.DefaultChannelId defaultProcessId
WARNING: Failed to find the current process ID from ''; using a random value: 708226864
```

Watch the server receive client responses to server heartbeats.
```
...[server]
Mar 10, 2018 5:01:35 PM io.netty.channel.DefaultChannelId defaultProcessId
WARNING: Failed to find the current process ID from ''; using a random value: -601158881
loop delay in ms: 53.232545
loop delay in ms: 1.638767
loop delay in ms: 1.44784
...
```
