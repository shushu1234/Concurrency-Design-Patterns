package com.liuyao;

/**
 * 处理路由表变更类
 * @author liuyao
 * @date 2018/08/23
 */
public class OMCAgent extends Thread {
    @Override
    public void run() {
        boolean isTableModificationMsg=false;
        String updatedTableName=null;
        while (true){
            //...
            //读取到新的路由表信息，进行更新
            if (isTableModificationMsg){
                if ("MMSCInfo".equals(updatedTableName)){
                    MMSCRouter.setInstance(new MMSCRouter());
                }
            }
            //....
        }
    }
}
