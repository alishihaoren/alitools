package com.ali.zn.data.dataprocess;

import lombok.val;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.junit.Test;

import java.io.*;

public class GasFired2031TagsProcessTest {

    @Test
    public void getTagFileInfo() {
        String basePath = "E:\\项目\\萧山电厂\\测点/";
        String file1 = "zn_xiaoshan.csv";
        String file2 = "zn_xscn_alarm_stable.csv";
        String file3 = "zn_xscn_stable.csv";
        String cndb = "xscn_db";
        String xsdb = "zn_gasfired_db";
        String xsStable = "zn_xiaoshan";
        String cnStable = "zn_xscn_stable";


        try {
            FileReader fileReader = new FileReader(basePath + file2);
            FileWriter rw = new FileWriter(basePath + file2.replace("csv","sql"));
            BufferedReader br = new BufferedReader(fileReader);
            String tmp = "";

            while ((tmp = br.readLine()) != null) {
                String[] columns = tmp.split(",");
                String tagName =columns[0];
                String tagDescribe = columns[1];
                String standardCode = columns[2];
                if(standardCode.equals("NULL")){
                    standardCode=tagName;
                }
                String tbName = columns[3].replace("'","");
                String sqlformat = new String("create table %s using  zn_xscn_alarm_stable(tag_name,tag_describe,standard_code) TAGS(%s,%s,%s);\r\n");
                String sql = String.format(sqlformat, tbName, tagName, tagDescribe, standardCode);
                rw.write(sql);
                System.out.println(sql);
            }
            rw.flush();
            rw.close();


        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

//        String tmp="";
//        try {
//            FileReader  fileReader=new FileReader(basePath+file1);
//            BufferedReader  br=new BufferedReader(fileReader);
//            while ((tmp=br.readLine())!=null){
//
//                System.out.println(tmp);
//            }
//
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }


    }

}
