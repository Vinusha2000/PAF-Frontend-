$(document).on("click", "#btnSave", function(event)
{ 
// Clear alerts---------------------
 $("#alertSuccess").text(""); 
 $("#alertSuccess").hide(); 
 $("#alertError").text(""); 
 $("#alertError").hide(); 
// Form validation-------------------
var status = validatePaymentForm(); 
if (status != true) 
 { 
 $("#alertError").text(status); 
 $("#alertError").show(); 
 return; 
 } 
// If valid------------------------
var type = ($("#hidPaymentIDSave").val() == "") ? "POST" : "PUT"; 
 $.ajax( 
 { 
 url : "PaymentsAPI", 
 type : type, 
 data : $("#formPayment").serialize(), 
 dataType : "text", 
 complete : function(response, status) 
 { 
 onPaymentSaveComplete(response.responseText, status); 
 } 
 }); 
});

function onPaymentSaveComplete(response, status)
{ 
if (status == "success") 
 { 
 var resultSet = JSON.parse(response); 
 if (resultSet.status.trim() == "success") 
 { 
 $("#alertSuccess").text("Successfully saved."); 
 $("#alertSuccess").show(); 
 $("#divPaymentsGrid").html(resultSet.data); 
 } else if (resultSet.status.trim() == "error") 
 { 
 $("#alertError").text(resultSet.data); 
 $("#alertError").show(); 
 } 
 } else if (status == "error") 
 { 
 $("#alertError").text("Error while saving."); 
 $("#alertError").show(); 
 } else
 { 
 $("#alertError").text("Unknown error while saving.."); 
 $("#alertError").show(); 
 }
$("#hidPaymentIDSave").val(""); 
$("#formPayment")[0].reset(); 
}


// UPDATE==========================================
$(document).on("click", ".btnUpdate", function(event)
		{ 
		$("#hidPaymentIDSave").val($(this).data("PaymentID")); 
		 $("#AccNumber").val($(this).closest("tr").find('td:eq(0)').text()); 
		 $("#DateOfPayment").val($(this).closest("tr").find('td:eq(1)').text()); 
		 $("#Amount").val($(this).closest("tr").find('td:eq(2)').text()); 
		 $("#TypeOfPayment").val($(this).closest("tr").find('td:eq(3)').text()); 
		});




$(document).on("click", ".btnRemove", function(event)
		{ 
		 $.ajax( 
		 { 
		 url : "PaymentsAPI", 
		 type : "DELETE", 
		 data : "PaymentID=" + $(this).data("PaymentID"),
		 dataType : "text", 
		 complete : function(response, status) 
		 { 
		 onPaymentDeleteComplete(response.responseText, status); 
		 } 
		 }); 
		});
		
function onPaymentDeleteComplete(response, status)
{ 
if (status == "success") 
 { 
 var resultSet = JSON.parse(response); 
 if (resultSet.status.trim() == "success") 
 { 
 $("#alertSuccess").text("Successfully deleted."); 
 $("#alertSuccess").show(); 
 $("#divPaymentsGrid").html(resultSet.data); 
 } else if (resultSet.status.trim() == "error") 
 { 
 $("#alertError").text(resultSet.data); 
 $("#alertError").show(); 
 } 
 } else if (status == "error") 
 { 
 $("#alertError").text("Error while deleting."); 
 $("#alertError").show(); 
 } else
 { 
 $("#alertError").text("Unknown error while deleting.."); 
 $("#alertError").show(); 
 } 
}


// CLIENT-MODEL================================================================
function validateItemForm()
{
	// CODE
	if ($("AccNumber").val().trim() == "")
	{
	return "Insert Acc Number.";
	}
	// NAME
	if ($("#DateOfPayment").val().trim() == "")
	{
	return "Insert Date.";
}

// PRICE-------------------------------
if ($("#Amount").val().trim() == ""){
	return "Insert Amount.";
}
		// is numerical value
		var tmpAmount = $("#Amount").val().trim();
		if (!$.isNumeric(tmpAmount))
	{
	return "Insert a numerical value for Amount.";
	}
		
// convert to decimal price
$("#Amount").val(parseFloat(tmpAmount).toFixed(2));

// DESCRIPTION------------------------
if ($("#TypeOfPayment").val().trim() == ""){
	
	return "Insert TypeOfPayment.";
}
	return true;
}