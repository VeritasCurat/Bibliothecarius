import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import opennlp.tools.postag.POSModel; 
import opennlp.tools.postag.POSSample; 
import opennlp.tools.postag.POSTaggerME; 
import opennlp.tools.tokenize.WhitespaceTokenizer;  

public class POS_Tagger_en { 
  
   public static void main(String args[]) throws Exception{ 
    
      //Loading Parts of speech-maxent model       
	  InputStream inputStream = new FileInputStream("C:\\Users\\Devin\\OneDrive\\Cloud\\Programme\\Projekte\\java\\DrThesaurus\\src\\source\\en-pos-maxent.bin");
      POSModel model = new POSModel(inputStream); 
       
      //Instantiating POSTaggerME class 
      POSTaggerME tagger = new POSTaggerME(model); 
       
      String sentence = "John likes the sun";
       
      //Tokenizing the sentence using WhitespaceTokenizer class  
      WhitespaceTokenizer whitespaceTokenizer= WhitespaceTokenizer.INSTANCE; 
      String[] tokens = whitespaceTokenizer.tokenize(sentence); 
       
      //Generating tags 
      String[] tags = tagger.tag(tokens);
      
      //Instantiating the POSSample class 
      POSSample sample = new POSSample(tokens, tags); 
      System.out.println(sample.toString()); 
   
   }
   
   
	   
   public static String getTaggedSentence(String eingabe) throws IOException{
		 //Loading Parts of speech-maxent model       
			  InputStream inputStream = new FileInputStream("sources/en-pos-maxent.bin");
		      POSModel model = new POSModel(inputStream); 
		       
		      //Instantiating POSTaggerME class 
		      POSTaggerME tagger = new POSTaggerME(model); 
		       
		      String sentence = eingabe; 
		       
		      //Tokenizing the sentence using WhitespaceTokenizer class  
		      WhitespaceTokenizer whitespaceTokenizer= WhitespaceTokenizer.INSTANCE; 
		      String[] tokens = whitespaceTokenizer.tokenize(sentence); 
		       
		      //Generating tags 
		      String[] tags = tagger.tag(tokens);
		      
		      //Instantiating the POSSample class 
		      POSSample sample = new POSSample(tokens, tags); 
		
		     return sample.toString();  
   }
   
   public static String getFUllTag(String eingabe){
	   return "";
   }
}  