
// Copyright (CC-BY-SA) 2020 shannaX. All rights reserved. See http://github.com/sh2282000/

import java.time.*;
import java.util.*;
import java.io.*;

public class ToDo {
	public static final int MAXENTRIES = 10;
	public static String[][] todos = new String[MAXENTRIES][2];
	public static String listeAlleAufgaben() {
		String outputList = ""; 
		for (int i=0; i<MAXENTRIES && todos[i][0]!=null; i++)
		{
			outputList += "  "+(i+1)+".  " + todos[i][0] + '\n';
		}
		if(outputList == "") return "The list is empty.\n";
		else return outputList;
	}
	public static boolean neueAufgabe(String titel) {
		int i=0;
		while (i<MAXENTRIES && todos[i][0]!=null) i++;
		if(i<MAXENTRIES)
		{
			todos[i][0] = titel;
			todos[i][1] = "2020-12-23";
			return true;
		} else return false;
	}
	public static boolean loescheAufgabe(String titel){
		int i=0;
		while(i<MAXENTRIES && !titel.equals(todos[i][0])) i++;
		if(i<MAXENTRIES)
		{
			while(i<MAXENTRIES-1)
			{
				todos[i][0] = todos[i+1][0];
				todos[i][1] = todos[i+1][1];
				i++;
			}
			todos[MAXENTRIES-1][0] = null;
			todos[MAXENTRIES-1][1] = null;
			return true;
		} else return false;
	}
	public static boolean bearbeiteAufgabe(String alterTitel, String neuerTitel) {
		int i=0;
		while(i<MAXENTRIES && !alterTitel.equals(todos[i][0])) i++;
		if(i<MAXENTRIES)
		{
			todos[i][0] = neuerTitel;
			return true;
		} else return false;
	}
	public static boolean setzeDatum(String titel, int jahr, int monat, int tag){
		if(jahr>0 && monat<=12 && monat>0 && tag>0 && tag<=31)
		{
			int i=0;
			while(i<MAXENTRIES && !titel.equals(todos[i][0])) i++;
			if(i<MAXENTRIES)
			{
				todos[i][1] = String.format("%04d-%02d-%02d", jahr, monat, tag);
				return true;
			} 
		}
		return false;
	}
	public static String listeAlleAufgabenMitDatum() {
		String outputList = ""; 
		for (int i=0; i<MAXENTRIES && todos[i][0]!=null; i++)
		{
			outputList += "  "+(i+1)+".  " + todos[i][0]+", fällig am " + todos[i][1] + '\n';
		}
		if(outputList == "") return "The list is empty.\n";
		else return outputList;
	}
	public static String listTmp(String[][] todosTmp){
		String outputList = ""; 
		for (int i=0; i<MAXENTRIES && todosTmp[i][0]!=null; i++)
		{
			outputList += "  "+(i+1)+".  " + todosTmp[i][0]+", fällig am " + todosTmp[i][1] + '\n';
		}
		if(outputList == "") return "The list is empty.\n";
		else return outputList;
	}
	public static String[][] copyFromMainTodos(){
		String[][] todosTmp = new String[MAXENTRIES][2];
		for(int i=0; i<MAXENTRIES; i++)
		{
			todosTmp[i][0] = todos[i][0];
			todosTmp[i][1] = todos[i][1];
		}
		return todosTmp;
	}
	public static String nachTitelSortieren(boolean aufsteigend) {
		String[][] todosTmp = copyFromMainTodos();
		String swapName, swapDate;
		for(int i=0; i<MAXENTRIES && todosTmp[i][0]!=null; i++)
		{
			for(int j=i+1; j<MAXENTRIES && todosTmp[j][0]!=null; j++)
			{
				if((todosTmp[i][0].compareTo(todosTmp[j][0]) > 0 && aufsteigend) || (todosTmp[i][0].compareTo(todosTmp[j][0]) < 0 && !aufsteigend))
				{
					swapName = todosTmp[i][0];
					swapDate = todosTmp[i][1];
					todosTmp[i][0] = todosTmp[j][0];
					todosTmp[i][1] = todosTmp[j][1];
					todosTmp[j][0] = swapName;
					todosTmp[j][1] = swapDate;
				}
			}
		}
		return listTmp(todosTmp);
	}
	public static boolean saveToFile() {
		try {
			FileWriter file = new FileWriter("savedToDos.txt");
			for (int i=0; i<MAXENTRIES && todos[i][0]!=null; i++)
			{
				file.write(todos[i][0]+"@"+todos[i][1]+'\n');
			}
			file.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	public static boolean readFile() {
		try {
			File file = new File("savedToDos.txt");
			Scanner reader = new Scanner(file);
			int i=0;
			while (reader.hasNextLine())
			{
				Scanner line = new Scanner(reader.nextLine());
				line.useDelimiter("@");
				todos[i][0] = line.next();	
				todos[i][1] = line.next();
				i++;
			}
			System.out.println("num of lines: "+i);
			reader.close();
			return true;
		} catch (IOException e) {
			System.out.println("EROOR OCCURED");
			e.printStackTrace();
			return false;
		}
	}
	public static boolean dateComparator(int[][] date){
		if(date[0][0]>date[0][1])
			return true;
		else if(date[0][0]==date[0][1])
		{
			if(date[1][0]>date[1][1])
				return true;
			else if(date[1][0]==date[1][1])
			{
				if(date[2][0]>date[2][1])
					return true;
			}
		}
		return false;
	}
	public static String nachDatumSortieren(boolean aufsteigend) {
		String[][] todosTmp = copyFromMainTodos();
		String swapName, swapDate;
		int[][] date = new int[3][2];
		for(int i=0; i<MAXENTRIES && todosTmp[i][0]!=null; i++)
		{
			for(int j=i+1; j<MAXENTRIES && todosTmp[j][0]!=null; j++)
			{
				Scanner scannerDate = new Scanner(todosTmp[i][1]);
				scannerDate.useDelimiter("-");
				for (int x=0; x<3; x++)
					date[x][0] = scannerDate.nextInt();

				scannerDate = new Scanner(todosTmp[j][1]);
				scannerDate.useDelimiter("-");
				for (int x=0; x<3; x++)
					date[x][1] = scannerDate.nextInt();

				if(!(dateComparator(date) ^ aufsteigend))
				{
					swapName = todosTmp[i][0];
					swapDate = todosTmp[i][1];
					todosTmp[i][0] = todosTmp[j][0];
					todosTmp[i][1] = todosTmp[j][1];
					todosTmp[j][0] = swapName;
					todosTmp[j][1] = swapDate;
				}
			}
		}
		return listTmp(todosTmp);
	}
	public static void main(String[] args) {
		int selection;
		String inputTitel, oldTitel;
		String inputDateDirectives[] = {"year in the following format YYYY",
										"month in the following format MM",
										"day in the following format DD"};
		String menuElement[] = {"To-Dos anzeigen",
								"Eintrag hinzufügen",
								"Eintrag löschen",
								"Programm beenden",
								"Eintrag ändern",
								"Eintrag setze Datum",
								"To-Dos anzeigen mit Datum",
								"To-Dos sortieren"};
		String menuSortElement[] = {"nach Titel aufsteigend",
								"nach Titel absteigend",
								"nach Datum aufsteigend",
								"nach Datum absteigend"};
		
		System.out.println(readFile()?"\n\nToDos read from file successfully. Congratulations!":"\n\nWe wasn't able to find your previous ToDos. :(");
		
		int inputDate[] = new int[3];
		Scanner scanner = new Scanner(System.in); 
		do{
			System.out.println("\033[2J  Willkommen in Ihrer To-Do-Liste, was möchten Sie tun?\n"); //only supprted if terminal accet ANSI escape codes
			for (int i = 0; i < 8; i++)
				System.out.println("\n   ["+(i+1)+"]   "+menuElement[i]+"\n\n");
	    	try {
	    		scanner = new Scanner(System.in);
	    		scanner.useDelimiter("\n");
				selection = scanner.nextInt();
			} catch (InputMismatchException e) {
				System.out.print("Invalid input. ");
				selection = -1;
			}
			switch (selection) {
				case 1:
					System.out.print("Aufgaben auf der Liste:\n" + listeAlleAufgaben());
					break;
				case 2:
					System.out.print("Enter the title of the entry you want to add: ");
					inputTitel = scanner.next();
					System.out.println((neueAufgabe(inputTitel)?"Adding "+inputTitel+" was successfull!":"There's no space in the list anymore (maximum space: "+MAXENTRIES+")!"));
					break;
				case 3:
					System.out.print("Enter the title of the entry you want to delete: ");
					inputTitel = scanner.next();
					System.out.println((loescheAufgabe(inputTitel)?"Deleting "+inputTitel+" was successfull!": (inputTitel + " wasn't found on the list.")));
					break;
				case 4:
					System.out.println("Good Bye!");
					break;
				case 5:
					System.out.print("Enter the title of the entry you want to modify: ");
					oldTitel = scanner.next();					
					System.out.print("\nFor what you want to modify it to: ");
					inputTitel = scanner.next();
					bearbeiteAufgabe(oldTitel, inputTitel);
					break;
				case 6:
					System.out.print("Enter the title of the entry you want to set the due date: ");
					inputTitel = scanner.next();			
					System.out.println("\nFor what you want to modify it to? ");
					for (int i=0; i<3;i++)
					{
						try {
							scanner = new Scanner(System.in);
	    					scanner.useDelimiter("\n");
	    					System.out.print("\nEnter the "+inputDateDirectives[i]+" : ");
							inputDate[i] = scanner.nextInt();
						} catch (InputMismatchException e) {
							System.out.print("\nInvalid input. Enter your input again: ");
							i --;
						}
					}
					System.out.println((setzeDatum(inputTitel, inputDate[0], inputDate[1], inputDate[2])?"Modifying due date of "+inputTitel+" was successfull!": (inputTitel + " wasn't found on the list or the date entered wasn't valid.")));
					break;
				case 7:
					System.out.print("Aufgaben auf der Liste:\n" + listeAlleAufgabenMitDatum());
					break;
				case 8:
					System.out.println("\033[2J");
					for (int i = 0; i < 4; i++)
						System.out.println("\n   ["+(i+1)+"]   "+menuSortElement[i]+"\n\n");
			    	try {
			    		scanner = new Scanner(System.in);
			    		scanner.useDelimiter("\n");
						selection = scanner.nextInt();
					} catch (InputMismatchException e) {
						System.out.print("Invalid input. ");
						selection = -1;
					}
					switch(selection) {
						case 1:
							System.out.print("Aufgaben auf der Liste "+menuSortElement[0]+":\n" + nachTitelSortieren(true));
							break;
						case 2:
							System.out.print("Aufgaben auf der Liste "+menuSortElement[1]+":\n" + nachTitelSortieren(false));
							break;
						case 3:
							System.out.print("Aufgaben auf der Liste "+menuSortElement[2]+":\n" + nachDatumSortieren(true));
							break;
						case 4:
							System.out.print("Aufgaben auf der Liste "+menuSortElement[3]+":\n" + nachDatumSortieren(false));
							selection = 0; //I didn't want to use another variable here
							break;
						default:
							System.out.println("Please enter a number corresponding to menus choices. You came back to the main menu.");
							break;
					}
					break;

				default:
					System.out.println("Please enter a number corresponding to menus choices.");
					break;
			}
			System.out.println(saveToFile()?"\n\nToDos successfully auto-saved. Congratulations!":"\n\nWe wasn't able to save your ToDos in a file, an error occurred. :(");
			try
			{
			    Thread.sleep(5000);
			}
			catch(InterruptedException ex)
			{
			    Thread.currentThread().interrupt();
			}
		}while(selection!=4);
		scanner.close();
		System.exit(0); 
	}
}
