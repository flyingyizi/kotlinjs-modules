package com.example.base

import kotlin.browser.document
import kotlinx.html.*
import kotlinx.html.dom.create
import kotlinx.html.js.onClickFunction

fun replaceDiv(divName: String = "root") {
    var root = document.getElementById(divName)
    // create a div tag which contains xxx
    var div = document.create.div {
        h1 {
            +"h1-hello javascript"
        }
        h2 {
            +"h2-hello javascript"
        }
        button(classes = "btn") {
            +"click me"
            onClickFunction = { println("clicked!") }
        }
    }
    root!!.appendChild(div)
}

fun sayHello(name :String): String {
    return "你好" + name + "from com.example.base"
}
