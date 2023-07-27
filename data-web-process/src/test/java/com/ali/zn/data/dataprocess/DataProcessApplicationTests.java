package com.ali.zn.data.dataprocess;


import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class DataProcessApplicationTests {


    void contextLoads() {
//      ConcurrentHashMap  hashMap=new ConcurrentHashMap<String,String>();
        Map<String, String> dataline = new HashMap<>();


        if ("/data/logs".matches("^(?:[\\w]\\:(\\[a-z_\\-\\s0-9\\.]+)*)")) {
            System.out.println("-------   prefect   ------   ");
        }

//        Semaphore semaphore=new Semaphore(10);
    }

    @Test
    public void getPublic() {

        if ("/data/logs2023".matches("^[A-Z\\/]+:?[_\\-\\w+\\/\\.]+$")) {
            System.out.println("-------   prefect   ------   ");
        }
    }


    @Test
    public void getFileContent() throws IOException {
        Path path = Paths.get("D:\\data\\backup\\back1/SCHEMA.json");
        Files.lines(path).forEach(line->{
            System.out.println(line);
        });


    }

}
