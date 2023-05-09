package com.ali.data

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SparkSession


object DatProcess {

  def main(args: Array[String]): Unit = {
    // 环境
    val conf = new SparkConf().setAppName(" data Anaysis ").setMaster("local[2]")
    val spark = SparkSession.builder().config(conf).getOrCreate();


    val df = spark.read.json("file:///F:\\data//solar20230404.json").repartition(1)
    df.createOrReplaceTempView(" tag_info_t")


//    spark.sql("select  substring(tagName,1,4) as companyCode  from tag_info_t ").show(100);
//
    spark.sql("select  substring(tagName,1,4) as companyCode  , count(  distinct tagName) as tagNums ,count(*) as  recordNums ,max(PIts) as endTime ,min(piTs) as startTime from tag_info_t group by  substring(tagName,1,4) order by companyCode   ").show(100, false)



    //  df.printSchema()

  }

}