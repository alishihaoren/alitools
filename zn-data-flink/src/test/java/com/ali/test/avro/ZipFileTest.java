package com.ali.test.avro;

import org.junit.Test;

import java.io.*;
import java.util.concurrent.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipFileTest {

    @Test
    public void compressZipFile() {
        Integer BUFFER_SIZE = 4 << 10;
        String fileDir = "D:\\data\\backup\\back1\\20230727";
        String zipFileName = "D:\\data\\backup\\back1\\20230727.zip";
        try {
            ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(zipFileName));
            File innerFile = new File(fileDir);
            if (innerFile.isDirectory()) {
                File[] flieList = innerFile.listFiles();
                for (File file : flieList) {
                    ZipEntry entry = new ZipEntry(file.getName());
                    zipOutputStream.putNextEntry(entry);
                    byte[] buffer = new byte[BUFFER_SIZE];
                    try (InputStream inputStream = new FileInputStream(file)) {
                        int len = -1;
                        while ((len = inputStream.read(buffer)) != -1) {
                            zipOutputStream.write(buffer, 0, len);
                        }
                        zipOutputStream.flush();
                        inputStream.close();
                    }
                }
                zipOutputStream.closeEntry();
                zipOutputStream.close();
            }
//            FileInputStream fileInputStream = new FileInputStream(avroFileName);
//            fileInputStream.read(lineData);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void decompressZipFile() {
        Integer BUFFER_SIZE = 4 << 10;
        String zipFileName = "D:\\data\\backup\\back1\\20230727.zip";
//        String zipDir = "D:\\data\\backup\\back1\\20230727";

//        String decompressFile = "D:\\data\\backup\\back1\\20230727/";
        String decomZipDir = zipFileName.substring(0, zipFileName.length() - 4);


        try {
            ZipFile zipFile = new ZipFile(zipFileName);
            ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipFileName));
            ZipEntry zipEntry;
            while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                if (!zipEntry.isDirectory()) {
                    File innerFile = new File(decomZipDir, zipEntry.getName());
                    if (!innerFile.exists()) {
                        final boolean mkdirs = innerFile.getParentFile().mkdirs();
                    }
                    final FileOutputStream fileOutputStream = new FileOutputStream(innerFile);
                    final BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
                    if (!innerFile.isDirectory()) {
                        InputStream zipInputStream1 = zipFile.getInputStream(zipEntry);
                        byte[] buffer = new byte[1024 * 10];
                        int len = -1;
                        while ((len = zipInputStream1.read(buffer)) != -1) {
                            bufferedOutputStream.write(buffer, 0, len);
                        }
                        bufferedOutputStream.close();
                        fileOutputStream.close();
                        zipInputStream.closeEntry();
                    }
                }
            }
            zipInputStream.close();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCountData() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        ExecutorService pool = new ThreadPoolExecutor(2, 2, 1000, TimeUnit.MILLISECONDS, new SynchronousQueue<Runnable>(), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
        pool.execute(new DataCountDown(countDownLatch, 300));
        countDownLatch.await(10,TimeUnit.SECONDS);
        System.out.println("结束执行");
        Thread.sleep(20000);

    }


    class DataCountDown implements Runnable {

        private CountDownLatch countDownLatch;
        private Integer num;

        public DataCountDown(CountDownLatch countDownLatch, Integer num) {
            this.countDownLatch = countDownLatch;
            this.num = num;
        }

        @Override
        public void run() {
            System.out.println(" exec ");
            try {
                Thread.sleep(num);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            countDownLatch.countDown();
        }
    }

}
