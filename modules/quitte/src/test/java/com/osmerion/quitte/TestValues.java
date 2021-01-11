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
package com.osmerion.quitte;

public final class TestValues {

    public static final boolean BoolValue_N = false;
    public static final boolean BoolValue_L = false;
    public static final boolean BoolValue_H = true;

    public static final byte ByteValue_N = 0;
    public static final byte ByteValue_L = Byte.MIN_VALUE;
    public static final byte ByteValue_H = Byte.MAX_VALUE;

    public static final short ShortValue_N = 0;
    public static final short ShortValue_L = Short.MIN_VALUE;
    public static final short ShortValue_H = Short.MAX_VALUE;

    public static final int IntValue_N = 0;
    public static final int IntValue_L = Integer.MIN_VALUE;
    public static final int IntValue_H = Integer.MAX_VALUE;

    public static final long LongValue_N = 0L;
    public static final long LongValue_L = Long.MIN_VALUE;
    public static final long LongValue_H = Long.MAX_VALUE;

    public static final float FloatValue_N = 0.0F;
    public static final float FloatValue_L = Float.MIN_VALUE;
    public static final float FloatValue_H = Float.MAX_VALUE;

    public static final double DoubleValue_N = 0.0D;
    public static final double DoubleValue_L = Double.MIN_VALUE;
    public static final double DoubleValue_H = Double.MAX_VALUE;

    public static final Object ObjectValue_N = null;
    public static final Object ObjectValue_L = "LOW";
    public static final Object ObjectValue_H = "HIGH";

}