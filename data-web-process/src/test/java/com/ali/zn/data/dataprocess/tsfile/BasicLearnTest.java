//package com.ali.zn.data.dataprocess.tsfile;
//
//import org.apache.tsfile.enums.TSDataType;
//import org.apache.tsfile.exception.read.ReadProcessException;
//import org.apache.tsfile.exception.write.NoMeasurementException;
//import org.apache.tsfile.exception.write.NoTableException;
//import org.apache.tsfile.exception.write.WriteProcessException;
//import org.apache.tsfile.file.metadata.ColumnSchemaBuilder;
//import org.apache.tsfile.file.metadata.TableSchema;
//import org.apache.tsfile.fileSystem.FSFactoryProducer;
//import org.apache.tsfile.read.TsFileReader;
//import org.apache.tsfile.read.TsFileSequenceReader;
//import org.apache.tsfile.read.common.Field;
//import org.apache.tsfile.read.common.Path;
//import org.apache.tsfile.read.common.RowRecord;
//import org.apache.tsfile.read.expression.QueryExpression;
//import org.apache.tsfile.read.query.dataset.QueryDataSet;
//import org.apache.tsfile.read.query.dataset.ResultSet;
//import org.apache.tsfile.read.query.dataset.ResultSetMetadata;
//import org.apache.tsfile.read.query.executor.TsFileExecutor;
//import org.apache.tsfile.read.v4.ITsFileReader;
//import org.apache.tsfile.read.v4.TsFileReaderBuilder;
//
//import org.apache.tsfile.write.record.Tablet;
//import org.apache.tsfile.write.v4.ITsFileWriter;
//import org.apache.tsfile.write.v4.TsFileWriterBuilder;
//import org.junit.Test;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.StringJoiner;
//
//import static org.junit.Assert.assertEquals;
//
//public class BasicLearnTest {
//
//
//    @Test
//    public void initTsFileTest() {
////        File f = new File("E:\\data\\tsfile\\test.tsfile");
//        try {
//
//            String path = "E:\\data\\tsfile\\test.tsfile";
//            File f = FSFactoryProducer.getFSFactory().getFile(path);
//
//            String tableName = "table1";
//
//            TableSchema tableSchema =
//                    new TableSchema(
//                            tableName,
//                            Arrays.asList(
//                                    new ColumnSchemaBuilder()
//                                            .name("id1")
//                                            .dataType(TSDataType.STRING)
//                                            .category(Tablet.ColumnCategory.TAG)
//                                            .build(),
//                                    new ColumnSchemaBuilder()
//                                            .name("id2")
//                                            .dataType(TSDataType.STRING)
//                                            .category(Tablet.ColumnCategory.TAG)
//                                            .build(),
//                                    new ColumnSchemaBuilder()
//                                            .name("s1")
//                                            .dataType(TSDataType.INT32)
//                                            .category(Tablet.ColumnCategory.FIELD)
//                                            .build(),
//                                    new ColumnSchemaBuilder()
//                                            .name("s2")
//                                            .dataType(TSDataType.BOOLEAN)
//                                            .category(Tablet.ColumnCategory.FIELD)
//                                            .build()));
//
//            long memoryThreshold = 10 * 1024 * 1024;
//
//            ITsFileWriter writer =
//                    new TsFileWriterBuilder()
//                            .file(f)
//                            .tableSchema(tableSchema)
//                            .memoryThreshold(memoryThreshold)
//                            .build();
//
//            for(int tableNum=0; tableNum<1000; tableNum++) {
//            Tablet tablet =
//                    new Tablet(
//                            Arrays.asList("id1", "id2", "s1", "s2"),
//                            Arrays.asList(
//                                    TSDataType.STRING, TSDataType.STRING, TSDataType.INT32, TSDataType.BOOLEAN),10000);
//            for (int row = 0; row < 10000; row++) {
//                long timestamp = row;
//                tablet.addTimestamp(row, timestamp);
//                tablet.addValue(row, "id1", "id1_filed_1"+tableNum);
//                tablet.addValue(row, "id2", "id2_filed_1"+tableNum);
//                tablet.addValue(row, "s1", row);
//                tablet.addValue(row, "s2", true);
//            }
//            writer.write(tablet);
//            }
//
//
//            writer.close();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        } catch (WriteProcessException e) {
//            throw new RuntimeException(e);
//        }
//
//
//    }
//
//
//    @Test
//    public  void QueryData(){
//         TsFileExecutor queryExecutorWithQueryFilter;
//        String path = "E:\\data\\tsfile\\test.tsfile";
//        File f = FSFactoryProducer.getFSFactory().getFile(path);
//
//        try {
//            ITsFileReader reader =new TsFileReaderBuilder().file(f).build();
//            String tableName = "table1";
//
//            List<Path> pathList = new ArrayList<>();
//            pathList.add(new Path("table1", "id1", true));
//            QueryExpression queryExpression = QueryExpression.create(pathList, null);
////            FileGenerator.generateFile(1000, 100);
//            TsFileSequenceReader reader1 = new TsFileSequenceReader(path);
//            TsFileReader  roTsFile = new TsFileReader(reader1);
//
//            long nowdate = System.currentTimeMillis();
//
//            QueryDataSet dataSet = roTsFile.query(queryExpression);
//            int cnt = 0;
//            while (dataSet.hasNext()) {
//                RowRecord r = dataSet.next();
//                if (cnt == 1) {
//                    assertEquals(1480562618980L, r.getTimestamp());
//                    Field f1 = r.getFields().get(0);
//                    assertEquals(108.0, f1.getFloatV(), 0.0);
//                }
//                if (cnt == 2) {
//                    assertEquals(1480562618990L, r.getTimestamp());
//                    Field f2 = r.getFields().get(0);
//                    assertEquals(110.0, f2.getFloatV(), 0.0);
//                }
//                cnt++;
//            }
//
//
//
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//
//    }
//
//
//
//
//}
