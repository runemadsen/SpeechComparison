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
   
   void display()
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
   
   void showOnly(int pressedNum)
   {
      _showOnly = pressedNum;
   }
}
