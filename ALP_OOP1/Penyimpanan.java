package ALP_OOP1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Penyimpanan {

    private static Locale indonesia = new Locale("id", "ID");
    private static NumberFormat rp = NumberFormat.getCurrencyInstance(indonesia);

    private static int idBarang = 1;
    private HashMap<Integer, Barang> barangMap = new HashMap<>();

    public Barang getBarangById(int id) {
        return barangMap.get(id);
    }

    public Barang getBarangByName(String name) {
        for (Barang barang : barangMap.values()) {
            if (barang.getNamabarang().equals(name)) {
                return barang;
            }
        }
        return null;
    }

    public void simpanBarang(Barang barang) {
        barang.setId(idBarang++);
        barangMap.put(barang.getId(), barang);
    }

    public void listBarang() {
        // ngecek value dalam hashmap barang sesuai dengan objek barang
        // lalu diget kemudian di simpan dalam string format
        for (Barang barang : barangMap.values()) {
            System.out.println(String.format("%d. Nama: %s | Stok: %d | Harga %s",
                    barang.getId(),
                    barang.getNamabarang(),
                    barang.getStokbarang(),
                    rp.format(barang.getHargabarang())
            ));
        }
    }

    public void simpanKeFile(String listBarang) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(listBarang))) { // Open in overwrite mode
            for (Barang barang : barangMap.values()) {
                String line = String.format("%d | %s | %d | Rp%.2f",
                        barang.getId(),
                        barang.getNamabarang(),
                        barang.getStokbarang(),
                        barang.getHargabarang());
                // .write itu nyimpen string line ini dalam file listBarang.txt
                writer.write(line);
                // .newLine ini biar bisa input barang baru yang disimpan dalam barangMap
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void bacaFile(String listBarang) {
        // Menggunakan try-with-resources untuk memastikan BufferedReader ditutup setelah digunakan
        try (BufferedReader reader = new BufferedReader(new FileReader(listBarang))) {
            String line;
            // Membaca setiap baris dalam file sampai mencapai akhir file (null)
            while ((line = reader.readLine()) != null) {
                // Lewati baris kosong
                if (line.trim().isEmpty()) {
                    continue; // Lewati baris kosong
                }
                // Memisahkan ID dan detail lainnya dari file berdasarkan delimiter " | "
                String[] parts = line.split(" \\| ");
                if (parts.length < 4) { // Memastikan ada setidaknya 4 bagian
                    // Log peringatan jika format baris tidak valid
                    Logger.getLogger(Penyimpanan.class.getName()).log(Level.WARNING, "Invalid line format: " + line);
                    continue;
                }
                try {
                    // Parsing nilai ID, nama barang, stok barang, dan harga barang dari string ke tipe data yang sesuai
                    int id = Integer.parseInt(parts[0].trim());
                    String namabarang = parts[1].trim();
                    int stokbarang = Integer.parseInt(parts[2].trim());
                    double hargabarang = Double.parseDouble(parts[3].replace("Rp", "").replace(",", "").trim());

                    // Membuat objek Barang dengan informasi yang telah di-parse
                    Barang barang = new Barang(namabarang, stokbarang, hargabarang);
                    barang.setId(id);
                    // Menambahkan objek Barang ke dalam peta (map) dengan ID sebagai kunci
                    barangMap.put(id, barang);

                    // Memperbarui idBarang jika ID yang di-parse lebih besar atau sama dengan idBarang saat ini
                    if (id >= idBarang) {
                        idBarang = id + 1;
                    }
                    // Mencetak baris yang telah dibaca dan di-parse
                    System.out.println(line);
                } catch (NumberFormatException e) {
                    // Menangani kasus di mana ID atau angka lain tidak valid
                    Logger.getLogger(Penyimpanan.class.getName()).log(Level.WARNING, "Invalid number format in file: " + line, e);
                }
            }
        } catch (IOException ex) {
            // Log kesalahan jika ada masalah dalam membaca file
            Logger.getLogger(Penyimpanan.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateBarang(Barang barang) {
        if (barangMap.containsKey(barang.getId())) {
            barangMap.put(barang.getId(), barang);
            simpanKeFile("listBarang.txt");
        }
    }
}
