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
import sorcer.service.Exertion;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static sorcer.eo.operator.*;

/**
 * @author Mike Sobolewski
 */
@RunWith(SorcerTestRunner.class)
@ProjectContext("examples/coffeemaker")
public class CoffeeMakerTest {
	private final static Logger logger = LoggerFactory.getLogger(CoffeeMakerTest.class);

	private CoffeeMaker coffeeMaker;
	private Inventory inventory;
	private Recipe espresso, mocha, macchiato, americano,americano2;

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

		macchiato = new Recipe();
		macchiato.setName("macchiato");
		macchiato.setPrice(40);
		macchiato.setAmtCoffee(7);
		macchiato.setAmtMilk(1);
		macchiato.setAmtSugar(2);
		macchiato.setAmtChocolate(0);

		americano = new Recipe();
		americano.setName("americano");
		americano.setPrice(40);
		americano.setAmtCoffee(7);
		americano.setAmtMilk(1);
		americano.setAmtSugar(2);
		americano.setAmtChocolate(0);

		americano2 = new Recipe();
		americano2.setName("americano");
		americano2.setPrice(11);
		americano2.setAmtCoffee(12);
		americano2.setAmtMilk(3);
		americano2.setAmtSugar(1);
		americano2.setAmtChocolate(1);
	}

	@Test
	public void testAddRecipe() {

		assertEquals(true, coffeeMaker.addRecipe(espresso));
		assertEquals(true, coffeeMaker.addRecipe(mocha));
		assertEquals(true, coffeeMaker.addRecipe(macchiato));
		assertEquals(false, coffeeMaker.addRecipe(americano));
	}

	@Test
	public void testDeleteRecipe() {

		boolean fulltable[] = coffeeMaker.getRecipeFull();

		for(int i =0; i < fulltable.length; i++ ){
			assertEquals(false,fulltable[i]);
		}
		assertEquals(true, coffeeMaker.addRecipe(espresso));
		assertEquals(true, coffeeMaker.deleteRecipe(espresso));
		fulltable = coffeeMaker.getRecipeFull();

		for(int i =0; i < fulltable.length; i++ ){
			assertEquals(false,fulltable[i]);
		}

		assertEquals(true, coffeeMaker.addRecipe(espresso));
		assertEquals(true, coffeeMaker.addRecipe(mocha));
		assertEquals(true, coffeeMaker.addRecipe(macchiato));


		fulltable = coffeeMaker.getRecipeFull();
		for(int i =0; i < fulltable.length; i++ ){
			assertEquals(true,fulltable[i]);
		}

		assertEquals(true, coffeeMaker.deleteRecipe(espresso));
		assertEquals(true, coffeeMaker.deleteRecipe(mocha));
		assertEquals(true, coffeeMaker.deleteRecipe(macchiato));

		fulltable = coffeeMaker.getRecipeFull();
		for(int i =0; i < fulltable.length; i++ ){
			assertEquals(false,fulltable[i]);
		}


	}

	@Test
	public void testEditRecipe() {
		assertTrue(coffeeMaker.addRecipe(macchiato));
		assertTrue(coffeeMaker.addRecipe(espresso));
		assertEquals(false, coffeeMaker.editRecipe(espresso,macchiato));
		assertEquals(false, coffeeMaker.editRecipe(americano,mocha));
	}

	@Test
	public void testContextCofee() throws ContextException {
		assertTrue(espresso.getAmtCoffee() == 6);
	}

	@Test
	public void testContextMilk() throws ContextException {
		assertTrue(espresso.getAmtMilk() == 1);
	}

	@Test
	public void addRecepie() throws Exception {
		coffeeMaker.addRecipe(mocha);
		assertEquals(coffeeMaker.getRecipeForName("mocha").getName(), "mocha");
	}

	@Test
	public void addContextRecepie() throws Exception {
		coffeeMaker.addRecipe(Recipe.getContext(mocha));
		assertEquals(coffeeMaker.getRecipeForName("mocha").getName(), "mocha");
	}

	@Test
	public void addServiceRecepie() throws Exception {
		Exertion cmt = task(sig("addRecipe", coffeeMaker),
						context(types(Recipe.class), args(espresso),
							result("recipe/added")));

		logger.info("isAdded: " + eval(cmt));
		assertEquals(coffeeMaker.getRecipeForName("espresso").getName(), "espresso");
	}

	@Test
	public void addRecipes() throws Exception {
		coffeeMaker.addRecipe(mocha);
		coffeeMaker.addRecipe(macchiato);
		coffeeMaker.addRecipe(americano);

		assertEquals(coffeeMaker.getRecipeForName("mocha").getName(), "mocha");
		assertEquals(coffeeMaker.getRecipeForName("macchiato").getName(), "macchiato");
		assertEquals(coffeeMaker.getRecipeForName("americano").getName(), "americano");
	}

	@Test
	public void makeCoffee() throws Exception {
		coffeeMaker.addRecipe(espresso);
		assertEquals(coffeeMaker.makeCoffee(espresso, 200), 150);

		coffeeMaker.addRecipe(espresso);
		coffeeMaker.addInventory(10, 10, 10, 10);

		int amtCoffee = inventory.getCoffee();
		int amtChocollate = inventory.getChocolate();
		int amtMilk = inventory.getMilk();
		int amtSugar = inventory.getSugar();
		coffeeMaker.makeCoffee(espresso, espresso.getPrice());
		assertEquals(amtChocollate - espresso.getAmtChocolate(), inventory.getChocolate());
		assertEquals(amtMilk - espresso.getAmtMilk(), inventory.getMilk());
		assertEquals(amtSugar - espresso.getAmtSugar(), inventory.getSugar());
		assertEquals(amtCoffee - espresso.getAmtCoffee(), inventory.getCoffee());
	}

	@Test
	public void testCheckInventory() {
		assertEquals(true,coffeeMaker.checkInventory() != null);
	}

	@Test
	public void testPurchaseBeverage() {

	}

}

