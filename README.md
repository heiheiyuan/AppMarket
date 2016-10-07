# AppMarket
Like the Google Play Market...of course Google Play Market is stronger than my app-_-||

  技术要点：
  
    1.界面搭建使用ViewPager+Fragment实现，DrawerLayout和ActionBar联动使用
    
    2.Fragment、Adapter、ViewHolder基类抽取和工厂设计模式加载不同的Fragment界面
    
    3.ListView优化之分页加载和首页无限轮播图的实现
    
    4.排行界面动态生成随机颜色的圆角矩形Drawable，在代码中设置Drawable的背景选择器，重写onMeasure，onLayout方法计算子控件合适的位置
    
    5.使用线程池下载应用安装包，观察者模式更新下载进度，实现多线程断点续传

