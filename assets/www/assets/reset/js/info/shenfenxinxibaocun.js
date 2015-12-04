$(function(){
	getLocalUser(function(user){
		if(user){
			$("#name").html(user.name);
			if(user.companyInfo){
				var companyInfo = user.companyInfo;
				if(companyInfo.complays&&(!companyInfo.complay)){
					
				}
			}else{
				$("#company").html(user.companyName);
				$("#branchCompany").html(user.branchCompanyName);
				$("#dept").html(user.departmentName);
			}
			$("#empId").html(user.clerkId);
		}
	});
});