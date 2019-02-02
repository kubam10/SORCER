package edu.pjatk.inn.coffeemaker.impl;

import edu.pjatk.inn.coffeemaker.Delivery;
import sorcer.service.Context;
import sorcer.service.ContextException;

import java.util.*;
import java.rmi.RemoteException;
import java.sql.Date;

/**
 * @author Pawe³ Wojtasiak
 * @editor Cezary Szymkiewicz
 */
public class OrderQueuingImpl implements OrderQueuing {

	/*
	 * Array of created orders
	 */
	private ArrayList<Date> ordersArray;
	/*
	 * Users wallet status
	 */
	private double usersWallet;
	/*
	 * Value describing if order is valid 
	 */
	private boolean canAddOrder = false;
	/*
	 * Adding minutes helper
	 */
	static final long ONE_MINUTE_IN_MILLIS=60000;
	
	/**
	 * Returns context with notifications for CoffeMaker by checking 
	 * if date of order is valid and if User has enought money in wallet
	 * @param Context context
	 * @return Context
	 */
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
    				 long fiveMinutesBeforeNextOrder = addFiveMins(ordersArray[i]);
    				 long fiveMinutesAfterNextOrder =  subtractFiveMins(ordersArray[i]);
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
    
    /**
	 * Returns date with time needed to make new coffe.
	 *
	 * @return   Returns the long:plusFive.
	 * @param Date date
	 */
    public long addFiveMins(Date date) {
    	long plusFive = date.getTime() + ONE_MINUTE_IN_MILLIS * 5;
    	return plusFive;
    }
    
    /**
	 * Returns date with time needed to make previous coffe.
	 *
	 * @return   Returns the minusFive.
	 * @param Date date
	 */
    public long subtractFiveMins(Date date) {
    	long minusFive = date.getTime() - ONE_MINUTE_IN_MILLIS * 5;
    	return minusFive;
    }
}
