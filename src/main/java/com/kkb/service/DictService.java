package com.kkb.service;

import com.kkb.pojo.CrmDict;

import java.util.List;

public interface DictService {
    public List<CrmDict> getDictList(String code);
}
