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
val packageName = "com.github.osmerion.quitte.value.change"

Type.values().forEach {
    val type = it
    val typeParams = if (type === Type.OBJECT) "<T>" else ""
    val valAnno = if (type === Type.OBJECT) "@Nullable " else ""

    template("${packageName.replace('.', '/')}/${type.abbrevName}ChangeListener") {
        """package $packageName;${if (type === Type.OBJECT) "\n\nimport javax.annotation.Nullable;" else ""}

import com.github.osmerion.quitte.internal.wrappers.*;
import com.github.osmerion.quitte.value.*;

/**
 * ${if (type === Type.OBJECT)
            "A generic change-listener."
        else
            "A specialized {@code ${type.raw}} change-listener."
        }
 *
 * @see ObservableValue
 * @see Observable${type.abbrevName}Value
 *
 * @since   0.1.0
 *
 * @author  Leon Linhart
 */
@FunctionalInterface
public interface ${type.abbrevName}ChangeListener$typeParams {

    /**
     * Processes a value change of an {@link Observable${type.abbrevName}Value} this listener is attached to.
     *
     * @param observable    the observable value that has changed
     * @param oldValue      the old value
     * @param newValue      the new value
     *
     * @since   0.1.0
     */
    void onChanged(Observable${type.abbrevName}Value$typeParams observable, $valAnno${type.raw} oldValue, $valAnno${type.raw} newValue);

    /**
     * Wraps the given listener into a specialized one that is {@link Object#equals(Object) equal} to the given one and
     * shares a hashcode with it.
     *
     * @param listener  the listener to be wrapped
     *
     * @return  the wrapper
     *
     * @since   0.1.0
     */
    static $typeParams ${type.abbrevName}ChangeListener$typeParams wrap(ChangeListener<${type.box}> listener) {
        return new Wrapping${type.abbrevName}ChangeListener$typeParams(listener);
    }

}"""
    }
}