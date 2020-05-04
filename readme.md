## 目标

- kotlin-js 编写客户端网页实践
- gradle多模块在kotlin-js项目实践


## 准备动作

该演示项目路径如下

```text
kotlinjs-modules
  ├─app
  ├─base
  └─lib
```

顶层kotlinjs-modules含三个子模块：app,base,lib . 依赖关系是顶层依赖三个子模块，app依赖lib与base， lib依赖base

1. 目录创建

    ```sh
    $mkdir -p kotlinjs-modules/{app,lib,base}
    ```

2. 顶层项目gradle框架建立

```sh
$cd kotlinjs-modules
$gradle init --dsl groovy  --type basic
```

3. 替换build.gradle文件内容为

    ```groovy
    plugins {
        id 'org.jetbrains.kotlin.js' version '1.3.72'  apply  false
    }

    allprojects {
        repositories {
            jcenter()
        }
    }
    ```

4. 编辑settings.gradle，增加内容

    ```groovy
    include ':base'
    include ':app'
    include ':lib'
    ```

## 子模块项目框架建立

在base目录，lib目录，app目录下分步执行下面的指令，默认接受project.NAME

```sh
base/$gradle init --dsl groovy --package com.example.base --test-framework kotlintest  --type kotlin-library
lib/$gradle init --dsl groovy --package com.example.lib --test-framework kotlintest  --type kotlin-library
app/$gradle init --dsl groovy --package com.example.app --test-framework kotlintest  --type kotlin-library
```

将base,app,lib这三个项目的`build.gradle`文件内容替换为：

```groovy
plugins {
    id 'org.jetbrains.kotlin.js' 
}
repositories {
}

dependencies {
    implementation "org.jetbrains.kotlin:kotlin-stdlib-js"
    testImplementation "org.jetbrains.kotlin:kotlin-test-js"

    implementation  "org.jetbrains.kotlinx:kotlinx-html-js:0.7.1"
}

kotlin.target.browser { }
compileKotlinJs.kotlinOptions.moduleKind = "commonjs"
```

## 建立子模块间依赖关系

三个子模块间依赖关系是：app依赖lib与base， lib依赖base

因此，需要进行如下修改：

1. 对app/build.gradle,增加下面内容

    ```groovy
    dependencies {
        ...
        implementation project(":lib")
        implementation project(":base")
    }
    ```

2. 对app/settings.gradle，增加下面内容

    ```groovy
    include ':lib'
    project(':lib').projectDir = new File(settingsDir, '../lib')
    include ':base'
    project(':base').projectDir = new File(settingsDir, '../base')
    ```

3. 对lib/build.gradle,增加下面内容

    ```groovy
    dependencies {
        ...
        implementation project(":base")
    }
    ```

4. 对lib/settings.gradle，增加下面内容

    ```groovy
    include ':base'
    project(':base').projectDir = new File(settingsDir, '../base')
    ```

最后，尝试执行构建，验证多项目构建成功

```sh
$./gradlew clean build

> Configure project :
Repository https://jcenter.bintray.com/ replaced by http://maven.aliyun.com/nexus/content/groups/public/.
Repository https://jcenter.bintray.com/ replaced by http://maven.aliyun.com/nexus/content/groups/public/.
Repository https://jcenter.bintray.com/ replaced by http://maven.aliyun.com/nexus/content/groups/public/.
Repository https://jcenter.bintray.com/ replaced by http://maven.aliyun.com/nexus/content/groups/public/.

> Task :base:packageJson
...

> Task :app:testPackageJson
...

> Task :kotlinNpmInstall
...
...
BUILD SUCCESSFUL in 1m 54s
41 actionable tasks: 35 executed, 6 up-to-date

tu_xu@desktop MINGW64 /c/home/mydemo/kotlinjs-modules
$
```

## 实践

实践过程中通过`./gradlew run -t` 查看持续集成的结果

### 演示js调用kotlin代码

见 “app\src\main\resources\index.html” 中代码片段

```html
...
</body>

<script src="app.js"></script>
<script>
    //演示js中调用kotlin，采用“<moduleName>. fully-qualified names”
    console.log(app.com.example.app.sayHello())
    app.com.example.app.fillEmail()
    app.com.example.app.replaceDiv("root")  
</script>
...
```

以及“app\src\main\kotlin\com\example\app\AppDemo.kt” 中代码片段

```kotlin
//见[Calling Kotlin from JavaScript](https://kotlinlang.org/docs/reference/js-to-kotlin-interop.html)
//说明，需要在js中调用的kotlin代码使用@JsName声明下，从实践看，如果不采取该措施，有时候会有问题，即名称不一定

@JsName("sayHello")
fun sayHello() {
    com.example.base.sayHello("新手")
}
...
```

### 演示操纵HTML element

见"base\src\main\kotlin\com\example\base\baseDemo.kt"中代码片段

```kotlin
...
fun replaceDiv(divName: String = "root") {
    var root = document.getElementById(divName)
    // create a div tag which contains xxx
    var div = document.create.div {
        h1 {
            +"h1-hello javascript"
...
```

见“lib\src\main\kotlin\com\example\lib\LibDemo.kt”中代码片段

```kotlin
...
    fun fillEmail() {
        val email = document.getElementById("email") as HTMLInputElement;
        email.value = "dummy@jetbrains.com"
...
```

## TODO

1. module
2. 