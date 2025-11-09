package ma.dentopro.model;

import lombok.*;

@Data @AllArgsConstructor @NoArgsConstructor @Getter @Setter @ToString

public class CategorieActe {

    private Long idCategorieActe; // Identifiant unique de la catégorie
    private String libelle; // Libellé de la catégorie (ex: "Soins de carie", "Orthodontie")
}