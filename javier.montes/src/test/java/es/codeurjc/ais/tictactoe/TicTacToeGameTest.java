package es.codeurjc.ais.tictactoe;


import static org.mockito.Mockito.mock;



import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.ArgumentCaptor;

import es.codeurjc.ais.tictactoe.TicTacToeGame.CellMarkedValue;
import es.codeurjc.ais.tictactoe.TicTacToeGame.EventType;
import es.codeurjc.ais.tictactoe.TicTacToeGame.WinnerValue;

public class TicTacToeGameTest {
	
	@Test
	public void SimulacionEmpate() {
		TicTacToeGame juego = new TicTacToeGame();
		Connection conexion1 = mock(Connection.class);
		Connection conexion2 = mock(Connection.class);
		juego.addConnection(conexion1);
		juego.addConnection(conexion2);
		Player jugador1 = new Player(0,"X","Alberto");
		Player jugador2 = new Player(1,"O","Javier");
		juego.addPlayer(jugador1);
		verify(conexion1).sendEvent(eq(EventType.JOIN_GAME), eq(Arrays.asList(jugador1)));
		verify(conexion2).sendEvent(eq(EventType.JOIN_GAME), eq(Arrays.asList(jugador1)));
		juego.addPlayer(jugador2);
		verify(conexion1,times(2)).sendEvent(eq(EventType.JOIN_GAME), eq(Arrays.asList(jugador1,jugador2)));
		verify(conexion2,times(2)).sendEvent(eq(EventType.JOIN_GAME), eq(Arrays.asList(jugador1,jugador2)));
		
		verify(conexion1).sendEvent(eq(EventType.SET_TURN),eq(jugador1));
		verify(conexion2).sendEvent(eq(EventType.SET_TURN),eq(jugador1));
		int[] jugadas = {0,1,2,3,4,6,7,8,5};
		int turno = 2;
		for(int jugada:jugadas) {
			juego.mark(jugada);
			
			ArgumentCaptor<CellMarkedValue> argumentCell = ArgumentCaptor.forClass(CellMarkedValue.class);
			verify(conexion1).sendEvent(eq(EventType.MARK), argumentCell.capture());
			assertEquals(argumentCell.getValue().cellId,jugada);
			assertEquals(argumentCell.getValue().player.getId(),turno%2);
			verify(conexion2).sendEvent(eq(EventType.MARK), argumentCell.capture());
			assertEquals(argumentCell.getValue().cellId,jugada);
			assertEquals(argumentCell.getValue().player.getId(),turno%2);
			
			if(juego.checkDraw()) {
				ArgumentCaptor<WinnerValue> argument = ArgumentCaptor.forClass(WinnerValue.class);
				verify(conexion1).sendEvent(eq(EventType.GAME_OVER), argument.capture());
				assertNull(argument.getValue());
				verify(conexion2).sendEvent(eq(EventType.GAME_OVER), argument.capture());
				assertNull(argument.getValue());
			}else if(turno%2==0) {
				verify(conexion1).sendEvent(eq(EventType.SET_TURN),eq(jugador2));
				verify(conexion2).sendEvent(eq(EventType.SET_TURN),eq(jugador2));	
			}else {
				verify(conexion1).sendEvent(eq(EventType.SET_TURN),eq(jugador1));
				verify(conexion2).sendEvent(eq(EventType.SET_TURN),eq(jugador1));	
			}
		turno++;
		reset(conexion1);
		reset(conexion2);
		}
	}

	@Test
	public void SimulacionVictoriaJugador1() {
		TicTacToeGame juego = new TicTacToeGame();
		Connection conexion1 = mock(Connection.class);
		Connection conexion2 = mock(Connection.class);
		juego.addConnection(conexion1);
		juego.addConnection(conexion2);
		Player jugador1 = new Player(0,"X","Alberto");
		Player jugador2 = new Player(1,"O","Javier");
		juego.addPlayer(jugador1);
		verify(conexion1).sendEvent(eq(EventType.JOIN_GAME), eq(Arrays.asList(jugador1)));
		verify(conexion2).sendEvent(eq(EventType.JOIN_GAME), eq(Arrays.asList(jugador1)));
		juego.addPlayer(jugador2);
		verify(conexion1,times(2)).sendEvent(eq(EventType.JOIN_GAME), eq(Arrays.asList(jugador1,jugador2)));
		verify(conexion2,times(2)).sendEvent(eq(EventType.JOIN_GAME), eq(Arrays.asList(jugador1,jugador2)));
		
		verify(conexion1).sendEvent(eq(EventType.SET_TURN),eq(jugador1));
		verify(conexion2).sendEvent(eq(EventType.SET_TURN),eq(jugador1));
		int[] jugadas = {0,3,1,4,2};
		int[] ganadora = {0,1,2};
		TicTacToeGame.WinnerResult resultado = new TicTacToeGame.WinnerResult();
		resultado.win = true;
		resultado.pos = ganadora;
		int turno = 2;
		for(int jugada:jugadas) {
			juego.mark(jugada);
			
			ArgumentCaptor<CellMarkedValue> argumentCell = ArgumentCaptor.forClass(CellMarkedValue.class);
			verify(conexion1).sendEvent(eq(EventType.MARK), argumentCell.capture());
			assertEquals(argumentCell.getValue().cellId,jugada);
			assertEquals(argumentCell.getValue().player.getId(),turno%2);
			verify(conexion2).sendEvent(eq(EventType.MARK), argumentCell.capture());
			assertEquals(argumentCell.getValue().cellId,jugada);
			assertEquals(argumentCell.getValue().player.getId(),turno%2);
			
			if(juego.checkWinner().win == true) {
				ArgumentCaptor<WinnerValue> argument = ArgumentCaptor.forClass(WinnerValue.class);
				verify(conexion1).sendEvent(eq(EventType.GAME_OVER), argument.capture());
				assertEquals(argument.getValue().player.getId(),turno%2);
				verify(conexion2).sendEvent(eq(EventType.GAME_OVER), argument.capture());
				assertEquals(argument.getValue().player.getId(),turno%2);
			}else if(turno%2==0) {
				verify(conexion1).sendEvent(eq(EventType.SET_TURN),eq(jugador2));
				verify(conexion2).sendEvent(eq(EventType.SET_TURN),eq(jugador2));	
			}else {
				verify(conexion1).sendEvent(eq(EventType.SET_TURN),eq(jugador1));
				verify(conexion2).sendEvent(eq(EventType.SET_TURN),eq(jugador1));	
			}
		turno++;
		reset(conexion1);
		reset(conexion2);
		}
	}
	
	@Test
	public void SimulacionVictoriaJugador2() {
		TicTacToeGame juego = new TicTacToeGame();
		Connection conexion1 = mock(Connection.class);
		Connection conexion2 = mock(Connection.class);
		juego.addConnection(conexion1);
		juego.addConnection(conexion2);
		Player jugador1 = new Player(0,"X","Alberto");
		Player jugador2 = new Player(1,"O","Javier");
		juego.addPlayer(jugador1);
		verify(conexion1).sendEvent(eq(EventType.JOIN_GAME), eq(Arrays.asList(jugador1)));
		verify(conexion2).sendEvent(eq(EventType.JOIN_GAME), eq(Arrays.asList(jugador1)));
		juego.addPlayer(jugador2);
		verify(conexion1,times(2)).sendEvent(eq(EventType.JOIN_GAME), eq(Arrays.asList(jugador1,jugador2)));
		verify(conexion2,times(2)).sendEvent(eq(EventType.JOIN_GAME), eq(Arrays.asList(jugador1,jugador2)));
		
		verify(conexion1).sendEvent(eq(EventType.SET_TURN),eq(jugador1));
		verify(conexion2).sendEvent(eq(EventType.SET_TURN),eq(jugador1));
		int[] jugadas = {4,0,5,1,7,2};
		int turno = 2;
		for(int jugada:jugadas) {
			juego.mark(jugada);
			
			ArgumentCaptor<CellMarkedValue> argumentCell = ArgumentCaptor.forClass(CellMarkedValue.class);
			verify(conexion1).sendEvent(eq(EventType.MARK), argumentCell.capture());
			assertEquals(argumentCell.getValue().cellId,jugada);
			assertEquals(argumentCell.getValue().player.getId(),turno%2);
			verify(conexion2).sendEvent(eq(EventType.MARK), argumentCell.capture());
			assertEquals(argumentCell.getValue().cellId,jugada);
			assertEquals(argumentCell.getValue().player.getId(),turno%2);
			
			if(juego.checkWinner().win == true) {
				ArgumentCaptor<WinnerValue> argument = ArgumentCaptor.forClass(WinnerValue.class);
				verify(conexion1).sendEvent(eq(EventType.GAME_OVER), argument.capture());
				assertEquals(argument.getValue().player.getId(),turno%2);
				verify(conexion2).sendEvent(eq(EventType.GAME_OVER), argument.capture());
				assertEquals(argument.getValue().player.getId(),turno%2);
			}else if(turno%2==0) {
				verify(conexion1).sendEvent(eq(EventType.SET_TURN),eq(jugador2));
				verify(conexion2).sendEvent(eq(EventType.SET_TURN),eq(jugador2));	
			}else {
				verify(conexion1).sendEvent(eq(EventType.SET_TURN),eq(jugador1));
				verify(conexion2).sendEvent(eq(EventType.SET_TURN),eq(jugador1));	
			}
		turno++;
		reset(conexion1);
		reset(conexion2);
		}
	}
}
