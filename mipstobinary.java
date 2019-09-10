
package MidAssi;

import java.util.Scanner;
public class mipstobinary{
	static Scanner input = new Scanner(System.in);
	static String operations[] = {"sub","and","or","jr","addi","lw","jal","bne"};
	static String operationBinary[] = {"000000","000000","000000","000000","001000","100011","000011","000101"};
	static String functionCode[] = {"100010","100100","100101","001000"};
	static String registers[] = {"$zero","$at","$v0","$v1","$a0","$a1","$a2","$a3","$t0","$t1","$t2","$t3","$t4","$t5","$t6","$t7","$s0","$s1","$s2","$s3","$s4","$s5","$s6","$s7","$t8","$t9","$k0","$k1","$gp","$sp","$fp","$ra","$s8"};
	static String registersBinary[] = new String [33];
	static int checker=0;
	static int checker2=0;
	static String userInput;
	static String stringParts[];
	static String opcode;
	static String register1;
	static String register2;
	static String register3;
	static String function;
	static String shamt;
	
	public static void binaryInputOfRegisters(){
		int stringLength = registers.length;
		int length;
		String zero = "0";
		for (int counter=0;counter<stringLength;counter++){
			if(counter<32)
			{
				registersBinary[counter] = Integer.toBinaryString(counter);
				length = registersBinary[counter].length();
				if(length <5){
					for (int i=0;i<5-length;i++){
						registersBinary[counter] = zero + registersBinary[counter];
					}
			
				}
			}else{
				registersBinary[counter] = Integer.toBinaryString(counter-2);
			}
		}
	}
	
	public static void supportedInstructions(){
	System.out.println("Supported Instructions are: sub, addi, and, or, lw, jal, bne, jr");
	}
	
	public static void inputs(){
		System.out.println("Input a Mips Instruction:");
		userInput = input.nextLine();
	}	

	public static void stringSplit(){
		stringParts= userInput.split("[ \\,\\(\\)]");
		int stringLength = stringParts.length;
		if(stringLength<6 & stringLength>4){
			stringParts[2]=	stringParts[3];
			stringParts[3]=	stringParts[4];
		}
		if(stringLength>5){
			stringParts[2]=	stringParts[3];
			stringParts[3]=	stringParts[5];
		}
	}
	
	public static void converter(){
		int counter;
		boolean availablityOperation;
		boolean availablityRegister1;
		boolean availablityRegister2;
		boolean availablityRegister3;
		int stringLength = operations.length;
		int stringLength2 = registers.length;
		
		
		for (counter=0;counter<stringLength;counter++){
			availablityOperation = operations[counter].contains(stringParts[0]);
			
			if(availablityOperation == true && counter<4){
			
				opcode = operationBinary[counter];
				function = functionCode[counter];
				shamt = "00000";
				if(operations[counter].contains("jr")){
					registerBinaryForJr();
					break;
				}
				checker++;
			}
			if(availablityOperation == true && counter>3){
				opcode = operationBinary[counter];
				if(operations[counter].contains("lw")){
					registerBinaryForLw();
					break;
				}
				if(operations[counter].contains("addi")){
					registerBinaryForAddi();
					break;
				}
				if(operations[counter].contains("jal")){
					register1 = BinaryConvertAndExtendForJal(stringParts[1]);
					System.out.println(opcode+" "+register1);
					return;
				}
				if(operations[counter].contains("bne")){
					registerBinaryForBne();
					break;
				}
				checker++;
			}
		}
		
	
		if(checker == 0){
			System.out.println("Invalid Instruction.");
		}
		if (checker!=0 && checker2==0){
			for (counter=0;counter<stringLength2;counter++){
				availablityRegister1 = stringParts[1].contains(registers[counter]);
				availablityRegister2 = stringParts[2].contains(registers[counter]);
				availablityRegister3 = stringParts[3].contains(registers[counter]);
				if(availablityRegister1 == true){
					register1 = registersBinary[counter];
				}
				if(availablityRegister2 == true){
					register2 = registersBinary[counter];
				}
				if(availablityRegister3 == true){
					register3 = registersBinary[counter];
				}
		}
			if(register1==null || register2==null || register3==null ){
				System.out.println("Invalid Instruction.");
				return;
			}
			if(function != null){
				System.out.println(opcode+" "+register2+" "+register3+" "+register1+" "+shamt+" "+function);
			}
		}
	}
	

		public static void registerBinaryForJr(){
			int counter;
			int stringLength = registers.length;
			int checkLength = stringParts.length;
			register2 = "00000";
			register3 = "00000";
			
				if(checkLength>2){
					System.out.println("Invalid Instruction.");
					return;
				}
				else{
					checker++;
					checker2++;
					for (counter=0;counter<stringLength;counter++){
							boolean truth = stringParts[1].contains(registers[counter]);
							if(truth == true){
							register1 = registersBinary[counter];
							}
					}
				}
				if(register1==null || register2==null || register3==null ){
					System.out.println("Invalid Instruction.");
					return;
				}		
				System.out.println(opcode+" "+register1+" "+register2+register3+shamt+" "+function);
			
		}
	
	public static void registerBinaryForLw(){
		int counter;
		int stringLength = registers.length;
		int checkLength = stringParts.length;
		boolean availablityRegister1;
		boolean availablityRegister2;
		if(checkLength>5){
			System.out.println("Invalid Instruction.");
			return;
		}
		else{
			checker++;
			checker2++;
			for (counter=0;counter<stringLength;counter++){
				availablityRegister1 = stringParts[1].contains(registers[counter]);
				availablityRegister2 = stringParts[3].contains(registers[counter]);
				if(availablityRegister1 == true){
					register2 = registersBinary[counter];
				}
				if(availablityRegister2 == true){
					register1 = registersBinary[counter];
				}
			}
			register3 = BinaryConvertAndExtend(stringParts[2]);
		}
		if(register1==null || register2==null || register3==null ){
			System.out.println("Invalid Instruction.");
				return;
		}		
		System.out.println(opcode+" "+register1+" "+register2+" "+register3);
	}
	
	public static void registerBinaryForAddi(){
		int counter;
		int stringLength = registers.length;
		int checkLength = stringParts.length;
		boolean availablityRegister1;
		boolean availablityRegister2;
		if(checkLength>6 || checkLength<3){
			System.out.println("Invalid Instruction.");
			return;
		}
		else{
			checker++;
			checker2++;
			for (counter=0;counter<stringLength;counter++){
				availablityRegister1 = stringParts[1].contains(registers[counter]);
				availablityRegister2 = stringParts[2].contains(registers[counter]);
				if(availablityRegister1 == true){
					register1 = registersBinary[counter];
				}
				if(availablityRegister2 == true){
					register2 = registersBinary[counter];
				}
			}
			register3 = BinaryConvertAndExtend(stringParts[3]);
		}
		if(register1==null || register2==null || register3==null ){
			System.out.println("Invalid Instruction.");
				return;
		}
		else
			System.out.println(opcode+" "+register1+" "+register2+" "+register3);
	}	
	
	public static void registerBinaryForBne(){
		int counter;
		int stringLength = registers.length;
		int checkLength = stringParts.length;
		boolean availablityRegister1;
		boolean availablityRegister2;
		if(checkLength>6){
			System.out.println("Invalid Instruction.");
			return;
		}
		else{
			checker++;
			checker2++;
			for (counter=0;counter<stringLength;counter++){
				availablityRegister1 = stringParts[1].contains(registers[counter]);
				availablityRegister2 = stringParts[2].contains(registers[counter]);
				if(availablityRegister1 == true){
					register1 = registersBinary[counter];
				}
				if(availablityRegister2 == true){
					register2 = registersBinary[counter];
				}
			}
			register3 = BinaryConvertAndExtend(stringParts[3]);
		}
		if(register1==null || register2==null || register3==null ){
			System.out.println("Invalid Instruction.");
				return;
		}
		
		System.out.println(opcode+" "+register1+" "+register2+" "+register3);
	}
		
	public static String BinaryConvertAndExtend(String decimal){
		int intDecimal;
		int length;
		intDecimal = Integer.parseInt(decimal);
		decimal = Integer.toBinaryString(intDecimal);
		length = decimal.length();
		if(length <16){
			String zero="0";
			for (int i=0;i<16-length;i++){
				decimal= zero+decimal;
			}
		}
		return decimal;
	}
	public static String BinaryConvertAndExtendForJal(String decimal){
		int intDecimal;
		int length;
		intDecimal = Integer.parseInt(decimal);
		decimal = Integer.toBinaryString(intDecimal);
		length = decimal.length();
		if(length <26){
			String zero="0";
			for (int i=0;i<26-length;i++){
				decimal= zero+decimal;
			}
		}
		return decimal;
	}

//Main method
	public static void main(String[] args) {
		try{
			supportedInstructions();
		 	binaryInputOfRegisters();
			inputs();
			stringSplit();
			converter();
		}catch(java.lang.NumberFormatException e){
			System.out.println("Invalid Instruction.");
		}catch(java.lang.ArrayIndexOutOfBoundsException e){
			System.out.println("Invalid Instruction.");
		}finally{}
	}
}
