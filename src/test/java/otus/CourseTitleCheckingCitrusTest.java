package otus;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.google.inject.Inject;
import extensions.UiExtensions;
import org.citrusframework.annotations.CitrusTest;
import org.citrusframework.junit.jupiter.CitrusExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import pages.CatalogPage;

@ExtendWith({UiExtensions.class, CitrusExtension.class})
public class CourseTitleCheckingCitrusTest {

    @Inject
    private CatalogPage catalogPage;

    @Test
    @CitrusTest
    void findCourseByNameTest() {

        String courseName = "Team Lead";

        catalogPage.open();
        catalogPage.clickCourseByName(courseName);

        String title = catalogPage.getCourseTitleByJsoup();

        assertEquals(
                courseName,
                title,
                String.format(
                        "Expected '%s', but found '%s'",
                        courseName,
                        title
                )
        );
    }
}
