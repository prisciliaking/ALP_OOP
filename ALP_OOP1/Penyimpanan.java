package ALP_OOP1;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;

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

}
