//App class which runs the application

package application;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class App extends Application 
{
    
    @Override
    public void start(Stage primaryStage) 
    {
       
        primaryStage.setTitle("Team Management Application");
        
       
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 700, 350, Color.WHITE);
        
        MenuBar menubar = new MenuBar();
        root.setTop(menubar);
        
        //Manager menu
        Menu fileMenu = new Menu("Manager");
        MenuItem item1 =new MenuItem("Team");
        MenuItem item2 =new MenuItem("Player");
        MenuItem item3 =new MenuItem("Exit");
        item3.setOnAction(e -> Platform.exit());
        
        fileMenu.getItems().addAll(item1, item2, new SeparatorMenuItem(), item3);
                                      
        // Team Table
        TableView<Team> table = new TableView<Team>();
        TableColumn<Team, String> teamColumn = new TableColumn<Team, String>("Team");
        teamColumn.setCellValueFactory(new PropertyValueFactory<Team, String>("teamName"));
		
		TableColumn<Team, Integer> scoreColumn = new TableColumn<Team, Integer>("Score");
		scoreColumn.setCellValueFactory(new PropertyValueFactory<Team, Integer>("score"));
        
        table.getColumns().add(teamColumn);
        table.getColumns().add(scoreColumn);
        
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY); // resizes the table to fit window
        
        root.setCenter(table);
        
        table.setVisible(false);
        item1.setOnAction(e -> table.setVisible(true));    // shows table when menu item for table is selected           
        
        
        //reads data from text file to table 
        java.io.File file = new java.io.File("TeamList.txt");
        {   
        
        	Scanner input = null;
        	{
	        	//try catch block
        		try {
	        		input = new Scanner(file);
	        	} catch (FileNotFoundException e1) {
	        		// TODO Auto-generated catch block
	        		e1.printStackTrace();
	        	}
	            
	        	List<Integer> sortList = new ArrayList<Integer>();   //list to be sorted 
	        	Set<Integer> set = new HashSet<Integer>();  // set to store the score
	            BST tree = new BST(); //instantiates a new binary search tree 
	            Hashtable<Integer, String> hashTable = new Hashtable<>(); //creates a new hashtable
	        	
	        	
	        	while (input.hasNext()) {        // iterates through team list
	            	String name = input.next();   // sets team name in list to variable name
	            	String str = input.next();    // sets score in list to variable str        	            	            	            	
	            	int score = Integer.parseInt(str);  // converts score from string into integer
	            	table.getItems().add(new Team (name, score)); // adds team names and scores from team list text file to table
	            	
	            	set.add(score);  // adds score to score set
	            	
	            	hashTable.put(score, name);
	            	
	            	tree.insert(score); // adds score into binary search tree
	            	
	            	sortList.add(score); //adds score to score list 
	            	
	        
	            }
	 
	            input.close();
	            
	            System.out.println(hashTable);
	        	
	            tree.inorder(); // prints the inorder traversal of the binary search tree; basically replaces the insertion sort below as it is more efficient
	            
	            //insertionSort(sortList);  //The time complexity of insertion sort is O(n^2)
        	}
        }
        


        
        
        
        
            
        
        //View Menu
        Menu viewMenu = new Menu("View");
        CheckMenuItem check1 = new CheckMenuItem("Editor");
        check1.setSelected(true);
        CheckMenuItem check2 = new CheckMenuItem("Line Number");
        check2.setSelected(true);
        viewMenu.getItems().addAll(check1, check2, new SeparatorMenuItem());
        
        Menu toolbar = new Menu(" ");
        toolbar.getItems().addAll(
                new CheckMenuItem("File"),
                new CheckMenuItem(" "),
                new CheckMenuItem(" ")
        );
        viewMenu.getItems().add(toolbar);
        
        //Mode menu
        Menu mode = new Menu("Mode");
        ToggleGroup tGroup = new ToggleGroup();
        RadioMenuItem mode1 = new RadioMenuItem("Desktop");
        mode1.setToggleGroup(tGroup);
        RadioMenuItem mode2 = new RadioMenuItem("Tablet");
        mode2.setToggleGroup(tGroup);
        mode2.setSelected(true);
        
        mode.getItems().addAll(mode1, mode2);
        
        //root.getChildren().add(table);
        
        menubar.getMenus().addAll(fileMenu, viewMenu, mode);
        
        primaryStage.setScene(scene);
        primaryStage.show();
   }
    
    
    

    public static void main(String[] args) 
    {
        launch(args);
    }
    

    //insertion sort method
	public static void insertionSort(List<Integer> list) 
	{
		   for (int i = 1; i < list.size(); i++) 
		   {
			   int currentElement = list.get(i);
			   int k;
			   for (k = i - 1; (k >= 0 && (list.get(k).compareTo(currentElement)) == 1); k--) 
			   {

				   list.set(k + 1, list.get(k));
						   
			   }

			   list.set(k + 1, currentElement);
			   
		   }
		   System.out.println(list);	
		  
	}
}

