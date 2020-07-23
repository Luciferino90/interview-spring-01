package it.demo.interview.interview.persistence.sql.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class User {

    private static final long serialVersionUID = 20200502_1423L;

    @Id
    private String id;

    @Column(unique = true)
    private String username;
    private String firstname;
    private String surname;
    @Column(name = "birth_date")
    private Date birthDate;

}
