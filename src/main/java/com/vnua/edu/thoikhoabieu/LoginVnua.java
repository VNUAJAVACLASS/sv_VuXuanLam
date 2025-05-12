package com.vnua.edu.thoikhoabieu;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.lang.model.element.Element;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

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
    private String startDateOfTerm(Page page) {
        // Bước 1: Mở tab thời khoá biểu
        page.waitForSelector("#WEB_TKB_1TUAN");
        page.click("#WEB_TKB_1TUAN");

        // Bước 2: Mở combobox chọn tuần
        page.waitForSelector("ng-select[bindlabel='thong_tin_tuan'] .ng-select-container");
        page.click("ng-select[bindlabel='thong_tin_tuan'] .ng-select-container");

        // Bước 3: Đợi dropdown hiện ra
        page.waitForSelector(".ng-dropdown-panel");

        // Bước 4: Lấy HTML của dropdown
        String dropdownHtml = page.innerHTML(".ng-dropdown-panel");

        // Bước 5: Phân tích với Jsoup
        Document doc = Jsoup.parse(dropdownHtml);
        Element firstOption = (Element) doc.selectFirst("div.ng-option span.ng-option-label");

        if (firstOption != null) {
            String text = ((org.jsoup.nodes.Element) firstOption).text();

            
            Pattern pattern = Pattern.compile("từ ngày (\\d{2}/\\d{2}/\\d{4})");
            Matcher matcher = pattern.matcher(text);

            if (matcher.find()) {
                String firstDay = matcher.group(1); 
                return firstDay;
            }
        }

        return null; 
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
        String abc=startDateOfTerm(page);
        ChuongTrinhChinh ct = new ChuongTrinhChinh();
        ct.setFirstDayOfTerm(abc);
        System.out.println("FIRSTDAYOFTERM" + ct.getFirstDayOfTerm());
        System.out.println("FIRSTDAYOFTERM" + abc);
        return htmltkb;
    }

}