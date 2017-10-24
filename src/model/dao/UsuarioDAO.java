/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

import connection.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Pflausino
 */
public class UsuarioDAO {

    public boolean checkLogin(String login, String senha) {

        //abre a conexao, prepara a SQL, traz um resultado,cria lista resultado
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean check = false;

        try {
            stmt = con.prepareStatement("SELECT * FROM usuarios "
                    + "WHERE login = ? and senha = ?");
            stmt.setString(1, login);
            stmt.setString(2, senha);
            
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                check = true;
            }


        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(
                    null, "Erro ao Receber dados do banco: " + ex
            );
        } finally {

            //codigo para encerar a conexao com o bd(sera executado com sucesso
            //ou falha) por isso sob o encadeamento "finally"
            ConnectionFactory.closeConnection(con, stmt, rs);

        }

        return check;
    }

    //Metodo para atualizar, similar ao create
    

}
