package org.selenide;


import Pages.Journey;
import Pages.SourcePage;
import com.codeborne.selenide.Selenide;
import org.junit.Test;



import java.lang.reflect.Array;
import java.security.Key;
import java.sql.SQLOutput;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;

public class RegiojetTest {

    @Test
    public void RegiojetOstravaBrnoShortest(){


        Selenide.open("https://shop.regiojet.sk/");
        SourcePage source = new SourcePage();
        source.setFrom("Ostrava");
        source.setTo("Brno");
        source.setThereInput("28");
        source.setBackInput("28");
        source.clickSearchButton();
        List<Journey> journeys = new ArrayList<>();
        journeys = source.loadJourneys();
        source.shortestTravel(journeys);


        }
        
    @Test
    public void RegiojetOstravaBrnolowestPrice(){


        Selenide.open("https://shop.regiojet.sk/");
        SourcePage source = new SourcePage();
        source.setFrom("Ostrava");
        source.setTo("Brno");
        source.setThereInput("28");
        source.setBackInput("28");
        source.clickSearchButton();
        List<Journey> journeys = new ArrayList<>();
        journeys = source.loadJourneys();
        source.lowestPriceTravel(journeys);


    }

}
