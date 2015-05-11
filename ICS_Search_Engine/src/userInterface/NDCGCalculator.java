package userInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Normalized discounted cumulative gain (NDCG) of a list of ranked items.
 * See http://recsyswiki.com/wiki/Discounted_Cumulative_Gain
 * @version 2.03
 */
public class NDCGCalculator 
{
  public static double compute(List<String> ranked_items, List<String> correct_items, List<String> ignore_items) 
  { 
    if (ignore_items == null)
      ignore_items = new ArrayList<String>();

    double rankedDCG = DCG(ranked_items, correct_items);
    double oracleDCG = DCG(correct_items, correct_items);
    
    double nDCG = rankedDCG/oracleDCG;
    return nDCG;
  }

  private static double DCG(List<String> ranked_items, List<String> correct_items) {
	  
	  Map<String, Integer> ref = new HashMap<String, Integer>();
	  ref.put(correct_items.get(0),3);
	  ref.put(correct_items.get(1),3);
	  ref.put(correct_items.get(2),2);
	  ref.put(correct_items.get(3),2); 
	  ref.put(correct_items.get(4),1);
	  double result = 0.0;
      for (int i = 0; i < 5; i++) {
          double rel = relevanceCalculator(ranked_items.get(i), ref);
          if (i+1 > 1) {
        	  rel /= log2Base(i+1);
          }
          result += rel;
      }
      return result;
  }
  
  private static double relevanceCalculator(String url, Map<String, Integer> ref ) {
	  Integer index = ref.get(url);
	  if(index!=null)
		  return index;
	  else
		  return 0;
  }
  
  private static double log2Base(double value) {
      return Math.log(value) / Math.log(2);
  }
}
