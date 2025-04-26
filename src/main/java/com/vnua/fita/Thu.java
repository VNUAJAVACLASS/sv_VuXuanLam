
import java.util.ArrayList;
import java.util.List;

public class Thu {
    private List<LichHoc> danhSachLichHoc = new ArrayList<>();

    public void themLichHoc(LichHoc lichHoc) {
        danhSachLichHoc.add(lichHoc);
    }

    public List<LichHoc> getDanhSachLichHoc() {
        return danhSachLichHoc;
    }
}
