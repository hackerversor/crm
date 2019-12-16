/*
 * login-register modal
 * Autor: Creative auv
 * Web-autor: creative.auv
 * Web script: #
 */


function showRegisterForm(){
    $('.loginBox').fadeOut('fast',function(){
        $('.registerBox').fadeIn('fast');
        $('.login-footer').fadeOut('fast',function(){
            $('.register-footer').fadeIn('fast');
        });
        $('.division').html('请注册');
    }); 
    $('.error').removeClass('alert alert-danger').html('');
       
}
function showLoginForm(){
    $('#loginModal .registerBox').fadeOut('fast',function(){
        $('.loginBox').fadeIn('fast');
        $('.register-footer').fadeOut('fast',function(){
            $('.login-footer').fadeIn('fast');    
        });
        $('.division').html('请登陆');
    });       
     $('.error').removeClass('alert alert-danger').html(''); 
}

function openLoginModal(){
    showLoginForm();
    setTimeout(function(){
        $('#loginModal').modal('show');    
    }, 230);
    
}
function openRegisterModal(){
    showRegisterForm();
    setTimeout(function(){
        $('#loginModal').modal('show');    
    }, 230);
    
}

//需要在加载点击登录之前校验
function shakeModal(){
    $('#loginModal .modal-dialog').addClass('shake');
    $('.error').addClass('alert alert-danger').html("无效的邮箱/密码组合");
    $('input[type="password"]').val('');
    setTimeout( function(){ 
         $('#loginModal .modal-dialog').removeClass('shake'); 
    }, 1000 ); 
}


//提交	
$(function(){
	$(".btn-login").click(function(){
		LOGIN.login();
	});
});

var redirectUrl = "${redirect}";//登录失败打回登录页,需要后端做重定向操作
var LOGIN = {
	login:function() {
		if (this.checkInput()) {
			this.doLogin();
		}
	},
	checkInput:function() {
		if ($("#username").val() == "") {
			alert("用户名不能为空");
			$("#username").focus();
			return false;
		}
		if ($("#password").val() == "") {
			alert("密码不能为空");
			$("#password").focus();
			return false;
		}
		return true;
	},
	doLogin:function() {
		$.post("/login", $("#loginform").serialize(),function(data){
			alert("登录成功！");
//			alert("重定向地址"+redirectUrl);
//			if (redirectUrl == "") {
//				location.href = "http://localhost:8080/index.html";
//			} else {
//				location.href = redirectUrl;
//			}
			window.location = "/index.html";
		});
	}
	
};

