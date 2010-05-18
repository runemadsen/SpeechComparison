/* Import 
___________________________________________________________________ */

import processing.opengl.*;
import penner.easing.*;

/* Properties
___________________________________________________________________ */
 
int treshold = 9;

PFont font;
ArrayList speeches = new ArrayList();
ArrayList totalWords = new ArrayList();
ArrayList legends = new ArrayList();

String[] names = {
"Al Gore",
"Bill Clinton",
"George Bush",
"John F. Kennedy",
"Kathleen Blanco",
"Martin Luther King",
"Barack Obama",
"Theodor Roosevelt",
"Steve Jobs"
};

String[] files = {
"algore_nobelspeech.csv",
"clinton_farewelladdress.csv",
"georgebush_911speech.csv",
"johnfkennedy_inauguralspeech.csv",
"kathleenblanco_katrinajointsession.csv",
"martinlutherking_ihaveadream.csv",
"obama_inauguralspeech.csv",
"roosevelt_pearlhabor.csv",
"stevejobs_stanfordcommencement.csv"
};

color[] colors = { 
#403767, 
#5585ca,
#e5f2e2,
#7ac298,
#2f372c,
#51422c,
#f0852a,
#ffce56,
#ff4627,
#7c3433
};

/* Setup
___________________________________________________________________ */

void setup() 
{
   size(1000, 800);
   smooth();
   font = createFont("Helvetica-Bold", 12);

   loadSpeeches();

   findTotalWords();

   setupTotalWords();
   
   createLegends();
}

void loadSpeeches()
{
   for(int i = 0; i < files.length; i++)
   {
      speeches.add(new Speech(names[i], files[i], colors[i], i + 1));
   } 
}

/* Find Total Words
 ___________________________________________________________________ */

void findTotalWords()
{
   TotalWordFinder finder = new TotalWordFinder();
   totalWords = finder.findTotal(speeches, font);
   Collections.sort(totalWords, new TotalWordCompare()); 
}

/* Positon total words
 ___________________________________________________________________ */

void setupTotalWords()
{
   TotalWord totalWord;
   int xPos = 0;
   int yPos = height;
   int xSpacing = 10;
   int startDelay = 100;
   
   for(int i = 0; i < totalWords.size(); i++)
   {  
      totalWord = (TotalWord) totalWords.get(i);
      totalWord.setStartDelay(startDelay);
      totalWord.setDuration(300);
      totalWord.setPos(xPos, yPos);
      totalWord.reset();

      xPos += xSpacing;
      startDelay += 10;
   }
}

void showOnly(int pressedNum)
{
   // total words
   TotalWord totalWord;
   
   for(int i = 0; i < totalWords.size(); i++)
   {  
      totalWord = (TotalWord) totalWords.get(i);
      totalWord.showOnly(pressedNum);
   }
   
   // speekers
   SpeekerLegend legend;

   for(int i = 0; i < legends.size(); i++)
   {  
      legend = (SpeekerLegend) legends.get(i);
      legend.showOnly(pressedNum);
   }
}

void resetEasings()
{
   TotalWord totalWord;
   
   for(int i = 0; i < totalWords.size(); i++)
   {  
      totalWord = (TotalWord) totalWords.get(i);
      totalWord.resetEasing();
   }
}

/* Create Legends
___________________________________________________________________ */

void createLegends()
{
   Speech speech;
   SpeekerLegend legend;
   int xPos = 20;
   int yPos = 20;
   int ySpacing = 20;
   
   for(int i = 0; i < speeches.size(); i++)
   {
      speech = (Speech) speeches.get(i);
      legend = new SpeekerLegend(speech.getSpeeker(), font);
      legend.setPos(xPos, yPos);
      
      legends.add(legend);  
      
      yPos += ySpacing;
   }
}

/* Draw
___________________________________________________________________ */

void draw() 
{
   background(15);
   
   drawTotalWords();
   
   drawLegends();
}

/* Draw Total Words
___________________________________________________________________ */

void drawTotalWords()
{   
   TotalWord totalWord;

   for(int i = 0; i < totalWords.size(); i++)
   {  
      totalWord = (TotalWord) totalWords.get(i);

      if(totalWord.getHighestNum() > treshold)
      {
         totalWord.update();
         totalWord.display();
      }
      
      // just to test
      //if(i == 0)   break;
   }
}

/* Draw Legends
___________________________________________________________________ */

void drawLegends()
{
   SpeekerLegend legend;

   for(int i = 0; i < legends.size(); i++)
   {  
      legend = (SpeekerLegend) legends.get(i);
      legend.display();
   }
}

/* Key pressed
___________________________________________________________________ */

void keyPressed()
{   
   if(keyCode > 47 && keyCode < 58)
   {
      int pressedNum = Integer.parseInt(str(key));
      showOnly(pressedNum);
   }
   
   if(keyCode == 10)
   {
      resetEasings();
   }
}




