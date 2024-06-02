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

    private Barang barang;
    private static Locale indonesia = new Locale("id", "ID");
    private static NumberFormat rp = NumberFormat.getCurrencyInstance(indonesia);

    private static int idBarang = 1;
    private HashMap<Integer, Barang> barangMap = new HashMap<>();

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
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(listBarang, true))) { // Open in append mode
            for (Barang barang : barangMap.values()) {
                String line = String.format("\n%d | %s | %d | Rp%.2f",
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
                System.out.println(line);
            }
        } catch (IOException ex) {
            Logger.getLogger(Penyimpanan.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
