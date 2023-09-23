package imb.progra2.cosmicleague.services;

import java.util.List;

import imb.progra2.cosmicleague.entity.Jugador;

public interface IJugadorService {
	List<Jugador> obtenerTodos();
    Jugador buscarPorId(Long id);
    Jugador crearJugador(Jugador jugador);
    String eliminarJugador(Long id);
    Jugador modificarJugador(Jugador jugador);
}
