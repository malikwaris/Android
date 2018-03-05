package org.sferadev.meetings.timer;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fima.cardsui.objects.Card;

public class MyReadingCard extends Card {

	View v;
	TextView chrono;
	TextView replies;
	
	public MyReadingCard(String titlePlay, String description, String color,
			String titleColor, Boolean hasOverflow, Boolean isClickable) {
		super(titlePlay, description, color, titleColor, hasOverflow,
				isClickable);
	}

	@Override
	public View getCardContent(Context context) {
		v = LayoutInflater.from(context).inflate(R.layout.card_reading, null);
		
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
		replies = (TextView) v.findViewById(R.id.replies);
		
		onLoadView();
		
		return v;
	}
	
	public void onLoadView(){
		if (MainActivity.bPart4 == 0 && MainActivity.lPart4 != "justStarted"){
			chrono.setText(MainActivity.lPart4);
		}
		if (MainActivity.bPart4 == 1 && MainActivity.lPart4 == "justStarted"){
			chrono.setText(App.getContext().getString(R.string.stop));
		}
		
		replies.setText("");
		
		int arraySize = MainActivity.aReplies.size();
		for(int i = 0; i < arraySize; i++) {
			if (i == 0) {
				replies.append(App.getContext().getString(R.string.reading_puntos) + ": ");
				replies.append(MainActivity.aReplies.get(i));
				replies.append("\n");
			}
			else {
				replies.append(App.getContext().getString(R.string.reading_reply) + " " + i + ": ");
				replies.append(MainActivity.aReplies.get(i));
				replies.append("\n");
			}
		}
	}
}
