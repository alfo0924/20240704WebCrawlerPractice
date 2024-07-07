package fcu.web;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.awt.color.ICC_ColorSpace;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

class FromEarliestToTheLatest
{
    private String title;
    private DateTime date;

    public FromEarliestToTheLatest(String title,DateTime date)
    {
    this.title=title;
    this.date=date;
    }

    public String getTitle()
    {
    return title;
    }
    public DateTime getDate()
    {
    return date;
    }
    @Override
    public String toString()
    {
    DateTimeFormatter formatter=DateTimeFormat.forPattern("yyyy-MM-dd");
    return title+"-"+formatter.print(date);
    }


}


public class Main {

    public static void main(String[] args) {
    WebDriver driver = new ChromeDriver();
    driver.get("https://www.vscinemas.com.tw/vsweb/film/index.aspx");
    String title = driver.getTitle();
    System.out.println(title); //print out webPageTitle
    List<FromEarliestToTheLatest> dates = new ArrayList<>();
    //Create arrayList to input date into ArrayList in order to compare from the earliest to the latest
    try (
    FileWriter writer = new FileWriter("Movie.csv");
    CSVPrinter printer = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader("電影名稱", "英文名稱", "上映日期", "下映日期"))) {

    List<WebElement> elements = driver.findElements(By.cssSelector(".infoArea"));
    int count=0;

for (WebElement element : elements) {
    WebElement nameElement = element.findElement(By.cssSelector("a"));
    WebElement engNameElement = element.findElement(By.cssSelector("h3"));
    WebElement timeNameElement = element.findElement(By.cssSelector("time"));

    DateTime now = new DateTime();
    DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd");
    DateTime day40 = now.plusDays(40);
    String MovieTime = timeNameElement.getText();
    DateTime MovieTimedates = format.parseDateTime(MovieTime);

    day40 = MovieTimedates.plusDays(40);
    String MovieTimeAft40days = format.print(day40);

    dates.add(new FromEarliestToTheLatest(nameElement.getText(), MovieTimedates));

    Thread.sleep(15000);
    //add 2nd page
    driver.findElements(By.cssSelector(".pagebar a")).get(2).click();

    WebElement nameElement12 = element.findElement(By.cssSelector("a"));
    WebElement engNameElement12 = element.findElement(By.cssSelector("h3"));
    WebElement timeNameElement12 = element.findElement(By.cssSelector("time"));

    String MovieTime12 = timeNameElement12.getText();
    DateTime MovieTimedates12 = format.parseDateTime(MovieTime12);

    day40 = MovieTimedates12.plusDays(40);
    String MovieTimeAft40days12 = format.print(day40);

    dates.add(new FromEarliestToTheLatest(nameElement12.getText(), MovieTimedates12));

    Thread.sleep(15000);

    System.out.print(nameElement.getText() + "\t");
    System.out.print(engNameElement.getText() + "\t");
    System.out.print(timeNameElement.getText() + "\t");
    System.out.print(nameElement12.getText()+"\t");
    System.out.print(engNameElement12.getText()+"\t");
    System.out.print(timeNameElement12.getText()+"\t");

    System.out.print("Add 40 days :\t" + MovieTimeAft40days);
    System.out.print(MovieTimeAft40days12);

    String P1TitlePlusP2Title=nameElement.getText()+nameElement12.getText();
    String P1engPlusP2eng=engNameElement.getText()+engNameElement12.getText();
    String P1TimePlusP2Time=timeNameElement.getText()+timeNameElement12.getText();
    String P1Aft40DaysplusP2Aft40Days=MovieTimeAft40days+MovieTimeAft40days12;
//
//    printer.printRecord(nameElement.getText(),engNameElement.getText(),timeNameElement.getText(),MovieTimedates);
    printer.printRecord(P1TitlePlusP2Title,P1engPlusP2eng,P1TimePlusP2Time,P1Aft40DaysplusP2Aft40Days);

    //put into movie.csv
        }

    System.out.println("\nFrom earliest to the latest :\n");
    Collections.sort(dates, Comparator.comparing(FromEarliestToTheLatest::getDate));
    for (FromEarliestToTheLatest date : dates) {
        System.out.println(date);
    }


    }

catch(NoSuchElementException e)
{
    e.printStackTrace();
}
catch(IOException e)
{
    e.printStackTrace();
}
catch(InterruptedException e)
{
    e.printStackTrace();
}
   finally
    {
        driver.quit();
    }
    System.out.println("\n\n");
    WebDriver driver2 = new ChromeDriver();
    driver2.get("https://autos.yahoo.com.tw/new-cars/make/audi");

    String title2 = driver2.getTitle();
    System.out.println(title2);
    for (int i = 0; i < 1500; i++) {
        ((JavascriptExecutor) driver2).executeScript("window.scrollTo(0, document.body.scrollHeight)");
        //in order to automatically scroll down the page and load the data
    }

    try {

    List<WebElement> elements2 = driver2.findElements(By.cssSelector(" .year-single"));

    for (WebElement element : elements2) {

        WebElement nameElement = element.findElement(By.cssSelector("span.title"));
        WebElement priceElement = element.findElement(By.cssSelector("span.price"));


        System.out.print(nameElement.getText() + "\t");
        System.out.print("車價 :\t" + priceElement.getText() + "\t萬\n");
    }


    } catch (NoSuchElementException e) {
        e.printStackTrace();
    } finally {
        driver2.quit();
    }


}

}