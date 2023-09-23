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
import imb.progra2.cosmicleague.services.ICopaService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

@RestController
@ControllerAdvice
@RequestMapping("/api/v1")
public class CopaController {
	@Autowired
	ICopaService service;

	@GetMapping("/Copa")
	public ResponseEntity<APIResponse<List<Copa>>> obtenerTodos() {
	    APIResponse<List<Copa>> response = new APIResponse<List<Copa>>(HttpStatus.OK.value(), null, service.obtenerTodos());
	    return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@GetMapping("/Copa/{id}")
	public ResponseEntity<APIResponse<Copa>> buscarporId(@PathVariable("id") Long id) {
	    Copa copaPorId = service.buscarPorId(id);
	    if (this.existe(id)) {
	        APIResponse<Copa> response = new APIResponse<Copa>(HttpStatus.OK.value(), null, copaPorId);
	        return ResponseEntity.status(HttpStatus.OK).body(response);
	    } else {
	        List<String> messages = new ArrayList<>();
	        messages.add("No se encontró una Copa con ID: " + id.toString());
	        APIResponse<Copa> response = new APIResponse<Copa>(HttpStatus.BAD_REQUEST.value(), messages, copaPorId);
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	    }
	}

	@PostMapping("/Copa")
	public ResponseEntity<APIResponse<Copa>> crearCopa(@RequestBody Copa copa) {
	    if (this.existe(copa.getId())) {
	        List<String> messages = new ArrayList<>();
	        messages.add("Ya existe una Copa con ID: " + copa.getId());
	        messages.add("Si desea actualizar, use el verbo PUT.");
	        APIResponse<Copa> response = new APIResponse<Copa>(HttpStatus.BAD_REQUEST.value(), messages, copa);
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	    } else {
	        service.crearCopa(copa);
	        APIResponse<Copa> response = new APIResponse<Copa>(HttpStatus.CREATED.value(), null, copa);
	        return ResponseEntity.status(HttpStatus.CREATED).body(response);
	    }
	}

	@PutMapping("/Copa")
	public ResponseEntity<APIResponse<Copa>> modificarCopa(@RequestBody Copa copa) {
	    if (this.existe(copa.getId())) {
	        service.modificarCopa(copa);
	        APIResponse<Copa> response = new APIResponse<Copa>(HttpStatus.OK.value(), null, copa);
	        return ResponseEntity.status(HttpStatus.OK).body(response);
	    } else {
	        List<String> messages = new ArrayList<>();
	        messages.add("No existe una Copa con ID: " + copa.getId());
	        messages.add("Si desea crear una, use el verbo POST.");
	        APIResponse<Copa> response = new APIResponse<Copa>(HttpStatus.BAD_REQUEST.value(), messages, null);
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	    }
	}

	@DeleteMapping("/Copa/{id}")
	public ResponseEntity<APIResponse<Copa>> eliminar(@PathVariable("id") Long id) {
	    Copa copaPorId = service.buscarPorId(id);
	    if (copaPorId == null) {
	        List<String> messages = new ArrayList<>();
	        messages.add("No existe una Copa con ID: " + id.toString());
	        APIResponse<Copa> response = new APIResponse<Copa>(HttpStatus.BAD_REQUEST.value(), messages, null);
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	    } else {
	        service.eliminarCopa(id);
	        List<String> messages = new ArrayList<>();
	        messages.add("Ya no existe una Copa con ID: " + id.toString() + ". Fue exitosamente eliminada.");
	        APIResponse<Copa> response = new APIResponse<Copa>(HttpStatus.OK.value(), messages, null);
	        return ResponseEntity.status(HttpStatus.OK).body(response);
	    }
	}

	public boolean existe(Long id) {
	    if (id == null) {
	        return false;
	    }
	    Copa copa = service.buscarPorId(id);
	    return copa != null;
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<APIResponse<?>> handleConstraintViolationException(ConstraintViolationException ex) {
	    List<String> errors = new ArrayList<>();
	    for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
	        errors.add(violation.getMessage());
	    }
	    APIResponse<Copa> response = new APIResponse<Copa>(HttpStatus.BAD_REQUEST.value(), errors, null);
	    return ResponseEntity.badRequest().body(response);
	}

}
