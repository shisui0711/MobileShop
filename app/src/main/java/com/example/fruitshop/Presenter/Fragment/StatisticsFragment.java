package com.example.fruitshop.Presenter.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fruitshop.Application.ViewModel.OrderViewModel;
import com.example.fruitshop.R;
import com.example.fruitshop.databinding.FragmentStatisticsBinding;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.logging.SimpleFormatter;

public class StatisticsFragment extends Fragment {
    FragmentStatisticsBinding binding;
    OrderViewModel orderViewModel;
    int selectFilter = 1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentStatisticsBinding.inflate(inflater);
        orderViewModel = new ViewModelProvider(this).get(OrderViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadChart();
        handleFilter();
//        orderViewModel.getOrderCountByDate("2025-01-01","2025-01-30").observe(getViewLifecycleOwner(),data -> {
//
//        });

    }

    public void handleFilter(){
        binding.btnDay.setOnClickListener(v->{
//            binding.btnMonth.setBackgroundDrawable(R.drawable);
        });
    }



    private void loadChart(){
        Map<Date, BigDecimal> revenueData = new LinkedHashMap<>();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            revenueData.put(sdf.parse("2025-01-10"), BigDecimal.valueOf(500));
            revenueData.put(sdf.parse("2025-01-11"), BigDecimal.valueOf(700));
            revenueData.put(sdf.parse("2025-01-12"), BigDecimal.valueOf(450));
            revenueData.put(sdf.parse("2025-01-13"), BigDecimal.valueOf(800));
            revenueData.put(sdf.parse("2025-01-14"), BigDecimal.valueOf(650));
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Chuyển Map<Date, BigDecimal> thành BarEntry
        ArrayList<BarEntry> entries = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>();

        int index = 0;
        for (Map.Entry<Date, BigDecimal> entry : revenueData.entrySet()) {
            entries.add(new BarEntry(index, entry.getValue().floatValue())); // Giá trị
            labels.add(new SimpleDateFormat("dd/MM", Locale.getDefault()).format(entry.getKey())); // Nhãn ngày
            index++;
        }

        // Tạo BarDataSet
        BarDataSet dataSet = new BarDataSet(entries, "Doanh thu");
        dataSet.setColor(getResources().getColor(R.color.primary_500));
        dataSet.setValueTextColor(getResources().getColor(R.color.black));

        // Tạo BarData
        BarData barData = new BarData(dataSet);
        barData.setBarWidth(0.8f); // Độ rộng cột

        // Gán dữ liệu cho BarChart
        binding.barChart.setData(barData);

        // Tùy chỉnh trục X
        XAxis xAxis = binding.barChart.getXAxis();
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return labels.get((int) value); // Hiển thị nhãn theo ngày
            }
        });
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f); // Đảm bảo nhảy đúng 1 đơn vị
        xAxis.setGranularityEnabled(true);

        // Tùy chỉnh khác
        binding.barChart.getDescription().setEnabled(false); // Tắt mô tả
        binding.barChart.getAxisRight().setEnabled(false); // Tắt trục Y bên phải
        binding.barChart.getAxisLeft().setAxisMinimum(0f); // Giá trị nhỏ nhất trên trục Y
        binding.barChart.animateY(1000); // Thêm hoạt ảnh

        // Làm mới biểu đồ
        binding.barChart.invalidate();
    }

    public static String getStartDate(int selectFilter) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        switch (selectFilter) {
            case 1: // Hôm nay
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                break;
            case 2: // Tuần này
                calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                break;
            case 3: // Tháng này
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                break;
            case 4: // Năm nay
                calendar.set(Calendar.DAY_OF_YEAR, 1);
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                break;
            default:
                throw new IllegalArgumentException("SelectFilter không hợp lệ");
        }

        return sdf.format(calendar.getTime());
    }

    public static String getEndDate(int selectFilter) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        switch (selectFilter) {
            case 1: // Hôm nay
                break; // Dùng giờ hiện tại
            case 2: // Tuần này
                calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek() + 6);
                calendar.set(Calendar.HOUR_OF_DAY, 23);
                calendar.set(Calendar.MINUTE, 59);
                calendar.set(Calendar.SECOND, 59);
                calendar.set(Calendar.MILLISECOND, 999);
                break;
            case 3: // Tháng này
                calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
                calendar.set(Calendar.HOUR_OF_DAY, 23);
                calendar.set(Calendar.MINUTE, 59);
                calendar.set(Calendar.SECOND, 59);
                calendar.set(Calendar.MILLISECOND, 999);
                break;
            case 4: // Năm nay
                calendar.set(Calendar.DAY_OF_YEAR, calendar.getActualMaximum(Calendar.DAY_OF_YEAR));
                calendar.set(Calendar.HOUR_OF_DAY, 23);
                calendar.set(Calendar.MINUTE, 59);
                calendar.set(Calendar.SECOND, 59);
                calendar.set(Calendar.MILLISECOND, 999);
                break;
            default:
                throw new IllegalArgumentException("SelectFilter không hợp lệ");
        }

        return sdf.format(calendar.getTime());
    }
}