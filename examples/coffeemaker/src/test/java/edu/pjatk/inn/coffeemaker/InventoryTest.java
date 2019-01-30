package edu.pjatk.inn.coffeemaker;

import edu.pjatk.inn.coffeemaker.impl.CoffeeMaker;
import edu.pjatk.inn.coffeemaker.impl.Inventory;
import edu.pjatk.inn.coffeemaker.impl.Recipe;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sorcer.service.ContextException;

import static org.junit.Assert.assertEquals;

public class InventoryTest {
    private final static Logger logger = LoggerFactory.getLogger(InventoryTest.class);

    private CoffeeMaker coffeeMaker;
    private Inventory inventory;

    @Before
    public void setUp() throws ContextException {
        coffeeMaker = new CoffeeMaker();
        inventory = coffeeMaker.checkInventory();
    }

    @Test
    public void testAddInventory()
    {
        assertEquals(false, coffeeMaker.addInventory(-1, 0, 0, 0));
        assertEquals(false, coffeeMaker.addInventory(0, -2, 0, 0));
        assertEquals(false, coffeeMaker.addInventory(0, 0, -3, -4));
        assertEquals(true, coffeeMaker.addInventory(1, 0, 0, 0));
        assertEquals(true, coffeeMaker.addInventory(0, 2, 0, 0));
        assertEquals(true, coffeeMaker.addInventory(0, 0, 3, 0));
        assertEquals(true, coffeeMaker.addInventory(0, 0, 0, 1));
    }

}
