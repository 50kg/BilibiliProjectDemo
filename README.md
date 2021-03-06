# Bilibili直播播放器 MVP+Retrofit+RxJava
- 直播接口使用Blibili客户端/游戏使用熊猫TV
- 如接口侵权请私信删除
---
# 介绍
- 该APP使用MVP框架，Retrofit网络请求，RxJava分发框架
- 面设计使用的是 Material Design，遵循 Google 设计规范
- 使用DrawerLayout+NavigationView侧滑显示
- 适配API19~24沉浸式状态栏，对国产ROM轻度适配，对DrawerLayout侧滑栏进行了优化
- 使用CoordinatorLayout/CpllapsingToolbarTayoutUI响应
- 适配边缘滑动返回上级
- 使用RecyclerView，通过GSON解析生成javabean，使用解耦式多布局适配器进行填充数据，实现了上拉加载，下拉刷新，Item加载动画等
- 使用广播进行WIFI/数据判断进行本地缓存策略
- 图片使用Glide图片加载器完成
- 直播库播放使用ijkPlaye，弹幕使DanmakuFlameMaster开源库，可实现在线观看直播，实时发送弹幕，重力感应切换方向，锁定屏幕，截图，设置屏幕比例等
- 自定义View圆形头像
- 使用Butterknife插件实现快速init
- 等其他技术


# 界面预览

 <img src="https://github.com/50kg/BilibiliProjectDemo/blob/master/images/gif1.gif" width = "297" height = "482"/>
 <img src="https://github.com/50kg/BilibiliProjectDemo/blob/master/images/gif2.gif" width = "297" height = "482" />
 <img src="https://github.com/50kg/BilibiliProjectDemo/blob/master/images/img1.png" width = "297" height = "482" />
 <img src="https://github.com/50kg/BilibiliProjectDemo/blob/master/images/img2.png" width = "297" height = "482" />
 <img src="https://github.com/50kg/BilibiliProjectDemo/blob/master/images/img3.png" width = "297" height = "482" />
 <img src="https://github.com/50kg/BilibiliProjectDemo/blob/master/images/img6.png" width = "297" height = "482" />
 <img src="https://github.com/50kg/BilibiliProjectDemo/blob/master/images/img7.png" width = "297" height = "482" />

 <img src="https://github.com/50kg/BilibiliProjectDemo/blob/master/images/img4.png" width = "582" height = "397" />
 <img src="https://github.com/50kg/BilibiliProjectDemo/blob/master/images/img5.png" width = "582" height = "397" />
# 致谢
感谢您花时间阅读我的Project，如果对你有所帮助，期待你的Start
