package com.liuyao;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;

/**
 * 负责连接告警服务器，并发送告警信息给服务器
 * @author liuyao
 * @date 2018/08/24
 */
public class AlarmAgent {
//    用于记录AlarmAgent是否连接上告警服务器
    private volatile boolean connectedToServer = false;

    private final Predicate agentConnected =new Predicate() {
        @Override
        public boolean evaluate() {
            return connectedToServer;
        }
    };

    private final Blocker blocker=new ConditionVarBlocker();

//    心跳定时器
    private final Timer heartbeatTimer=new Timer(true);

    /**
     * 发送告警信息
     * @param alarm
     * @throws Exception
     */
    public void sendAlarm(final AlarmInfo alarm) throws Exception {
//        可能需要等待，知道AlarmAgent连接上告警服务器（或者连接中断后重新连上服务器）
        GuardedAction<Void> guardedAction=new GuardedAction<Void>(agentConnected) {
            @Override
            public Void call() throws Exception {
                doSendAlarm(alarm);
                return null;
            }
        };
        blocker.callWithGuard(guardedAction);
    }

    /**
     * 通过网络连接将告警信息发送给服务器
     * @param alarm
     */
    private void doSendAlarm(AlarmInfo alarm) {
        System.out.println("sending alarm "+alarm);
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void init(){
        //...
//        告警服务器连接线程
        Thread connectingThread =new Thread(new ConnectingTask());

        connectingThread.start();
        heartbeatTimer.schedule(new HeartbeatTask(),60000,2000);
    }

    public void disconnect(){
        System.out.println("disconnected from alarm server");
        connectedToServer=false;
    }

    protected void onConnected(){
        try {
            blocker.signalAfter(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    connectedToServer=true;
                    System.out.println("Connected to Server");
                    return Boolean.TRUE;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void onDisConnected(){
        connectedToServer=false;
    }

    /**
     * 负责与告警服务器建立连接
     */
    private class ConnectingTask implements Runnable{

        @Override
        public void run() {
            //...
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            onConnected();
        }
    }

    /**
     * 心跳定时任务，定时检查与告警服务器的连接是否正常，发现异常后自动重新连接
     */
    private class HeartbeatTask extends TimerTask{

        @Override
        public void run() {
            if (!testConnection()){
                onDisConnected();
                reconnect();
            }
        }
    }

    private boolean testConnection(){
        //...
        return true;
    }

    private void reconnect(){
        ConnectingTask connectingThread=new ConnectingTask();
//        直接在心跳定时器线程中执行
        connectingThread.run();
    }
}
