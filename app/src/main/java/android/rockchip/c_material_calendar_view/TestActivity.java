package android.rockchip.c_material_calendar_view;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.applandeo.materialcalendarview.CalendarDay;
import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.CalendarWeekDay;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.exceptions.OutOfDateRangeException;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import kotlin.jvm.functions.Function1;

public class TestActivity extends AppCompatActivity {
    private CalendarView calendarView;
    private String TAG ="hank";
    private   List<EventDay> events;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        calendarView  = findViewById(R.id.calendarView);
        try {
            calendarView.setDate(Calendar.getInstance()); //設定日期
            calendarView.setHeaderColor(R.color.black); //設定日歷標題背景顏色
            calendarView.setHeaderLabelColor(R.color.design_default_color_error); //
            calendarView.setForwardButtonImage(getResources().getDrawable(R.drawable.ic_launcher_background));//設定日歷next圖片
            calendarView.setPreviousButtonImage(getResources().getDrawable(R.drawable.ic_launcher_background));//設定日歷back圖片
            calendarView.setHeaderVisibility(View.VISIBLE);//是否隱藏日歷標頭bar

            Calendar calendar = Calendar.getInstance();
            calendar.set(2021,12 , 22);
            Calendar minCalendar = Calendar.getInstance();
            Calendar maxCalendar = Calendar.getInstance();
            minCalendar.set(2021,12,10);
            maxCalendar.set(2021,12,26);
            calendarView.setMinimumDate(minCalendar);//設定可以點選的最小日期
            calendarView.setMaximumDate(maxCalendar);//設定可以點選的最大日期
            calendarView.setDate(calendar);//設定當前日期


            //設定圖片
            events = new ArrayList<>();
            Log.v(TAG , "calendar:" + calendar);
            events.add(new EventDay(calendar, R.drawable.ic_launcher_background));
            events.add(new EventDay(calendar, R.drawable.ic_launcher_background, Color.parseColor("#228B22")));
            calendarView.setEvents(events);



            //當日歷切換月份時
            calendarView.setOnPagePrepareListener(new Function1<Calendar, List<CalendarDay>>() {
                @Override
                public List<CalendarDay> invoke(Calendar calendar) {
                    Log.v(TAG ,"setOnPagePrepareListener() calendar:" + calendar.getTime());
                    return null;
                }
            });

           //當點選到日歷的天數時
            calendarView.setOnDayClickListener(new OnDayClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onDayClick(@NotNull EventDay eventDay) {
                    addDatIcon(eventDay);
                    for(EventDay eventDay1 : events){
                        Log.v(TAG ,"eventDay1:" + eventDay1.getCalendar().getTime());
                    }
                    Log.v(TAG ,"setOnDayClickListener() eventDay:" + eventDay.getCalendar().getTime());
                }
            });

        } catch (OutOfDateRangeException e) {
            e.printStackTrace();
        }
    }

    private void addDatIcon(@NotNull EventDay eventDay) {
        events.add(new EventDay(eventDay.getCalendar(), R.drawable.ic_launcher_background));
        calendarView.setEvents(events);
    }
}