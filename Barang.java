package ALP_OOP1;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;

public class Barang {

    String namabarang;
    int stokbarang, id;
    double hargabarang;
    
    // =====================================================================
    // ini biar nanti pas di output bkalan jadi rupiah indonesia ada titik komanya
    private static Locale indonesia = new Locale("id", "ID");
    private static NumberFormat rp = NumberFormat.getCurrencyInstance(indonesia);
    // =====================================================================
    public Barang(String namabarang, int stokbarang, double hargabarang) {
        this.namabarang = namabarang;
        this.stokbarang = stokbarang;
        this.hargabarang = hargabarang;
    }

    public int getId() {
        return id;
    }
    
    
    public void setId(int id){
        this.id= id;
    }

    public String getNamabarang() {
        return namabarang;
    }

    public int getStokbarang() {
        return stokbarang;
    }

    public double getHargabarang() {
        return hargabarang;
    }
}
