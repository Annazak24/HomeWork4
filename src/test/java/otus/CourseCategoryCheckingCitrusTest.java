package otus;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.google.inject.Inject;
import extensions.UiExtensions;
import org.citrusframework.annotations.CitrusTest;
import org.citrusframework.junit.jupiter.CitrusExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import pages.CatalogPage;
import pages.MainPage;

@ExtendWith({UiExtensions.class, CitrusExtension.class})
public class CourseCategoryCheckingCitrusTest {

    @Inject
    private CatalogPage catalogPage;

    @Inject
    private MainPage mainPage;

    @Test
    @CitrusTest
    void findCourseByCategoryTest() {

        mainPage.open();
        mainPage.hoverTraining();

        String categoriesName = mainPage.clickRandomCategory()
                .replaceAll("\\s*\\(\\d+\\)", "")
                .trim();

        String actualCategoriesName = catalogPage.getCategoryName();

        assertEquals(
                categoriesName,
                actualCategoriesName,
                String.format(
                        "Expected '%s', but found '%s'",
                        categoriesName,
                        actualCategoriesName
                )
        );
    }
}
