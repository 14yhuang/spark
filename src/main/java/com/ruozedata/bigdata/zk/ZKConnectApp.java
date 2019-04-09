package com.ruozedata.bigdata.zk;


import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.plaf.TableHeaderUI;
import java.util.concurrent.CountDownLatch;

public class ZKConnectApp implements Watcher {

    private static Logger logger = LoggerFactory.getLogger(ZKConnectApp.class);

    private static CountDownLatch connected = new CountDownLatch(1);

    public static void main(String[] args) throws Exception {

        ZooKeeper zooKeeper = new ZooKeeper("192.168.137.190:2181,192.168.137.190:2182,192.168.137.190:2183/kafka", 5000, new ZKConnectApp());


        System.out.println("客户端开始连接ZK Server...");
        System.out.print(zooKeeper.getState());
//        logger.warn("客户端开始连接ZK Server...");
//        logger.warn("连接状况：{}", zooKeeper.getState());
//        Thread.sleep(3000);

        //0时继续执行下面的
        connected.await();

        System.out.print(zooKeeper.getState());

    }
//Watcher的方法
    public void process(WatchedEvent watchedEvent) {
        //如果连接上了
        if (Event.KeeperState.SyncConnected == watchedEvent.getState()) {
            System.out.println(watchedEvent);
            //减1，1-1=0
            connected.countDown();
        }
    }
}
