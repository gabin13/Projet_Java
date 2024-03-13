import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MainScreen extends Application {
	
	private Scene mainScene;

    @Override
    public void start(Stage primaryStage) {
        // Configuration des éléments de la scène principale
        Label welcomeLabel = new Label("Bienvenue à la Bibliothèque !");
        welcomeLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        Label descriptionLabel = new Label("Votre portail de connaissances et de découvertes littéraires.");
        descriptionLabel.setStyle("-fx-font-size: 16px;");
        Label shortPresentation = new Label("Depuis 50 ans, avec plus de 10 000 livres et documents à votre disposition.");
        shortPresentation.setStyle("-fx-font-size: 14px;");
        VBox centerContent = new VBox(10, welcomeLabel, descriptionLabel, shortPresentation);
        centerContent.setAlignment(Pos.CENTER);
        centerContent.setPadding(new Insets(20));

        Button loginButton = new Button("Connexion");
        loginButton.setOnAction(event -> {
            primaryStage.setScene(showLoginForm(primaryStage));
            primaryStage.setFullScreen(true); // Assurez-vous que le Stage principal passe en mode plein écran
        });

        Button becomeMemberButton = new Button("Devenir membre");
        becomeMemberButton.setOnAction(event -> {
            primaryStage.setScene(showRegisterForm(primaryStage));
            primaryStage.setFullScreen(true); // Active le mode plein écran
        });

        HBox navBar = new HBox(10, loginButton, becomeMemberButton);
        navBar.setAlignment(Pos.CENTER_RIGHT);
        navBar.setPadding(new Insets(10));

        BorderPane root = new BorderPane();
        root.setCenter(centerContent);
        root.setTop(navBar);

        // Configuration initiale de la scène principale
        mainScene = new Scene(root, 600, 400);
        primaryStage.setTitle("Système de Gestion de Bibliothèque");
        primaryStage.setScene(mainScene);
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitHint(""); // Enlève le message d'échappement pour le mode plein écran
        primaryStage.show();
    }

    private Scene showLoginForm(Stage primaryStage) {
        VBox loginLayout = new VBox(10);
        loginLayout.setAlignment(Pos.CENTER);
        loginLayout.setPadding(new Insets(10));
        loginLayout.setMaxWidth(300);

        Label emailLabel = new Label("Email :");
        TextField emailField = new TextField();
        emailField.setPromptText("Entrez votre email");

        Label passwordLabel = new Label("Mot de passe :");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Entrez votre mot de passe");

        Button loginButton = new Button("Se connecter");
        loginButton.setOnAction(e -> {
            if (authenticate(emailField.getText(), passwordField.getText())) {
                Scene mainPageScene = MainPage.createMainPageScene(primaryStage, mainScene); // Créer la scène de la page principale
                primaryStage.setScene(mainPageScene); // Définir la scène de la page principale
                primaryStage.setFullScreen(true); // Passer en mode plein écran
            } else {
                System.out.println("Échec de la connexion");
                // Gérer l'échec de la connexion
            }
        });
        Button backButton = new Button("Retour");
        backButton.setOnAction(e -> {
            primaryStage.setScene(mainScene);
            primaryStage.setFullScreen(true);
        });

        // Apply some styling using CSS
        loginLayout.getStyleClass().add("form-container");
        emailLabel.getStyleClass().add("form-label");
        passwordLabel.getStyleClass().add("form-label");
        loginButton.getStyleClass().add("form-button");
        backButton.getStyleClass().add("form-button");

        loginLayout.getChildren().addAll(emailLabel, emailField, passwordLabel, passwordField, loginButton, backButton);

        BorderPane outerContainer = new BorderPane();
        outerContainer.setCenter(loginLayout);

        Scene loginScene = new Scene(outerContainer, primaryStage.getWidth(), primaryStage.getHeight());
        loginScene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm()); // Link CSS file
        return loginScene;
    }

    private Scene showRegisterForm(Stage primaryStage) {
        VBox registerLayout = new VBox(10);
        registerLayout.setAlignment(Pos.CENTER);
        registerLayout.setPadding(new Insets(10));
        registerLayout.setMaxWidth(300);

        Label nameLabel = new Label("Nom :");
        TextField nameField = new TextField();
        nameField.setPromptText("Entrez votre nom");
        
        Label prenomLabel = new Label("Prénom :");
        TextField prenomField = new TextField();
        prenomField.setPromptText("Entrez votre prénom");
        
        Label adresseLabel = new Label("Adresse :");
        TextField adresseField = new TextField();
        adresseField.setPromptText("Entrez votre adresse");

        Label emailLabel = new Label("Email :");
        TextField emailField = new TextField();
        emailField.setPromptText("Entrez votre email");

        Label passwordLabel = new Label("Mot de passe :");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Entrez votre mot de passe");

        Label confirmPasswordLabel = new Label("Confirmer le mot de passe :");
        PasswordField confirmPasswordField = new PasswordField();
        confirmPasswordField.setPromptText("Confirmez votre mot de passe");

        Button registerButton = new Button("S'inscrire");
        registerButton.setOnAction(e -> {
        	if (insertUser(nameField.getText(), prenomField.getText(), adresseField.getText(), emailField.getText(), passwordField.getText())) {
                System.out.println("Inscription réussie");
                primaryStage.setScene(mainScene); // Retournez à la scène principale si l'inscription est réussie
                primaryStage.setFullScreen(true);
            } else {
                System.out.println("Échec de l'inscription");
                // Ici, vous pouvez afficher un message d'erreur à l'utilisateur
            }
        });

        Button backButton = new Button("Retour");
        backButton.setOnAction(e -> {
            primaryStage.setScene(mainScene);
            primaryStage.setFullScreen(true);
        });

        // Apply some styling using CSS
        registerLayout.getStyleClass().add("form-container");
        nameLabel.getStyleClass().add("form-label");
        emailLabel.getStyleClass().add("form-label");
        passwordLabel.getStyleClass().add("form-label");
        confirmPasswordLabel.getStyleClass().add("form-label");
        registerButton.getStyleClass().add("form-button");
        backButton.getStyleClass().add("form-button");

        registerLayout.getChildren().addAll(nameLabel, nameField, prenomLabel, prenomField, adresseLabel, adresseField, emailLabel, emailField, passwordLabel, passwordField, confirmPasswordLabel, confirmPasswordField, registerButton, backButton);

        BorderPane outerContainer = new BorderPane();
        outerContainer.setCenter(registerLayout);

        Scene registerScene = new Scene(outerContainer, 600, 400);
        registerScene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm()); // Link CSS file
        return registerScene;
    }
    
    private boolean insertUser(String nom, String prenom, String adresse, String email, String password) {
        String query = "INSERT INTO membre (nom, prenom, adresse, email, password, date_inscription, role, statut) VALUES (?, ?, ?, ?, ?, CURRENT_DATE(), 'user', '1')";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, nom);
            pstmt.setString(2, prenom);
            pstmt.setString(3, adresse);
            pstmt.setString(4, email);
            pstmt.setString(5, password); // Pensez à hasher le mot de passe en production
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    private boolean authenticate(String email, String password) {
        String query = "SELECT * FROM membre WHERE email = ? AND password = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, email);
            pstmt.setString(2, password); // En production, utilisez un hash du mot de passe pour la vérification
            
            var resultSet = pstmt.executeQuery();
            return resultSet.next(); // Retourne true si un utilisateur correspondant est trouvé
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }



}
