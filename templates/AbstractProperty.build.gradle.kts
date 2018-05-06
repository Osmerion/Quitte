/*
 * Copyright (c) 2018 Leon Linhart,
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

    private final CopyOnWriteArraySet<${type.abbrevName}ChangeListener$typeParams> changeListeners = new CopyOnWriteArraySet<>();
    private Binding binding;

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final synchronized void bind(ObservableValue<${type.box}> observable) {
        if (this.binding != null) throw new IllegalStateException();

        this.binding = new GenericBinding<>(this, observable);
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final synchronized void bind(Observable${type.abbrevName}Value$typeParams observable) {
        if (this.binding != null) throw new IllegalStateException();

        this.binding = new BindingImpl${if (type === Type.OBJECT) "<>" else ""}(this, observable);
    }

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
    public final boolean addListener(ChangeListener<${type.box}> listener) {
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
    public final boolean removeListener(ChangeListener<${type.box}> listener) {
        //noinspection SuspiciousMethodCalls
        return this.changeListeners.remove(listener);
    }

    private static final class BindingImpl$typeParams implements Binding {

        private Observable${type.abbrevName}Value$typeParams boundTo;
        private ${type.abbrevName}ChangeListener$typeParams bindingListener;

        private BindingImpl(Writable${type.abbrevName}Property$typeParams property, Observable${type.abbrevName}Value$typeParams observable) {
            this.boundTo = observable;
            property.set(observable.get());
            observable.addListener(this.bindingListener = (o, oldValue, newValue) -> property.setValue(newValue));
        }

        @Override
        public void release() {
            this.boundTo.removeListener(this.bindingListener);
        }

    }

}"""
    }
}