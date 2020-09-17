### ZGC后台员工管理系统
---

#### 项目描述
> ZGC后台员工管理系统，基于SpringBoot + FreeMarker + Mybatis + shrio + redis + MySQL/Oracle等开发多模块化后台管理系统。本项目设计相对简单，具体业务逻辑与数据存储相对保守只用作学习。系统实现的功能模块有员工管理，部门管理，用户管理，角色管理，资源管理，文件上传，Druid数据库监控等。本项目初衷用于学习开发！

#### 运行预览
[![wDY559.gif](https://s1.ax1x.com/2020/09/14/wDY559.gif)](https://imgchr.com/i/wDY559)
___

#### 前/后开发技术栈
* 后台: SpringBoot + MyBatis + FreeMarker + Shiro + Redis等

* 前端: BootStrap + JQuery + zTree +  sweetAlert + Bootstrap-table等
---
#### 项目结构
![wDJpod.png](https://s1.ax1x.com/2020/09/14/wDJpod.png)

#### 数据库E-R关系图
[![wD4mxP.png](https://s1.ax1x.com/2020/09/14/wD4mxP.png)](https://imgchr.com/i/wD4mxP)

#### 功能列表
* 用户管理: 用于管理后台系统的用户，可进行增删改查等操作。
* 角色管理: 分配权限，通过角色给用户分配权限。
* 部门管理: 通过不同的部门来管理和区分用户。
* 员工管理: 用于管理员工个人信息，可进行增删改查等操作。
* 信息统计: 通过E-Charts统计员工与部门的信息。
* 资源管理: 用于管理用户可使用的资源，如菜单，数据导出等操作。
* 文件上传: 提供图片文件上传功能，可对图片与Base64的相互转换。
* 接口文档: 没有严格按照RESTFUL API设计，只是简单使用Swagger查看
---
#### 本地项目搭建教程

##### 1. 搭建系统开发环境
* JDK8+
* MySQL8.0 或 Oracle, 
* Redis
* Lombok

> 注1: 关于数据库选择与配置，具体可根据application.yml文件

> 注2: 关于redis配置，具体可根据application.yml文件

> 注3: 根据具体开发工具和系统安装Lombok，如本人: VSCode的话请在Marketplace查找Lombok，具体请参照Lombok官网

##### 2. 项目导入与运行

* 以VSCode为例

```
1. 打开vscode后，按下 `ctrl`(windows) / `cmd`(macos) + `shift` + p
2. git clone
3. 复制下面链接: https://github.com/EmirioBomb/zgc-ems.git
4. 选择`当前窗口打开`
5. 运行方法:
 5.1. 找到 zhaopin-admin目录下的ZhaopinAdminApplication.java运行
 或
 5.2. 按下`ctrl`(windows) / `cmd`(macos) + `，打开控制台输入命令: mvn spring-boot:run
6. 访问地址: http://locahost:8080
```

* 其他开发工具
```
1. 导入源代码或通过git导入项目
2. 找到ZhaopinAdminApplication.java运行即可
```
##### 3. 项目打包与运行
```text
1. 进入zgc-ems目录
2. 输入: mvn clean -DskipTests package
3. 结束后，进入到zhaopin-admin/target目录下，找到xxx.jar文件
4. 通过: java -jar xxx.jar运行项目即可
```

##### 4. 运行Release中的jar包
```text
1. mysql的密码设为root，redis的密码设为12345
2. 下载zgc-ems-v1.0.0.jar到本地
3. 通过: java -jar zgc-ems-v1.0.0.jar 运行项目
```
---

#### 运行预览

##### 1. 员工管理预览
[![wDcD1O.gif](https://s1.ax1x.com/2020/09/14/wDcD1O.gif)](https://imgchr.com/i/wDcD1O)

##### 2. 部门管理预览
![ws4OeK.gif](https://s1.ax1x.com/2020/09/15/ws4OeK.gif)

##### 3. 文档接口预览
[![wWZYBF.gif](https://s1.ax1x.com/2020/09/17/wWZYBF.gif)](https://imgchr.com/i/wWZYBF)

#### 总结
> 此项目对于基本的框架使用与开发有了不少的认识，接下来还需要增加很多功能，如用户分配，部门权限，部门资源等等。有待完善海量数据时的查询优化，提高部分功能的严谨性，比起Layui的简易开发性，Bootstrap在这方面不太友好，但在使用中感受到了更深的理解与流程，受益非浅。此项目为后续的新项目做好了基础，基于此项目的经验上会接着开发另一个比较流行且性能效率等会有显著提升的更完善的后台脚手架。To Be Continued ......