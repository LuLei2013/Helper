 prerequisite

安装了maven工具,JDK
在线编译该工程。
当工程依赖的jar包下载后，设置工程的jar工程classpath
目录变量名为M2_REPO


使用步骤：

1.clone 该工程带本地，然后执行
	mvn clean
	mvn eclipse  clean
	mvn eclipse:eclipse
	
2.打开eclipse，导入该工程
3，打开连连看游戏,任何连连看游戏都可以（最好是图标易于分辨的，
	 不然可能图像识别错误，影响后面的程序进行）
4，使用截图工具，把连连看的界面接下来，保存到工程目录下
	 命名为：capture.png
5,   Ctrl+ F11，运行该助手程序（MainTest）
6，接下来，静等连连看图标被消除


这个是测试用的连连看web版的
http://www.4399.com/flash/17801.htm