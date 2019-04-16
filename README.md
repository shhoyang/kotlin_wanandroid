自Google在2017的I/O大会上宣布Android支持Kotlin，到现在Kotlin语言已受到大批Android开发者的追捧，许多大厂已经使用纯Kotlin进行Android开发

2018的I/O大会又发布可跨平台的UI框架Flutter ，支持在Android和iOS平台运行

**这还没完没了了...**

大势所趋吧，不得不学...  

# 项目简介

**上图**，把四张拼一起了

![](https://upload-images.jianshu.io/upload_images/12337722-b9f7977f8d43c68a.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

- 基于Jetpack(包含livedata、lifecycle、paging、room)组件采用 MVVM架构，Jetpack组件的使用方法在这里不做说明
- 组件化开发，module之间跳转使用阿里的ARouter路由框架
- 数据来源于[hongyang玩Android开放API](http://www.wanandroid.com/blog/show/2)，聚集了鸿洋、郭霖等诸多大神的公众号文章和高质量的开源项目推荐介绍
- 你不妨把这个app当做你的掌上开源社区使用

# 项目使用到的开源库

- [kolint工具库anko](https://github.com/Kotlin/anko)
- [网络请求okhttp](https://github.com/square/okhttp)
- [网络请求Retrofit](https://github.com/square/retrofit)
- [异步框架Rxjava2](https://github.com/ReactiveX/RxJava)
- [依赖注入Dagger2](https://github.com/google/dagger)
- [阿里路由框架ARouter](https://github.com/alibaba/ARouter)
- [图片加载Glide](https://github.com/bumptech/glide)
- [日志打印KLog](https://github.com/ZhaoKaiQiang/KLog)
- [可翻页的RecyclerView](https://github.com/GcsSloop/pager-layoutmanager)
- [侧滑删除SwipeDelMenuLayout](https://github.com/mcxtzhang/SwipeDelMenuLayout)
- [流式布局FlowLayout](https://github.com/xiangcman/LayoutManager-FlowLayout)
- [轮播图控件banner](https://github.com/youth5201314/banner)

# 项目架构解析

## 项目分为四个module

app，App的壳
module_base，公共库
module_user、module_wan，业务模块

![](https://upload-images.jianshu.io/upload_images/12337722-f212f3574b2fc83f.jpeg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

既然是组件化开发，那各个业务模块肯定可以单独运行

```
//根据变量isModule判断是作为依赖library还是单独的application
if (isModule) {
  apply plugin: 'com.android.library'
} else {
  apply plugin: 'com.android.application'
}

sourceSets {
  main {
    //根据变量isModule使用不同文件夹的代码
    if (isModule) {
      manifest.srcFile 'src/main/AndroidManifest.xml'
      java {
        //去除指定文件夹代码
        exclude '*module'
      }
    } else {
      //这里面只用一个Activity，充当壳Activity
      java.srcDirs 'src/main/java', 'src/main/module/java'
      manifest.srcFile 'src/main/module/AndroidManifest.xml'
     }
  }
}
```

## app

它是app的壳，里面只有一个MainActivity，它负责整合所需要的业务模块

![](https://upload-images.jianshu.io/upload_images/12337722-12cfd686a8aa63fb.jpeg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

```
dependencies {
  implementation fileTree(include: ['*.jar'], dir: 'libs')
  implementation project(':module_base')
  kapt kaptLibs
  if (isModule) {
    //依赖业务module
    implementation project(':module_user')
    implementation project(':module_wan')
  }
}
```

## module_base

作为公共库，存放base和公用代码，其它的module都要依赖它

![](https://upload-images.jianshu.io/upload_images/12337722-94f4b61d6793d549.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## module_wan和module_user

主要的业务

![](https://upload-images.jianshu.io/upload_images/12337722-1fd4d92155442e3e.jpeg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

# Kotlin的一些特性在项目中的运用

## 扩展函数

```
//给ImageView添加一个扩展函数，就当做ImageView有了一个load方法
//ImageView作为接受者类型，只有ImageView和其子类能调用该方法
fun ImageView.load(url: Any) {
  //this代表接受这对象
  Glide.with(this).load(url).into(this)
}

var imageView: ImageView = ...
//直接使用ImageView对象调用load方法
imageView.load(url)
```

以前使用工具类必须要传一个类型为ImageView的参数，现在直接用ImageView调用扩展方法是不是简单了很多

## 空判断

```
override fun setTitle(title: CharSequence?) {  //这个？表示该变量可以为null
  //? 表示如果toolbar不为空才执行后面的代码，给其属性title赋值
  toolbar?.title = title
}
```

想想以前代码里面到处都是 if(... !=  null)类似的模板代码都阵痛

## 函数默认参数

```
fun <VH : RecyclerView.ViewHolder, A : Adapter<VH>> RecyclerView.init(adapter: A, 
    //layoutManager有一个默认值LinearLayoutManager(context)
    layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context)) {
  this.layoutManager = layoutManager
  this.adapter = adapter
}

//只传了一个参数，layoutManager使用默认值
 recyclerView.init(adapter)

//传了两个参数
 flowLayout.init(flowAdapter, FlowLayoutManager())
```

如果函数有多个参数默认的时候，我们只想某几个参数使用其默认值，为了不引起混乱，我们可以根据参数名传参，随便写个Demo说明一下

```
fun main() {
    //调用函数，如果不写 c = 默认是b = 
    test(1, c = 10)
}

fun test(a: Int, b: Int = 2, c: Int = 3, d: Int = 4) {
  ...
}
```

## 函数式编程

一个函数作为另一个函数的参数传递

```
fun <D, T : HttpResult<D>> Observable<T>.subscribeBy(onResponse: (D?) -> Unit, onFailure: (String) -> Unit) =
        subscribe({
            if (it.errorCode == 0) {
                //函数调用
                onResponse(it.data)
            } else {
                //函数调用
                onFailure(it.errorMsg)
            }
        }, {
            onFailure(it.message!!)
        })
```

onResponse参数接收一个参数类型为D,无返回值的方法；onFailure参数接收一个参数类型为String，无返回值的方法。如果定义一个函数无返回值，那么它的返回值就是Unit，不要问我为什么，这就是语法，约定俗成

```
Api.getKnowledgeArticle(page - 1, typeId).io_main().subscribeBy(
  { onResponse(it?.datas) },   //第一个参数，it就是类型为D的参数
  { onResponse(null)
    Log.d(TAG，it)
  }    //第二个参数，it就是类型为String的参数
).add()
```

是不是很懵逼，因为你可能不懂lambda表达式的使用

---
关于Kotlin的特性还用很多，例如when表达式、属性委托、字符串模板、类型自动转换等等，这里就不一一讲解了，讲不完的，现在不熬夜

# 进阶之路

*因每个人学习方法不同，仅供参考*

1. 先在 [Kotlin 语言中文站 ](https://www.kotlincn.net/docs/reference/)跟着教程敲一遍，语法你总得先基本掌握吧
2. 多看几个star很多的Kotlin开源项目，最好完全敲一遍，里面还会很有很多你在官网上可能没看到的东西
大神们的代码也会让你有很多意想不到的收获，起码告诉你，你的代码Low爆了，会让你有种从此回家带孩子吧的冲动

**项目中使用到了鸿洋的玩Android开放Api和很多开源库，在此向所有对开源做出过贡献的各位大神表示感谢，正是有你们的开源共享，我们这些菜鸟的技术才能有所提升，也是你们用最美的语言，推动了科技的进步**

**Apk玩Android下载地址** [https://haoshi.co/wanandroid.apk](https://haoshi.co/wanandroid.apk)

![扫码下载](https://upload-images.jianshu.io/upload_images/12337722-ac489d01613e3fa2.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


**End**
