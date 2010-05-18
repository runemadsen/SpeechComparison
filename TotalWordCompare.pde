class TotalWordCompare implements Comparator 
{
   int compare(Object o1, Object o2) 
   {
      int num1 = ((TotalWord) o1).getHighestNum();
      int num2 = ((TotalWord) o2).getHighestNum();
      return num1 > num2 ? 0 : 1;
   }
}
