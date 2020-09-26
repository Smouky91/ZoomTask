package Pages;

import Pages.Journey;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class SourcePage {

        SelenideElement from = $(By.id("route-from")),
                to = $(By.id("route-to")),
                thereInput = $(By.id("route-there-input")),
                backInput = $(By.id("route-back-input")),
                searchButton = $(By.id("search-button"));

        ElementsCollection journeys = $$(By.className("jsx-1148001864"));
        ElementsCollection details = $$(".detail-arrow");

        public void setFrom(String fromString){
                from.setValue(fromString).pressEnter();
        }

        public void setTo(String toString){

                to.setValue(toString).pressEnter();
        }

        public void setThereInput(String thereInputString){
                thereInput.sendKeys(Keys.CONTROL + "a");
                thereInput.sendKeys(Keys.DELETE);
                thereInput.setValue(thereInputString);
        }

        public void setBackInput(String backInputString){
                backInput.sendKeys(Keys.CONTROL + "a");
                backInput.sendKeys(Keys.DELETE);
                backInput.setValue(backInputString);
        }

        public void clickSearchButton(){

                searchButton.click();
                details.get(0).should(Condition.visible);

        }

        public List<Journey> loadJourneys(){
                List<Journey> listOfJourneys = new ArrayList<>();
                for(SelenideElement detail : details ){
                        Journey element = new Journey();
                        detail.click();
                        SelenideElement OpenedDetail = $(".connection-detail.open");
                        element.setDepartureTime(OpenedDetail.$(".open .text-regular[style='font-weight: bold;']").getValue());
                        element.setArrivalTime(OpenedDetail.$(".open .times [style='font-weight: normal;']").getValue());
                        LocalTime timeDistance = LocalTime.parse("00:00");
                        ElementsCollection distances = $$(By.className("jsx-333541677")).filter(Condition.text("Doba cesty"));
                        for(int i=1;i<distances.size();i=i+2){
                                String time = distances.get(i).getText();
                                time = time.replace("Doba cesty: ","").substring(0,5);
                                LocalTime ltime = LocalTime.parse(time);
                                timeDistance.plusHours(ltime.getHour()).plusMinutes(ltime.getMinute());
                        }
                        element.setDistance(timeDistance);
                        String price = OpenedDetail.$(".open #price-yellow-desktop").$("span").getText();
                        price = price.replaceFirst("od ", "").replaceAll(" €","");
                        element.setPrice(Float.parseFloat(price));
                        ElementsCollection connectionDetails =OpenedDetail.$$(".button[aria-label='accessibility.connectionDetailModal']");
                        int i = 0;
                        for(SelenideElement connectionDetail: connectionDetails){
                                connectionDetail.click();
                                i= i + $$(".active").size();
                                $(".CONNECTION_DETAIL_MODAL .close").click();
                        }
                        element.setNumberOfStops(i);
                        listOfJourneys.add(element);
                }
                return  listOfJourneys;
        }

        public Journey shortestTravel(List<Journey> Journeys){
                Journey fastestArrival = Journeys.get(0);
                LocalTime time2 = Journeys.get(0).getDistance();
                for(Journey Journey:Journeys){
                        LocalTime time = Journey.getDistance();
                        int value = time.compareTo(time2);
                        if(value < 0 ){
                                time2 = time;
                                fastestArrival = Journey;
                        }
                }
                return fastestArrival;
        }

        public Journey lowestPriceTravel(List<Journey> Journeys){
                Journey lowestPrice = Journeys.get(0);
                float a = Journeys.get(0).getPrice();
                for(Journey Journey:Journeys){
                        float price = Journey.getPrice();
                        if(price< a ){
                                a = price;
                                lowestPrice = Journey;
                        }
                }
                return lowestPrice;
        }

        public JourneyApi lowestPriceJourney(JourneyApis getJourneys,JourneyApi lowestPrice){
                float a = Float.parseFloat(lowestPrice.getPriceFrom());
                for(JourneyApi Journey1:getJourneys.getRoutes()){
                        float price = Float.parseFloat(Journey1.getPriceFrom());
                        if(price< a ){
                                a = price;
                                lowestPrice = Journey1;
                        }
                }
                return lowestPrice;
        }

        public JourneyApi fastestArrivalJourney(JourneyApis getJourneys,JourneyApi fastestArrival){
                LocalTime time2 = LocalTime.parse(fastestArrival.getTravelTime().substring(0,5));
                for(JourneyApi Journey1:getJourneys.getRoutes()){
                        LocalTime time = LocalTime.parse(Journey1.getTravelTime().substring(0,5));
                        int value = time.compareTo(time2);
                        if(value < 0 ){
                                time2 = time;
                                fastestArrival = Journey1;
                        }
                }
                return fastestArrival;
        }
}
