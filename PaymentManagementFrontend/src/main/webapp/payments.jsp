<%@page import="com.Payment"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Payments Management</title>
<script src="Components/payments.js"></script>
<script src="Components/jquery-3.6.0.js"></script>
<script src="Components/jquery-3.6.0.min.js"></script>
<link rel="stylesheet" href="Views/Styles.css">
</head>

<body> 
<div class="container"><div class="row"><div class="col-6"> 
<h1>Payments Management</h1>
<form id="formPayment" name="formPayment" method="post" action="payments.jsp">
 Account Number:
 <input id="AccNumber" name="AccNumber" type="text" 
 class="form-control form-control-sm" style="min-height:20px;min-width:350px;">
 <br> <br> Date Of Payment: 
 <input id="DateOfPayment" name="DateOfPayment" type="date" 
 class="form-control form-control-sm" style="min-height:30px;min-width:350px;">
 <br> <br> Amount: 
 <input id="Amount" name="Amount" type="text" 
 class="form-control form-control-sm" style="min-height:20px;min-width:350px;">
 <br><br> Type Of Payment: 
 <select name="TypeOfPayment" id="TypeOfPayment" style="min-height:30px;min-width:350px;">
        <option value="card">Card</option>
        <option value="cash">Cash</option>
    </select>
 <br><br>
 <input id="btnSave" name="btnSave" type="button" value="Save" 
 class="btn btn-primary">
 
</form>
<div id="alertSuccess" class="alert alert-success"></div>
<div id="alertError" class="alert alert-danger"></div>
<br>
<div id="divPaymentsGrid">
 <%
 Payment paymentObj = new Payment(); 
 out.print(paymentObj.readPayments()); 
 %>
</div>
</div> </div> </div> 
</body>
</html>