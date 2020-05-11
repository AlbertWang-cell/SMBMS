package com.smbms.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.smbms.pojo.Data;
import com.smbms.pojo.Provider;
import com.smbms.service.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/jsp")
public class ProviderController {
    @Autowired
    private ProviderService providerService;

    @RequestMapping("/plist")
    public String getProviders(Model model, String queryProCode, String queryProName) throws Exception{
        PageInfo<Provider> providerPageInfo  = providerService.getProviderList(1,5,queryProCode,queryProName);

        model.addAttribute("pageInfo",providerPageInfo );

        return "providerlist";
    }
    @RequestMapping("/delprovider")
    public String delProvider(Model model,Long proid) throws Exception{
        String delResult = providerService.delProviderById(proid);
        Data data = new Data();
        data.setDelResult(delResult);
        model.addAttribute("data",data);
        return "providerlist";
    }
    @RequestMapping("/predirectp")
    public String predirectp() throws Exception{
        return "provideradd";
    }
    @RequestMapping("/addprovider")
    public String addProvider(Provider providerForm) throws Exception{
        providerService.addProvider(providerForm);
        return "providerlist";
    }
    @RequestMapping("/queryprovider")
    public String queryProvider(Long proid,Model model) throws Exception{
        Provider provider = providerService.findProviderById(proid);
        model.addAttribute("provider",provider);
        return "providerview";
    }
    @RequestMapping("/predirectm")
    public String predirectm(Model model,Long proid) throws Exception{
        Provider provider = providerService.findProviderById(proid);
        model.addAttribute("provider",provider);
        return "providermodify";
    }

    @RequestMapping("/modifyprovider")
    public String modifyprovider(Provider provider) throws Exception{
        providerService.modifyProvider(provider);
        return "providerlist";
    }

    @RequestMapping("/getproviderlist")
    @ResponseBody
    public List<Provider> getProviderList() throws Exception{
        List<Provider> list = providerService.queryProviderList();
        return list;
    }
}
