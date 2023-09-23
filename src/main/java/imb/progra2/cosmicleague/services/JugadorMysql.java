package imb.progra2.cosmicleague.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import imb.progra2.cosmicleague.entity.Jugador;
import imb.progra2.cosmicleague.repository.JugadorRepository;

@Service
@Primary
public class JugadorMysql implements IJugadorService{

	@Autowired
	JugadorRepository repo;
	
	@Override
	public List<Jugador> obtenerTodos() {
		return repo.findAll();
	}

	@Override
	public Jugador buscarPorId(Long id) {
		Optional<Jugador> jugadorOptional = repo.findById(id);
        if (jugadorOptional.isPresent()) {
            return jugadorOptional.get();
        }
        else {
            return null;
        }
	}

	@Override
	public Jugador crearJugador(Jugador jugador) {
		return repo.save(jugador);
	}

	@Override
	public String eliminarJugador(Long id) {
		boolean existeRegistro = repo.existsById(id);
	    if (existeRegistro) {
	        repo.deleteById(id);
	        return "Jugador eliminado correctamente.";
	    } else {
	        return "Registro no encontrado.";
	    }
	}

	@Override
	public Jugador modificarJugador(Jugador jugadorModificado) {
		Long id = jugadorModificado.getId();
	    Optional<Jugador> jugadorOptional = repo.findById(id);
	    if (jugadorOptional.isPresent()) {
	        Jugador jugadorExistente = jugadorOptional.get();
	        jugadorExistente.setNombre(jugadorModificado.getNombre());
	        jugadorExistente.setApellido(jugadorModificado.getApellido());
	        jugadorExistente.setEdad(jugadorModificado.getEdad());
	        jugadorExistente.setDireccion(jugadorModificado.getDireccion());
	        jugadorExistente.setColor(jugadorModificado.getColor());
	        jugadorExistente.setPartidas(jugadorModificado.getPartidas());
	        jugadorExistente.setCopaGanada(jugadorModificado.getCopaGanada());
	        return repo.save(jugadorExistente);
	    } else {
	        return null;
	    }
	}
}
