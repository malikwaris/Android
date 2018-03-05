package org.sferadev.meetings.timer;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fima.cardsui.objects.Card;

public class MyPart3Card extends Card {

	View v;
	TextView chrono;
	
	public MyPart3Card(String titlePlay, String description, String color,
			String titleColor, Boolean hasOverflow, Boolean isClickable) {
		super(titlePlay, description, color, titleColor, hasOverflow,
				isClickable);
	}

	public View getCardContent(Context context) {
		v = LayoutInflater.from(context).inflate(R.layout.card_part3, null);
		
		((TextView) v.findViewById(R.id.title)).setText(titlePlay);
		((TextView) v.findViewById(R.id.title)).setTextColor(Color
				.parseColor(titleColor));
		((TextView) v.findViewById(R.id.description)).setText(description);
		((ImageView) v.findViewById(R.id.stripe)).setBackgroundColor(Color
				.parseColor(color));

		if (isClickable == true)
			((LinearLayout) v.findViewById(R.id.contentLayout))
					.setBackgroundResource(R.drawable.selectable_background_cardbank);

		chrono = (TextView) v.findViewById(R.id.chrono);
		
		onLoadView();
		
		return v;
	}
	
	public void onLoadView(){
		if (MainActivity.bPart3 == 0 && MainActivity.lPart3 != "justStarted"){
			chrono.setText(MainActivity.lPart3);
		}
		if (MainActivity.bPart3 == 1 && MainActivity.lPart3 == "justStarted"){
			chrono.setText(App.getContext().getString(R.string.stop));
		}
	}
}
