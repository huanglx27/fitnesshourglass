# fitnesshourglass 健身沙漏
` 8.9更新 问题解决，在CountdownTimer中使用handler来实现start()等方法，doInBackground没Looper.prepare()会报错。目前已经开始写无AsyncTask版本的fitnesshourglass，因此这个坑不填了，另开一坑` <Br/><Br/>
`doInBackgroung中没用handler，但是提示Can't create handler inside thread Thread[AsyncTask #1,5,main] that has not called Looper.prepare()。无法解决，打算弃坑重新写`

## 前言
六月份看完《JAVA核心技术I》，打算用Android来加深对JAVA的理解。七月份看完《第一行代码》，打算实战来巩固和提升。
## 需求
作为一个健身爱好者，需要对组间休息来进行计时和提醒。手机上应用需简洁、无多余功能但能满足需要。现在用的魅族自带时钟，有倒计时功能，较为简洁，但缺少一个对组内次数进行计数的功能。因此打算自己搞一个，能计数的倒计时钟。
## 更新计划
1.实现滑动定时的功能；<Br/>
2.建立定时页面完整的布局；<Br/>
3.实现倒计时的功能；<Br/>
4.完善整体布局；<Br/>
5.实现服务和通知；<Br/>
6.实现定时列表的无限滚动和震动；<Br/>
7.全屏提醒；<Br/>
7.滑动时的美化；<Br/>
8.A屏版；<Br/>
9.hiit版<Br/>
10.沙漏动画；<Br/>
11.前台无通知栏，后台有通知栏；<Br/>
12.提示音。<Br/>
