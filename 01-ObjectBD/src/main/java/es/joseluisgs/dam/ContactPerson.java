package es.joseluisgs.dam;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class ContactPerson implements Serializable {

    private String firstName;

    private String lastName;

    private String phone;

    // standard getters, setters
}