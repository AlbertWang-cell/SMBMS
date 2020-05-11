package com.smbms.controller;

import com.github.pagehelper.PageInfo;
import com.smbms.pojo.Bill;
import com.smbms.pojo.vo.BillVo;
import com.smbms.service.BillService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping("/jsp")
public class BillController {
    @Autowired
    private BillService billService;

    @RequestMapping("/blist")
    public String getBills(Model model, String queryProductName, Long queryProviderId, Integer queryIsPayment) throws Exception{
        PageInfo<BillVo> billPageInfo  = billService.getBillList(1,5,queryProductName,queryProviderId,queryIsPayment);
        model.addAttribute("pageInfo",billPageInfo );
        return "billlist";
    }
    @RequestMapping("/delbill")
    public String delBill(Long billid) throws Exception{
        billService.delBillById(billid);
        return "billlist";
    }
    @RequestMapping("/bredirectb")
    public String redirectu() throws Exception{
        return "billadd";
    }
    @RequestMapping("/addbill")
    public String addBill(Bill billForm) throws Exception{
        billService.addBill(billForm);
        return "billlist";
    }
    @RequestMapping("/bquerybill")
    public String queryBill(Long billid,Model model) throws Exception{
        BillVo billVo = billService.findBillById(billid);
        model.addAttribute("bill",billVo);
        return "billview";
    }
    @RequestMapping("/bredirectm")
    public String redirectm(Model model,Long billid) throws Exception{
        BillVo billVo = billService.findBillById(billid);
        model.addAttribute("bill",billVo);
        return "billmodify";
    }

    @RequestMapping("/modifybill")
    public String modifybill(Bill bill) throws Exception{
        billService.modifyBill(bill);
        return "billlist";
    }
}
