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
   
   String getWord()
   {
      return _word;  
   }
   
   int getRank()
   {
      return _rank;  
   }
}

