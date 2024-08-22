package com.motivaa.ReniReview;

import io.swagger.v3.oas.annotations.Parameters;

public class TestCaseSample_ReniStartHere {

    @BeforeClass
    @Parameters({"url"})
    public void setup(String url) throws Exception {
        loginfo("TSFlexDataTest - Before Class is called..");
        prepareIndexes(url);
        loadPageElements();
    }
    private void prepareIndexes(String url) {
        //Reni, these are the relevant methods for you to review.
        SkyeIndexManager.uploadIndexIfDoesntExists(
                "Colors",
                "src/test/resources/csv/Colors.csv",
                url+"/apis/index"
        );
        SkyeIndexManager.uploadIndexIfDoesntExists(
                "Colors",
                "v1",
                "src/test/resources/csv/Colors_v1.csv",
                url+"/apis/versioned/index"
        );
        SkyeIndexManager.uploadIndexIfDoesntExists(
                "DependencyHousenumberLookup",
                "src/test/resources/csv/DependencyHousenumberLookup.csv",
                url+"/apis/index");
        SkyeIndexManager.uploadIndexIfDoesntExists(
                "housenumberlookup1",
                "src/test/resources/csv/housenumberlookup1.csv",
                url+"/apis/index"
        );
        SkyeIndexManager.uploadIndexIfDoesntExists(
                "streetlookup2",
                "src/test/resources/csv/streetlookup2.csv",
                url+"/apis/index"
        );
        SkyeIndexManager.uploadIndexIfDoesntExists(
                "ZipLookup",
                "src/test/resources/csv/ZipLookup.csv",
                url+"/apis/index"
        );
    }
    private void loadPageElements() throws Exception
    {
        objRegProductsPage = PageFactory.initElements(getDriver(), RegProductsPage.class);
        objTSFDPage = PageFactory.initElements(getDriver(), TSFDPage.class);
    }
}
