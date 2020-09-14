<#include "/layout/header.ftl"/>
<#--  <div class="row">  -->
    <#--  show employee charts  -->
    <#--  <div class="col-md-12 col-sm-12 col-xs-12">
        <ol class="breadcrumb">
            <li><a href="/">首页</a></li>
            <li><a href="/employee">员工信息</a></li>
            <li class="active">信息统计</li>
        </ol>
    </div>   
</div>  -->


<div class="row">
    <div class="col-md-12 col-sm-12 col-xs-12">
        <div class="x_panel">
            <div class="x_title">
                <div class="btn-group" role="group" aria-label="button-group">
                    <button type="button" class="btn btn-primary btn-area">员工出身分布</button>
                    <button type="button" class="btn btn-success btn-annual-salary">年度薪资统计</button>
                    <button type="button" class="btn btn-warning btn-salary">部门薪资预览</button>
                </div>
            </div>
            <div class="x-content" id="area-chart" style="height: 400px"></div>
        </div>
    </div>
</div>

<div class="row">
    <div class="col-md-7 col-sm-7 col-xs-7">
        <div class="x_panel">
            <div class="x_title">
                <div class="btn-group" role="group" aria-label="button-group">
                    <button type="button" class="btn btn-primary btn-number">总比例统计</button>
                    <button type="button" class="btn btn-warning btn-age">按年龄统计</button>
                    <button type="button" class="btn btn-success btn-gender">年龄段统计</button>
                </div>
            </div>
            <div class="x-content" id="sex-chart" style="height: 300px"></div>
        </div>
    </div>

    <div class="col-md-5 col-sm-5 col-xs-5">
        <div class="x_panel">
            <div class="x_title">
                <div class="btn-group" role="group" aria-label="button-group">
                    <button type="button" class="btn btn-primary btn-job">职位统计</button>
                    <button type="button" class="btn btn-warning btn-dept">部门统计</button>
                </div>
            </div>
            <div class="x-content" id="employee-chart" style="height: 300px"></div>
        </div>
    </div>

</div> 


<#include "/layout/footer.ftl"/>

<#--  echart is only used in /employee/info page  -->
<script src="/assets/emirio-script/echarts.min.js"></script>
<script src="/assets/emirio-script/emirio.echart.js"></script>
<script src="/assets/emirio-script/china.min.js"></script>



            