package org.redout.sol;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.redout.sol.weather.WxUtil;
import org.redout.sol.weather.hourlyforecast.HourlyForecastList;

import java.util.List;


public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.MyViewHolder> {

    List<HourlyForecastList> forecastList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView fcstHigh, fcstLow;
        public ImageView fcstIcon;

        public MyViewHolder(View view) {
            super(view);
            fcstHigh = view.findViewById(R.id.fcstHigh);
            fcstLow = view.findViewById(R.id.fcstLow);
            fcstIcon = view.findViewById(R.id.fcstIcon);
        }

    }
    public ForecastAdapter(java.util.List<HourlyForecastList> forecastList) {
        this.forecastList = forecastList;
        System.out.println(forecastList.size());
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.forecast, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        HourlyForecastList forecastListItem = forecastList.get(position);
        holder.fcstHigh.setText(WxUtil.convTemp(forecastListItem.getMain().getTempMax(), WxUtil.TEMP_UNIT_KELVIN, WxUtil.TEMP_UNIT_FAHRENHEIT).toString());
        holder.fcstLow.setText(WxUtil.convTemp(forecastListItem.getMain().getTempMin(), WxUtil.TEMP_UNIT_KELVIN, WxUtil.TEMP_UNIT_FAHRENHEIT).toString());
        int iconId = holder.fcstIcon.getContext().getResources().getIdentifier("wxicon_" + forecastListItem.getWeather().get(0).getIcon(), "drawable", holder.fcstIcon.getContext().getPackageName() );
        holder.fcstIcon.setImageResource(iconId);
    }

    @Override
    public int getItemCount() {
        return forecastList.size();
    }
}
