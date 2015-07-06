package com.java.orders.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.java.orders.exceptions.VanNotAvailableException;
import com.java.orders.beans.Carton;
import com.java.orders.beans.ItemDetailDto;
import com.java.orders.beans.ItemsDto;
import com.java.orders.beans.VanDetailDto;
import com.java.orders.dao.BookingPostproccessingDao;
import com.java.orders.model.BookingDetails;
import com.java.orders.model.Cartons;
import com.java.orders.model.ItemDetail;
import com.java.orders.model.Items;
import com.java.orders.model.ItemsOngoing;
import com.java.orders.model.VanDetails;
import com.java.orders.service.BookingPostProcessorService;
import com.java.orders.service.VanService;
import com.java.orders.utils.CartonPackingUtils;

@Service("bookingpostprocessorservice")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = false)
public class BookingPostProcessorServiceImpl implements
		BookingPostProcessorService {

	List<Items> itemlist = null;
	String itemcode;
	List<ItemDetailDto> itemcompletedetails;
	List itemcodes = new ArrayList();
	public String timeslot;
	public String OrderDate;
	public Long Booking_Id;
	public String OrderName;
	@Autowired
	private BookingPostproccessingDao bookingpostprocessingdao;
	@Autowired
	private CartonPackingUtils cartonpackingutils;

	@Autowired
	private VanService vanservice;

	private Boolean NoCartonsLeft = false;
	private Integer Size_Allowed = 20;
	public List<VanDetailDto> Van1 = new ArrayList<>();
	public List<VanDetailDto> Van1toPersist = new ArrayList<>();
	public List<VanDetailDto> Van2 = new ArrayList<>();
	public List<VanDetailDto> Van2toPersist = new ArrayList<>();
	public List<VanDetailDto> Van3 = new ArrayList<>();
	public List<VanDetailDto> Van3toPersist = new ArrayList<>();
	public List<VanDetailDto> Van4 = new ArrayList<>();
	public List<VanDetailDto> Van4toPersist = new ArrayList<>();
	public List<Carton> cartonlist = new ArrayList<>();
	public List<Carton> cartondtolist = new ArrayList<>();
	public List<Carton> RemainingCartonList = new ArrayList<>();
	public List<VanDetailDto> vandetaildtolist = new ArrayList<>();
	public List<ItemDetailDto> itemdetaildto = new ArrayList<>();

	
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void fillupcartonwithitems(List<BookingDetails> bookingdetails,List<ItemsDto> itemsdto)
			throws Exception {
		Boolean result = false;
		for (BookingDetails bookingDetailobject : bookingdetails) {
			for (ItemsDto items : itemsdto) {

				itemcode = items.getCode();
				itemcodes.add(itemcode);

			}

			timeslot = bookingDetailobject.getTimeSlot();
			OrderDate = bookingDetailobject.getOrderdate();
			Booking_Id = bookingDetailobject.getBookingId();
			OrderName = bookingDetailobject.getOrdername();
		}

		itemcompletedetails = getCompleteItemDetails(itemcodes, itemsdto);

		List<Carton> cartons = assignCartonsToItems(itemcompletedetails,
				cartonpackingutils);
		/*
		 * A call to VanService Provider to pick up items this process has
		 * multiple steps 1.Service Provider will check whether the vans are
		 * available for fit or not 2.Service provider will then pick up the
		 * item and store into the van available.
		 */

		List<VanDetails> vandetails = vanservice.getVanDetails(timeslot,
				OrderDate);
		List<Carton> cartondtolist = new ArrayList<>();
		for (VanDetails vandetail : vandetails) {
			VanDetailDto vandetaildto = new VanDetailDto();
			vandetaildto.setBookingId(vandetail.getBookingId());
			for (Cartons carton : vandetail.getItems()) {
				Carton cartondto = new Carton();
				List<ItemsOngoing> itemongoing = carton.getItemsongoing();
				for (ItemsOngoing itemgoing : itemongoing) {
					ItemDetailDto dto = new ItemDetailDto();
					dto.setCode(itemgoing.getCode());
					dto.setHeight(itemgoing.getHeight());
					dto.setLength(itemgoing.getLength());
					dto.setWidth(itemgoing.getWidth());
					dto.setQuantity(itemgoing.getQuantity());
					itemdetaildto.add(dto);
				}
				cartondto.setItems(itemdetaildto);
				cartondtolist.add(cartondto);

			}
			vandetaildto.setItems(cartondtolist);
			vandetaildto.setOrderdate(vandetail.getOrderdate());
			vandetaildto.setOrdername(vandetail.getOrdername());
			vandetaildto.setTimeSlot(vandetail.getTimeSlot());
			vandetaildto.setVanNumber(vandetail.getVanNumber());
			vandetaildtolist.add(vandetaildto);
		}
		if (!vandetails.isEmpty()) {
			result = populateVanWithItems(vandetaildtolist, cartons);
		}

		else {
			result = populateEmptyVanWithItems(cartons);
		}
		if (result) {
			vanservice.addCartonsToVan(Van1toPersist, Van2toPersist, Van3toPersist, Van4toPersist, cartons);
			//after saving the data into database,clearing the respective arraylists for Van1,Van2,van3,Van4 
           clear();
		}
	}

	public void clear() {
		// TODO Auto-generated method stub
		Van1toPersist.clear();
		Van2toPersist.clear();
		Van3toPersist.clear();
		Van4toPersist.clear();
		Van1.clear();
		Van2.clear();
		Van3.clear();
		Van4.clear();
	}

	private Boolean populateEmptyVanWithItems(List<Carton> cartons) {
		// TODO Auto-generated method stub
		int cartonsRemaining = cartons.size();

		for (Carton carton : cartons) {

			
			if (Van1.isEmpty() || Van1.size() < Size_Allowed) {
				VanDetailDto vandetail = new VanDetailDto();
				cartonlist.add(carton);
				vandetail.setVanNumber("Van1");
				vandetail.setItems(cartonlist);
				vandetail.setBookingId(Booking_Id);
				vandetail.setOrderdate(OrderDate);
				vandetail.setOrdername(OrderName);
				vandetail.setTimeSlot(timeslot);
				Van1.add(vandetail);
				Van1toPersist.add(vandetail);
				cartonsRemaining--;
				continue;

			}
			if (Van2.isEmpty() || Van2.size() < Size_Allowed) {
				VanDetailDto vandetail = new VanDetailDto();
				cartonlist.add(carton);
				vandetail.setVanNumber("Van1");
				vandetail.setItems(cartonlist);
				vandetail.setBookingId(Booking_Id);
				vandetail.setOrderdate(OrderDate);
				vandetail.setOrdername(OrderName);
				vandetail.setTimeSlot(timeslot);
				Van2.add(vandetail);
				Van2toPersist.add(vandetail);
				cartonsRemaining--;
				continue;

			}
			if (Van3.isEmpty() || Van3.size() < Size_Allowed) {
				VanDetailDto vandetail = new VanDetailDto();
				cartonlist.add(carton);
				vandetail.setVanNumber("Van1");
				vandetail.setItems(cartonlist);
				vandetail.setBookingId(Booking_Id);
				vandetail.setOrderdate(OrderDate);
				vandetail.setOrdername(OrderName);
				vandetail.setTimeSlot(timeslot);
				Van3.add(vandetail);
				Van3toPersist.add(vandetail);
				cartonsRemaining--;
				continue;
			}

			if (Van4.isEmpty() || Van4.size() < Size_Allowed) {
				VanDetailDto vandetail = new VanDetailDto();
				cartonlist.add(carton);
				vandetail.setVanNumber("Van1");
				vandetail.setItems(cartonlist);
				vandetail.setBookingId(Booking_Id);
				vandetail.setOrderdate(OrderDate);
				vandetail.setOrdername(OrderName);
				vandetail.setTimeSlot(timeslot);
				Van4.add(vandetail);
				Van4toPersist.add(vandetail);
				cartonsRemaining--;
				continue;
			}

		}
		if (cartonsRemaining == 0) {
			NoCartonsLeft = true;
			
		}
		return NoCartonsLeft;
	}

	private Boolean populateVanWithItems(List<VanDetailDto> vandetails,
			List<Carton> cartons) throws Exception {
		// TODO Auto-generated method stub
		VanDetailDto dummyobject = null;
		int remainingCartons = cartons.size();
		int Vansize = vandetails.size();
		for (VanDetailDto vandetail : vandetails) {
			// A check whether Van1 is empty or not, if not then keeping the
			// van1 information from the table into Van1 list else an empty Van1
			// will be assigned for the cartons.
			if ("Van1".equalsIgnoreCase(vandetail.getVanNumber())) {

				dummyobject = vandetail;

				Van1.add(dummyobject);

			}

			// A check whether Van2 is empty or not, if not then keeping the
			// van1 information from the table into Van2 list else an empty Van2
			// will be assigned for the cartons.
			else if ("Van2".equalsIgnoreCase(vandetail.getVanNumber())) {
				dummyobject = vandetail;
				Van2.add(dummyobject);
			}
			// A check whether Van3 is empty or not, if not then keeping the
			// van1 information from the table into Van3 list else an empty Van3
			// will be assigned for the cartons.
			else if ("Van3".equalsIgnoreCase(vandetail.getVanNumber())) {
				dummyobject = vandetail;
				Van3.add(dummyobject);

			}
			// A check whether Van4 is empty or not, if not then keeping the
			// van4 information from the table into Van1 list else an empty Van4
			// will be assigned for the cartons.
			else if ("Van4".equalsIgnoreCase(vandetail.getVanNumber())) {
				dummyobject = vandetail;
				Van4.add(dummyobject);

			}
		}
		// Empty Check for Van2,Van3,Van4 as Van1 is the default Van if no van
		// details are found for particular orderdate and timeslot
		
		if (Van1.size()==Size_Allowed && Van2.isEmpty() && remainingCartons!=0) {
			NoCartonsLeft = populateEmptyVanWithItems(cartons);
		} else if (Van2.size()==Size_Allowed && Van3.isEmpty()&& remainingCartons!=0) {
			NoCartonsLeft = populateEmptyVanWithItems(cartons);

		}

		else if (Van3.size()==Size_Allowed && Van4.isEmpty()&& remainingCartons!=0) {
			NoCartonsLeft = populateEmptyVanWithItems(cartons);
		}
		
		
		
		for (Carton carton : cartons) {
			if (Van1.size() < Size_Allowed && remainingCartons != 0) {
				VanDetailDto vandetail = new VanDetailDto();
				cartonlist.add(carton);
				vandetail.setVanNumber("Van1");
				vandetail.setItems(cartonlist);
				vandetail.setBookingId(Booking_Id);
				vandetail.setOrderdate(OrderDate);
				vandetail.setOrdername(OrderName);
				vandetail.setTimeSlot(timeslot);
				Van1.add(vandetail);
				Van1toPersist.add(vandetail);
				remainingCartons--;
				continue;
			}

			else if (Van2.size() < Size_Allowed && remainingCartons != 0) {
				VanDetailDto vandetail = new VanDetailDto();
				cartonlist.add(carton);
				vandetail.setVanNumber("Van2");
				vandetail.setItems(cartonlist);
				vandetail.setBookingId(Booking_Id);
				vandetail.setOrderdate(OrderDate);
				vandetail.setOrdername(OrderName);
				vandetail.setTimeSlot(timeslot);
				Van2.add(vandetail);
				Van2toPersist.add(vandetail);
				remainingCartons--;
				continue;

			}

			else if (Van3.size() < Size_Allowed && remainingCartons != 0) {
				VanDetailDto vandetail = new VanDetailDto();
				cartonlist.add(carton);
				vandetail.setVanNumber("Van3");
				vandetail.setItems(cartonlist);
				vandetail.setBookingId(Booking_Id);
				vandetail.setOrderdate(OrderDate);
				vandetail.setOrdername(OrderName);
				vandetail.setTimeSlot(timeslot);
				Van3.add(vandetail);
				Van3toPersist.add(vandetail);
				remainingCartons--;
				continue;
			}

			else if (Van4.size() < Size_Allowed && remainingCartons != 0) {
				VanDetailDto vandetail = new VanDetailDto();
				cartonlist.add(carton);
				vandetail.setVanNumber("Van4");
				vandetail.setItems(cartonlist);
				vandetail.setBookingId(Booking_Id);
				vandetail.setOrderdate(OrderDate);
				vandetail.setOrdername(OrderName);
				vandetail.setTimeSlot(timeslot);
				Van4.add(vandetail);
				Van4toPersist.add(vandetail);
				remainingCartons--;
				continue;

			} else if (Van1.size() == Size_Allowed
					&& Van2.size() == Size_Allowed
					&& Van3.size() == Size_Allowed
					&& Van4.size() == Size_Allowed) {
				throw new VanNotAvailableException(
						"All vans are loaded ..Please try again");
			}
			
			
		}
		if(remainingCartons==0)
		{
			NoCartonsLeft=true;	
		}
		return NoCartonsLeft;
	}

	private List<ItemDetailDto> getCompleteItemDetails(List<String> itemcodes,
			List<ItemsDto> itemlist) {
		List<ItemDetailDto> itemdetaildtolist = new ArrayList<>();
		List<ItemDetail> itemdetail = bookingpostprocessingdao
				.getitemdetails(itemcodes);
		

		if (itemlist != null && !itemlist.isEmpty() && itemdetail != null
				&& !itemdetail.isEmpty()) {

			for(ItemsDto items : itemlist)
			{
				
				ItemDetailDto itemdetaildto = new ItemDetailDto();
				itemdetaildto.setQuantity(Short.parseShort(items.getQuantity()));
				itemdetaildto.setCode(items.getCode());
				for(ItemDetail itemdetaildata: itemdetail){
				itemdetaildto.setLength(itemdetaildata.getLength());
				itemdetaildto.setWidth(itemdetaildata.getWidth());
				itemdetaildto.setHeight(itemdetaildata.getHeight());
				
				}
				itemdetaildtolist.add(itemdetaildto);
			}
		}

		return itemdetaildtolist;
	}

	private List<Carton> assignCartonsToItems(
			List<ItemDetailDto> itemdetaildtolist,
			CartonPackingUtils cartonpacking) {
		List<Carton> cartonList = null;
		if (!itemdetaildtolist.isEmpty() && itemdetaildtolist != null) {

			cartonList = cartonpacking.assignCartonsToItems(itemdetaildtolist);
		}
		return cartonList;
	}

}
