package ALP_OOP1;

import java.io.*;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class detailTransaksi {

    private ArrayList<DetailTransaksiEntry> transaksiList = new ArrayList<>();
    private Map<LocalDate, Double> totalPriceByDate = new HashMap<>();
    private Locale indonesia = new Locale("id", "ID");
    private NumberFormat rp = NumberFormat.getCurrencyInstance(indonesia);
    private DateTimeFormatter date = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss", indonesia);

    public void addTransaksi(Transaksi transaksi, Barang barang) {
        transaksiList.add(new DetailTransaksiEntry(transaksi, barang));
        updateTotalPriceByDate(transaksi);
        simpanFile("listTransaksi.txt");
    }

    public void listTransaksi() {
        for (DetailTransaksiEntry entry : transaksiList) {
            Transaksi transaksi = entry.getTransaksi();
            Barang barang = entry.getBarang();
            String dateFormat = transaksi.getDate();

            System.out.println(String.format("%d | Tanggal: %s | Barang: %s | Jumlah: %d | Harga: %s",
                    transaksi.getIdTransaksi(),
                    dateFormat,
                    barang.getNamabarang(),
                    transaksi.getJumlahBarang(),
                    rp.format(transaksi.getTotalHarga())));
        }
    }

    public void simpanFile(String listTransaksi) {
    // Inisialisasi logger
    Logger logger = Logger.getLogger(getClass().getName());

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(listTransaksi, true))) {
        // Baca id transaksi terakhir dari file
        int lastIdTransaksi = bacaIdTransaksiTerakhir(listTransaksi);

        for (DetailTransaksiEntry entry : transaksiList) {
            Transaksi transaksi = entry.getTransaksi();
            Barang barang = entry.getBarang();

            // Update id transaksi jika sudah ada di file
            if (transaksi.getIdTransaksi() <= lastIdTransaksi) {
                transaksi.setIdTransaksi(lastIdTransaksi + 1);
            }

            String dateFormat = transaksi.getDate();
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

            // Log pesan bahwa data berhasil ditambahkan
           
        }
    } catch (IOException e) {
        // Tangani kesalahan dengan mencetak stack trace dan log pesan kesalahan
        logger.log(Level.SEVERE, "Terjadi kesalahan saat menyimpan file: " + listTransaksi, e);
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
                int idTransaksi = Integer.parseInt(parts[0].trim());
                LocalDateTime dateFormat = LocalDateTime.parse(parts[1].trim(), date);
                String namaBarang = parts[2].trim();
                int jumlahBarang = Integer.parseInt(parts[3].trim());
                double hargaBarang = Double.parseDouble(parts[4].trim().replace("Rp", "").replace(",", "").replace(" ", ""));

                Barang barang = penyimpanan.getBarangByName(namaBarang);
                Transaksi transaksi = new Transaksi(jumlahBarang, (int) hargaBarang);
                transaksi.setIdTransaksi(idTransaksi);
                transaksi.setDate(dateFormat);
                transaksiList.add(new DetailTransaksiEntry(transaksi, barang));

                updateTotalPriceByDate(transaksi); // Update total price by date
            }
        } catch (IOException ex) {
            Logger.getLogger(detailTransaksi.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void updateTotalPriceByDate(Transaksi transaksi) {
        LocalDateTime transactionDateTime = LocalDateTime.parse(transaksi.getDate(), date);
        LocalDate transactionDate = transactionDateTime.toLocalDate();

        double totalPrice = totalPriceByDate.getOrDefault(transactionDate, 0.0);
        // dikali 1000 soalny hanya ambil angka sebelum .000
        double totalFix = (totalPrice += transaksi.getTotalHarga() * 1000); // Tambahkan total harga transaksi ke total harga untuk tanggal tersebut
        totalPriceByDate.put(transactionDate, totalFix);

        // Debugging output with formatted total price
        //System.out.println("Updated total price for " + transactionDate + ": " + rp.format(totalPrice));
    }

    public double getTotalPriceByDate(LocalDate targetDate) {
        return totalPriceByDate.getOrDefault(targetDate, 0.0);
    }
}
    