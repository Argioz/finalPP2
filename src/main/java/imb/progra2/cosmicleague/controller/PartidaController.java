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

import imb.progra2.cosmicleague.entity.Partida;
import imb.progra2.cosmicleague.services.IPartidaService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

@RestController
@ControllerAdvice
@RequestMapping("/api/v1")
public class PartidaController {
	@Autowired
	IPartidaService service;

	@GetMapping("/Partida")
	public ResponseEntity<APIResponse<List<Partida>>> obtenerTodos() {
	    APIResponse<List<Partida>> response = new APIResponse<List<Partida>>(HttpStatus.OK.value(), null, service.obtenerTodos());
	    return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@GetMapping("/Partida/{id}")
	public ResponseEntity<APIResponse<Partida>> buscarporId(@PathVariable("id") Long id) {
	    Partida partidaPorId = service.buscarPorId(id);
	    if (this.existe(id)) {
	        APIResponse<Partida> response = new APIResponse<Partida>(HttpStatus.OK.value(), null, partidaPorId);
	        return ResponseEntity.status(HttpStatus.OK).body(response);
	    } else {
	        List<String> messages = new ArrayList<>();
	        messages.add("No se encontró una Partida con ID: " + id.toString());
	        APIResponse<Partida> response = new APIResponse<Partida>(HttpStatus.BAD_REQUEST.value(), messages, partidaPorId);
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	    }
	}

	@PostMapping("/Partida")
	public ResponseEntity<APIResponse<Partida>> crearPartida(@RequestBody Partida partida) {
	    if (this.existe(partida.getId())) {
	        List<String> messages = new ArrayList<>();
	        messages.add("Ya existe una Partida con ID: " + partida.getId());
	        messages.add("Si desea actualizar, use el verbo PUT.");
	        APIResponse<Partida> response = new APIResponse<Partida>(HttpStatus.BAD_REQUEST.value(), messages, partida);
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	    } else {
	        service.crearPartida(partida);
	        APIResponse<Partida> response = new APIResponse<Partida>(HttpStatus.CREATED.value(), null, partida);
	        return ResponseEntity.status(HttpStatus.CREATED).body(response);
	    }
	}

	@PutMapping("/Partida")
	public ResponseEntity<APIResponse<Partida>> modificarPartida(@RequestBody Partida partida) {
	    if (this.existe(partida.getId())) {
	        service.modificarPartida(partida);
	        APIResponse<Partida> response = new APIResponse<Partida>(HttpStatus.OK.value(), null, partida);
	        return ResponseEntity.status(HttpStatus.OK).body(response);
	    } else {
	        List<String> messages = new ArrayList<>();
	        messages.add("No existe una Partida con ID: " + partida.getId());
	        messages.add("Si desea crear una, use el verbo POST.");
	        APIResponse<Partida> response = new APIResponse<Partida>(HttpStatus.BAD_REQUEST.value(), messages, null);
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	    }
	}

	@DeleteMapping("/Partida/{id}")
	public ResponseEntity<APIResponse<Partida>> eliminar(@PathVariable("id") Long id) {
	    Partida partidaPorId = service.buscarPorId(id);
	    if (partidaPorId == null) {
	        List<String> messages = new ArrayList<>();
	        messages.add("No existe una Partida con ID: " + id.toString());
	        APIResponse<Partida> response = new APIResponse<Partida>(HttpStatus.BAD_REQUEST.value(), messages, null);
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	    } else {
	        service.eliminarPartida(id);
	        List<String> messages = new ArrayList<>();
	        messages.add("Ya no existe una Partida con ID: " + id.toString() + ". Fue exitosamente eliminada.");
	        APIResponse<Partida> response = new APIResponse<Partida>(HttpStatus.OK.value(), messages, null);
	        return ResponseEntity.status(HttpStatus.OK).body(response);
	    }
	}

	public boolean existe(Long id) {
	    if (id == null) {
	        return false;
	    }
	    Partida partida = service.buscarPorId(id);
	    return partida != null;
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<APIResponse<?>> handleConstraintViolationException(ConstraintViolationException ex) {
	    List<String> errors = new ArrayList<>();
	    for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
	        errors.add(violation.getMessage());
	    }
	    APIResponse<Partida> response = new APIResponse<Partida>(HttpStatus.BAD_REQUEST.value(), errors, null);
	    return ResponseEntity.badRequest().body(response);
	}

}
