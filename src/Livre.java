public class Livre {
    private int idLivre;
    private String titre;
    private String auteur;
    private int anneePublication;
    private String genre;
    private String isbn;
    private int quantiteDisponible;
    private String image;

    public Livre(int idLivre, String titre, String auteur, int anneePublication, String genre, String isbn, int quantiteDisponible, String image) {
        this.idLivre = idLivre;
        this.titre = titre;
        this.auteur = auteur;
        this.anneePublication = anneePublication;
        this.genre = genre;
        this.isbn = isbn;
        this.quantiteDisponible = quantiteDisponible;
        this.image = image;
    }

    // Getters
    public int getIdLivre() {
        return idLivre;
    }

    public String getTitre() {
        return titre;
    }

    public String getAuteur() {
        return auteur;
    }

    public int getAnneePublication() {
        return anneePublication;
    }

    public String getGenre() {
        return genre;
    }

    public String getIsbn() {
        return isbn;
    }

    public int getQuantiteDisponible() {
        return quantiteDisponible;
    }

    public String getImage() {
        return image;
    }
}
