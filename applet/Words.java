import processing.core.*; 
import processing.xml.*; 

import processing.opengl.*; 
import penner.easing.*; 

import java.applet.*; 
import java.awt.*; 
import java.awt.image.*; 
import java.awt.event.*; 
import java.io.*; 
import java.net.*; 
import java.text.*; 
import java.util.*; 
import java.util.zip.*; 
import java.util.regex.*; 

public class Words extends PApplet {

/* Import 
___________________________________________________________________ */




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

int[] colors = { 
0xff403767, 
0xff5585ca,
0xffe5f2e2,
0xff7ac298,
0xff2f372c,
0xff51422c,
0xfff0852a,
0xffffce56,
0xffff4627,
0xff7c3433
};

/* Setup
___________________________________________________________________ */

public void setup() 
{
   size(1000, 800);
   smooth();
   font = createFont("Helvetica-Bold", 12);

   loadSpeeches();

   findTotalWords();

   setupTotalWords();
   
   createLegends();
}

public void loadSpeeches()
{
   for(int i = 0; i < files.length; i++)
   {
      speeches.add(new Speech(names[i], files[i], colors[i], i + 1));
   } 
}

/* Find Total Words
 ___________________________________________________________________ */

public void findTotalWords()
{
   TotalWordFinder finder = new TotalWordFinder();
   totalWords = finder.findTotal(speeches, font);
   Collections.sort(totalWords, new TotalWordCompare()); 
}

/* Positon total words
 ___________________________________________________________________ */

public void setupTotalWords()
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

public void showOnly(int pressedNum)
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

public void resetEasings()
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

public void createLegends()
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

public void draw() 
{
   background(15);
   
   drawTotalWords();
   
   drawLegends();
}

/* Draw Total Words
___________________________________________________________________ */

public void drawTotalWords()
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

public void drawLegends()
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

public void keyPressed()
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




class DisplayObject
{
   /* Properties
    ________________________________________________ */

   protected float _xPos = 0;
   protected float _yPos = 0;
   protected float _zPos = 0;

   protected float _xRot = 0;
   protected float _yRot = 0;
   protected float _zRot = 0;

   protected float _curYRot = 0;
   protected float _curXRot = 0;
   protected float _curZRot = 0;

   /* Constructor 1
    ________________________________________________ */

   DisplayObject()
   {
      _xPos = width / 2;
      _yPos = height / 2;
      _zPos = 0;
   }

   /* Constructor 1
    ________________________________________________ */

   DisplayObject(float xPos,float yPos, float zPos)
   {
      _xPos = xPos;
      _yPos = yPos;
      _zPos = zPos;
   }

   /* Constructor 2
    ________________________________________________ */

   DisplayObject(float xPos,float yPos, float zPos, float xRot, float yRot, float zRot)
   {
      _xPos = xPos;
      _yPos = yPos;
      _zPos = zPos;

      _xRot = xRot;
      _yRot = yRot;
      _zRot = zRot;
   }

   /* Abstract
    ________________________________________________ */

   public void update()
   {
      println("Update must be overriden in subclass");
   }
   
   public void display()
   {
      println("Display must be overriden in subclass");
   }

   /* Methods
    ________________________________________________ */

   protected void locateRotation()
   {
      rotateY(radians(_curYRot)); 
      _curYRot += _yRot;

      rotateX(radians(_curXRot)); 
      _curXRot += _xRot; 

      rotateZ(radians(_curZRot));
      _curZRot += _zRot;
   }

   protected void locatePosition()
   {
      translate(_xPos, _yPos, _zPos);
   }

   /* Getter / Setter
    ________________________________________________ */

   public void setPos(float xPos, float yPos)
   {
      _xPos = xPos;
      _yPos = yPos;  
   }
   
   public void setPos(float xPos, float yPos, float zPos)
   {
      _xPos = xPos;
      _yPos = yPos;
      _zPos = zPos;  
   }
   
   public void setXPos(float xPos)
   {
      _xPos = xPos;  
   }

   public float getXPos()
   {
      return _xPos;  
   }

   public void setYPos(float yPos)
   {
      _yPos = yPos;  
   }

   public float getYPos()
   {
      return _yPos;  
   }

   public void setZPos(float zPos)
   {
      _zPos = zPos;  
   }

   public float getZPos()
   {
      return _zPos;  
   }

   public void setXRot(float xRot)
   {
      _xRot = xRot;  
   }

   public float getXRot()
   {
      return _xRot;  
   }

   public void setYRot(float yRot)
   {
      _yRot = yRot;  
   }

   public float getYRot()
   {
      return _yRot;  
   }

   public void setZRot(float zRot)
   {
      _zRot = zRot;  
   }

   public float getZRot()
   {
      return _zRot;  
   }

   public void setCurXRot(float curXRot)
   {
      _curXRot = curXRot;  
   }

   public float getCurXRot()
   {
      return _curXRot;  
   }

   public void setCurYRot(float curYRot)
   {
      _curYRot = curYRot;  
   }

   public float getCurYRot()
   {
      return _curYRot;  
   }

   public void setCurZRot(float curZRot)
   {
      _curZRot = curZRot;  
   }

   public float getCurZRot()
   {
      return _curZRot;  
   }
}

class Speech 
{
   /* Properties
   ___________________________________________________________________ */
   
   ArrayList _words;
   String _fileName;
   Speeker _speeker;
   
   /* Speech
   ___________________________________________________________________ */	

   Speech(String name, String fileName, int legendColor, int id) 
   {
      _words = new ArrayList();
      _fileName = fileName;
      _speeker = new Speeker(name, legendColor, id);
      
      String[] lines = loadStrings(fileName);
     
      parse(lines);
   }
   
   /* Parse
   ___________________________________________________________________ */

   public void parse( String[] lines ) 
   {
      for( int i = 0; i< lines.length; i++) 
      {
         String[] pieces = splitLine( lines[ i ] );
       	
         _words.add( new Word( pieces[0], pieces[1])); // String word, String rank
      }
   }
   
   /* Split line
   ___________________________________________________________________ */

   public String[] splitLine( String line ) 
   {
      char[] c = line.toCharArray();
      ArrayList pieces = new ArrayList();
      int prev = 0;
      boolean insideQuote = false;

      for( int i = 0; i < c.length; i ++ ) 
      {
         if( c[ i ] == ',' ) 
         {
            if( !insideQuote ) 
            {
               String s = new String( c, prev, i - prev ).trim();
               pieces.add( s );
               prev = i + 1;
            }
         } 
         else if( c[ i ] == '\"' ) 
         {
            insideQuote = !insideQuote;
         }
      }

      if( prev != c.length ) 
      {
         String s = new String( c, prev, c.length - prev ).trim();
         pieces.add( s );
      }

      String[] outgoing = new String[ pieces.size() ];
      pieces.toArray( outgoing );
      scrubQuotes( outgoing );
      return outgoing;
   }
   
   /* Scrub Quotes
   ___________________________________________________________________ */

   public void scrubQuotes( String[] array ) 
   {
      for( int i = 0; i < array.length; i ++) {
         if( array[ i ].length() > 2 ) {
            if( array[ i ].startsWith( "\"" ) && array[ i ].endsWith( "\"" )) {
               array[ i ] = array[ i ].substring( 1, array[ i ].length() - 1 );
            };
         };

         array[ i ] = array[ i ].replaceAll( "\"\"", "\"" );
      }
   }
   
   /* Get words
   ___________________________________________________________________ */
   
   public ArrayList getWords()
   {
      return _words;  
   }
   
   public Speeker getSpeeker()
   {
     return _speeker;  
   }

}

class Speeker
{
   /* Properties
   ___________________________________________________________________ */
   
   private String _name;
   private int _legendColor;
   private int _wordCount = 0;
   private int _id = 0;
   private float _percent;
   
   /* Constructor
   ___________________________________________________________________ */
   
   Speeker(String name, int legendColor, int id)
   {
      _name = name;
      _legendColor = legendColor;  
      _id = id;
   }
   
   /* Add WordCount
   ___________________________________________________________________ */
   
   public void addWordCount(int addNum)
   {
      _wordCount += addNum;
   }
   
   /* Getter / Setter
   ___________________________________________________________________ */
   
   public String getName()
   {
      return _name;
   }
   
   public int getLegendColor()
   {
      return _legendColor;
   }
   
   public int getId()
   {
      return _id;
   }
   
   public int getWordCount()
   {
      return _wordCount;  
   }
   
   public void setPercent(float percent)
   {
      _percent = percent;
   }
   
   public float getPercent()
   {
      return _percent;
   }
}
class SpeekerCompare implements Comparator 
{
   public int compare(Object o1, Object o2) 
   {
      int num1 = ((Speeker) o1).getWordCount();
      int num2 = ((Speeker) o2).getWordCount();
      return num1 > num2 ? 1 : 0; // switch these to make biggest be first
   }
}
class SpeekerLegend extends DisplayObject
{
   /* Properties
   ___________________________________________________________________ */ 
  
   private Speeker _speeker;
   private int _boxWidth = 40;
   private int _boxHeight = 20;
   private PFont _font;
   private int _textMarginTop = 15;
   private int _showOnly = 0;
  
   /* Constructor
   ___________________________________________________________________ */ 
   
   SpeekerLegend(Speeker speeker, PFont font)
   {
      _speeker = speeker;
      _font = font;
   }
   
   /* Display
   ___________________________________________________________________ */
   
   public void display()
   {
      if(_showOnly == 0 || _showOnly == _speeker.getId())
      {
         fill(_speeker.getLegendColor());
      }
      else
      {
         fill(_speeker.getLegendColor(), 15);
      }
      
      noStroke();
      //rect(_xPos, _yPos, _boxWidth, _boxHeight);
      
      textFont(font);
      //text(_speeker.getName(), _xPos, _yPos + _boxHeight + _textMarginTop);
      text(_speeker.getName(), _xPos, _yPos);
   }
   
   public void showOnly(int pressedNum)
   {
      _showOnly = pressedNum;
   }
}
class TotalWord extends DisplayObject
{
   /* Properties
   ___________________________________________________________________ */
   
   private String _word;
   private Speeker[] _speekers;
   private int _multiplier = 10;
   private boolean _sorted = false;
   private int _xCenter;
   private int _yCenter;
   private float _diameter;
   private float _diameterReal;
   private float _turnDegree = 180;
   private float _startDegree;
   private float _endDegree;
   private PFont _font;
   private float _pushX = 380;
   private int _wordMarginLeft = 10;
   private int _wordMarginTop = 5;
   private int _startDelay;
   private int _curDelay;
   private int _time = 0;
   private int _duration = 100;
   private float _easePercent = 0.0f;
   private float _easeDegree = 0.0f;
   private float _totalDegrees = 90;
   private int _showOnly = 0;
   
   /* Constructor
   ___________________________________________________________________ */
   
   TotalWord(Speeker speeker, Word word, int numSpeeches, PFont font)
   {
      _word = word.getWord();
      _speekers = new Speeker[1];
      _font = font;
      
      // copy speaker to create new count
      _speekers[0] = new Speeker(speeker.getName(), speeker.getLegendColor(), speeker.getId());
      _speekers[0].addWordCount(word.getRank());
      
      _xCenter = width / 2;
      _yCenter = height;
   }
   
   /* Update
   ___________________________________________________________________ */
   
   public void reset()
   {
      //sortSpeekers();
      
      placeArc();
      
      calcPercentages();
   }
   
   /* Update
   ___________________________________________________________________ */
   
   public void update()
   {
      if(_time <= _duration)   
      {
         if(_curDelay >= _startDelay)
         {
            _time++;
            
            _easePercent = Quart.easeInOut(_time, 0, 1, _duration);
            _easeDegree = _easePercent * _totalDegrees;
         }
         else
         {
            _curDelay++;
         }
      }
   }
   
   /* Sort speekers
   ___________________________________________________________________ */
   
   public void sortSpeekers()
   {
      if(!_sorted)
      {
         Arrays.sort(_speekers, new SpeekerCompare()); 
         
         _sorted = true;  
      }
   }
   
   /* Place arc
   ___________________________________________________________________ */
   
   public void placeArc()
   {
      _diameter = (_xCenter - _xPos) * 3;
      _diameterReal = _xCenter - _xPos;
      _xCenter += _pushX;
   }
   
   /* Calculate percentages
   ___________________________________________________________________ */
   
   public void calcPercentages()
   {
      // get highest number
      float highValue = 0;
      
      for(int i = 0; i < _speekers.length; i++)
      {
         highValue += _speekers[i].getWordCount();
      }
      
      // set percentages
      for(int i = 0; i < _speekers.length; i++)
      {
         _speekers[i].setPercent( PApplet.parseFloat(_speekers[i].getWordCount()) / highValue);
      }
   }
   
   /* Display function
   ___________________________________________________________________ */
   
   public void display()
   {
      _startDegree = 0;
      _endDegree = 0;
      
      for(int i = 0; i < _speekers.length; i++)
      {
         strokeWeight(8);
         strokeCap(SQUARE);
         noFill();
         
         if(_showOnly == 0 || _showOnly == _speekers[i].getId())
         {    
            stroke(_speekers[i].getLegendColor());
         }
         else
         {
            stroke(_speekers[i].getLegendColor(), 15);
         }
            
         _startDegree = _endDegree;
         _endDegree = _startDegree + (_totalDegrees * _speekers[i].getPercent());
         
         if(_endDegree > _easeDegree)
         {
            arc(_xCenter, _yCenter, _diameter, _diameter, radians(_turnDegree + _startDegree), radians(_turnDegree + _easeDegree));
         }
         else
         {
            arc(_xCenter, _yCenter, _diameter, _diameter, radians(_turnDegree + _startDegree), radians(_turnDegree + _endDegree));
         } 
      }
      
      fill(255);
      textFont(_font);
      text(_word, _xCenter + _wordMarginLeft, _yCenter - (_diameter / 2) + _wordMarginTop); 
   }
   
   /* Get word
   ___________________________________________________________________ */
   
   public String getWord()
   {
      return _word;  
   }
   
   /* Increment
   ___________________________________________________________________ */
   
   public void addWordCount(Speeker speeker, int addNum)
   {      
      for(int i = 0; i < _speekers.length; i++)
      {
         if(_speekers[i].getId() == speeker.getId())
         {
            _speekers[i].addWordCount(addNum);
            return;
         }  
      }
      
      // if no return, create new speaker
      _speekers = (Speeker[]) append(_speekers, new Speeker(speeker.getName(), speeker.getLegendColor(), speeker.getId()));
      _speekers[_speekers.length - 1].addWordCount(addNum);
       
   }
   
   /* Get Highest Num
   ___________________________________________________________________ */
   
   public int getHighestNum()
   {
      int highestVal = 0;
      
      for(int i = 0; i < _speekers.length; i++)
      {
         if(_speekers[i].getWordCount() > highestVal)
         {
            highestVal = _speekers[i].getWordCount();
         }
      }
      
      return highestVal;
   }
   
   /* Set start delay
   ___________________________________________________________________ */
   
   public void setStartDelay(int startDelay)
   {
      _startDelay = startDelay;
   }
   
   public void setDuration(int duration)
   {
      _duration = duration;
   }
   
   public void showOnly(int pressedNum)
   {
      _showOnly = pressedNum;
   }
   
   public void resetEasing()
   {
      _time = 0;  
      //_curDelay = 0;
   }
}

class TotalWordCompare implements Comparator 
{
   public int compare(Object o1, Object o2) 
   {
      int num1 = ((TotalWord) o1).getHighestNum();
      int num2 = ((TotalWord) o2).getHighestNum();
      return num1 > num2 ? 0 : 1;
   }
}
class TotalWordFinder
{
   /* Properties
   ___________________________________________________________________ */
   
   private ArrayList _totalWords = new ArrayList();
   private ArrayList _speeches;
   private PFont _font;
   
   /* Find total from speeches
   ___________________________________________________________________ */

   public ArrayList findTotal(ArrayList speeches, PFont font)
   { 
      _speeches = speeches;
      _font = font;
      
      // Loop through speeches and words
      for(int i = 0; i < speeches.size(); i++)
      {
         ArrayList words = ((Speech) speeches.get(i)).getWords(); 

         // loop words
         for(int t = 0; t < words.size(); t++)
         {
            Word word = (Word) words.get(t);

            compareWord(word, i);
            
         }
      }
      
      return _totalWords;
   }

   /* Compare single word
    ___________________________________________________________________ */

   public boolean compareWord(Word theWord, int numSpeech)
   {
      TotalWord totalWord;
      Speeker speeker = ((Speech) _speeches.get(numSpeech)).getSpeeker();

      // loop total words
      for(int i = 0; i < _totalWords.size(); i++)
      {
         totalWord = (TotalWord) _totalWords.get(i);

         if(theWord.getWord().equals(totalWord.getWord()))
         {
            totalWord.addWordCount(speeker, theWord.getRank());

            return false;
         }
      }

      // make a new if no return 
      totalWord = new TotalWord(speeker, theWord, speeches.size(), _font);
      _totalWords.add(totalWord);
      
      return true;
   }
}


class Word  extends DisplayObject
{
   /* Constructor
   ___________________________________________________________________ */
   
   String _word;
   int _rank;
   
   /* Constructor
   ___________________________________________________________________ */

   Word(String word, String rank) 
   {
      setPos(width / 10, height / 2);
      
      _word = word;
      _rank = Integer.parseInt(rank);
   }
   
   /* Get word
   ___________________________________________________________________ */
   
   public String getWord()
   {
      return _word;  
   }
   
   public int getRank()
   {
      return _rank;  
   }
}


   static public void main(String args[]) {
      PApplet.main(new String[] { "--present", "--bgcolor=#666666", "--stop-color=#cccccc", "Words" });
   }
}
