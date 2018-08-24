/*
授权声明：
本源码系《Java多线程编程实战指南（设计模式篇）》一书（ISBN：978-7-121-27006-2，以下称之为“原书”）的配套源码，
欲了解本代码的更多细节，请参考原书。
本代码仅为原书的配套说明之用，并不附带任何承诺（如质量保证和收益）。
以任何形式将本代码之部分或者全部用于营利性用途需经版权人书面同意。
将本代码之部分或者全部用于非营利性用途需要在代码中保留本声明。
任何对本代码的修改需在代码中以注释的形式注明修改人、修改时间以及修改内容。
本代码可以从以下网址下载：
https://github.com/Viscent/javamtp
http://www.broadview.com.cn/27006
*/

package com.liuyao;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;


/**
 * 本程序是为了演示“嵌套监视器锁死“而写的，因此本程序需要通过手工终止进程才能结束。
 * 
 * @author Viscent Huang
 *
 */
public class NestedMonitorLockoutExample {

    public static void main(String[] args) {
        final Helper helper = new Helper();
        System.out.println("Before calling guaredMethod.");

        Thread t = new Thread(new Runnable() {

            @Override
            public void run() {
                String result;
                result = helper.xGuarededMethod("test");
                System.out.println(result);
            }

        });
        t.start();

        final Timer timer = new Timer();

        // 延迟50ms调用helper.stateChanged方法
        final Helper helper1=new Helper();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                helper1.xStateChanged();
                timer.cancel();
            }

        }, 50, 10);

    }


}