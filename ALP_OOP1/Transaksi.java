package ALP_OOP1;

import java.time.LocalDate;

public class Transaksi {
    static int idTransaksi = 1;
    int jumlahBarang;
    private LocalDate date;
    private int currentIdTransaksi;

    public Transaksi(int id, int jumlahBarang) {
        this.currentIdTransaksi = idTransaksi++;
        this.date = LocalDate.now();
        this.jumlahBarang = jumlahBarang;
    }

    public int getIdTransaksi() {
        return currentIdTransaksi;
    }

    public void setIdTransaksi(int idTransaksi) {
        this.currentIdTransaksi = idTransaksi;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getJumlahBarang() {
        return jumlahBarang;
    }
}
