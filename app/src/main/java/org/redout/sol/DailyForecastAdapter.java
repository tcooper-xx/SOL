package org.redout.sol;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.redout.sol.weather.WxUtil;
import org.redout.sol.weather.dailyforecast.DailyForecastList;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class DailyForecastAdapter extends RecyclerView.Adapter<DailyForecastAdapter.MyViewHolder> {

    List<DailyForecastList> forecastList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView fcstHigh, fcstLow, fcstDay;
        public ImageView fcstIcon;

        public MyViewHolder(View view) {
            super(view);
            fcstDay = view.findViewById(R.id.fcstDay);
            fcstHigh = view.findViewById(R.id.fcstHigh);
            fcstLow = view.findViewById(R.id.fcstLow);
            fcstIcon = view.findViewById(R.id.fcstIcon);
        }

    }
    public DailyForecastAdapter(List<DailyForecastList> forecastList) {
        this.forecastList = forecastList;
        System.out.println(forecastList.size());
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.daily_forecast, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        DailyForecastList forecastListItem = forecastList.get(position);
        Date fcDate = new Date(forecastListItem.getDt()*1000);
        SimpleDateFormat sdf = new SimpleDateFormat("EEE");
        holder.fcstDay.setText(sdf.format(fcDate));
        holder.fcstHigh.setText(WxUtil.convTemp(forecastListItem.getTemp().getMax(), WxUtil.TEMP_UNIT_KELVIN, WxUtil.TEMP_UNIT_FAHRENHEIT).toString() + "°");
        holder.fcstLow.setText(WxUtil.convTemp(forecastListItem.getTemp().getMin(), WxUtil.TEMP_UNIT_KELVIN, WxUtil.TEMP_UNIT_FAHRENHEIT).toString() + "°");


        int iconId = holder.fcstIcon.getContext().getResources().getIdentifier("wxicon_" + forecastListItem.getWeather().get(0).getIcon(), "drawable", holder.fcstIcon.getContext().getPackageName() );
        holder.fcstIcon.setImageResource(iconId);
    }

    @Override
    public int getItemCount() {
        return forecastList.size();
    }
}
