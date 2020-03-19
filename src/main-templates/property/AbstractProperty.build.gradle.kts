/*
 * Copyright (c) 2018-2020 Leon Linhart,
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
val packageName = "com.github.osmerion.quitte.property"

Type.values().forEach {
    val type = it
    val typeParams = if (type === Type.OBJECT) "<T>" else ""

    template("${packageName.replace('.', '/')}/Abstract${type.abbrevName}Property") {
        """package $packageName;

import java.util.concurrent.CopyOnWriteArraySet;
import java.util.function.Function;

import javax.annotation.Nullable;

import com.github.osmerion.quitte.*;
import com.github.osmerion.quitte.functional.*;
import com.github.osmerion.quitte.internal.binding.*;
import com.github.osmerion.quitte.value.*;
import com.github.osmerion.quitte.value.change.*;

/**
 * ${if (type === Type.OBJECT)
            "A basic implementation for a generic writable property."
        else
            "A basic implementation for a specialized writable {@code ${type.raw}} property."
        }
 *
 * @since   0.1.0
 *
 * @author  Leon Linhart
 */
public abstract class Abstract${type.abbrevName}Property$typeParams implements Writable${type.abbrevName}Property$typeParams {

    private final transient CopyOnWriteArraySet<${type.abbrevName}ChangeListener$typeParams> changeListeners = new CopyOnWriteArraySet<>();
    private final transient CopyOnWriteArraySet<InvalidationListener> invalidationListeners = new CopyOnWriteArraySet<>();
    
    @Nullable
    private transient Binding binding;

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final synchronized void bindTo(ObservableValue<${type.box}> observable) {
        if (this.binding != null) throw new IllegalStateException();
        this.binding = new GenericBinding<>(this, observable);
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final synchronized <S> void bindTo(ObservableValue<S> observable, Function<S, ${type.box}> transform) {
        if (this.binding != null) throw new IllegalStateException();
        this.binding = new MutatingBinding<>(this, observable, transform);
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final synchronized void bindTo(Observable${type.abbrevName}Value$typeParams observable) {
        if (this.binding != null) throw new IllegalStateException();
        this.binding = new ${type.abbrevName}2${type.abbrevName}Binding${if (type === Type.OBJECT) "<>" else ""}(this::setInternal, observable, it -> it);
    }
${Type.values().joinToString(separator = "") { sourceType ->
    val sourceTypeParams = if (sourceType === Type.OBJECT) "<S>" else ""
    val transformTypeParams = when {
        sourceType === Type.OBJECT && type === Type.OBJECT -> "<S, T>"
        sourceType === Type.OBJECT -> "<S>"
        type === Type.OBJECT -> "<T>"
        else -> ""
    }

    """
    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final synchronized $sourceTypeParams${if (sourceTypeParams.isNotEmpty()) " " else ""}void bindTo(Observable${sourceType.abbrevName}Value$sourceTypeParams observable, ${sourceType.abbrevName}2${type.abbrevName}Function$transformTypeParams transform) {
        if (this.binding != null) throw new IllegalStateException();
        this.binding = new ${sourceType.abbrevName}2${type.abbrevName}Binding${if (sourceType === Type.OBJECT || type === Type.OBJECT) "<>" else ""}(this::setInternal, observable, transform);
    }
"""}}
    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final synchronized boolean isBound() {
        return (this.binding != null);
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final synchronized void unbind() {
        if (this.binding == null) throw new IllegalStateException();

        this.binding.release();
        this.binding = null;
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final boolean addListener(${type.abbrevName}ChangeListener$typeParams listener) {
        return this.changeListeners.add(listener);
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final boolean addBoxedListener(ChangeListener<${type.box}> listener) {
        return this.changeListeners.add(${type.abbrevName}ChangeListener.wrap(listener));
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final boolean removeListener(${type.abbrevName}ChangeListener$typeParams listener) {
        return this.changeListeners.remove(listener);
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final boolean removeBoxedListener(ChangeListener<${type.box}> listener) {
        //noinspection SuspiciousMethodCalls
        return this.changeListeners.remove(listener);
    }

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

    private void notifyListeners(${if (type === Type.OBJECT) "@Nullable " else ""}${type.raw} prevValue, ${if (type === Type.OBJECT) "@Nullable " else ""}${type.raw} newValue) {
        for (var itr = this.changeListeners.iterator(); itr.hasNext(); ) {
            var listener = itr.next();
            
            listener.onChanged(this, prevValue, newValue);
            if (listener.isInvalid()) itr.remove();
        }
    
        for (var itr = this.invalidationListeners.iterator(); itr.hasNext(); ) {
            var listener = itr.next();
            
            listener.onInvalidation(this);
            if (listener.isInvalid()) itr.remove();
        }
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override${if (type === Type.OBJECT) "\n    @Nullable" else ""}
    public ${type.raw} get() {
        return this.getImpl();
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override${if (type === Type.OBJECT) "\n    @Nullable" else ""}
    public final ${type.raw} set(${if (type === Type.OBJECT) "@Nullable " else ""}${type.raw} value) {
        if (this.binding != null) throw new IllegalStateException("A bound property's value may not be set explicitly");
        return this.setInternal(value);
    }
${if (type === Type.OBJECT) "\n    @Nullable" else ""}
    private ${type.raw} setInternal(${if (type === Type.OBJECT) "@Nullable " else ""}${type.raw} value) {
        ${type.raw} prev = this.getImpl();
        this.setImpl(value);
        this.notifyListeners(prev, value);

        return prev;
    }

    /**
     * TODO doc
     *
     * @since   0.1.0
     */${if (type === Type.OBJECT) "\n    @Nullable" else ""}
    protected abstract ${type.raw} getImpl();

    /**
     * TODO doc
     *
     * @since   0.1.0
     */
    protected abstract void setImpl(${if (type === Type.OBJECT) "@Nullable " else ""}${type.raw} value);

}"""
    }
}