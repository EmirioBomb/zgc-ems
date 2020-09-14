<#include "/layout/header.ftl"/>

<div class="row">
    <#--  show dept info table  -->
    <div class="col-md-12 col-sm-12 col-xs-12">
        <ol class="breadcrumb">
            <li><a href="/">首页</a></li>
            <li class="active">员工管理</li>
        </ol>
        <div class="x_panel">
            <div class="x_content">
                <div class="<#--table-responsive-->">
                    <div class="btn-group hidden-xs" id="toolbar">
                        <@shiro.hasPermission name="employee:add">
                        <button id="btn_add" type="button" class="btn btn-default" title="新增部门">
                            <i class="fa fa-plus"></i> 新增员工
                        </button>
                        </@shiro.hasPermission>
                        <@shiro.hasPermission name="employee:batchDelete">
                            <button id="btn_delete_ids" type="button" class="btn btn-default" title="删除选中">
                                <i class="fa fa-trash-o"></i> 批量删除
                            </button>
                        </@shiro.hasPermission>
                        <#--  import exel  -->
                        <div class="btn-group">
                            <input type="file" style="display: none;" id="open-file" name="file" accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel">
                            <button id="import-excel-btn" class="btn btn-danger fa fa-upload">导入</button>
                        </div>
                       <#--  export excel  -->
                        <div class="btn-group">
                            <button type="button" class="btn btn-primary dropdown-toggle fa fa-download" data-toggle="dropdown"> 
                               导出<span class="caret"></span>
                            </button>
                            <ul class="dropdown-menu" role="menu">
                                <li><a href="/employee/export">导出数据</a></li>
                                <li><a href="/employee/exportTemplate">导出模板</a></li>
                            </ul>
                        </div>

                    </div>
                    <table id="tablelist" class="table-striped">
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
</div>
<#include "/layout/footer.ftl"/>

<!--弹出部门添加框-->
<div class="modal fade" id="addOrUpdateModal" tabindex="-1" role="dialog" aria-labelledby="addroleLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 id="addEmployee" class="modal-title">添加员工</h4>
            </div>
            <div class="modal-body">
                <form id="addOrUpdateForm" class="form-horizontal form-label-left" novalidate>
                    <input type="hidden" name="id">
                    <#--  employee name  -->
                    <div class="item form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="name"><span class="required">*</span>员工名称:</label> 
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <input type="text" class="form-control col-md-7 col-xs-12" name="name" id="name" required="required" placeholder="请输入员工名称"/>
                        </div>
                    </div>
                    <#--  gender  -->
                    <div class="item form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="type"><span class="required">*</span>性别: </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <select name="sex" id="sex" required="required" class="form-control col-md-7 col-xs-12">
                                <option value="">请选择</option>
                                <option value="男">男</option>
                                <option value="女">女</option>
                                <option value="保密">保密</option>
                            </select>
                        </div>
                    </div>
                    <#--  birthday  -->
                    <div class="item form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="type">出生日期: <span class="required"></span></label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <div class='input-group date' id='datetimepicker'>
                                <input type='text' class="form-control" name="birthday"/>
                                <span class="input-group-addon">
                                    <span class="glyphicon glyphicon-calendar">
                                    </span>
                                </span>
                            </div>
                        </div>
                    </div>
                    <#--  phone  -->
                    <div class="item form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="phone">电话号码: <span class="required"></span></label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <input type="text" class="form-control col-md-7 col-xs-12" name="phone" id="phone"  placeholder="请输入电话号码"/>
                        </div>
                    </div>
                   <#--  address  -->
                    <div class="item form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="address">家庭住址: <span class="required"></span></label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <input type="text" class="form-control col-md-7 col-xs-12" name="address" id="address"  placeholder="请输入家庭住址"/>
                        </div>
                    </div>
                    <#--  salary  -->
                    <div class="item form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="salary">月薪工资: <span class="required"></span></label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <input type="text" class="form-control col-md-7 col-xs-12" name="salary" id="salary"  placeholder="请输入月薪"/>
                        </div>
                    </div>
                    <#--  job  -->
                    <div class="item form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="job">职位: <span class="required"></span></label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <input type="text" class="form-control col-md-7 col-xs-12" name="job" id="job"  placeholder="请输入当前职位"/>
                        </div>
                    </div>
                    <#--  department  -->
                    <div class="item form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="type">部门: </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <input type="select" class="form-control col-md-7 col-xs-12" id="deptId" placeholder="请选择部门" />
                            <#--  <div class="col-md-6 col-sm-6 col-xs-12" style="height: 50px" id="zTree">
                            </div>  -->
                            
                        </div>
                    </div>
                    <#--  entry_time  -->
                    <div class="item form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="type">入职日期: <span class="required"></span></label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <div class='input-group date' id='datetimepicker1'>
                                <input type='text' class="form-control" name="entryTime"/>
                                <span class="input-group-addon">
                                    <span class="glyphicon glyphicon-calendar">
                                    </span>
                                </span>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary addOrUpdateBtn">保存</button>
            </div>
        </div>
    </div>
</div>

<#--  <script src="/assets/emirio-script/xlsx.full.min.js"></script>  -->

<script>
/**
    * 操作按钮
    * @param code
    * @param row
    * @param index
    * @returns {string}
*/



$('#datetimepicker').datetimepicker({
    format: 'YYYY-MM-DD'
});
$('#datetimepicker1').datetimepicker({
    format: 'YYYY-MM-DD'
});
function operateFormatter(code, row, index) {
    var id = row.id;
    var operateBtn = [
        '<@shiro.hasPermission name="employee:edit"><a class="btn btn-xs btn-primary btn-update" data-id="' + id + '"><i class="fa fa-edit"></i>编辑</a></@shiro.hasPermission>',
        
        '<@shiro.hasPermission name="employee:delete"><a class="btn btn-xs btn-danger btn-remove" data-id="' + id + '"><i class="fa fa-trash-o"></i>删除</a></@shiro.hasPermission>'
    ];

    return operateBtn.join('');
}

var zNodes = [];
$(document).ready(function(){
    $.ajax({
        type: "POST",
        url: '/dept/getDeptTree',
        dataType: "json",
        success: function(data) {
            zNodes = data;
        }
    }); 
});

$(function () {
    var options = {
        url: "/employee/list",
        getInfoUrl: "/employee/get/{id}",
        updateUrl: "/employee/edit",
        removeUrl: "/employee/remove",
        createUrl: "/employee/add",
        importUrl: "/employee/import",
        columns: [
            {
                checkbox: true
            <#--  }, {
                field: 'id',
                title: '编号',
                editable: false  -->
            }, {
                field: 'name',
                title: '姓名',
                editable: false
            }, {
                field: 'sex',
                title: '性别',
                editable: false
            }, {
                field: 'age',
                title: '年龄',
                editable: false
            }, {
                field: 'birthday',
                title: '出生日期',
                editable: false
            }, {
                field: 'phone',
                title: '电话号码',
                editable: false
            }, {
                field: 'address',
                title: '家庭住址',
                editable: false
            }, {
                field: 'salary',
                title: '月薪',
                editable: false
            <#--  }, {
                field: 'email',
                title: '电子邮箱',
                editable: false  -->
            }, {
                field: 'job',
                title: '职位',
                editable: false
            }, {
                field: 'entryTime',
                title: '入职时间',
                editable: false
            }, {
                field: 'modifyTime',
                title: '修改时间',
                editable: false
            }, {
                field: 'operate',
                title: '操作',
                formatter: operateFormatter
            }
        ],
        modalName: "员工"
    };
    //1.初始化Table
    $.tableUtil.init(options);

    //2.初始化Button的点击事件
    $.buttonUtil.init(options);

    $.init(options.importUrl);
});

</script>


            