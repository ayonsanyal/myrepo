/**
 * 
 */
function timeslottrim() {

	$(document).ready(function() {
	});
}

function prepareOrderBookingURL(timeslotdetails, order ) {
	
	
	var bookingdetails = $.ajax({

		type : "GET",

		url : "/rest/Booking",
		data : [ {
			"orderid" : order.order_id,
			"timeslot" : timeslot,
			"customerid":order.customerid,
			"orderdate":order.orderdate
			
		} ],
		
		success:  function(data)
		{
			showconfirmation(data)
		}
	

	});

}

function showconfirmation(data)
{   
	 
	
	
	var confirmation = "Congrats,,  booking is done for" +data.BookingId +"for the timeslot"+data.TimeSlot+"for the date"+data.orderdate+"";
	$("#confirmationpopup").html(confirmation);
	$("#confirmationpopup").dialog("open");
	}


