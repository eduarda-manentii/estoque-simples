package br.com.eduarda.simplestock;

public class Product {

    private Integer id;
    private String description;
    private Double amount;
    private Measurements measurements;

    public Product(Double amount, Measurements measurements, String description) {
        this.amount = amount;
        this.measurements = measurements;
        this.description = description;
        this.id = 1; //TODO: fazer gerar um número aleatório
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setMeasurements(Measurements measurements) {
        this.measurements = measurements;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public Double getAmount() {
        return amount;
    }

    public Measurements getMeasurements() {
        return measurements;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", amount=" + amount +
                ", measurements=" + measurements +
                '}';
    }
}
