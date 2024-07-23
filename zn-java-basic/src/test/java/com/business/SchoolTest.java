package com.business;

import org.junit.Test;

import javax.xml.crypto.Data;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SchoolTest {


    @Test
    public void   getStudentName(){
        Map<String,String> dataMap=new HashMap<String,String>();
        FileReader fileReader1= null;
        BufferedReader br1=null;
        try {
            fileReader1 = new FileReader("F:\\学校/5班已填调查问卷.txt");
            br1=new BufferedReader(fileReader1);
            String tmp="";
            while ((tmp=br1.readLine())!=null){
//                System.out.println(tmp.split("\t")[0]);
                dataMap.put(tmp.split("\t")[0],null);
            }
            br1.close();
            fileReader1.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileReader fileReader= null;
        BufferedReader br=null;
        try {
            fileReader = new FileReader("F:\\学校/5班.txt");
            br=new BufferedReader(fileReader);
            String tmp="";
            while ((tmp=br.readLine())!=null){
              String name=tmp.split("\t")[0];
              if(!dataMap.containsKey(name)){
                  System.out.println(tmp);
              }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
