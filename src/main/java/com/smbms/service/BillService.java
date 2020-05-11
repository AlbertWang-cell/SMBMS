package com.smbms.service;

import com.github.pagehelper.PageInfo;
import com.smbms.pojo.Bill;
import com.smbms.pojo.vo.BillVo;


public interface BillService {
    PageInfo<BillVo> getBillList(int pageNum, int pageSize, String queryProductName, Long queryProviderId, Integer queryIsPayment) throws Exception;
    String delBillById(Long id) throws Exception;
    void addBill(Bill bill) throws Exception;
    BillVo findBillById(Long id) throws Exception;
    void modifyBill(Bill bill) throws Exception;
}
