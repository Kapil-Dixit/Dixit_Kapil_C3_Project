import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class RestaurantTest {

//    RestaurantService service;

    Restaurant restaurant;

    private static final int PRICE_OF_SWEET_CORN=119;
    private static final int PRICE_OF_LASAGNE=269;


    //REFACTOR ALL THE REPEATED LINES OF CODE
    @BeforeEach
    public void setupRestaurantTest() {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant =new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",PRICE_OF_SWEET_CORN);
        restaurant.addToMenu("Vegetable lasagne", PRICE_OF_LASAGNE);
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        //WRITE UNIT TEST CASE HERE
        Restaurant testRestaurant = Mockito.spy(restaurant);

        //Mock the localtime to 10:31:00
        LocalTime currentTime1 = LocalTime.parse("10:31:00");
        //Mock the localtime to 21:59:00
        LocalTime currentTime2 = LocalTime.parse("21:59:00");
        //Mock the localtime to 10:30:00
        LocalTime currentTime3 = LocalTime.parse("10:30:00");
        //Mock the localtime to 22:00:00
        LocalTime currentTime4 = LocalTime.parse("22:00:00");

        doReturn(currentTime1, currentTime2, currentTime3, currentTime4).when(testRestaurant).getCurrentTime();
        assertTrue(testRestaurant.isRestaurantOpen());
        assertTrue(testRestaurant.isRestaurantOpen());
        assertTrue(testRestaurant.isRestaurantOpen());
        assertTrue(testRestaurant.isRestaurantOpen());
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        //WRITE UNIT TEST CASE HERE
        Restaurant testRestaurant = Mockito.spy(restaurant);

        //Mock the localtime to 10:29:00
        LocalTime currentTime1 = LocalTime.parse("10:29:00");
        //Mock the localtime to 22:01:00
        LocalTime currentTime2 = LocalTime.parse("22:01:00");

        doReturn(currentTime1, currentTime2).when(testRestaurant).getCurrentTime();
        assertFalse(testRestaurant.isRestaurantOpen());
        assertFalse(testRestaurant.isRestaurantOpen());

    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {

        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    //<<<<<<<<<<<<<<<<<<<<<<<Order Value>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    @Test
    public void no_items_selected_from_menu_should_give_0_order_value_when_empty_list_provided() {
        List<String> emptyListOfItems = new ArrayList<>();
        int expectedItemPrice = 0;
        assertEquals(expectedItemPrice, restaurant.getTotalPrice(emptyListOfItems));
    }
    @Test
    public void order_value_should_equal_sum_of_prices_of_items_selected() {
        List<String> itemList = setupItemList();
        assertEquals(PRICE_OF_LASAGNE + PRICE_OF_SWEET_CORN, restaurant.getTotalPrice(itemList));
    }
    @Test
    public void order_value_should_decrease_by_the_price_of_item_if_that_item_is_unselected_from_order() {
        List<String> itemList = setupItemList();
        assertEquals(PRICE_OF_LASAGNE + PRICE_OF_SWEET_CORN, restaurant.getTotalPrice(itemList));
        itemList.remove("Vegetable lasagne");
        assertEquals(PRICE_OF_SWEET_CORN, restaurant.getTotalPrice(itemList));
    }

    @Test
    public void order_value_should_increase_by_the_price_of_item_if_that_item_is_selected_to_order() {
        List<String> itemList= new ArrayList<>();
        itemList.add("Sweet corn soup");
        assertEquals(PRICE_OF_SWEET_CORN, restaurant.getTotalPrice(itemList));
        itemList.add("Vegetable lasagne");
        assertEquals(PRICE_OF_LASAGNE + PRICE_OF_SWEET_CORN, restaurant.getTotalPrice(itemList));
    }
    private List<String> setupItemList() {
        List<String> itemList= new ArrayList<>();
        itemList.add("Sweet corn soup");
        itemList.add("Vegetable lasagne");
        return itemList;
    }

    //<<<<<<<<<<<<<<<<<<<<<<<Order Value>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
}