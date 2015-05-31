<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Choose a Time Slot and Book</title>
<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />

<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>

<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>


<script type="text/javascript">
	$(document).ready(
			function() {
				 
				    $( "#orderdate" ).datepicker({
				      changeMonth: true,//this option for allowing user to select month
				      changeYear: true //this option for allowing user to select from year range
				    });
				
				 $("#showtimeslot").click(function (){
					   
				var dateselected =  $("#orderdate").val();
				var currentDate= new Date();
				var currentHour = currentDate.getHours();
				var currenthour12 = currentHour-12;
				$('#timeslotwindow').hide();
 var req = [{"todaysTime":todayhour12,"orderDate":currentDate}];
  var reqfinal= JSON.stringify(req);
 
 
				 $.ajax({

					type : "GET",

					url : "/TimeSlot/loadtimeslots/orders",
					contentType : "application/json; charset=utf-8",
					accept:"application/json; charset=utf-8",
					mimeType:"application/json",
					dataType : "json",
					headers : "Accept: application/json",
					accept:"application/json",
					beforeSend:function(xhr){
						xhr.setRequestHeader("Content-Type","application/json");
					}    ,
					data :reqfinal,					
					success : function() {
						$('#timeslotwindow').show();
						populatetimeslots(data);
					},

					error : function(e) {

						alert('Error: ' + e);

					}

				});
				 });			 		
function populatetimeslots(data)
{
	var values = JSON.parse(data);
	$.each(data.object,function(i,obj)
            {
             alert(obj.TimeSlot);
             var div_content="<option>"+obj.TimeSlot+"</option>";
            alert(div_content);
            $(div_content).appendTo('#timeslotdropdown');
	      
            });
}
	
function booking() 
{
	var timeslot = $("select#timeslotdropdown").val();
	prepareOrderBookingURL(timeslot);
}


	function prepareOrderBookingURL(timeslot) {

		var bookingdetails = $.ajax({
/* Assuming that the parameters for the below url are present in the request */
			type : "POST",

			url : "/TimeSlot/loadtimeslots/booking/bookingorders",
			data : [ {
				"ordername" : "ayon",
				"timeslot" : timeslot,
				"items" :[
					
				{"name":"item1",
				"quantity":"2",
				"price":"50"
				},
				{ "name":"item2",
				  "quantity":"3",
				  "price":"75"
				}
				],
				
				"orderdate" : currentDate

			} ],

			success : function(data) {
				showconfirmation(data)
			}

		});

	}
	
	$(function() {
		$("#confirmationpopup").dialog({
			autoOpen : false,
			buttons : {
				OK : function() {
					$(this).dialog("close");
				}
			},
			title : "Success",
			position : {
				my : "left center",
				at : "left center"
			}
		});

	});
	function showconfirmation(data) 
	{

		var confirmation = "Congrats,,  booking is done for" + data.BookingId
				+ "for the timeslot" + data.TimeSlot + "for the date"
				+ data.orderdate + "";
		$("#confirmationpopup").html(confirmation);
		$("#confirmationpopup").dialog("open");
	}	
			});	
</script>
</head>




<body>

<center><p>Choose the Date: <input type="text" id="orderdate" /></p></center>
<br>
<button id ="showtimeslot" value ="getTimeSlots"></button>

	<div id="timeslotwindow" align="center">


		<select id="timeslotdropdown">


			<option></option>

		</select> <br>
		<button value="submit" onclick="booking();"></button>

	</div>

	<div id="confirmationpopup"></div>
</body>
</html>