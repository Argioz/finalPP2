package imb.progra2.cosmicleague.services;

import java.util.List;

import imb.progra2.cosmicleague.entity.Copa;

public interface ICopaService {
	 	List<Copa> obtenerTodos();
	    Copa buscarPorId(Long id);
	    Copa crearCopa(Copa copa);
	    String eliminarCopa(Long id);
	    Copa modificarCopa(Copa copa);
}
