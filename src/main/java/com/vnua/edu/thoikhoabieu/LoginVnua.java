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

import org.jsoup.nodes.Element;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.WaitForSelectorState;

public class LoginVnua {

	private ChuongTrinhChinh ct = new ChuongTrinhChinh();
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
            } catch (IOException e) {
                e.printStackTrace();
            } // Lưu vào cache
            System.out.println("Đã lưu lại file: " + tenFile);
        }
        return newHtml;
    }

    private void dangNhapHeThong(Page page, NguoiDung nguoidung) {
        page.navigate("https://daotao.vnua.edu.vn/#/home");
        page.waitForSelector("button:has-text('Đăng nhập')");
        page.click("button:has-text('Đăng nhập')");

        page.waitForSelector("input[formcontrolname='username']");
        page.fill("input[formcontrolname='username']", nguoidung.getTaiKhoan());
        page.fill("input[formcontrolname='password']", nguoidung.getMatKhau());

        page.click("button:has-text('Đăng nhập')");
        page.waitForSelector("span.text-primary.text-justify");
        
        
    }

    public String Login(NguoiDung nguoidung) {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = initBrowser(playwright);
            Page page = browser.newPage();

            dangNhapHeThong(page, nguoidung);

            // Chọn học kỳ và lấy thời khóa biểu ngay trong đây
            return layThoiKhoaBieu(page);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Browser initBrowser(Playwright playwright) {
        return playwright.chromium().launch(
                new BrowserType.LaunchOptions().setHeadless(false)
        );
    }

    private List<String> layDanhSachHocKy(Page page) {
        List<String> dsHocKy = new ArrayList<>();
        page.waitForSelector("#WEB_TKB_HK");
        page.click("#WEB_TKB_HK");
        page.waitForSelector("div[role='combobox']");
        page.click("div[role='combobox']");
        page.waitForTimeout(1000);

        List<ElementHandle> options = page.querySelectorAll("div.ng-option");

        for (ElementHandle option : options) {
            String hocKyText = option.innerText().trim();
            dsHocKy.add(hocKyText);
        }

        page.keyboard().press("Escape");
        return dsHocKy;
    }
    public LocalDate startDateOfTerm(Page page) {
        // Vào thời khóa biểu tuần
        page.locator("//a[@id='WEB_TKB_1TUAN']").click();
        page.waitForSelector("//a[@id='WEB_TKB_1TUAN']");

        // Click mở dropdown chọn tuần
        Locator weekDropdown = page.locator(
            "#fullScreen > div.card-body.p-0 > div.row.text-nowrap.px-1.pb-1 > div.d-inline-block.col-lg-7.col-md-12.col-sm-12.mb-1 > ng-select > div > div > div.ng-input"
        );
        weekDropdown.click();

        // Đợi dropdown hiển thị
        page.waitForSelector(".ng-dropdown-panel-items.scroll-host");

        // Cuộn dropdown về đầu
        page.evaluate("() => document.querySelector('.ng-dropdown-panel-items.scroll-host')?.scrollTo(0, 0)");
        page.waitForTimeout(1000); // Chờ cuộn xong

        // Tìm phần tử đầu tiên trong dropdown
        Locator firstOption = page.locator(
            "//div[@class='ng-dropdown-panel-items scroll-host']//div[contains(@class, 'ng-option')][1]"
        );

        // Đảm bảo phần tử tồn tại và đã hiển thị
        firstOption.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));

        // Lấy nội dung
        String weekText = firstOption.textContent();
        if (weekText == null || !weekText.contains("từ ngày")) {
            throw new RuntimeException("Không tìm thấy thông tin tuần hợp lệ.");
        }

        Pattern pattern = Pattern.compile("từ ngày (\\d{2}/\\d{2}/\\d{4})");
        Matcher matcher = pattern.matcher(weekText);

        if (matcher.find()) {
            String dateString = matcher.group(1);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return LocalDate.parse(dateString, formatter);
        } else {
            throw new RuntimeException("Không trích xuất được ngày bắt đầu từ thông tin tuần.");
        }
    }




    private String chonHocKy(Page page) {
        List<String> dsHocKy = layDanhSachHocKy(page);

        System.out.println("==========////Danh sách học kỳ hiện có////==========");
        int index = 1;
        for (String hocKy : dsHocKy) {
            System.out.println(index++ + ". " + hocKy);
        }

        // Nhập lựa chọn học kỳ
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

    private String layThoiKhoaBieu(Page page) {
    	
    	page.waitForTimeout(2000);
        // Chọn học kỳ trong quá trình lấy TKB
        String hocKyDaChon = chonHocKy(page);
       
        page.waitForSelector("div.ng-option:has-text('" + hocKyDaChon + "')");
        page.click("div.ng-option:has-text('" + hocKyDaChon + "')");

        page.waitForTimeout(1500);
        page.waitForSelector("table.table");

        String htmltkb = (String) page.evaluate("document.querySelector('table.table').outerHTML");
        LocalDate abc=startDateOfTerm(page);
        ChuongTrinhChinh.getInstance().setFirstDayOfTerm(abc);

        System.out.println("FIRSTDAYOFTERM: " + ChuongTrinhChinh.getInstance().getFirstDayOfTerm());
        return htmltkb;
    }

}