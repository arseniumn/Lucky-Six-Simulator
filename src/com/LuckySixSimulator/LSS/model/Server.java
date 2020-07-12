package com.LuckySixSimulator.LSS.model;
import java.util.LinkedHashSet;
import java.util.concurrent.ThreadLocalRandom;

public class Server {
	private int[] draw_numbers;
	private double[] multipliers = {1,1,1,1,1,
									10000, 7500, 5000, 2500, 1000, 500,
									300, 200, 150, 100, 90, 80,
									70, 60, 50, 40, 30, 25,
									20, 15, 10, 9, 8, 7,
									6, 5, 4, 3, 2, 1};
	private final int MIN_NUMBER = 1;
	private final int MAX_NUMBER = 48;
	private final int MIN_NUMBER_CLOVER = 5;
	private final int NUMBER_OF_DRAW_NUMBERS = 35;
	private LinkedHashSet<Integer> hashSet = new LinkedHashSet<Integer>();
	private int[] clover_indices = new int[]{1,1};

	public Server() {
		draw_numbers = new int[NUMBER_OF_DRAW_NUMBERS];	
	}	

	public void generateNumbers() {
		hashSet.add(ThreadLocalRandom.current().nextInt(MIN_NUMBER, MAX_NUMBER+1));
		while(hashSet.size()<NUMBER_OF_DRAW_NUMBERS) {
			int temp_number = ThreadLocalRandom.current().nextInt(MIN_NUMBER, MAX_NUMBER+1);
			while(hashSet.contains(temp_number)) 
				temp_number = ThreadLocalRandom.current().nextInt(MIN_NUMBER, MAX_NUMBER+1);			
			hashSet.add(temp_number);
		}
		Integer[] numbers = hashSet.toArray(new Integer[hashSet.size()]);
		for(int i=0; i<numbers.length; i++) 
			draw_numbers[i] = numbers[i];		
	}

	public int checkForWinningTicket(Ticket ticket) {
		int matches = 0;
		for(int i : ticket.getTicketNumbers()) {
			if(hashSet.contains(i)) matches++;							
		}
		return matches;
	}
	
	public double checkForMultiplier(Ticket ticket) {
		double temp_multiplier = 0;
		boolean flag = true;
		for(int i=(draw_numbers.length-1); i>=5; i--) {
			if(flag) {
				for (Integer j : ticket.getTicketNumbers()) {
					if (draw_numbers[i] == j && flag) {
						temp_multiplier = multipliers[i];
						flag = false;
					}
				}
			}
		}
		return temp_multiplier;
	}

	public void findCloverIndices() {
		clover_indices[0] = ThreadLocalRandom.current().nextInt(MIN_NUMBER_CLOVER, NUMBER_OF_DRAW_NUMBERS);
		clover_indices[1] = ThreadLocalRandom.current().nextInt(MIN_NUMBER_CLOVER, NUMBER_OF_DRAW_NUMBERS);
		while(clover_indices[0]== clover_indices[1]) 
			clover_indices[1] = ThreadLocalRandom.current().nextInt(MIN_NUMBER_CLOVER, NUMBER_OF_DRAW_NUMBERS);		
	}

	public double[] checkForClover(Ticket ticket) {
		boolean first_clover = false, second_clover = false;
		int clover_1 = 1, clover_2 = 1;
		for(int j : ticket.getTicketNumbers()) {
			if(draw_numbers[clover_indices[0]] == j) first_clover = true;
			if(draw_numbers[clover_indices[1]] == j) second_clover = true;
		}
		if(first_clover && !second_clover) clover_1 = 2;
		else if(second_clover && !first_clover) clover_2 = 2;
		else if(second_clover && first_clover) clover_2 = 3;
		return new double[]{clover_1,clover_2};
	}
	
	public void clearSet() {hashSet.clear();}
	
	public int[] getNumbers() {return draw_numbers;}
	
}