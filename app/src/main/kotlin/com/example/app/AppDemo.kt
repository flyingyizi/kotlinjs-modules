package com.example.app

//见[Calling Kotlin from JavaScript](https://kotlinlang.org/docs/reference/js-to-kotlin-interop.html)
//说明，需要在js中调用的kotlin代码使用@JsName声明下，从实践看，如果不采取该措施，有时候会有问题，即名称不一定

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