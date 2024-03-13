import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseConnection {
    public static Connection getConnection() {
        try {
            String dbUrl = "jdbc:mysql://localhost:3306/biblio?serverTimezone=UTC&useSSL=false";
            String username = "root";
            String password = "_root453*";
            return DriverManager.getConnection(dbUrl, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    
    public static List<Livre> getLivres() {
        List<Livre> livres = new ArrayList<>();
        String query = "SELECT * FROM livre LIMIT 10"; // Limite à 10 pour l'exemple
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                livres.add(new Livre(
                    rs.getInt("id_livre"),
                    rs.getString("titre"),
                    rs.getString("auteur"),
                    rs.getInt("annee_publication"),
                    rs.getString("genre"),
                    rs.getString("isbn"),
                    rs.getInt("quantite_disponible"),
                    rs.getString("image")
                ));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return livres;
    }
}
