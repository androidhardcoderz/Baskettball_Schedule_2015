package com.baskettballschedule2015.app;

/*
 * Helper class that contains all external fonts
 * location: /assets/fonts/
 * each method takes in a textview assigns the typeface created and 
 * returns that trextview
 */

import android.graphics.Typeface;
import android.widget.TextView;

public class Fonts {

	public Fonts() {
		// TODO Auto-generated constructor stub
	}

	// sets custom font from assets folder in project hierarchy
	public void setFont(TextView textView) {
		Typeface tf = Typeface.createFromAsset(textView.getContext()
				.getAssets(), "fonts/Hug Me Tight.ttf");

		textView.setTypeface(tf);

	}
	
	// sets custom font from assets folder in project hierarchy
		public void setFontKings(TextView textView) {
			Typeface tf = Typeface.createFromAsset(textView.getContext()
					.getAssets(), "fonts/Kingthings Sans.ttf");

			textView.setTypeface(tf);

		}

	// sets custom font from assets folder in project hierarchy
	public void setPointsFont(TextView textView) {
		Typeface tf = Typeface.createFromAsset(textView.getContext()
				.getAssets(), "fonts/cartoon_font.TTF");

		textView.setTypeface(tf);

	}
	
	// sets custom font from assets folder in project hierarchy
		public void setFontRoboto_BlackItalic(TextView textView) {
			Typeface tf = Typeface.createFromAsset(textView.getContext()
					.getAssets(), "fonts/Roboto-BlackItalic.ttf");

			textView.setTypeface(tf);

		}

	public void setCustomNotoSansTamilFont(TextView textView){
		Typeface tf = Typeface.createFromAsset(textView.getContext()
				.getAssets(), "fonts/NotoSansTamil-Regular.ttf");

		textView.setTypeface(tf);
	}

}
