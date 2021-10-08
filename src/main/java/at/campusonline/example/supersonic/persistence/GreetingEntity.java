package at.campusonline.example.supersonic.persistence;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
@Access(AccessType.FIELD)
@Table(name = "greetings")
public class GreetingEntity extends PanacheEntityBase {

    @Id
    private BigInteger id;

    @Column
    private String text;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}