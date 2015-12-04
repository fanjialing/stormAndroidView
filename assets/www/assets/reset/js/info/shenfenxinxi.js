 var userName = GetRequest()["userName"];
var companyId = GetRequest()["companyId"];
$(function(){
	getLocalUser(function(user){
		if(user){
			$("#name").html(user.name);
			$("#company").html(user.companyName);
			$("#branchCompany").html(user.branchCompanyName);
			$("#dept").html(user.departmentName);
			$("#empId").html(user.clerkId);
		}
	});
});