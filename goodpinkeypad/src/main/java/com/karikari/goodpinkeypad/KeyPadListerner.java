package com.karikari.goodpinkeypad;

public interface KeyPadListerner {
    void onKeyPadPressed(String value);

    void onKeyBackPressed();

    void onClear();
}
