package br.com.eduarda.simplestock;

public enum Measurements {
    UNIT,
    PKT,
    KG,
    LTS;

    @Override
    public String toString() {
        return this.name();
    }
}
