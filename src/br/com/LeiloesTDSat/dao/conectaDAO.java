package br.com.LeiloesTDSat.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class conectaDAO {
        /**
         * Método para obter uma conexão com o banco de dados.
         *
         * @return Um objeto Connection ou null em caso de erro.
         */
    public Connection connectDB() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Driver atualizado
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/atividade2UC11?useTimezone=true&serverTimezone=UTC&useSSL=false&allowPublicKeyRetrieval=true", "root", "@gdsaJ371029");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver do banco de dados não encontrado: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Erro ao conectar ao banco de dados: " + e.getMessage());
        }
        return null;
    }


}
