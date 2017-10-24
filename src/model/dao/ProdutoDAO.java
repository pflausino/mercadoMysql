/*
 * CRUD para mysql no java 
 */
package model.dao;

import connection.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import model.bean.Produto;

/**
 *
 * @author Pflausino
 */
public class ProdutoDAO {
    
    //Metodo de criação de novo produto
    public void create(Produto p){
        
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        
        try {
            //SQL para incerção, os campos com "?" sao subistituidos pelo 
            //PreparedStatement(stmt)
            stmt = con.prepareStatement(
                "INSERT INTO produtos (descricao, qtd, preco)VALUES(?,?,?)"
            );
            
            stmt.setString(1, p.getDescricao());
            stmt.setInt(2, p.getQtd());
            stmt.setDouble(3, p.getPreco());

            //Enviara ao BD
            stmt.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Salvo com Sucesso");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao Salvar: "+ex);
        } finally {
            //codigo para encerar a conexao com o bd(sera executado com sucesso
            //ou falha) por isso sob o encadeamento "finally"
            ConnectionFactory.closeConnection(con, stmt);
        }

        
    }
    
    public List<Produto> read(){
        
        //abre a conexao, prepara a SQL, traz um resultado,cria lista resultado
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Produto> produtos = new ArrayList<>();
        
        try {
            stmt = con.prepareStatement("SELECT * FROM produtos");
            rs = stmt.executeQuery();
            
            //laço de repetição para listar o retorno
            while (rs.next()) {
               
                Produto produto = new Produto();
                produto.setId(rs.getInt("id"));
                produto.setDescricao(rs.getString("descricao"));
                produto.setQtd(rs.getInt("qtd"));
                produto.setPreco(rs.getDouble("preco"));
                produtos.add(produto);
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(
                    null,"Erro ao Receber dados do banco: "+ ex
            );
        }finally{
            
            //codigo para encerar a conexao com o bd(sera executado com sucesso
            //ou falha) por isso sob o encadeamento "finally"
            ConnectionFactory.closeConnection(con, stmt,rs);
            
        }            
        
        return produtos;
    }
    
    //Metodo para atualizar, similar ao create
    public void update(Produto p){
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        
        try {
            //SQL para incerção, os campos com "?" sao subistituidos pelo 
            //PreparedStatement(stmt)
            stmt = con.prepareStatement(
                "UPDATE produtos SET "
                        + "descricao = ?, qtd = ?, preco = ?"
                        + "WHERE id = ?"
            );
            
            stmt.setString(1, p.getDescricao());
            stmt.setInt(2, p.getQtd());
            stmt.setDouble(3, p.getPreco());
            stmt.setInt(4, p.getId());

            //Enviara ao BD
            stmt.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Atualizado com Sucesso");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao Atualizar: "+ex);
        } finally {
            //codigo para encerar a conexao com o bd(sera executado com sucesso
            //ou falha) por isso sob o encadeamento "finally"
            ConnectionFactory.closeConnection(con, stmt);
        }

    }
    public void delete(Produto p){
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        
        try {
            //SQL para incerção, os campos com "?" sao subistituidos pelo 
            //PreparedStatement(stmt)
            stmt = con.prepareStatement(
                "DELETE FROM produtos WHERE id = ? "
                        
            );

            stmt.setInt(1, p.getId());

            //Enviara ao BD
            stmt.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Excluido com Sucesso");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao Excluir: "+ex);
        } finally {
            //codigo para encerar a conexao com o bd(sera executado com sucesso
            //ou falha) por isso sob o encadeamento "finally"
            ConnectionFactory.closeConnection(con, stmt);
        }

    }
    
}
