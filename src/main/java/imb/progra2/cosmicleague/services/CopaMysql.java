package imb.progra2.cosmicleague.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import imb.progra2.cosmicleague.entity.Copa;
import imb.progra2.cosmicleague.repository.CopaRepository;

@Service
@Primary
public class CopaMysql implements ICopaService {

	@Autowired
	CopaRepository repo;
	
	@Override
	public List<Copa> obtenerTodos() {
		return repo.findAll();
	}

	@Override
	public Copa buscarPorId(Long id) {
		Optional<Copa> copaOptional = repo.findById(id);
        if (copaOptional.isPresent()) {
            return copaOptional.get();
        }
        else {
            return null;
        }
	}

	@Override
	public Copa crearCopa(Copa copa) {
		return repo.save(copa);
	}

	@Override
	public String eliminarCopa(Long id) {
		boolean existeRegistro = repo.existsById(id);
	    if (existeRegistro) {
	        repo.deleteById(id);
	        return "Copa eliminada correctamente.";
	    } else {
	        return "Registro no encontrado.";
	    }
	}

	@Override
	public Copa modificarCopa(Copa copaModificada) {
		Long id = copaModificada.getId();
	    Optional<Copa> copaOptional = repo.findById(id);
	    if (copaOptional.isPresent()) {
	        Copa copaExistente = copaOptional.get();
	        copaExistente.setNombreCopa(copaModificada.getNombreCopa());
	        copaExistente.setNombreGanador(copaModificada.getNombreGanador());
	        copaExistente.setColorCampeon(copaModificada.getColorCampeon());
	        copaExistente.setFechaFinal(copaModificada.getFechaFinal());
	        copaExistente.setPartidas(copaModificada.getPartidas());
	        return repo.save(copaExistente);
	    } else {
	        return null;
	    }
	}

}
