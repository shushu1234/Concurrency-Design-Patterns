package com.liuyao;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 路由表
 * @author liuyao
 * @date 2018/08/23
 */
public class MMSCRouter {
    // volatile修饰，保证多线程环境下的可见性
    private static volatile MMSCRouter instance = new MMSCRouter();
//    维护一个手机号码到信息中心的路由表
    private final Map<String, MMSCInfo> routeMap;

    public MMSCRouter() {
//        将映射关系从数据库取出，存到内存
        this.routeMap = MMSCRouter.retrieveRouteMapFromDB();
    }

    private static Map<String, MMSCInfo> retrieveRouteMapFromDB() {
        Map<String, MMSCInfo> map = new HashMap<String, MMSCInfo>();
        //....
        return map;
    }

    public static MMSCRouter getInstance() {
        return instance;
    }

    public MMSCInfo getMMSC(String msisdnPrefix) {
        return routeMap.get(msisdnPrefix);
    }

    /**
     * 将当前的MMSCRouter实例更新为指定的新实例
     *
     * @param newInstance
     */
    public static void setInstance(MMSCRouter newInstance) {
        instance = newInstance;
    }

    /**
     * 进行深拷贝
     * @param m
     * @return
     */
    public static Map<String, MMSCInfo> deepCopy(Map<String, MMSCInfo> m) {
        Map<String, MMSCInfo> result = new HashMap<String, MMSCInfo>();
        for (String key : m.keySet()) {
            result.put(key, new MMSCInfo(m.get(key)));
        }
        return result;
    }

    public Map<String, MMSCInfo> getRouteMap() {
        //防御性复制
        return Collections.unmodifiableMap(deepCopy(routeMap));
    }
}
