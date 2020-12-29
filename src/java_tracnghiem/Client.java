/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package java_tracnghiem;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author quach
 */
public class Client {

    public static String KTDapAn() {
        Scanner s = new Scanner(System.in);
        String dapan = "";
        while (true) {
            try {
                dapan = s.nextLine();
                if (dapan.equalsIgnoreCase("A") || dapan.equalsIgnoreCase("B") || dapan.equalsIgnoreCase("C") || dapan.equalsIgnoreCase("D")) {
                    return dapan;
                } else {
                    System.out.println("Vui long nhap dap an (A, B, C, D)!!");
                }
            } catch (Exception e) {
                System.out.println("Vui long nhap dap an (A, B, C, D)!!");
                s.nextLine();
            }
        }

    }

    public static void main(String[] args) throws Exception {
        Socket client = new Socket("localhost", 1234);
        DataInputStream input = new DataInputStream(client.getInputStream());
        DataOutputStream ouput = new DataOutputStream(client.getOutputStream());
        String uname, passwd;
        Scanner s = new Scanner(System.in);
        boolean mess = true, check = true;
        do {
            System.out.print("Vui long nhap username: ");
            uname = s.nextLine();
            System.out.print("Vui long nhap password: ");
            passwd = s.nextLine();
            ouput.writeUTF(uname);
            ouput.writeUTF(passwd);
            mess = input.readBoolean();
            check = input.readBoolean();
            if (mess && check) {
                System.out.println("Dang nhap thanh cong!");
                break;
            } else if (!check && mess) {
                System.out.println("Sinh vien da thi roi, khong duoc lam bai tiep!");
            } else if (!mess) {
                System.out.println("Dang nhap that bai!");
            }
        } while (true);
        System.out.println("..................................");

        String dapan = "";
        int diem = 0;
        String kq = "";
        boolean[] arr = new boolean[10];
        int c = 0;
        while (true) {

            String noidung = input.readUTF();
            String dapanA = input.readUTF();
            String dapanB = input.readUTF();
            String dapanC = input.readUTF();
            String dapanD = input.readUTF();
            System.out.println("Cau hoi " + (c + 1) + ":" + noidung);
            System.out.println("A. " + dapanA);
            System.out.println("B. " + dapanB);
            System.out.println("C. " + dapanC);
            System.out.println("D. " + dapanD);
            System.out.print("Vui long chon dap an: ");
            dapan = KTDapAn();
            ouput.writeUTF(dapan);

            c++;
            if (c >= 10) {
                break;
            }
        }

        kq = input.readUTF();
        diem = input.readInt();
        int count = 1;
        for (int i = 0; i < kq.length(); i++) {
            if (kq.charAt(i) != '/') {
                System.out.print(count + " - " + kq.charAt(i));
                count++;
            } else {
                System.out.println();
            }
        }
        System.out.println("Tong diem cua ban la: " + diem);

        client.close();
    }

}
