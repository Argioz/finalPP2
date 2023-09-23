package imb.progra2.cosmicleague.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import imb.progra2.cosmicleague.entity.Copa;
import imb.progra2.cosmicleague.entity.Jugador;
import imb.progra2.cosmicleague.entity.Partida;
import imb.progra2.cosmicleague.services.IJugadorService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

@RestController
@ControllerAdvice
@RequestMapping("/api/v1")
public class JugadorController {

	@Autowired
	IJugadorService service;
	
	@GetMapping("/Jugador")
	public ResponseEntity<APIResponse<List<Jugador>>>obtenerTodos(){
		APIResponse<List<Jugador>> response = new APIResponse<List<Jugador>>(200, null, service.obtenerTodos());	
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@GetMapping("/Jugador/{id}")
	public ResponseEntity<APIResponse<Jugador>> buscarporId(@PathVariable("id") Long id) {
	    Jugador jugadorPorId = service.buscarPorId(id);
	    if (this.existe(id)) {
	        APIResponse<Jugador> response = new APIResponse<Jugador>(HttpStatus.OK.value(), null, jugadorPorId);
	        return ResponseEntity.status(HttpStatus.OK).body(response);
	    } else {
	        List<String> messages = new ArrayList<>();
	        messages.add("No se encontró un Jugador con ID: " + id.toString());
	        APIResponse<Jugador> response = new APIResponse<Jugador>(HttpStatus.BAD_REQUEST.value(), messages, jugadorPorId);
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	    }
	}

	@PostMapping("/Jugador")
	public ResponseEntity<APIResponse<Jugador>> crearJugador(@RequestBody Jugador jugador) {
	    if (this.existe(jugador.getId())) {
	        List<String> messages = new ArrayList<>();
	        messages.add("Ya existe un Jugador con ID: " + jugador.getId());
	        messages.add("Si desea actualizar, use el verbo PUT.");
	        APIResponse<Jugador> response = new APIResponse<Jugador>(HttpStatus.BAD_REQUEST.value(), messages, jugador);
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	    } else {
	        service.crearJugador(jugador);
	        APIResponse<Jugador> response = new APIResponse<Jugador>(HttpStatus.CREATED.value(), null, jugador);
	        return ResponseEntity.status(HttpStatus.CREATED).body(response);
	    }
	}
	
	@PutMapping("/Jugador")
	public ResponseEntity<APIResponse<Jugador>> modificarJugador(@RequestBody Jugador jugador) {
	    if (this.existe(jugador.getId())) {
	        service.modificarJugador(jugador);
	        APIResponse<Jugador> response = new APIResponse<Jugador>(HttpStatus.OK.value(), null, jugador);
	        return ResponseEntity.status(HttpStatus.OK).body(response);
	    } else {
	        List<String> messages = new ArrayList<>();
	        messages.add("No existe un Jugador con ID: " + jugador.getId());
	        messages.add("Si desea crear uno, use el verbo POST.");
	        APIResponse<Jugador> response = new APIResponse<Jugador>(HttpStatus.BAD_REQUEST.value(), messages, null);
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	    }
	}
	
	@DeleteMapping("/Jugador/{id}")
	public ResponseEntity<APIResponse<Jugador>> eliminar(@PathVariable("id") Long id) {
	    Jugador jugadorPorId = service.buscarPorId(id);
	    if (jugadorPorId == null) {
	        List<String> messages = new ArrayList<>();
	        messages.add("No existe un Jugador con ID: " + id.toString());
	        APIResponse<Jugador> response = new APIResponse<Jugador>(HttpStatus.BAD_REQUEST.value(), messages, null);
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	    } else {
	        service.eliminarJugador(id);
	        List<String> messages = new ArrayList<>();
	        messages.add("Ya no existe un Jugador con ID: " + id.toString() + ". Fue exitosamente eliminado.");
	        APIResponse<Jugador> response = new APIResponse<Jugador>(HttpStatus.OK.value(), messages, null);
	        return ResponseEntity.status(HttpStatus.OK).body(response);
	    }
	}

	public boolean existe(Long id) {
	    if (id == null) {
	        return false;
	    }
	    Jugador jugador = service.buscarPorId(id);
	    return jugador != null;
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<APIResponse<?>> handleConstraintViolationException(ConstraintViolationException ex) {
	    List<String> errors = new ArrayList<>();
	    for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
	        errors.add(violation.getMessage());
	    }
	    APIResponse<Jugador> response = new APIResponse<Jugador>(HttpStatus.BAD_REQUEST.value(), errors, null);
	    return ResponseEntity.badRequest().body(response);
	}

	
	
}
