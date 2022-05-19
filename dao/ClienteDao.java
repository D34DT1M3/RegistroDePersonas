/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.curso.dao;

import com.mycompany.curso.models.Cliente;
import com.mysql.jdbc.StringUtils;
import java.beans.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 57320
 */
public class ClienteDao {
    public Connection conectar(){
        String baseDeDatos = "java";
        String usuario = "root";
        String password = "";
        String host = "localhost";
        String puerto = "3306";
        String driver = "com.mysql.jdbc.Driver";
        String conexionUrl = "jdbc:mysql://"+host+":"+puerto+"/"+baseDeDatos+"?useSSL=false";
        
        Connection conexion  = null;
        try {
            Class.forName(driver);
            conexion = DriverManager.getConnection(conexionUrl, usuario, password);
        } catch (Exception ex) {
            Logger.getLogger(ClienteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conexion;
}
    public void agregar(Cliente cliente){
        try {
            Connection conexion  = conectar();
            String sql = "INSERT INTO `clientes` (`id`, `nombre`, `apellido`, `telefono`, `email`) VALUES (NULL, '"
                    +cliente.getNombre()+"', '"
                    +cliente.getApellido()+"', '"
                    +cliente.getTelefono()+"', '"
                    +cliente.getEmail()+"');";
            java.sql.Statement statement =  conexion.createStatement();
            statement.execute(sql);
        } catch (Exception ex) {
            Logger.getLogger(ClienteDao.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }
    public void actualizar(Cliente cliente){
        try {
            Connection conexion  = conectar();
            String sql = "UPDATE `clientes` SET `nombre` = '"+cliente.getNombre()+
                    "', `apellido` = '"+cliente.getApellido()+
                    "', `telefono` = '"+cliente.getTelefono()+
                    "', `email` = '"+cliente.getEmail()+
                    "' WHERE `clientes`.`id` = "+cliente.getId()+";";
            java.sql.Statement statement =  conexion.createStatement();
            statement.execute(sql);
        } catch (Exception ex) {
            Logger.getLogger(ClienteDao.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }
    public List<Cliente> listar(){
        List<Cliente> listado = new ArrayList<>();
        try {
            Connection conexion  = conectar();
            
            String sql = "SELECT * FROM `clientes`;";
            
            java.sql.Statement statement =  conexion.createStatement();
            ResultSet resultado = statement.executeQuery(sql); //trae todos los datos de la consulta
            
            while(resultado.next()){
                Cliente cliente = new Cliente();
                cliente.setId(resultado.getString("id"));
                cliente.setNombre(resultado.getString("nombre"));
                cliente.setApellido(resultado.getString("apellido"));
                cliente.setEmail(resultado.getString("email"));
                cliente.setTelefono(resultado.getString("telefono"));
                listado.add(cliente);
            }
        } catch (Exception ex) {
            Logger.getLogger(ClienteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listado;
        
    
    
    }
    
    public void eliminar(String id){
try {
            Connection conexion  = conectar();
            String sql = "DELETE FROM `clientes` WHERE `clientes`.`id` ="+id;
            java.sql.Statement statement =  conexion.createStatement();
            statement.execute(sql);
        } catch (Exception ex) {
            Logger.getLogger(ClienteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
}

    public void guardar(Cliente cliente) {
        if(StringUtils.isEmptyOrWhitespaceOnly(cliente.getId())){
            agregar(cliente);
        }
        else{
            actualizar(cliente);
        }
    }
}
