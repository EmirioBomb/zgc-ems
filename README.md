### ZGC后台员工管理系统
---

#### 项目描述
> ZGC后台员工管理系统，基于SpringBoot + FreeMarker + Mybatis + shrio + redis + MySQL/Oracle等开发的后台管理系统。本项目设计相对简单，具体业务逻辑与数据存储相对保守只用作学习。系统实现的功能模块有员工管理，部门管理，用户管理，角色管理，资源管理，文件上传，Druid数据库监控等。本项目仅用于开发学习！

#### 前/后开发技术栈
后台: SpringBoot + MyBatis + FreeMarker + Shiro + Redis等
前端: BootStrap + JQuery + zTree +  sweetAlert

#### 功能列表
用户管理：用于管理后台系统的用户，可进行增删改查等操作。
角色管理：分配权限，通过角色给用户分配权限。
部门管理：通过不同的部门来管理和区分用户。
员工管理：用于管理员工个人信息，可进行增删改查等操作。
资源管理：用于管理用户可使用的资源，如菜单，数据导出等操作。
文件上传：提供图片文件上传功能，可对图片与Base64的相互转换。

#### 本地项目搭建教程
1. 搭建开发环境
* JDK11
* MySQL8.0 或 Oracle, 
* Redis
* Lombok，

> 注1: 关于数据库选择与配置，具体可根据application.yml文件
> 注2: 关于redis配置，具体可根据application.yml文件
> 注2: 根据具体开发工具和系统安装Lombok，如本人: VSCode的话请在Marketplace查找Lombok，具体请参照Lombok官网

2. 项目导入与运行

* 以VSCode为例

```
1. 打开vscode后，按下 `ctrl`(windows) / `cmd`(macos) + `shift` + p
2. git clone
3. 复制下面链接: https://github.com/EmirioBomb/zgc-ems.git
4. 选择`当前窗口打开`
5. 运行方法:
 1. 找到 zhaopin-admin目录下的ZhaopinAdminApplication.java运行
 或
 2. 按下`ctrl`(windows) / `cmd`(macos) + `，打开控制台输入命令: mvn spring-boot:run
6. 访问地址: http://locahost:8080
```

* 其他开发工具
```
1. 导入源代码或通过git导入项目
2. 找到ZhaopinAdminApplication.java运行即可
```

3. 