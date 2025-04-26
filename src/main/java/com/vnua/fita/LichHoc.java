
public class LichHoc {
    private String maMon;
    private String tenMon;
    private int tietBatDau;
    private int soTiet;
    private String phong;
    private String giangVien;

    public LichHoc(String maMon, String tenMon, int tietBatDau, int soTiet, String phong, String giangVien) {
        this.maMon = maMon;
        this.tenMon = tenMon;
        this.tietBatDau = tietBatDau;
        this.soTiet = soTiet;
        this.phong = phong;
        this.giangVien = giangVien;
    }

    @Override
    public String toString() {
        TietHoc tietHoc = TietHoc.getTiet(tietBatDau);
        String gio = tietHoc != null ? tietHoc.getGioBatDau() : "Unknown";
        return String.format("%s - %s | Tiết %d (%s) | %d tiết | Phòng: %s | GV: %s",
                maMon, tenMon, tietBatDau, gio, soTiet, phong, giangVien);
    }
}
