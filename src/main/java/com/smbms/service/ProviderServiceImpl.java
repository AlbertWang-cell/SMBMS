package com.smbms.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.smbms.dao.ProviderMapper;
import com.smbms.pojo.Provider;
import com.smbms.pojo.ProviderExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProviderServiceImpl implements ProviderService {
    @Autowired
    private ProviderMapper providerMapper;

    @Override
  public PageInfo<Provider> getProviderList(int pageNum, int pageSize, String queryProCode,String queryProName) throws Exception {

            //设置页码和页面大小
        PageHelper.startPage(pageNum,pageSize);
        ProviderExample example = new ProviderExample();
        ProviderExample.Criteria criteria = example.createCriteria();
        if(queryProCode != null && !"".equals(queryProCode)) {
            criteria.andProCodeLike("%"+queryProCode+"%");
        }
        if(queryProName != null && !"".equals(queryProName)) {
            criteria.andProNameLike("%"+queryProName+"%");
        }
        List<Provider> list = providerMapper.selectByExample(example);
        //把分页后的list封装到带有
        PageInfo<Provider> providerPageInfo = new PageInfo<>(list);
        return providerPageInfo;

    }


    @Override
    public String delProviderById(Long id) throws Exception {
        Provider provider = providerMapper.selectByPrimaryKey(id);
        if(provider==null||("").equals(provider)){
            return "notexist";
        }else {
            int a = providerMapper.deleteByPrimaryKey(id);
            System.out.println(a);
            if (a != 0) {
                return "true";
            } else {
                return "false";
            }
        }
    }

    @Override
    public void addProvider(Provider provider) throws Exception {
        providerMapper.insertSelective(provider);
    }

    @Override
    public Provider findProviderById(Long id) throws Exception {
        return providerMapper.selectByPrimaryKey(id);
    }

    @Override
    public void modifyProvider(Provider provider) throws Exception {
        providerMapper.updateByPrimaryKeySelective(provider);
    }

    @Override
    public List<Provider> queryProviderList() throws Exception{
        ProviderExample example = new ProviderExample();
        List<Provider> list = providerMapper.selectByExample(example);
        return list;
    }
}
