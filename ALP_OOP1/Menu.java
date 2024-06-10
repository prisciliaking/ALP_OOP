package ALP_OOP1;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

public class Menu {

    private static Locale indonesia = new Locale("id", "ID");
    private static NumberFormat rp = NumberFormat.getCurrencyInstance(indonesia);

    Scanner scan = new Scanner(System.in);
    private Store store;
    private Barang barang;
    private Penyimpanan penyimpanan;
    private detailTransaksi detailTransaksi;
    private Transaksi transaksi;

    public void Menu() {
        System.out.println("==========================");
        System.out.println("=====   OOPSIETORE   =====");
        penyimpanan = new Penyimpanan();
        store = new Store();
        detailTransaksi = new detailTransaksi();
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

            try {
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
                            masukkanTransaksi();
                        }
                        break;

                    case 3:
                        if (!store.isOpen()) {
                            System.out.println("Buka toko dulu !");
                        } else {
                            detailStok();
                        }
                        break;

                    case 4:
                        if (!store.isOpen()) {
                            System.out.println("Buka toko dulu !");
                        } else {
                            daftarTransaksi();
                        }
                        break;

                    case 5:
                        if (!store.isOpen()) {
                            System.out.println("Buka toko dulu !");
                        } else {
                            totalPendapatan();
                        }
                        break;
                    case 0:

                        System.out.println("Thank you !");
                        System.exit(0);
                        break;

                    default:
                        System.out.println("Masukkan angka yang benar !");
                }
            } catch (InputMismatchException e) {
                System.out.println("Masukkan angka yang benar !");
                scan.nextLine(); // clear the invalid input
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

        try {
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
        } catch (InputMismatchException e) {
            System.out.println("Masukkan angka yang benar !");
            scan.nextLine(); // clear the invalid input
        }

    }

    public void daftarBarang() {
        System.out.println("\n==========================");
        System.out.println("===== Daftar  Barang =====");
        penyimpanan.bacaFile("listBarang.txt");
        System.out.println("==========================");
    }

    public void editStok() {
        try {
            System.out.println("\n==========================");
            System.out.println("===== Daftar  Barang =====");
            penyimpanan.bacaFile("listBarang.txt");
            System.out.println("==========================");

            System.out.print("Choose: ");
            int pick = scan.nextInt();

            Barang barangEdit = penyimpanan.getBarangById(pick);
            if (barangEdit == null) {
                System.out.println("Barang tidak ditemukan !");
                return;
            }

            System.out.println("==========================");
            System.out.println("\n== Nama Barang: " + barangEdit.getNamabarang());
            System.out.println("== Stok saat ini: " + barangEdit.getStokbarang());
            System.out.println("==========================");

            System.out.print("Masukkan stok terbaru: ");
            int newStock = scan.nextInt();

            System.out.println("% % processing . . . % %");
            barangEdit.setStokbarang(newStock);
            penyimpanan.updateBarang(barangEdit);
            System.out.println("Stok barang telah berhasil diedit: " + barangEdit.getStokbarang());
            System.out.println("==========================\n");

            String ans;
            do {
                System.out.println("Apakah masih ingin melihat pendapatan tanggal lain? (y/n)");
                ans = scan.next();
                if (ans.equalsIgnoreCase("y")) {
                    editStok();
                    return;
                } else if (ans.equalsIgnoreCase("n")) {
                    detailStok();
                    return;
                }
            } while (!(ans.equalsIgnoreCase("y") || ans.equalsIgnoreCase("n")));

        } catch (InputMismatchException e) {
            System.out.println("Masukkan angka yang benar !");
            scan.nextLine(); // clear the invalid input

        }
    }

    public void editHarga() {
        try {
            System.out.println("\n==========================");
            System.out.println("===== Daftar  Barang =====");
            penyimpanan.bacaFile("listBarang.txt");
            System.out.println("==========================");

            System.out.print("Choose: ");
            int pick = scan.nextInt();

            Barang barangEdit = penyimpanan.getBarangById(pick);
            if (barangEdit == null) {
                System.out.println("Barang tidak ditemukan !");
                return;
            }

            System.out.println("==========================");
            System.out.println("\n== Nama Barang: " + barangEdit.getNamabarang());
            System.out.println("== Harga saat ini: " + barangEdit.getHargabarang());
            System.out.println("==========================");

            System.out.print("Masukkan harga terbaru: ");
            int newHarga = scan.nextInt();

            System.out.println("% % processing . . . % %");
            barangEdit.setHargabarang(newHarga);
            penyimpanan.updateBarang(barangEdit);
            System.out.println("Harga barang telah berhasil diedit: " + barangEdit.getHargabarang());
            System.out.println("==========================\n");
            String ans;
            do {
                System.out.println("Apakah masih ingin melihat pendapatan tanggal lain? (y/n)");
                ans = scan.next();
                if (ans.equalsIgnoreCase("y")) {
                    editHarga(); // Rekursi untuk memanggil kembali method editHarga()
                    return; // Keluar dari method setelah rekursi selesai
                } else if (ans.equalsIgnoreCase("n")) {
                    detailStok(); // Panggil method detailStok()
                    return; // Keluar dari method setelah panggilan selesai
                }
            } while (!(ans.equalsIgnoreCase("y") || ans.equalsIgnoreCase("n")));

        } catch (InputMismatchException e) {
            System.out.println("Masukkan angka yang benar !");
            scan.nextLine(); // clear the invalid input

        }
    }

    public void tambahStok() {
        try {
            System.out.println("\n==========================");
            System.out.println("===== Daftar  Barang =====");
            penyimpanan.bacaFile("listBarang.txt");
            System.out.println("==========================");

            System.out.print("Choose: ");
            int pick = scan.nextInt();

            Barang barangEdit = penyimpanan.getBarangById(pick);
            if (barangEdit == null) {
                System.out.println("Barang tidak ditemukan !");
                return;
            }

            System.out.println("==========================");
            System.out.println("\n== Nama Barang: " + barangEdit.getNamabarang());
            System.out.println("== Stok saat ini: " + barangEdit.getStokbarang());
            System.out.println("==========================");

            System.out.print("Masukkan stok tambahan: ");
            int tambahStock = scan.nextInt();

            // Update stok barang
            System.out.println("% % processing . . . % %");
            barangEdit.setStokbarang(barangEdit.getStokbarang() + tambahStock);
            penyimpanan.updateBarang(barangEdit);
            System.out.println("Stok barang telah berhasil diedit: " + barangEdit.getStokbarang());
            System.out.println("==========================\n");

            String ans;
            do {
                System.out.println("Apakah masih ingin melihat pendapatan tanggal lain? (y/n)");
                ans = scan.next();
                if (ans.equalsIgnoreCase("y")) {
                    tambahStok(); // Rekursi untuk memanggil kembali method tambahStok()
                    return; // Keluar dari method setelah rekursi selesai
                } else if (ans.equalsIgnoreCase("n")) {
                    detailStok(); // Panggil method detailStok()
                    return; // Keluar dari method setelah panggilan selesai
                }
            } while (!(ans.equalsIgnoreCase("y") || ans.equalsIgnoreCase("n")));

        } catch (InputMismatchException e) {
            System.out.println("Masukkan angka yang benar !");
            scan.nextLine(); // clear the invalid input
        }

    }

    public void tambahBarang() {
        try {
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

            String ans;
            do {
                System.out.println("Apakah masih ingin melihat pendapatan tanggal lain? (y/n)");
                ans = scan.next();
                if (ans.equalsIgnoreCase("y")) {
                    tambahBarang(); // Rekursi untuk memanggil kembali method tambahBarang()
                    return; // Keluar dari method setelah rekursi selesai
                } else if (ans.equalsIgnoreCase("n")) {
                    detailStok(); // Panggil method detailStok()
                    return; // Keluar dari method setelah panggilan selesai
                }
            } while (!(ans.equalsIgnoreCase("y") || ans.equalsIgnoreCase("n")));

        } catch (InputMismatchException e) {
            System.out.println("Masukkan angka yang benar !");
            scan.nextLine(); // clear the invalid input
        }

    }

    public void masukkanTransaksi() {
        try {
            detailTransaksi = new detailTransaksi();
            System.out.println("\n==========================");
            System.out.println("===== Daftar Barang =====");
            penyimpanan.bacaFile("listBarang.txt");
            System.out.println("==========================");
            System.out.print("\nPilih barang: ");
            int barangId = scan.nextInt();
            Barang barang = penyimpanan.getBarangById(barangId);
            if (barang == null) {
                System.out.println("barang tidak ditemukan !");
                return;
            }
            System.out.println("Nama barang: " + barang.getNamabarang());
            System.out.println("Jumlah stok yang tersedia: " + barang.getStokbarang());
            System.out.print("Jumlah barang yang ingin dibeli: ");
            int jumlahBarang = scan.nextInt();

            // Error handling kalau inputan melebihi stok
            if (jumlahBarang > barang.getStokbarang()) {
                System.out.println("Stok tidak mencukupi !");
                return;
            }
            int totalharga = (int) (barang.getHargabarang() * jumlahBarang);
            // Update stok barang
            barang.setStokbarang(barang.getStokbarang() - jumlahBarang);
            penyimpanan.updateBarang(barang); // Save updated stock to file

            Transaksi transaksi = new Transaksi(jumlahBarang, totalharga);
            detailTransaksi.addTransaksi(transaksi, barang);

            System.out.println("Transaksi berhasil disimpan.");

            String ans;
            do {
                System.out.println("\nApakah masih ingin melihat pendapatan tanggal lain? (y/n)");
                ans = scan.next();
                if (ans.equalsIgnoreCase("y")) {
                    masukkanTransaksi();
                    return;
                } else if (ans.equalsIgnoreCase("n")) {
                    return;
                }
            } while (!(ans.equalsIgnoreCase("y") || ans.equalsIgnoreCase("n")));
            detailTransaksi.simpanFile("listTransaksi.txt");
        } catch (InputMismatchException e) {
            System.out.println("Masukkan angka yang benar !");
            scan.nextLine(); // clear the invalid input
        }
    }

    public void daftarTransaksi() {
        // Membuat objek detailTransaksi
        detailTransaksi = new detailTransaksi();

        System.out.println("\n==========================");
        System.out.println("===== Daftar Transaksi =====");
        // Memanggil metode listTransaksi() untuk menampilkan daftar transaksi
        detailTransaksi.bacaFile("listTransaksi.txt");
        System.out.println("==========================");
    }

    public void totalPendapatan() {
        try {
            detailTransaksi detailPendapatan = new detailTransaksi();

            detailPendapatan.bacaFile("listTransaksi.txt");
            System.out.println("\n==========================");
            System.out.print("Enter the date (dd-mm-yyyy): ");
            String date = scan.next();

            LocalDate targetDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy"));

            double total = detailPendapatan.getTotalPriceByDate(targetDate);
            String totalHarian = rp.format(total);

            System.out.println("\nTotal transactions on " + date + ": " + totalHarian);
            System.out.println("==========================");

            String ans;
            do {
                System.out.println("Apakah masih ingin melihat pendapatan tanggal lain? (y/n)");
                ans = scan.next();
                if (ans.equalsIgnoreCase("y")) {
                    totalPendapatan();
                    return;
                } else if (ans.equalsIgnoreCase("n")) {
                    detailStok();
                    return;
                }
            } while (!(ans.equalsIgnoreCase("y") || ans.equalsIgnoreCase("n")));

        } catch (DateTimeParseException e) {
            System.out.println("Masukkan format tanggal dengan format dd-mm-yyyy: 06-06-2000.");
        }
    }
}
