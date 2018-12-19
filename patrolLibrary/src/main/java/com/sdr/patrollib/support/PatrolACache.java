package com.sdr.patrollib.support;

import com.sdr.lib.support.ACache;
import com.sdr.lib.support.path.AppPath;

import java.io.File;

/**
 * Created by HyFun on 2018/12/07.
 * Email: 775183940@qq.com
 * Description:
 */

public class PatrolACache {
    private static ACache patrolAcache;

    public static final ACache getACache() {
        if (patrolAcache == null) {
            synchronized (PatrolACache.class) {
                if (patrolAcache == null) {
                    patrolAcache = ACache.get(new File(AppPath.getUserInfoCache()));
                }
            }
        }
        return patrolAcache;
    }

    /**
     * 拍照 视频 保存的位置
     *
     * @return
     */
    public static final String getSavePath() {
        return AppPath.getImagePath();
    }
}
