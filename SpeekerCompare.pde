class SpeekerCompare implements Comparator 
{
   int compare(Object o1, Object o2) 
   {
      int num1 = ((Speeker) o1).getWordCount();
      int num2 = ((Speeker) o2).getWordCount();
      return num1 > num2 ? 1 : 0; // switch these to make biggest be first
   }
}
