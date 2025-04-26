
public enum TietHoc {
    TIET_1(1, "06:45", "07:30"),
    TIET_2(2, "07:30", "08:15"),
    TIET_3(3, "08:15", "09:00"),
    TIET_4(4, "09:00", "09:45"),
    TIET_5(5, "10:00", "10:45"),
    TIET_6(6, "10:45", "11:30"),
    TIET_7(7, "13:00", "13:45"),
    TIET_8(8, "13:45", "14:30"),
    TIET_9(9, "14:30", "15:15"),
    TIET_10(10, "15:30", "16:15"),
    TIET_11(11, "16:15", "17:00"),
    TIET_12(12, "17:00", "17:45"),
    TIET_13(13, "17:45", "18:30");

    private final int tiet;
    private final String gioBatDau;
    private final String gioKetThuc;

    TietHoc(int tiet, String gioBatDau, String gioKetThuc) {
        this.tiet = tiet;
        this.gioBatDau = gioBatDau;
        this.gioKetThuc = gioKetThuc;
    }

    public static TietHoc getTiet(int tiet) {
        for (TietHoc t : values()) {
            if (t.tiet == tiet) return t;
        }
        return null;
    }

    public String getGioBatDau() { return gioBatDau; }
    public String getGioKetThuc() { return gioKetThuc; }
}
