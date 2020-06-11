package es.codeurjc.ais.tictactoe;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;

public class BoardTest {
	private Board tablero;
	
	@BeforeEach
	public void SetUp(){
		tablero = new Board();
	}
	
	@Test
	public void Jugador1GanaTest() {
		String Jug1 = "X";
		String Jug2 = "O";
		int[] movimientos1 = {0,1,2};
		int[] movimientos2 = {3,6,7};
		for(int movimiento:movimientos1) {
			tablero.getCell(movimiento).value = Jug1;
		}
		for(int movimiento:movimientos2) {
			tablero.getCell(movimiento).value = Jug2;
		}
		assertFalse(tablero.checkDraw());
		assertArrayEquals(tablero.getCellsIfWinner(Jug1),movimientos1);
	}
	@Test
	public void Jugador2Gana() {
		String Jug1 = "X";
		String Jug2 = "O";
		int[] movimientos1 = {1,2,3};
		int[] movimientos2 = {0,4,8};
		for(int movimiento:movimientos1) {
			tablero.getCell(movimiento).value = Jug1;
		}
		for(int movimiento:movimientos2) {
			tablero.getCell(movimiento).value = Jug2;
		}
		assertFalse(tablero.checkDraw());
		assertArrayEquals(tablero.getCellsIfWinner(Jug2),movimientos2);
	}
	@Test
	public void Empate() {
		String Jug1 = "X";
		String Jug2 = "O";
		int[] movimientos1 = {1,2,3,7,0};
		int[] movimientos2 = {4,5,6,8};
		for(int movimiento:movimientos1) {
			tablero.getCell(movimiento).value = Jug1;
		}
		for(int movimiento:movimientos2) {
			tablero.getCell(movimiento).value = Jug2;
		}
		assertTrue(tablero.checkDraw());
	}
}

