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
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void bacaFile(String listBarang) {
        try (BufferedReader reader = new BufferedReader(new FileReader(listBarang))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue; // Skip empty lines
                }
                // Parsing the ID from the file
                String[] parts = line.split(" \\| ");
                if (parts.length < 4) { // Ensure there are at least 4 parts
                    Logger.getLogger(Penyimpanan.class.getName()).log(Level.WARNING, "Invalid line format: " + line);
                    continue;
                }
                try {
                    int id = Integer.parseInt(parts[0].trim());
                    String namabarang = parts[1].trim();
                    int stokbarang = Integer.parseInt(parts[2].trim());
                    double hargabarang = Double.parseDouble(parts[3].replace("Rp", "").replace(",", "").trim());

                    Barang barang = new Barang(namabarang, stokbarang, hargabarang);
                    barang.setId(id);
                    barangMap.put(id, barang);

                    if (id >= idBarang) {
                        idBarang = id + 1;
                    }
                    System.out.println(line);
                } catch (NumberFormatException e) {
                    // Handle the case where ID or other numbers are not valid
                    Logger.getLogger(Penyimpanan.class.getName()).log(Level.WARNING, "Invalid number format in file: " + line, e);
                }
            }
        } catch (IOException ex) {
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
