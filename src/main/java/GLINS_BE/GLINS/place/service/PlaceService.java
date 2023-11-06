package GLINS_BE.GLINS.place.service;

import GLINS_BE.GLINS.place.domain.Place;
import GLINS_BE.GLINS.place.dto.PlaceRequestDto;
import GLINS_BE.GLINS.place.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PlaceService {
    private final PlaceRepository placeRepository;

    public String createPlace(PlaceRequestDto requestDto) {

        String url = "https://www.diningcode.com/search.dc?query="
                + requestDto.getAddress() + " " + requestDto.getPlaceName() ; // 크롤링할 웹 페이지 URL
        System.out.println("url = " + url);

        // Chrome WebDriver 경로 설정
//        System.setProperty("webdriver.chrome.driver", "C:\\Users\\skap0\\Desktop\\Project\\chromedriver_win32\\chromedriver.exe");
        System.setProperty("webdriver.chrome.driver", "/home/ubuntu/chromedriver-linux64/chromedriver");


        // Chrome 브라우저 설정 (headless 모드로 설정하면 화면이 노출되지 않음)
        ChromeOptions options = new ChromeOptions();
        options.setHeadless(true);
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--window-size=1920x1080"); // 브라우저 창 크기 설정
        options.addArguments("--disable-gpu"); // GPU 가속 비활성화
        options.addArguments("--user-agent=Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36"); // User-Agent 설정
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");


        // WebDriver 초기화
        WebDriver driver = new ChromeDriver(options);
        String category = null;
        String hashtag = null;
        String imgUrl = null;

        try {
            // 웹 페이지 열기
            driver.get(url);

            Thread.sleep(500);
//            System.out.println("driver.getPageSource() = " + driver.getPageSource());


            // Category 추출
            WebElement categoryElement = driver.findElement(By.cssSelector(".Category"));
            category = categoryElement.getText().replaceAll("\n", "").trim();
            System.out.println("Category: " + category);

            // Hash 추출
            WebElement hashElement = driver.findElement(By.cssSelector(".Hash"));
            hashtag = hashElement.getText().replaceAll("\n", "").trim().trim();
            System.out.println("Hash: " + hashtag);

            // 이미지 추출
            WebElement imgElement = driver.findElement(By.className("RHeader")).findElement(By.className("title"));
            imgUrl = imgElement.getAttribute("src");
            System.out.println("ImgUrl: " + imgUrl);
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        finally {
            // WebDriver 종료
            driver.quit();
            Place place = requestDto.toEntity(category, hashtag, imgUrl);
            placeRepository.save(place);
            return "장소 등록 완료";
        }
    }
}
