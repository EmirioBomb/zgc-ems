<#include "/layout/header.ftl"/>
<div class="clearfix"></div>
<div class="row">
    <div class="col-md-3 col-sm-12 col-xs-12" id="dept_struct">
        <#--  show dept ztree  -->
        <div class="panel panel-default">
            <div class="panel-heading">部门结构 <a href="javascript:void(0)">[上级部门的名称与PID是唯一的]</a></div>
            <div class="panel-body" id="tree_body" style="overflow:auto">
                <div class="zTreeDemoBackground left">
                    <ul id="treeDemo" class="ztree" style="overflow:auto"></ul>
                </div>
            </div>
        </div>    
    </div>

    <#--  show dept info table  -->
    <div class="col-md-9 col-sm-12 col-xs-12">
        <ol class="breadcrumb">
            <li><a href="/">首页</a></li>
            <li class="active">部门管理</li>
        </ol>
        <div class="x_panel">
            <div class="x_content">
                <div class="<#--table-responsive-->">
                    <div class="btn-group hidden-xs" id="toolbar">
                        <@shiro.hasPermission name="dept:add">
                        <button id="btn_add" type="button" class="btn btn-default" title="新增部门">
                            <i class="fa fa-plus"></i> 新增部门
                        </button>
                        </@shiro.hasPermission>
                        <@shiro.hasPermission name="dept:batchDelete">
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
                                <li><a href="/dept/export">导出数据</a></li>
                                <li><a href="/dept/exportTemplate">导出模板</a></li>
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

<#include "/layout/footer.ftl"/>

<!--弹出部门添加框-->
<div class="modal fade" id="addOrUpdateModal" tabindex="-1" role="dialog" aria-labelledby="addroleLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 id="addDept" class="modal-title">添加部门</h4>
            </div>
            <div class="modal-body">
                <form id="addOrUpdateForm" class="form-horizontal form-label-left" novalidate>
                <#--  隐藏数据部门ID  -->
                    <input type="hidden" name="deptId">
                    <input type="hidden" name="dept_id">
                    <#--  部门名称区域  -->
                    <div class="item form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="deptName">部门名称: <span class="required">*</span></label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <input type="text" class="form-control col-md-7 col-xs-12" name="deptName" id="deptName" required="required" placeholder="请输入部门名称"/>
                        </div>
                    </div>
                    <div class="item form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="parentId">部门层级: <span class="required">*</span></label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <input type="text" class="form-control col-md-7 col-xs-12" id="parentId" name="parentId" required="required" placeholder="请输入部门层级"/>
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

<script src="/assets/emirio-script/xlsx.full.min.js"></script>

<script>
/**
    * 操作按钮
    * @param code
    * @param row
    * @param index
    * @returns {string}
*/

function operateFormatter(code, row, index) {
    var trDeptId = row.deptId;
    var operateBtn = [
        '<@shiro.hasPermission name="dept:edit"><a class="btn btn-xs btn-primary btn-update" data-id="' + trDeptId + '"><i class="fa fa-edit"></i>编辑</a></@shiro.hasPermission>',
        
        '<@shiro.hasPermission name="dept:delete"><a class="btn btn-xs btn-danger btn-remove" data-id="' + trDeptId + '"><i class="fa fa-trash-o"></i>删除</a></@shiro.hasPermission>'
    ];

    return operateBtn.join('');
}

$(function () {
    var options = {
        url: "/dept/list",
        <#--  url: "/dept/getDeptTree",  -->
        getInfoUrl: "/dept/get/{id}",
        updateUrl: "/dept/edit",
        removeUrl: "/dept/remove",
        createUrl: "/dept/add",
        importUrl: "/dept/import",
        columns: [
            {
                checkbox: true
            }, {
                field: 'deptId',
                title: '部门编号',
                editable: false
            }, {
                field: 'deptName',
                title: '部门名称',
                editable: false
            }, {
                field: 'parentId',
                title: '部门层级',
                editable: false
            }, {
                field: 'operate',
                title: '操作',
                formatter: operateFormatter
            }
        ],
        modalName: "部门"
    };
    //1.初始化Table
    $.tableUtil.init(options);

    //2.初始化Button的点击事件
    $.buttonUtil.init(options);

    $.init(options.importUrl);
});

</script>
<script src="/assets/emirio-script/emirio.ztree.js"></script>
