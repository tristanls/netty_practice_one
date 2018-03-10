module com.primeaeterna.netty_practice_one.client {
    requires com.primeaeterna.netty_practice_one.codec;
    requires com.primeaeterna.netty_practice_one.model;
    requires io.netty.resolver;
    requires io.netty.transport;

    exports com.primeaeterna.netty_practice_one.client;
}