package ALP_OOP1;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaksi {
    static int idTransaksi = 1;
    int jumlahBarang;
    private LocalDateTime date;
    private int currentIdTransaksi;

    public Transaksi(int jumlahBarang) {
        this.currentIdTransaksi = idTransaksi++;
        this.date = LocalDateTime.now();
        this.jumlahBarang = jumlahBarang;
    }

    public int getIdTransaksi() {
        return currentIdTransaksi;
    }

    public void setIdTransaksi(int idTransaksi) {
        this.currentIdTransaksi = idTransaksi;
    }

    public String getDate() {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yy HH:mm:ss");
        return date.format(dateFormat);
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public int getJumlahBarang() {
        return jumlahBarang;
    }
}
