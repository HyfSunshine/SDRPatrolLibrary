package com.sdr.patrollib;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;

import com.sdr.patrollib.ui.main.PatrolMainActivity;

/**
 * Created by HyFun on 2018/12/06.
 * Email: 775183940@qq.com
 * Description:
 */

public class PatrolLibrary {
    private static PatrolLibrary patrolLibrary;

    private PatrolLibrary() {
    }

    public static final PatrolLibrary getInstance() {
        if (patrolLibrary == null) {
            synchronized (PatrolLibrary.class) {
                if (patrolLibrary == null) {
                    patrolLibrary = new PatrolLibrary();
                }
            }
        }
        return patrolLibrary;
    }

    /**
     * 开启巡检主Activity
     *
     * @param context
     * @param patrolUser
     */
    public static final void start(Context context, PatrolUser patrolUser) {
        // 先设置用户
        getInstance().setPatrolUser(patrolUser);
        context.startActivity(new Intent(context, PatrolMainActivity.class));
    }


    private Application application;
    private boolean debug;
    private String url;
    private int toolbarRes;
    private Drawable headerBarDrawable;

    /**
     * 初始化  在application中初始化
     *
     * @param application
     * @param url
     * @param toolbarRes
     * @param headerBarDrawable
     */
    public void init(Application application,boolean debug, String url, int toolbarRes, Drawable headerBarDrawable) {
        this.application = application;
        this.debug = debug;
        this.url = url;
        this.toolbarRes = toolbarRes;
        this.headerBarDrawable = headerBarDrawable;
    }

    public Application getApplication() {
        return application;
    }

    public boolean isDebug() {
        return debug;
    }

    public String getUrl() {
        return url;
    }

    public int getToolbarRes() {
        return toolbarRes;
    }

    public Drawable getHeaderBarDrawable() {
        return headerBarDrawable;
    }

    private PatrolUser patrolUser;

    public PatrolUser getPatrolUser() {
        return patrolUser;
    }

    public void setPatrolUser(PatrolUser patrolUser) {
        this.patrolUser = patrolUser;
    }

    private PatrolConfig patrolConfig = new PatrolConfig();

    public PatrolConfig getPatrolConfig() {
        return patrolConfig;
    }

    public void setPatrolConfig(PatrolConfig patrolConfig) {
        this.patrolConfig = patrolConfig;
    }
}
