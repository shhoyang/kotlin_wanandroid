### Activity启动流程步骤

##### 1.LauncherActivity

*桌面*

onListItemClick

startActivity

startActivityForResult

mInstrumentation.execStartActivity

##### 2.Instrumentation

execStartActivity

ActivityTaskManager.getService().startActivity

##### 3.ActivityTaskManagerService

startActivity

startActivityAsUser

getActivityStartController().obtainStarter().execute

##### 4.ActivityStarter

*处理Intent和flag，选择启动的task，复用Activity。构建ActivityRecord，选择或者创建TaskRecord和ActivityStack*

execute

startActivityMayWait

startActivity

startActivityUnchecked

mRootActivityContainer.resumeFocusedStacksTopActivities

##### 5.RootActivityContainer

resumeFocusedStacksTopActivities

focusedStack.resumeTopActivityUncheckedLocked

##### 6.ActivityStack

resumeTopActivityUncheckedLocked

resumeTopActivityInnerLocked

mStackSupervisor.startSpecificActivityLocked

##### 7.ActivityStackSupervisor

*负责管理ActivityStack*

startSpecificActivityLocked

realStartActivityLocked

mService.getLifecycleManager().scheduleTransaction

##### 8.ClientLifecycleManager

scheduleTransaction

transaction.schedule

##### 9.ClientTransaction

schedule

mClient.scheduleTransaction

##### 10.ApplicationThread

*ActivityThread的内部类*

scheduleTransaction

ActivityThread.this.scheduleTransaction，*调用ActivityThread父类ClientTransactionHandler的scheduleTransaction*

##### 11.ClientTransactionHandler

*ActivityThread的父类*

scheduleTransaction

sendMessage

##### 12.ActivityThread

sendMessage

mH.sendMessage(msg)，Handle发送消息

##### 13.H，

*ActivityThread的内部类，Handle的子类*

handleMessage

mTransactionExecutor.execute(transaction)

##### 14.TransactionExecutor

execute

executeCallbacks

item.execute

##### 14.LauncherActivityItem

execute

client.handleLaunchActivity

##### 15.ActivityThread

handleLaunchActivity

performLaunchActivity