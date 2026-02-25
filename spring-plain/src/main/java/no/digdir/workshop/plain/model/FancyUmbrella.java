package no.digdir.workshop.plain.model;

public class FancyUmbrella {

    private String color;

    public FancyUmbrella(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "FancyUmbrella{" +
                "color='" + color + '\'' +
                '}';
    }
}
