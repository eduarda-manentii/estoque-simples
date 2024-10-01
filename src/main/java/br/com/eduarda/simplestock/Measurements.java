package br.com.eduarda.simplestock;

public enum Measurements {
    UNIT("Unidade"),
    PKT("Pacote"),
    KG("Quilogramas"),
    LTS("Litros");

    private String description;

    Measurements(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
