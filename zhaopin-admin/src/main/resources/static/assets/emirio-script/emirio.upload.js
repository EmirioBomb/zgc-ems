/**
 * emirio.upload.js
 * Convert images to Base64 code
 * 
 * 1. Preview image
 * 2. Show image's Base64 code
 * 3. Upload image to Back-End /upload folder as Base64
 * 4. clear image and Base64 code area
 * 
 * @author emirio
 * @date 2020/5/27
 * @version v1.1
 * @since v1.0
 */


/**
 * open file trigger
 */
 $("#open-file-btn").on('click', function() {
    // it must be trigger, not 'click'
    $("#open-image-file").trigger('click');
});

/**
 * open file and show preview image
 */
var formData ;
$("#open-image-file").on("change", function (e) { 
    // create FormData 
    formData = new FormData();
    formData.append("image", $("#open-image-file")[0].files[0]);

    // setting as follows to get the first file
    // var selectFiles = $("#open-image-file")[0].files[0];

    // get all select files
    var selectFiles = document.getElementById("open-image-file").files;

    // set max file size as 1mb
    var maxFileSize = 1048576;
    if(selectFiles.length > 0) {
        // select first select file, or user above method
        var file = selectFiles[0];

        // check file size, because large file cost more time to covert to Base64
        if((file.size > maxFileSize) && (file.szie === 0)) {
            Swal.fire({
                icon: 'warning',
                title: '文件过大',
                html:
                    '请选择小于1MB以下的图片文件！',
                    confirmButtonText: '确定',
            }).then((result) => {
                location.reload();
            });
            // it will show image if without return;
            return;
        }

        var fileReader = new FileReader();

        fileReader.onload = function(event) {
            var base64value = event.target.result;
            $("#image-preview").show();
            $("#image-preview").attr('src', base64value);
        }
        fileReader.readAsDataURL(file);
    }
});
    
/**
 * upload image to back-end /upload folder
 */
$("#upload-image-btn").on('click', function() {
    var imgSource = $("#image-preview").attr('src');
    if(imgSource === "") {
        Swal.fire({
            icon: 'info',
            title: '没有检测到图片',
            html:
                '请先打开图片, 支持的格式有 .jpg/.jpeg/.gig/.png/.ico !',
            confirmButtonText: '确定',
        });
    } else {
        $.ajax({
            async: true,
            crossDomain: true,
                url: "/upload/image",
            type: "post",
            data: formData,
            processData: false,
            contentType: false,
            success: function(data) {
                if(data === 0) {
                    Swal.fire({
                        icon: 'error',
                        title: '上传文件失败',
                        confirmButtonText: '确定',
                    });
                } else if(data === -1) {
                    Swal.fire({
                        icon: 'warning',
                        title: '上传文件已存在',
                        confirmButtonText: '确定',
                    });
                } else if(data === 1) {
                    Swal.fire({
                        icon: 'success',
                        title: '上传文件成功',
                        confirmButtonText: '确定',
                    });
                }

                
            }
        });
    }
});

/**
 * convert image to base64
 */
$("#img-to-base64-btn").on('click', function() {
    var imgSource = $("#image-preview").attr('src');
    if(imgSource === "") {
        Swal.fire({
            icon: 'info',
            title: '没有检测到图片',
            html:
                '请先打开图片, 支持的格式有 .jpg/.jpeg/.gig/.png/.ico !',
            confirmButtonText: '确定',
        });
    } else {
        $("#textarea-form").val(imgSource);
    }
});

/**
 * check if base64 is mateched to data:image/*;base64
 * @param {base64 textarea} base64 
 */
function isBase64(base64) {
    var reg = /^\s*data:([a-z]+\/[a-z0-9-+.]+(;[a-z-]+=[a-z0-9-]+)?)?(;base64)?,([a-z0-9!$&',()*+;=\-._~:@\/?%\s]*?)\s*$/i;
    if(reg.test(base64)) {
        return true;
    } else {
        return false;
    }
}

/**
 * convert base64 to image
 */
$("#base64-to-img-btn").on('click', function() {
    var textValue = $("#textarea-form").val();
    
    if (textValue === "") {
        Swal.fire({
            icon: 'info',
            title: '没有检测到Base64代码',
            html:
                '目前支持的图片格式为: .jpg/.jpeg/.gig/.png/.ico !',
            confirmButtonText: '确定',
        });
    } else if (!isBase64(textValue)) {
        Swal.fire({
            icon: 'warning',
            title: '非法的Base64图像编码',
            html:
                '编码以 <span style="color: red">data:image/*;base64,</span>开头的base64编码',
            confirmButtonText: '确定'
        }).then((result) => {
            $("#textarea-form").val("");
        })
    } else {
        $("#image-preview").show();
        $("#image-preview").attr('src', textValue);
    }
});

/**
 * clear image-preview and textarea content
 */
$("#clear-area-btn").on('click', function() { 
    $("#image-preview").attr('src', "");
    $("#image-preview").hide();
    $("#textarea-form").val("");
});

