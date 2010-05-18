class TotalWordFinder
{
   /* Properties
   ___________________________________________________________________ */
   
   private ArrayList _totalWords = new ArrayList();
   private ArrayList _speeches;
   private PFont _font;
   
   /* Find total from speeches
   ___________________________________________________________________ */

   ArrayList findTotal(ArrayList speeches, PFont font)
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

   boolean compareWord(Word theWord, int numSpeech)
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


