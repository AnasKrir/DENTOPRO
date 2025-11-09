package ma.dentopro.model;


import lombok.*;


@AllArgsConstructor @NoArgsConstructor @Data @Getter @Setter @ToString
public class Personne {

    private String nom;
    private String prenom;
    private String adresse;
    private Long id;
    private String telephone;
    private String email;
    private String CIN;

}
