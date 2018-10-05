package org.redout.sol;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.MyViewHolder> {
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView fcstHigh, fcstLow;
        public ImageView fcstImage;

        public myViewHolder(View view) {
            super(view);
            fcstHigh = view.findViewsWithText(R.id.fcstHigh);


        }
    }
}
