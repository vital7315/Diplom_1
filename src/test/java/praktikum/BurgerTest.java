package praktikum;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BurgerTest {

    private static final float PRICE_DELTA = 0.01f;

    @Mock
    private Bun bunMock;

    @Mock
    private Ingredient ingredientMock;

    @Test
    public void testSetBuns() {
        Burger burger = new Burger();
        when(bunMock.getName()).thenReturn("Ржаной");

        burger.setBuns(bunMock);

        assertEquals("Ржаной", burger.bun.getName());
    }

    @Test
    public void testAddIngredient() {
        Burger burger = new Burger();
        when(ingredientMock.getName()).thenReturn("hot sauce");

        burger.addIngredient(ingredientMock);

        assertEquals(1, burger.ingredients.size());
        assertEquals("hot sauce", burger.ingredients.get(0).getName());
    }

    @Test
    public void testRemoveIngredient() {
        Burger burger = new Burger();
        burger.addIngredient(ingredientMock);

        burger.removeIngredient(0);

        assertTrue(burger.ingredients.isEmpty());
    }

    @Test
    public void testMoveIngredient() {
        Burger burger = new Burger();

        Ingredient ing1 = mock(Ingredient.class);
        Ingredient ing2 = mock(Ingredient.class);
        when(ing1.getName()).thenReturn("hot sauce");
        when(ing2.getName()).thenReturn("cutlet");

        burger.addIngredient(ing1);
        burger.addIngredient(ing2);
        burger.moveIngredient(0, 1);

        assertEquals("cutlet", burger.ingredients.get(0).getName());
        assertEquals("hot sauce", burger.ingredients.get(1).getName());
    }

    @Test
    public void testGetPrice() {
        Burger burger = new Burger();

        when(bunMock.getPrice()).thenReturn(2.0f);
        when(ingredientMock.getPrice()).thenReturn(1.0f);

        Ingredient ingredient2 = mock(Ingredient.class);
        when(ingredient2.getPrice()).thenReturn(2.0f);

        burger.setBuns(bunMock);
        burger.addIngredient(ingredientMock);
        burger.addIngredient(ingredient2);

        // 2 булки + 1 + 2 = 2*2 + 1 + 2 = 7.0
        assertEquals(7.0f, burger.getPrice(), PRICE_DELTA);
    }

    @Test
    public void testGetReceiptWithBunAndIngredients() {
        Burger burger = new Burger();

        when(bunMock.getName()).thenReturn("Белый");
        when(bunMock.getPrice()).thenReturn(2.5f);

        Ingredient sauceMock = mock(Ingredient.class);
        when(sauceMock.getType()).thenReturn(IngredientType.SAUCE);
        when(sauceMock.getName()).thenReturn("ketchup");

        Ingredient fillingMock = mock(Ingredient.class);
        when(fillingMock.getType()).thenReturn(IngredientType.FILLING);
        when(fillingMock.getName()).thenReturn("cutlet");

        burger.setBuns(bunMock);
        burger.addIngredient(sauceMock);
        burger.addIngredient(fillingMock);

        String receipt = burger.getReceipt();

        assertTrue(receipt.contains("Белый"));
        assertTrue(receipt.contains("ketchup"));
        assertTrue(receipt.contains("cutlet"));
        assertTrue(receipt.contains("Price:"));
    }

    @Test
    public void testGetReceiptWithBunOnly() {
        Burger burger = new Burger();

        when(bunMock.getName()).thenReturn("Ржаной");
        when(bunMock.getPrice()).thenReturn(3.0f);

        burger.setBuns(bunMock);
        String receipt = burger.getReceipt();

        assertTrue(receipt.contains("Ржаной"));
        assertTrue(receipt.contains("Price:"));
    }
}