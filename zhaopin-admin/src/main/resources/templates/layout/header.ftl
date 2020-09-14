<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <#--  <meta http-equiv="Content-Type" content="multipart/form-data; charset=UTF-8">  -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>中关村招聘平台</title>
    <link href="/assets/images/favicon.ico" rel="icon"">
    <link href="/assets/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    
    <#--  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" >  -->
    <link href="/assets/bootstrap/3.3.0/css/bootstrap.min.css" rel="stylesheet">
    <link href="/assets/jquery-confirm/2.5.1/jquery-confirm.min.css" rel="stylesheet">
    <#--  <link href="/assets/jquery-confirm/3.3.4/jquery-confirm.min.css" rel="stylesheet">  -->
    <link href="/assets/fancybox/2.1.5/jquery.fancybox.min.css" rel="stylesheet">
    <link href="/assets/nprogress/0.2.0/nprogress.min.css" rel="stylesheet">
    <link href="/assets/toastr.js/2.0.3/css/toastr.min.css" rel="stylesheet">
    <link href="/assets/iCheck/1.0.2/skins/square/green.css" rel="stylesheet">
    <#--  <link href="/assets/bootstrap-table/1.11.1/bootstrap-table.min.css" rel="stylesheet">  -->
        
    <!--  import bootstrap-table css  -->
    <link href="/assets/emirio-css/bootstrap-table.min.css" rel="stylesheet">

    <link href="/assets/bootstrap-daterangepicker/2.1.24/daterangepicker.min.css" rel="stylesheet">
    <link href="/assets/bootstrap-datetimepicker/4.17.47/css/bootstrap-datetimepicker.min.css" rel="stylesheet">
    <link href="/assets/zTree.v3/3.5.29/css/metroStyle/metroStyle.min.css" rel="stylesheet">
    <link href="/assets/emirio-css/bootstrap-datetimepicker.min.css" rel="stylesheet">
    
    <#--  import treegrid css  -->
    <link href="/assets/css/jquery.treegrid.css" rel="stylesheet">
    <link href="/assets/css/zhyd.core.css" rel="stylesheet">
    <link href="/assets/emirio-css/comboTree.css" rel="stylesheet">
    <style>
    .btn-danger {
        font-weight: bold;
    }
    .btn-primary {
        font-weight: bold;
    }
    </style>
</head>
<body class="nav-md">
<div class="container body">
    <div class="main_container">
        <div class="col-md-3 left_col">
            <div class="left_col scroll-view">
                <div class="navbar nav_title" style="border: 0;">
                    <a href="/" class="site_title"><i class="fa fa-coffee"></i> <span>中关村招聘管理</span></a>
                </div>
                <div class="clearfix"></div>
                <div class="profile clearfix">
                    <div class="profile_pic">
                        <img src="/assets/images/${user.username}.png" alt="..." class="img-circle profile_img">
                    </div>
                    <div class="profile_info">
                        <span>您好！</span>
                        <h2>尊敬的管理员</h2>
                    </div>
                </div>
                <br />
            <#include "/layout/sidebar.ftl"/>
            </div>
        </div>
        <#include "/layout/setting.ftl"/>
        <div class="right_col" role="main">