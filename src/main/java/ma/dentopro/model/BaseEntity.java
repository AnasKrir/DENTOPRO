package ma.dentopro.model;

import lombok.*;

import java.io.Serializable;

@Getter @Setter @NoArgsConstructor @EqualsAndHashCode @ToString
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;

    // Constructor with parameters, Lombok will automatically generate this.
    public BaseEntity(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}

