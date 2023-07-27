package com.java.basic.test;

import redis.clients.jedis.JedisClientConfig;

public class ZNJedisClientConfig  implements JedisClientConfig {


    private String password;
    private String user;
    private Integer database;

    public ZNJedisClientConfig(String password, Integer database) {
        this.password = password;
        this.database = database;
    }

    public ZNJedisClientConfig(String password) {
        this.password = password;
    }


    public ZNJedisClientConfig(Integer database) {
        this.database = database;
    }

    public ZNJedisClientConfig(String password, String user, Integer database) {
        this.password = password;
        this.user = user;
        this.database = database;
    }

    @Override
    public String getPassword() {
        return password;
    }

   @Override
   public String getUser(){
        return  user;
   }



    @Override
    public int getDatabase() {
        return database;
    }
}
