package com.LuckySixSimulator.LSS.main;
import java.io.FileNotFoundException;

import com.LuckySixSimulator.LSS.simulator.LuckySixSimulator;

public class Main {
	
	public static void main(String[] args) {
		LuckySixSimulator lucky_picks = new LuckySixSimulator();
		try {
			lucky_picks.run();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}				
	}
}
