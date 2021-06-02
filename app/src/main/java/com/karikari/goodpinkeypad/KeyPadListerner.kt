package com.karikari.goodpinkeypad

interface KeyPadListerner {
    fun onKeyPadPressed(value: String?)
    fun onKeyBackPressed()
    fun onClear()
}