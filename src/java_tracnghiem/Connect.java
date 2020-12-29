/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package java_tracnghiem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author quach
 */
public class Connect {

    public static Connection getConnectionToMSSQL() {
        Connection connection = null;
        String url = "jdbc:sqlserver://;databaseName=KIEMTRALTM";
        String user = "sa";
        String pass = "123";
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(url, user, pass);
            System.out.println("Ket noi csdl thanh cong");
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("Khong ket noi csdl thanh cong");
        }

        return connection;
    }
}
