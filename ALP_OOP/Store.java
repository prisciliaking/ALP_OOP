package ALP_OOP;

public class Store {
    //nyimpen kalau store open or close dicek pake boolean
    private boolean isOpen;

    public Store() {
        this.isOpen = false;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void openStore() {
        if (isOpen) {
            System.out.println("Toko sudah dibuka.");
        } else {
            isOpen = true;
            System.out.println("Toko telah dibuka.");
        }
    }

    public void closeStore() {
        if (!isOpen) {
            System.out.println("Toko sudah tutup.");
        } else {
            isOpen = false;
            System.out.println("Toko telah ditutup.");
        }
    }
}
