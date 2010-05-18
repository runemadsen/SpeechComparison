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
   private float _easePercent = 0.0;
   private float _easeDegree = 0.0;
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
   
   void reset()
   {
      //sortSpeekers();
      
      placeArc();
      
      calcPercentages();
   }
   
   /* Update
   ___________________________________________________________________ */
   
   void update()
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
   
   void sortSpeekers()
   {
      if(!_sorted)
      {
         Arrays.sort(_speekers, new SpeekerCompare()); 
         
         _sorted = true;  
      }
   }
   
   /* Place arc
   ___________________________________________________________________ */
   
   void placeArc()
   {
      _diameter = (_xCenter - _xPos) * 3;
      _diameterReal = _xCenter - _xPos;
      _xCenter += _pushX;
   }
   
   /* Calculate percentages
   ___________________________________________________________________ */
   
   void calcPercentages()
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
         _speekers[i].setPercent( float(_speekers[i].getWordCount()) / highValue);
      }
   }
   
   /* Display function
   ___________________________________________________________________ */
   
   void display()
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
   
   String getWord()
   {
      return _word;  
   }
   
   /* Increment
   ___________________________________________________________________ */
   
   void addWordCount(Speeker speeker, int addNum)
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
   
   int getHighestNum()
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
   
   void setStartDelay(int startDelay)
   {
      _startDelay = startDelay;
   }
   
   void setDuration(int duration)
   {
      _duration = duration;
   }
   
   void showOnly(int pressedNum)
   {
      _showOnly = pressedNum;
   }
   
   void resetEasing()
   {
      _time = 0;  
      //_curDelay = 0;
   }
}

