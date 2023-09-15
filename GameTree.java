import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.*;
import java.io.*;

/**
 * A model for the game of 20 questions
 *
 * @author Rick Mercer
 */
public class GameTree
{
   private String fileName;
   private TreeNode root;
   private TreeNode current;

	/**
	 * Constructor needed to create the game.
	 *
	 * @param fileName
	 *          this is the name of the file we need to import the game questions
	 *          and answers from.
	 */
   private class TreeNode 
   {
      public String str;
      public TreeNode yes, no;
      public TreeNode(String str) 
      {
         this.str = str;
         no = yes = null;
      }
   
      @Override
      public String toString() 
      { 
         return "" + this.str;
      }
   }
    
   public GameTree(String fileName)
   {
     
   	//TODO
      this.fileName = fileName;
      Scanner file = new Scanner(fileName);
      try
      {
         file = new Scanner(new File(fileName));
         root = new TreeNode(file.nextLine());
         current = root;
         cHelper(current, file);
      }
      catch (FileNotFoundException E)
      {
         System.out.println("error");
      }
   }
   
   public void cHelper(TreeNode t, Scanner s)
   {
      if (t.str.contains("?"))
      {
         t.yes = new TreeNode(s.nextLine());
         cHelper(t.yes, s);
         t.no = new TreeNode(s.nextLine());
         cHelper(t.no, s);
      }
      
   }
   
   

	/*
	 * Add a new question and answer to the currentNode. If the current node has
	 * the answer chicken, theGame.add("Does it swim?", "goose"); should change
	 * that node like this:
	 */
	// -----------Feathers?-----------------Feathers?------
	// -------------/----\------------------/-------\------
	// ------- chicken  horse-----Does it swim?-----horse--
	// -----------------------------/------\---------------
	// --------------------------goose--chicken-----------
	/**
	 * @param newQ
	 *          The question to add where the old answer was.
	 * @param newA
	 *          The new Yes answer for the new question.
	 */
   public void add(String newQ, String newA)
   {
   	//TODO
      current.yes = new TreeNode(newA);
      current.no = new TreeNode(current.str);
      current.str = newQ;
   }

	/**
	 * True if getCurrent() returns an answer rather than a question.
	 *
	 * @return False if the current node is an internal node rather than an answer
	 *         at a leaf.
	 */
   public boolean foundAnswer()
   {
   	//TODO
      
      if(getCurrent().contains("?") == false)
         {
            return true;
         }
   
      return false; //replace
   }

	/**
	 * Return the data for the current node, which could be a question or an
	 * answer.  Current will change based on the users progress through the game.
	 *
	 * @return The current question or answer.
	 */
   public String getCurrent()
   {
   	//TODO
   
      return current.str; //replace
   }

	/**
	 * Ask the game to update the current node by going left for Choice.yes or
	 * right for Choice.no Example code: theGame.playerSelected(Choice.Yes);
	 *
	 * @param yesOrNo
	 */
   public void playerSelected(Choice yesOrNo)
   {
   	//TODO
      
      if(yesOrNo == Choice.Yes)
      {
         current = current.yes;
      }
      if(yesOrNo == Choice.No)
      {
         current = current.no;
      }
   }

	/**
	 * Begin a game at the root of the tree. getCurrent should return the question
	 * at the root of this GameTree.
	 */
   public void reStart()
   {
   	//TODO
      current = root;
   }

   @Override
   public String toString()
   {
   	//TODO
      String answer = "";
      String d = "";
      return sHelper(root, d);
   }
   
   public String sHelper(TreeNode ques, String depth)
      {
         if(ques == null)
            {
               return "";
            }
         String answer = "";
         answer += sHelper(ques.no, depth + "- ");
         answer += depth + " " + ques.str;
         answer += "\n";
         answer += sHelper(ques.yes, depth + "- ");
         return answer;
      }

	/**
	 * Overwrite the old file for this gameTree with the current state that may
	 * have new questions added since the game started.
	 *
	 */
   public void saveGame()
   {
   	//TODO
       String outputFileName = fileName;
       PrintWriter diskFile = null;
       try 
       {
         diskFile = new PrintWriter(new File(outputFileName));
       }
       catch (IOException io) 
       {
         System.out.println("Could not create file: " + outputFileName);
       }
       saveGameHelper(diskFile, root);
       diskFile.close();
   }
   
   public void saveGameHelper(PrintWriter p, TreeNode n)
   {
      if (n == null)
      {
         return;
      }
      p.println(n.str);
      saveGameHelper(p, n.yes);
      saveGameHelper(p, n.no);
   }
}
