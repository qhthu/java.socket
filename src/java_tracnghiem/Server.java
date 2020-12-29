/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package java_tracnghiem;

import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *
 * @author quach
 */
public class Server {

    public static boolean check(int arr[], int n, int numberR) {

        for (int i = 0; i < n; i++) {
            if (numberR == arr[i]) {
                return true;
            }
        }
        return false;
    }

    public static int random(int arr[], int n) {

        while (true) {
            int rand = (int) (Math.random() * 200) + 1;
            if (!check(arr, n, rand)) {

                return rand;
            }
        }
    }

    public static void main(String[] args) throws Exception {
        ServerSocket server = new ServerSocket(1234);

        System.out.println("Server đã sẵn sàng");

        Socket client = server.accept();
        DataOutputStream dos = new DataOutputStream(client.getOutputStream());
        DataInputStream dis = new DataInputStream(client.getInputStream());

        Connection connect = Connect.getConnectionToMSSQL();
        Statement state = connect.createStatement();

        String sql = "select * from SINHVIEN";
        String sql2 = "select * from BODE";
        String sql3 = "select * from BANGDIEM";
        int diem = 0;
        String masv = "";
        boolean mess = true, check = true;
        do {
            ResultSet rs = state.executeQuery(sql);
            String uname = dis.readUTF();
            String passwd = dis.readUTF();
            while (rs.next()) {
                if (rs.getString("UserName").equals(uname) && rs.getString("PassWord").equals(passwd)) {
                    masv = rs.getString("MASV");
                    ResultSet rs3 = state.executeQuery(sql3);
                    while (rs3.next()) {
                        if (rs3.getString("MASV").equals(masv)) {
                            check = false;
                            break;
                        }
                        check = true;
                    }
                    mess = true;
                    break;
                } else {
                    mess = false;
                }
            }
            dos.writeBoolean(mess);
            dos.writeBoolean(check);
        } while (!mess || !check);

        int[] arrQ = new int[11];
        int count = 0;
        String[] arrD = new String[11];
        String kq = "";
        int i = 0;
        while (true) {
            int socau = random(arrQ, 10);
            arrQ[count] = socau;
            count++;
            ResultSet rs2 = state.executeQuery(sql2);
            while (rs2.next()) {
                if (socau == Integer.parseInt(rs2.getString("CAUHOI"))) {
                    String noidung = rs2.getString("NOIDUNG");
                    String dapanA = rs2.getString("A");
                    String dapanB = rs2.getString("B");
                    String dapanC = rs2.getString("C");
                    String dapanD = rs2.getString("D");
                    dos.writeUTF(noidung);
                    dos.writeUTF(dapanA);
                    dos.writeUTF(dapanB);
                    dos.writeUTF(dapanC);
                    dos.writeUTF(dapanD);

                    arrD[i] = dis.readUTF();
                    kq += arrD[i] + "/";
                    if (arrD[i].equals(rs2.getString("DAP_AN"))) {

                        diem++;
                    }
                    i++;
                    break;
                }
            }
            if (count >= 10) {
                break;
            }
        }
        dos.writeUTF(kq);
        dos.writeInt(diem);
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat dateOnly = new SimpleDateFormat("MM/dd/yyyy");
        String d = dateOnly.format(cal.getTime());
        try {
            String sql4 = "insert into BANGDIEM values ('" + masv + "'," + 1 + ",'" + d + "'," + diem + ",'" + 1 + "')";
            int row = state.executeUpdate(sql4);
            System.out.println("Thanh cong");
        } catch (Exception e) {
            System.out.println("That bai");
        }
        state.close();
        connect.close();

        server.close();
    }
}
