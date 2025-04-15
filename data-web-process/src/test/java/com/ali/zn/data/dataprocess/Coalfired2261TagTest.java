package com.ali.zn.data.dataprocess;

import org.junit.Test;

import java.io.*;
import java.util.*;

public class Coalfired2261TagTest {



    @Test
    public void test() {

        FileReader fr = null;
        try {
            fr = new FileReader("E:\\项目\\舟山煤电-六横电厂\\测点/tags.txt");
            BufferedReader br = new BufferedReader(fr);
            String line="";

            Map<String, String> map = new HashMap<String, String>();
            while ((line= br.readLine())!=null) {
                map.put(line, line);
            }
            br.close();
            fr.close();
            Map<String, String> mapNew = new HashMap<String, String>();
            FileReader  newfr = new FileReader("E:\\项目\\舟山煤电-六横电厂\\测点/tags-new-full.txt");
            List<String> fullList = new ArrayList<String>();
            BufferedReader newbr = new BufferedReader(newfr);
            String linenew="";
            while ((linenew= newbr.readLine())!=null) {
                String resStr="";
                String[]   tag_desc=linenew.split("\t");
                if(tag_desc.length>1) {
                    mapNew.put("2261." + tag_desc[0], tag_desc[1]);
                    resStr=tag_desc[0]+"\t"+"2261." + linenew;
                }else {
                    mapNew.put("2261." + tag_desc[0], "");
                    resStr=tag_desc[0]+"\t"+"2261." + tag_desc[0]+"\t"+tag_desc[0];
                }
                fullList.add( resStr);
            }
            FileWriter fw=new FileWriter("E:\\项目\\舟山煤电-六横电厂\\测点/tags-new-all.txt");
            for(String innerKey:fullList){
                fw.write(innerKey+"\r\n");
            }
//            FileWriter fw=new FileWriter("E:\\项目\\舟山煤电-六横电厂\\测点/tags-need-full.txt");
//            for(String innerKey:mapNew.keySet()){
//                if(!map.containsKey(innerKey)){
//                    System.out.println(innerKey);
//                    fw.write(innerKey.replace("2261.","")+"\t"+innerKey+"\t"+mapNew.get(innerKey)+"\r\n");
//                }
//            }
            fw.flush();
            fw.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testDrops() {

        FileReader fr = null;
        try {
            fr = new FileReader("E:\\项目\\舟山煤电-六横电厂\\测点/tags.txt");
            BufferedReader br = new BufferedReader(fr);
            String line="";

            Map<String, String> map = new HashMap<String, String>();
            while ((line= br.readLine())!=null) {
                map.put(line, line);
//                if(line.contains("ERTU")){
//                    System.out.println(line);
//                }
            }
            br.close();
            fr.close();
            Map<String, String> mapNew = new HashMap<String, String>();
            FileReader  newfr = new FileReader("E:\\项目\\舟山煤电-六横电厂\\测点/tags-new-full.txt");
            List<String> fullList = new ArrayList<String>();
            BufferedReader newbr = new BufferedReader(newfr);
            String linenew="";
            while ((linenew= newbr.readLine())!=null) {
                String resStr="";
                String[]   tag_desc=linenew.split("\t");
                if(tag_desc.length>1) {
                    mapNew.put("2261." + tag_desc[0], tag_desc[1]);
                    resStr=tag_desc[0]+"\t"+"2261." + linenew;
                }else {
                    mapNew.put("2261." + tag_desc[0], "");
                    resStr=tag_desc[0]+"\t"+"2261." + tag_desc[0]+"\t"+tag_desc[0];
                }
                fullList.add( resStr);
            }

            Integer  nums=0;
            FileWriter fw=new FileWriter("E:\\项目\\舟山煤电-六横电厂\\测点/tags-drops.txt");
            for(String oldKey:map.keySet()){
                if(!mapNew.containsKey(oldKey)){
                    System.out.println(oldKey);
                    fw.write(oldKey.replace("2261.","")+"\t"+oldKey+"\t"+" "+"\r\n");
                    nums++;
                }
            }
            fw.flush();
            fw.close();

            System.out.println("total"+nums);


//            FileWriter fw=new FileWriter("E:\\项目\\舟山煤电-六横电厂\\测点/tags-new-all.txt");
//            for(String innerKey:fullList){
//                fw.write(innerKey+"\r\n");
//            }
//            FileWriter fw=new FileWriter("E:\\项目\\舟山煤电-六横电厂\\测点/tags-need-full.txt");
//            for(String innerKey:mapNew.keySet()){
//                if(!map.containsKey(innerKey)){
//                    System.out.println(innerKey);
//                    fw.write(innerKey.replace("2261.","")+"\t"+innerKey+"\t"+mapNew.get(innerKey)+"\r\n");
//                }
//            }
//            fw.flush();
//            fw.close();




        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }


}
