package br.com.LeiloesTDSat.dao;

import br.com.LeiloesTDSat.models.ProdutosDTO;
import java.sql.PreparedStatement;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProdutosDAO {

    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();

    public void cadastrarProduto(ProdutosDTO produto) {
        conn = new conectaDAO().connectDB(); // Obter conexão 

        String sql = "INSERT INTO produtos (nome, valor, status) VALUES (?, ?, ?)"; 

        try {
            prep = conn.prepareStatement(sql);
            prep.setString(1, produto.getNome()); 
            prep.setDouble(2, produto.getValor()); 
            prep.setString(3, produto.getStatus()); 
            
            prep.executeUpdate();
            JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar produto: " + e.getMessage());
        } finally {
            // Fechar recursos para evitar vazamentos
        }
    }

    public ArrayList<ProdutosDTO> listarProdutos() {
      
        return null;
      
    }
}
