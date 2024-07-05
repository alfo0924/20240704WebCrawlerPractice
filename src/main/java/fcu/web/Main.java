package fcu.web;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

public class Main {
    public static void main(String[] args) {

       WebDriver driver =new ChromeDriver();
       driver.get("https://www.vscinemas.com.tw/vsweb/film/index.aspx");
       String title=driver.getTitle();
       System.out.println(title);

       try(FileWriter writer=new FileWriter("Movie.csv");
       CSVPrinter printer=new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader("電影名稱","英文名稱","上映日期")))
       {
           List<WebElement> elements =driver.findElements(By.cssSelector(".infoArea"));

           for(WebElement element : elements)
           {
            WebElement nameElement = element.findElement(By.cssSelector("a"));
            WebElement engNameElement=element.findElement(By.cssSelector("h3"));
            WebElement timeNameElement=element.findElement(By.cssSelector("time"));


            System.out.print(nameElement.getText()+"\t");
            System.out.print(engNameElement.getText()+"\t");
            System.out.println(timeNameElement.getText());

               printer.printRecord(nameElement.getText(),engNameElement.getText(),timeNameElement.getText());


           }

       }
       catch (NoSuchElementException e)
       {
           e.printStackTrace();
       }
       catch(IOException e)
       {
           e.printStackTrace();
       }
       finally
       {
           driver.quit();
       }

       WebDriver driver2=new ChromeDriver();
       driver2.get("https://autos.yahoo.com.tw/new-cars/make/audi");
       String title2=driver2.getTitle();
       System.out.println(title2);
       try{

       List<WebElement> elements2=driver2.findElements(By.cssSelector(".year-single"));

       for(WebElement element : elements2)
       {
        WebElement nameElement=element.findElement(By.cssSelector("span.title"));
        WebElement priceElement=element.findElement(By.cssSelector("span.price"));
        System.out.print(nameElement.getText()+"\t");
        System.out.print(priceElement.getText()+"\n");

       }


       }
       catch(NoSuchElementException e)
       {
           e.printStackTrace();
       }
       finally
       {
           driver2.quit();
       }











    }
}