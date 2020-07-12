package com.LuckySixSimulator.LSS.simulator;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;

import com.LuckySixSimulator.LSS.model.Server;
import com.LuckySixSimulator.LSS.model.Ticket;

public class LuckySixSimulator {
	
	private static final long serialVersionUID = 1L;
	private final int NUMBER_OF_DRAWS = 144; // 144 draws represent the number of draws for 12 hours, you can change it
	private final int NUMBER_OF_TICKETS = 100000; // 100K tickets for simulatiom, you can change it
	private final int MAX_PAYMENT = 10000; // If the payment for a ticket occurs > 10k then he will win 10k only, you can change it
	private double MoneyIn = 0, MoneyOut = 0, RTP = 0;
	private ArrayList<Ticket> all_tickets = new ArrayList<Ticket>();
	
	public void run() throws FileNotFoundException {
		DecimalFormat df = new DecimalFormat("####0.000");

		//Server creation
		Server server = new Server();
		double[] clover_multipliers = new double[]{1,1};

		//For all draws
		for(int d=0; d<NUMBER_OF_DRAWS; d++){
			
			//Find clover indices
			server.findCloverIndices();

			//Server generates random numbers
			server.generateNumbers();
			
			for(int i=0; i<NUMBER_OF_TICKETS; i++) {					
				// Add in MoneyIn the bet of the ticket
				MoneyIn += generateTickets();				
			}
			
			//Check for winning ticket and add prize from ticket to MoneyOut
			MoneyOut += getTotalWinFromTickets(server, clover_multipliers);			
			
			//Clear server set to generate new numbers
			server.clearSet();
			
			// Clear all stored tickets from last draw
			all_tickets.clear();
		}

		//Calculate RTP
		RTP = (MoneyOut/MoneyIn);

		System.out.println("General Return To Player (RTP)"+df.format(RTP));
	}

	private double getTotalWinFromTickets(Server server, double[] clover_multipliers) {
		double payment = 0, sum = 0;
		//Check all tickets
		for(Ticket ticket : all_tickets) {
			//How many matches are there of server and the current ticket
			int matched_numbers = server.checkForWinningTicket(ticket);

			//If it is 6 out of 6 (winning ticket)
			if (matched_numbers == 6) {

				//Check for the multiplier that has to be applied on winning ticket
				double multiplier = server.checkForMultiplier(ticket);

				//Apply clover multipliers
				clover_multipliers = server.checkForClover(ticket);

				//Get the prize
				payment = multiplier * clover_multipliers[0] * clover_multipliers[1] * ticket.getBET();

				//If prize > MAX_PAYMENT --> prize = MAX_PAYMENT (10.000)
				if (payment > MAX_PAYMENT) payment = MAX_PAYMENT;

				//Add the prize to sum
				sum += payment;
			}
		}
		return sum;
	}

	private double generateTickets() {
		//Random bet for each ticket in range [1,20]
		double BET = new Random().nextInt(20) + 1;

		//Ticket Creation
		Ticket ticket = new Ticket(BET);

		//Generate numbers for current ticket
		ticket.generateNumbers();

		//Save the ticket for next trials
		all_tickets.add(ticket);

		//Add in MoneyIn
		return BET;
	}
}
