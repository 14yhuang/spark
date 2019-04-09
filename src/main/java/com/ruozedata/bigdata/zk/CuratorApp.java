package com.ruozedata.bigdata.zk;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.Stat;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 *
 @BeforeClass 全局只会执行一次，而且是第一个运行
 @Before 在测试方法运行之前运行
 @Test 测试方法
 @After 在测试方法运行之后允许
 @AfterClass 全局只会执行一次，而且是最后一个运行
 @Ignore 忽略此方法

 */


public class CuratorApp {

    CuratorFramework client = null;
    String zk = "192.168.137.190:2181,192.168.137.190:2182,192.168.137.190:2183/kafka";

//    @Test
//    public void testCreateNode() throws Exception{
//        //创建节点，没有父节点就创建
//        //持久化节点，瞬时节点session过了就看不到了
//        //最后的结果于在g5/huahua 的数据有 你爱花花吗?
//        client.create().creatingParentsIfNeeded()
//                .withMode(CreateMode.PERSISTENT)
//                .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)
//                .forPath("/huahua","你爱花花吗?".getBytes());
//    }

//    @Test
//    public void testSetData() throws Exception {
//        client.setData().withVersion(0).forPath("/huahua","你爱花花吗?1111".getBytes());
//    }
//
//    @Test
//    public void gotData() throws Exception {
//        String asd = new String(client.getData().forPath("/huahua"));
//        System.out.println(asd);
//    }
//
//    @Test
//    public void gotchild() throws Exception{
//        List<String> dsa = client.getChildren().forPath("/");
//        System.out.println(dsa);
//    }
//
//
//    @Before
//    public void setUp(){
//        //namespace就是zookeeper的一个目录，前面会自动拼个/，即/g5
//        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 5);
//        client = CuratorFrameworkFactory.builder().connectString(zk)
//                .sessionTimeoutMs(20000).retryPolicy(retryPolicy)
//                .namespace("g5").build();
//        client.start();
//    }
//
////    @After
////    public void delete(){
////        Stat stat =new Stat();
////        client.delete().guaranteed().deletingChildrenIfNeeded().withVersion(stat.getVersion()).forPath("/g5");
////    }
//
//    @After
//    public void tearDown(){
//        //连接问题可以保证
//
//        client.close();
//    }

}
