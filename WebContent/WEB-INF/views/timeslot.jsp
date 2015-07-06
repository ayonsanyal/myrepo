<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Choose a Time Slot and Book</title>
<link rel="stylesheet"
	href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
<link type="text/css"
	href="http://css/ui-lightness/jquery-ui-1.8.11.custom.css"
	rel="stylesheet" />
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>

<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>


<script type="text/javascript">
	$(document)
			.ready(
					function() {

						$("#orderdate").datepicker({
							changeMonth : true,//this option for allowing user to select month
							changeYear : true
						//this option for allowing user to select from year range
						});
						/* For the time slot window service */
						$("#timeslotwindow").hide();
						$('#timeslotpanel').submit(
								function(event) {

									var dateselected = $("#orderdate").val();
									var currentDate = new Date();
									var currentHour = currentDate.getHours();
									var currenthour12 = currentHour - 12;
									var req = {
										"todaysTime" : currenthour12,
										"orderDate" : dateselected
									};
									var reqfinal = JSON.stringify(req);
									$.ajax({
										url : $("#timeslotpanel")
												.attr("action"),
										data : reqfinal,
										type : "POST",

										beforeSend : function(xhr) {
											xhr.setRequestHeader("Accept",
													"application/json");
											xhr.setRequestHeader(
													"Content-Type",
													"application/json");
										},
										success : function(data) {

											populatetimeslots(data);
											$('#timeslotwindow').show();
										},

										error : function(e) {

											errormessage(e);

										}

									});
									event.preventDefault();

								});

						/* This function will populate the dropdown for the timeslots available */
						function populatetimeslots(data) {

							$.each(data, function(idx, obj) {

								var div_content = "<option>" + obj.timeSlot
										+ "</option>";

								$(div_content).appendTo('#timeslotdropdown');

							});
						}

						/* Function to call the booking url  */
						$('#timeslotbookingpanel').submit(
								function(event) {
									var timeslot = $("select#timeslotdropdown")
											.val();
									var timestamp;
									var dateforbooking = $("#orderdate").val();
									var date = new Date();
									var hour = date.getHours();
									if (hour >= 12) {
										hour = hour - 12;
										timestamp = hour + ":"
												+ date.getMinutes() + "PM";
									} else {
										timestamp = hour + ":"
												+ date.getMinutes() + "AM";
									}
									/* Assuming that the below parameters for the below url are present in the request */
									var requestparamforbooking = {
										"ordername" : "ayon",
										"timeslot" : timeslot,
										"items" : [

										{
											"name" : "ayon",
											"code" : "Rice",
											"quantity" : "10",
											"price" : "1500"
										}, {
											"name" : "ayon",
											"code" : "chocklat",
											"quantity" : "6",
											"price" : "4000"
										} ],

										"orderdate" : dateforbooking,
										"timestamp" : timestamp

									};
									var finalrequestparamforbooking = JSON
											.stringify(requestparamforbooking);
									$.ajax({
										url : $("#timeslotbookingpanel").attr(
												"action"),
										data : finalrequestparamforbooking,
										type : "POST",
										beforeSend : function(xhr) {
											xhr.setRequestHeader("Accept",
													"application/json");
											xhr.setRequestHeader(
													"Content-Type",
													"application/json");
										},
										success : function(data) {
											showconfirmation(data);
										},
										error : function(e) {

											bookingerrormessage(e);

										}

									});
									event.preventDefault();

								});

						$(function() {
							$("#confirmationpopup")
									.dialog(
											{
												autoOpen : false,
												buttons : {
													OK : function() {
														$(this).dialog("close");
													}
												},
												show : 'fade',
												modal : true,
												hide : 'fade',
												maxWidth : 500,
												maxHeight : 600,
												width : 500,
												height : 450,
												draggable : false,
												title : "Booking Confirmed ",
												position : {
													my : " center",
													at : " center"
												},
												open : function() {
													$('.ui-widget-overlay',
															this).hide()
															.fadeIn();

													$('.ui-icon-closethick')
															.bind(
																	'click.close',
																	function() {
																		$(
																				'.ui-widget-overlay')
																				.fadeOut(
																						function() {
																							$(
																									'.ui-icon-closethick')
																									.unbind(
																											'click.close');
																							$(
																									'.ui-icon-closethick')
																									.trigger(
																											'click');
																						});

																		return false;
																	});
												}

											});
						});

						$(function() {
							$("#errorpopup")
									.dialog(
											{
												autoOpen : false,
												buttons : {
													OK : function() {
														$(this).dialog("close");
													}
												},
												show : 'fade',
												hide : 'fade',
												modal : true,
												maxWidth : 500,
												maxHeight : 600,
												width : 500,
												height : 450,
												draggable : false,
												title : "Error",
												position : {
													my : " center",
													at : " center"
												},
												open : function() {
													$('.ui-widget-overlay',
															this).hide()
															.fadeIn();

													$('.ui-icon-closethick')
															.bind(
																	'click.close',
																	function() {
																		$(
																				'.ui-widget-overlay')
																				.fadeOut(
																						function() {
																							$(
																									'.ui-icon-closethick')
																									.unbind(
																											'click.close');
																							$(
																									'.ui-icon-closethick')
																									.trigger(
																											'click');
																						});

																		return false;
																	});
												}

											});
						});

						$(function() {
							$("#bookingerror")
									.dialog(
											{
												autoOpen : false,
												buttons : {
													OK : function() {
														$(this).dialog("close");
													}
												},
												show : 'fade',
												hide : 'fade',
												modal : true,
												maxWidth : 500,
												maxHeight : 600,
												width : 500,
												height : 450,
												draggable : false,
												title : "Error",
												position : {
													my : " center",
													at : " center"
												},
												open : function() {
													$('.ui-widget-overlay',
															this).hide()
															.fadeIn();

													$('.ui-icon-closethick')
															.bind(
																	'click.close',
																	function() {
																		$(
																				'.ui-widget-overlay')
																				.fadeOut(
																						function() {
																							$(
																									'.ui-icon-closethick')
																									.unbind(
																											'click.close');
																							$(
																									'.ui-icon-closethick')
																									.trigger(
																											'click');
																						});

																		return false;
																	});
												}

											});
						});
						
						function showconfirmation(data) {

							$
									.each(
											data,
											function(idx, obj) {
												var overlay_content_Bookingid = "<p>"
														+ "Congrats....     your order has been  booked with id"
														+ obj.bookingId + ".";

												var Other_Details = "For the name "
														+ ":"
														+ "--"
														+ obj.orderName
														+ ","
														+ "for the timeslot"
														+ ":"
														+ "-"
														+ obj.timeSlot
														+ ","

														+ "for the date"
														+ ":"
														+ "-"
														+ obj.orderdate
														+ "." +"at"+obj.timeStamp +"</p>";
												var finishing = "<p>"
														+ "your order will be delivered shortly."
														+ "</p>";
												var Final_Content = overlay_content_Bookingid
														+ Other_Details
														+ finishing;

												$("#confirmationpopup").html(
														Final_Content);
											});
							$("#confirmationpopup").dialog("open");
						}

						function errormessage(e) {
							var errorMessageHeading = "<H1>"
									+ "We apologize for inconvinience."
									+ "</H1>";
							var errorMessageMainBody = "<p>"
									+ "We do not take orders for past dates."
									+ "Future dates are acceptable but only till 7 days from today"
									+ "." + "</p>";
						var errorMessageMainBodyMainMessage="<p>"
							+ "Please Choose the date which is either today or the date within 7 days from today."
							+ "Thank You"
							+ "." + "</p>";			
							var errorMessage = errorMessageHeading 
									+ errorMessageMainBody+errorMessageMainBodyMainMessage;
							$("#errorpopup").html(errorMessage);
							$("#errorpopup").dialog("open");

						}
						
						function bookingerrormessage(e){
							
							var BookingerrorMessageHeading = "<H1>"
								+ "We apologize for inconvinience."
								+ "</H1>";
								
							var BookingerrorMessageMainBody=	"<p>"
								+ "It seems either vans are not available for delivery"
								+ "or some internal error occured.Please try after some time"
								+ "." + "</p>";
							var BookingErrorMessage =BookingerrorMessageHeading+BookingerrorMessageMainBody;
							$("#bookingerror").html(BookingErrorMessage);
							$("#bookingerror").dialog("open");
						}

					});
</script>
</head>




<body>
	<center>
		<form:form id="timeslotpanel"
			action="${pageContext.request.contextPath}/order/timeslots">
			<table>
				<tbody>
					<tr>
						<td>Choose the Date:</td>

						<td><input type="text" id="orderdate" /></td>

					</tr>
					<tr>
						<td><input type="submit" value="ShowTimeSlots" /></td>
						<td></td>
					</tr>

				</tbody>
			</table>
		</form:form>
	</center>

	<div id="timeslotwindow" align="center">
		<h1>Please Choose The available Timeslots</h1>
		<h2>The Slot window is available from 9AM to 6PM everyday</h2>
		<p>The slot between 1PM to 2 PM is not available</p>
		<form:form id="timeslotbookingpanel"
			action="${pageContext.request.contextPath}/booking/bookingorders">
			<table>
				<tbody>
					<tr>
						<td><select id="timeslotdropdown"><option></option></select></td>
					</tr>
					<tr>
						<td><input type="submit" value="BookSlot" /></td>
					</tr>
				</tbody>
			</table>
		</form:form>

	</div>
	<div id="errorpopup"></div>
	<div id="confirmationpopup"></div>
	<div id="bookingerror"></div>
</body>
</html>