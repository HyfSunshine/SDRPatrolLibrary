package com.sdr.patrollib.ui.history;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarLayout;
import com.haibin.calendarview.CalendarView;
import com.sdr.patrollib.R;
import com.sdr.patrollib.base.fragment.PatrolBaseFragment;
import com.sdr.patrollib.contract.PatrolHistoryDeviceContract;
import com.sdr.patrollib.data.device_history.PatrolHistoryDevice;
import com.sdr.patrollib.presenter.PatrolHistoryDevicePresenter;
import com.sdr.patrollib.ui.history.adapter.PatrolHistoryDeviceRecyclerAdapter;
import com.sdr.patrollib.ui.history_info.PatrolHistoryInfoDeviceActivity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by HyFun on 2018/12/13.
 * Email: 775183940@qq.com
 * Description:
 */

public class PatrolHistoryDeviceFragment extends PatrolBaseFragment<PatrolHistoryDevicePresenter> implements PatrolHistoryDeviceContract.View {
    private TextView tvYearMonth;
    private FrameLayout flToday;
    private TextView tvToday;
    private CalendarLayout calendarLayout;
    private CalendarView calendarView;
    private RecyclerView recyclerView;

    private long startTime;
    private long endTime;
    private Map<Long, List<PatrolHistoryDevice>> dataMap = new HashMap<>();
    private PatrolHistoryDeviceRecyclerAdapter patrolHistoryDeviceRecyclerAdapter;

    @Override
    protected PatrolHistoryDevicePresenter instancePresenter() {
        return new PatrolHistoryDevicePresenter();
    }

    @Nullable
    @Override
    public View onCreateFragmentView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        return layoutInflater.inflate(R.layout.patrol_fragment_history, null);
    }

    @Override
    public String getFragmentTitle() {
        return "设备巡检历史";
    }

    @Override
    protected void bindButterKnife(View view) {
        tvYearMonth = view.findViewById(R.id.patrol_fragment_history_calendar_year_month);
        flToday = view.findViewById(R.id.patrol_fragment_history_fl_calendar_today);
        tvToday = view.findViewById(R.id.patrol_fragment_history_calendar_today);
        calendarLayout = view.findViewById(R.id.patrol_fragment_history_calendar_layout);
        calendarView = view.findViewById(R.id.patrol_fragment_history_calendar_view);
        recyclerView = view.findViewById(R.id.patrol_fragment_history_recycler_view);
    }

    @Override
    protected void onFragmentFirstVisible() {
        patrolHistoryDeviceRecyclerAdapter = new PatrolHistoryDeviceRecyclerAdapter(R.layout.patrol_layout_item_recycler_history_device);
        patrolHistoryDeviceRecyclerAdapter.setEmptyView(getEmptyView());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(patrolHistoryDeviceRecyclerAdapter);
        // 设置起始时间
        startTime = getStartTime();
        endTime = getEndTime();
        // 获取数据
        showLoadingView();
        presenter.getHistoryDeviceList(startTime, endTime);


        // calendar
        {
            // 展开
            calendarLayout.expand();
            // 获取当前时间 显示在顶部
            tvToday.setText(new Date().getDate() + "");
            updateYearMonth(calendarView.getSelectedCalendar());
            // 设置显示时间区间
            Date minDate = new Date(startTime);
            java.util.Calendar minCalendar = java.util.Calendar.getInstance();
            minCalendar.setTime(minDate);

            Date maxDate = new Date(endTime);
            java.util.Calendar maxCalendar = java.util.Calendar.getInstance();
            maxCalendar.setTime(maxDate);
            calendarView.setRange(minCalendar.get(java.util.Calendar.YEAR), minCalendar.get(java.util.Calendar.MONTH) + 1, maxCalendar.get(java.util.Calendar.YEAR), maxCalendar.get(java.util.Calendar.MONTH) + 1);
        }


        // listener
        {
            flToday.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (calendarView.getSelectedCalendar().isCurrentDay()) return;
                    calendarView.scrollToCurrent(true);
                }
            });
            calendarView.setOnDateSelectedListener(dateSelectedListener);
            calendarView.setOnMonthChangeListener(onMonthChangeListener);

            patrolHistoryDeviceRecyclerAdapter.setOnItemClickListener(onItemClickListener);
        }
    }

    //———————————————————————Listener———————————————————————


    // 日期变化监听
    private CalendarView.OnDateSelectedListener dateSelectedListener = new CalendarView.OnDateSelectedListener() {
        @Override
        public void onDateSelected(Calendar calendar, boolean isClick) {
            updateYearMonth(calendar);
            java.util.Calendar c = java.util.Calendar.getInstance();
            c.clear();
            c.set(calendar.getYear(), calendar.getMonth() - 1, calendar.getDay());
            refreshRecycler(c.getTimeInMillis());
        }
    };

    // 月份变化监听
    private CalendarView.OnMonthChangeListener onMonthChangeListener = new CalendarView.OnMonthChangeListener() {
        @Override
        public void onMonthChange(int year, int month) {
            Calendar calendar = new Calendar();
            calendar.setYear(year);
            calendar.setMonth(month);
            updateYearMonth(calendar);
        }
    };

    private BaseQuickAdapter.OnItemClickListener onItemClickListener = new BaseQuickAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
            PatrolHistoryInfoDeviceActivity.start(getContext(), patrolHistoryDeviceRecyclerAdapter.getItem(position).getId() + "");
        }
    };

    //———————————————————————Private———————————————————————

    /**
     * 更新顶部年月显示
     */
    private void updateYearMonth(Calendar calendar) {
        tvYearMonth.setText(calendar.getYear() + "年" + calendar.getMonth() + "月");
    }


    /**
     * 获取结束时间  今天的24点  即 明天的0点  所以加一天
     *
     * @return
     */
    private long getEndTime() {
        Date currentDate = new Date();
        Date date = new Date(currentDate.getYear(), currentDate.getMonth(), currentDate.getDate());
        // 再加一天
        return date.getTime() + 24 * 60 * 60 * 1000l;
    }

    /**
     * 获取查询的开始时间
     *
     * @return
     */
    private long getStartTime() {
        Date currentDateTime = new Date();
        Date currentDate = new Date(currentDateTime.getYear(), currentDateTime.getMonth(), currentDateTime.getDate());
        // 当前日期 - 90天
        return currentDate.getTime() - 90 * 24 * 60 * 60 * 1000l;
    }

    /**
     * 将具体的时间日期  转换成日期  忽略时间
     *
     * @param detailDate
     * @return
     */
    private long transfromDate(long detailDate) {
        Date currentDate = new Date(detailDate);
        Date date = new Date(currentDate.getYear(), currentDate.getMonth(), currentDate.getDate());
        return date.getTime();
    }

    /**
     * 判断 scheme  集合中是否已经存在
     *
     * @param time
     * @param list
     * @return
     */
    private boolean isHaveSchemes(long time, List<Calendar> list) {
        Date date = new Date(time);
        java.util.Calendar c = java.util.Calendar.getInstance();
        c.setTime(date);
        for (int i = 0; i < list.size(); i++) {
            Calendar calendar = list.get(i);
            if (calendar.getYear() == c.get(java.util.Calendar.YEAR) && calendar.getMonth() == (c.get(java.util.Calendar.MONTH) + 1) && calendar.getDay() == c.get(java.util.Calendar.DATE))
                return true;
        }
        return false;
    }

    private void refreshRecycler(long time) {
        // 找出集合
        List<PatrolHistoryDevice> patrolHistoryMobiles = dataMap.get(time);
        patrolHistoryMobiles = (patrolHistoryMobiles == null ? new ArrayList<PatrolHistoryDevice>() : patrolHistoryMobiles);
        patrolHistoryDeviceRecyclerAdapter.setNewData(patrolHistoryMobiles);
    }


    // ———————————————————————VIEW—————————————————————————

    @Override
    public void loadHistoryDeviceListSuccess(List<PatrolHistoryDevice> patrolHistoryDeviceList) {
//
        dataMap.clear();
        List<Calendar> schemes = new ArrayList<>();
        for (PatrolHistoryDevice historyMobile : patrolHistoryDeviceList) {
            long patroltime = transfromDate(historyMobile.getPatrolTime());
            if (!isHaveSchemes(patroltime, schemes)) {
                // 则添加
                java.util.Calendar c = java.util.Calendar.getInstance();
                c.setTime(new Date(patroltime));
                Calendar calendar = new Calendar();
                calendar.setYear(c.get(java.util.Calendar.YEAR));
                calendar.setMonth(c.get(java.util.Calendar.MONTH) + 1);
                calendar.setDay(c.get(java.util.Calendar.DATE));
                calendar.setSchemeColor(Color.parseColor("#09bb07"));//如果单独标记颜色、则会使用这个颜色
                calendar.setScheme("巡");
                schemes.add(calendar);
            }
            // 添加map
            if (dataMap.containsKey(patroltime)) {
                dataMap.get(patroltime).add(historyMobile);
            } else {
                List<PatrolHistoryDevice> list = new ArrayList<>();
                list.add(historyMobile);
                dataMap.put(patroltime, list);
            }
        }
        // 添加标记
        calendarView.setSchemeDate(schemes);
        refreshRecycler(transfromDate(new Date().getTime()));
    }

    @Override
    public void loadDataComplete() {
        showContentView();
    }


}
