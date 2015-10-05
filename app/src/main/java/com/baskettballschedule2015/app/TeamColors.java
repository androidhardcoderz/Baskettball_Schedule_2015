package com.baskettballschedule2015.app;

import java.util.ArrayList;

public class TeamColors {

	ArrayList<Integer> colors = new ArrayList<Integer>();

	public ArrayList<Integer> getColors() {
		return colors;
	}

	public Integer getColors(int i) {
		return colors.get(i);
	}

	public TeamColors() {
		setColors();
	}

	private void setColors() {
		
		colors.add(R.color.hawks);
		colors.add(R.color.celtics);
		colors.add(R.color.nets);
		colors.add(R.color.hornets);
		colors.add(R.color.bulls);
		colors.add(R.color.cavs);
		colors.add(R.color.mavericks);
		colors.add(R.color.nuggets);
		colors.add(R.color.pistons);
		colors.add(R.color.warriors);
		colors.add(R.color.rockets);
		colors.add(R.color.pacers);
		colors.add(R.color.cippers);
		colors.add(R.color.lakers);
		colors.add(R.color.grizzles);
		colors.add(R.color.heat);
		colors.add(R.color.bucks);
		colors.add(R.color.timberwolves);
		colors.add(R.color.pelicans);
		colors.add(R.color.knicks);
		colors.add(R.color.thunder);
		colors.add(R.color.magic);
		colors.add(R.color.sixers);
		colors.add(R.color.suns);
		colors.add(R.color.blazers);
		colors.add(R.color.kings);
		colors.add(R.color.spurs);
		colors.add(R.color.raptors);
		colors.add(R.color.jazz);
		colors.add(R.color.wizards);

	}

}
