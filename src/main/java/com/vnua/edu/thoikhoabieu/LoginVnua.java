package com.vnua.edu.thoikhoabieu;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class LoginVnua {
    public  String  Login() {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions().setHeadless(false)
            );
            Page page = browser.newPage();

            page.navigate("https://daotao.vnua.edu.vn/#/home");

            // Click nút đăng nhập
            page.waitForSelector("button:has-text('Đăng nhập')"); 
            page.click("button:has-text('Đăng nhập')");

            // Nhập tài khoản
            page.waitForSelector("input[formcontrolname='username']");
            page.fill("input[formcontrolname='username']", "671605");
            page.fill("input[formcontrolname='password']", "Lam14032004@");

            // Nhấn nút đăng nhập
            page.click("button:has-text('Đăng nhập')");

            // Chờ tên hiển thị sau khi đăng nhập thành công
            page.waitForSelector("span.text-primary.text-justify");

           
            
            page.click("#WEB_TKB_HK");

            page.waitForSelector("table.table");

            page.waitForTimeout(1000);
            page.waitForSelector("div[role='combobox']");
            page.click("div[role='combobox']");  

            page.waitForSelector("div.ng-option");

            page.click("div.ng-option:has-text('Học kỳ 2 - Năm học 2024 - 2025')");
            page.waitForTimeout(3000);  // Thời
            
            String tkbHtml = (String) page.evaluate("document.querySelector('table.table').outerHTML");
            
            return tkbHtml;

        } catch (Exception e) {
            e.printStackTrace();
        }
		return null;
    }
}
