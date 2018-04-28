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

    template("${packageName.replace('.', '/')}/Writable${type.abbrevName}Property") {
        """package $packageName;

import com.github.osmerion.quitte.value.*;

/**
 * ${if (type === Type.OBJECT)
            "A generic writable property."
        else
            "A specialized writable {@code ${type.raw}} property."
        }
 *
 * @since   0.1.0
 *
 * @author  Leon Linhart
 */
public interface Writable${type.abbrevName}Property$typeParams extends WritableProperty<${type.box}>, Readable${type.abbrevName}Property$typeParams, Writable${type.abbrevName}Value$typeParams {

    /**
     * Binds this property to the given observable value.
     *
     * <p>While a property is bound, its value will be equal to the observable value. Any attempt to set the value of a
     * bound property explicitly will fail. A bound property may be unbound by calling {@link #unbind()}.</p>
     *
     * @param observable    the observable to bind this property to
     *
     * @since   0.1.0
     */
    void bind(Observable${type.abbrevName}Value$typeParams observable);

}"""
    }
}