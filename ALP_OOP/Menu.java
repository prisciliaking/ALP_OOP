package ALP_OOP;

import java.sql.SQLException;
import java.util.Scanner;

public class Menu {

    Scanner scan = new Scanner(System.in);
    private Store store;

    public void Menu() throws SQLException {
        store = new Store();
        do {
            System.out.println("==========================");
            System.out.println("== = = Welcome  !!! = = ==");
            System.out.println("==========================");
            if (store.isOpen()) {
                System.out.println("1. Tutup Toko ");
            } else {
                System.out.println("1. Buka Toko ");
            }
            System.out.println("2. Daftar Stok");
            System.out.println("3. Daftar Transaksi");
            System.out.println("4. Total Pendapatan Harian");
            System.out.println("o. Exit");
            System.out.println("==========================");
            System.out.print("Choose : ");
            int pick = scan.nextInt();

            switch (pick) {
                case 1:
                    if (store.isOpen()) {
                        store.closeStore();
                    } else {
                        store.openStore();
                    }
                    break;

                case 2:
                    break;

                case 3:
                    break;

                case 4:
                    break;
                case 0:
                    System.out.println("Thank you !");
                    System.exit(0);
                    break;

                default:
                    System.out.println("Masukkan angka yang benar !");
            }
        } while (true);
    }
}
