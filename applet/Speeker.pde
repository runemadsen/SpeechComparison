class Speeker
{
   /* Properties
   ___________________________________________________________________ */
   
   private String _name;
   private color _legendColor;
   private int _wordCount = 0;
   private int _id = 0;
   private float _percent;
   
   /* Constructor
   ___________________________________________________________________ */
   
   Speeker(String name, color legendColor, int id)
   {
      _name = name;
      _legendColor = legendColor;  
      _id = id;
   }
   
   /* Add WordCount
   ___________________________________________________________________ */
   
   void addWordCount(int addNum)
   {
      _wordCount += addNum;
   }
   
   /* Getter / Setter
   ___________________________________________________________________ */
   
   String getName()
   {
      return _name;
   }
   
   color getLegendColor()
   {
      return _legendColor;
   }
   
   int getId()
   {
      return _id;
   }
   
   int getWordCount()
   {
      return _wordCount;  
   }
   
   void setPercent(float percent)
   {
      _percent = percent;
   }
   
   float getPercent()
   {
      return _percent;
   }
}
