package ALP_OOP;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.Locale;

public class Barang {

    String namabarang;
    int stokbarang;
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

    public String getNamabarang() {
        return namabarang;
    }

    public int getStokbarang() {
        return stokbarang;
    }

    public double getHargabarang() {
        return hargabarang;
    }

    private static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/oopstore";
        String username = "root";
        String password = "";
        return DriverManager.getConnection(url, username, password);
    }

    // inputan tambah barang user disini
    public void simpanBarang() throws SQLException {
        String sql = "INSERT INTO barang (nama_barang, stok_barang, harga_barang) VALUES (?, ?, ?)";
        try (Connection con = getConnection(); PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, getNamabarang());
            pst.setInt(2, getStokbarang());
            pst.setDouble(3, getHargabarang());
            pst.executeUpdate();
            System.out.println("Barang berhasil ditambahkan ke database.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void listBarang() throws SQLException {
        int i = 1;
        String sql = "SELECT nama_barang, stok_barang, harga_barang FROM barang";
        try (Connection con = getConnection(); PreparedStatement pst = con.prepareStatement(sql); ResultSet rs = pst.executeQuery()) {
            System.out.println("==========================");
            while (rs.next()) {
                String namaBarang = rs.getString("nama_barang");
                int stokBarang = rs.getInt("stok_barang");
                int hargaBarang = rs.getInt("harga_barang");
                System.out.printf(i + ". Nama: %s, Stok: %d, Harga: %s\n", namaBarang, stokBarang, rp.format(hargaBarang));
                i++;
            }
            System.out.println("==========================");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error !");
        }
    }
}
