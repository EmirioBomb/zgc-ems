<#include "layout/header.ftl"/>

<#--  if row doesn't contain whole page content, footer will be disgusting  -->
<div class="row">
    <div class="col-md-4 col-sm-4 col-xs-4">
        <div class="panel panel-default">
            <div class="panel-heading">项目介绍/Project Introduction</div>
                <div class="panel-body">
                    <p style="font-color: red">
                        中关村招聘后台管理系统，是基于SpringBoot 2.0.1框架搭建起的简单的系统。采用模块化结构设计便于开发与维护，主要的功能有：用户管理，员工管理，角色管理，部门管理，资源管理等。
                    </p>
                </div>
            </div>

            <div class="panel panel-default">
                <div class="panel-heading">技术栈/List of Technologies</div>
                    <div class="panel-body">
                        <li>Front-End：Bootstrap, JQuery, zTree, sweetalert, sweetalert2, bootstrap-table, echart等</li>
                            
                        <li>Back-End：Springboot + mybatis + Shiro + Redis</li>
                        <li>Database: Mysql8.0 + Oracle 11</li>
                        <li>Dev-Tool：Visual Studio Code</li>
                    </div>
                </div>

            <div class="panel panel-default">
                <div class="panel-heading">开发人员/Team Members</div>
                    <div class="panel-body">
                        <li>Coder：Jinlong Wang(Main Developer)</li>                       
                    </div>
                </div>


            <div class="panel panel-default">
                <div class="panel-heading">功能列表/Features</div>
                    <div class="panel-body">
                        <li>用户管理：用来管理后台管理的用户，可进行增删改查操作</li>    
                        <li>部门管理：用来管理用户人员所在的部门，可进行增删改查操作</li>
                        <li>部门树形：用于部门的增删改操作</li>
                        <li>员工管理：用于员工的增删改操作</li>
                        <li>信息统计：统计有关员工各种信息</li>
                        <li>角色管理：用于分配角色或权限</li>
                        <li>资源管理：用于分配系统资源</li>
                        <li>搜索功能：通过名称查询数据</li>
                    </div>
                </div>
        </div>

    <div class="col-md-4 col-sm-4 col-xs-4">
        <div class="panel panel-default">
            <div class="panel-heading">待解决/已解决的问题或Bugs</div>
                <div class="panel-body">
                    <li>Todo List</li>
                    <ol>
                        <li>员工列表的访问速度问题，后台分页处理有待改善，提高少量数据时只显示用户所需要显示的数据，只显示页数/首页数据，点击页数显示该页的数据</li> 
                        <li>左侧菜单如果按钮的父级菜单为菜单，则会显示在菜单栏上，改为父级0或其他就不会显示</li> 
                        <li>上传文件中，打开图片添加URL的方法</li> 
                        <li>SysEmployee的Date类型转成LocalDate，在添加新员工时不能正确解析日期格式，会报错</li> 
                        <li>左侧菜单，连续过多点击下拉菜单会不停执行到指令结束</li> 
                        <li>ExcelUtils需完善，对错误文件进行更多的判断</li> 
                        <li>部门层级以树状显示，禁止以数字修改或添加</li> 
                        <li>增加部门与员工关系表，部门拥有的权限同步于该部门下的所有员工</li> 
                        <li>下载文件</li> 
                        <li>上传文件，增加种类，可添加其他文件类型</li> 
                        <li>导出更多不同格式的文件，需要进一步修改ExcelUtils工具类</li> 
                        <li>验证码功能未实现</li> 
                        <li>persistence.beans.Sysuser序列化错误，stream classdesc serialVersionUID != local class serialVersionUID</li> 

                        <#--  @1.12  -->
                        <li><b style="color:red">@v1.11 fixed</b> <s>修改控制台无法输出mybatis的sql处理语句,在mytatis-config.xml中添加settings->logImpl</s></li> 

                        <#--  @1.11  -->
                        <li><b style="color:red">@v1.11 fixed</b> <s>部门树上修改名称时判断空格，否则可以添加相同部门名</s></li> 
                        <li><b style="color:red">@v1.11 fixed</b> <s>在oracle中concat只能连接两个字段，修改mapper.xml中所有like concat部分</s></li> 

                        <#--  @1.10  -->
                        <li><b style="color:red">@v1.10 fixed</b> <s>Mybatis中resultType=java.util.HashMap时返回的结果是无序的，散列映射，所以用TreeMap或LinkedHashMap</s></li> 
                        <li><b style="color:red">@v1.10 fixed</b> <s>员工信息统计时会加载部门树的请求，检查其他模块中会不会加载多余的请求</s></li> 

                        <#--  @1.9  -->
                        <li><b style="color:red">@v1.9 fixed</b> <s>jdbcType=Date正确获取日期格式，entity和beans使用日期格式时使用java.time.LocalDate，此为线程安全且更加简单，java.util.Date不建议使用</s></li> 
                        <li><b style="color:red">@v1.9 fixed</b> <s>添加员工信息管理区别于用户管理(指后台操作管理人员)</s></li> 
                        <li><b style="color:red">@v1.9 fixed</b> <s>后台管理员与员工管理分开建表</s></li> 

                        <#--  @1.8  -->
                        <li><b style="color:red">@v1.8 fixed</b> <s>部门树操作时，解决Enter键会快速触发下一个指令，通过setTimeout方法</s></li> 
                        <li><b style="color:red">@v1.8 fixed</b> <s>部门树操作时，解决新增部门时，Enter键的keydown和keyup事件一同执行，导致部门名称为空</s></li> 
                        <li><b style="color:red">@v1.8 fixed</b> <s>部门树操作时，解决上述问题，sweetalert提供用[空格]键为默认按键</s></li> 
                        <li><b style="color:red">@v1.8 fixed</b> <s>部门树操作时，解决在树的编辑操作上无法添加相同部门</s></li> 
                        <li><b style="color:red">@v1.8 fixed</b> <s>部门树操作时，解决在树上无法添加相同普通部门</s></li> 
                        <li><b style="color:red">@v1.8 fixed</b> <s>部门树操作时，解决编辑部门时相同部门名提示已有相同部门提示，通过判断treeNode.deptName与newName</s></li> 
                        <li><b style="color:red">@v1.8 fixed</b> <s>部门树操作时，解决删除部门时，节点先被删掉，通过替换回调函数onRemove为beforeRemove，具体实现不变</s></li> 
                        <li><b style="color:red">@v1.8 fixed</b> <s>添加相同部门，无法添加相同部门到不同根节点</s></li> 
                        <li><b style="color:red">@v1.8 fixed</b> <s>部门树删除或编辑中取消操作，无法正常显示节点，必须刷新</s></li> 
                        <li><b style="color:red">@v1.8 fixed</b> <s>终端出现大量CONDITIONS EVALUATION REPORT信息，
                        application.yml加入logging.level.org.springframework.boot.autoconfigure: ERROR </s></li> 

                        <#--  @1.7  -->
                        <li><b style="color:red">@v1.7 fixed</b> <s>图片文件以Base64形式插入到数据库</s></li> 
                        <#--  @1.6  -->
                        <li><b style="color:red">@v1.6 fixed</b> <s>上传文件页面图片预览和Base64编码框会超出div范围</s></li>
                        <#--  @v1.4  -->
                        <li><b style="color:red">@v1.4 fixed</b> <s>成功登陆时一直按住Esc会取消登陆，[暂时无解]，浏览器操作无法取消</s> </li>
                         <li><b style="color:red">@v1.4 fixed</b> <s>重新设计登陆UI</s> </li> 
                        <li><b style="color:red">@v1.4 fixed</b> <s>登陆时地址栏传入JESSIONID</s> </li>
                        <#--  @v1.3  -->
                        <li><b style="color:red">@v1.3 fixed</b> <s>部门树节点>3时会出现滚动条</s> </li>
                        <li><b style="color:red">@v1.3 fixed</b> <s>部门树添加节点时，点击框外时会创建空部门</s> </li>
                        <li><b style="color:red">@v1.3 fixed</b> <s>部门树节点过多时，节点会超出body外，并且不显示其他图标</s> </li>
                        <li><b style="color:red">@v1.3 fixed</b> <s>点击部门编辑时，若parentId为0,则parentId栏不会显示数据</s> </li>
                        <li><b style="color:red">@v1.3 fixed</b> <s>点击部门编辑时，先编辑parentId为非0的部门后，再编辑为parentId0的部门时，parentId会更改为之前的parentId</s> </li>
                        <li>树的根节点不能被删除，其他功能待完善</li> 
                        <li><b style="color:red">@v1.3 fixed</b> <s>节点BUG，修改根节点名称后，节点对应的部门等级会变成默认的1</s> </li> 
                        <#--  @v1.2  -->
                        <li><b style="color:red">@v1.2 fixed</b> <s>页面响应慢</s></li> 
                        <li><b style="color:red">@v1.2 fixed</b> <s>登陆时用户为空会出现异常</s></li> 
                        <li><b style="color:red">@v1.2 fixed</b> <s>减少cdn外部文件如:bootstrap-table,sweetalert</s> </li> 
                        <#--  no need to fix  -->
                        <li><b style="color:red">no need to fix</b> <s>解决加载时console中出现许多404文件未找到map错误</s></li> 
                        
                    </ol>
                </div>
        </div>        
        </div>

    <div class="col-md-4 col-sm-4 col-xs-4">
        <div class="panel panel-default">
        <div class="panel-heading">更新日志/Changelog</div>
            <div class="panel-body">
                <li><b style="color:red">Oracle && Mysql difference @ 2020/6/28</b></li>
                <ol>
                    <h5><b style="color:red">基本用法区别</b></h5>
                    <li>区别1: oracle查询表时from后面不能用as匿名处理</li>
                    <li>区别2: oracel没有limit, offset等，要用rownum</li>
                    <li>区别3: oracle没有自增序列auto_increment功能，要自己添加序列</li>
                    <li>区别4: oracle对单引号，双引号要求高，不支持双引号，会报invalid identifier错误</li>
                    <li>区别5.1: oracle使用group by时，select后的字段要么是参与分组的列，这里指的是select后所有的列，要么列包含在聚合函数中不然会报not a group by statement错误</li>
                    <li>区别5.2: 例: select id, name, sex from xxx group by id, name, sex 不能任何一个列或字段，不然就会报错</li>
                    <li>区别6: oracle没有int, float数据类型，有的是number类型</li>
                    <li>区别7: oracle有to_number, to_date函数，如果$.post得到的值是123456, 入库的时候要通过to_number转换，不然会存为string类型</li>
                    <li>区别8: oracle默认端口为1521, mysql为3306</li>
                    <li>区别9: oracle通过:sqlplus user/password方式登陆，mysql通过: mysql -hlocalhost -uroot -p方式登陆</li>
                    <li>区别X: oracle对[`]号不支持，mysql支持</li>
                    <li>区别X: oracle结果字段想要为小写必须添加[""]双引号，默认会是大写</li>
                    <li>区别X: oracle在插入数据时，不能插入直接插入NULL，要通过一些手段添加</li>

                    <li><b style="color:red">区别10: 事务提交</b> oracle不会自动提交事务，需要手动提交mysql自动提交事务</li>                        
                    <li><b style="color:red">区别11: 事务支持</b> oracle只有一种数据引擎，完全支持事务，mysql只有在innodb存储引擎的行级锁下才可支持事务</li>                        
                    <li><b style="color:red">区别12: 保存数据的持久性</b> oracle把sql的操作线写入到在线联机日志文件中，保存到了磁盘上，可随时恢复，mysql在更新或重启数据库时会丢失数据</li>                        
                    <li><b style="color:red">区别13: 并发性</b> oracle的并发性高，通过使用行级锁，在数据行上加锁，而非通过索引， mysql以表级锁为主，通过innodb存储引擎的表可用行级锁，不过依赖于索引，如果没索引则还是表级锁</li>                        
                    <li><b style="color:red">区别14: 分页查询</b> oracle分页通过rownum, mysql通过limit, offset</li>                        
                    <li><b style="color:red">区别15: 性能诊断</b> oracle有很多诊断工具，像sqltrace, addm, tkproof等，mysql主要有慢查询日志</li>                        
                    <li><b style="color:red">区别16: 分区表与分区索引</b> oracle功能很成熟，稳定，mysql不太成熟</li>                        
                    <li><b style="color:red">区别17: 最重要的区别</b> oracle价格昂贵，mysql开源免费</li>                        
                </ol>

                <li>Ver.1.13 @ 2020/7/15</li>
                <ol>
                    <li>修改: application.yml中注释掉config-location，此为设置mybatis的“setting”，但在springboot中通过application.yml配置信息，尽量不要用xml形式</li>
                    <li>修改: application.yml中注释掉configuration, 打开注释后添加jdbc-type-for-null='null',允许空值传入</li>
                    <li>修改: application.yml中注释掉configuration, 打开注释,log-impl表示mybatis-config.xml中的logImpl，输出mybatis的sql信息</li>
                    <li>修改: role的list.ftl文件，编辑信息时可用栏不能正常显示数值，因为后端返回的是true对象，前端无法识别，若改成"true"就可以识别，但要更改select option中的value="true" or "false, bootstrap-table中的可用是通过column中的formatter的操作进行信息转换。service层中的getAvailable控制前端接收的数据类型格式，若改成如isAvailable是没有available的返回值的"</li>
                    
                </ol>

                <li>Ver.1.12 @ 2020/7/8</li>
                <ol>
                    <li>增加: application.yml中添加日期字段的限制: spring.jackson.time-zone&&date-format</li>
                    <li>增加: 实现oracle下添加员工功能, 成功正解添加日期格式，以非JSON格式</li>
                    <li><b style="color: blue">修改: SysEmployee的日期类型修改为Date类型，LocalDate暂时没解决将表单serialize()后带着Date转成对象</b></li>
                    <li><b style="color: blue">修改: Employee的getter(), setter()修改为Date类型</b></li>
                    <li>修改: 在SysEmployee的属性上添加DateTimeFormat()注解</li>
                    <li>修改: ExcelUtils通过fieldCount判断导入的Excel文件中Cell的个数，通过getLastCellNum只会获取到最后数据显示的位置，所以不适用</li>
                    <li>修改: 修改Employee的getter和setter，前端接收的key是根据getter变化，getEmployeeId则返回的是employeeId, getId返回的是id，谨记！</li>
                    <li>增加: 在RestEmployeeController里添加@InitBinder，正确接收前端传来的日期属性，不加会出现can not convert java.lang.String to java.util.Date错误</li>
                </ol>

                <li>Ver.1.11 @ 2020/6/25</li>
                <ol>
                    <li>增加: 对Oracle的支持</li>
                    <li>增加: 将原来的mybatis文件夹分成mysql与oracle的mapper文件</li>
                    <li>安装: mvn install:install-file -DgroupId=com.oracle -DartifactId=ojdbc6 -Dversion=11.2.0.2.0 -Dpackaging=jar -Dfile=[filepath]ojdbc6</li>
                    <li>修改: pom.xml文件添加ojdbc6的支持</li>
                    <li>修改: 修改emirio.ztree.js文件，判断支持对空格的判断</li>
                    <li>修改: 修改index.ftl文件，重新排版，将bugs与更新版块并列展示</li>
                    <li><b style="color:red">=========================================</b></li>
                    <li>修改: RestDeptController.java, add方法添加对oracle的插入操作</li>
                    <li>修改: SysDeptMapper/SysDeptService等文件, 添加insertDept和checkCurrentMaxId方法</li>
                    <li>修改: application.yml修改mybatis.config-location与configuration不能共存，谨记！</li>
                    <li>添加: resources/config/mybatis-config.xml 添加settings, 允许'NULL'值</li>
                    <li><b style="color:red">=========================================</b></li>
                    <li>修改: Mybatis的PageHelper会在select语句中自动添加LIMIT，是因为数据库为mysql且pagehelper-dialect=mysql时，mybatis会自动在select之前执行count方法，之后在最后添加limit实行分页方法</li>
                    <li>修改: 解决方法是在Controller里，在return ResultUtil之前添加 PageHelper.clearPage()，此行为会删掉LIMIT</li>
                    <li>注意: PageHelper切换不同数据源时，需要在application.yml或properties文件下设定不同的数据库的同时，修改pagehelper.pagehelper-dialect=mysql or oracle，不然mybatis解析sql语句时还是会按照所对应的dialect进行sql解析</li>
                    <li>注意: 分页查询中，pagehelper-dialect=mysql, 会自动在结尾处添加LIMIT用来进行分页查询, 且在查询所有数据前会先进行COUNT()操作</li>
                    <li>注意: 分页查询中，pagehelper-dialect=oracle, 会自动在查询所有数据之前执行以下操作</li>
                    <li><b style="color:red">注意1: SELECT TMP_PAGE.*, ROWNUM ROW_ID FROM ( SELECT s.dept_id, s.parent_id, dept_name FROM sys_dept s ) TMP_PAGE WHERE ROWNUM <= ? </b></li>
                    <li><b style="color:red">注意2: 在SysXXXServiceImpl.java中的findPagereakCondition当中通过设定PageHelper.startPage(vo.getPageNumber(), vo.getPageSize(), false)进行开关count()方法，具体参照: https://www.jianshu.com/p/637254b99835</b></li>

                </ol>

                <li>Ver.1.10 @ 2020/6/24</li>
                <ol>
                    <li>增加: 员工管理->信息统计模块</li>
                    <li>增加: 增加emirio.echart.js</li>
                    <li>增加: 员工表与部门进行关联</li>
                    <li>增加: 信息统计 -> 薪资与部门关系电子表图</li>
                    <li>增加: 信息统计 -> 员工按性别/年龄统计</li>
                    <li>增加: 信息统计 -> 职位与部门分布情况</li>
                    <li>增加: 信息统计 -> 在地图上显示员工住址</li>
                    <li>增加: 信息统计 -> 全年统计各月的薪资情况，难度在于[数据提取] MYSQL 与[数据显示] 前端的ECHART</li>
                    <li>修改: 修改SysEmployeeMapper.xml, SysEmployeeService.java等相关文件</li>
                    <li>修改: 使用员工管理功能时删除掉无用的请求，如:部门树的请求</li>
                    <li>修改: 修改footer.ftl, header.ftl文件，当且只有当该模块被使用时，才加载相应的请求及功能模块</li>
                    <li>修改: 员工管理分为[员工列表]/[信息统计]</li>
                    <li>修改: 员工实体和POJO的薪资类型更改为Long，根据需要进行更改</li>
                    <li>提醒: Mybatis SUM()函数返回的类型为BigDecimal, 要注意</li>
                    <li>完善: 尽量减少请求，提高访问速度(信息统计)模块中效果显著，设定3个全局刷新的三个Option</li>
                    <li>优化: 优化mybatis sql语句，提高处理速度</li>
                </ol>

                <li>Ver.1.9 @ 2020/6/15</li>
                <ol>
                    <li>增加: 员工管理</li>
                    <li>增加: 新增员工表</li>
                    <li>修改: 通过java.time.LocalDate类确定不带时间的纯日期格式</li>
                    <li>完善: 树上部门编辑</li>
                </ol>

                <li>Ver.1.8 @ 2020/6/4</li>
                <ol>
                    <li>完善: 部门树的功能</li>
                    <li>完善: 树上部门添加</li>
                    <li>完善: 树上部门编辑</li>
                    <li>完善: 树上部门删除</li>
                    <li>完善: 树的操作基本在前端进行判断操作，尽可能与后端分开，为前后端分离做好准备</li>
                    <li>新增: 判断部门名称中是否有特殊字符，版本只判断中英文特殊字符，有待增加判断</li>
                    <li>新增: 树上的所有默认按键操作改为[空格键]</li>
                    <li>修复: 新增部门时Enter键引起的部门名称为空的Bug, 只能用[空格键]代替Enter键的效果，若想用Enter键改用sweetalert2</li>
                    <li>更新: 更新emirio.ztree.js为v1.4</li>
                </ol>

                <li>Ver.1.7 @ 2020/5/29</li>
                <ol>
                    <li>完善: 图片上传功能</li>
                    <li>完善: 图片与Base64转换的功能</li>
                    <li>完善: 判断是否为正确的图片Base64编码</li>
                    <li>完善: 后台判断文件是否存在，若存在提示文件已存在</li>
                    <li>完善: 后台判断/upload文件夹是否存在，若不存在则创建新目录</li>
                    <li>完善: 后台保存web传来的图片的base64编码，可不适合插入到数据库，一般存储文件路径</li>
                </ol>

                <li>Ver.1.6 @ 2020/5/28</li>
                <ol>
                    <li>增加: 图片上传功能</li>
                    <li>增加: 图片转Base64，Base64转图片功能</li>
                    <li>增加: emirio.upload.js</li>
                    <li>增加: 显示图片预览效果</li>
                    <li>增加: 显示Base64代码</li>
                    <li>增加: 判断上传文件大小，过大文件影响转换速度，占用更多资源</li>
                    <li>修复: 上传文件页面底部会出现绿色恶心的框，用div=row包起来解决</li>
                </ol>

                <li>Ver.1.5 @ 2020/5/26</li>
                <ol>
                    <li>添加: Excel的导出与导入</li>
                    <li>添加: 各个菜单都有导出与导入功能</li>
                    <li>添加: poi-ooxl依赖</li>
                    <li>添加: emirio.excel.js</li>
                    <li>增加: ExcelUtils.java工具类，处理excel文件的导出，导入，数据类型匹配</li>
                    <li>增加: HttpServletUtils工具类，获取前端传递的request</li>
                    <li>增加: SpringContextUtil工具类，获取上下文中的bean</li>
                    <li>增加: 导出模板功能，带有格式化的表头</li>
                    <li>增加: 导入Excel时将返回数据库操作的结果集，修改前后端insert, update等计数作为统计</li>
                    <li>增加: 后台插入对象实体集合的方法，insertListWithCheck</li>
                    <li>修复: 导出数据字段顺序</li>
                    <li>修复: 导出模板，只有风格格式化后的表头，数据单元暂无</li>
                    <li>修复: sweetalert2与sweetalert因导入顺序会出现冲突，将sweetalert2放在sweetalert上方即可</li>                            
                    <li>修复: 资源管理不能正常分页显示数据，修改zyd.table.js 禁用idField, treeShowField等参数</li>                            
                    <li>修复: 导入时不能正常显示Boolean类型值，Excel工具类checkFormat中加入对Boolean类型的判断</li>                            
                    <li>修复: 导入文件时，筛选为excel文件，如:*.xlsx文件</li>                            
                    <li>修改: 导入以文件上传的方式传到后台，替换原来的路径字符串传递</li>                            
                    <li>修改: 前端导入以ajax传递FormData方式传到后台，替换之前的表单传递</li>                            
                    <li>修改: 重新设计Excel工具类，提升文件类型数据匹配</li>  
                    <li>修改: downloadExcel方法添加了如用户，资源等ID继承于AbstractDO，无法获取其ID值，通过FieldUtils.getAllFields方法获取</li>  
                    <li>修改: 将输出语句改友log(@slf4j)的形式输出</li>  
                    <li>修改: 修改logback.xml，添加彩色输出日志信息</li>  
                    <li>修改: 修改各个list.ftl文件，添加导入导出按键，增加init方法传递每个菜单的导入导出url</li>  
                    <li>修改: 修改logback.xml，添加彩色输出日志信息</li>  
                    <li>修改: 导入数据时检测字段匹配</li>  
                    <li>删除: 从@Excel自定义注解中删除dict()与join()功能</li>  
                    
                </ol>

                <li>Ver.1.4 @ 2020/5/15</li>
                <ol>
                    <li>重新设计登陆界面UI</li>
                    <li>增加: emirio.login.js, emirio.form.css</li>
                    <li>增加: sweetalert2, animate.css, cssanimation.css, vanta, three.r95,  etc.</li>
                    <li>修复: 登陆页面加载过慢</li>
                    <li>修复: 删除冗余的代码</li>
                    <li>替换: 新图标</li>
                    <li>修复: DOM层按键监听，会不断提交表单问题，使用sweetalert2</li>                            
                    <li>修复: shiro登陆成功时附带JESSIONID, 修改ShiroConfig.java -> DefaultWebSessionManager sessionManager() ->         sessionManager.setSessionIdUrlRewritingEnabled(false)</li>                            
                </ol>

                <li>Ver.1.3 @ 2020/5/8</li>
                <ol>
                    <li>完善:树的所有功能</li>
                    <li>修复:编辑parentId为0的部门信息会不显示0的Bug</li>
                    <li>修复:连续非保存编辑部门时，parentId会替换为0的部门parentId</li>
                    <li>添加:增加节点时增加总部门节点</li>
                    <li>修复:添加树节点，点击外框时添加空部门</li>
                    <li>修改:树上增加部门时增加新功能</li>
                    <li>修改:zTree/metro.min.css注释掉line_conn图片，防止滚动条影响美观</li>
                    <li>修改:部门节点的溢出，ul id=treeDemo style="overflow:auto"</li>
                    <li>修改:表的奇偶行不同颜色table添加class="table table-striped"</li>
                        
                </ol>


                <li>Ver.1.2 @ 2020/5/5</li>
                <ol>
                    <li>增加:sweetalert插件</li>
                    <li>引入:sweetalert插件</li>
                    <li>引入:新bootstrap-table插件</li>
                    <li>增加:部门ztree树的增删改操作</li>
                    <li>增加:/emirio/emirioTree.js文件</li>
                    <li>解决:搜索不能按键不能正常显示</li>
                    <li>添加:搜索功能，通过名称查找或其他</li>
                </ol>

                <li>Ver.1.1 @ 2020/4/28</li>
                <ol>
                    <li>增加:添加部门功能</li>
                    <li>增加:删除部门功能</li>
                    <li>增加:修改部门功能</li>
                    <li>增加:批量删除部门功能</li>
                    <li>增加:部门树结构表示功能</li>
                    <li>增加:部门树的增删改功能</li>
                    <li>修复:用户批量删除功能</li>
                </ol>

                <li>Ver.1.0 @ 2020/4/25</li>
                <ol>
                    <li>增加:添加用户功能</li>
                    <li>增加:删除用户功能</li>
                    <li>增加:修改用户功能</li>
                    <li>增加:资源管理功能</li>
                    <li>增加:角色管理功能</li>
                    <li>增加:基本界面设置</li>
                </ol>                
            </div>
        </div>
</div>


<#include "layout/footer.ftl"/>