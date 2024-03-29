/*
 * Copyright (c) 2018-2023 Leon Linhart,
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
package com.osmerion.quitte.internal.binding;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.osmerion.quitte.InvalidationListener;
import com.osmerion.quitte.WeakInvalidationListener;
import com.osmerion.quitte.collections.ObservableSet;
import com.osmerion.quitte.collections.SetChangeListener;
import com.osmerion.quitte.collections.WeakSetChangeListener;

/**
 * A specialized {@link Set} binding.
 *
 * @author  Leon Linhart
 */
public final class SetBinding<S, E> implements Binding {

    private final Deque<SetChangeListener.Change<? extends S>> changes = new ArrayDeque<>();

    private final ObservableSet<S> source;

    private final InvalidationListener invalidationListener;
    private final SetChangeListener<S> changeListener;

    private final Function<S, E> transform;

    public SetBinding(Runnable invalidator, ObservableSet<S> source, Function<S, E> transform) {
        this.source = source;
        this.transform = transform;

        this.source.addInvalidationListener(new WeakInvalidationListener(this.invalidationListener = (observable) -> invalidator.run()));
        this.source.addChangeListener(new WeakSetChangeListener<>(this.changeListener = ((observable, change) -> this.changes.addLast(change))));
    }

    public List<SetChangeListener.Change<E>> getChanges() {
        List<SetChangeListener.Change<E>> changes = new ArrayList<>(this.changes.size());
        Iterator<SetChangeListener.Change<? extends S>> changeItr = this.changes.iterator();

        while (changeItr.hasNext()) {
            SetChangeListener.Change<? extends S> change = changeItr.next();
            changeItr.remove();

            SetChangeListener.Change<E> transformedChange = new SetChangeListener.Change<>(
                change.addedElements().stream().map(this.transform).collect(Collectors.toUnmodifiableSet()),
                change.removedElements().stream().map(this.transform).collect(Collectors.toUnmodifiableSet())
            );

            changes.add(transformedChange);
        }

        return changes;
    }

    @Override
    public void release() {
        this.source.removeInvalidationListener(this.invalidationListener);
        this.source.removeChangeListener(this.changeListener);
    }

}