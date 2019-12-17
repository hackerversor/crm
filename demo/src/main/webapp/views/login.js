/**
 * 
 */
$().ready(function(){
	
	$("#loginform").submit(function(){
		$("#sub-btn").button("loading");
		$.ajax({
			url:path + "/login",
			data:$('#loginform').serialize(),
			async :false,
			type:'post',
			dataType:'json',
			error:function(XMLHttpRequest, textStatus, errorThrown){
				$.MsgBox.Alert("提示", "系统访问异常，请联系管理员！");	
				$("#sub-btn").button("reset");
			},
			
			success:function(checkRes){
				console.log(1111111111);
				//登录失败，提示错误信息
				if(checkRes.success !="true" && checkRes.success != true){
					
					$("#login-message").empty();
					$("#login-message").append(checkRes.msg);
					$("#login-message").show();
					$("#sub-btn").button("reset");
				}
				else{
					console.log(2222222222);
					var msg = checkRes.msg;
					msg = $.trim(msg);
					
					//如果有提示信息，则弹出信息
					if(msg != null && msg !="" && msg !="null" && msg != undefined && msg != "undefined"){
						$.MsgBox.Alert("提示", msg);	

					}
					console.log(3333333333);
					window.location = path + "/index.html";
				}
			}
		});
		
		return false;
	});
});

