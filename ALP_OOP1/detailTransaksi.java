package ALP_OOP1;

import java.io.*;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class detailTransaksi {

    private Map<Transaksi, Barang> transaksiMap = new HashMap<>();
    private static Locale indonesia = new Locale("id", "ID");
    private static NumberFormat rp = NumberFormat.getCurrencyInstance(indonesia);

    public void addTransaksi(Transaksi transaksi, Barang barang) {
        transaksiMap.put(transaksi, barang);
    }

    public void listTransaksi() {
        for (Map.Entry<Transaksi, Barang> entry : transaksiMap.entrySet()) {
            Transaksi transaksi = entry.getKey();
            Barang barang = entry.getValue();
            System.out.println(String.format("Transaksi ID: %d | Tanggal: %s | Barang: %s | Jumlah: %d | Harga: Rp%.2f",
                    transaksi.getIdTransaksi(),
                    transaksi.getDate(),
                    barang.getNamabarang(),
                    transaksi.getJumlahBarang(),
                    barang.getHargabarang()
            ));
        }
    }

    public void simpanFile(String listTransaksi) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(listTransaksi, true))) {
            for (Map.Entry<Transaksi, Barang> entry : transaksiMap.entrySet()) {
                Transaksi transaksi = entry.getKey();
                Barang barang = entry.getValue();
                String line = String.format("%d | %s | %s | %d | Rp%.2f",
                        transaksi.getIdTransaksi(),
                        transaksi.getDate(),
                        barang.getNamabarang(),
                        transaksi.getJumlahBarang(),
                        barang.getHargabarang());
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void bacaFile(String listTransaksi, Penyimpanan penyimpanan) {
        try (BufferedReader reader = new BufferedReader(new FileReader(listTransaksi))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue; // Skip empty lines
                }
                String[] parts = line.split(" \\| ");
                if (parts.length < 5) { // Ensure there are at least 5 parts
                    Logger.getLogger(detailTransaksi.class.getName()).log(Level.WARNING, "Invalid line format: " + line);
                    continue;
                }
                try {
                    int idTransaksi = Integer.parseInt(parts[0].trim());
                    LocalDate date = LocalDate.parse(parts[1].trim());
                    String namaBarang = parts[2].trim();
                    int jumlahBarang = Integer.parseInt(parts[3].trim());
                    double hargaBarang = Double.parseDouble(parts[4].replace("Rp", "").replace(",", "").trim());

                    // Find barang by name (assuming names are unique)
                    Barang barang = penyimpanan.getBarangByName(namaBarang);
                    if (barang == null) {
                        Logger.getLogger(detailTransaksi.class.getName()).log(Level.WARNING, "Barang not found: " + namaBarang);
                        continue;
                    }

                    Transaksi transaksi = new Transaksi(idTransaksi, jumlahBarang);
                    transaksi.setIdTransaksi(idTransaksi);
                    transaksi.setDate(date);
                    transaksiMap.put(transaksi, barang);

                } catch (NumberFormatException | DateTimeParseException e) {
                    Logger.getLogger(detailTransaksi.class.getName()).log(Level.WARNING, "Invalid number format in file: " + line, e);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(detailTransaksi.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
