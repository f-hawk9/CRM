package com.kkb.common;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.kkb.dao.CrmDictMapper;
import com.kkb.pojo.CrmDict;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
@Component
public class CacheUtil {
    @Autowired
    CrmDictMapper dictMapper;

    private LoadingCache<String ,Object> cache = CacheBuilder.newBuilder()
            .expireAfterAccess(1, TimeUnit.DAYS)
            .maximumSize(10)
            .build(new CacheLoader<String, Object>() {
                @Override
                public Object load(String key) throws Exception {
                    System.out.println("访问数据库");
                    if (key.equals("dictData")){
                        List<CrmDict> dicts = dictMapper.selectByExample(null);
                        HashMap<String,String> map = new HashMap<>();
                        for (CrmDict dict:dicts){
                            map.put(dict.getDictId(),dict.getDictItemName());
                        }
                        return map;
                        //查询数据库放入缓存
                    }
                    return null;
                }
            });
    public String getDictNameByID(String id)  {

        try {
            HashMap<String ,String>  map = (HashMap<String, String>) cache.get("dictData");
            return  map.get(id);
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    //添加自定义缓存
    public  void put(String key,Object value){
        cache.put(key,value);
    }

    //清楚某个缓存
    public void invalidate(String key){
        cache.invalidate(key);
    }

    //获取某个缓存数据
    public Object get(String key){
        try {
            return cache.get(key);
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }
}
