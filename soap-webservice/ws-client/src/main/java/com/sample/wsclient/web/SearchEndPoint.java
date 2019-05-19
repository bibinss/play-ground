package com.sample.wsclient.web;

import com.sample.wsclient.dataaccess.AirShopResponse;
import com.sample.wsclient.dataaccess.AirShopService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SearchEndPoint {

    @Autowired
    private AirShopService airShopService;

    @RequestMapping(path="/search", method = RequestMethod.GET)
    public SearchResponse search(@RequestParam String destination) {
        AirShopResponse airShopResponse = airShopService.shop(destination);
        val searchResponse = new SearchResponse();
        searchResponse.setAirline(airShopResponse.getAirline());
        return searchResponse;
    }
}
