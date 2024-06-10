package ALP_OOP1;

public class DetailTransaksiEntry {

    private Transaksi transaksi;
    private Barang barang;

    public DetailTransaksiEntry(Transaksi transaksi, Barang barang) {
        this.transaksi = transaksi;
        this.barang = barang;
    }

    public Transaksi getTransaksi() {
        return transaksi;
    }

    public Barang getBarang() {
        return barang;
    }
}
