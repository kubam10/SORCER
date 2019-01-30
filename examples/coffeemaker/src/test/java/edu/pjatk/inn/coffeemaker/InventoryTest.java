package edu.pjatk.inn.coffeemaker;

import edu.pjatk.inn.coffeemaker.impl.CoffeeMaker;
import edu.pjatk.inn.coffeemaker.impl.Inventory;
import edu.pjatk.inn.coffeemaker.impl.Recipe;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sorcer.test.ProjectContext;
import org.sorcer.test.SorcerTestRunner;
import sorcer.service.ContextException;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

/**
 * @author Jakub Małyszek
 */

@RunWith(SorcerTestRunner.class)
@ProjectContext("examples/coffeemaker")
public class InventoryTest {
    private final static Logger logger = LoggerFactory.getLogger(InventoryTest.class);

    private CoffeeMaker coffeeMaker;
    private Inventory inventory;
    private Recipe espresso;

    @Before
    public void setUp() throws ContextException {
        coffeeMaker = new CoffeeMaker();
        inventory = coffeeMaker.checkInventory();

        espresso = new Recipe();
        espresso.setName("espresso");
        espresso.setPrice(50);
        espresso.setAmtCoffee(6);
        espresso.setAmtMilk(1);
        espresso.setAmtSugar(1);
        espresso.setAmtChocolate(1);
    }

    @Test
    public void testGetCoffee()
    {
        assertEquals(15, inventory.getCoffee());

    }

    @Test
    public void testGetChocolate()
    {
        assertEquals(15, inventory.getChocolate());

    }

    @Test
    public void testGetMilk()
    {
        assertEquals(15, inventory.getMilk());

    }

    @Test
    public void testGetSugar()
    {
        assertEquals(15, inventory.getSugar());

    }

    @Test
    public void testEnoughIngredients()
    {
        assertEquals(true, inventory.enoughIngredients(espresso));

        inventory.setSugar(0);

        assertEquals(false, inventory.enoughIngredients(espresso));
        inventory.setSugar(15);

        inventory.setChocolate(0);

        assertEquals(false, inventory.enoughIngredients(espresso));

        inventory.setChocolate(15);

        inventory.setMilk(0);

        assertEquals(false, inventory.enoughIngredients(espresso));

        inventory.setMilk(15);

        inventory.setCoffee(0);

        assertEquals(false, inventory.enoughIngredients(espresso));

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
