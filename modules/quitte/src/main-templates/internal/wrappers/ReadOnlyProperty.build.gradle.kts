/*
 * Copyright (c) 2018-2021 Leon Linhart,
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
val packageName = "com.osmerion.quitte.internal.wrappers"

Type.values().forEach {
    val type = it
    val typeParams = if (type === Type.OBJECT) "<T>" else ""

    template("${packageName.replace('.', '/')}/ReadOnly${type.abbrevName}Property") {
        """package $packageName;

import com.osmerion.quitte.*;
import com.osmerion.quitte.property.*;
import com.osmerion.quitte.value.change.*;

/**
 * ${if (type === Type.OBJECT)
            "A generic read-only property."
        else
            "A specialized read-only {@code ${type.raw}} property."
        }
 *
 * @author  Leon Linhart
 */
public final class ReadOnly${type.abbrevName}Property$typeParams implements Readable${type.abbrevName}Property$typeParams {

    protected final Readable${type.abbrevName}Property$typeParams property;

    public ReadOnly${type.abbrevName}Property(Readable${type.abbrevName}Property$typeParams property) {
        this.property = property;
    }

    @Override
    public Readable${type.abbrevName}Property$typeParams asReadOnlyProperty() {
        return this;
    }

    @Override
    public ${type.raw} get() {
        return this.property.get();
    }

	@Override
    public ${type.box} getValue() {
        return this.property.getValue();
    }

	@Override
    public boolean isBound() {
        return this.property.isBound();
    }

    @Override
    public boolean isWritable() {
        return false;
    }

    @Override
    public boolean addChangeListener(${type.abbrevName}ChangeListener$typeParams listener) {
        return this.property.addChangeListener(listener);
    }

	@Override
    public boolean addBoxedChangeListener(ChangeListener<${type.box}> listener) {
        return this.property.addBoxedChangeListener(listener);
    }

    @Override
    public boolean removeChangeListener(${type.abbrevName}ChangeListener$typeParams listener) {
        return this.property.removeChangeListener(listener);
    }

    @Override
    public boolean removeBoxedChangeListener(ChangeListener<${type.box}> listener) {
        return this.property.removeBoxedChangeListener(listener);
    }

    @Override
    public final boolean addInvalidationListener(InvalidationListener listener) {
        return this.property.addInvalidationListener(listener);
    }

    @Override
    public final boolean removeInvalidationListener(InvalidationListener listener) {
        return this.property.removeInvalidationListener(listener);
    }

}"""
    }
}