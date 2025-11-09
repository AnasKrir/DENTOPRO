package ma.dentopro.model;


import lombok.*;

@AllArgsConstructor @NoArgsConstructor @Data @Getter @Setter @ToString
public class Secretaire extends Utilisateur{

    private Double salaireDeBase ;
    private Double prime ;

}
