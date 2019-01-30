package edu.pjatk.inn.coffeemaker;

import edu.pjatk.inn.coffeemaker.impl.CoffeeMaker;
import edu.pjatk.inn.coffeemaker.impl.Inventory;
import edu.pjatk.inn.coffeemaker.impl.Recipe;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
public class RecipeTest {

    private CoffeeMaker coffeeMaker;
    private Inventory inventory;
    private Recipe espresso, mocha, test1, test2, test3;

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
        espresso.setAmtChocolate(0);

        mocha = new Recipe();
        mocha.setName("mocha");
        mocha.setPrice(100);
        mocha.setAmtCoffee(8);
        mocha.setAmtMilk(1);
        mocha.setAmtSugar(1);
        mocha.setAmtChocolate(2);

        test1 = new Recipe();
        test1.setName("test1");
        test1.setPrice(150);
        test1.setAmtCoffee(48);
        test1.setAmtMilk(10);
        test1.setAmtSugar(10);
        test1.setAmtChocolate(5);

        test2 = new Recipe();
        test2.setName("test2");
        test2.setPrice(12);
        test2.setAmtCoffee(122);
        test2.setAmtMilk(12);
        test2.setAmtSugar(12);
        test2.setAmtChocolate(1);

        test3 = new Recipe();
        test3.setName("test2");
        test3.setPrice(12);
        test3.setAmtCoffee(122);
        test3.setAmtMilk(12);
        test3.setAmtSugar(123);
        test3.setAmtChocolate(1);
    }

    @Test
    public void testGetAmtChocolate(){
        assertEquals(0, espresso.getAmtChocolate());
    }

    @Test
    public void testGetAmtCoffee(){
        assertEquals(6, espresso.getAmtCoffee());
    }

    @Test
    public void testGetAmtMilk(){
        assertEquals(1, espresso.getAmtMilk());
    }

    @Test
    public void testGetAmtSugar(){
        assertEquals(1, espresso.getAmtSugar());
    }

    @Test
    public void testSetAmtChocolate(){
        espresso.setAmtChocolate(15);
        assertEquals(15, espresso.getAmtChocolate());

        espresso.setAmtChocolate(-5);
        assertEquals(15, espresso.getAmtChocolate());

        espresso.setAmtChocolate(0);
        assertEquals(0, espresso.getAmtChocolate());
    }

    @Test
    public void testSetAmtCoffee(){
        espresso.setAmtCoffee(15);
        assertEquals(15, espresso.getAmtCoffee());

        espresso.setAmtCoffee(-5);
        assertEquals(15, espresso.getAmtCoffee());

        espresso.setAmtCoffee(0);
        assertEquals(0, espresso.getAmtCoffee());
    }

    @Test
    public void testSetAmtMilk(){
        espresso.setAmtMilk(15);
        assertEquals(15, espresso.getAmtMilk());

        espresso.setAmtMilk(-5);
        assertEquals(15, espresso.getAmtMilk());

        espresso.setAmtMilk(0);
        assertEquals(0, espresso.getAmtMilk());
    }

    @Test
    public void testSetAmtSugar(){
        espresso.setAmtSugar(15);
        assertEquals(15, espresso.getAmtSugar());

        espresso.setAmtSugar(-5);
        assertEquals(15, espresso.getAmtSugar());

        espresso.setAmtSugar(0);
        assertEquals(0, espresso.getAmtSugar());
    }

}
