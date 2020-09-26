package org.selenide;
import Pages.JourneyApi;
import Pages.JourneyApis;
import Pages.SourcePage;
import io.restassured.RestAssured;
import io.restassured.RestAssured.*;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLOutput;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

public class RegiojetApiTest {

    @Before
    public void setup() {
        RestAssured.baseURI = "https://brn-ybus-pubapi.sa.cz";
    }

    @Test
    public void givenUrl_Journeys() {
        JourneyApis getJourneys1 =
             RestAssured.given().log().all()
                .when()
                .param("departureDate","2020-09-28")
                .param("fromLocationId","10202000")
                .param("fromLocationType","CITY")
                .param("tariffs","REGULAR")
                .param("toLocationId","10202002")
                .param("toLocationType","CITY")
                .request("GET", "/restapi/routes/search/simple")
                .then().statusCode(200)
                .extract()
                .as(JourneyApis.class);

        JourneyApis getJourneys2 =
                RestAssured.given().log().all()
                        .when()
                        .param("departureDate","2020-09-28")
                        .param("fromLocationId","10202002")
                        .param("fromLocationType","CITY")
                        .param("tariffs","REGULAR")
                        .param("toLocationId","10202000")
                        .param("toLocationType","CITY")
                        .request("GET", "/restapi/routes/search/simple")
                        .then().statusCode(200)
                        .extract()
                        .as(JourneyApis.class);

        SourcePage source = new SourcePage();
        JourneyApi lowestPrice = getJourneys1.getRoutes().get(0);
        JourneyApi fastestArrival = getJourneys1.getRoutes().get(0);
        lowestPrice = source.lowestPriceJourney(getJourneys1,lowestPrice);
        lowestPrice = source.lowestPriceJourney(getJourneys2,lowestPrice);
        fastestArrival = source.fastestArrivalJourney(getJourneys1,fastestArrival);
        fastestArrival = source.fastestArrivalJourney(getJourneys2,fastestArrival);
        System.out.println(lowestPrice);
        System.out.println(fastestArrival);

    }
}
