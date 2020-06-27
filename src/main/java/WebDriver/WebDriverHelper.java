package WebDriver;

public class WebDriverHelper {

    static Browser browser = new Browser();
    public static Browser getBrowser(){
        if(browser == null)
            browser = new Browser();
        return browser;
    }
}
