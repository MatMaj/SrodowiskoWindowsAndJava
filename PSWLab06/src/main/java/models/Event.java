package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

@Entity
@Table(name="events")
public class Event {
    @Id
    private Long id;
    private String name;
    private String agenda;
    private Date date;

    public Event(Long id, String name, String agenda, Date date) {
        this.id = id;
        this.name = name;
        this.agenda = agenda;
        this.date = date;
    }

    public Event(String name, String agenda, Date date) {
        this.name = name;
        this.agenda = agenda;
        this.date = date;
    }

    public Event(){

    }

    public Event(Long id){
        this.id=id;
    }

    @Override
    public String toString(){
        return name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAgenda() {
        return agenda;
    }

    public Date getDate() {
        return date;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAgenda(String agenda) {
        this.agenda = agenda;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
