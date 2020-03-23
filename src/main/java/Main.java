import org.apache.commons.io.IOUtils;

import javax.print.attribute.HashAttributeSet;
import javax.xml.transform.dom.DOMLocator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class Main {

    private String nameFollowedByItem = "[Nn][Aa][Mm][Ee]\\p{Punct}\\w+\\p{Punct}[Pp][Rr][Ii][Cc][Ee][:]\\d*.\\d*[;]";
    private ArrayList<char[]> unique = new ArrayList<char[]>();

    public String readRawDataToString() throws Exception{
        ClassLoader classLoader = getClass().getClassLoader();
        String result = IOUtils.toString(classLoader.getResourceAsStream("RawData.txt"));
        return result;
    }
    public void arrange(){

        LinkedHashMap<String , ArrayList<Double>> uniqueSet  = new LinkedHashMap<String , ArrayList<Double>>();

        for(char[] element : unique){
            String name = getNAme(element);
            Double price  = getPRice(element);

            if(uniqueSet.containsKey(name)){

                ArrayList<Double> newArray = new ArrayList<Double>();
                for(Double elemnt  : uniqueSet.get(name)){
                    newArray.add(elemnt);
                }
                newArray.add(price);
                uniqueSet.put(name , newArray);
            }else{
                ArrayList<Double> firstArray = new ArrayList<Double>();
                firstArray.add(price);
                uniqueSet.put(name , firstArray);
            }
        }

        for(String element : uniqueSet.keySet()){

            System.out.println("name:      " + element + "         " + "seen: " + uniqueSet.get(element).size() + " times");
            System.out.println("===============" +    "          " + "=============");

            ArrayList<Double> ans = uniqueSet.get(element);
            LinkedHashMap<Double , Integer> ans2 = getUnique(ans);
            int pos = 0;
            for(Double elemen : ans2.keySet()){
                System.out.println("Price:     " + elemen + "          " + "seen: " + ans2.get(elemen) );
                pos++;
                if(pos != ans2.keySet().size()){
                    System.out.println("---------------" +     "          " + "-------------");
                }

            }
            System.out.print("\n");

        }

        System.out.println("\n" + "Total Valid Matches = " + unique.size());
    }
    public LinkedHashMap<Double , Integer>  getUnique(ArrayList<Double> array){

        LinkedHashMap<Double , Integer> unique = new LinkedHashMap<Double, Integer>();
        for(Double element  : array){

            if(unique.containsKey(element)){
                unique.put(element , unique.get(element) + 1);
            }else{
                unique.put(element , 1);
            }
        }
        return unique;
    }
    public String getNAme( char[] element){

        int pos = 5;
        String answer = "";
        while(element[pos] != ';'){
            String letter = "";
            letter += element[pos];

            if(pos == 5){
                answer += letter.toUpperCase();
            }else{
                if(letter.equals("0")){
                    letter = "o";
                }
                answer += letter.toLowerCase();
            }
            pos++;
        }
        return answer;
    }
    public Double getPRice(char[] element){

        int pos  =  element.length - 5;
        Double answer = 0.00;
        String doubAsString = "";
        while (element[pos] != ';'){
            doubAsString += element[pos];
            pos++;
        }
        answer = Double.parseDouble(doubAsString);
        return answer;
    }

    public Integer findSequence() throws Exception {
        Pattern pattern = Pattern.compile(nameFollowedByItem);
        Matcher matcher  = pattern.matcher(readRawDataToString());
        Integer count  = 0;
        while (matcher.find()){
            String answer =  matcher.group();
            char[] str = answer.toCharArray();
            unique.add(str);
            count ++;
        }
        return count;
    }

    public static void main(String[] args) throws Exception{

        Main main = new Main();

        String original = main.readRawDataToString();
        System.out.println(original + "\n");

        main.findSequence();
        main.arrange();

    }
}
