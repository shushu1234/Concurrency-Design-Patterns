package com.liuyao;

import java.util.concurrent.Callable;

/**
 * @author liuyao
 * @date 2018/08/24
 */
public abstract class GuardedAction<V> implements Callable<V> {
    protected final Predicate guard;

    protected GuardedAction(Predicate guard) {
        this.guard = guard;
    }
}
