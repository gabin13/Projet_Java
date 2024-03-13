import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import java.util.List;
import java.io.InputStream;

public class MainPage {
    public static Scene createMainPageScene(Stage primaryStage, Scene mainScene) {
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        // Récupérer les livres
        List<Livre> livres = DatabaseConnection.getLivres();

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        int column = 0;
        int row = 0;
        for (Livre livre : livres) {
            VBox livreBox = new VBox(5);
            Label titre = new Label(livre.getTitre());
            Label auteur = new Label(livre.getAuteur());
            Image image = null;

            try {
                String imagePath = "/ressources/images/" + livre.getImage(); // Assurez-vous que le chemin correspond à la structure de votre projet
                InputStream is = MainPage.class.getResourceAsStream(imagePath);
                if (is == null) {
                    throw new IllegalArgumentException("Fichier introuvable: " + imagePath);
                }
                image = new Image(is, 100, 150, true, true);
                is.close(); // Fermez le flux après utilisation
            } catch (Exception e) {
                System.err.println("Erreur lors du chargement de l'image: " + livre.getImage() + "\n" + e.getMessage());
                // Chargement d'une image de secours
                try (InputStream isFallback = MainPage.class.getResourceAsStream("/ressources/images/default.jpg")) {
                    if (isFallback != null) {
                        image = new Image(isFallback, 100, 150, true, true);
                    }
                } catch (Exception ex) {
                    System.err.println("Erreur lors du chargement de l'image de secours: " + ex.getMessage());
                }
            }

            ImageView imageView = new ImageView(image);
            livreBox.getChildren().addAll(imageView, titre, auteur);
            
            grid.add(livreBox, column, row);
            column++;
            if (column == 3) {
                column = 0;
                row++;
            }
        }

        layout.getChildren().addAll(new Label("Livres disponibles:"), grid, new Button("Déconnexion"));

        Scene scene = new Scene(layout, 800, 600);
        return scene;
    }
}
