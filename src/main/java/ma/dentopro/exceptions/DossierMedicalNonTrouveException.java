package ma.dentopro.exceptions;


public class DossierMedicalNonTrouveException extends RuntimeException {
    private Long dossierId;

    public DossierMedicalNonTrouveException(String message, Long dossierId) {
        super(message);
        this.dossierId = dossierId;
    }
    public DossierMedicalNonTrouveException(String message) {
        super(message);

    }

    public Long getDossierId() {
        return dossierId;
    }
}

