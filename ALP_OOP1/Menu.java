package ALP_OOP1;

import java.util.Scanner;

public class Menu {

    Scanner scan = new Scanner(System.in);
    private Store store;
    private Barang barang;
    private Penyimpanan penyimpanan;
    private detailTransaksi detailTransaksi;
    private Transaksi transaksi;

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
            System.out.println("2. Masukkan Transaksi");
            System.out.println("3. Detail Stok");
            System.out.println("4. Daftar Transaksi");
            System.out.println("5. Total Pendapatan Harian");
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
                    masukkanTransaksi();
                    break;

                case 3:
                    if (!store.isOpen()) {
                        System.out.println("Buka toko dulu !");
                    } else {
                        detailStok();
                    }
                    break;

                case 4:
                    daftarTransaksi();
                    break;

                case 5:
                    totalPendapatan();
                    break;
                case 0:
                    detailTransaksi.simpanFile("listTransaksi.txt");
                    penyimpanan.simpanKeFile("listBarang.txt");
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
        System.out.println("\n==========================");
        System.out.println("===== Daftar  Barang =====");
        penyimpanan.bacaFile("listBarang.txt");
        System.out.println("==========================");
    }

    public void editStok() {
        System.out.println("\n==========================");
        System.out.println("===== Daftar  Barang =====");
        penyimpanan.bacaFile("listBarang.txt");
        System.out.println("==========================");
        //nanti stok baklan ke sout : terus di milih mau edit stok yang mana
        // misalkan 
        // 1. baju tidur | stok: 7 | harga : 70.000
        // milih lah dia no 1 trus stok di kasir asliny 7 tpi ternyata digudang
        // cuma 5, nah nanti jadiny gni
        // ========
        // ID : (inputan user sesuai id barang yang mau di ubah)
        // Nama barang: 
        // Stok baru : (inputan user stok digudang ada berapa banyak)

        // kalau udah ke ubah di hashmapny, ksh statement "stok telah terubah" biar
        // tau kalau stokny udah ke ubah
    }

    public void editHarga() {
        System.out.println("\n==========================");
        System.out.println("===== Daftar  Barang =====");
        penyimpanan.bacaFile("listBarang.txt");
        System.out.println("==========================");
        // ini sama kek editStok, cuma ini harga aja
    }

    public void tambahStok() {
        System.out.println("\n==========================");
        System.out.println("===== Daftar  Barang =====");
        penyimpanan.bacaFile("listBarang.txt");
        System.out.println("==========================");
        // kalau ini nanti sama aja awalanny tpi outputny nnti gini
        // =====
        // Nama barang: (nama barangnya apa)
        // Stok terkini: (jumlah stok barangnya)
        // Stok tambahan:

        // Stok update: (jumlah antara terkini sama tambahan, yang kesimpen di hashmap)
        // ksih statement "stok telah ditambahkan"
    }

    public void tambahBarang() {
        // Load existing items from the file to ensure new item is appended correctly
        penyimpanan.bacaFile("listBarang.txt");

        System.out.println("\n==========================");
        System.out.println("===== Tambah  Barang =====");
        System.out.print("==  Nama barang: ");
        String namabarang = scan.next() + scan.nextLine();
        System.out.print("\n==  Stok barang: ");
        int stokbarang = scan.nextInt();
        System.out.print("\n==  Harga barang: ");
        double hargabarang = scan.nextDouble();
        System.out.println("==========================");

        Barang barang = new Barang(namabarang, stokbarang, hargabarang);
        penyimpanan.simpanBarang(barang);
        System.out.println("Barang telah tersimpan dengan id: " + barang.getId());

        // Save the new item to the file
        penyimpanan.simpanKeFile("listBarang.txt");
    }

    public void masukkanTransaksi() {
        detailTransaksi = new detailTransaksi();
        System.out.println("\n==========================");
        System.out.println("===== Daftar Barang =====");
        penyimpanan.bacaFile("listBarang.txt");
        penyimpanan.listBarang();
        System.out.println("==========================");
        System.out.print("\nPilih barang: ");
        int barangId = scan.nextInt();
        Barang barang = penyimpanan.getBarangById(barangId);
        if (barang == null) {
            System.out.println("Barang dengan ID tersebut tidak ditemukan.");
            return;
        }
        System.out.println("Nama barang: " + barang.getNamabarang());
        System.out.println("Jumlah stok yang tersedia: " + barang.getStokbarang());
        System.out.print("Jumlah barang yang ingin dibeli: ");
        int jumlahBarang = scan.nextInt();

        // Error handling kalau inputan melebihi stok
        if (jumlahBarang > barang.getStokbarang()) {
            System.out.println("Jumlah barang yang diminta melebihi stok yang tersedia.");
            return;
        }
        
        int idTransaksi = transaksi.getIdTransaksi();
        // Update stok barang
        barang.setStokbarang(barang.getStokbarang() - jumlahBarang);
        penyimpanan.updateBarang(barang); // Save updated stock to file

        Transaksi transaksi = new Transaksi(idTransaksi, jumlahBarang);
        detailTransaksi.addTransaksi(transaksi, barang);
        detailTransaksi.simpanFile("listTransaksi.txt");
        System.out.println("Transaksi berhasil disimpan.");
    }


    public void daftarTransaksi() {
        // Membuat objek detailTransaksi
        detailTransaksi transaksiDetail = new detailTransaksi();

        // Memanggil metode bacaFile() dari objek detailTransaksi
        transaksiDetail.bacaFile("listTransaksi.txt", penyimpanan);

        System.out.println("\n==========================");
        System.out.println("===== Daftar Transaksi =====");
        // Memanggil metode listTransaksi() untuk menampilkan daftar transaksi
        transaksiDetail.listTransaksi();
        System.out.println("==========================");
    }

    public void totalPendapatan() {

    }
}
