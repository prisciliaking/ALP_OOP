package ALP_OOP1;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaksi {
    static int idTransaksi = 1;
    int jumlahBarang, totalharga;
    private LocalDateTime date;
    private int currentIdTransaksi;

    public Transaksi(int jumlahBarang, int totalharga) {
        this.currentIdTransaksi = idTransaksi++;
        this.date = LocalDateTime.now();
        this.jumlahBarang = jumlahBarang;
        this.totalharga = totalharga;
    }
    
    public int getIdTransaksi() {
        return currentIdTransaksi;
    }

    public void setIdTransaksi(int idTransaksi) {
        this.currentIdTransaksi = idTransaksi;
    }

    public String getDate() {
        // nyimpen dalam format pattern, pake localdatetime kemudian di format biar ada timenya
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return date.format(dateFormat);
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public int getJumlahBarang() {
        return jumlahBarang;
    }
    
    public int getTotalHarga(){
        return totalharga;
    }
}
