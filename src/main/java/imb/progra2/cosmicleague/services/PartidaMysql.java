package imb.progra2.cosmicleague.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import imb.progra2.cosmicleague.entity.Partida;
import imb.progra2.cosmicleague.repository.PartidaRepository;

@Service
@Primary
public class PartidaMysql implements IPartidaService{

	@Autowired
	PartidaRepository repo;
	
	@Override
	public List<Partida> obtenerTodos() {
		return repo.findAll();
	}

	@Override
	public Partida buscarPorId(Long id) {
		Optional<Partida> partidaOptional = repo.findById(id);
        if (partidaOptional.isPresent()) {
            return partidaOptional.get();
        }
        else {
            return null;
        }
	}

	@Override
	public Partida crearPartida(Partida partida) {
		return repo.save(partida);
	}

	@Override
	public String eliminarPartida(Long id) {
		boolean existeRegistro = repo.existsById(id);
	    if (existeRegistro) {
	        repo.deleteById(id);
	        return "Partida eliminada correctamente.";
	    } else {
	        return "Registro no encontrado.";
	    }
	}

	@Override
	public Partida modificarPartida(Partida partidaModificada) {
		Long id = partidaModificada.getId();
	    Optional<Partida> partidaOptional = repo.findById(id);
	    if (partidaOptional.isPresent()) {
	        Partida partidaExistente = partidaOptional.get();
	        partidaExistente.setFecha(partidaModificada.getFecha());
	        partidaExistente.setCopa(partidaModificada.getCopa());
	        partidaExistente.setJugadores(partidaModificada.getJugadores());
	        return repo.save(partidaExistente);
	    } else {
	        return null;
	    }
	}

}
