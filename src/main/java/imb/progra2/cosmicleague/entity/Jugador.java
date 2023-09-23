package imb.progra2.cosmicleague.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class Jugador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "El nombre no puede estar vacío.")
	@Size(max=50, message = "El nombre no puede tener más de 50 caracteres.")
    private String nombre;
    @NotBlank(message = "El apellido no puede estar vacío.")
	@Size(max=50, message = "El apellido no puede tener más de 50 caracteres.")
    private String apellido;
    private int edad;
    @NotBlank(message = "La dirección no puede estar vacía.")
	@Size(min = 3, max = 100, message = "Mínimo 3, máximo 100 caracteres.")
    private String direccion;
    private String color;
    
    @ManyToMany
    @JoinTable(name = "jugador_partida",
                joinColumns = @JoinColumn(name = "jugador_id"),
                inverseJoinColumns = @JoinColumn(name = "partida_id"))
    private List<Partida> partidas = new ArrayList<>();
    
    @ManyToOne
    @JoinColumn(name = "copa_ganada_id")
    private Copa copaGanada;
    
    public Copa getCopaGanada() {
		return copaGanada;
	}

	public void setCopaGanada(Copa copaGanada) {
		this.copaGanada = copaGanada;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

	public List<Partida> getPartidas() {
		return partidas;
	}

	public void setPartidas(List<Partida> partidas) {
		this.partidas = partidas;
	}
}
