
import java.util.HashMap;
import java.util.Map;

public class Tuan {
    private Map<Integer, Thu> danhSachThu = new HashMap<>();

    public Tuan() {
        for (int i = 2; i <= 8; i++) {  // Thứ 2 -> CN
            danhSachThu.put(i, new Thu());
        }
    }

    public Thu getThu(int thu) {
        return danhSachThu.get(thu);
    }
}
