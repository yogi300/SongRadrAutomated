package WebDriver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.HashMap;
import java.util.Map;

public class Browser {

    static WebDriver webDriver;
    public static WebDriver getWebDriver(){
        if(webDriver == null){
            System.setProperty("webdriver.chrome.driver",
                    "C:\\Users\\Public\\webdriver\\chromedriver.exe");
            Map<String, Object> prefs = new HashMap<String, Object>();
            prefs.put("profile.default_content_setting_values.notifications", 2);
            ChromeOptions options = new ChromeOptions();
            options.setExperimentalOption("prefs", prefs);
            webDriver=new ChromeDriver(options);
        }
        return webDriver;
    }
}
