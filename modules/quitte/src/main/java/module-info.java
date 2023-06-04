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
/** Defines APIs for observable properties and expressions, and observable collections. */
@SuppressWarnings("module")
module com.osmerion.quitte {

    /*
     * With Gradle, modules are compiled separately. Thus, quitte.i18n (etc.) are unknown during compilation of this
     * module and warnings are logged accordingly. Since this is expected, we just suppress all "module" related
     * warnings for the descriptor.
     */

    requires static jsr305;

    exports com.osmerion.quitte;
    exports com.osmerion.quitte.expression;
    exports com.osmerion.quitte.collections;
    exports com.osmerion.quitte.functional;
    exports com.osmerion.quitte.property;
    exports com.osmerion.quitte.value;
    exports com.osmerion.quitte.value.change;

    exports com.osmerion.quitte.internal to
//        com.osmerion.quitte.holo,
        com.osmerion.quitte.i18n;

//    exports com.osmerion.quitte.internal.addon to com.osmerion.quitte.holo;

}