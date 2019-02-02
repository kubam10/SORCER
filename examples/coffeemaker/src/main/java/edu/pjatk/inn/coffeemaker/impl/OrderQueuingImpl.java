package edu.pjatk.inn.coffeemaker.impl;

import edu.pjatk.inn.coffeemaker.Delivery;
import edu.pjatk.inn.coffeemaker.OrderQueuing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sorcer.service.Context;
import sorcer.service.ContextException;
import sorcer.service.MogramException;

import java.util.*;
import java.rmi.RemoteException;
import java.sql.Date;

import static sorcer.eo.operator.exert;
import static sorcer.eo.operator.upcontext;

/**
 * @author Paweł Wojtasiak
 * @editor Cezary Szymkiewicz
 * @editor Jakub Małyszek
 */
public class OrderQueuingImpl implements OrderQueuing {


	private final static Logger logger = LoggerFactory.getLogger(OrderQueuing.class);
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
    public Context queOrder(Context context) throws RemoteException, ContextException {
    	
    	 ordersArray = (ArrayList<Date>) context.getValue("coffeeMaker/orders");
    	 usersWallet = (double) context.getValue("coffeeMaker/wallet");

		Context out = null;
		try {
			out = upcontext(exert(context));
		} catch (MogramException e) {
			e.printStackTrace();
		}


    	 double price = (double)context.getValue("order/price");
    	 if(usersWallet >= price) {
    		 canAddOrder = true;
    	 }
    	 else {
    		 context.putValue("order/notification", "You don't have required funds on your account");
    		 return context;
    	 }
    	 
		 Date order = (Date)context.getValue("order/date");
    	 if(ordersArray != null) {
    		 if(!ordersArray.isEmpty()) {
    			 for(int i = 0 ; i < ordersArray.size(); i++) {
    				 long fiveMinutesBeforeNextOrder = addFiveMins(ordersArray.get(i));
    				 long fiveMinutesAfterNextOrder =  subtractFiveMins(ordersArray.get(i));
    				 if(ordersArray.get(i).compareTo(order) != 0 &&
    						 (order.getTime() >= fiveMinutesAfterNextOrder || order.getTime() <= fiveMinutesBeforeNextOrder)) {
    					 canAddOrder = true;
    				 }
    				 else {
    					 canAddOrder = false;
    					 break;
    				 }
    			 }
		    	 if(canAddOrder) {
	    			 ordersArray.add(order);
		    		 context.putValue("order/notification", "Order is added");
					 context.putValue("coffeeMaker/wallet", usersWallet - price);
		    	     context.putValue("coffeeMaker/orders", ordersArray);
		    	 }
		    	 else {
		    		 context.putValue("order/notification", "Can't add order at this time");
		    	 }
    		 }
    		 else {
    			 ordersArray.add(order);
				 context.putValue("order/notification", "Order is added");
				 context.putValue("coffeeMaker/wallet", usersWallet - price);
				 context.putValue("coffeeMaker/orders", ordersArray);
    		 }
        			 
    	 }
    	 else {
    		 ordersArray = new ArrayList<Date>();
    		 ordersArray.add(order);
			 context.putValue("order/notification", "Order is added");
    		 context.putValue("coffeeMaker/wallet", usersWallet - price);
			 context.putValue("coffeeMaker/orders", ordersArray);
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
