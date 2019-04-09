package com.rz.date.zk;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


public class ZKCreateNodeApp implements Watcher{
    private static Logger logger = LoggerFactory.getLogger(ZKConnectApp.class);

    public static void main(String[] args) throws Exception{
        ZooKeeper zooKeeper = new ZooKeeper("192.168.137.190:2181,192.168.137.190:2182,192.168.137.190:2183/kafka", 5000, new ZKConnectApp());

        Thread.sleep(2000);

//        String path = zooKeeper.create("/g5_zk_test01", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
//        logger.warn("成功创建节点：{}", path);
//        System.out.println(path);

//        Stat stat = zooKeeper.setData("/g5_zk_test01","ruozedata".getBytes(),-1);
//        logger.warn("version：{}", stat.getVersion());

//        byte[] data = zooKeeper.getData("/g5_zk_test01", true, new Stat());
//        logger.warn("data：{}", new String(data));

//        Stat stat  = zooKeeper.exists("/g5_zk33333_test01", true);
//        logger.warn("exist：{}", stat!=null);

//        List<String> children =  zooKeeper.getChildren("/g5_zk_test01", true);
//        for(String child : children) {
//            logger.warn(child);
//        }

        zooKeeper.delete("/g5_zk_test01",-1);
    }
    public void process(WatchedEvent watchedEvent) {

    }
}