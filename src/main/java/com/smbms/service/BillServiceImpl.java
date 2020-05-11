package com.smbms.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.smbms.dao.BillMapper;
import com.smbms.dao.ProviderMapper;
import com.smbms.pojo.Bill;
import com.smbms.pojo.BillExample;
import com.smbms.pojo.Provider;
import com.smbms.pojo.ProviderExample;
import com.smbms.pojo.vo.BillVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class BillServiceImpl implements BillService {
    @Autowired
    private BillMapper billMapper;
    @Autowired
    private ProviderMapper providerMapper;

    @Override
    public PageInfo<BillVo> getBillList(int pageNum, int pageSize, String queryProductName, Long queryProviderId, Integer queryIsPayment) throws Exception {
        //设置页码和页面大小
        PageHelper.startPage(pageNum,pageSize);
        BillExample example = new BillExample();
        BillExample.Criteria criteria = example.createCriteria();
        if(queryProductName != null && !"".equals(queryProductName)) {
            criteria.andProductNameLike("%"+queryProductName+"%");
        }
        if(queryProviderId != null && queryProviderId>0) {
            criteria.andProviderIdEqualTo(queryProviderId);
        }
        if(queryIsPayment != null &&queryIsPayment>0) {
            criteria.andIsPaymentEqualTo(queryIsPayment);
        }
        List<Bill> list = billMapper.selectByExample(example);
        List<BillVo> billVoList = new ArrayList<>();
        BillVo billVo;
        for(Bill bill:list){
            billVo = new BillVo();
            BeanUtils.copyProperties(bill,billVo);
            Provider provider = providerMapper.selectByPrimaryKey(bill.getProviderId());
            billVo.setProviderName(provider.getProName());
            billVoList.add(billVo);
        }
        //把分页后的list封装到带有
        PageInfo<BillVo> billPageInfo = new PageInfo<>(billVoList);
        return billPageInfo;
    }

    @Override
    public String delBillById(Long id) throws Exception {
        Bill bill = billMapper.selectByPrimaryKey(id);
        if(bill==null||("").equals(bill)){
            return "notexist";
        }else {
            int a = billMapper.deleteByPrimaryKey(id);
            if (a == 0) {
                return "false";
            } else {
                return "true";
            }
        }
    }

    @Override
    public void addBill(Bill bill) throws Exception {
        billMapper.insertSelective(bill);
    }

    @Override
    public BillVo findBillById(Long id) throws Exception {
        Bill bill = billMapper.selectByPrimaryKey(id);
        BillVo billVo = new BillVo();
        BeanUtils.copyProperties(bill,billVo);
        Provider provider = providerMapper.selectByPrimaryKey(bill.getProviderId());
        billVo.setProviderName(provider.getProName());
        return billVo;
    }

    @Override
    public void modifyBill(Bill bill) throws Exception {
        billMapper.updateByPrimaryKeySelective(bill);
    }
}
