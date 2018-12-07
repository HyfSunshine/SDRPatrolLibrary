package com.sdr.patrollib.support;

import com.sdr.lib.support.ACache;
import com.sdr.patrollib.PatrolLibrary;

/**
 * Created by HyFun on 2018/12/07.
 * Email: 775183940@qq.com
 * Description:
 */

public class PatrolACache {
    private static ACache patrolAcache;

    public static final ACache getACache(){
        if (patrolAcache == null){
            synchronized (PatrolACache.class){
                if (patrolAcache == null){
                    patrolAcache = ACache.get(PatrolLibrary.getInstance().getApplication().getFilesDir());
                }
            }
        }
        return patrolAcache;
    }
}
