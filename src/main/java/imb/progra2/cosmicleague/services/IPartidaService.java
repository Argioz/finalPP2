package imb.progra2.cosmicleague.services;

import java.util.List;

import imb.progra2.cosmicleague.entity.Partida;

public interface IPartidaService {
    List<Partida> obtenerTodos();
    Partida buscarPorId(Long id);
    Partida crearPartida(Partida partida);
    String eliminarPartida(Long id);
    Partida modificarPartida(Partida partida);
}
