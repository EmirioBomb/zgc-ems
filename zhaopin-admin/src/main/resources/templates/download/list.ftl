<#include "/layout/header.ftl"/>
<#--  if container doesn't contain whole page content, footer will be disgusting  -->
<div class="container">
    <div class="col-md-12 col-sm-12 col-xs-12" id="download-panel">
        <div class="panel panel-default">
            <div class="panel-heading">文件下载</div>
            <div class="panel-body">
                <li>说明: 从服务器下载文件</li>    
            </div>
           
            <div class="operation-btn-group">
                <input type="file" style="display: none;" id="open-image-file" name="image" accept="image/*">
                <button style="margin-bottom: 0" id="open-file-btn" class="btn btn-success">打开图片</button>
                <button style="margin-bottom: 0" id="upload-image-btn" class="btn btn-danger fa fa-cloud-upload">上传图片</button>
                <button style="margin-bottom: 0" id="clear-area-btn" class="btn btn-dark fa fa-times">清空</button>
            </div>
            
        </div>  
    </div>

</div>

<#include "/layout/footer.ftl"/>
