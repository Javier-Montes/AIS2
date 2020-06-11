package es.codeurjc.ais.tictactoe;






import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TicTacToeHandlerTest {
	 private WebDriver driver1,driver2;
	
	 @BeforeAll
	 public static void setupClass() {
	 WebDriverManager.firefoxdriver().setup();
	 WebApp.start();
	 }
	 
	 @AfterAll
	 public static void teardownclass() {
	 WebApp.stop();
	 }
	
	 @BeforeEach
	 public void setupTest() {
	 driver1 = new FirefoxDriver();
	 driver2 = new FirefoxDriver();
	 }
	 
	 @AfterEach
	 public void teardown() {
	 if (driver1 != null) {
		 driver1.quit();}
	 if (driver2 != null) {
		 driver2.quit();}
	 }
	 
	 @Test
	 public void SimulacionEmpate(){
	 driver1.get("http://localhost:8080/");
	 driver2.get("http://localhost:8080/");
	 String jugador1 = "Alberto";
	 String jugador2 = "Montes";
	 WebElement webElement1 = driver1.findElement(By.id("nickname"));
	 WebElement webElement2 = driver2.findElement(By.id("nickname"));
	 webElement1.sendKeys(jugador1);
	 webElement2.sendKeys(jugador2);
	 WebElement webElement3 = driver1.findElement(By.id("startBtn"));
	 WebElement webElement4 = driver2.findElement(By.id("startBtn"));
	 webElement3.click();
	 webElement4.click();
	 int[] jugadas = {0,1,2,3,4,6,7,8,5};
	 int turno = 2;
		for(int jugada:jugadas) {
			if (turno%2 == 0) {
				 WebElement webElement5 = driver1.findElement(By.id("cell-" + jugada));
				 webElement5.click();
			}else {
				WebElement webElement6 = driver2.findElement(By.id("cell-" + jugada));
				webElement6.click();
			}
			
			if(turno == 11) {
				assertEquals(driver2.switchTo().alert().getText(),"Draw!");
				assertEquals(driver1.switchTo().alert().getText(),"Draw!");
			}
		turno++;	
	 }
	 }
	 
	 @Test
	 public void SimulacionVictoriaJugador1(){
	 driver1.get("http://localhost:8080/");
	 driver2.get("http://localhost:8080/");
	 String jugador1 = "Alberto";
	 String jugador2 = "Montes";
	 WebElement webElement1 = driver1.findElement(By.id("nickname"));
	 WebElement webElement2 = driver2.findElement(By.id("nickname"));
	 webElement1.sendKeys(jugador1);
	 webElement2.sendKeys(jugador2);
	 WebElement webElement3 = driver1.findElement(By.id("startBtn"));
	 WebElement webElement4 = driver2.findElement(By.id("startBtn"));
	 webElement3.click();
	 webElement4.click();
	 int[] jugadas = {0,3,1,4,2};
	 int turno = 2;
		for(int jugada:jugadas) {
			if (turno%2 == 0) {
				 WebElement webElement5 = driver1.findElement(By.id("cell-" + jugada));
				 webElement5.click();
			}else {
				WebElement webElement6 = driver2.findElement(By.id("cell-" + jugada));
				webElement6.click();
			}
			
			if(turno == 7) {
				assertEquals(driver2.switchTo().alert().getText(), jugador1 +" wins! "+ jugador2 + " looses. ");
				assertEquals(driver1.switchTo().alert().getText(), jugador1 +" wins! "+ jugador2 + " looses. ");
			}
		turno++;	
	 }
	 }
	 
	 @Test
	 public void SimulacionDerrotaJugador1(){
	 driver1.get("http://localhost:8080/");
	 driver2.get("http://localhost:8080/");
	 String jugador1 = "Alberto";
	 String jugador2 = "Montes";
	 WebElement webElement1 = driver1.findElement(By.id("nickname"));
	 WebElement webElement2 = driver2.findElement(By.id("nickname"));
	 webElement1.sendKeys(jugador1);
	 webElement2.sendKeys(jugador2);
	 WebElement webElement3 = driver1.findElement(By.id("startBtn"));
	 WebElement webElement4 = driver2.findElement(By.id("startBtn"));
	 webElement3.click();
	 webElement4.click();
	 int[] jugadas = {4,0,5,1,7,2};
	 int turno = 2;
		for(int jugada:jugadas) {
			if (turno%2 == 0) {
				 WebElement webElement5 = driver1.findElement(By.id("cell-" + jugada));
				 webElement5.click();
			}else {
				WebElement webElement6 = driver2.findElement(By.id("cell-" + jugada));
				webElement6.click();
			}
			
			if(turno == 8) {
				assertEquals(driver2.switchTo().alert().getText(), jugador2 +" wins! "+ jugador1 + " looses. ");
				assertEquals(driver1.switchTo().alert().getText(), jugador2 +" wins! "+ jugador1 + " looses. ");
			}
		turno++;	
	 }
	 }
}
