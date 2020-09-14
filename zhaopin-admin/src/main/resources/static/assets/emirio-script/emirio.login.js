/** 
 * login script for zhaopin login page by emirio
 * Features:
 *  1. wave background settings
 *  2. login form validation
 *  3. sweetalert settings
 *  4. animatied effect by js and css library
 * 
 *  Notice:
 *  1. sweetalert2 must before sweetalert.js
 * 
 * @author emirio
 * @version v1.1
 * @date 2020/5/14
 * @since ver 1.0
*/

// automatically load script when load page
(function ($) {
    "use strict";

    // show wave background
    VANTA.WAVES({
        el: "#wave",
        mouseControls: true,
        touchControls: true,
        minHeight: 200.00,
        minWidth: 200.00,
        scale: 1.00,
        scaleMobile: 1.00,
        color: 0x1d4f64,

        // generate random color
        // color: "#"+((1<<24)*Math.random()|0).toString(16),
        shininess: 81.00,
        waveHeight: 25.00,
        waveSpeed: 1,
        zoom: 1.02
    });


   // if username & password input is blured
    $('.input100').each(function(){
        $(this).on('blur', function(){
            if($(this).val().trim() != "") {
                $(this).addClass('has-val');
            }
            else {
                $(this).removeClass('has-val');
            }
        })    
    })
  
    document.onkeydown = function(event) {
        var keycode = (event.keyCode ? event.keyCode : event.which);
        if(keycode === 13) {
            $(".login100-form-btn").click();
        }
    }


    // validate form
    var input = $('.validate-input .input100');

    $('.validate-form').on('submit',function(e){   
        var check = true;
        for(var i=0; i<input.length; i++) {
            if(validate(input[i]) == false){
                showValidate(input[i]);
                check=false;
            }
        }
        if(check == true) {
            e.preventDefault();
            $.ajax({
                type: "POST",
                url: "/passport/signin",
                data: $("#login-form").serialize(),
                dataType: "json",
                success: function(json) {
                    checkLogin(json);
                } 
            });
        }
        return check;
    });


    // check if login status is success or failed
    function checkLogin(result) {
        // prevet kyedown event temporary
        // $(window).keydown(function(e){
        //     if(e.keyCode === 27) {
        //         e.preventDefault();
        //     }
        // }); 

        // const Toast = Swal.mixin({
        //     toast: true,
        //     position: 'center',
        //     showConfirmButton: false,
        //     timer: 2000,
        //     timerProgressBar: true,
        //     allowEscapeKey: false,
        //     allowEnteryKey: false,
        //     allowOutsideClick: false, 
        // });
        

        if(result.status === 200) {
            // Toast.fire({
            //     icon: 'success',
            //     title: '恭喜您, 登陆成功！',
                
            // }).then((value) => {
            //     window.location.href = "/";
            // });
            
            Swal.fire({
                title: "恭喜您成功登陆本系统！",
                icon: "success",
                timer: 2000,
                showConfirmButton: false,
                allowEscapeKey: false,
                allowOutsideClick: false
            })
            .then((value) => {
                // you'd better add then statement,
                // icon loading will disappear quickly, if window.loacation is outside of then statement.
                window.location.href = "/";
            });
        } else {
            Swal.fire({
                title: "登陆失败",
                text: "用户名或密码不正确！",
                icon: "error",
                timer: 2000,
                showConfirmButton: false,
                allowEscapeKey: false,
                allowOutsideClick: false,
            });

            // Toast.fire({
            //     icon: 'error',
            //     title: '抱歉, 登陆失败！',
            //     allowEscapeKey: false,
            //     allowOutsideClick: false, 
            // });
        }
    }

    // if element get focused, then hide validate info
    $('.validate-form .input100').each(function(){
        $(this).focus(function(){
           hideValidate(this);
        });
    });

    // validate email format
    function validate (input) {
        if($(input).attr('type') == 'email' || $(input).attr('name') == 'email') {
            if($(input).val().trim().match(/^([a-zA-Z0-9_\-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([a-zA-Z0-9\-]+\.)+))([a-zA-Z]{1,5}|[0-9]{1,3})(\]?)$/) == null) {
                return false;
            }
        }
        else {
            if($(input).val().trim() == ''){
                return false;
            }
        }
    }

    // show validate alert info
    function showValidate(input) {
        var thisAlert = $(input).parent();

        $(thisAlert).addClass('alert-validate');
    }

    // hide validate alert info
    function hideValidate(input) {
        var thisAlert = $(input).parent();

        $(thisAlert).removeClass('alert-validate');
    }  
})(jQuery);