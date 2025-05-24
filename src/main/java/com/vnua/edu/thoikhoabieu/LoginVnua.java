package com.vnua.edu.thoikhoabieu;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.WaitForSelectorState;

public class LoginVnua {

	public String taoFileHtml(NguoiDung nguoidung) {
	    String cacheDir = "src/main/java/resources";

	    try {
	        Files.createDirectories(Paths.get(cacheDir));
	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	    String tenFile = cacheDir + "/tkb_msv_" + nguoidung.getTaiKhoan() + ".html";
	    Path path = Paths.get(tenFile);

	    if (Files.exists(path)) {
	        try {
	            FileTime lastModified = Files.getLastModifiedTime(path);
	            long ageInHours = Duration.between(lastModified.toInstant(), Instant.now()).toHours();

	            if (ageInHours < 24) {
	                System.out.println("Đang dùng file cache cũ");

	                if (ChuongTrinhChinh.getFirstDayOfTerm() == null) {
	                    Path startDateFile = Paths.get(cacheDir, "firstDayOfTerm.txt");
	                    if (Files.exists(startDateFile)) {
	                        try {
	                            String dateStr = Files.readString(startDateFile).trim();
	                            LocalDate startDate = LocalDate.parse(dateStr);
	                            ChuongTrinhChinh.setFirstDayOfTerm(startDate);
	                            System.out.println("📌 Đã đọc ngày học kỳ từ file cache: " + startDate);
	                        } catch (Exception e) {
	                            System.err.println("📌 Lỗi đọc ngày học kỳ từ file: " + e.getMessage());
	                            // Không gán ngày mặc định, để null luôn
	                        }
	                    } else {
	                        System.out.println("📌 File ngày học kỳ không tồn tại, không gán ngày mặc định.");
	                    }
	                }

	                return Files.readString(path);
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

	    System.out.println("Đang truy cập web để lấy TKB mới.");
	    String newHtml = Login(nguoidung);

	    if (newHtml != null) {
	        try {
	            Files.writeString(path, newHtml);
	            System.out.println("Đã lưu lại file: " + tenFile);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

	    return newHtml;
	}


    // Thực hiện đăng nhập vào hệ thống bằng thông tin tài khoản người dùng
    private void dangNhapHeThong(Page page, NguoiDung nguoidung) {
        page.navigate("https://daotao.vnua.edu.vn/#/home");

        // Chờ nút đăng nhập hiện ra rồi bấm vào
        page.waitForSelector("button:has-text('Đăng nhập')");
        page.click("button:has-text('Đăng nhập')");

        // Điền thông tin username và password
        page.waitForSelector("input[formcontrolname='username']");
        page.fill("input[formcontrolname='username']", nguoidung.getTaiKhoan());
        page.fill("input[formcontrolname='password']", nguoidung.getMatKhau());

        // Bấm đăng nhập và chờ thông báo đăng nhập thành công
        page.click("button:has-text('Đăng nhập')");
        page.waitForSelector("span.text-primary.text-justify");
    }

    // Hàm chính đăng nhập và lấy thời khóa biểu
    public String Login(NguoiDung nguoidung) {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = initBrowser(playwright);
            Page page = browser.newPage();

            // Đăng nhập vào hệ thống
            dangNhapHeThong(page, nguoidung);

            // Lấy thời khóa biểu sau khi đăng nhập thành công
            return layThoiKhoaBieu(page);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Khởi tạo trình duyệt Chromium với chế độ hiển thị (không chạy ẩn)
    private Browser initBrowser(Playwright playwright) {
        return playwright.chromium().launch(
                new BrowserType.LaunchOptions().setHeadless(true)
        );
    }

    // Lấy danh sách các học kỳ có thể chọn trên trang
    private List<String> layDanhSachHocKy(Page page) {
        List<String> dsHocKy = new ArrayList<>();

        page.waitForSelector("#WEB_TKB_HK");
        page.click("#WEB_TKB_HK");

        page.waitForSelector("div[role='combobox']");
        page.click("div[role='combobox']");
        page.waitForTimeout(1000);

        // Lấy tất cả các tùy chọn học kỳ hiện có trong dropdown
        List<ElementHandle> options = page.querySelectorAll("div.ng-option");
        for (ElementHandle option : options) {
            String hocKyText = option.innerText().trim();
            dsHocKy.add(hocKyText);
        }

        // Đóng dropdown bằng phím Esc
        page.keyboard().press("Escape");
        return dsHocKy;
    }

 // Hàm tiện lợi tạo đường dẫn file trong thư mục resources
    private Path taoDuongDanFile(String tenFile) {
        Path resourceDir = Paths.get("src/main/java/resources");
        try {
            if (!Files.exists(resourceDir)) {
                Files.createDirectories(resourceDir);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resourceDir.resolve(tenFile);
    }

    // Lấy ngày bắt đầu học kỳ từ thông tin tuần đầu tiên của thời khóa biểu
    public LocalDate startDateOfTerm(Page page) {
        // Vào trang thời khóa biểu tuần
        page.locator("//a[@id='WEB_TKB_1TUAN']").click();
        page.waitForSelector("//a[@id='WEB_TKB_1TUAN']");

        // Mở dropdown chọn tuần
        Locator weekDropdown = page.locator(
            "#fullScreen > div.card-body.p-0 > div.row.text-nowrap.px-1.pb-1 > div.d-inline-block.col-lg-7.col-md-12.col-sm-12.mb-1 > ng-select > div > div > div.ng-input"
        );
        weekDropdown.click();

        // Đợi dropdown hiện lên
        page.waitForSelector(".ng-dropdown-panel-items.scroll-host");

        // Cuộn dropdown về đầu để lấy tuần đầu tiên
        page.evaluate("() => document.querySelector('.ng-dropdown-panel-items.scroll-host')?.scrollTo(0, 0)");
        page.waitForTimeout(1000);

        // Lấy phần tử đầu tiên trong dropdown
        Locator firstOption = page.locator(
            "//div[@class='ng-dropdown-panel-items scroll-host']//div[contains(@class, 'ng-option')][1]"
        );
        firstOption.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));

        // Lấy nội dung text và trích xuất ngày bắt đầu tuần
        String weekText = firstOption.textContent();
        if (weekText == null || !weekText.contains("từ ngày")) {
            throw new RuntimeException("Không tìm thấy thông tin tuần hợp lệ.");
        }

        Pattern pattern = Pattern.compile("từ ngày (\\d{2}/\\d{2}/\\d{4})");
        Matcher matcher = pattern.matcher(weekText);

        if (matcher.find()) {
            String dateString = matcher.group(1);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate startDate = LocalDate.parse(dateString, formatter);

            // Ghi ngày bắt đầu vào file firstDayOfTerm.txt trong thư mục resources
            Path filePath = taoDuongDanFile("firstDayOfTerm.txt");
            try {
                Files.writeString(filePath, startDate.toString());
                System.out.println("✅ Đã ghi ngày bắt đầu học kỳ vào file: " + filePath);
            } catch (IOException e) {
                System.err.println("❌ Lỗi khi ghi file: " + e.getMessage());
            }

            return startDate;
        } else {
            throw new RuntimeException("Không trích xuất được ngày bắt đầu từ thông tin tuần.");
        }
    }

  // Cho người dùng chọn học kỳ muốn xem thời khóa biểu
    private String chonHocKy(Page page) {
        List<String> dsHocKy = layDanhSachHocKy(page);

        System.out.println("==========////Danh sách học kỳ hiện có////==========");
        int index = 1;
        for (String hocKy : dsHocKy) {
            System.out.println(index++ + ". " + hocKy);
        }

        // Yêu cầu nhập lựa chọn cho tới khi hợp lệ
        Scanner scanner = new Scanner(System.in);
        int luaChon = -1;
        while (luaChon < 1 || luaChon > dsHocKy.size()) {
            System.out.print("Nhập số thứ tự học kỳ bạn muốn xem: ");
            luaChon = scanner.nextInt();
        }

        page.waitForTimeout(2000);

        String hocKyDaChon = dsHocKy.get(luaChon - 1);
        System.out.println("🎓 Đang lấy TKB cho: " + hocKyDaChon);
        return hocKyDaChon;
    }

    // Lấy thời khóa biểu theo học kỳ đã chọn và thiết lập ngày bắt đầu học kỳ
    private String layThoiKhoaBieu(Page page) {
        page.waitForTimeout(2000);

        // Yêu cầu người dùng chọn học kỳ
        String hocKyDaChon = chonHocKy(page);

        // Click chọn học kỳ trên trang
        page.waitForSelector("div.ng-option:has-text('" + hocKyDaChon + "')");
        page.click("div.ng-option:has-text('" + hocKyDaChon + "')");

        page.waitForTimeout(1500);
        page.waitForSelector("table.table");

        // Lấy HTML bảng thời khóa biểu
        String htmltkb = (String) page.evaluate("document.querySelector('table.table').outerHTML");

        // Lấy ngày bắt đầu học kỳ từ trang và lưu vào chương trình chính
        LocalDate startDate = startDateOfTerm(page);
        ChuongTrinhChinh.setFirstDayOfTerm(startDate);

        System.out.println("FIRSTDAYOFTERM: " + ChuongTrinhChinh.getFirstDayOfTerm());

        return htmltkb;
    }

}
