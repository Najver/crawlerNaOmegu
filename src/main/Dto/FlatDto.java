package main.Dto;

public class FlatDto {
    private int metraz;

    private String rozloha;

    private String energetickaNarocnost;

    private String stav;

    private String lokalita;

    private int cena;

    private boolean sklep;

    public FlatDto(int metraz, String rozloha, String energetickaNarocnost, String stav, String lokalita, int cena) {
        this.metraz = metraz;
        this.rozloha = rozloha;
        this.energetickaNarocnost = energetickaNarocnost;
        this.stav = stav;
        this.lokalita = lokalita;
        this.cena = cena;
    }

    public FlatDto() {

    }


    @Override
    public String toString() {
        return metraz + "," + rozloha + "," + energetickaNarocnost + "," + stav + "," + lokalita + "," + cena;
    }
}
