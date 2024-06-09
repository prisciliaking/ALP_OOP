package ALP_OOP1;

import java.text.NumberFormat;
import java.util.Locale;

public class Barang {

    String namabarang;
    int stokbarang, id;
    double hargabarang; 

    // =====================================================================
    public Barang(String namabarang, int stokbarang, double hargabarang) {
        this.namabarang = namabarang;
        this.stokbarang = stokbarang;
        this.hargabarang = hargabarang;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNamabarang() {
        return namabarang;
    }

    public int getStokbarang() {
        return stokbarang;
    }

    public void setStokbarang(int stokbarang) {
        this.stokbarang = stokbarang;
    }

    public double getHargabarang() {
        return hargabarang;
    }

    public void setHargabarang(double hargabarang) {
        this.hargabarang = hargabarang;
    }
    
    
}
