package pom.vlad.pfsofttest;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class ColorAdapter extends RecyclerView.Adapter<ColorAdapter.ColorViewHolder> {
    private ArrayList<String> mColorsName;
    private Context mContext;


    class ColorViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextView;
        private CardView mCardView;

        public ColorViewHolder(View itemView) {
            super(itemView);
            mCardView = (CardView) itemView.findViewById(R.id.card_view_color);
            mTextView = (TextView) itemView.findViewById(R.id.text_view_item);
        }
    }

    public ColorAdapter(ArrayList<String> colorsName, Context mContext) {
        this.mColorsName = colorsName;
        this.mContext = mContext;
    }


    @Override
    public ColorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_color, parent, false);

        return new ColorViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ColorViewHolder holder, final int position) {
        final TypedArray arrayColors = mContext.getResources().obtainTypedArray(R.array.colors_array);

        holder.mTextView.setText(mColorsName.get(position));
        holder.mTextView.setTextColor(arrayColors.getColor(position, ContextCompat.getColor(mContext, R.color.colorGrey)));
        holder.mCardView.setCardBackgroundColor(ContextCompat.getColor(mContext, R.color.colorGrey));
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ValueAnimator anim;
                final int ZOOM_CONST=5;
                int verticalSize = (int) mContext.getResources().getDimension(R.dimen
                        .card_view_height);

                if (holder.mCardView.getMeasuredHeightAndState() == verticalSize) {

                    holder.mCardView.setCardBackgroundColor(arrayColors.getColor(position, ContextCompat.getColor(mContext, R.color.colorGrey)));
                    holder.mTextView.setTextColor(ContextCompat.getColor(mContext, R.color.colorGrey));
                    anim = ValueAnimator.ofInt(holder.mCardView.getMeasuredHeightAndState()
                            , holder.mCardView.getMeasuredHeightAndState() * ZOOM_CONST);

                } else {
                    holder.mCardView.setCardBackgroundColor(ContextCompat.getColor(mContext, R.color.colorGrey));
                    holder.mTextView.setTextColor((arrayColors.getColor(position, ContextCompat.getColor(mContext, R.color.colorGrey))));
                    anim = ValueAnimator.ofInt(holder.mCardView.getMeasuredHeightAndState(), verticalSize);
                }

                anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        int val = (Integer) valueAnimator.getAnimatedValue();
                        ViewGroup.LayoutParams layoutParams = holder.mCardView.getLayoutParams();
                        layoutParams.height = val;
                        holder.mCardView.setLayoutParams(layoutParams);
                    }
                });
                anim.start();
                anim.end();
            }

        });

    }

    @Override
    public int getItemCount() {
        return mColorsName.size();
    }
}
