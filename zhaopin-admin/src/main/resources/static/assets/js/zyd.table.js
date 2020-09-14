/**
 * bootstrap-table工具类
 *
 * @author emirio
 * @version 1.0
 * @date 2019-04-22
 * @since 1.0
 */
(function ($) {
    $.extend({
        tableUtil: {
            _option: {},
            init: function (options) {
                if(options.columns[1].field === 'deptName') {
                    var tmpId = 'deptId';
                    var tmpField = 'deptName';
                    var tmpParentId = 'parentId';
                    var tmpPagination = true;
                    var tmpColumns = options.columns;
                    
                } else {
                    var tmpId = 'id';
                    var tmpField = 'name';
                    var tmpParentId = 'parentId';
                    var tmpPagination = true;
                    var tmpColumns = options.columns;
                }
                $.tableUtil._option = options;
                // console.log(options.url);
                // $('#tablelist').bootstrapTable('destroy');
                $('#tablelist').bootstrapTable({
                    url: options.url,
                    // url: '/dept/getDeptTree',
                    method: 'post',                      //请求方式（*）
                    toolbar: '#toolbar',                //工具按钮用哪个容器
                    striped: true,                      //是否显示行间隔色
                    cache: true,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
                    contentType: "application/x-www-form-urlencoded", // 发送到服务器的数据编码类型, application/x-www-form-urlencoded为了实现post方式提交
                    sortable: false,                     //是否启用排序
                    sortOrder: "asc",                   //排序方式
                    sortStable: true,                   // 设置为 true 将获得稳定的排序
                    queryParams: $.tableUtil.queryParams,//传递参数（*）
                    queryParamsType: '',
                    pagination: tmpPagination,                   //是否显示分页（*）
                    sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
                    onlyInfoPagination: false,
                    smartDisplay: false,
                    pageNumber: 1,                       //初始化加载第一页，默认第一页
                    pageSize: 10,                       //每页的记录行数（*）
                    pageList: "[10, 20, 30, 50, 100, ALL]",        //可供选择的每页的行数（*）
                    search: true,                       //是否启用搜索框 根据sidePagination选择从前后台搜索
                    // strictSearch: false,                 //设置为 true启用 全匹配搜索，否则为模糊搜索
                    
                    // searchOnEnterKey: true,            // 设置为 true时，按回车触发搜索方法，否则自动触发搜索方法
                    minimumCountColumns: 1,             //最少允许的列数
                    showColumns: true,  
                    showSearchButton: true,                //是否显示 内容列下拉框
                    showSearchClearButton: true,
                    showRefresh: true,                  //是否显示刷新按钮
                    showToggle: false,                   //是否显示详细视图和列表视图的切换按钮
                    showExport: true,
                    // exportDataType: 'all',
                    exportOptions: {
                        ignoreColumn: [0, 4],
                    },
                    clickToSelect: true,                //是否启用点击选中行
                  
                    /**
                    若想bootstrap-table以树状显示，请注释掉下面语句
                        url: '/dept/getDeptTree',
                        idField: 'deptId',
                        treeShowField: 'deptName',
                        parentIdField: 'parentId',

                        idField: tmpId,
                        treeShowField: tmpField,
                        parentIdField: tmpParentId, 

                        onLoadSuccess: function(data) {
                            $("#tablelist").treegrid({
                            initialState: 'collapsed',
                            treeColumn: 1,
                            expanderExpandedClass: 'glyphicon glyphicon-chevron-down',
                            expanderCollapsedClass: 'glyphicon glyphicon-chevron-right',
                            onChange: function() {
                                $("#tablelist").bootstrapTable('resetWidth');
                            }
                            });
                        },
                     */
                    

                    onEditableSave: function (field, row, oldValue, $el) {
                        if (options.updateUrl) {
                            $.ajax({
                                type: "post",
                                url: options.updateUrl,
                                data: {strJson: JSON.stringify(row)},
                                success: function (json) {
                                    if (json.status == 200) {
                                        $.tool.alert(json.message);
                                    } else {
                                        $.tool.alertError(json.message);
                                    }
                                },
                                error: function () {
                                    $.tool.alertError("网络超时！");
                                }
                            });
                        } else {
                            $.tool.alertError("无效的请求地址！");
                            return;
                        }
                    },
                    // rowStyle: options.rowStyle || function (row, index){return {};},
                    // columns: options.columns
                    columns: tmpColumns
                });
            },
            queryParams: function (params) {
                params = $.extend({}, params);

                // .extend merge multi objects
                // note1: $.extend({}, obj1, ..., objN)
                // note2: $.extend({}) blank means a null object, which merge as a new object by obj1 + ... + objN
                // keywords = $(".search-input").val();

                // まとめ:
                // jQuery.extend() で複数のオブジェクトをマージできる
                // 値の中身を書き換えられたくない場合は第1引数に空のオブジェクト {} を指定
                // 下の階層の値も再帰的にマージ（ディープコピー）してほしい場合は、extend() の第1引数に true を指定します。
                // 以下通り使えます
                //  var tempOjb = {
                //     keywords: $(".search-input").val()
                // }
                // $.extend({}, params, tempObj);
                
                // xxxMapper.xml -> <if test keywords=xxx>
                // params post keywords to back-end
                // if not, show same resultSet for every page

                params.keywords = $(".search-input").val();
                return params;
            },
            refresh: function () {
                $("#tablelist").bootstrapTable('refresh', {url: $.tableUtil._option.url});
                
                // not recommended, better method is to be continued
                location.reload();
            }
        },
        buttonUtil: {
            init: function (options) {
                /* 添加 */
                $("#btn_add").click(function () {
                    resetForm();
                    $("#addOrUpdateModal").modal('show');

                    // 点击新增部门时将<input name="dept_id">提交上去，避免新增时出现500错误
                    // $("input[name='deptId']").removeAttr("name");
                    $("input[name='deptId']").attr("disabled", "true");
                    // $("input[name='employeeId']").attr("disabled", "true");
                    

                    $("#addOrUpdateModal").find(".modal-dialog .modal-content .modal-header h4.modal-title").html("添加" + options.modalName);

                    if ($("#password") && $("#password")[0]) {
                        $("#password").attr("required", "required");
                    }
                    if ($("#username") && $("#username")[0]) {
                        $("#username").removeAttr("readonly");
                    }

                    var SampleJSONData = [
                        {
                            id: 0,
                            title: 'Horse'
                        }, {
                            id: 1,
                            title: 'Birds',
                            isSelectable: false,
                            subs: [
                                {
                                    id: 10,
                                    title: 'Pigeon',
                                    isSelectable: false
                                }, {
                                    id: 11,
                                    title: 'Parrot'
                                }, {
                                    id: 12,
                                    title: 'Owl'
                                }, {
                                    id: 13,
                                    title: 'Falcon'
                                }
                            ]
                        }, {
                            id: 2,
                            title: 'Rabbit'
                        }, {
                            id: 3,
                            title: 'Fox'
                        }, {
                            id: 5,
                            title: 'Cats',
                            subs: [
                                {
                                    id: 50,
                                    title: 'Kitty'
                                }, {
                                    id: 51,
                                    title: 'Bigs',
                                    subs: [
                                        {
                                            id: 510,
                                            title: 'Cheetah'
                                        }, {
                                            id: 511,
                                            title: 'Jaguar'
                                        }, {
                                            id: 512,
                                            title: 'Leopard'
                                        }
                                    ]
                                }
                            ]
                        }, {
                            id: 6,
                            title: 'Fish'
                        }
                    ];

                    var comboTree2 = $('#deptId').comboTree({
                        source : SampleJSONData,
                        isMultiple: false
                    });

                    bindSaveInfoEvent(options.createUrl);
                });

                /* 修改 */
                $('#tablelist').on('click', '.btn-update', function () {
                    var $this = $(this);
                    var userId = $this.attr("data-id");

                    // block posting deptId to back-end which caust 500 server error
                    // dept_id is right for back-end
                     $("input[name='deptId']").removeAttr("disabled");
                    //  $("input[name='employeeId']").removeAttr("disabled");
                    
                    $.ajax({
                        type: "post",
                        url: options.getInfoUrl.replace("{id}", userId),
                        success: function (json) {
                            var info = json.data;
                            // console.log(JSON.stringify(info));   
                            // var str = JSON.stringify(json.data).replace("deptId", "dept_id").replace(
                            //     "deptId", "dept_id"
                            // );
                            // var info = JSON.parse(str);
                            
                            // console.log(info);
                            resetForm(info);
                            $("#addOrUpdateModal").modal('show');
                            $("#addOrUpdateModal").find(".modal-dialog .modal-content .modal-header h4.modal-title").html("修改" + options.modalName);

                            /**
                             * issues:
                             * 1. parentId will not be shown in input_bar
                             * 2. parentId will exchange to another parentId
                             * 3. make sure parentId is correct
                             */
                            $("#parentId").val(json.data.parentId);

                            if ($("#password") && $("#password")[0]) {
                                $("#password").removeAttr("required");
                            }
                            if ($("#username") && $("#username")[0]) {
                                $("#username").attr("readonly", "readonly");
                            }

                            bindSaveInfoEvent(options.updateUrl);

                        },
                        error: $.tool.ajaxError
                    });
                });

                /* 删除 */
                function remove(ids) {
                    // debugger;
                    $.tool.confirm("确定删除该" + options.modalName + "信息？", function () {
                        $.ajax({
                            type: "post",
                            url: options.removeUrl,
                            traditional: true,
                            data: {'ids': ids},
                            success: function (json) {
                                $.tool.ajaxSuccess(json);
                                $.tableUtil.refresh();
                            },
                            error: $.tool.ajaxError
                        });
                    }, function () {

                    }, 5000);
                }

                /* 批量删除用户 */
                $("#btn_delete_ids").click(function () {
                    var selectedId = getSelectedId();
                    if (!selectedId || selectedId == '[]' || selectedId.length == 0) {
                        $.tool.alertError("请至少选择一条记录");
                        return;
                    }
                    remove(selectedId);
                });

                /* 删除 */
                $('#tablelist').on('click', '.btn-remove', function () {
                    var $this = $(this);
                    var userId = $this.attr("data-id");
                    remove(userId);
                });
            }
        }
    });
})(jQuery);

function bindSaveInfoEvent(url) {
    // debugger;
    $(".addOrUpdateBtn").unbind('click');
    $(".addOrUpdateBtn").click(function () {
        if (validator.checkAll($("#addOrUpdateForm"))) {
            $.ajax({
                type: "post",
                // contentType: "application/json;charset=UTF-8",
                url: url,
                data: $("#addOrUpdateForm").serialize(),
                success: function (json) {
                    $.tool.ajaxSuccess(json);
                    $("#addOrUpdateModal").modal('hide');
                    $.tableUtil.refresh();
                },
                error: $.tool.ajaxError
            });
        }
    })
}

function resetForm(info) {
    $("#addOrUpdateModal form input,#addOrUpdateModal form select,#addOrUpdateModal form textarea").each(function () {
        var $this = $(this);
        clearText($this, this.type, info);
    });
}

function clearText($this, type, info){
    var $div = $this.parents(".item");
    if ($div.hasClass("bad")) {
        $div.removeClass("bad");
        $div.find("div.alert").remove();
    }
    if (info) {
        var thisName = $this.attr("name");
        var thisValue = info[thisName];
        if (type == 'radio') {
            $this.iCheck(((thisValue && 1 == $this.val()) || (!thisValue && 0 == $this.val())) ? 'check' : 'uncheck')
        } else if (type == 'checkbox') {
            $this.iCheck((thisValue || thisValue == 1 || thisValue == 0) ? 'check' : 'uncheck');
        } else {
            if (thisValue && thisName != 'password') {
                $this.val(thisValue);
            }
        }
    } else {
        if (type === 'radio' || type === 'checkbox') {
            $this.iCheck('uncheck');
        }else{
            $this.val('');
        }
    }
}

/**
 * 获取选中的记录ID
 * @returns {Array}
 */
function getSelectedId() {
    var selectedJson = $("#tablelist").bootstrapTable('getAllSelections');
    var ids = [];
    $.each(selectedJson, function (i) {
        if(selectedJson[i].deptId != undefined) {
            ids.push(selectedJson[i].deptId);
        } else if(selectedJson[i].id != undefined){
            ids.push(selectedJson[i].id);
        }
        // } else if(selectedJson[i].employeeId != undefined) {
        //     ids.push(selectedJson[i].employeeId);
        // }
            // ids.push(selectedJson[i].id);
    });
    return ids;
}

/**
 * 获取选中的记录
 * @returns {*|jQuery}
 */
function getSelectedObj() {
    var selectedJson = $("#tablelist").bootstrapTable('getAllSelections');
    return selectedJson;
}
