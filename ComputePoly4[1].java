import java.awt.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
//1111
//2222
////asdfad
public class ComputePoly4 {
	//filter all the void character. 
	public static int isdigit(char c){
		if (c >= 48 && c <= 57)
			return 1;
		else {
			return 0;
		}
	}// if the current char is a number
	public static boolean voidFormula(String str){
		Pattern neg_p = Pattern.compile("[0123456789]"); 
		Matcher neg_m = neg_p.matcher(str);
		if (!neg_m.find()){
			System.out.println("该输入没有数字，不能计算"); 
			return true;
		}
		return false;
	}
	public static boolean formatError(String str){
		String newsc = str.replaceAll("\\s+", "");
		if (newsc.charAt(0) == '+' || newsc.charAt(0) == '-' ){
			newsc = newsc.substring(1);
		}
		newsc += "+";
	    String string = newsc;
	    Pattern neg_p = Pattern.compile("(\\{(\\(-?[0-9]+,[0-9]+\\),)*\\(-?[0-9]+,[0-9]+\\)\\}(\\+|-))");
		Matcher neg_m = neg_p.matcher(string);
		while(neg_m.find()){
		   newsc = newsc.replace(neg_m.group(), "");
		}
		if(newsc.length() != 0){
			System.out.println("格式错误");
			return true;
		}
		return false;
	}
	public static boolean illegalChar(String str){
		Pattern neg_p = Pattern.compile("[^ \\{\\}\\(\\)\\+-0123456789]"); 
		Matcher neg_m = neg_p.matcher(str);
		if (neg_m.find()){
			System.out.println("输入了不合法字符"); 
			return true;
		}
		return false;
	}
	public static boolean bracketsUnmatch(String str){
		char []origin = str.toCharArray();
		int i = 0;
		ArrayList<Character> brackets = new ArrayList<Character>();
		for(i = 0; i < str.length(); i++){
			if((brackets.size() == 0 && (origin[i] == '(' || origin[i] == '{' || origin[i] == ')' || origin[i] == '}')) || origin[i] == '(' || origin[i] == '{'){
				brackets.add(origin[i]);
			}
			else if (origin[i] == ')'){
				if ((Character)brackets.get(brackets.size() - 1) == '(')
					brackets.remove(brackets.size() - 1);
				else
					brackets.add(origin[i]);
			}
			else if (origin[i] == '}'){
				if ((Character)brackets.get(brackets.size() - 1) == '{')
					brackets.remove(brackets.size() - 1);
				else
					brackets.add(origin[i]);
			}//the match of the brackets
		}
		if (brackets.size() > 0) {
			System.out.println("括号不匹配");
			return true;
		}
		return false;
	}
	public static boolean minusSquare(String str){
		String newsc = str.replaceAll("\\s+", "");
		if (newsc.charAt(0) == '+' || newsc.charAt(0) == '-' ){
			newsc = newsc.substring(1);
		}
		newsc += "+";
	    String string = newsc;
	    Pattern neg_p = Pattern.compile("(\\{(\\(-?[0-9]+,[0-9]+\\),)*\\(-?[0-9]+,[0-9]+\\)\\}(\\+|-))");
		Matcher neg_m = neg_p.matcher(string);
		while(neg_m.find()){
		   newsc = newsc.replace(neg_m.group(), "");
		}
		if(newsc != null){
			Pattern p = Pattern.compile("(\\(-?[0-9]+,-[0-9]+\\))"); 
			Matcher m = p.matcher(newsc);
			if (m.find()){
				System.out.println("阶数为负");
				return true;
			}
		}
		return false;
	}
	public static boolean tooLong(String str){
		String newsc = str.replaceAll("\\s+", "");
		Pattern p = Pattern.compile("(0{0,}[1-9]{9,})"); 
		Pattern q = Pattern.compile("(,0{0,}[1-9]{7,}\\))"); 
		Matcher m = p.matcher(newsc);
		Matcher n = q.matcher(newsc);
		if (m.find() || n.find()){
			System.out.println("数字过长");
			return true;
		}
		return false;
	}
	public static void Compute(String str){
		int []formula = new int[1000000];
		//
		int i = 0;
		int nums_ver = 0, nums_hor = 0;
		char []origin = str.toCharArray();
		char operand = '+';
		int temp = 0, temp1 = 0, temp2 = 0;
		boolean isMinus = false;
		for (i = 0; i < str.length(); i++){
			if (origin[i] == '}'){
				nums_ver++;
			}
			else if ( ((origin[i] == '+' || origin[i] == '-') && (i == 0)) || (origin[i] == '+' && origin[i - 1] == '}' && origin[i + 1] == '{') 
					|| (origin[i] == '-' && origin[i - 1] == '}' && origin[i + 1] == '{')
					 ){
				operand = origin[i];
			}// let the operand in
			else{
				if (origin[i] == '-' && origin[i - 1] == '(' && isdigit(origin[i + 1]) != 0 ){
					isMinus = true;
				}
				if( isdigit(origin[i]) != 0 ){
					temp = 10 * temp + origin[i] - 48;
				}//if is digit
				if( origin[i] == ','){
					temp1 = temp;
					temp = 0;
					if (isMinus) {
						temp1 = -temp1;
						isMinus = false;
					};
					continue;
				}//the input of XiShu is finished, get one XiShu
				if( origin[i] == ')' ){
					temp2 = temp;
					temp = 0;
					if (operand == '+')
						formula[temp2] += temp1;
					else if(operand == '-')	
						formula[temp2] -= temp1;
					if (temp2 > nums_hor) 
						nums_hor = temp2;
					continue;
				}//the input of ZhiShu is finished, get one ZhiShu
			}
		}//main loop
		
		System.out.print("{");
		for (i = 0; i < nums_hor + 1; i++){
			if (nums_ver == 1) nums_ver = 0;//special occation
			if (formula[i] != 0){
				if (i != nums_hor ){
					System.out.print("(");
					System.out.print(formula[i]);
					System.out.print(",");
					System.out.print(i);
					System.out.print("),");
				}
				else{
					System.out.print("(");
					System.out.print(formula[i]);
					System.out.print(",");
					System.out.print(i);
					System.out.print(")");
				}
			}
		}
		System.out.print("}");
	}
	public static void main(String []args){
		//all the methods are specified here
		System.out.println("please enter: ");
		String str=new Scanner(System.in).nextLine();
		String newsc = str.replaceAll("\\s+", "");
		boolean error = formatError(newsc) | 
				illegalChar(newsc) | 
				bracketsUnmatch(newsc) | 
				minusSquare(newsc) | 
				tooLong(newsc) | 
				voidFormula(newsc);
		if (!error){
			Compute(newsc);
		}
	}
}
/*public static int ErrorFind(String str){
	if (illegalChar(str)){
		System.out.println("输入了不合法字符"); 
		return 1;
	}
	if (formatError(str)){
		if(bracketsUnmatch(str)){
			System.out.println("格式错误:括号不匹配");
			return 3;
		}
		System.out.println("格式错误");
		return 2;
	}
	if(minusSquare(str)){
		System.out.println("阶数为负");
		return 4;
	}
	if (tooLong(str)){
		System.out.println("数字过长");
		return 5;
	}
	return 0;
}//judge if the raw string is legal*/
/*
public static void main(String args[]){  
        byte[] buffer=new byte[512];  
        try {  
            System.out.print("请你输入: ");  
            System.in.read(buffer);//input your data, and ends with a "Return" key.  
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
        String str=new String(buffer);  
          
        System.out.println("what you input is : "+str);  
    } 
 */

/*Scanner sc=new Scanner(System.in).nextLine();
System.out.print("1输入字符：");
String m=sc.next();
System.out.println("你输入的是"+m);*/

/*BufferedReader bf=new BufferedReader(new InputStreamReader(System.in));
System.out.print("2输入字符: ");
String s=null;
try {
s=bf.readLine();
} catch (IOException e) { e.printStackTrace();}
System.out.println("你输入的是"+s);*/

/*Pattern pos_p = Pattern.compile("[0-9\\.]+");
Pattern neg_p = Pattern.compile("-[0-9\\.]+"); 
Matcher pos_m = pos_p.matcher(str);
Matcher neg_m = neg_p.matcher(str);
int i = 0;
String j = new String();
while(pos_m.find() || neg_m.find()){
   if (pos_m.find()) j = pos_m.group();
   else if (neg_m.find()) j = neg_m.group();
   i = Integer.parseInt(j);
   System.out.print(i+","); 
   }*/

/*for(i = 0; i < nums_hor + 1; i++){
	for (j = 0, formula[nums_ver][i] = formula[0][i]; j < nums_ver - 1; j++){
		operand.get(j);
		if ((Character)operand.get(j) == '+'){
			formula[nums_ver][i] += formula[j + 1][i];
		}
		else if ((Character)operand.get(j) == '-'){
			formula[nums_ver][i] -= formula[j + 1][i];
		}
	}
}//compute the formula*/
