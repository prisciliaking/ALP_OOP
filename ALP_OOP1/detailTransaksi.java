package ALP_OOP1;

import java.io.*;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    DateTimeFormatter date = DateTimeFormatter.ofPattern("dd-MM-yy HH:mm:ss", indonesia);

    public void addTransaksi(Transaksi transaksi, Barang barang) {
        transaksiMap.put(transaksi, barang);
    }

    public void listTransaksi() {
        for (Map.Entry<Transaksi, Barang> entry : transaksiMap.entrySet()) {
            Transaksi transaksi = entry.getKey();
            Barang barang = entry.getValue();
            String dateFormat = transaksi.getDate().formatted(date);

            System.out.println(String.format("%d | Tanggal: %s | Barang: %s | Jumlah: %d | Harga: %s",
                    transaksi.getIdTransaksi(),
                    dateFormat,
                    barang.getNamabarang(),
                    transaksi.getJumlahBarang(),
                    rp.format(transaksi.getTotalHarga())));
        }
    }

    public void simpanFile(String listTransaksi) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(listTransaksi, true))) {
            // Baca id transaksi terakhir dari file
            int lastIdTransaksi = bacaIdTransaksiTerakhir(listTransaksi);

            for (Map.Entry<Transaksi, Barang> entry : transaksiMap.entrySet()) {
                Transaksi transaksi = entry.getKey();
                Barang barang = entry.getValue();

                // Update id transaksi jika sudah ada di file
                if (transaksi.getIdTransaksi() <= lastIdTransaksi) {
                    transaksi.setIdTransaksi(lastIdTransaksi + 1);
                }

                String dateFormat = transaksi.getDate().formatted(date);
                String line = String.format("%d | %s | %s | %d | %s ",
                        transaksi.getIdTransaksi(),
                        dateFormat,
                        barang.getNamabarang(),
                        transaksi.getJumlahBarang(),
                        rp.format(transaksi.getTotalHarga()));
                writer.write(line);
                writer.newLine();

                // Perbarui lastIdTransaksi setelah penulisan
                lastIdTransaksi = transaksi.getIdTransaksi();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int bacaIdTransaksiTerakhir(String listTransaksi) {
        int lastIdTransaksi = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(listTransaksi))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue; // Skip empty lines
                }
                String[] parts = line.split(" \\| ");
                if (parts.length > 0) {
                    int idTransaksi = Integer.parseInt(parts[0].trim());
                    if (idTransaksi > lastIdTransaksi) {
                        lastIdTransaksi = idTransaksi;
                    }
                }
            }
        } catch (IOException | NumberFormatException e) {
            Logger.getLogger(detailTransaksi.class.getName()).log(Level.WARNING, "Error reading last transaction ID", e);
        }
        return lastIdTransaksi;
    }

    public void bacaFile(String listTransaksi) {
        Penyimpanan penyimpanan = new Penyimpanan();
        try (BufferedReader reader = new BufferedReader(new FileReader(listTransaksi))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);

                if (line.trim().isEmpty()) {
                    continue;
                }
                String[] parts = line.split(" \\| ");
                if (parts.length < 5) {
                    Logger.getLogger(detailTransaksi.class.getName()).log(Level.WARNING, "Invalid line format: " + line);
                    continue;
                }
                try {
                    int idTransaksi = Integer.parseInt(parts[0].trim());
                    LocalDateTime dateFormat = LocalDateTime.parse(parts[1].trim(), date);
                    String namaBarang = parts[2].trim();
                    int jumlahBarang = Integer.parseInt(parts[3].trim());
                    double hargaBarang = Double.parseDouble(parts[4].trim().replace("Rp", "").replace(",", "").replace(" ", ""));

                    Barang barang = penyimpanan.getBarangByName(namaBarang);
                    Transaksi transaksi = new Transaksi(jumlahBarang, (int) hargaBarang);
                    transaksi.setIdTransaksi(idTransaksi);
                    transaksi.setDate(dateFormat);
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
