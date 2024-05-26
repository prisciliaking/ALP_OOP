/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ALP_OOP1;

import java.util.Scanner;

/**
 *
 * @author ASUS
 */
public class Menu {

    Scanner scan = new Scanner(System.in);
    private Store store;
    private Barang barang;
    private Penyimpanan penyimpanan;

    public void Menu() {
        penyimpanan = new Penyimpanan();
        store = new Store();
        do {
            System.out.println("");
            System.out.println("==========================");
            if (store.isOpen()) {
                System.out.println("1. Tutup Toko ");
            } else {
                System.out.println("1. Buka Toko ");
            }
            System.out.println("2. Detail Stok");
            System.out.println("3. Daftar Transaksi");
            System.out.println("4. Total Pendapatan Harian");
            System.out.println("0. Exit");
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
                    if (!store.isOpen()) {
                        System.out.println("Buka toko dulu !");
                    } else {
                        detailStok();
                    }
                    break;

                case 3:
                    daftarTransaksi();
                    break;

                case 4:
                    totalPendapatan();
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

    // daftar stok barang
    public void detailStok() {
        System.out.println("");
        System.out.println("==========================");
        System.out.println("=====  Detail  Stok  =====");
        System.out.println("===   1. Daftar Stok   ===");
        System.out.println("===   2. Edit Stok     ===");
        System.out.println("===   3. Edit Harga    ===");
        System.out.println("===   4. Tambah Stok   ===");
        System.out.println("===   5. Tambah barang ===");
        System.out.println("===== 0. Kembali     =====");
        System.out.println("==========================");
        System.out.print("Pilih : ");
        int pick = scan.nextInt();

        switch (pick) {
            case 1:
                daftarBarang();
                break;

            case 2:
                editStok();
                break;

            case 3:
                editHarga();
                break;

            case 4:
                tambahStok();
                break;

            case 5:
                tambahBarang();
                break;

            case 0:
                Menu();
                break;

            default:
                System.out.println("Masukkan angka yang benar !");
        }

    }

    public void daftarBarang() {
        System.out.println("==========================");
        System.out.println("\n===== Daftar  Barang =====");
        penyimpanan.listBarang();
        System.out.println("==========================");
    }

    public void editStok() {

    }

    public void editHarga() {

    }

    public void tambahStok() {

    }

    public void tambahBarang() {
        System.out.println("\n==========================");
        System.out.println("===== Tambah  Barang =====");
        System.out.print("==  Nama barang: ");
        String namabarang = scan.next() + scan.nextLine();
        System.out.print("\n==  Stok barang: ");
        int stokbarang = scan.nextInt();
        System.out.print("\n==  Harga barang: ");
        double hargabarang = scan.nextInt();
        System.out.println("==========================");
        Barang barang = new Barang(namabarang, stokbarang, hargabarang);
        penyimpanan.simpanBarang(barang);
        System.out.println("Barang telah tersimpan dengan id: " + barang.getId());
    }

    public void daftarTransaksi() {

    }

    public void totalPendapatan() {

    }
}
