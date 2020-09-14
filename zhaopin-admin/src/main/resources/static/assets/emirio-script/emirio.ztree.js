/**
* emirio.ztree.js based on zTree project
* this script solves operations on tree node of department

* Features:
* 1. parent node is unique
* 2. add new department, headquarters and branch included
* 3. add / edit / remove functions
* 4. add sweetalert as default prompt
* implements add / edit / remove options on nodes
*
* Technologies:
*   zTree, sweetalert, sweetalert2, jquery, bootstrap
*
* @author: emirio
* @version: v1.4
* @date: 2020/6/4
*/

/**
 < usage for sweetalert >
    $(".swal-footer").css({
    'background-color': 'rgb(245, 248, 250)',
    'margin-top': '32px',
    'border-top': '1px solid #E9EEF1',
    'overflow': 'hidden'
    });

    $(".swal-modal").css({
        'background-color': 'rgb(230, 230, 230)',
        'border': '2px solid #FF00FF'
    });
 */


// zTree settings
var setting = {
    view: {
        // addHoverDom & removeHoverDom used by couple
        addHoverDom: addHoverDom,
        removeHoverDom: removeHoverDom,
        selectedMulti: false
    },
    edit: {
        enable: true,
        showRemoveBtn: showRemoveBtn,
        // showRenameBtn: showRenameBtn
    },
    data: {
        key: {
            name: "deptName"
        },
        simpleData: {
            enable: true,
            idKey: "deptId",
            pIdKey: "parentId",
			rootPId: 0,     // rootPId should be declared, if not bug occurred
        }
    },
    callback: {
        beforeDrag: beforeDrag,
        beforeEditName: beforeEditName,
        beforeRemove: beforeRemove,
        beforeRename: beforeRename,
        onRemove: onRemove,
        onRename: onRename
    }
};


// set zTree style and show log information
var log, className = "dark";


// beforeDrag event, disable drag event
function beforeDrag(treeId, treeNodes) {
    return false;
}


// beforeEditName event, check whether node is editible
function beforeEditName(treeId, treeNode, newName) {
    className = (className === "dark" ? "":"dark");
    var zTree = $.fn.zTree.getZTreeObj("treeDemo");
    zTree.selectNode(treeNode);
   
    // issues: https://stackoverflow.com/questions/48872448/how-to-make-text-bold-with-sweetalert-in-2018
    var content = document.createElement('div');
    content.innerHTML = '确定要编辑【<strong style="color:red">' + treeNode.deptName + '</strong>】信息吗?';
    swal({
        title: "编辑部门信息",
        content: content,
        buttons: ["取消", "确定"],
    })
    .then((value) => {
        if(value == true) {
            zTree.editName(treeNode);
        }
    });
            
    // set swal-title css
    $(".swal-title").css({
        "margin": "0px",
        "font-size": "16px",
        "box-shadow": "0px 1px 1px rgba(0, 0, 0, 0.21)",
        "margin-bottom": "28px",
    });
    return false;
}


/**
 *  beforeRename event, check if new name is null or has already exist on the current parent node
 *  setTimeout prevent quick execute next command
 */ 
function beforeRename(treeId, treeNode, newName, isCancel) {
    className = (className === "dark" ? "":"dark");

    // check cn and en special characters, maybe others to be added after like jp, kor...
    var pattern = new RegExp("[`~!@#%$^&*()=|{}':;',\\[\\].<>/?～！@#%￥……&*（）——|{}_【】“”‘「」；：`。，、？《》〈〉-]"); 

    var zTree = $.fn.zTree.getZTreeObj("treeDemo");

    // check order can't be changed, newName check must be the last
    if (newName.length == 0) {
        setTimeout(function() {
            swal({
                title: "操作失败",
                text: "部门名称不能为空,请重试！",
                icon: "error",
            }).then((value) => {
                if(value === true) {
                    zTree.editName(treeNode);
                }
            });         
        }, 10);

        return false;
    } else if((newName.indexOf(' ') != -1) || pattern.test(newName)) {
        setTimeout(function() {
            swal({
                title: "操作失败",
                text: "部门名称中含有特殊字符,请重试！",
                icon: "error",
            }).then((value) => {
                if(value === true) {
                    zTree.editName(treeNode);
                }
            });         
        }, 10);

        return false;
    } else if(treeNode.deptName === newName) {  // if dept name change nothing, then return true
        return true;
    }

    // get parent node info, if check current treeNode, rename will be useless, getNodeByParam() uses parentNode better
    // parentNode contains children: {...}, leave node 
    var parentNode = treeNode.getParentNode();

    // check if new depart has already exist in the current parent node
    // if it doesn't exist, checkNode is null, else checkNode is an Object
    var checkNode = zTree.getNodeByParam("deptName", newName, parentNode);

    if(checkNode != null) {
        // https://stackoverflow.com/questions/44118673/sweet-alert-immidiately-closed-before-click-ok-button-or-hit-enter-when-sweet-al
        // fix sweetalert trigger event again with enter key
        // use setTimeout function with 10ms
        setTimeout(function() {
            swal({
                title: "操作失败",
                text: "当前上级部门中已有您输入的部门，请重试！",
                icon: "error",
            }).then((value) => {
                if(value === true) {
                    zTree.editName(treeNode);
                }
            });         
        }, 10);
        return false;
    }
    return true;
}

// rename node, edit deptName and refresh current page
function onRename(e, treeId, treeNode, isCancel) {
    // get dept info from treeNode
    if(!isCancel) { // isCancel means beforeRename->newName is null or undefined
        var deptObj = {
            deptId: treeNode.deptId,
            deptName: treeNode.deptName,
            parentId: treeNode.parentId
        }

        // post deptObj to back-end
        $.ajax({
            type: "post",
            url: "/dept/edit",
            data: deptObj,
            success: function (json) {
                if(json.status == 200) {
                    swal("操作成功!", json.message, "success")
                    .then((value) => {
                        location.reload();
                    });
                } else if(json.status == 500) {
                    swal("操作失败!", json.message, "error")
                }
            },
        });
    }    
}


// beforeRemove event, show return value to onRemove event
function beforeRemove(treeId, treeNode) {
    className = (className === "dark" ? "":"dark");
    var zTree = $.fn.zTree.getZTreeObj("treeDemo");
    zTree.selectNode(treeNode);

    // remove node can be implemented in this method
    // beforeRemove may be better than onRemove

    // issue: https://stackoverflow.com/questions/48872448/how-to-make-text-bold-with-sweetalert-in-2018
    var content = document.createElement('div');
    content.innerHTML = '确定要删除【<strong style="color:red">' + treeNode.deptName + '</strong>】的信息吗?';
    swal({
        title: "删除部门信息",
        content: content,
        buttons: ["取消", "确定"],
    })
    .then((value) => {
        if(value == true) {
            $.ajax({
                type: "post",
                url: "/dept/remove",
                data: {'ids': treeNode.deptId},
                success: function (json) {
                    if(json.status == 200) {
                        swal("操作成功!", json.message, "success")
                        .then((value) => {
                            location.reload();
                        });
                    } else if(json.status == 500) {
                        swal("操作失败!", json.message, "error")
                        .then((value) => {
                            location.reload();
                        });
                    }
                },
            });
        }
    });

    // set swal-title css
    $(".swal-title").css({
        "margin": "0px",
        "font-size": "16px",
        "box-shadow": "0px 1px 1px rgba(0, 0, 0, 0.21)",
        "margin-bottom": "28px",
    });
   
    return false;
}

// remove node event, remove deptObj and refresh current page
function onRemove(e, treeId, treeNode) {    
   // to be implemented future
}

// add new dept node
function addHoverDom(treeId, treeNode) {
    // prevent enter key [keydown] => enter input deptname [keyup] => submit null deptname, prompt deptname is null
    $(document).keypress(function(event) {
        if(event.which == 13) {
            event.preventDefault();
        }
        event.stopPropagation
    })

    var sObj = $("#" + treeNode.tId + "_span");
    if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
    var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
        + "' title='add node' onfocus='this.blur();'></span>";
    sObj.after(addStr);
    var btn = $("#addBtn_"+treeNode.tId);
    if (btn) btn.bind("click", function(event){
        var zTree = $.fn.zTree.getZTreeObj("treeDemo");
        
        // set regular expression pattern, exclude cn, en special characters
        var pattern = new RegExp("[`~!@#%$^&*()=|{}':;',\\[\\].<>/?～！@#%￥……&*（）——|{}_【】“”‘「」；：`。，、？《》〈〉-]"); 

        var content = document.createElement('div');
        content.innerHTML = '请选择添加【<strong style="color:red">总部门</strong>】还是【<strong style="color:red">普通部门</strong>】';

        swal({
            title: "添加新部门",
            content: content,
            buttons: ["总部门", "普通部门"],
        })
        .then((value) => {
            if(value == true) {
                swal("请输入普通部门名称：", {
                    content: {
                        element: "input",
                        attributes: {
                            placeholder: "普通部门名称",
                            type: "text"
                        }
                    },
                    closeOnEsc: false,
                    closeOnClickOutside: false,
                })
                .then((value) => {
                    if(value === '') {
                        swal("操作失败", "普通部门名称不能为空！", "error");
                        return false;
                    } else if(pattern.test(value) || (value.indexOf(' ') != -1)) {
                        swal("操作失败", "部门名称中含有特殊字符,请重试！", "error");
                        return false;
                    }

                    // check if newNode has exist in the current parent node
                    var checkNode = zTree.getNodeByParam("deptName", value, treeNode);
                    if(checkNode == null) {
                        var newObj = {
                            dept_id: null,
                            deptName: value,
                            parentId: treeNode.deptId
                        }

                        $.ajax({
                            type: "POST",
                            url: "/dept/add",
                            data: newObj,
                            success: function(json){
                                if(json.status == 200) {
                                    swal("操作成功!", json.message, "success")
                                    .then((value) => {
                                        location.reload();
                                    });
                                } else if(json.status == 500) {
                                    swal("操作失败!", json.message, "error")
                                    .then((value) => {
                                        location.reload();
                                    });
                                }
                            }
                        });
                    } else {
                        swal("操作失败", "当前上级部门中已有您输入的部门，请重试！", "error");
                    }
                });
            } else if(value == null) {
                swal("请输入总部门名称：", {
                    content: {
                        element: "input",
                        attributes: {
                            placeholder: "总部门名称",
                            type: "text"
                        }
                    },
                    closeOnEsc: false,
                    closeOnClickOutside: false
                })
                .then((value) => {
                    if(value === '') {
                        swal("操作失败", "总部门名称不能为空！", "error");
                        return false;
                    } else if(pattern.test(value) || (value.indexOf(' ') != -1)) {
                        swal("操作失败", "部门名称中含有特殊字符,请重试！", "error");
                        return false;
                    }

                    // headquaters must be unique, so check all nodes on the tree
                    var checkNode = zTree.getNodeByParam("deptName", value, null);
                    if( checkNode == null) {
                        var newObj = {
                            dept_id: null,
                            deptName: value,
                            parentId: 0
                        }

                        $.ajax({
                            type: "POST",
                            url: "/dept/add",   
                            data: newObj,
                            success: function(json){
                                if(json.status == 200) {
                                    swal("操作成功!", json.message, "success")
                                    .then((value) => {
                                        location.reload();
                                    });
                                } else if(json.status == 500) {
                                    swal("操作失败!", json.message, "error")
                                    .then((value) => {
                                        location.reload();
                                    });
                                }
                            }
                        });
                    } else {
                        swal("操作失败", "该总部门己存在，请重试！", "error");
                    }
                });
            }
        });

        return false;
    });
};

// removeHoverDom event
function removeHoverDom(treeId, treeNode) {
    $("#addBtn_"+treeNode.tId).unbind().remove();
};

// show remove button
function showRemoveBtn(treeId, treeNode) {
    // if node has childrenNode, then do not show remove button
    return !treeNode.isParent;
}

// show rename button, method reserved
function showRenameBtn(treeId, treeNode) {
    return !treeNode.isLastNode;
}

// initialize dept ztree
$(document).ready(function(){
    $.ajax({
        type: "POST",
        url: '/dept/getDeptTree',
        dataType: "json",
        success: function(data) {
            var zNodes = [];
            zNodes = data;
            var tree = $.fn.zTree.init($("#treeDemo"), setting, zNodes);
            tree.expandAll(true);
        }
    });         
});

/** ************ methods below are reserved *********** **/

// selectAll event, but method reserved
function selectAll() {
    var zTree = $.fn.zTree.getZTreeObj("treeDemo");
    zTree.setting.edit.editNameSelectAll =  $("#selectAll").attr("checked");
}

// show log info
function showLog(str) {
    if (!log) log = $("#log");
    log.append("<li class='"+className+"'>"+str+"</li>");
    if(log.children("li").length > 8) {
        log.get(0).removeChild(log.children("li")[0]);
    }
}

// get current time
function getTime() {
    var now= new Date(),
    h=now.getHours(),
    m=now.getMinutes(),
    s=now.getSeconds(),
    ms=now.getMilliseconds();
    return (h+":"+m+":"+s+ " " +ms);
}