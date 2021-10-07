$(function() {
    "use strict";

    $(".preloader").fadeOut();
    // this is for close icon when navigation open in mobile view
    $(".nav-toggler").on('click', function() {
        $("#main-wrapper").toggleClass("show-sidebar");
        $(".nav-toggler i").toggleClass("ti-menu");
    });
    $(".search-box a, .search-box .app-search .srh-btn").on('click', function() {
        $(".app-search").toggle(200);
        $(".app-search input").focus();
    });

    // ============================================================== 
    // Resize all elements
    // ============================================================== 
    $("body, .page-wrapper").trigger("resize");
    $(".page-wrapper").delay(20).show();
    
    //****************************
    /* This is for the mini-sidebar if width is less then 1170*/
    //**************************** 
    var setsidebartype = function() {
        var width = (window.innerWidth > 0) ? window.innerWidth : this.screen.width;
        if (width < 1170) {
            $("#main-wrapper").attr("data-sidebartype", "mini-sidebar");
        } else {
            $("#main-wrapper").attr("data-sidebartype", "full");
        }
    };
    $(window).ready(setsidebartype);
    $(window).on("resize", setsidebartype);

});

function checkProductName(){
	var productName = $("#productName").val();
	var productId = $("#productId").val();
		$.ajax({
			type: 'GET',
			url: 'http://localhost:8081/api/product',
			data: {
				name: productName,
				id: productId
			},
		
			
		}).done(function(data){
			$("#available").html(data);
			if(data == "This name had been used!")
			{
				$("#update-product-btn").attr('disabled', 'disabled');
			} 
			else
			{
				$("#update-product-btn").removeAttr('disabled');
			};
		});
};

function checkUsername(){
	var username = $("#username").val();
		$.ajax({
			type: 'GET',
			url: 'http://localhost:8081/api/user',
			data: {
				username: username,
			},
		}).done(function(data){
			$("#available-username").html(data);
			if(data == "This username has been taken!")
			{
				$("#username-validation").html("");
				$("#create-user-btn").attr('disabled', 'disabled');
			} 
			else
			{
				$("#create-user-btn").removeAttr('disabled');
			};
			if($("#username").val().indexOf(" ") > -1)
			{
				$("#username-validation").html("");
				$("#available-username").html("Spaces are not allowed");
				$("#create-user-btn").attr('disabled', 'disabled');
			}
		});
};

function checkIdentityNumber(){
	var identityNumber = $("#identity-number").val();
		$.ajax({
			type: 'GET',
			url: 'http://localhost:8081/api/identity',
			data: {
				identityNumber: identityNumber,
			},
		}).done(function(data){
			$("#available-id").html(data);
			if(data == "This ID number has been taken!")
			{
				$("#id-validation").html("");
				$("#create-user-btn").attr('disabled', 'disabled');
			} 
			else if($("#identity-number").val().indexOf(" ") > -1)
			{
				$("#id-validation").html("");
				$("#available-id").html("Spaces are not allowed");
				$("#create-user-btn").attr('disabled', 'disabled');
			}
			else
			{
				$("#create-user-btn").removeAttr('disabled');
			};
		});
};

function checkPhoneNumber(){
	var phoneNumber = $("#phone-number").val();
		$.ajax({
			type: 'GET',
			url: 'http://localhost:8081/api/phone',
			data: {
				phoneNumber: phoneNumber,
			},
		}).done(function(data){
			$("#available-phone").html(data);
			if(data == "This phone number has been taken!")
			{
				$("#phone-validation").html("");
				$("#create-user-btn").attr('disabled', 'disabled');
			} 
			else if($("#phone-number").val().indexOf(" ") > -1)
			{
				$("#phone-validation").html("");
				$("#available-phone").html("Spaces are not allowed");
				$("#create-user-btn").attr('disabled', 'disabled');
			}
			else
			{
				$("#create-user-btn").removeAttr('disabled');
			};
		});
};

function checkUserPhoneNumber(){
	var phoneNumber = $("#phone-number").val();
	var userId = $("#user-id").val();
		$.ajax({
			type: 'GET',
			url: 'http://localhost:8081/api/user/phone',
			data: {
				phoneNumber: phoneNumber,
				userId: userId
			},
		
			
		}).done(function(data){
			$("#available-phone").html(data);
			if(data == "This phone number has been taken!")
			{
				$("#phone-validation").html("");
				$("#update-user-btn").attr('disabled', 'disabled');
			} 
			else if($("#phone-number").val().indexOf(" ") > -1)
			{
				$("#phone-validation").html("");
				$("#available-phone").html("Spaces are not allowed");
				$("#update-user-btn").attr('disabled', 'disabled');
			}
			else
			{
				$("#update-user-btn").removeAttr('disabled');
			};
		});
};

function checkUserIdentityNumber(){
	var identityNumber = $("#identity-number").val();
	var userId = $("#user-id").val();
		$.ajax({
			type: 'GET',
			url: 'http://localhost:8081/api/user/identity',
			data: {
				identityNumber: identityNumber,
				userId: userId
			},
		
			
		}).done(function(data){
			$("#available-id").html(data);console.log($("#datepicker").value);
			if(data == "This ID number has been taken!")
			{
				$("#id-validation").html("");
				$("#update-user-btn").attr('disabled', 'disabled');
			} 
			else if($("#identity-number").val().indexOf(" ") > -1)
			{
				$("#id-validation").html("");
				$("#available-id").html("Spaces are not allowed");
				$("#update-user-btn").attr('disabled', 'disabled');
			}
			else
			{
				$("#update-user-btn").removeAttr('disabled');
			};
		});
};

function checkCode(){
	var code = $("#code").val();
		$.ajax({
			type: 'GET',
			url: 'http://localhost:8081/api/code',
			data: {
				code: code,
			},
		}).done(function(data){
			$("#available-code").html(data);
			if(data == "This code has been taken!")
			{
				$("#code-validation").html("");
				$("#add-product-btn").attr('disabled', 'disabled');
			} 
			else
			{
				$("#add-product-btn").removeAttr('disabled');
			};
			if($("#code").val().indexOf(" ") > -1)
			{
				$("#code-validation").html("");
				$("#available-code").html("Spaces are not allowed");
				$("#add-product-btn").attr('disabled', 'disabled');
			}
		});
};

function checkName(){
	var name = $("#name").val();
		$.ajax({
			type: 'GET',
			url: 'http://localhost:8081/api/name',
			data: {
				name: name,
			},
		}).done(function(data){
			$("#available-name").html(data);
			if(data == "This name has been taken!")
			{
				$("#name-validation").html("");
				$("#add-product-btn").attr('disabled', 'disabled');
			} 
			else
			{
				$("#add-product-btn").removeAttr('disabled');
			};
			
		});
};

function checkNewCustomerPhoneNumber(){
	var phoneNumber = $("#phoneNumber").val();
		$.ajax({
			type: 'GET',
			url: 'http://localhost:8081/api/customer/new_phone_number',
			data: {
				phoneNumber: phoneNumber,
			},
		}).done(function(data){
			$("#available-phone").html(data);
			if(data == "This phone number has been taken!")
			{
				$("#phone-validation").html("");
				$("#create-customer-btn").attr('disabled', 'disabled');
			} 
			else if($("#phoneNumber").val().indexOf(" ") > -1)
			{
				$("#phone-validation").html("");
				$("#available-phone").html("Spaces are not allowed");
				$("#create-customer-btn").attr('disabled', 'disabled');
			}
			else
			{
				$("#create-customer-btn").removeAttr('disabled');
			};
			
		});
};

function checkNewCustomerEmail(){
	var email = $("#email").val();
		$.ajax({
			type: 'GET',
			url: 'http://localhost:8081/api/customer/new_email',
			data: {
				email: email,
			},
		}).done(function(data){
			$("#available-email").html(data);
			if(data == "This email has been taken!")
			{
				$("#email-validation").html("");
				$("#create-customer-btn").attr('disabled', 'disabled');
			} 
			else if($("#email").val().indexOf(" ") > -1)
			{
				$("#email-validation").html("");
				$("#available-email").html("Spaces are not allowed");
				$("#create-customer-btn").attr('disabled', 'disabled');
			}
			else
			{
				$("#create-customer-btn").removeAttr('disabled');
			};
			
		});
};

function checkCustomerPhoneNumber(){
	var phoneNumber = $("#phoneNumber").val();
	var customerId = $("#customer-id").val();
		$.ajax({
			type: 'GET',
			url: 'http://localhost:8081/api/customer/phone_number',
			data: {
				phoneNumber: phoneNumber,
				customerId: customerId,
			},
		}).done(function(data){
			$("#available-phone").html(data);
			if(data == "This phone number has been taken!")
			{
				$("#phone-validation").html("");
				$("#update-customer-btn").attr('disabled', 'disabled');
			} 
			else if($("#phoneNumber").val().indexOf(" ") > -1)
			{
				$("#phone-validation").html("");
				$("#available-phone").html("Spaces are not allowed");
				$("#update-customer-btn").attr('disabled', 'disabled');
			}
			else
			{
				$("#update-customer-btn").removeAttr('disabled');
			};
			
		});
};

function checkRoleName(){
	var roleName = $("#role-name").val();
	var roleId = $("#role-id").val();
		$.ajax({
			type: 'GET',
			url: 'http://localhost:8081/api/role_name',
			data: {
				roleName: roleName,
				roleId: roleId,
			},
		}).done(function(data){
			$("#available-role-name").html(data);
			if(data == "This name has been taken!")
			{
				$("#role-name-validation").html("");
				$("#update-role-btn").attr('disabled', 'disabled');
			} 
			else
			{
				$("#update-role-btn").removeAttr('disabled');
			};
			
		});
};

function checkNewRoleName(){
	var roleName = $("#role-name").val();
		$.ajax({
			type: 'GET',
			url: 'http://localhost:8081/api/new_role_name',
			data: {
				roleName: roleName,
			},
		}).done(function(data){
			$("#available-role-name").html(data);
			if(data == "This name has been taken!")
			{
				$("#role-name-validation").html("");
				$("#create-role-btn").attr('disabled', 'disabled');
			} 
			else
			{
				$("#create-role-btn").removeAttr('disabled');
			};
			
		});
};

function checkNewCategoryCode(){
	var categoryCode = $("#category-code").val();
		$.ajax({
			type: 'GET',
			url: 'http://localhost:8081/api/new_category_code',
			data: {
				categoryCode: categoryCode,
			},
		}).done(function(data){
			$("#available-category-code").html(data);
			if(data == "This code has been taken!")
			{
				$("#category-code-validation").html("");
				$("#create-category-btn").attr('disabled', 'disabled');
			} 
			else if($("#category-code").val().indexOf(" ") > -1)
			{
				$("#category-code-validation").html("");
				$("#available-category-code").html("Spaces are not allowed");
				$("#create-category-btn").attr('disabled', 'disabled');
			}
			else
			{
				$("#create-category-btn").removeAttr('disabled');
			};
			
		});
};

function checkNewCategoryName(){
	var categoryName = $("#category-name").val();
		$.ajax({
			type: 'GET',
			url: 'http://localhost:8081/api/new_category_name',
			data: {
				categoryName: categoryName,
			},
		}).done(function(data){
			$("#available-category-name").html(data);
			if(data == "This name has been taken!")
			{
				$("#category-name-validation").html("");
				$("#create-category-btn").attr('disabled', 'disabled');
			} 
			else
			{
				$("#create-category-btn").removeAttr('disabled');
			};
			
		});
};

function checkCategoryName(){
	var categoryName = $("#category-name").val();
	var categoryId = $("#category-id").val();
		$.ajax({
			type: 'GET',
			url: 'http://localhost:8081/api/category_name',
			data: {
				categoryName: categoryName,
				categoryId: categoryId,
			},
		}).done(function(data){
			$("#available-category-name").html(data);
			if(data == "This name has been taken!")
			{
				$("#category-name-validation").html("");
				$("#update-category-btn").attr('disabled', 'disabled');
			} 
			else
			{
				$("#update-category-btn").removeAttr('disabled');
			};
			
		});
};






