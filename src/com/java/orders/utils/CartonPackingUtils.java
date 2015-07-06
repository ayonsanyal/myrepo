package com.java.orders.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.java.orders.beans.Carton;
import com.java.orders.beans.ItemDetailDto;

@Repository("cartonpackingutils")
public class CartonPackingUtils {

	private static Double Length_Permitted = 15.0;
	private static Double Width_Permitted = 30.0;
	private static Double Height_Permitted = 15.0;
	private Double Volume_Permitted = (Length_Permitted * Width_Permitted * Height_Permitted);
	private Double Volume_Permitted_Temp = Volume_Permitted;
	private static Integer items_size_brforefilling = 0;
	private static Integer items_size_afterfilling = 0;
	public boolean cartonfilled = false;
	public Boolean itemsremaining = true;
	public boolean cartonisfull = false;
	public boolean itemslefttofill = false;
	public boolean bigitemsfilledflag = false;
	public boolean firstitementered=false;
	private int quantityofitemspicked =0;
	private int quantityofitemsincarton =0;
	private int quantityinitial=0;
	List<ItemDetailDto> itemdetaildtolistforbigitems = null;

	List<ItemDetailDto> items = new ArrayList<ItemDetailDto>();
	List<ItemDetailDto> itemstemp = new ArrayList<>();
	List<Carton> cartonadded = new ArrayList<>();
	private short quantity_idicator;

	private Carton getCartons() {
		Carton carton = new Carton();
		return carton;
	}

	public List<Carton> assignCartonsToItems(
			List<ItemDetailDto> itemdetaildtolist) {
		if (!itemdetaildtolist.isEmpty()) {
			if(!items.isEmpty())
			{
				items.clear();
			}
			Volume_Permitted_Temp = Volume_Permitted;
			items_size_brforefilling = itemdetaildtolist.size();
			for (ItemDetailDto itemdetaildto : itemdetaildtolist) {
				quantityinitial=itemdetaildto.getQuantity();
				CartonFilling(itemdetaildto, Volume_Permitted_Temp,
						Length_Permitted, Width_Permitted, Height_Permitted);

			}
			//exit point check if any item left to pick in carton.
			if(quantityofitemspicked!=0)
			{
				Carton cartonnew = new Carton();
				List<ItemDetailDto> itemdetailslistforcartons = new ArrayList<>();
				itemdetailslistforcartons=itemstemp;
				cartonnew.setItems(itemdetailslistforcartons);
				cartonadded.add(cartonnew);
				quantityofitemspicked=0;
			}
			
		}
		return cartonadded;
	}

	private void CartonFilling(ItemDetailDto itemdetaildto,
			Double Volume_Permitted_local, Double lengthAllowed,
			Double widthAllowed, Double heightallowed) {
		Double volumeOfAnItem = 0.0;
		Double itemvolumeWithQuantity = 0.0;
		
       
		if (itemdetaildto != null) {
			
			if(cartonisfull || quantityinitial==quantityofitemsincarton){
				
				List<ItemDetailDto> itemdetailslistforcarton = new ArrayList<>(itemstemp);
				Carton carton = new Carton();
				
				carton.setItems(itemdetailslistforcarton);
				cartonadded.add(carton);
				quantityofitemsincarton=0;
				//flushing the items list after adding into carton
				
				
				itemstemp.clear();
				cartonisfull=false;
			}

			if (Volume_Permitted_Temp < 1.0 || Volume_Permitted_Temp == 0.0) {
				Carton carton = getCartons();
				carton.setItems(itemstemp);
				cartonadded.add(carton);
				// again reset the total volume to the permitted volume
				Volume_Permitted_Temp = Volume_Permitted;
				CartonFilling(itemdetaildto, Volume_Permitted_Temp,
						Length_Permitted, Width_Permitted, Height_Permitted);

			}

			/*
			 * ideal case where the comparison will be done for the
			 * length,width,height of an individual item with length,width and
			 * height permitted total volume (quantity * volume ) with the
			 * volume permitted.
			 */

			if (itemdetaildto.getLength() < lengthAllowed
					&& itemdetaildto.getWidth() < widthAllowed
					&& itemdetaildto.getHeight() < heightallowed) {
				// evaluating total volume of an item
				volumeOfAnItem = (itemdetaildto.getLength()
						* itemdetaildto.getWidth() * itemdetaildto.getHeight());
				itemvolumeWithQuantity = (itemdetaildto.getQuantity() * volumeOfAnItem);
				// Assuming the ideal case
				if (Volume_Permitted_local > itemvolumeWithQuantity) {
					quantityofitemspicked= quantityofitemspicked+itemdetaildto.getQuantity();
					itemstemp.add(itemdetaildto);
					Volume_Permitted_local = Volume_Permitted_local
							- itemvolumeWithQuantity;
					Volume_Permitted_Temp = Volume_Permitted_local;
					items_size_afterfilling++;
				}
				
				//Scenario when the volume of a carton becomes less than the perunit volume of an item
				else 
					if(Volume_Permitted_local<volumeOfAnItem)
				{
					Volume_Permitted_local=Volume_Permitted;
					cartonisfull=true;
					quantityofitemsincarton=quantityofitemspicked;
					quantityofitemspicked=0;
					CartonFilling(itemdetaildto, Volume_Permitted_local,
							Length_Permitted, Width_Permitted, Height_Permitted);
					
					
				}
				
				
				// Scenario when per unit volume of an item is greater than the
				// space left in a carton.
				
				// case 2 . When all the items cannot be put into the carton .In
				// this case the part of the item will be taken which will be
				// occupied in the carton.

				else {
					bigitemsfilledflag = CartonAssignmentForNonFittingBigItems(
							itemdetaildto.getQuantity(),
							Volume_Permitted_local, volumeOfAnItem,
							itemdetaildto);
					
				}

			}

		}

	}

	private Boolean CartonAssignmentForNonFittingBigItems(
			Short quantity, Double volume_Permitted2,
			Double itemvolumeWithQuantity, ItemDetailDto itemdetaildto) {
		// TODO Auto-generated method stub

		/* The items are divided and put into the carton */
		Short quantitytemp = quantity;

		int itemstobeadded = QuantityCheck(quantitytemp, volume_Permitted2,
				itemvolumeWithQuantity);
		if (itemstobeadded >= 0) {
			{
				if (itemstobeadded == 0)
				{
					
					
					if(quantity_idicator!=0){
						{   
							ItemDetailDto itemdetaildtoforleftitems = new ItemDetailDto();
							itemdetaildtoforleftitems.setCode(itemdetaildto.getCode());
							itemdetaildtoforleftitems.setHeight(itemdetaildto
									.getHeight());
							itemdetaildtoforleftitems.setLength(itemdetaildto
									.getLength());
							itemdetaildtoforleftitems
									.setWidth(itemdetaildto.getWidth());
							itemdetaildtoforleftitems.setQuantity(quantity_idicator);
							CartonFilling(itemdetaildtoforleftitems,
									Volume_Permitted_Temp, Length_Permitted,
									Width_Permitted, Height_Permitted);
							
							
						}
					return true;
					}
					
					
					
			     }
				else{

				ItemDetailDto itemdetailwithacceptedquantity = new ItemDetailDto();
				itemdetailwithacceptedquantity.setLength(itemdetaildto
						.getLength());
				itemdetailwithacceptedquantity.setCode(itemdetaildto.getCode());
				itemdetailwithacceptedquantity.setWidth(itemdetaildto
						.getWidth());
				itemdetailwithacceptedquantity.setHeight(itemdetaildto
						.getHeight());

				itemdetailwithacceptedquantity
						.setQuantity((short) itemstobeadded);

				CartonFilling(itemdetailwithacceptedquantity,
						Volume_Permitted_Temp, Length_Permitted,
						Width_Permitted, Height_Permitted);
				quantitytemp = (short) (quantitytemp - itemstobeadded);
				quantity_idicator = quantitytemp;
				
				}
				 
					
				
				if (quantity_idicator != 0) {
					ItemDetailDto itemdetaildtoforleftitems = new ItemDetailDto();
					itemdetaildtoforleftitems.setCode(itemdetaildto.getCode());
					itemdetaildtoforleftitems.setHeight(itemdetaildto
							.getHeight());
					itemdetaildtoforleftitems.setLength(itemdetaildto
							.getLength());
					itemdetaildtoforleftitems
							.setWidth(itemdetaildto.getWidth());
					itemdetaildtoforleftitems.setQuantity(quantity_idicator);
					
					bigitemsfilledflag=CartonAssignmentForNonFittingBigItems(
							quantity_idicator,
							Volume_Permitted_Temp, itemvolumeWithQuantity,
							itemdetaildtoforleftitems);
					
					
				}

			}

		} 

		return bigitemsfilledflag;
	}

	private int QuantityCheck(int quantity, Double volume_Permitted2,
			Double itemvolumeWithQuantity) {
		// TODO Auto-generated method stub

		if (quantity == 0) {
			return 0;
		}
		if ((quantity * itemvolumeWithQuantity) < volume_Permitted2) {
			return quantity;
		}

		else {

			quantity = QuantityCheck(quantity / 2, volume_Permitted2,
					itemvolumeWithQuantity);
		}

		return quantity;
	}

}
