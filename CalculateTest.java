import java.util.*;
import java.math.*;

/**
 *	自定义一个异常类
 */
class AuctionException extends Exception{
	private static final long serialVersionUID = 1L;
	public AuctionException(){}
	public AuctionException(String msg){
		super(msg);
	}
}

// 以BigDecimal为基础定义一个Arith工具类
class Arith{
	//默认除法运算精度，，默认为小数点后10位
	private static final int DEF_DIV_SCALE = 17;
	private Arith(){
	}
	
	/**
	 * 提供精确的加法运算
	 * @param num1 被加数
	 * @param num2 加数
	 * @return 两个参数的和
	 * @throws AuctionException
	 */
	public static double add(double num1, double num2) throws AuctionException{
			double result = 0.0;
			BigDecimal operator1 = BigDecimal.valueOf(num1);
			BigDecimal operator2 = BigDecimal.valueOf(num2);
			try{
				result = operator1.add(operator2).doubleValue();
			}catch (ArithmeticException exception){
				throw new AuctionException("算数异常");
			}
			return result;
	}
	
	/**
	 * 提供精确的减法运算
	 * @param num1 减数
	 * @param num2 被减数
	 * @return 两个参数的差
	 * @throws AuctionException
	 */
	public static double sub(double num1, double num2) throws AuctionException{
		double result = 0.0;
		BigDecimal operator1 = BigDecimal.valueOf(num1);
		BigDecimal operator2 = BigDecimal.valueOf(num2);
		try{
			result = operator2.subtract(operator1).doubleValue();
		}catch(ArithmeticException exception){
			throw new AuctionException("算数异常");
		}
		return result;
	}
	
	/**
	 * 提供精确的乘法运算
	 * @param num1 被乘数
	 * @param num2 乘数
	 * @return 两个参数的积
	 * @throws AuctionException
	 */
	public static double mul(double num1, double num2) throws AuctionException{
		double result = 0.0;
		BigDecimal operator1 = BigDecimal.valueOf(num1);
		BigDecimal operator2 = BigDecimal.valueOf(num2);
		try{
			result = operator1.multiply(operator2).doubleValue();
		}catch(ArithmeticException exception){
			throw new AuctionException("算数异常");
		}
		return result;
	}
	
	/**
	 * 提供精确的除法
	 * @param num1 除数
	 * @param num2 被除数
	 * @return 两个参数的商
	 * @throws AuctionException
	 */
	public static double div(double num1, double num2) throws AuctionException{
		double result = 0.0;
		//如果除数为0，抛出异常
		if ((new Double(num1).compareTo(new Double("0.0"))) == 0){
			throw new AuctionException("除数为0");
		}
		
		BigDecimal operator1 = BigDecimal.valueOf(num1);
		BigDecimal operator2 = BigDecimal.valueOf(num2);
		try{
			//默认的除法运算精度为：DEF_DIV_SCALE，采用四舍五入的小数保留模式
			result = operator2.divide(operator1, DEF_DIV_SCALE, BigDecimal.ROUND_HALF_UP).doubleValue();
		}catch(ArithmeticException exception){
			throw new AuctionException("算数异常");
		}
		return result;
	}
	
	
	/**
	 * 模除
	 * @param num1 模除数
 	 * @param num2 被模除数
	 * @return 两个参数的模除
	 * @throws AuctionException
	 */
	public static double rem(double num1, double num2) throws AuctionException{
		double result = 0.0;
		if ((new Double(num1).compareTo(new Double("0.0"))) == 0){
			throw new AuctionException("模除数为0");
		}
		BigDecimal operator1 = BigDecimal.valueOf(num1);
		BigDecimal operator2 = BigDecimal.valueOf(num2);
		try{
			result = operator2.remainder(operator1).doubleValue();
		}catch(ArithmeticException exception){
			throw new AuctionException("算数异常");
		}
		return result;
	}
}

class Calculate{
	//计算式字符串
	private String Expression = "";
	//最终结果字符串
	private String resultFinalExpression = "";
	//对多参数问题的处理，参数一
	private double parameter_value1 = 0.0;   
	//参数二
	private double parameter_value2 = 0.0;   
	
	/**
	 * 构造方法
	 * @param Expre
	 */
	public Calculate(String str)  
	{
		//将所有的大写字母转化为小写字母
		Expression = str.toLowerCase(); 
	}

	/**
	 * 得到最终结果
	 * @return
	 */
	String Getresult(){
		return resultFinalExpression;
	}
	
	
	//判断空格的合法性，第一部合法性判断
		boolean JudgeSpace() { 
			String replace = Expression;
			Expression = "######" + Expression + "######";
			for (int i = 0; i < Expression.length(); i ++) {
				//对出现空格的地方，前后字符进行解析，判断空格的存在是否合法
				if (Character.isWhitespace(Expression.charAt(i)) && i != 0 && (i + 1) <= Expression.length()){
				
					if (Character.isDigit(Expression.charAt(i - 1)) &&
						Character.isDigit(Expression.charAt(i + 1))){
							//对“数字——数字的处理”
						System.out.println("数字中出现空格，error！");
						return false;
					} else if (Character.isLowerCase(Expression.charAt(i - 1)) &&
							Character.isLowerCase(Expression.charAt(i + 1))) {
							//对“字母——字母”的处理
						System.out.println("字母中出现空格，error！");
						return false;
					} else if (Character.isLowerCase(Expression.charAt(i - 1)) &&
						Character.isDigit(Expression.charAt(i + 1))) {
							//对“字母——数字”的处理
						System.out.println("字母和数字中间出现空格, error！");
						return false;
					} else if (Character.isLowerCase(Expression.charAt(i - 1))&&
							(Expression.charAt(i + 1) == '(' || Expression.charAt(i + 1) == ')')) {
							//对“字母——（”的处理
						System.out.println("函数名和括弧之间出现空格,error!");
						return false;
					} else if (Character.isLowerCase(Expression.charAt(i-1)) && (Expression.charAt(i + 1) == '-')){
							//对“字母——负号”的处理
						System.out.println("字母和负号之间出现空格，error！");
						return false;
					}
				}
			}
			Expression = replace;
			//System.out.println(Expression);
			//当所有空格都合法时，返回真
			return true;
		}

		//判断字符的合法性,第二步合法性判断，其中包括操作符的多次应用合法性以及数字的合法性
		boolean JudgeChar() {
			Expression = Expression.replaceAll("\\s*","");         //去除表达式中所有空格
			int i = 0;
			StringBuilder expression = new StringBuilder(Expression);
			expression.append("##########");
			while( i < expression.length()) {
				//判断第一个字符是否合法，如果第一个字符为'*'、'/'、'^'、'%'、']'、')'时，返回false
				if (i == 0 && (Expression.charAt(i) == '*' || Expression.charAt(i) == '/' ||
						Expression.charAt(i) == '^' || Expression.charAt(i) == '%' ||
						Expression.charAt(i) == ']'|| Expression.charAt(i)== ')' || Expression.charAt(i) == ',')){
					System.out.println("第一个字符非法，error！");
					return false;
				}
			
				//判断最后一个字符是否合法，如果最后一个一个字符为'('、'['、'*'、'/'、'^'、'%'、'+'、'-'时，返回false
				if (i == (Expression.length() - 1)&& (Character.isLowerCase(Expression.charAt(i)) ||
							Expression.charAt(i) == '(' ||Expression.charAt(i) == '['||
							Expression.charAt(i) == '*' || Expression.charAt(i) == '/' ||
						Expression.charAt(i) == '^' || Expression.charAt(i) == '%' || 
						Expression.charAt(i) == '+' || Expression.charAt(i) == '-' || Expression.charAt(i) == ',')){
					System.out.println("最后一个字符非法, error!");
					return false;
				}

				//当第i个元素走到原来字符串的最后时，跳出循环
				if (i == Expression.length()){
					//System.out.println("跳出循环");
					break;
				}

				else {
					//判断操作符的合法性
					if (expression.charAt(i) == '+' || expression.charAt(i) == '-' || 
						expression.charAt(i) == '*' || expression.charAt(i) == '/' ||
						expression.charAt(i) == '%' || expression.charAt(i) == ',' || expression.charAt(i) == '^') {  
						//当第i个字符是'+'、'-'、'*'、'/'、'%'、',','^'时
						
						if (expression.charAt(i + 1) == '*') {   
							//如果第i + 1个字符是'*',返回false
							
							System.out.println("操作符非法, error！");
							return false;

						} else if (expression.charAt(i + 1) == '/') {   
							//如果第i + 1个字符是'/',返回false
							
							System.out.println("操作符非法, error！");
							return false;

						} else if (expression.charAt(i + 1) == '^') {  
							//如果第i + 1个字符是'^',返回false
							
							System.out.println("操作符非法, error！");
							return false;

						} else if (expression.charAt(i + 1) == '%') {  
							//如果第i + 1个字符是'%',返回false
							
							System.out.println("操作符非法, error！");
							return false;

						} else if (expression.charAt(i + 1) == ')' || expression.charAt(i + 1) == ']'){   
							//如果第i + 1个字符是')',返回false
							//对操作符）的问题
							
							System.out.println("操作符非法, error！");
							return false;
						}
					}

					//出现-+操作的数
					if (expression.charAt(i) == '-' && expression.charAt(i + 1) == '+'&&
							(((i - 1) < 0) || (!Character.isDigit(expression.charAt(i - 1))))){
						System.out.println("操作符错误！");
						return false;
					}

					//出现两个以上++或--的操作运算符
					if (((expression.charAt(i) == '+' && expression.charAt(i + 1) == '+') ||
						(expression.charAt(i) == '+' && expression.charAt(i + 1) == '+')) &&
							(expression.charAt(i + 2) == '+' || expression.charAt(i) == '-')){
						System.out.println("操作符输入错误！");
						return false;
					}
					//如果第i个字符是'*'、'/'、'%'、'^'
					if (expression.charAt(i) == '*' || expression.charAt(i) == '/' || 
						expression.charAt(i) == '%' || expression.charAt(i) == '^' || expression.charAt(i) == ',') {
							if (expression.charAt(i - 1) == '(' || expression.charAt(i - 1) == '['){     
								//如果第i - 1 个字符是'(', 返回false
								//对于（操作符的处理
								System.out.println("操作符非法, error！");
								return false;
							}
						}

					//判断函数名是否合法

					if (Character.isLowerCase(expression.charAt(i)))

					{

						//substring(begin, end)函数为截取begin,到end 之间的字符串

						//c1.compaseTo(c2)函数是用来比较c1,c2字符串的大小 

						if (expression.charAt(i) == 's') {

							if (expression.indexOf("sin(", i) != -1) {

								//如果字符串是合法的函数名sin,i要向后移动3位

								i += 3;
								continue;
							} else if (expression.indexOf("sinh(", i) != -1 ||

									expression.indexOf("sqrt(", i) != -1 || expression.indexOf("sum([", i) != -1) {

								//如果字符串是合法的函数名sinh和sqrt，i要向后移动4位

								i += 4;

								continue;

							} else if (expression.indexOf("stdev([", i) != -1) {

							
								//如果字符串是合法的函数名stdev，i要向后移动6位
								i += 6;

								continue;

							} else if (expression.indexOf("stdevp([", i) != -1) {
								//如果字符传是合法的函数名stdevp，i要向后移动7位
								i += 7;

								continue;

							} else{    //以上条件都不满足的情况，说明函数名错误，返回false
								System.out.println("函数名错误, error！");
								return false;
							}
					
						} else if (expression.charAt(i) == 'a') {     //判断以a开头的函数名是否合法

							if (expression.indexOf("avg([", i) != -1) {

								//如果函数名是avg时, i 要向后移动4位

								i += 4;

								continue;

							} else if (expression.indexOf("arcos(", i) != -1) {

								i += 5;

								continue;

							} else if (expression.indexOf("arcsin(", i) != -1 ||
									expression.indexOf("arctan(") != -1) {

								//函数名是“arcsin” 或者是"arctan",i要向后移动6位

								i += 6;

								continue;

							} else {
								System.out.println("函数名错误， error");
								return false;
							}

						} else if (expression.charAt(i) == 'c') {

							if (expression.indexOf("cos(", i) != -1) {
								//如果函数名是“cos”，i要向后移动3位
								i += 3;

								continue;

							} else if (expression.indexOf("cosh(", i) != -1) {
								//如果函数名是“cosh”,i要向后移动4位
								i += 4;

								continue;

							} else if (expression.indexOf("cuberoot(", i) != -1) {
								//如果函数名是“cuberoot”,i要先后移动8位
								i += 8;

								continue;

							} else {
								System.out.println("函数名错误， error");
								return false;
							}

						} else if (expression.charAt(i) == 't') {

							if (expression.indexOf("tan(") != -1) {
								//如果函数名是“tan”，i向后移动3位
								i += 3;

								continue;

							} else if (expression.indexOf("tanh(", i) != -1) {

								//如果函数名是“tanh”，i向后移动4位
								i += 4;

								continue;

							} else {
								System.out.println("函数名错误， error");
								return false;
							}

						} else if (expression.charAt(i) == 'l') { //函数名以l 开头的

							if (Expression.indexOf("ln(", i) != -1){

								//如果函数名是“ln”，i向后移动2位
								i += 2;

								continue;

							} else if (expression.indexOf("log(", i) != -1) {

								//如果函数名是“log”，i向后移动3位
								i += 3;

								continue;

							} else if (expression.indexOf("log10(", i) != -1) {

								//如果函数名是“log10”，i向后移动5位
								i += 5;

								continue;

							} else {
								System.out.println("函数名错误， error");
								return false;
							}
						
						} else if (expression.charAt(i) == 'e') {

							//e是指数而并非函数名的字符
							if (Character.isDigit(expression.charAt(i+1))){
								i++;
								continue;
							} else if (expression.indexOf("exp(", i) != -1) {
						
								//如果函数名是“exp”，i向后移动3位
								i += 3;

								continue;

							} else {
								System.out.println("函数名错误， error");
								return false;
							}

						} else if  (expression.charAt(i) == 'p') {

							if (expression.indexOf("pow(", i) != -1) {

								//如果函数名是“pow”，i向后移动3位
								i += 3;

								continue;

							}else {
								System.out.println("函数名错误， error");
								return false;
							}
								
						} else if  (expression.charAt(i) == 'm') {

							if (expression.indexOf("mod(", i) != -1) {
						
								//如果函数名是“mod”，i向后移动3位
								i += 3;
								continue;
							} else {
								System.out.println("函数名错误， error");
								return false;
							}

						} else if  (expression.charAt(i) == 'f') {
							if (expression.indexOf("fact(", i) != -1) {
								
								//如果函数名是“fact”，i向后移动4位
								i += 4;
								continue;
							}else {
								System.out.println("函数名错误， error");
								return false;
							}

						} else if  (expression.charAt(i) == 'r') {
							if (expression.indexOf("root(", i) != -1) {
								
								//如果函数名是“root”，i向后移动4位
								i += 4;
								continue;
							} else {
								System.out.println("函数名错误， error");
								return false;
							}
						} else if (expression.charAt(i) == 'v') {
							if (expression.indexOf("var([", i) != -1) {
								//如果函数名是“var”，i向后移动4位
								i += 4;
								continue;
							} else if (expression.indexOf("varp([", i) != -1) {
								//如果函数名是“varp”，i向后移动5位
								i += 5;
								continue;
							}
						} else{ 
							System.out.println("函数名错误, error!");
							return false;
						}

					}


					//对中括号的处理
					if (expression.charAt(i) == '['){
						if (i >= 4){   //判断[之前的东西是否合法
							if ((i >= 4) &&(((expression.substring(i-4, i)).compareTo("agv(") == 0) 
							|| (expression.substring(i-4, i).compareTo("sum(") == 0)|| 
							(expression.substring(i-4,i).compareTo("var(") == 0))){
								i++;
							//	System.out.println("lalala1");
								continue;
							} else if ((i >= 5) &&(((expression.substring(i - 5, i)).compareTo("varp(") != -1)||
										((expression.substring(i - 5, i)).compareTo("stdev(") != -1))){
								i++;

							//	System.out.println("lalala2");
								continue;
							} else if ((i >= 6) && (expression.substring(i-6, i).compareTo("stdevp(")) != -1){
								i++;
							//	System.out.println("lalala3");
								continue;
							}else {
								System.out.println("函数名错误，'['括号位置错误，error");
								return false;
							}
						} else {
							System.out.println("函数名错误,'['括号位置错误， error");
							return false;
						}
					}
					//对小数点的处理
					if (expression.charAt(i) == '.'){
					//	System.out.println("进入小数点");
						int count = 0, ecount = 0;
						while(true)
						{
							//字符是“.”
							if (expression.charAt(i) == '.')
								count ++;
							//字符是“e”
							if (expression.charAt(i) == 'e')
								ecount ++;
							//小数点个数> 1时报错
							if (count > 1){
								System.out.println("小数点个数count > 1, error!");
								return false;
							}
							if (ecount > 1){   //e的个数ecount > 1时，报错
								System.out.println("e的个数ecount > 1, error!");
								return false;
							}
							
							//循环判断条件，当字符不是数字和小数点时，跳出循环
							if (((expression.charAt(i) < '0' || expression.charAt(i) > '9') && 
									expression.charAt(i) != '.' && expression.charAt(i) != 'e') 
									|| i == (expression.length() - 1)) {
								if (count == 0 || count == 1){
									i -= 1;
									break;
								}
							}
							i++;	
						}
					}
				}
				i++;
			}
			//判断多逗号是否正确
			if (Multi_Comma(Expression) == true){
				return true;
			} else {
				return false;
			}
		}
		
		//多个逗号的处理函数
		boolean Multi_Comma(String Expression){
			
			int lenght = Expression.length();
			int[] temp = new int[lenght];   //记录做括弧的位置
			int t = 0, count = 0;           //t用来记录左括弧的下标，count用来记录逗号的个数
			LinkedList<Character> stack = new LinkedList<Character>();
			char stackTop = ' ';           //接收栈顶元素
			stack.addFirst('#'); //对多参数处
		
			for (int i = 0; i <temp.length; i++){
				temp[i] = 0;
			}
			for (int i = 0; i < Expression.length(); i++){
				if ((Expression.charAt(i) == '(' && Expression.charAt(i + 1) == ')')||
						(Expression.charAt(i) == '[' && Expression.charAt(i + 1) == ']')) {
					System.out.print("出现无参函数");
					return false;
				}
				if (Expression.charAt(i) == '(' || Expression.charAt(i) == '['){
					if (Expression.charAt(i) == '('){
						temp[t++] = i;
					//	System.out.println("i = " + i +"t = " + t + "temp[t - 1] = " + temp[t - 1]);
					}
					stack.addFirst(Expression.charAt(i));
				}

				if (Expression.charAt(i) == ','){        
					//判断是否出现“，”
					
					if (Expression.charAt(i + 1) == ',' || Expression.charAt(i + 1) == ')' || Expression.charAt(i + 1) == ']'){
						//如果相邻两个逗号之间没有其他字符串，报错，并返回false
						System.out.println("逗号多了,error!");
						return false;
					}
					stackTop = stack.peekFirst();      //取栈顶元素
					if (stackTop == '['){
						continue;
					} else if (stackTop == '('){
						count = 0;       //清除以前的记录，避免重复计算
						count++;
						if ((Expression.indexOf("pow(", temp[t-1]- 4) != -1) || (Expression.indexOf("mod(", temp[t-1] - 4) != -1) 
								||(Expression.indexOf("g(", temp[t-1] - 4) != -1)){
							if (count > 1){         
								//对于pow(),mode(),log()这三个函数，如果出现三个及三个以上参数，报错，并返回false
								System.out.println("运算符错误,参数过多!");
								return false;
							}
							continue;
						} else{
							if (count >= 1) 
							//对于其他函数，如果出现两个及两个以上参数，报错，并返回false
							System.out.println("逗号错误,参数过多");
							return false;
						}
					}
				}
				if (Expression.charAt(i) == ']'){
					stackTop = stack.peekFirst();       //出现‘]’
					if(stackTop == '[') {                //判断栈顶元素是否为'['
						stack.removeFirst(); 
					} else {
						stack.addFirst(Expression.charAt(i));
					}
				}
				if (Expression.charAt(i) == ')'){
					stackTop = stack.peekFirst();
					if (stackTop == '('){     //如果栈顶元素是'（',则t--，并且让count = 0
						stack.removeFirst();
						if ((Expression.indexOf("pow(", temp[t-1]- 4) != -1) || (Expression.indexOf("mod(", temp[t-1] - 4) != -1) 
								||(Expression.indexOf("log(", temp[t-1] - 4) != -1)){
							if (count != 1){        
								//对于pow(),mode(),log()这三个函数，如果出现三个及三个以上参数，报错，并返回false
								System.out.println("符号错误! 逗号过多或者过少");
								return false;
							}
							t--;    //括号匹配，t--记录（号的下标前移
							continue;
						} else{
							if (count >= 1) {      
								//对于其他函数，如果出现两个及两个以上参数，报错，并返回false
								System.out.println("逗号错误,参数过多！");
								return false;
							}
						}
					} else {            //如果不是，则压栈
						stack.addFirst(Expression.charAt(i));
					}
				}
			}
			//读取栈顶元素
			stackTop = stack.peekFirst();
			//如果栈顶元素不为“#”并且非空，那么输出“括号不匹配”，并返回false
			if (stackTop != '#' && !stack.isEmpty()){
				System.out.println("括号不匹配");
				return false;
			}
			//如果栈顶元素为“#”，则取出栈顶元素
			if (stackTop == '#'){
				stack.removeFirst();
			}
			//如果此时栈为空，则表示括号匹配正确，返回true
			if (stack.isEmpty() == true){
				return true;
			//否则表示括号匹配失败，返回false
			}else{
				System.out.println("括号不匹配");
				return false;
			}
		}

		//实现调用所有字符串中函数的方法
		public double DealFunction(String funcString){
		
			String[] temp = funcString.split("\\(");
		//	System.out.println("temp = " + temp[0]);
			temp[0] = temp[0] + '(';    
			double result = 0.0;

			switch(temp[0]){
				case "sin(":
				case "cos(":
				case "tan(" :
				case "arcsin(":
				case "arcos(":
				case "arctan(":
				case "sinh(":
				case "cosh(":
				case "tanh(":
					result = DealTriangle(funcString);  //reault获得DealTriangle()方法的返回值
					break;
				case "log(":
				case "log10(":
				case "ln(":
				case "pow(":
				case "exp(":
				case "fact(":
				case "mod(":
				case "sqrt(":
				case "suberoot(":
					result =  DeallogIndexFactorial(funcString);   //reault获得DeallogIndexFactorial()方法的返回值
					break;
				case "avg(":
				case "sum(":
				case "var(":
				case "varp(":
				case "stdev(":
				case "stdevp(":
					result = DealStatistics(funcString);  //reault获得DealStatic()方法的返回值
					break;
			}
			return result;
		}

		//求阶乘的方法
		double fact(double n){
			double result;
			if ((int)n == 1) return 1.0;   //递归出口
			result = fact((int)n - 1)*n;
			return result;
		}	

		//当函数的参数是两个时，得到两个参数的大小，parameter_value1和parameter_value2
		void DealString(String temp){
			String numString1 = "";            //记录参数一的字符串
			String numString2 = "";            //记录参数二的字符串

			int count = 0;      //判断括号是否对称
			for (int i = 0; i < temp.length(); i++){
				if (temp.charAt(i) == ',' && count == 0){                 //当遇到“,”时判断括号是否匹配,如果匹配就截取
					numString1 = temp.substring(0, i);       //得到第一个参数
					numString2 = temp.substring(i + 1, temp.length()); //得到第二个参数

					if (isNumber(numString1) == true){            //判断字符串1是否为纯数字
						parameter_value1 = Double.parseDouble(numString1);
					} else {
						parameter_value1 = calculateTrans(analysisExpression(numString1)); 
							//对第一个参数进行解析，并中转后计算
					}
					if (isNumber(numString2) == true){           //判断字符串2是否为数字
						parameter_value2 = Double.parseDouble(numString2);
					} else {
						parameter_value2 = calculateTrans(analysisExpression(numString2));
							//对第二个参数进行解析，并中转后
					}
				}
				if (temp.charAt(i) == '[' || temp.charAt(i) == '('){     //遇到左括弧, count ++
					count ++;
				} 
				if (temp.charAt(i) == ')' || temp.charAt(i) == ']'){     //遇到右括弧，count--
					count --;
				}
			}

		}


		//对数，指数，模运算等的函数接口
		public double DeallogIndexFactorial(String funcString){
			String tempsubString = "";
			String temp0 = "";
			String temp1 = "";
			double length = funcString.length();

			if(length >= 10){     //字符串的长度太长，截取部分，进行循环，减少时间复杂度
				tempsubString = funcString.substring(0, 10);
			} else {
				tempsubString = funcString;
			}

			int k = 0;     //记录遇到第一个“（”的位置
			for (int i = 0; i < length; i++){
				if (tempsubString.charAt(i) == '('){
					k = i;    //得到第一个‘（’的位置
					break;
				}
			}
			temp0 = funcString.substring(0, k + 1);        //截取字符串的前k个元素,即：若funcString = "sin(21+cos(23))",temp0 = "sin("
			temp1 = funcString.substring(k + 1, funcString.length() - 1);  //temp1 = "21+cos(23)"

			double number = 0;   //记录函数只有一个参数的值
			String numString1 = "";
			if (temp0.compareTo("log(") == 0){
				try{
					DealString(temp1);
				}catch(Exception e){                   //如果出现异常，则要抛出异常
					System.out.println("log()函数计算异常");
					System.exit(1);
				}

//				System.out.println("number1 = " + number1 + "number2 = " + number2);
				if (parameter_value1 <= 0.0 || parameter_value2 <= 0.0 || parameter_value1 == -0.0 || parameter_value2 == -0.0){
					System.out.println("log()函数参数非正，错误！");
					System.exit(1);
				}
				return Math.log(parameter_value1)/Math.log(parameter_value2);   //以parameter_value2为底数的parameter_value1的对数
			} else if (temp0.compareTo("log10(") == 0){
				try{
					if (isNumber(temp1) == true){                  //判断参数是不是一个简单的数
						number = Double.parseDouble(temp1);
					} else{
						number = calculateTrans(analysisExpression(temp1));
					}
				}catch(Exception e){                   
					System.out.println("log10()函数计算异常");
					System.exit(1);
				}
				if (number <= 0.0 || number == -0.0 || number == +0.0){
					System.out.println("log10()函数参数非正，错误");
					System.exit(1);           //异常退出
				}
				return Math.log10(number);
			} else if (temp0.compareTo("ln(") == 0){
				try{
					if (isNumber(temp1) == true){                  //判断参数是不是一个简单的数
						number = Double.parseDouble(temp1);
					}else{
						number = calculateTrans(analysisExpression(temp1));
					}
				}catch(Exception e){
					System.out.println("ln(）函数计算异常");
					System.exit(1);
				}	
				if (number <= 0.0 || number == -0.0|| number == +0.0){
					System.out.println("ln()函数参数非正，错误");
					System.exit(1);           //异常退出
				}

				return Math.log(number);
			} else if (temp0.compareTo("pow(") == 0){                   //求x的y次方
				try{
					DealString(temp1);        //处理得到参数1, 2即number1,2
				}catch(Exception e){
					System.out.println("pow()函数计算异常");
					System.exit(1);
				}
				return Math.pow(parameter_value1, parameter_value2);	
			} else if (temp0.compareTo("exp(") == 0){   		 //求e的x次方幂
				try{
					if (isNumber(temp1) == true){                  //判断参数是不是一个简单的数
						number = Double.parseDouble(temp1);
					}else{
						number = calculateTrans(analysisExpression(temp1));
					}
				}catch(Exception e){
					System.out.println("exp()函数计算异常");
					System.exit(1);
				}

				return Math.exp(number);
			} else if (temp0.compareTo("fact(" )== 0){
				try{
					if (isNumber(temp1) == true){                  //判断参数是不是一个简单的数
						number = Double.parseDouble(temp1);
					}else{
						number = calculateTrans(analysisExpression(temp1));
					}
				}catch(Exception e){
					System.out.println("fact()函数计算异常");
					System.exit(1);
				}
				if (number < 1.0 || number == -1.0 || number == +1.0){    //n小于1.0则报错
					System.out.println("fact()函数的参数异常");
					System.exit(1);
				}
				return fact(number);
			} else if (temp0.compareTo("mod(") == 0){
				try{
					DealString(temp1);
				}catch(Exception e){
					System.out.println("mod()函数计算错误");
					System.exit(1);
				}
				
				return parameter_value1%parameter_value2;
			} else if (temp0.compareTo("sqrt(") == 0){            //开平方根
				try{
					if (isNumber(temp1) == true){                  //判断参数是不是一个简单的数
						number = Double.parseDouble(temp1);
					}else{
						number = calculateTrans(analysisExpression(temp1));
					}
				}catch(Exception e) {
					System.out.println("sqrt()函数计算错误");
					System.exit(1);
				}

				if (number < 0.0){             //如果函数sqrt()的参数小于0.0则要抛出错误
					System.out.println("sqrt()函数参数错误");
					System.exit(1);
				}
				return Math.sqrt(number);
			} else if (temp0.compareTo("cuberoot(") == 0){
				try{
					if (isNumber(temp1) == true){                  //判断参数是不是一个简单的数
						number = Double.parseDouble(temp1);
					}else{
						number = calculateTrans(analysisExpression(temp1));
					}
				}catch(Exception e){
					System.out.println("cuberoot()函数计算错误");
					System.exit(1);
				}
				return Math.pow(number, 1.0/3.0);
			} 

			return -1;
		}
		
		
		/*判断一个字符是不是如下字符*/
		public boolean symbolJudge(char ch){
			if(ch == '+' || ch == '-' || ch == '*' || ch == '/' || ch == '^' || ch == '%' || ch == '@' || ch == '(' || ch == '[' || ch == ',' )
				return true;
			else
				return false;
		}	

		/*美化表达式的方法*/
		public String beautifyExpression(){
			Expression = Expression.replaceAll("\\s*","");               //去除表达式中所有空格
			Expression = Expression.replace("exp","?");                  //将表达式中的exp全部替换为?
			Expression = Expression.replace("cuberoot","!");             //将表达式中的cuberoot全部替换为!
			Expression = Expression.replace("root","@");      	     //将表达式中的root替换为@
			Expression = Expression.replace("e","*10^"); 		     //将表达式中的e替换为*10^
			Expression = Expression.replace("?","exp");                  //将表达式中的?全部替换回exp
			Expression = Expression.replace("cuberoot","!");             //将表达式中的!全部替换回cuberoot

		//	System.out.println("Expression"+Expression);
			StringBuilder expression = new StringBuilder(Expression);    //将String类对象转换成StringBuilder类的对象
			expression.append("#");       				     //在表达式最后添加一个'#',表示字符串结束
			int k;                                                       //k记录每次判断后i改变前的值

			for(int i = 0; i < expression.length(); i++ ){
				/*
				if( i <= expression.length()-4 && expression.substring(i, i+4).equals("root")){
					expression.replace(i,i+4,"@");
				}
				*/

				/*遇到小数点前没有数字的则补0*/
				if( expression.charAt(i) == '.'  ){
					/*如果字符串第一个字符就是'.'，则直接在字符串前补0*/
					if( i == 0){
						expression.insert(i, '0');
					}
					else if(symbolJudge(expression.charAt(i-1)) || expression.charAt(i-1) == ')'){
						expression.insert(i, '0');
						i--;
					}
				}
				/*遇到'-'且前面没有括号则加上括号*/
				if( expression.charAt(i) == '-' ){ 
					/*如果字符串的第一个字符为'-'，则在该负数前后加上(）将字符串变成(0-a)*/
					if( i == 0 ){
						/*如果是--a+b则会报错*/
						if(expression.charAt(i+1) == '-'){
							System.out.println("操作符错误!");
							System.exit(1);
						}
						else{
							k = i;
							/*插入"（0"*/
							expression.insert(i, "(0");
							i += 2;
							/*通过while循环找到一个完整的数字(只能由数字、小数点组成)*/
							while(Character.isDigit(expression.charAt(i+1)) || expression.charAt(i+1) == '.' || expression.charAt(i+1) == '-')
								i++;
							/*插入')'*/
							expression.insert(++i, ')');
							i = k;	
						}
					}
					/*如果是+-a+b则会报错*/
					else if(i == 1 && expression.charAt(i-1) == '+')
					{
						System.out.println("操作符错误！");
						System.exit(1);
					}
					/*a--b -> a-(0-b)*/
					else if( symbolJudge(expression.charAt(i-1))){
						k = i;
						/*如果'-'的下一个字符为'-'，则删掉一个减号 a*--b -> a*(0-b)*/
						if(expression.charAt(i+1) == '-'){
							expression.delete(i+1, i+2);
						}
						/*插入"（0"*/
						expression.insert(i, "(0");
						i += 2;
						/*通过while循环找到一个完整的数字(只能由数字、小数点和'-'组成)*/
						while(Character.isDigit(expression.charAt(i+1)) || expression.charAt(i+1) == '.' || expression.charAt(i+1) == '-')
							i++;
						/*插入')'*/
						expression.insert(++i, ')');
						i = k;
					}
					/* a-+b -> a-b */
					else if( expression.charAt(i+1) == '+'){
						/*删掉一个负号*/
						expression.delete(i+1, i+2);
					}
				}
				/*遇到++符号则删去一个'+'*/
				if( expression.charAt(i) == '+' && expression.charAt(i+1) == '+'){
					/*如果字符串开始就是++a，则删去两个'+'*/
					if(i == 0){
						expression.delete(i,i+2);
					}
					/*如果在字符串中间出现++，则将其中一个加当做正号删去*/
					else{
						expression.delete(i, i+1);
					}
				}
				/*如果+a前面是操作符的话，则将+号按照正号处理，直接删去*/
				if( expression.charAt(i) == '+' ){
					if(i == 0 && Character.isDigit(expression.charAt(i+1))){
						expression.delete(i, i+1);
					}
					else if(i != 0 && symbolJudge(expression.charAt(i-1))){
						expression.delete(i,i+1);
					}
				}
				/*如果遇到'('，且其前一个字符是数字或是')'，则添加乘号*/
				if((i != 0 && expression.charAt(i) == '(' && (Character.isDigit(expression.charAt(i-1)) || expression.charAt(i-1) == ')')) ){
						expression.insert(i, '*');
				}
				/*如果遇到')'，且后一个字符是数字或'('或字母（函数名），则添加乘号*/
				if(expression.charAt(i) == ')' && (Character.isDigit(expression.charAt(i+1)) || expression.charAt(i+1) == '(' || Character.isLowerCase(expression.charAt(i+1))) && expression.charAt(i+1) != 'e'){
					i++;
					expression.insert(i, '*');
				}		
				/*如果遇到字母（函数名），且前一个字符是数字，则添加乘号*/
				if( i != 0 && Character.isLowerCase(expression.charAt(i)) && expression.charAt(i) != 'e' && Character.isDigit( expression.charAt(i-1) ) ){
					expression.insert(i, '*');
				}
			}

			expression.delete(expression.length()-1,expression.length()); //删去字符串最后的添加的'#'
		//	System.out.println("优化后的表达式："+expression);
			Expression = expression.toString();                    //将StringBuilder类对象转换为String类

			return Expression;
		}	

		/*判断一个字符串是不是一个数*/
		public boolean isNumber(String str){
			try{
				double num = Double.parseDouble(str);
				return true;
			}catch(Exception e){
				return false;
			}
		}

		/*计算的后缀表达式*/
		public double calculateTrans(ArrayDeque<String> queueTrans){
			//创建一个新的存double类型数据的双端队列queueCalcu，用来存储后缀表达式并计算
			ArrayDeque<Double> queueCalcu = new ArrayDeque<Double>();
			double f = 0.0;  //存储每次计算后的结果
			Double tempDouble = 0.0;  
			String tempString;        //记录每次从queueTrans出队的字符串

			//System.out.println("aaa" + queueTrans);
			/*如果queueTrans中只有一个元素，则将其转换为double类型后输出*/
			if(queueTrans.size() == 1 ){
				/*读出队列中的唯一元素并将String类型的数转换为double类型*/
				try{
					BigDecimal num = new BigDecimal(queueTrans.removeFirst());
					tempDouble = num.doubleValue();
					/*将最后结果转换为String类型*/
					resultFinalExpression = Double.toString(tempDouble);
				}catch(Exception e){
					System.out.println("操作数有误！");
					System.exit(1);
				}
				/*输出结果*/
				//System.out.println("f:"+resultFinalExpression);

				return tempDouble;
			}
			
			/*如果队列中元素>=2个，则依次读出处理*/
			while(!queueTrans.isEmpty()){
				tempString = queueTrans.peekFirst(); //每次读出一个字符串到tempString
				//System.out.println("temp: " + tempString);
				queueTrans.removeFirst();
				/*如果读出的字符串是一个数字，则直接压到ArrayCalcu的队头*/
		//		if(tempString.length() >= 2){
				if( (tempString.charAt(0)=='-' && tempString.length() >= 2) || !JudgeOperator(tempString.charAt(0))  ){
					/*将读出的String类型元素转换为double类型*/
					try{
						BigDecimal num = new BigDecimal(tempString);
						tempDouble = num.doubleValue();
					}catch(Exception e){
						System.out.println("操作数有误！");
						System.exit(1);
					}
					//System.out.println("cccc" + queueCalcu);
					/*压入队列队头*/
					queueCalcu.addLast(tempDouble);
				}
				/*如果读出的字符串是"+",则从ArrayCalcu中读出两个元素进行加法运算*/
				else if(tempString.equals("+")){
					/*如果在计算中接受到异常则退出程序*/
					try{
						/*计算加法*/
						
						f = Arith.add(queueCalcu.removeLast(),queueCalcu.removeLast());
					}catch(AuctionException exception){
						System.out.println("计算时操作数有误！");
						System.exit(1);
					}
					/*将计算结果f压入队列队头*/
					queueCalcu.addLast(f);
				}
				/*如果读出的字符串是"-",则从ArrayCalcu中读出两个元素进行减法运算*/
				else if(tempString.equals("-")){
					/*如果在计算中接受到异常则退出程序*/
					try{
						/*计算减法*/
						f = Arith.sub(queueCalcu.removeLast(),queueCalcu.removeLast());
					}catch(AuctionException exception){
						System.out.println("计算时操作数有误！");
						System.exit(1);
					}
					/*将计算结果f压入队列队头*/
					queueCalcu.addLast(f);
				}
				/*如果读出的字符串是"*",则从ArrayCalcu中读出两个元素进行乘法运算*/
				else if(tempString.equals("*")){
					/*如果在计算中接受到异常则退出程序*/
					try{
						/*计算乘法*/
						f = Arith.mul(queueCalcu.removeLast(),queueCalcu.removeLast());
					}catch(AuctionException exception){
						System.out.println("计算时操作数有误！");
						System.exit(1);
					}
					/*将计算结果f压入队列队头*/
					queueCalcu.addLast(f);
				}
				/*如果读出的字符串是"/",则从ArrayCalcu中读出两个元素进行除法运算*/
				else if(tempString.equals("/")){
					/*如果在计算中接受到异常则退出程序*/
					try{
						/*计算除法*/
						f = Arith.div(queueCalcu.removeLast(),queueCalcu.removeLast());
					}catch(AuctionException exception){
						System.out.println("计算时操作数有误！");
						System.exit(1);
					}
					/*将计算结果f压入队列队头*/
					queueCalcu.addLast(f);
				}
				/*如果读出的字符串是"%",则从ArrayCalcu中读出两个元素进行取余运算*/
				else if(tempString.equals("%")){
					/*如果在计算中接受到异常则退出程序*/
					try{
						/*计算取余*/
						f = Arith.rem(queueCalcu.removeLast(),queueCalcu.removeLast());
					}catch(AuctionException exception){
						System.out.println("计算时操作数有误！");
						System.exit(1);
					}
					/*将计算结果f压入队列队头*/
					queueCalcu.addLast(f);
				}
				/*如果读出的字符串是"^",则从ArrayCalcu中读出两个元素进行乘方运算*/
				else if(tempString.equals("^")){
					/*从ArrayCalcu中取出两个操作数*/
					double num1 = queueCalcu.removeLast();
					double num2 = queueCalcu.removeLast();
					/*如果在计算中接受到异常则退出程序*/
					try{
						/*计算乘方*/
						f = Math.pow(num2,num1);
					}catch(Exception e){
						System.out.println("计算时操作数有误！");
						System.exit(1);
					}
					/*将计算结果f压入队列队头*/
					queueCalcu.addLast(f);
				}
				/*如果读出的字符串是"@",则从ArrayCalcu中读出两个元素进行开次方根运算*/
				else if(tempString.equals("@")){
					/*从ArrayCalcu中取出两个操作数*/
					double num1 = queueCalcu.removeLast();
					double num2 = queueCalcu.removeLast();
					/*如果在计算中接受到异常则退出程序*/
					try{
						/*计算开次方根*/
						f = Math.pow(num2,1.0/num1);
					}catch(Exception e){
						System.out.println("计算时操作数有误！");
						System.exit(1);
					}
					/*将计算结果f压入队列队头*/
					queueCalcu.addLast(f);
				}
			}

			/*将最后double类型的结果转换为String类型，传给resultFinalExpression*/
			resultFinalExpression = Double.toString(f);
	//		System.out.println("f:"+resultFinalExpression);
			if(resultFinalExpression.equals("Infinity")){
				System.out.println("操作数过大，无法进行计算");
				System.exit(1);
			}

			return f;
		}


		/*处理三角、反三角、双曲函数*/
		public double DealTriangle(String str){
			int i;   //i记录函数名后紧跟的括号的位置
			double tempdouble;    //记录三角函数后面的参数的最终计算值

			/*找到函数名后紧跟的前括号'('的下标*/
			for(i = 2; i < 7; i++){   //该for循环处理的最短函数名为3个字符，最长的三角函数名为6个字符
				if(str.charAt(i) == '(')
					break;
			}

			String tempFuncName = str.substring(0, i);  //截取出函数名
			String tempExpression = str.substring(i+1, str.length()-1);  //截取出函数中间的表达式
			
			switch(tempFuncName){
			/*将函数的参数传入中缀转后的方法解析，将解析的结果传入计算方法进行计算，计算的结果存入tempdouble*/
				case "sin": 
					tempdouble = calculateTrans(analysisExpression(tempExpression));
					/*返回计算结果*/
					return Math.sin(tempdouble);
			/*将函数的参数传入中缀转后的方法解析，将解析的结果传入计算方法进行计算，计算的结果存入tempdouble*/
				case "cos":
					tempdouble = calculateTrans(analysisExpression(tempExpression));
					/*返回计算结果*/
					return Math.cos(tempdouble);
			/*将函数的参数传入中缀转后的方法解析，将解析的结果传入计算方法进行计算，计算的结果存入tempdouble*/
				case "tan":
					tempdouble = calculateTrans(analysisExpression(tempExpression));
					/*返回计算结果*/
					return Math.tan(tempdouble);
			/*将函数的参数传入中缀转后的方法解析，将解析的结果传入计算方法进行计算，计算的结果存入tempdouble*/
				case "sinh":
					tempdouble = calculateTrans(analysisExpression(tempExpression));
					/*返回计算结果*/
					return Math.sinh(tempdouble);
			/*将函数的参数传入中缀转后的方法解析，将解析的结果传入计算方法进行计算，计算的结果存入tempdouble*/
				case "cosh":
					tempdouble = calculateTrans(analysisExpression(tempExpression));
					/*返回计算结果*/
					return Math.cosh(tempdouble);
			/*将函数的参数传入中缀转后的方法解析，将解析的结果传入计算方法进行计算，计算的结果存入tempdouble*/
				case "tanh":	
					tempdouble = calculateTrans(analysisExpression(tempExpression));
					/*返回计算结果*/
					return Math.tanh(tempdouble);
			/*将函数的参数传入中缀转后的方法解析，将解析的结果传入计算方法进行计算，计算的结果存入tempdouble*/
				case "arcsin":
					tempdouble = calculateTrans(analysisExpression(tempExpression));
					/*如果tempdouble不符合该函数的定义域，则会报错*/
					if(Double.toString(Math.acos(tempdouble)).equals("NaN")){
						System.out.println("操作数不正确");
						System.exit(1);
					}
					/*返回计算结果*/
					return Math.asin(tempdouble);
			/*将函数的参数传入中缀转后的方法解析，将解析的结果传入计算方法进行计算，计算的结果存入tempdouble*/
				case "arccos":
					tempdouble = calculateTrans(analysisExpression(tempExpression));
					/*如果tempdouble不符合该函数的定义域，则会报错*/
					if(Double.toString(Math.acos(tempdouble)).equals("NaN")){
						System.out.println("操作数不正确");
						System.exit(1);
					}
					/*返回计算结果*/
					return Math.acos(tempdouble);
			/*将函数的参数传入中缀转后的方法解析，将解析的结果传入计算方法进行计算，计算的结果存入tempdouble*/
				case "arctan":
					tempdouble = calculateTrans(analysisExpression(tempExpression));
					/*如果tempdouble不符合该函数的定义域，则会报错*/
					if(Double.toString(Math.atan(tempdouble)).equals("NaN")){
						System.out.println("操作数不正确");
						System.exit(1);
					}
					/*返回计算结果*/
					return Math.atan(tempdouble);
			}
				
			return 0.0;
		}
		
		
	
	
	/**
	 * 判断括号是否匹配，如果匹配返回true，否则提示“括号不匹配”，并且返回false
	 * @param 计算式的字符串 JudgeExpression
	 * @return 布尔值 如果匹配成功返回true，不成功返回false
	 */
	public boolean JudgeBrackets(String JudgeExpression){
	LinkedList<Character> stack  = new LinkedList<Character>();
		int length = JudgeExpression.length();
		char stackTop = ' ';
		stack.addFirst('#');
		for (int i = 0; i < length; i++){
			//如果当前符号为左括号则直接进栈
			switch(JudgeExpression.charAt(i)){
				case '(':
				case '[':
					stack.addFirst(JudgeExpression.charAt(i));
					break;
			//如果当前符号为右小括号，
				case ')': 
					//则取栈顶元素
					stackTop = stack.peekFirst();
					//如果栈顶元素为左小括号，则出栈
					if (stackTop == '('){
						 stack.removeFirst();
					}
					//否则将当前符号进栈
					else{ 
						stack.addFirst(JudgeExpression.charAt(i));
					}
					break;
			//如果当前符号为右中括号		
				case ']': 
					//取栈顶元素
					stackTop = (char) stack.peekFirst();
					//如果栈顶元素为左中括号，则出栈
					if (stackTop == '['){
						stack.removeFirst();
					}
					//否则将当前符号进栈
					else {
						stack.addFirst(JudgeExpression.charAt(i));
					}
					break;
				}
		}
		//读取栈顶元素
		stackTop = stack.peekFirst();
		//如果栈顶元素不为“#”并且非空，那么输出“括号不匹配”，并返回false
		if (stackTop != '#' && !stack.isEmpty()){
			System.out.println("括号不匹配");
			return false;
		}
		//如果栈顶元素为“#”，则取出栈顶元素
		if (stackTop == '#'){
			stack.removeFirst();
		}
		//如果此时栈为空，则表示括号匹配正确，返回true
		if (stack.isEmpty() == true){
			return true;
		//否则表示括号匹配失败，返回false
		}else{
			System.out.println("括号不匹配");
			return false;
		}
	}
	
	/**
	 * 获得操作符的优先级,优先级的整数表示越大，优先级越高
	 * @param 操作符 KeySymbol
	 * @return 整数 操作符的优先级
	 */
	public int GetPriority(char keySymbol){
		HashMap <Character, Integer> priority = new HashMap <Character, Integer>();
		priority.put('@', 3);        //操作符为“@”，表示求次根，优先级为3
		priority.put('^', 3);		 //操作符为“^”,表示求次方，优先级为3
		priority.put('*', 2);		 //操作符为“*”,表示求乘法，优先级为2
		priority.put('/', 2); 		 //操作符为“/”，表示求除法，优先级为2
		priority.put('%', 2);		 //操作符为“%”，表示求模除，优先级为2
		priority.put('+', 1);		 //操作符为“+”，表示求加法，优先级为1
		priority.put('-', 1); 		 //操作符为“-”，表示求减法，优先级为1
		priority.put('(', 1);		 //操作符为“（”，优先级为1
		priority.put(')', 4);        //操作符为“）”，优先级为4
		//返回操作符的优先级
		return priority.get(keySymbol);
		}
	
	/**
	 * 判断传入的字符是否为操作符
	 * @param 字符 KeySymbol
	 * @return 布尔值 如果字符为操作符，返回true，否则返回false
	 */
	public boolean JudgeOperator(char KeySymbol){
		HashSet <Character> exit = new HashSet<Character>();
		
		/**
		 * 操作符有：+，—,*,/,%,^,(,),@
		 */
		exit.add('+');
		exit.add('-');
		exit.add('*');
		exit.add('/');
		exit.add('%');
		exit.add('^');
		exit.add('(');
		exit.add(')');
		exit.add('@');
		//返回是否存在，存在返回true，不存在返回false
		return exit.contains(KeySymbol);
	}
	
	/**
	 * 将计算式中缀转为后缀,返回一个队列
	 * @param transExpression
	 * @return 返回保存操作符、操作数和操作函数的队列
	 */
	public ArrayDeque<String> analysisExpression(String transExpression){
		LinkedList<Character> stack = new LinkedList<Character>(); 	//压操作符栈
		ArrayDeque<String> queueTrans = new ArrayDeque<String>();	//最后的结果队列
		int length = transExpression.length();						//计算式字符串
		char stackTop = ' ';										//栈顶元素
		int topPriority = 0;										//栈顶操作符的优先级
		int currentPriority = 0;									//当前符号的优先级
		StringBuilder num = new StringBuilder(""); 					//得到的数字串
		
		transExpression = transExpression + "#"; 					//给计算式字符串后补个“#”
		//StringBuilder string = new StringBuilder("");				

		for (int i = 0; i < length; i++){
			//解析出数字，如果当前字符为数字、小数
			if (Character.isDigit(transExpression.charAt(i))|| transExpression.charAt(i) == '.'){
				num = num.append(transExpression.charAt(i));
				//如果当前字符的下一个字符不是数字也不是点也不是e则可以截取该字符串，该字符串表示一个完整的数
				if ((i + 1 <= length) && ((Character.isDigit(transExpression.charAt(i+1)) == false) && transExpression.charAt(i+1) != '.') ){
					//直接将数入队列
					queueTrans.addLast(num.toString());
			   //     string.append(num);   //delete~~~~~~~~~~~~~~~~`
				//	string.append(" ");	  //delete~~~~~~~~~~~~~~~~
			        num.delete(0, num.length());
			        }
				//如果它是计算操作符
			}else if(this.JudgeOperator(transExpression.charAt(i))){
				//如果他是左括号，栈里元素依次出栈直到遇到右括号
				if (transExpression.charAt(i) == ')'){
					stackTop = stack.peekFirst();
					while (stackTop != '('){
						stackTop = stack.peekFirst();
						stack.removeFirst();
						queueTrans.addLast(String.valueOf(stackTop));
					//	string.append(stackTop); //delete~~~~~~~~~~~~~~~~~~~
						stackTop = stack.peekFirst();
					}
					//将右括号出栈
					stack.removeFirst();
					//如果遇到右括号，直接进栈
				}else if(transExpression.charAt(i) == '('){
					stack.addFirst(transExpression.charAt(i));
					//如果是计算操作符不是括号
				}else {
					//如果是栈空，直接进栈
					if (stack.isEmpty()){
						stack.addFirst(transExpression.charAt(i));
					}else{
						//将第一个不是括号的操作符进栈
						if ((stack.peekFirst() == '(') && (stack.size() == 1)){
						stack.addFirst(transExpression.charAt(i));
						continue;
					}   
						//得到当前操作符和栈顶元素的优先级比较
						stackTop = stack.peekFirst();
						//当前操作符的优先级
						currentPriority = this.GetPriority(transExpression.charAt(i));
						//栈顶元素的优先级
						topPriority = this.GetPriority(stackTop);	
						//如果当前的优先级大于等于栈顶的优先级，直接进栈
						if (currentPriority >= topPriority){
							stack.addFirst(transExpression.charAt(i));
						}else{
							//如果当前的优先级小于栈顶的优先级，将栈顶的优先级依次出栈，
							do{
								stackTop = stack.peekFirst();
								stack.removeFirst();
								queueTrans.addLast(String.valueOf(stackTop));
						//		string.append(stackTop); //delete~~~~~~~~~~~~~~~
								//如果栈空停止出栈
								if (stack.isEmpty()){
									break;
								}
								stackTop = stack.peekFirst();
								topPriority = this.GetPriority(stackTop);
								//如果栈顶元素为左括号，停止出栈
								if (stackTop == '('){
									break;
								}
								//直到遇到栈顶的优先级低于当前的优先级或者遇到左括号
							}while((currentPriority > topPriority )|| (transExpression.charAt(i) != '('));
							//将当前的操作符进栈
							stack.addFirst(transExpression.charAt(i));
						}
					}
				}
				//如果字符既不是操作数又不是操作符，那么就是函数，需要将函数整块提出来
		}else { 
				int between = 0; 			//0表示括号配对，非0表示括号不配对
				boolean flag = false;		//如果遇到左括号，则标志为true，否则为fasle
				StringBuilder funcstr = new StringBuilder(""); //
				//获取函数块，如sin（1+2+3），就获取sin（1+2+3）这个整体
				for  (int count = i; count < transExpression.length(); count++){
					//得到函数块，通过括号是否配对截取
					if ((flag == true) && (between == 0)){
						i = count - 1;
					//	System.out.println(funcstr);
						String funStrResult = "";
						funStrResult = funcstr.toString();
						//System.out.println("中转后里截取的函数: " + funStrResult);
						//funcStrResult为函数块，调用处理函数计算的方法，返回一个数值
						double numResult = this.DealFunction(funStrResult);
						//将数值直接进入队列
						queueTrans.addLast(Double.toString(numResult));
					//	string.append(Double.toString(numResult)); //delete~~~~~~~~
					//	string.append(" ");  //delete~~~~~~~~~
						break;
						}
					//通过括号数来截出函数块，遇到左括号between+1;遇到右括号between-1;当between为0是，表示函数块结束
					//遇到了一个左括号，将当前字符追加如funstr字符串中，并将flag设为true，表示已经遇到一个左括号，排除了仅通过between值来截取函数块的弊处
					//仅通过between为0来判断的话，如果没有遇到括号，那么符合between == 0条件会直接break
					if (transExpression.charAt(count) == '('){
						between++;
						flag = true;
						funcstr.append(transExpression.charAt(count));
						continue;
					}
					if (transExpression.charAt(count) == ')'){
						between--;
						funcstr.append(transExpression.charAt(count));
						continue;
					}
					//将当前字符加入到函数串里
						funcstr.append(transExpression.charAt(count));	
				}
		}
	}
		//将栈里剩余的操作符出栈
		while(stack.isEmpty() == false){
			stackTop = stack.peekFirst();
			stack.removeFirst();
			queueTrans.addLast(String.valueOf(stackTop));
		//	string.append(stackTop);  //delete~~~~~~~~~~
		}
		
	//	System.out.println("中缀里面的： " + queueTrans);//delete~~~~~~
	//	System.out.println(string);   //delete~~~~~~~~~`
		//返回队列
		return queueTrans;
	}
	
	
	/**
	 * 从str字符串从position位置截取统计函数
	 * @param str	计算式字符串
	 * @param position 从position位置开始截str字符串
	 * @return 字符串数组 ：统计函数的名字，整块的统计函数，以及去掉名字的统计函数的内容部分
	 */
	public String[] getFuncStatisics(String str, int position){
		int between = 0;
		boolean flag = false;
		String[] funcInfor = new String[3];
		funcInfor[0] = "";  //name
		funcInfor[1] = "";  //posture，整个统计函数块
		funcInfor[2] = "";	//contain avg([a,b,c]) → a,b,c])
		
		for(int i = position; i < str.length(); i++){
			//提出去完整的统计函数块
			if (flag == true && between == 0){
				int index = position;
				String funcName = funcInfor[1].substring(0, 8);
				funcInfor[1] = funcInfor[1] + ")";
				//通过'['将函数名截取出来
				funcInfor[0] = funcName.split("\\[")[0];
				index = position + funcInfor[0].length();
				funcInfor[2] = funcInfor[1].substring(index + 1, funcInfor[1].length());
				return funcInfor;
			}
			//如果遇到左中括号
			if (str.charAt(i) == '['){
				between++;
				funcInfor[1] = funcInfor[1] + str.charAt(i);
				flag = true;
				continue;
			}
			//如果遇到右中括号
			if (str.charAt(i) == ']'){
				between--;
				funcInfor[1] = funcInfor[1] + str.charAt(i);
				continue;
			}
			//将当前字符连接到funcInfor[1]中
			funcInfor[1] = funcInfor[1] + str.charAt(i); 
		}
		return null;
	}
	
	
	/**
	 * 平均数函数字符串处理,将平均函数转成除式形式字符串
	 * @param queue 存入avg函数里的操作数
	 * @return 返回字符串 e.g avg([1,2,3]) → ((1+2+3)/3)
	 */
	public String avgFunc(ArrayDeque<String> queue){
	//	System.out.println("avgFunc: " + queue);
		int count = queue.size(); 	//记录一共有多少个操作数
		StringBuilder resExpression = new StringBuilder("");  //形成结果的最终串
		//根据要求一个一个连接成最终的字符串
		resExpression.append("((");
		resExpression.append(queue.peekFirst());
		queue.removeFirst();
		//将队列中的元素一个一个连接到最终的字符串
		while(queue.isEmpty() != true){
			resExpression.append("+");
			resExpression.append(queue.peekFirst());
			queue.removeFirst();
		}
		resExpression.append(")/");
		resExpression.append(Integer.toString(count));
		//System.out.println("resExpression: " + resExpression);
		//需要在字符串的外面添一对括号，防止计算顺序出现问题
		resExpression.append(")");
		return resExpression.toString();
			
 	}	
	
	/**
	 * 总计函数字符串处理,将总计函数转成和式形式字符串
	 * @param queue 存入sum函数里的操作数
	 * @return 返回字符串 e.g. aum([1,2,3]) → (1+2+3)
	 */
	public String sumFunc(ArrayDeque<String> queue){
		StringBuilder resExpression = new StringBuilder("");  //形成结果的最终串
		//根据形势要求将队列里的元素一个一个连接成最终的字符串
		resExpression.append("(");
		resExpression.append(queue.peekFirst());
		queue.removeFirst();
		//将队列中的元素一个一个连接到最终的字符串
		while(queue.isEmpty() != true){
			resExpression.append("+");
			resExpression.append(queue.peekFirst());
			queue.removeFirst();
		}
		//在字符串的外面添加一对括号，防止计算顺序出现问题
		resExpression.append(")");
	//	System.out.println("resExpression: " + resExpression);
		return resExpression.toString();
	}
	
	/**
	 * 估算方差/总体方差函数字符串处理
	 * @param queue 保存了var函数的操作数
	 * @param avg 	平均函数的结果
	 * @param count 记录一共有多少个操作数
	 * @return 字符串 e.g.var([1,2,3])(A：用来表示(1+2+3)/3) → ((pow((A-1),2)+pow((A-2),2)+pow((A-3),2))/2)
	 */
	public String varFunc(ArrayDeque<String> queue, String avg, int count){
		ArrayDeque<String> singlepos = new ArrayDeque<String>();  //队列，其中的元素为(A-x)
		StringBuilder resExpression = new StringBuilder("");
		StringBuilder numDivide = new StringBuilder("");   //将A连接为(A-X)的形式
		resExpression.append("((");
		//将A连接为(A-1)的形式
		while(queue.isEmpty()!=true){
			numDivide.append("(");
			numDivide.append(avg);
			numDivide.append("-");
			numDivide.append(queue.peekFirst());
			numDivide.append(")");
			singlepos.addLast(numDivide.toString());
			numDivide.delete(0, numDivide.length());
			queue.removeFirst();
		}
		//将字符串连接为((pow((A-1),2)+pow((A-2),2)+pow((A-3),2))/2)的形式
	//	System.out.println("var: " + singlepos);
		resExpression.append("pow(");
		resExpression.append(singlepos.peekFirst());
		singlepos.removeFirst();
		resExpression.append(",2)");
		while(singlepos.isEmpty() != true){
			resExpression.append("+pow(");
			resExpression.append(singlepos.peekFirst());
			singlepos.removeFirst();
			resExpression.append(",2)");
		}
		resExpression.append(")/");
		resExpression.append(Integer.toString(count - 1));
		//在字符串的外面再添加一对括号，防止计算顺序出现问题
		resExpression.append(")");
	//	System.out.println("var: " + resExpression);
		return resExpression.toString();
	}
	
	
	/**
	 * 统计函数族处理
	 * @param str 统计函数字符串
	 * @return 返回计算得到的结果 double类型
	 */
   public double DealStatistics(String str){
		getFuncStatisics(str, 0);
		int betweenM = 0;
		int betweenS = 0;
		double resultValue = 0.0;
		String[] funcInfor = new String[3];
		StringBuilder numDivide = new StringBuilder(""); //逗号分隔
		//存入函数里的操作数
		ArrayDeque<String> queue = new ArrayDeque<String>(); 
		ArrayDeque<String> queue2 = new ArrayDeque<String>();
		ArrayDeque<String> postureQueue = new ArrayDeque<String>(); //计算的栈
		//需要中缀转后缀的计算式
		String resExpression = "";
		funcInfor[0] = "";  //name
		funcInfor[1] = "";  //posture
		funcInfor[2] = "";	//contain avg([a,b,c]) → a,b,c])
		//分析统计函数，得到函数名和函数内容
		funcInfor = getFuncStatisics(str, 0);
		//解析参数，将一个个参数分离出来
		for (int i = 0; i < funcInfor[2].length(); i++){
			if (funcInfor[2].charAt(i) == '['){
				betweenM++;
			}
			if (funcInfor[2].charAt(i) == ']'){
				betweenM--;
			}
			if (funcInfor[2].charAt(i) == '('){
				betweenS++;
			}
			if(funcInfor[2].charAt(i) == ')'){
				betweenS--;
			}
			//以逗号为分割符，并且判断该逗号是否为一个嵌套统计函数里的逗号，
			//如果是，则该逗号不是分割符
			if (funcInfor[2].charAt(i) == ',' && betweenM == 0 && betweenS ==0){
				queue.addLast(numDivide.toString());
				numDivide.delete(0, numDivide.length());
				continue;
			}
			//如果遇到最后一个操作数，则不能以逗号为分隔符，分离出最后一个操作数，循环停止
			if (i + 1 == funcInfor[2].length()-1){
				queue.addLast(numDivide.toString());
				numDivide.delete(0, numDivide.length());
				break;
			}
			//将当前字符存入numDivide中
			numDivide.append(funcInfor[2].charAt(i));
		}
		queue2 = queue.clone();
		//判断函数名
		switch (funcInfor[0]){
		//如果是平均是函数
		case "avg(":
					resExpression = avgFunc(queue);    //调用平均数函数字符串处理方法，返回结果字符串
				//	System.out.println("case Expression: " + resExpression);
					//将得到的式子中缀转后缀
					postureQueue = this.analysisExpression(resExpression);
				//	System.out.println("中缀转后缀的： " + postureQueue);
					//调用计算方法
					resultValue = calculateTrans(postureQueue);
					//返回计算结果
					return resultValue;
		case "sum(":
					resExpression = sumFunc(queue);   //调用总计函数字符串处理方法，返回结果字符串
				//	System.out.println("case Expression: " + resExpression);
					//将得到的式子中缀转后缀
					postureQueue = this.analysisExpression(resExpression);
				//	System.out.println("中缀转后缀的：" + postureQueue);
					//计算结果
					resultValue = calculateTrans(postureQueue);
					//返回计算结果
					return resultValue;
		case "var(":
					resExpression = avgFunc(queue2); //调用平均数函数字符串处理方法，返回结果字符串
					//调用中缀转后缀方法
					postureQueue = this.analysisExpression(resExpression);
					//计算平均数函数的结果
					resultValue = calculateTrans(postureQueue);  
					//将结果赋给字符串resExpression
					resExpression = Double.toString(resultValue);
				//	System.out.println("resExpression : " + resExpression);
					//将字符串resExpression作为参数传递给估计方差/总体方差函数字符串处理方法，返回结果字符串
					resExpression = varFunc(queue, resExpression, queue.size());
					//调用中缀转后缀
					postureQueue = this.analysisExpression(resExpression);
					//计算结果
					resultValue = calculateTrans(postureQueue);
					//返回计算结果
					return resultValue;
		case "varp(": 
					resExpression = avgFunc(queue2); //调用平均数函数字符串处理方法，返回结果字符串
					//调用中缀转后缀方法
					postureQueue = this.analysisExpression(resExpression);
					//计算平均数函数的结果
					resultValue = calculateTrans(postureQueue);
					//System.out.println("平均数1为：" + resultValue);
					//将结果赋给字符串resExpression
					resExpression = Double.toString(resultValue);
					//System.out.println("resExpression: " + resExpression);
					//将字符串resExpression作为参数传递给估计方差/总体方差函数字符串处理方法，返回结果字符串
				//	System.out.println("平均数为：" + resExpression);
					resExpression = varFunc(queue,resExpression, queue.size() + 1);
					//调用中缀转后缀
					postureQueue = this.analysisExpression(resExpression);
					//计算结果
					resultValue = calculateTrans(postureQueue);
					//返回计算结果
					return resultValue;
		case "stdev(":
					resExpression = avgFunc(queue2); //调用平均数函数字符串处理方法，返回结果字符串
					//调用中缀转后缀方法
					postureQueue = this.analysisExpression(resExpression);
					//计算平均数函数的结果
					resultValue = calculateTrans(postureQueue);  
					//将结果赋给字符串resExpression
					//System.out.println("平均数：" + resultValue);
					resExpression = Double.toString(resultValue);
					//System.out.println("resExpression : " + resExpression);
					//将字符串resExpression作为参数传递给估计方差/总体方差函数字符串处理方法，返回结果字符串
					resExpression = varFunc(queue, resExpression, queue.size());
					//调用中缀转后缀
					postureQueue = this.analysisExpression(resExpression);
					//计算
				//	System.out.println("方差：" + resultValue);
					resultValue = calculateTrans(postureQueue);
					//将得到的结果开方
					return Math.pow(resultValue,0.5);
		case "stdevp(":
					resExpression = avgFunc(queue2); //调用平均数函数字符串处理方法，返回结果字符串
					//调用中缀转后缀方法
					postureQueue = this.analysisExpression(resExpression);
					//计算平均数
					resultValue = calculateTrans(postureQueue);
					//将结果赋给resExpression
					resExpression = Double.toString(resultValue);
					//System.out.println("resExpression: " + resExpression);
					//将resExpression作为参数传递给估计方差/总体方差函数字符串处理方法，返回结果字符串
					resExpression = varFunc(queue,resExpression, queue.size() + 1);
					//调用中缀转后缀方法
					postureQueue = this.analysisExpression(resExpression);
					//计算
					resultValue = calculateTrans(postureQueue);
					//将计算的结果开方
					return Math.pow(resultValue,0.5);
		}
		
		return 0.0;
  }
}


public class CalculateTest{
	public static void main(String [] args) {
	String oldexpression = new String();
	System.out.println("input the posture:");
	Scanner reader = new Scanner(System.in);
	oldexpression = reader.nextLine();
	Calculate Expre = new Calculate(oldexpression);

	while(true){
	if (Expre.JudgeSpace() == true){
	//	System.out.println("一步成功");
		if (Expre.JudgeChar() == true){
		//	System.out.println("二步成功");
			if(Expre.JudgeBrackets(oldexpression) == true){
			//	System.out.println("括号第一次成功匹配");
				oldexpression = Expre.beautifyExpression();
				if(Expre.JudgeBrackets(oldexpression) == true){
			//		System.out.println("括号第二次成功匹配");
					ArrayDeque<String> queue = new ArrayDeque<String>(); 
					queue = Expre.analysisExpression(oldexpression);
					Expre.calculateTrans(queue);
					break;
				}
				else{
					System.out.println("第二次括号匹配失败");
				}
			}
			else{
				System.out.println("第一次括号匹配失败");
			}	
		} 
		else {
			System.out.println("请重新输入：");
	//		String oldexpression = new String();
			reader = new Scanner(System.in);
			oldexpression = reader.nextLine();
			Expre = new Calculate(oldexpression);

		}
	} 
	else {

		System.out.println("请重新输入：");
//		String oldexpression = new String();
		reader = new Scanner(System.in);
		oldexpression = reader.nextLine();
		Expre = new Calculate(oldexpression);


	}
	}
	System.out.println("结果：" + Expre.Getresult());
	reader.close();
}
}

