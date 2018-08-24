package com.liuyao;

import java.util.concurrent.Callable;

/**
 * @author liuyao
 * @date 2018/08/24
 */
public class Helper {
        private volatile boolean isStateOK = false;
        private final Predicate stateBeOK = new Predicate() {

            @Override
            public boolean evaluate() {
                return isStateOK;
            }

        };

        private final Blocker blocker = new ConditionVarBlocker();

        public synchronized String xGuarededMethod(final String message) {
            GuardedAction<String> ga = new GuardedAction<String>(stateBeOK) {

                @Override
                public String call() throws Exception {
                    return message + "->received.";
                }

            };
            String result = null;
            try {
                result = blocker.callWithGuard(ga);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        public synchronized void xStateChanged() {
            try {
                blocker.signalAfter(new Callable<Boolean>() {

                    @Override
                    public Boolean call() throws Exception {
                        isStateOK = true;
                        System.out.println("state ok.");
                        return Boolean.TRUE;
                    }

                });
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
}
