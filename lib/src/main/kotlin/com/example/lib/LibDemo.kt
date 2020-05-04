package com.example.lib

import org.w3c.dom.HTMLInputElement
import kotlin.browser.document
import kotlinx.html.*

class LibDemo {
    fun sayHello(): String {
        return "你好 from com.example.lib"
    }

    fun fillEmail() {
        val email = document.getElementById("email") as HTMLInputElement;
        email.value = "dummy@jetbrains.com"
    }
}