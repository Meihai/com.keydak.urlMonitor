package com.keydak.urlmonitor.controller;

import com.keydak.urlmonitor.constants.SpringCon;
import com.keydak.urlmonitor.services.IUrlService;
import com.keydak.urlmonitor.services.Result;
import com.keydak.urlmonitor.services.UrlEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by admin on 2018/7/10.
 */
@Controller
public class UrlMapController extends AbsController {
    @Resource(name= SpringCon.URLSERVICE)
    IUrlService urlService;


    @RequestMapping(value="/toShortUrl", method = RequestMethod.POST)
    @ResponseBody
    public Result getShortUrl(@RequestBody UrlEncoder urlEncoder){
        Result result=urlService.longToShortUrl(urlEncoder);
        return result;
    }


    @RequestMapping(value="/s/{shortUrl}")
    public void forward(@PathVariable("shortUrl") String shortUrl, HttpServletRequest req, HttpServletResponse resp) throws Exception{
         String longUrl=urlService.getLongUrl(shortUrl);
         resp.sendRedirect(longUrl);
    }



}
