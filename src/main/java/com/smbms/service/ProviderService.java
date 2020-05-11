package com.smbms.service;

import com.github.pagehelper.PageInfo;
import com.smbms.pojo.Provider;

import java.util.List;

public interface ProviderService {
   PageInfo<Provider> getProviderList(int pageNum, int pageSize,String queryProCode,String queryProName) throws Exception;

    String delProviderById(Long id) throws Exception;
    void addProvider(Provider provider) throws Exception;
    Provider findProviderById(Long id) throws Exception;
    void modifyProvider(Provider provider) throws Exception;
    List<Provider> queryProviderList() throws Exception;
}
