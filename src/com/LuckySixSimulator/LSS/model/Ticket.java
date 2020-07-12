package com.LuckySixSimulator.LSS.model;
import java.util.HashSet;
import java.util.concurrent.ThreadLocalRandom;

public class Ticket implements Cloneable{

	private Integer[] aux_numbers;
	private final int MIN_NUMBER = 1;
	private final int MAX_NUMBER = 48;
	private final int NUMBER_OF_TICKET_NUMBERS = 6;
	private HashSet<Integer> hashSet = new HashSet<Integer>();
	private int[] ticket_numbers;
	private double BET;

	public Ticket(double aBET) {
		aux_numbers = new Integer[NUMBER_OF_TICKET_NUMBERS];		
		ticket_numbers = new int[NUMBER_OF_TICKET_NUMBERS];	
		this.BET = aBET;
	}
	
	public Ticket(int[] numbers2, double aBET) {
		this.ticket_numbers = new int[NUMBER_OF_TICKET_NUMBERS];		
		for(int i=0; i<numbers2.length; i++)
			this.ticket_numbers[i] = numbers2[i];	
		this.BET = aBET;
	}

	public void generateNumbers() {
		hashSet.add(ThreadLocalRandom.current().nextInt(MIN_NUMBER, MAX_NUMBER+1));
		while(hashSet.size()<NUMBER_OF_TICKET_NUMBERS) {
			int temp_number = ThreadLocalRandom.current().nextInt(MIN_NUMBER, MAX_NUMBER+1);
			while(hashSet.contains(temp_number)) 
				temp_number = ThreadLocalRandom.current().nextInt(MIN_NUMBER, MAX_NUMBER+1);			
			hashSet.add(temp_number);
		}
		aux_numbers = hashSet.toArray(new Integer[hashSet.size()]);
		for(int i=0; i<aux_numbers.length; i++) 
			ticket_numbers[i] = aux_numbers[i];		
	}

	public int[] getTicketNumbers() {return ticket_numbers;}

	public void setTicketNumbers(int[] ticket_numbers) {this.ticket_numbers = ticket_numbers;}
	
	public double getBET() {return this.BET;}

}
