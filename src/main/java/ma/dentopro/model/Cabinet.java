package ma.dentopro.model;

import lombok.*;

import java.util.List;


@AllArgsConstructor @NoArgsConstructor @Data @Getter @Setter @ToString
public class Cabinet {

    Long idCabinet;
    String nom;
    String adresse;
    String logo;
    String email;
    Caisse caisse;
    List<Staff> staff;
    String tel;
    String telephone1;
    String telephone2;


}
