var categoryarray = [];
var different = false;
$(function() {
	var modifyform = $("#modifyform1").dialog({
		autoOpen : false,
		height : 150,
		width : 265,
		modal : true,
		buttons : {
			"확인" : function() {
				//$( "#dialog-upload-form form" ).submit();
				$("#modifypost").submit();
				$(this).dialog("close");
			},
			"취소" : function() {
				$(this).dialog("close");
			}
		},

		close : function() {

		}
	});
	
	$("#reset").click(function(){
		var rsp = {password:$("#resetpassword").val()};
		
		$.ajax( {
		    url :  "/account-book/" + currentid + "/reset",
		    type: "POST",
		    dataType: "JSON",
		    data: JSON.stringify(rsp),
		    contentType: "application/json; charset=UTF-8",
		    success: function( response ){
		    	console.log(response);
		    	if(response.result == "fail"){
		    		alert("비밀번호를 확인해주세요.");
			    	return;
			    }
		    	if(response.result == "success"){
		    		alert("초기화 되었습니다.");
		    		different = true;
			    	return;
			    }
		    },
		    error: function( XHR, status, error ){
		       console.error( status + " : " + error );	       
		    }
		});
	});

	
	$("#limitmodify").submit(function(){
		
		if(String(Number($("#limit").val())) == "NaN"){
			alert("숫자로만 입력해주세요.");
			$("#limit").focus();
			return false;
		}
		
	});
	
	for (var i = 0; i < categoryarray.length; i++) {
		var number = categoryarray[i];

		$("#" + categoryarray[i]).click(function(number) {
			modifyform.dialog("open");
			var categoryid = {
				"categoryId" : number.target.id
			};

			$.ajax({
				url : "/account-book/" + currentid + "/categoryModify",
				type : "POST",
				dataType : "JSON",
				data : JSON.stringify(categoryid),
				contentType : "application/json; charset=UTF-8",
				success : function(response) {
					console.log(response);
					$("#categoryId").val(response.data.categoryId);
					$("#category").val(response.data.category);
				},
				error : function(XHR, status, error) {
					console.error(status + " : " + error);
				}
			});
		});
	}
});