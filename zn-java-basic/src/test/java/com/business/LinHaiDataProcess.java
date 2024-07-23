package com.business;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class LinHaiDataProcess {

    private String dataLine=" select *  from  zn_wind_stable where tag_name='%s' and time>'2023-05-10 00:00:00' and time<'2023-08-31 00:00:00' >> /data/jiahai31/%s.csv ; ";
    private String importLine=" insert into zjny_center_tsdb_td.zn_wind_stable_%s file /data/jiahai31/%s.csv;";


    @Test
    public  void  crateTableSql(){
        try {
            FileReader fr=new FileReader("C:\\Users\\sunyunli\\Desktop\\工作\\临海\\东电数据导入/jx31.txt");
            BufferedReader br=new BufferedReader(fr);
            String tmp="";
            while((tmp=br.readLine())!=null){
                String[] dataArr=tmp.split("\t");
                String tagName=dataArr[0];
                String tabName=tagName.replace(".","_").replace("-","_");
                String tag_describe=dataArr[1];
                String point_source=dataArr[2];
                String  units=dataArr[3];
                String point_type=dataArr[4];
//                String exeSql=String.format(dataLine,tagName,tagName);
                String exeSql=String.format(importLine,tabName,tagName);
                System.out.println(exeSql);

//                System.out.println(tagName+"___"+tag_describe+"-------"+point_source+"  -----  "+units+"    \\\\\\  "+point_type);


            }





        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }




}
