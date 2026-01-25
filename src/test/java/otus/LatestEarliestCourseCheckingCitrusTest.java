package otus;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.google.inject.Inject;
import dto.CourseInfo;
import extensions.UiExtensions;
import java.util.List;
import java.util.Map;
import org.citrusframework.annotations.CitrusTest;
import org.citrusframework.junit.jupiter.CitrusExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import pages.CatalogPage;

@ExtendWith({UiExtensions.class, CitrusExtension.class})
public class LatestEarliestCourseCheckingCitrusTest {

    @Inject
    private CatalogPage catalogPage;

    @Inject
    private WebDriver driver;

    @Test
    @CitrusTest
    void findEarliestAndLatestCoursesTest() {

        catalogPage.open();

        List<CourseInfo> allCourses = catalogPage.getAllCourses();

        Map<String, List<String>> earliestAndLatestCourses =
                catalogPage.getEarliestAndLatestCourseNames(allCourses);

        String earliestCourseName =
                earliestAndLatestCourses.get("earliest").getFirst();
        String latestCourseName =
                earliestAndLatestCourses.get("latest").getFirst();

        // Earliest course
        catalogPage.clickCourseByName(earliestCourseName);
        String earliestTitle = catalogPage.getCourseTitleByJsoup();

        assertTrue(
                earliestTitle.contains(earliestCourseName),
                String.format(
                        "Expected '%s', but found '%s'",
                        earliestCourseName,
                        earliestTitle
                )
        );

        driver.navigate().back();

        // Latest course
        catalogPage.clickCourseByName(latestCourseName);
        String latestTitle = catalogPage.getCourseTitleByJsoup();

        assertTrue(
                latestTitle.contains(latestCourseName),
                String.format(
                        "Expected '%s', but found '%s'",
                        latestCourseName,
                        latestTitle
                )
        );
    }
}
