package imb.progra2.cosmicleague.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class Copa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombreCopa;
    @NotBlank(message = "El nombre del ganador no puede estar vacío.")
	@Size(max=50, message = "El nombre no puede tener más de 50 caracteres.")
    private String nombreGanador;
    private String colorCampeon;
    private Date fechaFinal;

    @OneToMany(mappedBy = "copa", cascade = CascadeType.ALL)
    private List<Partida> partidas = new ArrayList<>();

    @OneToMany(mappedBy = "copaGanada")
    private List<Jugador> jugadoresQueGanaron;
    
    public List<Jugador> getJugadoresQueGanaron() {
		return jugadoresQueGanaron;
	}

	public void setJugadoresQueGanaron(List<Jugador> jugadoresQueGanaron) {
		this.jugadoresQueGanaron = jugadoresQueGanaron;
	}

	public List<Partida> getPartidas() {
		return partidas;
	}

	public void setPartidas(List<Partida> partidas) {
		this.partidas = partidas;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreCopa() {
        return nombreCopa;
    }

    public void setNombreCopa(String nombreCopa) {
        this.nombreCopa = nombreCopa;
    }

    public String getNombreGanador() {
        return nombreGanador;
    }

    public void setNombreGanador(String nombreGanador) {
        this.nombreGanador = nombreGanador;
    }

    public Date getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public String getColorCampeon() {
        return colorCampeon;
    }

    public void setColorCampeon(String colorCampeon) {
        this.colorCampeon = colorCampeon;
    }
}
