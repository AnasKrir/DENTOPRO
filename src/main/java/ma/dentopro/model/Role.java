package ma.dentopro.model;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor @NoArgsConstructor @Data @Getter @Setter @ToString
public class Role {

    private Long idRole;
    @Getter
    private ma.dentopro.model.enums.Role nomRole;
    @Getter
    private List<String> privileges = new ArrayList<>();;

    public void ajouterPrivilege(String privilege) {
        if (privilege != null && !privilege.isEmpty()) {
            this.privileges.add(privilege);
        }
    }


    public void supprimerPrivilege(String privilege) {
        if (privilege != null && !privilege.isEmpty()) {
            this.privileges.remove(privilege);
        }
    }



}
