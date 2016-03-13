
import static junit.framework.TestCase.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class goEuroTest {

    public static void main(final String[] args) throws InterruptedException {

        // declaration and instantiation of objects/variables
        WebDriver driver = new FirefoxDriver();
        String baseUrl = "http://www.goeuro.de";

        // launch Firefox and direct it to the Base URL
        driver.get(baseUrl);
        driver.findElement(By.xpath(("//input[@tabindex='1']"))).sendKeys("Berlin");
        driver.findElement(By.xpath(("//input[@tabindex='2']"))).sendKeys("Prag");
        driver.findElement(By.className("checkbox")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//input[@id='search-form__submit-btn']")).submit();
        Thread.sleep(5000);

        // Creating an ArrayList to store all the prices from results displayed order.

        List<Double> priceList = new ArrayList<Double>();

        // Creating an arraylist to fetch the webelements to get prices
        List<WebElement> allElements = driver.findElements(By.xpath(
                    "//*[@id='results-train']//td[@class='price-cell']"));

        Iterator<WebElement> itr = allElements.iterator();

        while (itr.hasNext()) {
            WebElement element = itr.next();
            String currency_beforecomma;
            String currency_separator;
            String currency_decimals;
            currency_beforecomma = element.findElement(By.className("currency-beforecomma")).getText();
            currency_separator = element.findElement(By.className("currency-separator")).getText();
            currency_decimals = element.findElement(By.className("currency-decimals")).getText();

            // As the Price is splitted in to three different values in web page, combining all of them and parsing to
            // Double.

            Double totalPrice = Double.parseDouble(currency_beforecomma + currency_separator + currency_decimals);
            priceList.add(totalPrice);

        }

        // Creating another ArrayList to make a copy of Sorted prices List.

        List<Double> sortedArrayList = new ArrayList<Double>();

        // Copying of all the arrayList values to new arrayList.
        sortedArrayList.addAll(priceList);

        // Sorting the arrayList
        Collections.sort(sortedArrayList);

        /* Verifying that the sorted ArratList and the values in pricesList are both in same order. So that we can
         * make sure that the the values are displayed in sorted order in web page. */
        assertEquals(sortedArrayList, priceList);

        // close Firefox
        driver.close();

        // exit the program explicitly
        System.exit(0);
    }

}
