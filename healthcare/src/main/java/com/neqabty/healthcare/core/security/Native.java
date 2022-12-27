package com.neqabty.healthcare.core.security;

class Native {

    static {
        System.loadLibrary("native-lib");
        System.loadLibrary("vvb2060");
    }

    static native boolean isMagiskPresentNative();

}