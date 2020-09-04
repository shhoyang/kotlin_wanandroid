### 1 性能优化
##### 1.1 布局优化
- 使用include标签复用布局
- 使用merge替换父布局标签，减少层级
- 减少使用wrap_content，减少计算成本
- 删除空间无用属性
- 移除不必要背景
- gone代替invisible
- ConstraintLayout减少层级
- 工具：调试GPU过度绘制、 GPU呈现模式分析

##### 1.2 内存优化
- 内存泄漏，单例，静态变量，非静态内部类，IO，数据库的Cursor，未取消注册，动画，handle [参考](https://www.jianshu.com/p/72eef0f144a6)
- 内存溢出，图片色彩模式，压缩，bitmap回收
- 扩大内存，largeHeap="true"
- LeakCanary
- Android Lint
- 减少枚举
- SparseArray代替Map
- 强软弱虚
- onDraw不要创建对象，不要耗时任务
- 使用线程池

##### 1.3 网络优化
- 数据分页
- 缓存
- 上传图片压缩
- 根据网络情况返回不同清晰度的图片
- 连接复用 keep-alive
- 请求合并
- GZIP压缩

##### 1.4 交互优化
- 不要主线程IO
- 布局优化

##### 1.5 启动优化
- 异步或者延迟初始化

##### 1.6 刷新优化
- 减少刷新次数
- 减少刷新区域

##### 1.7 动画优化

##### 1.8 图片优化

##### 1.9 apk瘦身
- 代码混淆
- 资源优化，删除无用资源，Android Lint
- 图片优化，图片压缩，一套图片webp
- 删除无用依赖库
- cpu类型armeabi-v7a，减少so

### 2 Kotlin
##### 2.1 优势
- 智能转换类型
- 空判断
- data class
- when

##### 2.2 高级用法

- data class
- 解构声明
- 扩展函数、扩展属性
- Lazy / lateinit 延迟加载器
- Delegates 属性观察者
- let / apply / run / with
- inline 内联函数


### 2 启动流程

类名                             方法名                                 调用方法
LauncherActivity                onListItemClick                         Activity.startActivity
                                                                        Activity.startActivityForResult
Activity                        startActivityForResult                  mInstrumentation.execStartActivity
Instrumentation                 execStartActivity                       ActivityTaskManager.getService().startActivity  
ActivityTaskManagerService      startActivity->startActivityAsUser
ActivityStarter                 execute->startActivityMayWait->startActivity->startActivityUnchecked
RootActivityContainer           resumeFocusedStacksTopActivities



