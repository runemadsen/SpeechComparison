class Speech 
{
   /* Properties
   ___________________________________________________________________ */
   
   ArrayList _words;
   String _fileName;
   Speeker _speeker;
   
   /* Speech
   ___________________________________________________________________ */	

   Speech(String name, String fileName, color legendColor, int id) 
   {
      _words = new ArrayList();
      _fileName = fileName;
      _speeker = new Speeker(name, legendColor, id);
      
      String[] lines = loadStrings(fileName);
     
      parse(lines);
   }
   
   /* Parse
   ___________________________________________________________________ */

   void parse( String[] lines ) 
   {
      for( int i = 0; i< lines.length; i++) 
      {
         String[] pieces = splitLine( lines[ i ] );
       	
         _words.add( new Word( pieces[0], pieces[1])); // String word, String rank
      }
   }
   
   /* Split line
   ___________________________________________________________________ */

   String[] splitLine( String line ) 
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

   void scrubQuotes( String[] array ) 
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
   
   ArrayList getWords()
   {
      return _words;  
   }
   
   Speeker getSpeeker()
   {
     return _speeker;  
   }

}

