package edu.pjatk.inn.coffeemaker.impl;

import edu.pjatk.inn.coffeemaker.Delivery;
import sorcer.service.Context;
import sorcer.service.ContextException;

import java.util.*;
import java.rmi.RemoteException;
import java.sql.Date;

/**
 * Created by Pawe³ Wojtasiak
 */
public class OrderQueuingImpl implements OrderQueuing {

	private ArrayList<Date> ordersArray;
	private double usersWallet;
	private boolean canAddOrder = false;
	static final long ONE_MINUTE_IN_MILLIS=60000;
	
    @Override
    public Context QueOrder(Context context) throws RemoteException, ContextException {
    	
    	 ordersArray = new ArrayList<Date>(context.getValue("coffeeMaker/orders", currentOrders));
    	 usersWallet = context.getValue("coffeeMaker/wallet", usersFunds)
    			 
    	 double price = (double)context.getValue("order/price", price)
    	 if(usersWallet >= price) {
    		 canAddOrder = true;
    	 }
    	 else {
    		 context.putValue("order/notification", "You don't have required funds on your account")
    		 return context;
    	 }
    	 
		 Date order = (Date)context.getValue("order/date", date)
    	 if(ordersArray != null) {
    		 if(!ordersArray.isEmpty()) {
    			 for(int i = 0 ; i < ordersArray.length; i++) {
    				 long fiveMinutesBeforeNextOrder = ordersArray[i].getTime() - ONE_MINUTE_IN_MILLIS * 5;
    				 long fiveMinutesAfterNextOrder =  ordersArray[i].getTime() + ONE_MINUTE_IN_MILLIS * 5;
    				 if(ordersArray[i].compareTo(order) != 0 && 
    						 (order.getTime() >= fiveMinutesAfterNextOrder || order.getTime() <= fiveMinutesBeforeNextOrder)) {
    					 canAddOrder = true;
    				 }
    				 else {
    					 canAddOrder = false;
    					 break;
    				 }
    			 }
		    	 if(canAddOrder) {
	    			 ordersArray.add(order)
		    		 context.putValue("order/notification", "Order is added")
		    	     context.putValue("coffeeMaker/orders", ordersArray);
		    	 }
		    	 else {
		    		 context.putValue("order/notification", "Can't add order at this time")
		    	 }
    		 }
    		 else {
    			 ordersArray.add(order)
        		 context.putValue("coffeeMaker/orders", ordersArray)
    		 }
        			 
    	 }
    	 else {
    		 ordersArray = new ArrayList()<Date>();
    		 ordersArray.add(order)
    		 context.putValue("coffeeMaker/orders", ordersArray)
    	 }
        return context;
    }
}
