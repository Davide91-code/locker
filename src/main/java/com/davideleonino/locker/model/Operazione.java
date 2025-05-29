package com.davideleonino.locker.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "statusbox")
public class Operazione {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String codiceAccesso;

    @ManyToOne
    @JoinColumn(name = "box_id")
    private Box boxAssociato;

    private String tipoOperazione;

    private LocalDateTime dataOrario;

    public Operazione(Integer id, String codiceAccesso, Box boxAssociato, String tipoOperazione, LocalDateTime dataOrario) {
        this.id = id;
        this.codiceAccesso = codiceAccesso;
        this.boxAssociato = boxAssociato;
        this.tipoOperazione = tipoOperazione;
        this.dataOrario = dataOrario;
    }

    public Operazione(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodiceAccesso() {
        return codiceAccesso;
    }

    public void setCodiceAccesso(String codiceAccesso) {
        this.codiceAccesso = codiceAccesso;
    }

    public Box getBoxAssociato() {
        return boxAssociato;
    }

    public void setBoxAssociato(Box boxAssociato) {
        this.boxAssociato = boxAssociato;
    }

    public String getTipoOperazione() {
        return tipoOperazione;
    }

    public void setTipoOperazione(String tipoOperazione) {
        this.tipoOperazione = tipoOperazione;
    }

    public LocalDateTime getDataOrario() {
        return dataOrario;
    }

    public void setDataOrario(LocalDateTime dataOrario) {
        this.dataOrario = dataOrario;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Operazione that = (Operazione) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }


}
