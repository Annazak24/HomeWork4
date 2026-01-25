package factory;

import exceptions.BrowserNotSupportedException;
import factory.settings.ChromeSettings;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;

public class WebDriverFactory {

    private final String runType = System.getProperty("run.type", "remote");

    public WebDriver create() {
        System.out.println("DOCKER_API_VERSION = " + System.getenv("DOCKER_API_VERSION"));
        try {
            if ("remote".equals(runType)) {
                ChromeOptions options = new ChromeOptions();
                options.setCapability("browserName", "chrome");
                options.setCapability("browserVersion", "128.0");
                options.setCapability("selenoid:options", java.util.Map.of(
                        "enableVNC", true
                ));

                return new RemoteWebDriver(
                        new URL("http://localhost:4444/wd/hub"),
                        options
                );
            }

            // LOCAL
            return new org.openqa.selenium.chrome.ChromeDriver(
                    (ChromeOptions) new ChromeSettings().settings()
            );

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
