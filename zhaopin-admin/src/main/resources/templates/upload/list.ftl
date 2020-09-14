<#include "/layout/header.ftl"/>
<#--  if container doesn't contain whole page content, footer will be disgusting  -->
<div class="container">
    <div class="col-md-12 col-sm-12 col-xs-12" id="upload-panel">
        <div class="panel panel-default">
            <div class="panel-heading">文件上传</div>
            <div class="panel-body">
                <li>功能1: 图片与Base64编码互相转换</li>    
                <li>功能2: 打开图片，上传当前预览区图片</li>
            </div>
            <#--  
            <form method="post" action="/upload/image" enctype="multipart/form-data">
                <input type="file" name="file"><br>
                <input type="submit" value="提交">
            </form>
            -->
            <div class="operation-btn-group">
                <input type="file" style="display: none;" id="open-image-file" name="image" accept="image/*">
                <button style="margin-bottom: 0" id="open-file-btn" class="btn btn-success">打开图片</button>
                <button style="margin-bottom: 0" id="upload-image-btn" class="btn btn-danger fa fa-cloud-upload">上传图片</button>
                <button style="margin-bottom: 0" id="img-to-base64-btn" class="btn btn-warning">图片>>BASE64</button>
                <button style="margin-bottom: 0" id="base64-to-img-btn" class="btn btn-info">BASE64>>图片</button>
                <button style="margin-bottom: 0" id="clear-area-btn" class="btn btn-dark fa fa-times">清空</button>
            </div>

            
        </div>  
    </div>

    <div class="col-md-6 col-sm-12 col-xs-12 text-center" id="image-preview-panel">
        <div class="panel panel-default">
            <div class="panel-heading">图片预览</div>
                <div style="height: 480px">
                    <#--  display: none, avoid to show broken image when src=""  -->
                    <img id="image-preview" style="width: 100%; height: 100%; display: none" src="">
                </div>
            </div>
        </div>

        <div class="col-md-6 col-sm-12 col-xs-12 text-center" id="base64Code">
            <div class="panel panel-default">
                <div class="panel-heading">Base64编码</div>
                    <div style="height: 480px">
                        <textarea id="textarea-form" placeholder="Base64 编码" class="form-control" style="resize: none; width: 100%; height: 100%;" ></textarea>
                    </div>
                </div>
            </div>

            
    </div>

</div>

<#include "/layout/footer.ftl"/>
