module com.primeaeterna.netty_practice_one.server {
    requires com.primeaeterna.netty_practice_one.codec;
    requires com.primeaeterna.netty_practice_one.model;
    requires io.netty.common;
    requires io.netty.handler;
    requires io.netty.transport;

    exports com.primeaeterna.netty_practice_one.server;
}