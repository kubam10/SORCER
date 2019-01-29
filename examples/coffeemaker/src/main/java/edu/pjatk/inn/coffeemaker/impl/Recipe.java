package edu.pjatk.inn.coffeemaker.impl;

import sorcer.core.context.ServiceContext;
import sorcer.service.Context;
import sorcer.service.ContextException;

import java.io.Serializable;

/**
*  COPYRIGHT (C) INN.
*  Classes to support coffe making
*  Homework Inn H2
 * @author Cezary Szymkiewicz, Jakub Malyszek, Pawel Wojtasiak
*  @version 1.01 2019-28-01
*/

/**
 *@class defining Recipe model for coffemaker
 */
public class Recipe implements Serializable {
    private String name;
    private int price;
    private int amtCoffee;
    private int amtMilk;
    private int amtSugar;
    private int amtChocolate;
    
    /**
     * @constructor contructor that defines object of Recipie.
     */
    public Recipe() {
    	this.name = "";
    	this.price = 0;
    	this.amtCoffee = 0;
    	this.amtMilk = 0;
    	this.amtSugar = 0;
    	this.amtChocolate = 0;
    }
    
    /**
	 * Returns amount of chocolate of this recipe.
	 *
	 * @return   Returns the amtChocolate.
	 */
    public int getAmtChocolate() {
		return amtChocolate;
	}
    
    /**
     * Method that sets value of amtChocolate if parameter is higher or equal 0
     *
	 * @param amtChocolate   The amtChocolate to setValue.
	 */
    public void setAmtChocolate(int amtChocolate) {
		if (amtChocolate >= 0) {
			this.amtChocolate = amtChocolate;
		} 
	}
    
    /**
	 * Returns amount of coffee of this recipe.
	 *
	 * @return   Returns the amtCoffee.
	 */
    public int getAmtCoffee() {
		return amtCoffee;
	}
    
    /**
     * Method that sets value of amtCoffee if parameter is higher or equal 0
     *
	 * @param amtCoffee   The amtCoffee to setValue.
	 */
    public void setAmtCoffee(int amtCoffee) {
		if (amtCoffee >= 0) {
			this.amtCoffee = amtCoffee;
		} 
	}
    
    /**
	 * Returns amount of milk of this recipe.
	 *
	 * @return   Returns the amtMilk.
	 */
    public int getAmtMilk() {
		return amtMilk;
	}
    
    /**
     * Method that sets value of amtMilk if parameter is higher or equal 0
     *
	 * @param amtMilk   The amtMilk to setValue.
	 */
    public void setAmtMilk(int amtMilk) {
		if (amtMilk >= 0) {
			this.amtMilk = amtMilk;
		} 
	}
    
    /**
	 * Returns amount of sugar of this recipe.
	 *
	 * @return   Returns the amtSugar.
	 */
    public int getAmtSugar() {
		return amtSugar;
	}
    
    /**
     * Method that sets value of amtSugar if parameter is higher or equal 0
     *
	 * @param amtSugar   The amtSugar to setValue.
	 */
    public void setAmtSugar(int amtSugar) {
		if (amtSugar >= 0) {
			this.amtSugar = amtSugar;
		} 
	}
    
    /**
	 * Returns the name of this recipe.
	 *
	 * @return   Returns the name.
	 */
    public String getName() {
		return name;
	}
    
    /**
     * Method that sets value of name if it's still null
	 *
	 * @param name   The name to setValue.
	 */
    public void setName(String name) {
    	if(name != null) {
    		this.name = name;
    	}
	}
    
    /**
	 * Returns the price of this recipe.
	 *
	 * @return   Returns the price.
	 */
    public int getPrice() {
		return price;
	}
    
    /**
     * Method that sets value of amtSugar if parameter is higher or equal 0
     *
	 * @param price   The price to setValue.
	 */
    public void setPrice(int price) {
		if (price >= 0) {
			this.price = price;
		} 
	}
    
    /**
	 * Compares this recipe to given one and returns a value indicating if it's the same recipe.
	 *
     * @return Returns bool if recipe has same name to actual.
	 * @param price   The price to setValue.
	 */
    public boolean equals(Recipe r) {
        if((this.name).equals(r.getName())) {
            return true;
        }
        return false;
    }
    public String toString() {
    	return name;
    }
    
    /**
     * Method that returns object of recipe by setting its fields from given context using metgods defined in this class
     *
     * @param context context to populate Recipe
     * @return Returns object of Recipe class that is created with given context
     *
     */
	static public Recipe getRecipe(Context context) throws ContextException {
		Recipe r = new Recipe();
		r.name = (String)context.getValue("name");
		r.price = (int)context.getValue("price");
		r.amtCoffee = (int)context.getValue("amtCoffee");
		r.amtMilk = (int)context.getValue("amtMilk");
		r.amtSugar = (int)context.getValue("amtSugar");
		r.amtChocolate = (int)context.getValue("amtChocolate");
		return r;
	}

	/**
	 * Method that creates context from given recipe by using methods returning its values.
	 *
	 * @param recipe recipe that is source of values to get context
	 * @return Returns object of Context class
	 */
	static public Context getContext(Recipe recipe) throws ContextException {
		Context cxt = new ServiceContext();
		cxt.putValue("name", recipe.getName());
		cxt.putValue("price", recipe.getPrice());
		cxt.putValue("amtCoffee", recipe.getAmtCoffee());
		cxt.putValue("amtMilk", recipe.getAmtMilk());
		cxt.putValue("amtSugar", recipe.getAmtSugar());
		cxt.putValue("amtChocolate", recipe.getAmtChocolate());
		return cxt;
	}


}
