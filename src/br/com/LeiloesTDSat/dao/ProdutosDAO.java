package br.com.LeiloesTDSat.dao;

import br.com.LeiloesTDSat.models.ProdutosDTO;
import java.sql.PreparedStatement;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
        ArrayList<ProdutosDTO> produtos = new ArrayList<>();
        conn = new conectaDAO().connectDB(); // obtém a conexão

        String sql = "SELECT * FROM produtos";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ProdutosDTO produto = new ProdutosDTO();
                produto.setId(rs.getInt("id")); 
                produto.setNome(rs.getString("nome")); 
                produto.setValor(rs.getDouble("valor")); 
                produto.setStatus(rs.getString("status")); 

                produtos.add(produto);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close(); // Fecha a conexão
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return produtos;
    }
    
   public void venderProduto(int id) {
    Connection conn = new conectaDAO().connectDB();
    PreparedStatement pstm = null;
    ResultSet rs = null;

    try {
        // 1. Verificar o status atual do produto
        String checkStatusSQL = "SELECT status FROM produtos WHERE id = ?";
        pstm = conn.prepareStatement(checkStatusSQL);
        pstm.setInt(1, id);
        rs = pstm.executeQuery();

        if (rs.next()) {
            String statusAtual = rs.getString("status");

            if ("Vendido".equalsIgnoreCase(statusAtual)) {
                JOptionPane.showMessageDialog(null, "Este produto já foi vendido!");
                return; // Sai do método sem tentar atualizar
            }
        } else {
            JOptionPane.showMessageDialog(null, "Nenhum produto encontrado com esse ID.");
            return; // Sai do método, pois o produto não existe
        }

        // 2. Atualizar para "Vendido" se ainda não estiver vendido
        String updateSQL = "UPDATE produtos SET status = 'Vendido' WHERE id = ?";
        pstm = conn.prepareStatement(updateSQL);
        pstm.setInt(1, id);
        int rowsUpdated = pstm.executeUpdate();

        if (rowsUpdated > 0) {
            JOptionPane.showMessageDialog(null, "Produto vendido com sucesso!");
        } else {
            JOptionPane.showMessageDialog(null, "Erro ao vender o produto.");
        }
    } catch (SQLException e) {
        System.out.println("Erro ao vender o produto: " + e.getMessage());
    } finally {
        try {
            if (rs != null) rs.close();
            if (pstm != null) pstm.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            System.out.println("Erro ao fechar conexão: " + e.getMessage());
        }
    }
}

    public List<ProdutosDTO> listarProdutosVendidos() {
        List<ProdutosDTO> produtosVendidos = new ArrayList<>();
        Connection conn = new conectaDAO().connectDB();
        PreparedStatement pstm = null;
        ResultSet rs = null;

        String sql = "SELECT * FROM produtos WHERE status = 'Vendido'";

        try {
            pstm = conn.prepareStatement(sql);
            rs = pstm.executeQuery();

            while (rs.next()) {
                ProdutosDTO produto = new ProdutosDTO();
                produto.setId(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));
                produto.setValor(rs.getDouble("valor"));
                produto.setStatus(rs.getString("status"));

                produtosVendidos.add(produto);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar produtos vendidos: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstm != null) {
                    pstm.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.out.println("Erro ao fechar conexão: " + e.getMessage());
            }
        }
        return produtosVendidos;
    }

}
