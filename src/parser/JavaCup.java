package parser;

public class JavaCup {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String specificationFile =  (args.length != 0)? 
											args[0] :
											"parser/cool.cup";
		String[] arguments = {"-package",
								"parser",
								"-parser",
								"CoolParser",
								"-symbols",
								"Sym",
								specificationFile};
		
		try {
			java_cup.Main.main(arguments);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
