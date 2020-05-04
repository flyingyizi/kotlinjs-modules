package com.example.app

@JsName("sayHello")
fun sayHello() {
    com.example.base.sayHello("新手")
}

@JsName("fillEmail")
fun  fillEmail() {
    val d = com.example.lib.LibDemo()
    d.fillEmail()
}

@JsName("replaceDiv")
fun replaceDiv(divName: String = "root") {
    com.example.base.replaceDiv(divName)
}