//package com.ruozedata.bigdata.hdfs;
//
//
//import org.apache.hadoop.conf.Configuration;
//import org.apache.hadoop.fs.FSDataInputStream;
//import org.apache.hadoop.fs.FSDataOutputStream;
//import org.apache.hadoop.fs.FileSystem;
//import org.apache.hadoop.fs.Path;
//import org.apache.hadoop.io.IOUtils;
//import org.junit.*;
//
//import java.io.IOException;
//import java.net.URI;
//import java.net.URISyntaxException;
//
////java操作hdfs
//public class HDFSApp {
//    Configuration configuration ;
//    URI uri;
//    FileSystem fileSystem;
//
//    @BeforeClass
//    public static void begin(){
//        System.out.println("begin");
//    }
//
//    @AfterClass
//    public static void stop(){
//        System.out.println("stop");
//    }
//
//    @Before
//    public void setUp(){
//        System.out.println("setUp");
//        //设置连接的参数的
//        configuration = new Configuration();
//        //这个是设置hdfs副本数，不设置的话，用这种操作会默认3副本
//        configuration.set("dfs.replication","1");
//        try {
//            uri = new URI("hdfs://hadoop000:8020");
//            //后面加hadoop就是以什么用户访问，默认是当前电脑用户
//            fileSystem = FileSystem.get(uri,configuration,"hadoop");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    @After
//    public void tearDown(){
//        System.out.println("tearDown");
//        try {
//            fileSystem.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    public void test01() throws Exception{
//        //操作hdfs创建文件夹咯
//        fileSystem.mkdirs(new Path("/g5"));
//    }
//
//    @Test
//    public void test02() throws Exception{
//        //输出到文件
//        FSDataOutputStream out =  fileSystem.create(new Path("/g5/test/ruozedata-hadoop.txt"));
//        out.writeUTF("ruoze");
//        out.writeUTF("J总");
//
//        out.flush();
//        out.close();
//    }
//
//    @Test
//    public void test03() throws Exception {
//        FSDataInputStream in = fileSystem.open(new Path("/g5/test/ruozedata-hadoop.txt"));
//        //--in:是FSDataInputStream类的对象，是有关读取文件的类，也就是所谓“输入流”
//        //--out:是FSDataOutputStream类的对象，是有关文件写入的类，也就是“输出流”
//        //--4096表示用来拷贝的buffer大小（buffer是缓冲区）--缓冲区大小
//        //把文件读到控制台
//        IOUtils.copyBytes(in, System.out, 1024);
//    }
//
//    @Test
//    public void test04() throws Exception {
//        //改名字
//        fileSystem.rename(new Path("/g5/test/ruozedata-hadoop.txt"),
//                new Path("/g5/test/ruozedata-hadoop.txt-bak"));
//
//        // local ==> hdfs
//        fileSystem.copyFromLocalFile(new Path(""), new Path(""));
//
//        // hdfs ==> local
//        fileSystem.copyToLocalFile(new Path(""), new Path(""));
//
//        //遍历一个路径，是否递归
//        fileSystem.listFiles(new Path(""), true);
//
//        fileSystem.delete(new Path(""),true);
//
//
//    }
//
//}
//
