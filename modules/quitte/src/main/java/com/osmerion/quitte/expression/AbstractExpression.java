/*
 * Copyright (c) 2018-2022 Leon Linhart,
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its contributors
 *    may be used to endorse or promote products derived from this software
 *    without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package com.osmerion.quitte.expression;

import java.util.IdentityHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.annotation.Nullable;

import com.osmerion.quitte.InvalidationListener;
import com.osmerion.quitte.Observable;
import com.osmerion.quitte.WeakInvalidationListener;
import com.osmerion.quitte.functional.BoolSupplier;

/**
 * Common logic for expressions.
 *
 * @since   0.1.0
 *
 * @author  Leon Linhart
 */
public abstract class AbstractExpression implements Expression {

    private final transient CopyOnWriteArraySet<InvalidationListener> invalidationListeners = new CopyOnWriteArraySet<>();

    @Nullable
    private transient IdentityHashMap<Observable, WeakInvalidationListener> dependencies;

    // package-private constructor for an effectively sealed class
    AbstractExpression() {}

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    public final boolean addListener(InvalidationListener listener) {
        return this.invalidationListeners.add(listener);
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    public final boolean removeListener(InvalidationListener listener) {
        return this.invalidationListeners.remove(listener);
    }

    /**
     * Invalidates the result of this expression.
     *
     * @since   0.1.0
     */
    protected final void invalidate() {
        this.doInvalidate();
    }

    abstract void doInvalidate();

    final void notifyInvalidationListeners() {
        for (var listener : this.invalidationListeners) {
            if (listener.isInvalid()) {
                this.invalidationListeners.remove(listener);
                continue;
            }

            listener.onInvalidation(this);
            if (listener.isInvalid()) this.invalidationListeners.remove(listener);
        }
    }

    /**
     * Adds a dependency for this expression. This expression will be invalidated when the given {@link Observable} is
     * invalidated.
     *
     * @param observable    the observable on which this expression should depend
     *
     * @throws IllegalArgumentException if this expression already depends on the given {@code Observable}
     *
     * @since   0.1.0
     */
    protected final synchronized void addDependency(Observable observable) {
        if (this.dependencies == null) this.dependencies = new IdentityHashMap<>();

        WeakInvalidationListener listener = new WeakInvalidationListener(ignored -> this.doInvalidate());
        this.dependencies.compute(observable, (key, oldValue) -> {
            if (oldValue != null) throw new IllegalArgumentException("Expression already depends on observable: " + observable);

            observable.addListener(listener);
            return listener;
        });
    }

    /**
     * Adds a dependency for this expression. This expression will be invalidated when the given {@link Observable} is
     * invalidated and the given verifier function returns {@code true}.
     *
     * @param observable    the observable on which this expression should depend
     * @param verifier      the verifier that decides whether an invalidation should be propagated
     *
     * @throws IllegalArgumentException if this expression already depends on the given {@code Observable}
     *
     * @since   0.1.0
     */
    protected final synchronized void addDependency(Observable observable, BoolSupplier verifier) {
        if (this.dependencies == null) this.dependencies = new IdentityHashMap<>();

        WeakInvalidationListener listener = new WeakInvalidationListener(ignored -> {
            if (verifier.get()) this.doInvalidate();
        });
        this.dependencies.compute(observable, (key, oldValue) -> {
            if (oldValue != null) throw new IllegalArgumentException("Expression already depends on observable: " + observable);

            observable.addListener(listener);
            return listener;
        });
    }

    /**
     * Adds a dependency for this expression. This expression will be invalidated when the given {@link Observable} is
     * invalidated.
     *
     * <p>The given {@link Runnable} is executed when the given {@link Observable} is invalidated and may be used to
     * implemented side effects.</p>
     *
     * @param observable    the observable on which this expression should depend
     * @param action        the action that should be performed when the dependency is invalidated but before the value
     *                      of this action is recomputed
     *
     * @throws IllegalArgumentException if this expression already depends on the given {@code Observable}
     *
     * @since   0.1.0
     */
    protected final synchronized void addDependency(Observable observable, Runnable action) {
        if (this.dependencies == null) this.dependencies = new IdentityHashMap<>();

        WeakInvalidationListener listener = new WeakInvalidationListener(ignored -> {
            action.run();
            this.doInvalidate();
        });
        this.dependencies.compute(observable, (key, oldValue) -> {
            if (oldValue != null) throw new IllegalArgumentException("Expression already depends on observable: " + observable);

            observable.addListener(listener);
            return listener;
        });
    }

    /**
     * Removes a dependency for this expression.
     *
     * @param observable    the observable on which this expression should not longer depend
     *
     * @throws IllegalArgumentException if this expression does not depend on the given {@code Observable}
     *
     * @since   0.1.0
     */
    protected final synchronized void removeDependency(Observable observable) {
        if (this.dependencies == null) throw new IllegalArgumentException("Expression does not depend on observable: " + observable);

        WeakInvalidationListener listener = this.dependencies.remove(observable);
        if (listener == null) throw new IllegalArgumentException("Expression does not depend on observable: " + observable);

        observable.removeListener(listener);
    }

}