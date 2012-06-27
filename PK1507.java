///////Code For Code'm Poker Event in TechFest 2012
///////Team id : 1507
///////Team Leader : Saheb Motiani
///////Team Member 2: Smit Sanghavi
///////Team Member 3: Dharak Shah

import java.io.File;
import java.io.FileWriter;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Scanner;

public class PK1507{
	public static void main(String[] args)
	{
		FileCreator f1 = new FileCreator();
		f1.readFile("deals_money.txt");
		f1.readFile("inputf.txt");
		f1.readFile("deal.txt");
	}
}
////////////////////////////////////////////////////////////////////////////////
class Card implements Cloneable {
	int value;
	int suit;
	int hand;
	int n;
	public Card(int n,int ihand)
	{
		value=(n-1)%13;
		suit=(n-1)/13+1;
		hand=ihand;
		this.n=n;
	}
}
////////////////////////////////////////////////////////////////////////////////
class FileCreator {
	static int BIG_BLIND=10;

	Scanner s1,s2,s3,m1,f1,f3;
	RandomAccessFile qq;
	FileWriter[] h= new FileWriter[7];
	FileWriter filepointer,money,out,folder;
	String stage;

	int holecard1,holecard2,player_no,moneyspent;
	int[] boardcard = new int[6];
	int[] isPlay = new int[7];
	int playercount=0;
	int dealnum,dealer,mainpot;
	int[] moneyleft = new int[7];
	int[] old=new int[7];
	Card b1,b2,b3,b4,b5;
	Card h1,h2;
	Card[][]  holecards;

	public void readFile(String name){
		//s1 is file pointer for file--inputf.txt
		handsee pp = new handsee();

		if(name=="inputf.txt"){
			try{
				s1 = new Scanner(new File(name));
				while(s1.hasNext()){
					holecard1 = s1.nextInt();
					holecard2 = s1.nextInt();
					player_no = s1.nextInt();
					moneyspent = s1.nextInt();
				}
				h1 = new Card(holecard1,1);
				h2 = new Card(holecard2,1);
			}
			catch(Exception e){
				try{
					out.write("-10");
					out.close();
					}
					catch(Exception e1){
				System.out.println("cannot open file");
					}
			}
			s1.close();
		}
		// s2 is file pointer for file-- outputf.txt
		else if(name=="deals_money.txt")
		{
			try{
				s2 = new Scanner(new File(name));
				//m1 = new Scanner(new File("MoneyMatters.txt"));

				while(s2.hasNext()){
					s2.next();
					dealnum = s2.nextInt();
					for(int i=1;i<7;i++)
					{
						s2.nextInt();
						moneyleft[i] = s2.nextInt();
					//	System.out.println(moneyleft[i]);
					}
				}
				}

			catch(Exception e)
			{
				try{
					out.write("-10");
					out.close();
					}
					catch(Exception e1){
				System.out.println("cannot open file");
					}
			}
			s2.close();
		}
		//s3 is file pointer for deal.txt
		else if(name =="deal.txt")
		{
			holecards = new Card[3][7];

			//while(true)
			//{
				try{

				s3 = new Scanner(new File(name));
				s3.next();
				dealer = s3.nextInt();
				s3.next();
				stage = s3.next();
				//System.out.println(stage);
				s3.next();
				mainpot = s3.nextInt();
				int counter = 0,prevcounter=0;

				if(!stage.equals("Show_Down"))
				{
					int opponent=player_no;
					//Current playing strategy
					//System.out.println(stage);
						File k1 = new File("Fp_matters.txt");
						File k2 = new File("MoneyMatters.txt");
						File k3 = new File("FoldorNot.txt");
					if(k1.exists()){
					f1 = new Scanner(new File("Fp_matters.txt"));
					if(f1.hasNext())
						{
						int flag=1;
						if(!(f1.next().equals(stage)))
							flag = 0;
					    counter=f1.nextInt();
					    if(flag==0)
					    {
					    	prevcounter=counter;
					    	counter=0;
					    }
						playercount = 6-(f1.nextInt());
						opponent=f1.nextInt();
					/*	if(opponent>player_no)
							prevcounter=prevcounter-1; */

						}
					}
					filepointer = new FileWriter("Fp_matters.txt",false);

					if(k2.exists()){
					m1 = new Scanner(new File("MoneyMatters.txt"));
					if(m1.hasNext()){
							for(int i=1;i<7;i++)
							{
								moneyleft[i] = m1.nextInt();
								old[i]=m1.nextInt();
								//System.out.println(stage);
							}
					}
					}

					if(k3.exists()){
						f3 = new Scanner(new File("FoldorNot.txt"));
						if(f3.hasNext()){
							for(int i=1;i<7;i++)
							{
								isPlay[i] = f3.nextInt();
							}
						}
					}
					else
						for(int i=1;i<7;i++)
					{
						isPlay[i] = 1;
					}

					money = new FileWriter("MoneyMatters.txt",false);


					out = new FileWriter("outputf.txt");
					//System.out.println(stage);

					if(stage.equals("Pre_Flop")){
					String g="";
					while(!g.equals("Pre_Flop"))
					{
						g = s3.next();
					}

					String[][] current = new String[7][5];

					//\\ \\// //\\ \\// //\\ \\// //\\ \\// //\\

					for(int or=1;or<7;or++)
						for(int ir=1;ir<5;ir++)
							current[or][ir]="0";

					if(counter==0)
					{
						int runner,cnt=0;
						if(player_no>dealer)
							runner=player_no-dealer-1;
						else
							runner=player_no-dealer+5;

						if((dealer+1)%6==player_no%6&&counter==0)
						{
							runner=6;
							counter++;

						}
					else if((dealer+2)%6==player_no%6&&counter==0)
					{
						runner=7;
						counter++;
					}



					for(int er=(dealer+1);cnt<runner;er++)
					{
						if(er>6)
							er=er%6;
						if(s3.nextInt()==er)
						{
							current[er][1]=s3.next();
							if(!current[er][1].equals("-"))
							{
								current[er][2]=s3.next();
								if(current[er][1].equals("Fold"))
									isPlay[er]=0;
								current[er][4]=((Integer)moneyleft[er]).toString();
								moneyleft[er]=(Integer)(moneyleft[er]-Integer.parseInt(current[er][2])+old[er]);
							}
							else
							{
								current[er][1]="0";
								current[er][2]="0";
								current[er][3]="0";
								current[er][4]="0";
								isPlay[er]=0;
							}
							if(er==6)
								er=er%6;
							cnt++;
						}
						else System.out.println(stage+" "+counter+" "+er);
					}
					}
					else
					{
						int runner=6,cnt=0,skipper;
						if(player_no>dealer)
							skipper=player_no-dealer-1+((counter-1)*6);
						else
							skipper=player_no-dealer+5+((counter-1)*6);

						for(int sm=0;sm<skipper;sm++)
						{
							s3.next();
							if(!s3.next().equals("-"))
							{
								s3.next();
							}
						}

						for(int er=player_no;cnt<runner;er++)
						{
							if(s3.nextInt()==er)
							{
								current[er][1]=s3.next();
								if(!current[er][1].equals("-"))
								{
									current[er][2]=s3.next();
									if(current[er][1].equals("Fold"))
										isPlay[er]=0;
									current[er][4]=((Integer)moneyleft[er]).toString();
									moneyleft[er]=(Integer)(moneyleft[er]-Integer.parseInt(current[er][2])+old[er]);
								}
								else
								{
									current[er][1]="0";
									current[er][2]="0";
									current[er][3]="0";
									current[er][4]="0";
									isPlay[er]=0;
								}
								if(er==6)
									er=er%6;
								cnt++;
							}
							else System.out.println(stage+" "+counter+" "+er);
						}
					}





						playercount=0;
							for(int il=1;il<7;il++)
							{
								money.write(moneyleft[il]+"\r\t");
								money.write(current[il][2]+"\r\n");
								current[il][3]=((Integer)(6-playercount)).toString();
								playercount+=isPlay[il];
							}
							playercount=6-playercount;
					/*
						for(int m =1;m<7;m++){
							System.out.println();
							for(int n=1;n<5;n++)
								System.out.print(current[m][n]+" ");}*/

						counter++;

						int output=0;
						int maxr=0;
						opponent=player_no;


						for(int f=1;f<7;f++)
						{
							if(!current[f][1].equals("0"))
							if(current[f][1].equals("Rise")&&Integer.parseInt(current[f][2])>=maxr)
							{
								opponent=f;
								maxr=Integer.parseInt(current[f][2]);
							}
						}
			/*		int newf=0;
						if(opponent==player_no&&counter!=1)
							{
							counter--;
							newf=1;
							}*/

						filepointer.write(stage+"\r\t");
						filepointer.write(counter+"\r\t");
						filepointer.write(6-playercount+"\r\t");
						filepointer.write(opponent+"\r\t");
						filepointer.close();
						//m1.close();
						money.close();
						//f1.close();
						stages stg=new stages();

			/*			if(newf==1)
							counter++; */

						handsee hh = new handsee();
						int opponent_balance=Integer.parseInt(current[opponent][4]);
						int our_balance=moneyleft[player_no];
						Card[] cc = {h1,h2};
						double our_strength = hh.strengthfinder(cc);
						//System.out.println(opponent);
						double opponent_strength=our_strength;
						if(dealnum>10)
						{
						Scanner[] ss = new Scanner[7];
						ss[1] = new Scanner(new File("P1.txt"));
						ss[2] = new Scanner(new File("P2.txt"));
						ss[3] = new Scanner(new File("P3.txt"));
						ss[4] = new Scanner(new File("P4.txt"));
						ss[5] = new Scanner(new File("P5.txt"));
						ss[6] = new Scanner(new File("P6.txt"));
						Double strengthcatch;

						for(int ii=1;ii<7;ii++)
						{
							String k5 = "";
							while(!k5.equals("Preflop"))
								{
								k5 = ss[ii].next();
								//System.out.println(k5);
								}
							k5 = "";
							while(!(k5.equals("RiseList")))
								{
								k5 = ss[ii].next();
								//System.out.println(k5);

								//System.out.println(ii);
								}

							String vv="";
							String kk="";
							String jj =ss[ii].next();
							int flag =-1;
							int cou = 0;
							while(!jj.equals("CallList"))
							{
								//System.out.println(current[ii][2]);
								if(jj.equals(current[ii][2]))
								{
									flag=0;
									vv =ss[ii].next();
									//System.out.println(vv);
									break;
								}
								else if(Integer.parseInt(jj)>Integer.parseInt(current[ii][2]))
								{
								vv = ss[ii].next();
								}
								else
								{
								kk = ss[ii].next();
								flag = 1;
								break;
								}
								cou++;
								jj = ss[ii].next();
							}
							//System.out.println(jj);

							if(flag==1)
							{
								if(cou>1)
								strengthcatch = (Double.parseDouble(vv) + Double.parseDouble(kk))/2;
								else
									strengthcatch = Double.parseDouble(kk);
							}
							else if(flag ==0)
							{
								strengthcatch = Double.parseDouble(vv);
							}
							else
							{
								strengthcatch = 0.0;
							}
							if(opponent_strength<strengthcatch&&ii!=player_no)
							{
								opponent_strength=strengthcatch;
							}
						}
						}
						//System.out.println(dealer+""+player_no);



					    if(opponent==player_no)
						{
							output=0;
						}



						else output = stg.preflop_strategy(maxr,opponent_balance,our_balance,opponent_strength,our_strength) ;///after some process

						//SJDCVJHXVBKDFNBKNCKLVBNKGNBKCNJNBCMVC KBVJXBVB XKB KBKJBKBKJBBKBKBKDGBVKBDVKJBJHVBKJFVJKFBV!!!!
						//SJDCVJHXVBKDFNBKNCKLVBNKGNBKCNJNBCMVC KBVJXBVB XKB KBKJBKBKJBBKBKBKDGBVKBDVKJBJHVBKJFVJKFBV!!!!
						//SJDCVJHXVBKDFNBKNCKLVBNKGNBKCNJNBCMVC KBVJXBVB XKB KBKJBKBKJBBKBKBKDGBVKBDVKJBJHVBKJFVJKFBV!!!!

						if(output!=0)
						{
							int rise2=maxr-old[opponent];
								 if(output<maxr)
									 output=-10;
								 else if(output<maxr+(rise2/2))
									 	output=0;
								 	 else if(output<maxr+rise2)
								 		 	output=maxr+rise2;
								 	 	  else
								 	 	  {
								 	 		  int outr=output%BIG_BLIND;
								 	 		  output=output-outr;
								 	 		  if(output<maxr+rise2)
								 	 			  output=maxr+rise2;
								 	 	  }
						}


						folder=new FileWriter(new File("FoldorNot.txt"),false);
						for(int fg=1;fg<7;fg++)
						folder.write(isPlay[fg]+"\r\n");
						folder.close();


						out.write(""+output);
						out.close();
				}

					//SJDCVJHXVBKDFNBKNCKLVBNKGNBKCNJNBCMVC KBVJXBVB BORO XKB KBKJBKBKJBBKBKBKDGBVKBDVKJBJHVBKJFVJKFBV!!!!
					//SJDCVJHXVBKDFNBKNCKLVBNKGNBKCNJNBCMVC KBVJXBVB XKB KBKJBKBKJBBKBKBKDGBVKBDVKJBJHVBKJFVJKFBV!!!!
					//SJDCVJHXVBKDFNBKNCKLVBNKGNBKCNJNBCMVC KBVJXBVB XKB KBKJBKBKJBBKBKBKDGBVKBDVKJBJHVBKJFVJKFBV!!!!



				else if(stage.equals("Flop")){
					//System.out.println(playercount);
					String g="";

					while(!g.equals("Pre_Flop"))
					{
						g = s3.next();
					}

					String[][] current = new String[7][5];

					for(int or=1;or<7;or++)
						for(int ir=1;ir<5;ir++)
							current[or][ir]="0";
		//\\ \\// //\\ \\// //\\ \\// //\\ \\// //\\

					if(counter==0)
					{
						int runner,cnt=0,skipper;
						if(player_no>dealer)
							skipper=player_no-dealer-1+((prevcounter-1)*6);
						else
							skipper=player_no-dealer+5+((prevcounter-1)*6);
						if(opponent>player_no)
							runner=opponent-player_no;
						else runner=opponent-player_no+6;

						for(int sm=0;sm<skipper;sm++)
						{
							s3.next();
							if(!s3.next().equals("-"))
							{
								s3.next();
							}
						}

						for(int er=player_no;cnt<runner;er++)
						{
							String str=s3.next();
							int number;
							if(str.equals("End"))
							{
								number=Integer.parseInt(str);
							if(number==er)
							{
								current[er][1]=s3.next();
								if(!current[er][1].equals("-"))
								{
								 	current[er][2]=s3.next();
								 	if(current[er][1].equals("Fold"))
								 		isPlay[er]=0;
								 	current[er][4]=((Integer)moneyleft[er]).toString();
								 	moneyleft[er]=(Integer)(moneyleft[er]-Integer.parseInt(current[er][2])+old[er]);
								 }
								else
								{
									current[er][1]="0";
									current[er][2]="0";
									current[er][3]="0";
									current[er][4]="0";
									isPlay[er]=0;
								 }
								if(er==6)
									er=er%6;
								cnt++;
							 }
							else System.out.println(stage+" p"+prevcounter+" "+er);
							}
							else break;
						 }

					}





					while(!g.equals("Flop"))
					{
						g = s3.next();
					}

					boardcard[1] = s3.nextInt();
					boardcard[2] = s3.nextInt();
					boardcard[3] = s3.nextInt();
					b1 = new Card(boardcard[1],0);
					b2 = new Card(boardcard[2],0);
					b3 = new Card(boardcard[3],0);

					if(counter==0)
					{
					int runner,cnt=0;
					if(player_no>dealer)
						runner=player_no-dealer-1;
					else
						runner=player_no-dealer+5;
					for(int er=(dealer+1);cnt<runner;er++)
					{
						if(er>6)
							er=er%6;
						if(s3.nextInt()==er)
						{
							current[er][1]=s3.next();
							if(!current[er][1].equals("-"))
							{
								current[er][2]=s3.next();
								if(current[er][1].equals("Fold"))
									isPlay[er]=0;
								current[er][4]=((Integer)moneyleft[er]).toString();
								moneyleft[er]=(Integer)(moneyleft[er]-Integer.parseInt(current[er][2])+old[er]);
							}
							else
							{
								current[er][1]="0";
								current[er][2]="0";
								current[er][3]="0";
								current[er][4]="0";
								isPlay[er]=0;
							}
							if(er==6)
								er=er%6;
							cnt++;
						}
						else System.out.println(stage+" "+counter+" "+er);
					}
					}
					else
					{
						int runner=6,cnt=0,skipper;
						if(player_no>dealer)
							skipper=player_no-dealer-1+((counter-1)*6);
						else
							skipper=player_no-dealer+5+((counter-1)*6);

						for(int sm=0;sm<skipper;sm++)
						{
							s3.next();
							if(!s3.next().equals("-"))
							{
								s3.next();
							}
						}

						for(int er=player_no;cnt<runner;er++)
						{
							if(s3.nextInt()==er)
							{
								current[er][1]=s3.next();
								if(!current[er][1].equals("-"))
								{
									current[er][2]=s3.next();
									if(current[er][1].equals("Fold"))
										isPlay[er]=0;
									current[er][4]=((Integer)moneyleft[er]).toString();
									moneyleft[er]=(Integer)(moneyleft[er]-Integer.parseInt(current[er][2])+old[er]);
								}
								else
								{
									current[er][1]="0";
									current[er][2]="0";
									current[er][3]="0";
									current[er][4]="0";
									isPlay[er]=0;
								}
								if(er==6)
									er=er%6;
								cnt++;
							}
							else System.out.println(stage+" "+counter+" "+er);
						}
					}



					playercount=0;
				for(int il=1;il<7;il++)
				{
					money.write(moneyleft[il]+"\r\t");
					money.write(current[il][2]+"\r\n");
					current[il][3]=((Integer)(6-playercount)).toString();
					playercount+=isPlay[il];
				}
				playercount=6-playercount;

		/*	for(int m =1;m<7;m++){
				System.out.println();
				for(int n=1;n<5;n++)
					System.out.print(current[m][n]+" ");}*/
			counter++;
			int output=0;
			int maxr=0;
			opponent=player_no;


			for(int f=1;f<7;f++)
			{
				if(!current[f][1].equals("0"))
				if(current[f][1].equals("Rise")&&Integer.parseInt(current[f][2])>maxr)
				{
					opponent=f;
					maxr=Integer.parseInt(current[f][2]);
				}
			}
			/*
			if(opponent==player_no)
				counter--;*/


			filepointer.write(stage+"\r\t");
			filepointer.write(counter+"\r\t");
			filepointer.write(6-playercount+"\r\t");
			filepointer.write(opponent+"\r\t");
			filepointer.close();
			//m1.close();
			money.close();
			//f1.close();
			stages stg=new stages();

			handsee hh = new handsee();
			double handstrength=hh.handstrengthfinder(h1.value, h1.suit, h2.value,h2.suit, b1.value, b1.suit, b2.value, b2.suit, b3.value, b3.suit,6-playercount);
			int opponent_balance=Integer.parseInt(current[opponent][4]);
			int our_balance=moneyleft[player_no];
			double our_strength = handstrength;
			//System.out.println(opponent);
			double opponent_strength=our_strength;
			if(dealnum>10)
			{
			Scanner[] ss = new Scanner[7];
			ss[1] = new Scanner(new File("P1.txt"));
			ss[2] = new Scanner(new File("P2.txt"));
			ss[3] = new Scanner(new File("P3.txt"));
			ss[4] = new Scanner(new File("P4.txt"));
			ss[5] = new Scanner(new File("P5.txt"));
			ss[6] = new Scanner(new File("P6.txt"));
			Double strengthcatch;

			for(int ii=1;ii<7;ii++)
			{
				String k5 = "";
				while(!k5.equals("Flop"))
					{
					k5 = ss[ii].next();
					//System.out.println(k5);
					}
				k5 = "";
				while(!(k5.equals("RiseList")))
					{
					k5 = ss[ii].next();
					//System.out.println(k5);

					//System.out.println(ii);
					}

				String vv="";
				String kk="";
				String jj =ss[ii].next();
				int flag =-1;
				int cou = 0;
				while(!jj.equals("CallList"))
				{

					if(jj.equals(current[ii][2]))
					{
						flag = 0;
						vv =ss[ii].next();
					//	System.out.println(vv);
						break;
					}
					else if(Integer.parseInt(jj)>Integer.parseInt(current[ii][2]))
					{
					vv = ss[ii].next();
					}
					else
					{
					kk = ss[ii].next();
					flag = 1;
					break;
					}
					cou++;
					jj = ss[ii].next();
				}
				//System.out.println(jj);

				if(flag==1)
				{
					if(cou>1)
					strengthcatch = (Double.parseDouble(vv) + Double.parseDouble(kk))/2;
					else
					strengthcatch = Double.parseDouble(kk);

				}
				else if(flag ==0)
				{
					strengthcatch = Double.parseDouble(vv);
				}
				else
				{
					strengthcatch = 0.0;
				}
				if(opponent_strength<strengthcatch&&ii!=player_no)
				{
					opponent_strength=strengthcatch;
				}
			}
			}

			if(opponent==player_no)
			{
				output=0;
			//	counter--;
			}

			   if(maxr==0)
			    {
			    	stages vv=new stages();
			    	output=vv.checkornot(Integer.parseInt(current[player_no][4]),handstrength);
			    }

			else output = stg.postflop_strategy(maxr,opponent_balance,our_balance,opponent_strength,our_strength,handstrength) ;///after some process
		//	System.out.println(output);

			//SJDCVJHXVBKDFNBKNCKLVBNKGNBKCNJNBCMVC KBVJXBVB XKB KBKJBKBKJBBKBKBKDGBVKBDVKJBJHVBKJFVJKFBV!!!!
			//SJDCVJHXVBKDFNBKNCKLVBNKGNBKCNJNBCMVC KBVJXBVB XKB KBKJBKBKJBBKBKBKDGBVKBDVKJBJHVBKJFVJKFBV!!!!
			//SJDCVJHXVBKDFNBKNCKLVBNKGNBKCNJNBCMVC KBVJXBVB XKB KBKJBKBKJBBKBKBKDGBVKBDVKJBJHVBKJFVJKFBV!!!!

			if(output!=0)
			{
				int rise2=maxr-old[opponent];
					 if(output<maxr)
						 output=-10;
					 else if(output<maxr+(rise2/2))
						 	output=0;
					 	 else if(output<maxr+rise2)
					 		 	output=maxr+rise2;
					 	 	  else
					 	 	  {
					 	 		  int outr=output%BIG_BLIND;
					 	 		  output=output-outr;
					 	 		  if(output<maxr+rise2)
					 	 			  output=maxr+rise2;
					 	 	  }
			}

			folder=new FileWriter(new File("FoldorNot.txt"),false);
			for(int fg=1;fg<7;fg++)
			folder.write(isPlay[fg]+"\r\n");
			folder.close();


			out.write(""+output);
			out.close();
	}
				else if(stage.equals("Fourth_Street")){

					String g="";

					while(!g.equals("Flop"))
					{
						g = s3.next();
					}

					boardcard[1]=s3.nextInt();
					boardcard[2]=s3.nextInt();
					boardcard[3]=s3.nextInt();

					Card b1=new Card(boardcard[1],0);
					Card b2=new Card(boardcard[2],0);
					Card b3=new Card(boardcard[3],0);

					String[][] current = new String[7][5];

					for(int or=1;or<7;or++)
						for(int ir=1;ir<5;ir++)
							current[or][ir]="0";

					if(counter==0)
					{
						int runner,cnt=0,skipper;
						if(player_no>dealer)
							skipper=player_no-dealer-1+((prevcounter-1)*6);
						else
							skipper=player_no-dealer+5+((prevcounter-1)*6);
						if(opponent>player_no)
							runner=opponent-player_no;
						else runner=opponent-player_no+6;

						for(int sm=0;sm<skipper;sm++)
						{
							s3.next();
							if(!s3.next().equals("-"))
							{
								s3.next();
							}
						}

						for(int er=player_no;cnt<runner;er++)
						{
							String str=s3.next();
							int number;
							if(str.equals("End"))
							{
								number=Integer.parseInt(str);
							if(number==er)
							{
								current[er][1]=s3.next();
								if(!current[er][1].equals("-"))
								{
									current[er][2]=s3.next();
									if(current[er][1].equals("Fold"))
										isPlay[er]=0;
									current[er][4]=((Integer)moneyleft[er]).toString();
									moneyleft[er]=(Integer)(moneyleft[er]-Integer.parseInt(current[er][2])+old[er]);
								}
								else
								{
									current[er][1]="0";
									current[er][2]="0";
									current[er][3]="0";
									current[er][4]="0";
									isPlay[er]=0;
								}
								if(er==6)
									er=er%6;
								cnt++;
							}
							else System.out.println(stage+" p"+prevcounter+" "+er);
							}
							else break;
						}


					}



					while(!g.equals("Fourth_Street"))
					{
						g = s3.next();
					}
					boardcard[4] = s3.nextInt();
					b4 = new Card(boardcard[4],0);



					if(counter==0)
					{
					int runner,cnt=0;
					if(player_no>dealer)
						runner=player_no-dealer-1;
					else
						runner=player_no-dealer+5;
					for(int er=(dealer+1);cnt<runner;er++)
					{
						if(er>6)
							er=er%6;
						if(s3.nextInt()==er)
						{
							current[er][1]=s3.next();
							if(!current[er][1].equals("-"))
							{
								current[er][2]=s3.next();
								if(current[er][1].equals("Fold"))
									isPlay[er]=0;
								current[er][4]=((Integer)moneyleft[er]).toString();
								moneyleft[er]=(Integer)(moneyleft[er]-Integer.parseInt(current[er][2])+old[er]);
							}
							else
							{
								current[er][1]="0";
								current[er][2]="0";
								current[er][3]="0";
								current[er][4]="0";
								isPlay[er]=0;
							}
							if(er==6)
								er=er%6;
							cnt++;
						}
						else System.out.println(stage+" "+counter+" "+er);
					}
					}
					else
					{
						int runner=6,cnt=0,skipper;
						if(player_no>dealer)
							skipper=player_no-dealer-1+((counter-1)*6);
						else
							skipper=player_no-dealer+5+((counter-1)*6);

						for(int sm=0;sm<skipper;sm++)
						{
							s3.next();
							if(!s3.next().equals("-"))
							{
								s3.next();
							}
						}

						for(int er=player_no;cnt<runner;er++)
						{
							if(s3.nextInt()==er)
							{
								current[er][1]=s3.next();
								if(!current[er][1].equals("-"))
								{
									current[er][2]=s3.next();
									if(current[er][1].equals("Fold"))
										isPlay[er]=0;
									current[er][4]=((Integer)moneyleft[er]).toString();
									moneyleft[er]=(Integer)(moneyleft[er]-Integer.parseInt(current[er][2])+old[er]);
								}
								else
								{
									current[er][1]="0";
									current[er][2]="0";
									current[er][3]="0";
									current[er][4]="0";
									isPlay[er]=0;
								}
								if(er==6)
									er=er%6;
								cnt++;
							}
							else System.out.println(stage+" "+counter+" "+er);
						}
					}




			playercount=0;
				for(int il=1;il<7;il++)
				{
					money.write(moneyleft[il]+"\r\t");
					money.write(current[il][2]+"\r\n");
					current[il][3]=((Integer)(6-playercount)).toString();
					playercount+=isPlay[il];
				}
				playercount=6-playercount;


		/*	for(int m =1;m<7;m++){
				System.out.println();
				for(int n=1;n<5;n++)
					System.out.print(current[m][n]+" ");}*/
			counter++;
			int output=0;
			int maxr=0;
			opponent=player_no;


			for(int f=1;f<7;f++)
			{
				if(!current[f][1].equals("0"))
				if(current[f][1].equals("Rise")&&Integer.parseInt(current[f][2])>maxr)
				{
					opponent=f;
					maxr=Integer.parseInt(current[f][2]);
				}
			}
	/*		if(opponent==player_no&&counter!=1)
			counter--;*/
			filepointer.write(stage+"\r\t");
			filepointer.write(counter+"\r\t");
			filepointer.write(6-playercount+"\r\t");
			filepointer.write(opponent+"\r\t");
			filepointer.close();
			//m1.close();
			money.close();
			//f1.close();
			stages stg=new stages();

			handsee hh = new handsee();


			double handstrength=hh.handstrengthfinder(h1.value, h1.suit, h2.value,h2.suit, b1.value, b1.suit, b2.value, b2.suit, b3.value, b3.suit,b4.value,b4.suit,6-playercount);


			int opponent_balance=Integer.parseInt(current[opponent][4]);
			int our_balance=moneyleft[player_no];
			double our_strength = handstrength;
			//System.out.println(opponent);
			double opponent_strength=our_strength;
			if(dealnum>10)
			{
			Scanner[] ss = new Scanner[7];
			ss[1] = new Scanner(new File("P1.txt"));
			ss[2] = new Scanner(new File("P2.txt"));
			ss[3] = new Scanner(new File("P3.txt"));
			ss[4] = new Scanner(new File("P4.txt"));
			ss[5] = new Scanner(new File("P5.txt"));
			ss[6] = new Scanner(new File("P6.txt"));
			Double strengthcatch;

			for(int ii=1;ii<7;ii++)
			{
				String k5 = "";
				while(!k5.equals("Preflop"))
					{
					k5 = ss[ii].next();
					//System.out.println(k5);
					}
				k5 = "";
				while(!(k5.equals("RiseList")))
					{
					k5 = ss[ii].next();
					//System.out.println(k5);

					//System.out.println(ii);
					}

				String vv="";
				String kk="";
				String jj =ss[ii].next();
				int flag =-1;
				int cou = 0;
				while(!jj.equals("CallList"))
				{

					if(jj.equals(current[ii][2]))
					{
						flag =0;
						vv =ss[ii].next();
					//	System.out.println(vv);
						break;
					}
					else if(Integer.parseInt(jj)>Integer.parseInt(current[ii][2]))
					{
					vv = ss[ii].next();
					}
					else
					{
					kk = ss[ii].next();
					flag = 1;
					break;
					}
					cou++;
					jj = ss[ii].next();
				}
				//System.out.println(jj);

				if(flag==1)
				{
					if(cou>1)
					strengthcatch = (Double.parseDouble(vv) + Double.parseDouble(kk))/2;
					else
						strengthcatch = Double.parseDouble(kk);
				}
				else if(flag==0)
				{
					strengthcatch = Double.parseDouble(vv);
				}
				else strengthcatch = 0.0 ;
				if(opponent_strength<strengthcatch&&ii!=player_no)
				{
					opponent_strength=strengthcatch;
				}
			}
			}
			if(opponent==player_no)
			{
				output=0;
			}
			   if(maxr==0)
			    {
			    	stages vv=new stages();
			    	output=vv.checkornot(Integer.parseInt(current[player_no][4]),handstrength);
			    }

			else output = stg.postflop_strategy(maxr,opponent_balance,our_balance,opponent_strength,our_strength,handstrength) ;///after some process
			//System.out.println(output);

			if(output!=0)
			{
				int rise2=maxr-old[opponent];
					 if(output<maxr)
						 output=-10;
					 else if(output<maxr+(rise2/2))
						 	output=0;
					 	 else if(output<maxr+rise2)
					 		 	output=maxr+rise2;
					 	 	  else
					 	 	  {
					 	 		  int outr=output%BIG_BLIND;
					 	 		  output=output-outr;
					 	 		  if(output<maxr+rise2)
					 	 			  output=maxr+rise2;
					 	 	  }
			}

			folder=new FileWriter(new File("FoldorNot.txt"),false);
			for(int fg=1;fg<7;fg++)
			folder.write(isPlay[fg]+"\r\n");
			folder.close();


			out.write(""+output);
			out.close();
	}
				else if(stage.equals("Fifth_Street")){

					String g="";

					while(!g.equals("Flop"))
					{
						g = s3.next();
					}

					boardcard[1]=s3.nextInt();
					boardcard[2]=s3.nextInt();
					boardcard[3]=s3.nextInt();

					Card b1=new Card(boardcard[1],0);
					Card b2=new Card(boardcard[2],0);
					Card b3=new Card(boardcard[3],0);


					while(!g.equals("Fourth_Street"))
					{
						g = s3.next();
					}

					boardcard[4]=s3.nextInt();
					Card b4=new Card(boardcard[4],0);

					String[][] current = new String[7][5];

					for(int or=1;or<7;or++)
						for(int ir=1;ir<5;ir++)
							current[or][ir]="0";

					if(counter==0)
					{
						int runner,cnt=0,skipper;
						if(player_no>dealer)
							skipper=player_no-dealer-1+((prevcounter-1)*6);
						else
							skipper=player_no-dealer+5+((prevcounter-1)*6);
						if(opponent>player_no)
							runner=opponent-player_no;
						else runner=opponent-player_no+6;

						for(int sm=0;sm<skipper;sm++)
						{
							s3.next();
							if(!s3.next().equals("-"))
							{
								s3.next();
							}
						}

						for(int er=player_no;cnt<runner;er++)
						{
							String str=s3.next();
							int number;
							if(str.equals("End"))
							{
								number=Integer.parseInt(str);
							if(number==er)
							{
								current[er][1]=s3.next();
								if(!current[er][1].equals("-"))
								{
									current[er][2]=s3.next();
									if(current[er][1].equals("Fold"))
										isPlay[er]=0;
									current[er][4]=((Integer)moneyleft[er]).toString();
									moneyleft[er]=(Integer)(moneyleft[er]-Integer.parseInt(current[er][2])+old[er]);
								}
								else
								{
									current[er][1]="0";
									current[er][2]="0";
									current[er][3]="0";
									current[er][4]="0";
									isPlay[er]=0;
								}
								if(er==6)
									er=er%6;
								cnt++;
							}
							else System.out.println(stage+" p"+prevcounter+" "+er);
							}
							else break;
						}

					}



					while(!g.equals("Fifth_Street"))
					{
						g = s3.next();
					}
					boardcard[5] = s3.nextInt();
					b5 = new Card(boardcard[5],0);



					if(counter==0)
					{
					int runner,cnt=0;
					if(player_no>dealer)
						runner=player_no-dealer-1;
					else
						runner=player_no-dealer+5;
					for(int er=(dealer+1);cnt<runner;er++)
					{
						if(er>6)
							er=er%6;
						if(s3.nextInt()==er)
						{
							current[er][1]=s3.next();
							if(!current[er][1].equals("-"))
							{
								current[er][2]=s3.next();
								if(current[er][1].equals("Fold"))
									isPlay[er]=0;
								current[er][4]=((Integer)moneyleft[er]).toString();
								moneyleft[er]=(Integer)(moneyleft[er]-Integer.parseInt(current[er][2])+old[er]);
							}
							else
							{
								current[er][1]="0";
								current[er][2]="0";
								current[er][3]="0";
								current[er][4]="0";
								isPlay[er]=0;
							}
							if(er==6)
								er=er%6;
							cnt++;
						}
						else System.out.println(stage+" "+counter+" "+er);
					}
					}
					else
					{
						int runner=6,cnt=0,skipper;
						if(player_no>dealer)
							skipper=player_no-dealer-1+((counter-1)*6);
						else
							skipper=player_no-dealer+5+((counter-1)*6);

						for(int sm=0;sm<skipper;sm++)
						{
							s3.next();
							if(!s3.next().equals("-"))
							{
								s3.next();
							}
						}

						for(int er=player_no;cnt<runner;er++)
						{
							if(s3.nextInt()==er)
							{
								current[er][1]=s3.next();
								if(!current[er][1].equals("-"))
								{
									current[er][2]=s3.next();
									if(current[er][1].equals("Fold"))
										isPlay[er]=0;
									current[er][4]=((Integer)moneyleft[er]).toString();
									moneyleft[er]=(Integer)(moneyleft[er]-Integer.parseInt(current[er][2])+old[er]);
								}
								else
								{
									current[er][1]="0";
									current[er][2]="0";
									current[er][3]="0";
									current[er][4]="0";
									isPlay[er]=0;
								}
								if(er==6)
									er=er%6;
								cnt++;
							}
							else System.out.println(stage+" "+counter+" "+er);
						}
					}



			playercount=0;
				for(int il=1;il<7;il++)
				{
					money.write(moneyleft[il]+"\r\t");
					money.write(current[il][2]+"\r\n");
					current[il][3]=((Integer)(6-playercount)).toString();
					playercount+=isPlay[il];
				}
				playercount=6-playercount;


		/*	for(int m =1;m<7;m++){
				System.out.println();
				for(int n=1;n<5;n++)
					System.out.print(current[m][n]+" ");}*/
			counter++;
			int output=0;
			int maxr=0;
			opponent=player_no;


			for(int f=1;f<7;f++)
			{
				if(!current[f][1].equals("0"))
				if(current[f][1].equals("Rise")&&Integer.parseInt(current[f][2])>maxr)
				{
					opponent=f;
					maxr=Integer.parseInt(current[f][2]);
				}

			}
		/*	if(opponent==player_no)
				counter--;*/

			filepointer.write(stage+"\r\t");
			filepointer.write(counter+"\r\t");
			filepointer.write(6-playercount+"\r\t");
			filepointer.write(opponent+"\r\t");
			filepointer.close();
			//m1.close();
			money.close();
			//f1.close();
			stages stg=new stages();

			handsee hh = new handsee();
			double handstrength=hh.handstrengthfinder(h1.value, h1.suit, h2.value,h2.suit, b1.value, b1.suit, b2.value, b2.suit, b3.value, b3.suit,b4.value,b4.suit,b5.value,b5.suit,6-playercount);

			int opponent_balance=Integer.parseInt(current[opponent][4]);
			int our_balance=moneyleft[player_no];
			double our_strength = handstrength;
			//System.out.println(opponent);
			double opponent_strength=our_strength;
			if(dealnum>10)
			{
			Scanner[] ss = new Scanner[7];
			ss[1] = new Scanner(new File("P1.txt"));
			ss[2] = new Scanner(new File("P2.txt"));
			ss[3] = new Scanner(new File("P3.txt"));
			ss[4] = new Scanner(new File("P4.txt"));
			ss[5] = new Scanner(new File("P5.txt"));
			ss[6] = new Scanner(new File("P6.txt"));
			Double strengthcatch;

			for(int ii=1;ii<7;ii++)
			{
				String k5 = "";
				while(!k5.equals("Preflop"))
					{
					k5 = ss[ii].next();
					//System.out.println(k5);
					}
				k5 = "";
				while(!(k5.equals("RiseList")))
					{
					k5 = ss[ii].next();
					//System.out.println(k5);

					//System.out.println(ii);
					}

				String vv="";
				String kk="";
				String jj =ss[ii].next();
				int flag =-1;
				int cou = 0;
				while(!jj.equals("CallList"))
				{

					if(jj.equals(current[ii][2]))
					{
						flag = 0;
						vv =ss[ii].next();
						//System.out.println(vv);
						break;
					}
					else if(Integer.parseInt(jj)>Integer.parseInt(current[ii][2]))
					{
					vv = ss[ii].next();
					}
					else
					{
					kk = ss[ii].next();
					flag = 1;
					break;
					}
					cou++;
					jj = ss[ii].next();
				}
				//System.out.println(jj);

				if(flag==1)
				{
					if(cou>1)
					strengthcatch = (Double.parseDouble(vv) + Double.parseDouble(kk))/2;
					else
						strengthcatch = Double.parseDouble(kk);
				}
				else if(flag == 0)
				{
					strengthcatch = Double.parseDouble(vv);
				}
				else strengthcatch = 0.0;
				if(opponent_strength<strengthcatch&&ii!=player_no)
				{
					opponent_strength=strengthcatch;
				}
			}
			}

			if(opponent==player_no)
			{
				output=0;
			}
			   if(maxr==0)
			    {
			    	stages vv=new stages();
			    	output=vv.checkornot(Integer.parseInt(current[player_no][4]),handstrength);
			    }

			else output = stg.finalflop_strategy(maxr,opponent_balance,our_balance,opponent_strength,our_strength,handstrength) ;///after some process
			//System.out.println(output);

			if(output!=0)
			{
				int rise2=maxr-old[opponent];
					 if(output<maxr)
						 output=-10;
					 else if(output<maxr+(rise2/2))
						 	output=0;
					 	 else if(output<maxr+rise2)
					 		 	output=maxr+rise2;
					 	 	  else
					 	 	  {
					 	 		  int outr=output%BIG_BLIND;
					 	 		  output=output-outr;
					 	 		  if(output<maxr+rise2)
					 	 			  output=maxr+rise2;
					 	 	  }
			}

			folder=new FileWriter(new File("FoldorNot.txt"),false);
			for(int fg=1;fg<7;fg++)
			folder.write(isPlay[fg]+"\r\n");
			folder.close();

			out.write(""+output);
			out.close();
	}
				}
				else{
					//Code to keep track of previous records////////////////////////////////////////////////////////////////////////////////////
				File qq = new File("Fp_matters.txt");
				File rr = new File("MoneyMatters.txt");
				File qr = new File("FoldorNot.txt");
				qq.delete();
				rr.delete();
				qr.delete();
					String v="";
					while(!v.equals("Flop"))
					{
						v = s3.next();
					}
					boardcard[1]=s3.nextInt();
					boardcard[2]=s3.nextInt();
					boardcard[3]=s3.nextInt();

					Card b1=new Card(boardcard[1],0);
					Card b2=new Card(boardcard[2],0);
					Card b3=new Card(boardcard[3],0);


					while(!v.equals("Fourth_Street"))
					{
						v = s3.next();
					}

					boardcard[4]=s3.nextInt();
					Card b4=new Card(boardcard[4],0);
					while(!v.equals("Fifth_Street"))
					{
						v = s3.next();
					}

					boardcard[5]=s3.nextInt();
					Card b5=new Card(boardcard[5],0);

				while(!v.equals("Show_Down")){
					v = s3.next();
					//System.out.println(v);
				}
				for(int i=1;i<7;i++){
					//s3.nextLine();
					s3.nextInt();
					int temp1 = s3.nextInt();
					//System.out.print(temp1+" ");
					int temp2 = s3.nextInt();
					//System.out.println(temp2);
					holecards[1][i] = new Card(temp1,1);
					holecards[2][i] = new Card(temp2,1);
				}
				s3.close();
				s3 = new Scanner(new File(name));
				h[1] = new FileWriter("Player1.txt",true);
				h[2] = new FileWriter("Player2.txt",true);
				h[3] = new FileWriter("Player3.txt",true);
				h[4] = new FileWriter("Player4.txt",true);
				h[5] = new FileWriter("Player5.txt",true);
				h[6] = new FileWriter("Player6.txt",true);
				for(int i=1;i<7;i++){
					h[i].write("Deal\t" + dealnum + "\r\n" );
					h[i].write("Amount Left\t");
					h[i].write(moneyleft[i]+"\r\n");
					h[i].write("Hand\t" + holecards[1][i].n + "\t" + holecards[2][i].n+"\r\n");
					h[i].write(	"Board\t" + boardcard[1] +"\t" + boardcard[2] + "\t" + boardcard[3]+ "\t" + boardcard[4]+"\t" + boardcard[5]+ "\r\n");
					h[i].write("Stage\t");
					h[i].write("Action\t");
					h[i].write("Amount\t");
					h[i].write("NPP\t");
					h[i].write("HS\t");
					h[i].write("\r\nPreflop\r\t");
				}
				// headings and Preflop...........
				s3 = new Scanner(new File("deal.txt"));
				int i=1;
				String k="";
				for(int p=0;p<6;p++)
				{
						s3.next();
				}
					while(!k.equals("Pre_Flop")){
					k = s3.next();
					}

					while(!(k.equals("End")))
					{
						k = s3.next();
						//System.out.println(k);
						if(k.endsWith("End"))
							break;
						i = Integer.parseInt(k);
						k = s3.next();

						if(k.equals("Fold")) {
							playercount++;
						}

						Card[] c = {holecards[1][i],holecards[2][i]};
						if(!(k.equals("End")||k.equals("-"))){
						h[i].write(k+"\r\t");
						h[i].write(s3.nextInt()+"\r\t");
						h[i].write(6-playercount+"\r\t");
						h[i].write(pp.strengthfinder(c)+"\r\n\t");
						//System.out.println();
						}
					}

		//postflop..............


				s3 = new Scanner(new File("deal.txt"));
			for(int p=0;p<6;p++)
				{
					s3.next();
				}
				while(!k.equals("Flop")){
				k = s3.next();
				}
				for(int o=1;o<7;o++)
				{
					h[o].write("End\r\nFlop\r\t");
				}

				for(int o=1;o<4;o++)
				{
					s3.next();
				}
				k = "";
				while(!(k.equals("End")))
				{
					k = s3.next();
					if(k.equals("End"))
						break;
					i = Integer.parseInt(k);
					k = s3.next();
					//System.out.println(k);
					if(k.equals("Fold")) {
						playercount++;
					}
					if(!(k.equals("End")||k.equals("-"))){
					h[i].write(k + "\r\t");
					h[i].write(s3.nextInt()+"\r\t");
					h[i].write(6-playercount+"\r\t");
					//System.out.println("Hello");
					h[i].write(pp.handstrengthfinder(holecards[1][i].value, holecards[1][i].suit, holecards[2][i].value, holecards[2][i].suit, b1.value, b1.suit, b2.value, b2.suit, b3.value, b3.suit,6-playercount)+"\r\n\t");
					//System.out.println("Hello");
					}
				}

	// fourth street....................
				//System.out.println("Hello");
				s3 = new Scanner(new File("deal.txt"));
				for(int p=0;p<6;p++)
					{
						s3.next();
					}
					while(!k.equals("Fourth_Street")){
					k = s3.next();
					}
				for(int o=1;o<7;o++)
				{
					h[o].write("End\r\n4th\r\t");
				}
				s3.next();

				k = "";
				while(!(k.equals("End")))
				{
					k = s3.next();
					if(k.equals("End"))
							break;
					i = Integer.parseInt(k);
					k = s3.next();
					if(k.equals("Fold"))
						playercount++;
					if(!(k.equals("End")||k.equals("-"))){
					h[i].write(k+"\r\t");
					h[i].write(s3.nextInt()+"\r\t");
					h[i].write(6-playercount+"\r\t");
					h[i].write(pp.handstrengthfinder(holecards[1][i].value, holecards[1][i].suit, holecards[2][i].value, holecards[2][i].suit, b1.value, b1.suit, b2.value, b2.suit, b3.value, b3.suit,b4.value,b4.suit,6-playercount)+"\r\n\t");
					}
				}
	// 5th street	...........................
				s3 = new Scanner(new File("deal.txt"));
				for(int p=0;p<6;p++)
					{
						s3.next();
					}
					while(!k.equals("Fifth_Street")){
					k = s3.next();
					}
				for(int o=1;o<7;o++)
				{
					h[o].write("End\r\n5th\r\t");
				}

				s3.next();
				while(!(k.equals("End")))
				{
					k = s3.next();
					if(k.equals("End"))
						break;
					if(k.equals("Fold")) {
						playercount++;
					}
					i = Integer.parseInt(k);
					k = s3.next();
					if(!(k.equals("End")||k.equals("-"))){
					h[i].write(k+"\r\t");
					h[i].write(s3.nextInt()+"\r\t");
					h[i].write(6-playercount+"\r\t");
					h[i].write(pp.handstrengthfinder(holecards[1][i].value, holecards[1][i].suit, holecards[2][i].value, holecards[2][i].suit, b1.value, b1.suit, b2.value, b2.suit, b3.value, b3.suit,b4.value,b4.suit,b5.value,b5.suit,6-playercount)+"\r\n\t");
					}
				}
				for(int o=1;o<7;o++)
				{
					h[o].write("End\r\n");
				}

			//closing of all files............
			h[1].close();
			h[2].close();
			h[3].close();
			h[4].close();
			h[5].close();
			h[6].close();
			ImprovedFileCreator mm = new ImprovedFileCreator();
			if(dealnum%10==0 && dealnum != 0)
			{
				//new FileWriter(new File("P1.txt"),false);
				//new FileWriter(new File("P2.txt"),false);
				//new FileWriter(new File("P3.txt"),false);
				//new FileWriter(new File("P4.txt"),false);
				//new FileWriter(new File("P5.txt"),false);
				//new FileWriter(new File("P6.txt"),false);
				mm.create();
				new FileWriter(new File("Player1.txt"),false);
				new FileWriter(new File("Player2.txt"),false);
				new FileWriter(new File("Player3.txt"),false);
				new FileWriter(new File("Player4.txt"),false);
				new FileWriter(new File("Player5.txt"),false);
				new FileWriter(new File("Player6.txt"),false);
			}
				}//else braces ends
		}// try block ends

			catch(Exception e)
			{
				try{
				out.write("-10");
				out.close();
				}
				catch(Exception e1){
				System.out.println("Player file cannot be made");}
			}
		}
	}
}
///////////////////////////////////////////////////////////////////////////////


class handsee {

	public double ispstraight(Card c1, Card c2, Card c3, Card c4,Card c5)
	{
		Card[] c={c1,c2,c3,c4,c5};
		double mstrength=0;
		for(int i=0;i<2;i++)
		{
			int scnt=1,hcnt=0,j;
			if(c[i].hand==1)
				hcnt++;
			double strength=0;
			for(j=i+1;j<5;j++)
				{
				if(c[j].value-c[i].value<5)
					{
					if(c[j].value!=c[j-1].value)
					scnt++;
					if(c[j].hand==1)
						hcnt++;
					}

				}

			if(scnt==4)
				if(hcnt==2)
					strength=3.5+(c[i+scnt-1].value*.04);
				else strength=1+(c[i+scnt-1].value*.04);

			if(scnt==5)
				strength=(7.5+(c[i+scnt-1].value*.04));

			if(mstrength<=strength)
			{
				mstrength=strength;
			}

		}

		if(c[4].value==12)
		{
			int scnt=1,hcnt=0,j;
			double strength=0;
		for(j=0;j<4;j++)
		{
		if(c[j].value<4)
			{
			if(j!=0)
			if(c[j].value==c[j-1].value)
				scnt--;
			scnt++;
			if(c[j].hand==1)
				hcnt++;
			}
		else break;

		}
		if(scnt==4)
			if(hcnt==2)
				strength=3.5+(3*.04);
			else strength=1+(3*.04);

		if(scnt==5)
			strength=(7.5+(3*.04));

		if(mstrength<=strength)
		{
			mstrength=strength;
		}

		}


		return mstrength;
	}

	public double ispflush(Card c1, Card c2, Card c3, Card c4,Card c5)
	{
		Card[] c={c1,c2,c3,c4,c5};
		int maxcnt=-1,mindex=-1,maxh=-1;
		for(int i=0;i<2;i++)
		{
			int scnt=0,hcnt=0,j,index=0;;
			for(j=i;j<5;j++)
				{
				if(c[j].suit==c[i].suit)
					{
					scnt++;
					index=j;
					if(c[j].hand==1)
						hcnt++;
					}

				}
			if(scnt>=maxcnt)
			{
				maxcnt=scnt;
				mindex=index;
				maxh=hcnt;
			}
		}
		if(maxcnt==4)
			if(maxh==2)
				return (3.5+(c[mindex].value*.04));
			else	return (1+(c[mindex].value*.04));
		if(maxcnt==5)
			return (8+(c[mindex].value*.04));
		return 0;
	}

	public double ispstrflush(Card c1, Card c2, Card c3, Card c4,Card c5)
	{
		Card[] c={c1,c2,c3,c4,c5};

		double mstrength=0;


		for(int i=0;i<2;i++)
		{
			int scnt=1,hcnt=0,j;
			if(c[i].hand==1)
				hcnt++;
			double strength=0;
			for(j=i+1;j<5;j++)
				{
				if(c[j].value-c[i].value<5&&c[j].suit==c[i].suit)
					{
					if(c[j].value!=c[j-1].value)
					scnt++;
					if(c[j].hand==1)
						hcnt++;
					}

				}

			if(scnt==4)
				if(hcnt==2)
					strength=4.5+(c[i+scnt-1].value*.04);
				else strength=1.5+(c[i+scnt-1].value*.04);

			if(scnt==5)
				strength=(9.5+(c[i+scnt-1].value*.04));

			if(mstrength<=strength)
			{
				mstrength=strength;
			}

		}

		if(c[4].value==12)
		{
			int scnt=1,hcnt=0,j;
			double strength=0;
		for(j=0;j<4;j++)
		{
		if(c[j].value<4&&c[j].suit==c[4].suit)
			{
			if(j!=0)
			if(c[j].value==c[j-1].value)
				scnt--;
			scnt++;
			if(c[j].hand==1)
				hcnt++;
			}
		else break;

		}
		if(scnt==4)
			if(hcnt==2)
				strength=4.5+(3*.04);
			else strength=1.5+(3*.04);

		if(scnt==5)
			strength=(9.5+(3*.04));

		if(mstrength<=strength)
		{
			mstrength=strength;
		}
		}
		return mstrength;
	}

	public double is2pair(Card c1, Card c2, Card c3, Card c4,Card c5)
{
	Card[] c={c1,c2,c3,c4,c5};
	int pair=0,trio=0,pindex1=-1,pindex2=-1,tindex=-1,thcnt=0,p1hcnt=0,p2hcnt=0,foakind=0,findex=-1;
	double strength=0;
	for(int i=0;i<4;i++)
	{
		int scnt=0,hcnt=0,j;
		for(j=i;j<5;j++)
			{
			if(c[j].value==c[i].value)
				{
				scnt++;
				if(c[j].hand==1)
					hcnt++;
				}
			else break;

			}
		i=j-1;
		if(scnt==2)
			{
			pair++;
			if(pindex1==-1)
				{
				pindex1=c[i].value;
				p1hcnt=hcnt;
				}
			else if(pindex2==-1)
				{
				pindex2=c[i].value;
				p2hcnt=hcnt;
				}
			}

		if(scnt==3)
		{
			trio++;
			tindex=c[i].value;
			thcnt=hcnt;
		}

		if(scnt==4)
		{
			foakind++;
			findex=c[i].value;
		}
	}

	if(foakind==1)
	{
		strength=9+(findex)*.04;
	}

	if(pair==1&&trio==1)
	{
		//full house
		strength=8.5+(pindex1*.04);
	}

	if(pair==0&&trio==1)
	{
		//trio
		if(thcnt==2)
		{
			if(tindex<6)
				strength=6+(tindex*.04);
			else
				strength=7+(tindex*.04);
		}

		if(thcnt==1)
		{
			if(tindex<6)
				strength=5.5+(tindex*.04);
			else
				strength=6.5+(tindex*.04);
		}

	}

	if(pair==1&&trio==0)
	{
		//pair
		strength=.5+(pindex1)*.04;
	}

	if(pair==2&&trio==0)
	{
		//2pair
		if(p1hcnt==1&&p2hcnt==1)
			strength=5+(pindex2)*.04;

		if(p1hcnt==0&&p2hcnt==2)
			if(pindex2<6)
			strength=2.5+(pindex2)*.04;
			else 	strength=4+(pindex2)*.04;

		if(p2hcnt==0&&p1hcnt==2)
			strength=2.5+(pindex2)*.04;

		if(p1hcnt==0&&p2hcnt==1)
			if(pindex2<6)
				strength=2+(pindex2)*.04;
			else	strength=3+(pindex2)*.04;

		if(p1hcnt==1&&p2hcnt==0)
			strength=2+(pindex1)*.04;


	}

	return strength;
}





	public double is2pair(Card c1, Card c2, Card c3, Card c4,Card c5,Card c6)
{
	Card[] c={c1,c2,c3,c4,c5,c6};
	int pair=0,trio=0,pindex1=-1,pindex2=-1,pindex3=-1,tindex1=-1,tindex2=-1,foakind=0,findex=-1;
	double strength=0;
	for(int i=0;i<5;i++)
	{
		int scnt=0,j;
		for(j=i;j<6;j++)
			{
			if(c[j].value==c[i].value)
				{
				scnt++;
				}
			else break;

			}
		i=j-1;
		if(scnt==2)
			{
			pair++;
			if(pindex1==-1)
				{
				pindex1=c[i].value;
				}
			else if(pindex2==-1)
				{
				pindex2=c[i].value;
				}
			else if(pindex3==-1)
			{
				pindex3=c[i].value;
			}
			}

		if(scnt==3)
		{
			trio++;
			if(tindex1==-1)
			{
			tindex1=c[i].value;
			}
			else if(tindex2==-1)
			{
				tindex2=c[i].value;
			}
		}

		if(scnt==4)
		{
			foakind++;
			findex=c[i].value;
		}
	}

	if(foakind==1)
	{
		strength=10+(findex)*.08;
	}

	if((pair==1&&trio==1)||trio==2)
	{
		//full house
		if(tindex2!=-1)
		strength=9+(tindex2*.08);
		else strength=9+(tindex1*.08);

	}

	if(pair==0&&trio==1)
	{
		//trio
		strength=6+(tindex1*.08);



	}

	if(pair==1&&trio==0)
	{
		//pair
		strength=1+(pindex1)*.08;
	}

	if(pair==2&&trio==0)
	{
		//2pair

		strength=5+(pindex2)*.08;


	}

	return strength;
}

	public double ispstraight(Card c1, Card c2, Card c3, Card c4,Card c5,Card c6)
{

	Card[] c={c1,c2,c3,c4,c5,c6};

	double mstrength=0;


	for(int i=0;i<3;i++)
	{
		int scnt=1,repeat=0,j;
		double strength=0;
		for(j=i+1;j<6;j++)
			{
			if(c[j].value-c[i].value<5)
				{
				if(c[j].value!=c[j-1].value)
				scnt++;
				else repeat++;
				}

			}

		if(scnt==4)
				strength=2+(c[i+scnt-1+repeat].value*.08);

		if(scnt>=5)
			strength=(7+(c[i+scnt-1+repeat].value*.08));

		if(mstrength<=strength)
		{
			mstrength=strength;
		}

	}

	if(c[4].value==12)
	{
		int scnt=1,repeat=0,j;
		double strength=0;
	for(j=0;j<4;j++)
	{
	if(c[j].value<4)
		{
		if(j!=0)
		if(c[j].value==c[j-1].value)
			repeat++;
		else scnt++;
		}
	else break;

	}
	if(scnt==4)
			strength=2+(3*.08);

	if(scnt>=5)
		strength=7+(3*.08);

	if(mstrength<=strength)
	{
		mstrength=strength;
	}

	}


	return mstrength;
}

	public double ispflush(Card c1, Card c2, Card c3, Card c4,Card c5,Card c6)
{
	Card[] c={c1,c2,c3,c4,c5,c6};
	int maxcnt=-1,mindex=-1;
	for(int i=0;i<3;i++)
	{
		int scnt=0,j,index=0;;
		for(j=i;j<6;j++)
			{
			if(c[j].suit==c[i].suit)
				{
				scnt++;
				index=j;

				}

			}
		if(scnt>=maxcnt)
		{
			maxcnt=scnt;
			mindex=index;
		}
	}
	if(maxcnt==4)
			return (3+(c[mindex].value*.08));

	if(maxcnt>=5)
		return (8+(c[mindex].value*.08));
	return 0;
}

	public double ispstrflush(Card c1, Card c2, Card c3, Card c4,Card c5,Card c6)
{
	Card[] c={c1,c2,c3,c4,c5,c6};

	double mstrength=0;


	for(int i=0;i<3;i++)
	{
		int scnt=1,repeat=0,j;
		double strength=0;
		for(j=i+1;j<6;j++)
			{
			if(c[j].value-c[i].value<5&&c[j].suit==c[i].suit)
				{
				if(c[j].value!=c[j-1].value)
				scnt++;
				else repeat++;
				}

			}

		if(scnt==4)
				strength=4+(c[i+scnt-1].value*.08);

		if(scnt>=5)
			strength=(11+(c[i+scnt-1].value*.08));

		if(mstrength<=strength)
		{
			mstrength=strength;
		}

	}

	if(c[4].value==12)
	{
		int scnt=1,repeat=0,j;
		double strength=0;
	for(j=0;j<4;j++)
	{
	if(c[j].value<4&&c[j].suit==c[4].suit)
		{
		if(j!=0)
		if(c[j].value==c[j-1].value)
			repeat++;
		else scnt++;
		}
	else break;

	}
	if(scnt==4)
			strength=4+(3*.08);

	if(scnt>=5)
		strength=(11+(3*.08));

	if(mstrength<=strength)
	{
		mstrength=strength;
	}
	}
	return mstrength;
}






	public double is2pair(Card c1, Card c2, Card c3, Card c4,Card c5,Card c6,Card c7)
{
	Card[] c={c1,c2,c3,c4,c5,c6,c7};
	int pair=0,trio=0,pindex1=-1,pindex2=-1,pindex3=-1,tindex1=-1,tindex2=-1,foakind=0,findex=-1;
	double strength=0;
	for(int i=0;i<6;i++)
	{
		int scnt=0,j;
		for(j=i;j<7;j++)
			{
			if(c[j].value==c[i].value)
				{
				scnt++;

				}
			else break;
		}
		i=j-1;
		if(scnt==2)
			{
			pair++;
			if(pindex1==-1)
				{
				pindex1=c[i].value;
				}
			else if(pindex2==-1)
				{
				pindex2=c[i].value;
				}
			else if(pindex3==-1)
			{
				pindex3=c[i].value;
			}
			}

		if(scnt==3)
		{
			trio++;
			if(tindex1==-1)
			{
			tindex1=c[i].value;
			}
			else if(tindex2==-1)
			{
				tindex2=c[i].value;
			}
		}

		if(scnt==4)
		{
			foakind++;
			findex=c[i].value;
		}
	}

	if(foakind==1)
	{
		strength=10+(findex)*.08;
	}

	if((pair==1&&trio==1)||trio==2||(pair==2&&trio==1))
	{
		//full house
		if(tindex2!=-1)
		strength=9+(tindex2*.08);
		else strength=9+(tindex1*.08);

	}

	if(pair==0&&trio==1)
	{
		//trio
		strength=6+(tindex1*.08);



	}

	if(pair==1&&trio==0)
	{
		//pair
		strength=1+(pindex1)*.08;
	}

	if((pair==2||pair==3)&&trio==0)
	{
		//2pair
		if(pindex3==-1)
		strength=5+(pindex2)*.08;
		else strength=5+(pindex3)*.08;


	}

	return strength;
}

	public double ispstraight(Card c1, Card c2, Card c3, Card c4,Card c5,Card c6,Card c7)
{

	Card[] c={c1,c2,c3,c4,c5,c6,c7};

	double mstrength=0;


	for(int i=0;i<4;i++)
	{
		int scnt=1,repeat=0,j;
		double strength=0;
		for(j=i+1;j<7;j++)
			{
			if(c[j].value-c[i].value<5)
				{
				if(c[j].value!=c[j-1].value)
				scnt++;
				else repeat++;
				}

			}



		if(scnt>=5)
			strength=(7+(c[i+scnt-1+repeat].value*.08));

		if(mstrength<=strength)
		{
			mstrength=strength;
		}

	}

	if(c[4].value==12)
	{
		int scnt=1,repeat=0,j;
		double strength=0;
	for(j=0;j<4;j++)
	{
	if(c[j].value<4)
		{
		if(j!=0)
		if(c[j].value==c[j-1].value)
			repeat++;
		else scnt++;
		}
	else break;

	}


	if(scnt>=5)
		strength=7+(3*.08);

	if(mstrength<=strength)
	{
		mstrength=strength;
	}

	}


	return mstrength;
}

	public double ispflush(Card c1, Card c2, Card c3, Card c4,Card c5,Card c6,Card c7)
{
	Card[] c={c1,c2,c3,c4,c5,c6,c7};
	int maxcnt=-1,mindex=-1;
	for(int i=0;i<4;i++)
	{
		int scnt=0,j,index=0;;
		for(j=i;j<7;j++)
			{
			if(c[j].suit==c[i].suit)
				{
				scnt++;
				index=j;

				}

			}
		if(scnt>=maxcnt)
		{
			maxcnt=scnt;
			mindex=index;
		}
	}


	if(maxcnt>=5)
		return (8+(c[mindex].value*.08));
	return 0;
}

	public double ispstrflush(Card c1, Card c2, Card c3, Card c4,Card c5,Card c6,Card c7)
{
	Card[] c={c1,c2,c3,c4,c5,c6,c7};

	double mstrength=0;


	for(int i=0;i<4;i++)
	{
		int scnt=1,repeat=0,j;
		double strength=0;
		for(j=i+1;j<7;j++)
			{
			if(c[j].value-c[i].value<5&&c[j].suit==c[i].suit)
				{
				if(c[j].value!=c[j-1].value)
				scnt++;
				else repeat++;
				}

			}



		if(scnt>=5)
			strength=(11+(c[i+scnt-1].value*.08));

		if(mstrength<=strength)
		{
			mstrength=strength;
		}

	}

	if(c[4].value==12)
	{
		int scnt=1,repeat=0,j;
		double strength=0;
	for(j=0;j<4;j++)
	{
	if(c[j].value<4&&c[j].suit==c[4].suit)
		{
		if(j!=0)
		if(c[j].value==c[j-1].value)
			repeat++;
		else scnt++;
		}
	else break;

	}

	if(scnt>=5)
		strength=(11+(3*.08));

	if(mstrength<=strength)
	{
		mstrength=strength;
	}
	}
	return mstrength;
}



	public void sort(Card[] x)
	{
		for(int i=0;i<x.length;i++)
		{
			for(int j=i;j>0;j--)
			{
				if(x[j].value<x[j-1].value)
				{
					Card temp=x[j-1];
					x[j-1]=x[j];
					x[j]=temp;
				}
			}
		}

	}

	public double handstrengthfinder(int v1,int su1,int v2,int su2,int v3,int su3,int v4, int su4,int v5,int su5,int player_count)
	{
		Card c1=new Card(0,0);
		Card c2=new Card(0,0);
		Card c3=new Card(0,0);
		Card c4=new Card(0,0);
		Card c5=new Card(0,0);

		c1.value=v1;
		c1.suit=su1;
		c1.hand=1;
		c2.value=v2;
		c2.suit=su2;
		c2.hand=1;
		c3.value=v3;
		c3.suit=su3;
		c4.value=v4;
		c4.suit=su4;
		c5.value=v5;
		c5.suit=su5;

		Card[] c={c1,c2,c3,c4,c5};
		sort(c);
		double cnt=0,counter=0;
		double strength=strengthfinder(c);
		//System.out.println(strength);
		for(int i=1;i<52;i++)
		{

			for(int j=i+1;j<53;j++)
			{
				Card s1=new Card(i,1);
				Card s2=new Card(j,1);
				if(!((s1.value==v1&&s1.suit==su1)||(s2.value==v1&&s2.suit==su1)||
						(s1.value==v2&&s1.suit==su2)||(s2.value==v2&&s2.suit==su2)||
						(s1.value==v3&&s1.suit==su3)||(s2.value==v3&&s2.suit==su3)||
						(s1.value==v4&&s1.suit==su4)||(s2.value==v4&&s2.suit==su4)||
						(s1.value==v5&&s1.suit==su5)||(s2.value==v5&&s2.suit==su5)))
				{
					counter++;
					c3.value=v3;
					c3.suit=su3;
					c4.value=v4;
					c4.suit=su4;
					c5.value=v5;
					c5.suit=su5;
					Card[] a={s1,s2,c3,c4,c5};
					sort(a);
					double strength1=strengthfinder(a);
					if(strength1<strength)
						cnt++;
				}
			}
		}
		//System.out.println(counter);
		return (Math.pow((cnt/1081),player_count));
	}


	public double handstrengthfinder(int v1,int su1,int v2,int su2,int v3,int su3,int v4, int su4,int v5,int su5,int v6,int su6,int player_count)
	{
		Card c1=new Card(0,0);
		Card c2=new Card(0,0);
		Card c3=new Card(0,0);
		Card c4=new Card(0,0);
		Card c5=new Card(0,0);
		Card c6=new Card(0,0);

		c1.value=v1;
		c1.suit=su1;
		c1.hand=1;
		c2.value=v2;
		c2.suit=su2;
		c2.hand=1;
		c3.value=v3;
		c3.suit=su3;
		c4.value=v4;
		c4.suit=su4;
		c5.value=v5;
		c5.suit=su5;
		c6.value=v6;
		c6.suit=su6;

		Card[] c={c1,c2,c3,c4,c5,c6};
		sort(c);
		double cnt=0,counter=0;
		double strength=strengthfinder(c);
		//System.out.println(strength);
		for(int i=1;i<52;i++)
		{

			for(int j=i+1;j<53;j++)
			{
				Card s1=new Card(i,1);
				Card s2=new Card(j,1);
				if(!((s1.value==v1&&s1.suit==su1)||(s2.value==v1&&s2.suit==su1)||
						(s1.value==v2&&s1.suit==su2)||(s2.value==v2&&s2.suit==su2)||
						(s1.value==v3&&s1.suit==su3)||(s2.value==v3&&s2.suit==su3)||
						(s1.value==v4&&s1.suit==su4)||(s2.value==v4&&s2.suit==su4)||
						(s1.value==v5&&s1.suit==su5)||(s2.value==v5&&s2.suit==su5)||
						(s1.value==v6&&s1.suit==su6)||(s2.value==v6&&s2.suit==su6)))
				{
					counter++;
					c3.value=v3;
					c3.suit=su3;
					c4.value=v4;
					c4.suit=su4;
					c5.value=v5;
					c5.suit=su5;
					c6.value=v6;
					c6.suit=su6;
					Card[] a={s1,s2,c3,c4,c5,c6};
					sort(a);
					double strength1=strengthfinder(a);
					if(strength1<strength)
						cnt++;
				}
			}
		}
		//System.out.println(counter);
		return (Math.pow((cnt/1081),player_count));
	}

	public double handstrengthfinder(int v1,int su1,int v2,int su2,int v3,int su3,int v4, int su4,int v5,int su5,int v6,int su6,int v7,int su7,int player_count)
	{
		Card c1=new Card(0,0);
		Card c2=new Card(0,0);
		Card c3=new Card(0,0);
		Card c4=new Card(0,0);
		Card c5=new Card(0,0);
		Card c6=new Card(0,0);
		Card c7=new Card(0,0);

		c1.value=v1;
		c1.suit=su1;
		c1.hand=1;
		c2.value=v2;
		c2.suit=su2;
		c2.hand=1;
		c3.value=v3;
		c3.suit=su3;
		c4.value=v4;
		c4.suit=su4;
		c5.value=v5;
		c5.suit=su5;
		c6.value=v6;
		c6.suit=su6;
		c7.value=v7;
		c7.suit=su7;

		Card[] c={c1,c2,c3,c4,c5,c6,c7};
		sort(c);
		double cnt=0,counter=0;
		double strength=strengthfinder(c);
		//System.out.println(strength);
		for(int i=1;i<52;i++)
		{

			for(int j=i+1;j<53;j++)
			{
				Card s1=new Card(i,1);
				Card s2=new Card(j,1);
				if(!((s1.value==v1&&s1.suit==su1)||(s2.value==v1&&s2.suit==su1)||
						(s1.value==v2&&s1.suit==su2)||(s2.value==v2&&s2.suit==su2)||
						(s1.value==v3&&s1.suit==su3)||(s2.value==v3&&s2.suit==su3)||
						(s1.value==v4&&s1.suit==su4)||(s2.value==v4&&s2.suit==su4)||
						(s1.value==v5&&s1.suit==su5)||(s2.value==v5&&s2.suit==su5)||
						(s1.value==v6&&s1.suit==su6)||(s2.value==v6&&s2.suit==su6)||
						(s1.value==v7&&s1.suit==su7)||(s2.value==v7&&s2.suit==su7)))
				{
					counter++;
					c3.value=v3;
					c3.suit=su3;
					c4.value=v4;
					c4.suit=su4;
					c5.value=v5;
					c5.suit=su5;
					c6.value=v6;
					c6.suit=su6;
					c7.value=v7;
					c7.suit=su7;
					Card[] a={s1,s2,c3,c4,c5,c6,c7};
					sort(a);
					double strength1=strengthfinder(a);
					if(strength1<strength)
						cnt++;
				}
			}
		}
		//System.out.println(counter);
		return (Math.pow((cnt/1081),player_count));
	}



	public double strengthfinder(Card[] c)
	{
		handsee h=new handsee();
		double[] s=new double[4];
		double max=0;
		if(c.length==2){
		max = preflopstrength(c[0],c[1]);
		}
		if(c.length==5)
		{
			max=c[4].value*.04;
		s[1]=h.is2pair(c[0], c[1], c[2], c[3], c[4]);
		s[2]=h.ispstrflush(c[0], c[1], c[2], c[3], c[4]);
		s[3]=h.ispstraight(c[0], c[1], c[2], c[3], c[4]);
		s[0]=h.ispflush(c[0], c[1], c[2], c[3], c[4]);
		for(int i=0;i<4;i++)
			if(s[i]>max)
				max=s[i];
		}

		if(c.length==6)
		{
			max=c[5].value*.08;
		s[1]=h.is2pair(c[0], c[1], c[2], c[3], c[4],c[5]);
		s[2]=h.ispstrflush(c[0], c[1], c[2], c[3], c[4],c[5]);
		s[3]=h.ispstraight(c[0], c[1], c[2], c[3], c[4],c[5]);
		s[0]=h.ispflush(c[0], c[1], c[2], c[3], c[4],c[5]);
		for(int i=0;i<4;i++)
			if(s[i]>max)
				max=s[i];
		}

		if(c.length==7)
		{
		max=c[6].value*.08;
		s[1]=h.is2pair(c[0], c[1], c[2], c[3], c[4],c[5],c[6]);
		s[2]=h.ispstrflush(c[0], c[1], c[2], c[3], c[4],c[5],c[6]);
		s[3]=h.ispstraight(c[0], c[1], c[2], c[3], c[4],c[5],c[6]);
		s[0]=h.ispflush(c[0], c[1], c[2], c[3], c[4],c[5],c[6]);
		for(int i=0;i<4;i++)
			if(s[i]>max)
				max=s[i];
		}
		return max;

	}
	int[][] prestrength = new int[53][53];
	public void assignpreflopstrength()
	{
	    prestrength[13][26]=10;
	    prestrength[26][39]=10;
	    prestrength[39][52]=10;
	    prestrength[13][39]=10;
	    prestrength[13][52]=10;
	    prestrength[26][52]=10;
	    prestrength[12][25]=10;
	    prestrength[12][38]=10;
	    prestrength[12][51]=10;
	    prestrength[25][38]=10;
	    prestrength[25][51]=10;
	    prestrength[38][51]=10;
	    prestrength[11][24]=10;
	    prestrength[11][37]=10;
	    prestrength[11][50]=10;
	    prestrength[24][37]=10;
	    prestrength[24][50]=10;
	    prestrength[37][50]=10;
	    prestrength[10][23]=10;
	    prestrength[10][36]=10;
	    prestrength[10][49]=10;
	    prestrength[23][36]=10;
	    prestrength[23][49]=10;
	    prestrength[36][49]=10;
	    prestrength[9][22]=10;
	    prestrength[9][35]=10;
	    prestrength[9][48]=10;
	    prestrength[22][35]=10;
	    prestrength[22][48]=10;
	    prestrength[35][48]=10;
	    prestrength[12][13]=10;
	    prestrength[13][25]=10;
	    prestrength[13][38]=10;
	    prestrength[13][51]=10;
	    prestrength[12][26]=10;
	    prestrength[25][26]=10;
	    prestrength[26][38]=10;
	    prestrength[26][51]=10;
	    prestrength[12][39]=10;
	    prestrength[25][39]=10;
	    prestrength[38][39]=10;
	    prestrength[39][51]=10;
	    prestrength[12][52]=10;
	    prestrength[25][52]=10;
	    prestrength[38][52]=10;
	    prestrength[51][52]=10;
	    prestrength[11][13]=10;
	    prestrength[11][26]=10;
	    prestrength[11][39]=10;
	    prestrength[11][52]=10;
	    prestrength[13][24]=10;
	    prestrength[24][26]=10;
	    prestrength[24][39]=10;
	    prestrength[24][52]=10;
	    prestrength[13][37]=10;
	    prestrength[26][37]=10;
	    prestrength[37][39]=10;
	    prestrength[37][52]=10;
	    prestrength[13][50]=10;
	    prestrength[26][50]=10;
	    prestrength[39][50]=10;
	    prestrength[50][52]=10;
	    prestrength[8][21]=10;
	    prestrength[8][34]=10;
	    prestrength[8][47]=10;
	    prestrength[21][34]=10;
	    prestrength[21][47]=10;
	    prestrength[34][47]=10;
	    prestrength[10][13]=10;
	    prestrength[13][23]=10;
	    prestrength[13][36]=10;
	    prestrength[13][49]=10;
	    prestrength[10][26]=10;
	    prestrength[23][26]=10;
	    prestrength[26][36]=10;
	    prestrength[26][49]=10;
	    prestrength[10][39]=10;
	    prestrength[23][39]=10;
	    prestrength[36][39]=10;
	    prestrength[39][49]=10;
	    prestrength[10][52]=10;
	    prestrength[23][52]=10;
	    prestrength[36][52]=10;
	    prestrength[49][52]=10;

	    prestrength[11][12]=10;
	    prestrength[11][25]=10;
	    prestrength[11][38]=10;
	    prestrength[11][51]=10;
	    prestrength[12][24]=10;
	    prestrength[24][25]=10;
	    prestrength[24][38]=10;
	    prestrength[24][51]=10;
	    prestrength[12][37]=10;
	    prestrength[25][37]=10;
	    prestrength[37][38]=10;
	    prestrength[37][51]=10;
	    prestrength[12][50]=10;
	    prestrength[25][50]=10;
	    prestrength[38][50]=10;
	    prestrength[50][51]=10;

	    prestrength[10][12]=10;
	    prestrength[10][25]=10;
	    prestrength[10][38]=10;
	    prestrength[10][51]=10;
	    prestrength[12][23]=10;
	    prestrength[23][25]=10;
	    prestrength[23][38]=10;
	    prestrength[23][51]=10;
	    prestrength[12][36]=10;
	    prestrength[25][36]=10;
	    prestrength[36][38]=10;
	    prestrength[36][51]=10;
	    prestrength[12][49]=10;
	    prestrength[25][49]=10;
	    prestrength[38][49]=10;
	    prestrength[49][51]=10;


	    prestrength[9][12]=10;
	    prestrength[9][25]=10;
	    prestrength[9][38]=10;
	    prestrength[9][51]=10;
	    prestrength[12][22]=10;
	    prestrength[22][25]=10;
	    prestrength[22][38]=10;
	    prestrength[22][51]=10;
	    prestrength[12][35]=10;
	    prestrength[25][35]=10;
	    prestrength[35][38]=10;
	    prestrength[35][51]=10;
	    prestrength[12][48]=10;
	    prestrength[25][48]=10;
	    prestrength[38][48]=10;
	    prestrength[48][51]=10;

	    prestrength[9][13]=10;
	    prestrength[9][26]=10;
	    prestrength[9][39]=10;
	    prestrength[9][52]=10;
	    prestrength[13][22]=10;
	    prestrength[22][26]=10;
	    prestrength[22][39]=10;
	    prestrength[22][52]=10;
	    prestrength[13][35]=10;
	    prestrength[26][35]=10;
	    prestrength[35][39]=10;
	    prestrength[35][52]=10;
	    prestrength[13][48]=10;
	    prestrength[26][48]=10;
	    prestrength[39][48]=10;
	    prestrength[48][52]=10;

	    prestrength[10][11]=10;
	    prestrength[11][23]=10;
	    prestrength[11][36]=10;
	    prestrength[11][49]=10;
	    prestrength[10][24]=10;
	    prestrength[23][24]=10;
	    prestrength[24][36]=10;
	    prestrength[24][49]=10;
	    prestrength[10][37]=10;
	    prestrength[23][37]=10;
	    prestrength[36][37]=10;
	    prestrength[37][49]=10;
	    prestrength[10][50]=10;
	    prestrength[23][50]=10;
	    prestrength[36][50]=10;
	    prestrength[49][50]=10;

	    prestrength[9][10]=10;
	    prestrength[9][23]=10;
	    prestrength[9][36]=10;
	    prestrength[9][49]=10;
	    prestrength[10][22]=10;
	    prestrength[22][23]=10;
	    prestrength[22][36]=10;
	    prestrength[22][49]=10;
	    prestrength[10][35]=10;
	    prestrength[23][35]=10;
	    prestrength[35][36]=10;
	    prestrength[35][49]=10;
	    prestrength[10][48]=10;
	    prestrength[23][48]=10;
	    prestrength[36][48]=10;
	    prestrength[48][49]=10;

	    prestrength[11][9]=10;
	    prestrength[9][24]=10;
	    prestrength[9][37]=10;
	    prestrength[9][50]=10;
	    prestrength[11][22]=10;
	    prestrength[22][24]=10;
	    prestrength[22][37]=10;
	    prestrength[22][50]=10;
	    prestrength[11][35]=10;
	    prestrength[24][35]=10;
	    prestrength[36][37]=10;
	    prestrength[35][50]=10;
	    prestrength[11][48]=10;
	    prestrength[24][48]=10;
	    prestrength[37][48]=10;
	    prestrength[48][50]=10;

	    prestrength[8][13]=10;  //A 9
	    prestrength[8][26]=10;
	    prestrength[8][39]=10;
	    prestrength[8][52]=10;
	    prestrength[13][21]=10;
	    prestrength[21][26]=10;
	    prestrength[21][39]=10;
	    prestrength[21][52]=10;
	    prestrength[13][34]=10;
	    prestrength[26][34]=10;
	    prestrength[34][39]=10;
	    prestrength[34][52]=10;
	    prestrength[13][47]=10;
	    prestrength[26][47]=10;
	    prestrength[39][47]=10;
	    prestrength[47][52]=10;

	    prestrength[8][12]=10;  //K 9
	    prestrength[8][25]=10;
	    prestrength[8][38]=10;
	    prestrength[8][51]=10;
	    prestrength[12][21]=10;
	    prestrength[21][25]=10;
	    prestrength[21][38]=10;
	    prestrength[21][51]=10;
	    prestrength[12][34]=10;
	    prestrength[25][34]=10;
	    prestrength[34][38]=10;
	    prestrength[34][51]=10;
	    prestrength[12][47]=10;
	    prestrength[25][47]=10;
	    prestrength[38][47]=10;
	    prestrength[47][51]=10;


	    prestrength[8][11]=10;  //Q 9
	    prestrength[8][24]=10;
	    prestrength[8][37]=10;
	    prestrength[8][50]=10;
	    prestrength[11][21]=10;
	    prestrength[21][24]=10;
	    prestrength[21][37]=10;
	    prestrength[21][50]=10;
	    prestrength[11][34]=10;
	    prestrength[24][34]=10;
	    prestrength[34][37]=10;
	    prestrength[34][50]=10;
	    prestrength[11][47]=10;
	    prestrength[24][47]=10;
	    prestrength[37][47]=10;
	    prestrength[47][50]=10;


	    prestrength[8][10]=10;  //J 9
	    prestrength[8][23]=10;
	    prestrength[8][36]=10;
	    prestrength[8][49]=10;
	    prestrength[10][21]=10;
	    prestrength[21][23]=10;
	    prestrength[21][36]=10;
	    prestrength[21][49]=10;
	    prestrength[10][34]=10;
	    prestrength[23][34]=10;
	    prestrength[34][36]=10;
	    prestrength[34][49]=10;
	    prestrength[10][47]=10;
	    prestrength[23][47]=10;
	    prestrength[36][47]=10;
	    prestrength[47][49]=10;

	    prestrength[7][13]=9;  //A 8
	    prestrength[7][26]=9;
	    prestrength[7][39]=9;
	    prestrength[7][52]=9;
	    prestrength[13][20]=9;
	    prestrength[20][26]=9;
	    prestrength[20][39]=9;
	    prestrength[20][52]=9;
	    prestrength[13][33]=9;
	    prestrength[26][33]=9;
	    prestrength[33][39]=9;
	    prestrength[33][52]=9;
	    prestrength[13][46]=9;
	    prestrength[26][46]=9;
	    prestrength[39][46]=9;
	    prestrength[46][52]=9;

	    prestrength[6][13]=9;  //A 7
	    prestrength[6][26]=9;
	    prestrength[6][39]=9;
	    prestrength[6][52]=9;
	    prestrength[13][19]=9;
	    prestrength[19][26]=9;
	    prestrength[19][39]=9;
	    prestrength[19][52]=9;
	    prestrength[13][32]=9;
	    prestrength[26][32]=9;
	    prestrength[32][39]=9;
	    prestrength[32][52]=9;
	    prestrength[13][45]=9;
	    prestrength[26][45]=9;
	    prestrength[39][45]=9;
	    prestrength[45][52]=9;


	    prestrength[5][13]=9;  //A 6
	    prestrength[5][26]=9;
	    prestrength[5][39]=9;
	    prestrength[5][52]=9;
	    prestrength[13][18]=9;
	    prestrength[18][26]=9;
	    prestrength[18][39]=9;
	    prestrength[18][52]=9;
	    prestrength[13][31]=9;
	    prestrength[26][31]=9;
	    prestrength[31][39]=9;
	    prestrength[31][52]=9;
	    prestrength[13][44]=9;
	    prestrength[26][44]=9;
	    prestrength[39][44]=9;
	    prestrength[44][52]=9;

	    prestrength[4][13]=9;  //A 5
	    prestrength[4][26]=9;
	    prestrength[4][39]=9;
	    prestrength[4][52]=9;
	    prestrength[13][17]=9;
	    prestrength[17][26]=9;
	    prestrength[17][39]=9;
	    prestrength[17][52]=9;
	    prestrength[13][30]=9;
	    prestrength[26][30]=9;
	    prestrength[30][39]=9;
	    prestrength[30][52]=9;
	    prestrength[13][43]=9;
	    prestrength[26][43]=9;
	    prestrength[39][43]=9;
	    prestrength[43][52]=9;


	    prestrength[4][12]=9;  //K 5
	    prestrength[4][25]=9;
	    prestrength[4][38]=9;
	    prestrength[4][51]=9;
	    prestrength[12][17]=9;
	    prestrength[17][25]=9;
	    prestrength[17][38]=9;
	    prestrength[17][51]=9;
	    prestrength[12][30]=9;
	    prestrength[25][30]=9;
	    prestrength[30][38]=9;
	    prestrength[30][51]=9;
	    prestrength[12][43]=9;
	    prestrength[25][43]=9;
	    prestrength[38][43]=9;
	    prestrength[43][51]=9;

	    prestrength[5][12]=9;  //K 6
	    prestrength[5][25]=9;
	    prestrength[5][38]=9;
	    prestrength[5][51]=9;
	    prestrength[12][18]=9;
	    prestrength[18][25]=9;
	    prestrength[18][38]=9;
	    prestrength[18][51]=9;
	    prestrength[12][31]=9;
	    prestrength[25][31]=9;
	    prestrength[31][38]=9;
	    prestrength[31][51]=9;
	    prestrength[12][44]=9;
	    prestrength[25][44]=9;
	    prestrength[38][44]=9;
	    prestrength[44][51]=9;

	    prestrength[6][12]=9;  //K 7
	    prestrength[6][25]=9;
	    prestrength[6][38]=9;
	    prestrength[6][51]=9;
	    prestrength[12][19]=9;
	    prestrength[19][25]=9;
	    prestrength[19][38]=9;
	    prestrength[19][51]=9;
	    prestrength[12][32]=9;
	    prestrength[25][32]=9;
	    prestrength[32][38]=9;
	    prestrength[32][51]=9;
	    prestrength[12][45]=9;
	    prestrength[25][45]=9;
	    prestrength[38][45]=9;
	    prestrength[45][51]=9;

	    prestrength[7][12]=9;  //K 8
	    prestrength[7][25]=9;
	    prestrength[7][38]=9;
	    prestrength[7][51]=9;
	    prestrength[12][20]=9;
	    prestrength[20][25]=9;
	    prestrength[20][38]=9;
	    prestrength[20][51]=9;
	    prestrength[12][33]=9;
	    prestrength[25][33]=9;
	    prestrength[33][38]=9;
	    prestrength[33][51]=9;
	    prestrength[12][46]=9;
	    prestrength[25][46]=9;
	    prestrength[38][46]=9;
	    prestrength[46][51]=9;


	    prestrength[7][11]=9;  //Q 8
	    prestrength[7][24]=9;
	    prestrength[7][37]=9;
	    prestrength[7][50]=9;
	    prestrength[11][20]=9;
	    prestrength[20][24]=9;
	    prestrength[20][37]=9;
	    prestrength[20][50]=9;
	    prestrength[11][33]=9;
	    prestrength[24][33]=9;
	    prestrength[33][37]=9;
	    prestrength[33][50]=9;
	    prestrength[11][46]=9;
	    prestrength[24][46]=9;
	    prestrength[37][46]=9;
	    prestrength[46][50]=9;


	    prestrength[6][11]=9;  //Q 7
	    prestrength[6][24]=9;
	    prestrength[6][37]=9;
	    prestrength[6][50]=9;
	    prestrength[11][19]=9;
	    prestrength[19][24]=9;
	    prestrength[19][37]=9;
	    prestrength[19][50]=9;
	    prestrength[11][32]=9;
	    prestrength[24][32]=9;
	    prestrength[32][37]=9;
	    prestrength[32][50]=9;
	    prestrength[11][45]=9;
	    prestrength[24][45]=9;
	    prestrength[37][45]=9;
	    prestrength[45][50]=9;


	    prestrength[5][11]=9;  //Q 6
	    prestrength[5][24]=9;
	    prestrength[5][37]=9;
	    prestrength[5][50]=9;
	    prestrength[11][18]=9;
	    prestrength[18][24]=9;
	    prestrength[18][37]=9;
	    prestrength[18][50]=9;
	    prestrength[11][31]=9;
	    prestrength[24][31]=9;
	    prestrength[31][37]=9;
	    prestrength[31][50]=9;
	    prestrength[11][44]=9;
	    prestrength[24][44]=9;
	    prestrength[37][44]=9;
	    prestrength[44][50]=9;


	    prestrength[4][11]=9;  //Q 5
	    prestrength[4][24]=8;
	    prestrength[4][37]=8;
	    prestrength[4][50]=8;
	    prestrength[11][17]=9;
	    prestrength[17][24]=9;
	    prestrength[17][37]=8;
	    prestrength[17][50]=8;
	    prestrength[11][30]=8;
	    prestrength[24][30]=9;
	    prestrength[30][37]=9;
	    prestrength[30][50]=8;
	    prestrength[11][43]=8;
	    prestrength[24][43]=8;
	    prestrength[37][43]=9;
	    prestrength[43][50]=9;

	     prestrength[5][10]=9;  //J 6
	    prestrength[5][23]=8;
	    prestrength[5][36]=8;
	    prestrength[5][49]=8;
	    prestrength[10][18]=9;
	    prestrength[18][23]=9;
	    prestrength[18][36]=8;
	    prestrength[18][49]=8;
	    prestrength[10][31]=8;
	    prestrength[23][31]=9;
	    prestrength[31][36]=9;
	    prestrength[31][49]=8;
	    prestrength[10][44]=8;
	    prestrength[23][44]=8;
	    prestrength[36][44]=8;
	    prestrength[44][49]=9;


	    prestrength[6][10]=9;  //J 7
	    prestrength[6][23]=8;
	    prestrength[6][36]=8;
	    prestrength[6][49]=8;
	    prestrength[10][19]=9;
	    prestrength[19][23]=9;
	    prestrength[19][36]=8;
	    prestrength[19][49]=8;
	    prestrength[10][32]=8;
	    prestrength[23][32]=9;
	    prestrength[32][36]=9;
	    prestrength[32][49]=8;
	    prestrength[10][45]=8;
	    prestrength[23][45]=8;
	    prestrength[36][45]=9;
	    prestrength[45][49]=9;

	    prestrength[7][10]=9;  //J 8
	    prestrength[7][23]=9;
	    prestrength[7][36]=9;
	    prestrength[7][49]=9;
	    prestrength[10][20]=9;
	    prestrength[20][23]=9;
	    prestrength[20][36]=9;
	    prestrength[20][49]=9;
	    prestrength[10][33]=9;
	    prestrength[23][33]=9;
	    prestrength[33][36]=9;
	    prestrength[33][49]=9;
	    prestrength[10][46]=9;
	    prestrength[23][46]=9;
	    prestrength[36][46]=9;
	    prestrength[46][49]=9;


	    prestrength[7][9]=9;  //T 8
	    prestrength[7][22]=9;
	    prestrength[7][35]=9;
	    prestrength[7][48]=9;
	    prestrength[9][20]=9;
	    prestrength[20][22]=9;
	    prestrength[20][35]=9;
	    prestrength[20][48]=9;
	    prestrength[9][33]=9;
	    prestrength[22][33]=9;
	    prestrength[33][35]=9;
	    prestrength[33][48]=9;
	    prestrength[9][46]=9;
	    prestrength[22][46]=9;
	    prestrength[35][46]=9;
	    prestrength[46][48]=9;

	    prestrength[8][9]=9;  //T 9
	    prestrength[8][22]=9;
	    prestrength[8][35]=9;
	    prestrength[8][48]=9;
	    prestrength[9][21]=9;
	    prestrength[21][22]=9;
	    prestrength[21][35]=9;
	    prestrength[21][48]=9;
	    prestrength[9][34]=9;
	    prestrength[22][34]=9;
	    prestrength[34][35]=9;
	    prestrength[34][48]=9;
	    prestrength[9][47]=9;
	    prestrength[22][47]=9;
	    prestrength[35][47]=9;
	    prestrength[47][48]=9;

	    prestrength[7][8]=9;  //9 8
	    prestrength[7][21]=9;
	    prestrength[7][34]=9;
	    prestrength[7][47]=9;
	    prestrength[8][20]=9;
	    prestrength[20][21]=9;
	    prestrength[20][34]=9;
	    prestrength[20][47]=9;
	    prestrength[8][33]=9;
	    prestrength[21][33]=9;
	    prestrength[33][34]=9;
	    prestrength[33][47]=9;
	    prestrength[8][46]=9;
	    prestrength[21][46]=9;
	    prestrength[34][46]=9;
	    prestrength[47][48]=9;

	    prestrength[7][20]=9;  //8 8
	    prestrength[7][33]=9;
	    prestrength[7][46]=9;
	    prestrength[20][33]=9;
	    prestrength[20][46]=9;
	    prestrength[33][46]=9;

	    prestrength[6][19]=9;  //7 7
	    prestrength[6][32]=9;
	    prestrength[6][45]=9;
	    prestrength[19][32]=9;
	    prestrength[19][45]=9;
	    prestrength[32][45]=9;

	    prestrength[5][18]=9;  //6 6
	    prestrength[5][31]=9;
	    prestrength[5][44]=9;
	    prestrength[18][31]=9;
	    prestrength[18][44]=9;
	    prestrength[31][44]=9;

	    prestrength[4][17]=9;   //5 5
	    prestrength[4][30]=9;
	    prestrength[4][43]=9;
	    prestrength[17][30]=9;
	    prestrength[17][43]=9;
	    prestrength[30][43]=9;

	    prestrength[3][16]=8;  //4 4
	    prestrength[3][29]=8;
	    prestrength[3][42]=8;
	    prestrength[16][29]=8;
	    prestrength[16][42]=8;
	    prestrength[29][42]=9;

	    prestrength[2][15]=8;  // 3 3
	    prestrength[2][28]=8;
	    prestrength[2][41]=8;
	    prestrength[15][28]=8;
	    prestrength[15][41]=8;
	    prestrength[28][41]=8;

	    prestrength[1][14]=8;  // 2 2
	    prestrength[1][27]=8;
	    prestrength[1][40]=8;
	    prestrength[14][27]=8;
	    prestrength[14][40]=8;
	    prestrength[27][40]=8;

	    prestrength[6][7]=8;  // 7 8
	    prestrength[6][20]=8;
	    prestrength[6][33]=8;
	    prestrength[6][46]=8;
	    prestrength[7][19]=8;
	    prestrength[19][20]=8;
	    prestrength[19][33]=8;
	    prestrength[19][46]=8;
	    prestrength[7][32]=8;
	    prestrength[20][32]=8;
	    prestrength[32][33]=8;
	    prestrength[32][46]=8;
	    prestrength[7][45]=8;
	    prestrength[20][45]=8;
	    prestrength[33][45]=8;
	    prestrength[45][46]=8;

	    prestrength[5][6]=8;  // 6 7
	    prestrength[5][19]=8;
	    prestrength[5][32]=8;
	    prestrength[5][45]=8;
	    prestrength[6][18]=8;
	    prestrength[18][19]=8;
	    prestrength[18][32]=8;
	    prestrength[18][45]=8;
	    prestrength[6][31]=8;
	    prestrength[19][31]=8;
	    prestrength[31][32]=8;
	    prestrength[31][45]=8;
	    prestrength[6][44]=8;
	    prestrength[19][44]=8;
	    prestrength[32][44]=8;
	    prestrength[44][45]=8;

	    prestrength[4][5]=8; // 5 6
	    prestrength[4][18]=8;
	    prestrength[4][31]=8;
	    prestrength[4][44]=8;
	    prestrength[5][17]=8;
	    prestrength[17][18]=8;
	    prestrength[17][31]=8;
	    prestrength[17][44]=8;
	    prestrength[5][30]=8;
	    prestrength[18][30]=8;
	    prestrength[30][31]=8;
	    prestrength[30][44]=8;
	    prestrength[5][43]=8;
	    prestrength[18][43]=8;
	    prestrength[31][43]=8;
	    prestrength[43][44]=8;

	    prestrength[3][4]=8;  // 4 5
	    prestrength[3][17]=8;
	    prestrength[3][30]=8;
	    prestrength[3][43]=8;
	    prestrength[4][16]=8;
	    prestrength[16][17]=8;
	    prestrength[16][30]=8;
	    prestrength[16][43]=8;
	    prestrength[4][29]=8;
	    prestrength[17][29]=8;
	    prestrength[29][30]=8;
	    prestrength[29][43]=8;
	    prestrength[4][42]=8;
	    prestrength[17][42]=8;
	    prestrength[30][42]=8;
	    prestrength[42][43]=8;

	    prestrength[2][3]=8; // 3 4
	    prestrength[2][16]=8;
	    prestrength[2][29]=8;
	    prestrength[2][42]=8;
	    prestrength[3][15]=8;
	    prestrength[15][16]=8;
	    prestrength[15][29]=8;
	    prestrength[15][42]=8;
	    prestrength[3][28]=8;
	    prestrength[16][28]=8;
	    prestrength[28][29]=8;
	    prestrength[28][42]=8;
	    prestrength[3][41]=8;
	    prestrength[16][41]=8;
	    prestrength[29][41]=8;
	    prestrength[41][42]=8;

	    prestrength[1][2]=8;   // 2 3
	    prestrength[1][15]=8;
	    prestrength[1][28]=8;
	    prestrength[1][41]=8;
	    prestrength[2][14]=8;
	    prestrength[14][15]=8;
	    prestrength[14][28]=8;
	    prestrength[14][41]=8;
	    prestrength[2][27]=8;
	    prestrength[15][27]=8;
	    prestrength[27][28]=8;
	    prestrength[27][41]=8;
	    prestrength[2][40]=8;
	    prestrength[15][40]=8;
	    prestrength[28][40]=8;
	    prestrength[40][41]=8;

	    prestrength[3][13]=8;  //A 4
	    prestrength[3][26]=8;
	    prestrength[3][39]=8;
	    prestrength[3][52]=8;
	    prestrength[13][16]=8;
	    prestrength[16][26]=8;
	    prestrength[16][39]=8;
	    prestrength[16][52]=8;
	    prestrength[13][29]=8;
	    prestrength[26][29]=8;
	    prestrength[29][39]=8;
	    prestrength[29][52]=8;
	    prestrength[13][42]=8;
	    prestrength[26][42]=8;
	    prestrength[39][42]=8;
	    prestrength[42][52]=8;

	     prestrength[2][13]=8;  //A 3
	    prestrength[2][26]=8;
	    prestrength[2][39]=8;
	    prestrength[2][52]=8;
	    prestrength[13][15]=8;
	    prestrength[15][26]=8;
	    prestrength[15][39]=8;
	    prestrength[15][52]=8;
	    prestrength[13][28]=8;
	    prestrength[26][28]=8;
	    prestrength[28][39]=8;
	    prestrength[28][52]=8;
	    prestrength[13][41]=8;
	    prestrength[26][41]=8;
	    prestrength[39][41]=8;
	    prestrength[41][52]=8;

	    prestrength[1][13]=8;  //A 2
	    prestrength[1][26]=8;
	    prestrength[1][39]=8;
	    prestrength[1][52]=8;
	    prestrength[13][14]=8;
	    prestrength[14][26]=8;
	    prestrength[14][39]=8;
	    prestrength[14][52]=8;
	    prestrength[13][27]=8;
	    prestrength[26][27]=8;
	    prestrength[27][39]=8;
	    prestrength[27][52]=8;
	    prestrength[13][40]=8;
	    prestrength[26][40]=8;
	    prestrength[39][40]=8;
	    prestrength[40][52]=8;

	    prestrength[3][12]=8;  //K 4
	    prestrength[3][25]=8;
	    prestrength[3][38]=8;
	    prestrength[3][51]=8;
	    prestrength[12][16]=8;
	    prestrength[16][25]=8;
	    prestrength[16][38]=8;
	    prestrength[16][51]=8;
	    prestrength[12][29]=8;
	    prestrength[25][29]=8;
	    prestrength[29][38]=8;
	    prestrength[29][51]=8;
	    prestrength[12][42]=8;
	    prestrength[25][42]=8;
	    prestrength[38][42]=8;
	    prestrength[42][51]=8;

	    prestrength[2][12]=8;  //K 3
	    prestrength[2][25]=8;
	    prestrength[2][38]=8;
	    prestrength[2][51]=8;
	    prestrength[12][15]=8;
	    prestrength[15][25]=8;
	    prestrength[15][38]=8;
	    prestrength[15][51]=8;
	    prestrength[12][28]=8;
	    prestrength[25][28]=8;
	    prestrength[28][38]=8;
	    prestrength[28][51]=8;
	    prestrength[12][41]=8;
	    prestrength[25][41]=8;
	    prestrength[38][41]=8;
	    prestrength[41][51]=8;

	    prestrength[1][12]=8;  //K 2
	    prestrength[1][25]=8;
	    prestrength[1][38]=8;
	    prestrength[1][51]=8;
	    prestrength[12][14]=8;
	    prestrength[14][25]=8;
	    prestrength[14][38]=8;
	    prestrength[14][51]=8;
	    prestrength[12][27]=8;
	    prestrength[25][27]=8;
	    prestrength[27][38]=8;
	    prestrength[27][51]=8;
	    prestrength[12][40]=8;
	    prestrength[25][40]=8;
	    prestrength[38][40]=8;
	    prestrength[40][51]=8;

	    prestrength[4][10]=8;  // J 5s
	    prestrength[17][23]=8;
	    prestrength[30][36]=8;
	    prestrength[43][49]=8;
	    prestrength[4][23]=7;
	    prestrength[4][36]=7;
	    prestrength[4][49]=7;
	    prestrength[10][17]=7;
	    prestrength[17][36]=7;
	    prestrength[17][49]=7;
	    prestrength[10][30]=7;
	    prestrength[23][30]=7;
	    prestrength[30][49]=7;
	    prestrength[10][43]=7;
	    prestrength[23][43]=7;
	    prestrength[36][43]=7;


	    prestrength[3][10]=8;  // J 4s
	    prestrength[16][23]=8;
	    prestrength[29][36]=8;
	    prestrength[42][49]=8;
	    prestrength[3][23]=7;
	    prestrength[3][36]=7;
	    prestrength[3][49]=7;
	    prestrength[10][16]=7;
	    prestrength[16][36]=7;
	    prestrength[16][49]=7;
	    prestrength[10][29]=7;
	    prestrength[23][29]=7;
	    prestrength[29][49]=7;
	    prestrength[10][42]=7;
	    prestrength[23][42]=7;
	    prestrength[36][42]=7;

	    prestrength[2][10]=8;  // J 3s
	    prestrength[15][23]=8;
	    prestrength[28][36]=8;
	    prestrength[41][49]=8;
	    prestrength[2][23]=7;
	    prestrength[2][36]=7;
	    prestrength[2][49]=7;
	    prestrength[10][15]=7;
	    prestrength[15][36]=7;
	    prestrength[15][49]=7;
	    prestrength[10][28]=7;
	    prestrength[23][28]=7;
	    prestrength[28][49]=7;
	    prestrength[10][41]=7;
	    prestrength[23][41]=7;
	    prestrength[36][41]=7;

	    prestrength[1][10]=8;  // J 2s
	    prestrength[14][23]=8;
	    prestrength[27][36]=8;
	    prestrength[40][49]=8;
	    prestrength[1][23]=7;
	    prestrength[1][36]=7;
	    prestrength[1][49]=7;
	    prestrength[10][14]=7;
	    prestrength[14][36]=7;
	    prestrength[14][49]=7;
	    prestrength[10][27]=7;
	    prestrength[23][27]=7;
	    prestrength[27][49]=7;
	    prestrength[10][40]=7;
	    prestrength[23][40]=7;
	    prestrength[36][40]=7;

	    prestrength[3][11]=8;  // Q 4s
	    prestrength[16][24]=8;
	    prestrength[29][37]=8;
	    prestrength[42][50]=8;
	    prestrength[3][24]=7;
	    prestrength[3][37]=7;
	    prestrength[3][50]=7;
	    prestrength[11][16]=7;
	    prestrength[16][37]=7;
	    prestrength[16][50]=7;
	    prestrength[11][29]=7;
	    prestrength[24][29]=7;
	    prestrength[29][50]=7;

	    prestrength[2][11]=8;  // Q 3s
	    prestrength[15][24]=8;
	    prestrength[28][37]=8;
	    prestrength[41][50]=8;
	    prestrength[2][24]=7;
	    prestrength[2][37]=7;
	    prestrength[2][50]=7;
	    prestrength[11][15]=7;
	    prestrength[15][37]=7;
	    prestrength[15][50]=7;
	    prestrength[11][28]=7;
	    prestrength[24][28]=7;
	    prestrength[28][50]=7;

	    prestrength[1][11]=8;  // Q 2s
	    prestrength[14][24]=8;
	    prestrength[27][37]=8;
	    prestrength[40][50]=8;
	    prestrength[1][24]=7;
	    prestrength[1][37]=7;
	    prestrength[1][50]=7;
	    prestrength[11][14]=7;
	    prestrength[14][37]=7;
	    prestrength[14][50]=7;
	    prestrength[11][27]=7;
	    prestrength[24][27]=7;
	    prestrength[27][50]=7;

	    prestrength[6][9]=8;    // T 7
	    prestrength[19][22]=8;
	    prestrength[32][35]=8;
	    prestrength[45][48]=8;
	    prestrength[6][22]=7;
	    prestrength[6][35]=7;
	    prestrength[6][48]=7;
	    prestrength[9][19]=7;
	    prestrength[19][35]=7;
	    prestrength[19][48]=7;
	    prestrength[9][32]=7;
	    prestrength[22][32]=7;
	    prestrength[32][48]=7;
	    prestrength[9][45]=7;
	    prestrength[22][45]=7;
	    prestrength[35][45]=7;

	    prestrength[5][9]=8;    // T 6
	    prestrength[18][22]=8;
	    prestrength[31][35]=8;
	    prestrength[44][48]=8;
	    prestrength[5][22]=7;
	    prestrength[5][35]=7;
	    prestrength[5][48]=7;
	    prestrength[9][18]=7;
	    prestrength[18][35]=7;
	    prestrength[18][48]=7;
	    prestrength[9][31]=7;
	    prestrength[22][31]=7;
	    prestrength[31][48]=7;
	    prestrength[9][44]=7;
	    prestrength[22][44]=7;
	    prestrength[35][44]=7;

	    prestrength[4][9]=8;    // T 5
	    prestrength[17][22]=8;
	    prestrength[30][35]=8;
	    prestrength[43][48]=8;
	    prestrength[4][22]=7;
	    prestrength[4][35]=7;
	    prestrength[4][48]=7;
	    prestrength[9][17]=7;
	    prestrength[17][35]=7;
	    prestrength[17][48]=7;
	    prestrength[9][30]=7;
	    prestrength[22][30]=7;
	    prestrength[30][48]=7;
	    prestrength[9][43]=7;
	    prestrength[22][43]=7;
	    prestrength[35][43]=7;

	    prestrength[3][9]=8;    // T 4
	    prestrength[16][22]=8;
	    prestrength[29][35]=8;
	    prestrength[42][48]=8;
	    prestrength[3][22]=7;
	    prestrength[3][35]=7;
	    prestrength[3][48]=7;
	    prestrength[9][16]=7;
	    prestrength[16][35]=7;
	    prestrength[16][48]=7;
	    prestrength[9][29]=7;
	    prestrength[22][29]=7;
	    prestrength[29][48]=7;
	    prestrength[9][42]=7;
	    prestrength[22][42]=7;
	    prestrength[35][42]=7;

	    prestrength[2][9]=8;    // T 3
	    prestrength[15][22]=8;
	    prestrength[28][35]=8;
	    prestrength[41][48]=8;
	    prestrength[2][22]=7;
	    prestrength[2][35]=7;
	    prestrength[2][48]=7;
	    prestrength[9][15]=7;
	    prestrength[15][35]=7;
	    prestrength[15][48]=7;
	    prestrength[9][28]=7;
	    prestrength[22][28]=7;
	    prestrength[28][48]=7;
	    prestrength[9][41]=7;
	    prestrength[22][41]=7;
	    prestrength[35][41]=7;


	    prestrength[1][9]=8;    // T 2
	    prestrength[14][22]=8;
	    prestrength[27][35]=8;
	    prestrength[40][48]=8;

	    prestrength[6][8]=8;   // 9 7s
	    prestrength[19][21]=8;
	    prestrength[32][34]=8;
	    prestrength[45][47]=8;
	    prestrength[6][21]=7;
	    prestrength[6][34]=7;
	    prestrength[6][47]=7;
	    prestrength[8][19]=7;
	    prestrength[19][34]=7;
	    prestrength[19][47]=7;
	    prestrength[8][32]=7;
	    prestrength[21][32]=7;
	    prestrength[32][47]=7;
	    prestrength[8][45]=7;
	    prestrength[21][45]=7;
	    prestrength[34][45]=7;

	    prestrength[5][8]=8;   // 9 6s
	    prestrength[18][21]=8;
	    prestrength[31][34]=8;
	    prestrength[44][47]=8;
	    prestrength[5][21]=7;
	    prestrength[5][34]=7;
	    prestrength[5][47]=7;
	    prestrength[8][18]=7;
	    prestrength[18][34]=7;
	    prestrength[18][47]=7;
	    prestrength[8][31]=7;
	    prestrength[21][31]=7;
	    prestrength[31][47]=7;
	    prestrength[8][44]=7;
	    prestrength[21][44]=7;
	    prestrength[34][44]=7;

	    prestrength[4][8]=8;   // 9 5s
	    prestrength[17][21]=8;
	    prestrength[30][34]=8;
	    prestrength[43][47]=8;

	    prestrength[3][8]=8;   // 9 4s
	    prestrength[16][21]=8;
	    prestrength[29][34]=8;
	    prestrength[42][47]=8;

	    prestrength[2][8]=8;   // 9 3s
	    prestrength[15][21]=8;
	    prestrength[28][34]=8;
	    prestrength[41][47]=8;

	   prestrength[6][7]=8;    // 8 7s
	   prestrength[19][20]=8;
	   prestrength[32][33]=8;
	   prestrength[45][46]=8;
	   prestrength[6][20]=7;
	   prestrength[6][33]=7;
	   prestrength[6][46]=7;
	   prestrength[7][19]=7;
	   prestrength[19][33]=7;
	   prestrength[19][46]=7;
	   prestrength[7][32]=7;
	   prestrength[20][32]=7;
	   prestrength[32][46]=7;
	   prestrength[7][45]=7;
	   prestrength[20][45]=7;
	   prestrength[33][45]=7;


	   prestrength[5][7]=8;    // 8 6s
	   prestrength[18][20]=8;
	   prestrength[31][33]=8;
	   prestrength[44][46]=8;
	   prestrength[5][20]=7;
	   prestrength[5][33]=7;
	   prestrength[5][46]=7;
	   prestrength[7][18]=7;
	   prestrength[18][33]=7;
	   prestrength[18][46]=7;
	   prestrength[7][31]=7;
	   prestrength[20][31]=7;
	   prestrength[31][46]=7;
	   prestrength[7][44]=7;
	   prestrength[20][44]=7;
	   prestrength[33][44]=7;

	   prestrength[4][7]=8;    // 8 5s
	   prestrength[17][20]=8;
	   prestrength[30][33]=8;
	   prestrength[43][46]=8;

	   prestrength[3][7]=8;    // 8 4s
	   prestrength[16][20]=8;
	   prestrength[29][33]=8;
	   prestrength[42][46]=8;


	   prestrength[2][7]=8;    // 8 3s
	   prestrength[15][20]=8;
	   prestrength[28][33]=8;
	   prestrength[41][46]=8;

	   prestrength[5][6]=8;  // 7 6s
	   prestrength[18][19]=8;
	   prestrength[31][32]=8;
	   prestrength[44][45]=8;
	   prestrength[5][19]=7;
	   prestrength[5][32]=7;
	   prestrength[5][45]=7;
	   prestrength[6][18]=7;
	   prestrength[18][32]=7;
	   prestrength[18][45]=7;
	   prestrength[6][31]=7;
	   prestrength[19][31]=7;
	   prestrength[31][45]=7;
	   prestrength[6][44]=7;
	   prestrength[19][44]=7;
	   prestrength[32][44]=7;


	   prestrength[4][6]=8;  // 7 5s
	   prestrength[17][19]=8;
	   prestrength[30][32]=8;
	   prestrength[43][45]=8;
	   prestrength[4][19]=7;
	   prestrength[4][32]=7;
	   prestrength[4][45]=7;
	   prestrength[6][17]=7;
	   prestrength[17][32]=7;
	   prestrength[17][45]=7;
	   prestrength[6][30]=7;
	   prestrength[19][30]=7;
	   prestrength[30][45]=7;
	   prestrength[6][43]=7;
	   prestrength[19][43]=7;
	   prestrength[32][43]=7;

	   prestrength[3][6]=8;  // 7 4s
	   prestrength[16][19]=8;
	   prestrength[29][32]=8;
	   prestrength[42][45]=8;

	   prestrength[4][5]=8;  // 6 5s
	   prestrength[17][18]=8;
	   prestrength[30][31]=8;
	   prestrength[43][44]=8;
	   prestrength[4][18]=7;
	   prestrength[4][31]=7;
	   prestrength[4][44]=7;
	   prestrength[5][17]=7;
	   prestrength[17][31]=7;
	   prestrength[17][44]=7;
	   prestrength[5][30]=7;
	   prestrength[18][30]=7;
	   prestrength[30][44]=7;
	   prestrength[5][43]=7;
	   prestrength[18][43]=7;
	   prestrength[31][43]=7;


	   prestrength[3][5]=8;  // 6 4s
	   prestrength[16][18]=8;
	   prestrength[29][31]=8;
	   prestrength[42][44]=8;
	   prestrength[3][18]=7;
	   prestrength[3][31]=7;
	   prestrength[3][44]=7;
	   prestrength[5][16]=7;
	   prestrength[16][31]=7;
	   prestrength[16][44]=7;
	   prestrength[5][29]=7;
	   prestrength[18][29]=7;
	   prestrength[29][44]=7;
	   prestrength[5][42]=7;
	   prestrength[18][42]=7;
	   prestrength[31][42]=7;

	   prestrength[2][5]=8;  // 6 3s
	   prestrength[15][18]=8;
	   prestrength[28][31]=8;
	   prestrength[41][44]=8;

	   prestrength[1][5]=8;  // 6 2s
	   prestrength[14][18]=8;
	   prestrength[27][31]=8;
	   prestrength[40][44]=8;

	   prestrength[3][4]=8;  //5 4
	   prestrength[3][17]=7;
	   prestrength[3][30]=7;
	   prestrength[3][43]=7;
	   prestrength[4][16]=7;
	   prestrength[16][17]=8;
	   prestrength[16][30]=7;
	   prestrength[16][43]=7;
	   prestrength[4][29]=7;
	   prestrength[17][29]=7;
	   prestrength[29][30]=8;
	   prestrength[29][43]=7;
	   prestrength[4][42]=7;
	   prestrength[17][42]=7;
	   prestrength[30][42]=7;
	   prestrength[42][43]=8;

	   prestrength[2][3]=8;    // 4 3
	   prestrength[2][16]=7;
	   prestrength[2][29]=7;
	   prestrength[2][42]=7;
	   prestrength[3][15]=7;
	   prestrength[15][16]=8;
	   prestrength[15][29]=7;
	   prestrength[15][42]=7;
	   prestrength[3][28]=7;
	   prestrength[16][28]=7;
	   prestrength[28][29]=8;
	   prestrength[28][42]=7;
	   prestrength[3][41]=7;
	   prestrength[16][41]=7;
	   prestrength[29][41]=7;
	   prestrength[41][42]=8;

	   prestrength[1][2]=8;   // 2 3
	   prestrength[1][15]=7;
	   prestrength[1][28]=7;
	   prestrength[1][41]=7;
	   prestrength[2][14]=7;
	   prestrength[14][15]=8;
	   prestrength[14][28]=7;
	   prestrength[14][41]=7;
	   prestrength[2][27]=7;
	   prestrength[15][27]=7;
	   prestrength[27][28]=8;
	   prestrength[27][41]=7;
	   prestrength[2][40]=7;
	   prestrength[15][40]=7;
	   prestrength[28][40]=7;
	   prestrength[40][41]=8;

	   prestrength[2][4]=8;   //5 3
	   prestrength[15][17]=8;
	   prestrength[28][30]=8;
	   prestrength[41][43]=8;

	   prestrength[1][4]=8; //5 2
	   prestrength[14][17]=8;
	   prestrength[27][30]=8;
	   prestrength[40][43]=8;

	   prestrength[1][3]=7;   //4 2
	   prestrength[14][16]=7;
	   prestrength[27][29]=7;
	   prestrength[40][42]=7;
	   int count =0;
	   int count1=0;
	   for(int i=1;i<52;i++)
	   {
	       for(int j=i+1;j<53;j++)
	       {
	           count1=count1+1;
	           if(prestrength[i][j]<7)
	           {
	               count=count+1;
	               prestrength[i][j]=6;
	           }
	       }
	   }

	}


	public double preflopstrength(Card c0,Card c1)
	{
	    assignpreflopstrength();
	    if(c0.n>c1.n)
	    {
	        return prestrength[c1.n][c0.n];
	    }
	    else return prestrength[c0.n][c1.n];
	}


}
////////////////////////////////////////////////////////////////////////////////

class ImprovedFileCreator {
	ArrayList<String> action;
	ArrayList<Integer> amount;
	ArrayList<Double> strength;
	ArrayList<Integer> rise_amount;
	ArrayList<Double> rise_strength;
	ArrayList<Integer> call_amount;
	ArrayList<Double> call_strength;
	double max_strength;
	double min_strength;
	String max_action;
	String min_action;
	int max_amount;
	int min_amount;
	public void create(){
		FileWriter[] hh = new FileWriter[7];
		RandomAccessFile[] rr = new RandomAccessFile[7];

		action = new ArrayList<String>();
		amount = new ArrayList<Integer>();
		strength = new ArrayList<Double>();
		rise_amount = new ArrayList<Integer>();
		rise_strength = new ArrayList<Double>();
		call_amount= new ArrayList<Integer>();
		call_strength = new ArrayList<Double>();
		try{
			hh[1] = new FileWriter(new File("P1.txt"),false);
			rr[1] = new RandomAccessFile(new File("Player1.txt"),"r");
			for(int i=1;i<11;i++)
			{
				String t = "";
				while(! t.equals("Preflop"))
				{
					t = rr[1].readLine();
				}
				t = "";
				while(!(t.equals("End")||t.equals("-")))
				{
					//System.out.println("h");
					rr[1].seek(rr[1].getFilePointer()+1);
					t = rr[1].readLine();
					//System.out.println(t);
					if(!(t.equals("End")||t.equals("-")))
					{
						action.add(t);
						rr[1].seek(rr[1].getFilePointer()+1);
						int z = Integer.parseInt(rr[1].readLine());
						//System.out.println(z);
						amount.add(z);
						rr[1].seek(rr[1].getFilePointer()+1);
						rr[1].readLine();
						rr[1].seek(rr[1].getFilePointer()+1);
						Double x = Double.parseDouble(rr[1].readLine());
						strength.add(x);
						//System.out.println(x);
					}
				}
			}
			//three arryalist have been made now.
				int n = strength.size();
				for(int i=n-1;i>0;i--)
					for(int j=0;j<i;j++)
						if(strength.get(j)>strength.get(j+1))
						{
							double temp = strength.get(j);
							strength.set(j,strength.get(j+1));
							strength.set(j+1,temp);
							String temp1 = action.get(j);
							action.set(j,action.get(j+1));
							action.set(j+1,temp1);
							int temp2 = amount.get(j);
							amount.set(j,amount.get(j+1));
							amount.set(j+1,temp2);
						}
				if(n>0){
				//double max_strength = strength.get(n-1);
				//double min_strength = strength.get(0);
				//String max_action = action.get(n-1);
				//String min_action = action.get(0);
				//int max_amount = amount.get(n-1);
				//int min_amount = amount.get(0);
				}
			/*	for(int i=0;i<n;i++){
					System.out.print(amount.get(i));
					System.out.print(action.get(i));
					System.out.println(strength.get(i));
		}*/
				for(int i=n-1;i>0;i--)
					for(int j=0;j<i;j++)
						if(amount.get(j)>amount.get(j+1))
						{
							int temp = amount.get(j);
							amount.set(j,amount.get(j+1));
							amount.set(j+1,temp);
							String temp1 = action.get(j);
							action.set(j,action.get(j+1));
							action.set(j+1,temp1);
							double temp2 = strength.get(j);
							strength.set(j,strength.get(j+1));
							strength.set(j+1,temp2);
						}
//calculating average strength during rise..............
				int k=0;
				double total=0;
				double total1 = 0;
				for(int m=n-1;m>-1;m--)
				{

					if(action.get(m).equals("Rise")&&amount.get(m)>10)
					{

						total +=strength.get(m);
						total1 +=amount.get(m);
						rise_amount.add(amount.get(m));
						rise_strength.add(strength.get(m));
						//System.out.println(rise_strength.get(k));
						k++;
					}
				}
				double average_rise=0;
				double average_riseamount = 0;
				if(k!=0){
				average_rise = total/k;
				average_riseamount = total1/k;
				}
				else
				{
					average_rise = 0;
					average_riseamount=0;
				}
				////calculating avergae strength during call............
				int kk =0;
				double total2= 0;
				double total3 = 0;
				for(int m=n-1;m>-1;m--)
				{
					if(action.get(m).equals("Call"))
					{
						total2 +=strength.get(m);
						total3 +=amount.get(m);
						//System.out.println(amount.get(m));
						call_amount.add(amount.get(m));
						//System.out.println();
						call_strength.add(strength.get(m));
						kk++;
					}
				}
				double average_call =0;
				double average_callamount = 0;
				if(kk!=0){
				average_call = total2/kk;
				average_callamount = total/kk;
				}
				else {
					average_call = 0;
					average_callamount = 0;
				}
				//System.out.println(average_call);

	////writing this information to P1.txt.....
				hh[1].write("Preflop\r\n");
				hh[1].write("Maxstrength==>\t"+ max_strength+"\r\n");
				hh[1].write("Minstrength==>\t"+ min_strength+"\r\n");
				hh[1].write("AverageRiseStrength==>\t"+average_rise+"\r\n");
				hh[1].write("AverageRiseAmount==>\t"+average_riseamount+"\r\n");
				hh[1].write("AverageCallStrength==>\t"+average_call+"\r\n");
				hh[1].write("AverageCallAmount==>\t"+average_callamount+"\r\n");
				hh[1].write("RiseList\r\n");
				for(int c=0;c<rise_strength.size();c++){
					hh[1].write(rise_amount.get(c)+"\t");
					hh[1].write(rise_strength.get(c)+"\t\r\n");
					}
					hh[1].write("CallList\r\n");
					for(int c=0;c<call_strength.size();c++){
						hh[1].write(call_amount.get(c)+"\t");
						hh[1].write(call_strength.get(c)+"\t\r\n");
						}
				////hh[1].close();
/////////////////////player 1 ends////////////////////////
				/////player 2 begins//////writing to file p2.txt///////////


					hh[2] = new FileWriter(new File("P2.txt"),false);
				rr[2] = new RandomAccessFile(new File("Player2.txt"),"r");
				action = new ArrayList<String>();
				amount = new ArrayList<Integer>();
				strength = new ArrayList<Double>();
				rise_amount = new ArrayList<Integer>();
				rise_strength = new ArrayList<Double>();
				call_amount= new ArrayList<Integer>();
				call_strength = new ArrayList<Double>();

				for(int i=1;i<11;i++)
				{
					String t = "";
					while(!t.equals("Preflop"))
					{
						t = rr[2].readLine();
					}
					t = "";
					while(!(t.equals("End")||t.equals("-")))
					{
						//System.out.println("h");
						rr[2].seek(rr[2].getFilePointer()+1);
						t = rr[2].readLine();
						//System.out.println(t);
						if(!(t.equals("End")||t.equals("-")))
						{
							action.add(t);
							rr[2].seek(rr[2].getFilePointer()+1);
							int z = Integer.parseInt(rr[2].readLine());
							//System.out.println(z);
							amount.add(z);
							rr[2].seek(rr[2].getFilePointer()+1);
							rr[2].readLine();
							rr[2].seek(rr[2].getFilePointer()+1);
							Double x = Double.parseDouble(rr[2].readLine());
							strength.add(x);
							//System.out.println(x);
						}
					}
				}
				//three arryalist have been made now.
					n = strength.size();
					for(int i=n-1;i>0;i--)
						for(int j=0;j<i;j++)
							if(strength.get(j)>strength.get(j+1))
							{
								double temp = strength.get(j);
								strength.set(j,strength.get(j+1));
								strength.set(j+1,temp);
								String temp1 = action.get(j);
								action.set(j,action.get(j+1));
								action.set(j+1,temp1);
								int temp2 = amount.get(j);
								amount.set(j,amount.get(j+1));
								amount.set(j+1,temp2);
							}
					if(n>0){
					max_strength = strength.get(n-1);
					min_strength = strength.get(0);
					max_action = action.get(n-1);
					min_action = action.get(0);
					max_amount = amount.get(n-1);
					min_amount = amount.get(0);
					}
				/*	for(int i=0;i<n;i++){
						System.out.print(amount.get(i));
						System.out.print(action.get(i));
						System.out.println(strength.get(i));
			}*/
					for(int i=n-1;i>0;i--)
						for(int j=0;j<i;j++)
							if(amount.get(j)>amount.get(j+1))
							{
								int temp = amount.get(j);
								amount.set(j,amount.get(j+1));
								amount.set(j+1,temp);
								String temp1 = action.get(j);
								action.set(j,action.get(j+1));
								action.set(j+1,temp1);
								double temp2 = strength.get(j);
								strength.set(j,strength.get(j+1));
								strength.set(j+1,temp2);
							}
	//calculating average strength during rise..............
					k=0;
					total=0;
					total1=0;
					for(int m=n-1;m>-1;m--)
					{

						if(action.get(m).equals("Rise")&& amount.get(m)>10)
						{

							total +=strength.get(m);
							total1 +=amount.get(m);
							rise_amount.add(amount.get(m));
							rise_strength.add(strength.get(m));
							//System.out.println(rise_strength.get(k));
							k++;
						}
					}
					if(k!=0){
					average_rise = total/k;
					average_riseamount=total1/k;
					}
					else
					{
						average_rise =0;
						average_riseamount=0;
					}
					////calculating avergae strength during call............
					kk =0;
					total2= 0;
					total3=0;
					for(int m=n-1;m>-1;m--)
					{
						if(action.get(m).equals("Call"))
						{
							total2 +=strength.get(m);
							total3 +=amount.get(m);
							//System.out.println(amount.get(m));
							call_amount.add(amount.get(m));
							//System.out.println();
							call_strength.add(strength.get(m));
							kk++;
						}
					}
					if(kk!=0)
					{
					average_call = total2/kk;
					average_callamount=total3/kk;
					}
					else
					{
						average_call = 0;
						average_callamount = 0;
					}
					//System.out.println(average_call);

		////writing this information to P2.txt.....
					hh[2].write("Preflop\r\n");
					hh[2].write("Maxstrength==>\t"+max_strength+"\r\n");
					hh[2].write("Minstrength==>\t"+min_strength+"\r\n");
					hh[2].write("AverageRiseStrength==>\t"+average_rise+"\r\n");
					hh[2].write("AverageRiseAmount==>\t"+average_riseamount+"\r\n");
					hh[2].write("AverageCallStrength==>\t"+average_call+"\r\n");
					hh[2].write("AverageCallAmount==>\t"+average_callamount+"\r\n");
					hh[2].write("RiseList\r\n");
					for(int c=0;c<rise_strength.size();c++){
						hh[2].write(rise_amount.get(c)+"\t");
						hh[2].write(rise_strength.get(c)+"\t\r\n");
						}
						hh[2].write("CallList\r\n");
						for(int c=0;c<call_strength.size();c++){
							hh[2].write(call_amount.get(c)+"\t");
							hh[2].write(call_strength.get(c)+"\t\r\n");
							}
					//hh[2].close();
	////////////////////////player 2 ends/////////
					/////player 3 begins//////writing to file P3.txt///////////


					hh[3] = new FileWriter(new File("P3.txt"),false);
					rr[3] = new RandomAccessFile(new File("Player3.txt"),"r");
					action = new ArrayList<String>();
					amount = new ArrayList<Integer>();
					strength = new ArrayList<Double>();
					rise_amount = new ArrayList<Integer>();
					rise_strength = new ArrayList<Double>();
					call_amount= new ArrayList<Integer>();
					call_strength = new ArrayList<Double>();

					for(int i=1;i<11;i++)
					{
						String t = "";
						while(! t.equals("Preflop"))
						{
							t = rr[3].readLine();
						}
						t = "";
						while(!(t.equals("End")||t.equals("-")))
						{
							//System.out.println("h");
							rr[3].seek(rr[3].getFilePointer()+1);
							t = rr[3].readLine();
							//System.out.println(t);
							if(!(t.equals("End")||t.equals("-")))
							{
								action.add(t);
								rr[3].seek(rr[3].getFilePointer()+1);
								int z = Integer.parseInt(rr[3].readLine());
								//System.out.println(z);
								amount.add(z);
								rr[3].seek(rr[3].getFilePointer()+1);
								rr[3].readLine();
								rr[3].seek(rr[3].getFilePointer()+1);
								Double x = Double.parseDouble(rr[3].readLine());
								strength.add(x);
								//System.out.println(x);
							}
						}
					}
					//three arryalist have been made now.
						n = strength.size();
						for(int i=n-1;i>0;i--)
							for(int j=0;j<i;j++)
								if(strength.get(j)>strength.get(j+1))
								{
									double temp = strength.get(j);
									strength.set(j,strength.get(j+1));
									strength.set(j+1,temp);
									String temp1 = action.get(j);
									action.set(j,action.get(j+1));
									action.set(j+1,temp1);
									int temp2 = amount.get(j);
									amount.set(j,amount.get(j+1));
									amount.set(j+1,temp2);
								}
						if(n>0){
						max_strength = strength.get(n-1);
						min_strength = strength.get(0);
						max_action = action.get(n-1);
						min_action = action.get(0);
						max_amount = amount.get(n-1);
						min_amount = amount.get(0);
						}
					/*	for(int i=0;i<n;i++){
							System.out.print(amount.get(i));
							System.out.print(action.get(i));
							System.out.println(strength.get(i));
				}*/
						for(int i=n-1;i>0;i--)
							for(int j=0;j<i;j++)
								if(amount.get(j)>amount.get(j+1))
								{
									int temp = amount.get(j);
									amount.set(j,amount.get(j+1));
									amount.set(j+1,temp);
									String temp1 = action.get(j);
									action.set(j,action.get(j+1));
									action.set(j+1,temp1);
									double temp2 = strength.get(j);
									strength.set(j,strength.get(j+1));
									strength.set(j+1,temp2);
								}
		//calculating average strength during rise..............
						k=0;
						total=0;
						total1=0;
						for(int m=n-1;m>-1;m--)
						{

							if(action.get(m).equals("Rise")&& amount.get(m)>10)
							{

								total +=strength.get(m);
								total1 +=amount.get(m);
								rise_amount.add(amount.get(m));
								rise_strength.add(strength.get(m));
								//System.out.println(rise_strength.get(k));
								k++;
							}
						}
						if(k!=0){
						average_rise = total/k;
						average_riseamount=total1/k;
						}
						else
						{
							average_rise =0;
							average_riseamount=0;
						}
						////calculating avergae strength during call............
						kk =0;
						total2= 0;
						total3=0;
						for(int m=n-1;m>-1;m--)
						{
							if(action.get(m).equals("Call"))
							{
								total2 +=strength.get(m);
								total3 +=amount.get(m);
								//System.out.println(amount.get(m));
								call_amount.add(amount.get(m));
								//System.out.println();
								call_strength.add(strength.get(m));
								kk++;
							}
						}
						if(kk!=0){
						average_call = total2/kk;
						average_callamount=total3/kk;
						}
						else
						{
							average_call = 0;
							average_callamount = 0;
						}
						//System.out.println(average_call);

			////writing this information to P3.txt.....
						hh[3].write("Preflop\r\n");
						hh[3].write("Maxstrength==>\t"+max_strength+"\r\n");
						hh[3].write("Minstrength==>\t"+min_strength+"\r\n");
						hh[3].write("AverageRiseStrength==>\t"+average_rise+"\r\n");
						hh[3].write("AverageRiseAmount==>\t"+average_riseamount+"\r\n");
						hh[3].write("AverageCallStrength==>\t"+average_call+"\r\n");
						hh[3].write("AverageCallAmount==>\t"+average_callamount+"\r\n");
						hh[3].write("RiseList\r\n");
						for(int c=0;c<rise_strength.size();c++){
							hh[3].write(rise_amount.get(c)+"\t");
							hh[3].write(rise_strength.get(c)+"\t\r\n");
							}
							hh[3].write("CallList\r\n");
							for(int c=0;c<call_strength.size();c++){
								hh[3].write(call_amount.get(c)+"\t");
								hh[3].write(call_strength.get(c)+"\t\r\n");
								}
						//hh[3].close();
////////////////////////player 3 ends/////////
						/////player 4 begins//////writing to file P4.txt///////////


						hh[4] = new FileWriter(new File("P4.txt"),false);
						rr[4] = new RandomAccessFile(new File("Player4.txt"),"r");
						action = new ArrayList<String>();
						amount = new ArrayList<Integer>();
						strength = new ArrayList<Double>();
						rise_amount = new ArrayList<Integer>();
						rise_strength = new ArrayList<Double>();
						call_amount= new ArrayList<Integer>();
						call_strength = new ArrayList<Double>();

						for(int i=1;i<11;i++)
						{
							String t = "";
							while(!(t.equals("Preflop")))
							{
								t = rr[4].readLine();
							}
							t = "";
							while(!(t.equals("End")||t.equals("-")))
							{
								//System.out.println("h");
								rr[4].seek(rr[4].getFilePointer()+1);
								t = rr[4].readLine();
								//System.out.println(t);
								if(!t.equals("End"))
								{
									action.add(t);
									rr[4].seek(rr[4].getFilePointer()+1);
									int z = Integer.parseInt(rr[4].readLine());
									//System.out.println(z);
									amount.add(z);
									rr[4].seek(rr[4].getFilePointer()+1);
									rr[4].readLine();
									rr[4].seek(rr[4].getFilePointer()+1);
									Double x = Double.parseDouble(rr[4].readLine());
									strength.add(x);
									//System.out.println(x);
								}
							}
						}
						//three arryalist have been made now.
							n = strength.size();
							for(int i=n-1;i>0;i--)
								for(int j=0;j<i;j++)
									if(strength.get(j)>strength.get(j+1))
									{
										double temp = strength.get(j);
										strength.set(j,strength.get(j+1));
										strength.set(j+1,temp);
										String temp1 = action.get(j);
										action.set(j,action.get(j+1));
										action.set(j+1,temp1);
										int temp2 = amount.get(j);
										amount.set(j,amount.get(j+1));
										amount.set(j+1,temp2);
									}
							if(n>0){
							max_strength = strength.get(n-1);
							min_strength = strength.get(0);
							max_action = action.get(n-1);
							min_action = action.get(0);
							max_amount = amount.get(n-1);
							min_amount = amount.get(0);
							}
						/*	for(int i=0;i<n;i++){
								System.out.print(amount.get(i));
								System.out.print(action.get(i));
								System.out.println(strength.get(i));
					}*/
							for(int i=n-1;i>0;i--)
								for(int j=0;j<i;j++)
									if(amount.get(j)>amount.get(j+1))
									{
										int temp = amount.get(j);
										amount.set(j,amount.get(j+1));
										amount.set(j+1,temp);
										String temp1 = action.get(j);
										action.set(j,action.get(j+1));
										action.set(j+1,temp1);
										double temp2 = strength.get(j);
										strength.set(j,strength.get(j+1));
										strength.set(j+1,temp2);
									}
			//calculating average strength during rise..............
							k=0;
							total=0;
							total1=0;
							for(int m=n-1;m>-1;m--)
							{

								if(action.get(m).equals("Rise")&& amount.get(m)>10)
								{

									total +=strength.get(m);
									total1 +=amount.get(m);
									rise_amount.add(amount.get(m));
									rise_strength.add(strength.get(m));
									//System.out.println(rise_strength.get(k));
									k++;
								}
							}
							if(k!=0){
							average_rise = total/k;
							average_riseamount=total1/k;
							}
							else
							{
								average_rise =0;
								average_riseamount=0;
							}
							////calculating avergae strength during call............
							kk =0;
							total2= 0;
							total3=0;
							for(int m=n-1;m>-1;m--)
							{
								if(action.get(m).equals("Call"))
								{
									total2 +=strength.get(m);
									total3 +=amount.get(m);
									//System.out.println(amount.get(m));
									call_amount.add(amount.get(m));
									//System.out.println();
									call_strength.add(strength.get(m));
									kk++;
								}
							}
							if(kk!=0){
							average_call = total2/kk;
							average_callamount=total3/kk;
							}
							else
							{
								average_call =  0;
								average_callamount = 0;
							}
							//System.out.println(average_call);

				////writing this information to P4.txt.....
							hh[4].write("Preflop\r\n");
							hh[4].write("Maxstrength==>\t"+max_strength+"\r\n");
							hh[4].write("Minstrength==>\t"+min_strength+"\r\n");
							hh[4].write("AverageRiseStrength==>\t"+average_rise+"\r\n");
							hh[4].write("AverageRiseAmount==>\t"+average_riseamount+"\r\n");
							hh[4].write("AverageCallStrength==>\t"+average_call+"\r\n");
							hh[4].write("AverageCallAmount==>\t"+average_callamount+"\r\n");
							hh[4].write("RiseList\r\n");
							for(int c=0;c<rise_strength.size();c++){
								hh[4].write(rise_amount.get(c)+"\t");
								hh[4].write(rise_strength.get(c)+"\t\r\n");
								}
								hh[4].write("CallList\r\n");
								for(int c=0;c<call_strength.size();c++){
									hh[4].write(call_amount.get(c)+"\t");
									hh[4].write(call_strength.get(c)+"\t\r\n");
									}
							//hh[4].close();
	////////////////////////player 4 ends/////////
							/////player 5 begins//////writing to file P5.txt///////////


							hh[5] = new FileWriter(new File("P5.txt"),false);
							rr[5] = new RandomAccessFile(new File("Player5.txt"),"r");
							action = new ArrayList<String>();
							amount = new ArrayList<Integer>();
							strength = new ArrayList<Double>();
							rise_amount = new ArrayList<Integer>();
							rise_strength = new ArrayList<Double>();
							call_amount= new ArrayList<Integer>();
							call_strength = new ArrayList<Double>();

							for(int i=1;i<11;i++)
							{
								String t = "";
								while(! t.equals("Preflop"))
								{
									t = rr[5].readLine();
								}
								t = "";
								while(!(t.equals("End")||t.equals("-")))
								{
									//System.out.println("h");
									rr[5].seek(rr[5].getFilePointer()+1);
									t = rr[5].readLine();
									//System.out.println(t);
									if(!(t.equals("End")||t.equals("-")))
									{
										action.add(t);
										rr[5].seek(rr[5].getFilePointer()+1);
										int z = Integer.parseInt(rr[5].readLine());
										//System.out.println(z);
										amount.add(z);
										rr[5].seek(rr[5].getFilePointer()+1);
										rr[5].readLine();
										rr[5].seek(rr[5].getFilePointer()+1);
										Double x = Double.parseDouble(rr[5].readLine());
										strength.add(x);
										//System.out.println(x);
									}
								}
							}
							//three arryalist have been made now.
								n = strength.size();
								for(int i=n-1;i>0;i--)
									for(int j=0;j<i;j++)
										if(strength.get(j)>strength.get(j+1))
										{
											double temp = strength.get(j);
											strength.set(j,strength.get(j+1));
											strength.set(j+1,temp);
											String temp1 = action.get(j);
											action.set(j,action.get(j+1));
											action.set(j+1,temp1);
											int temp2 = amount.get(j);
											amount.set(j,amount.get(j+1));
											amount.set(j+1,temp2);
										}
								if(n>0){
								max_strength = strength.get(n-1);
								min_strength = strength.get(0);
								max_action = action.get(n-1);
								min_action = action.get(0);
								max_amount = amount.get(n-1);
								min_amount = amount.get(0);
								}
							/*	for(int i=0;i<n;i++){
									System.out.print(amount.get(i));
									System.out.print(action.get(i));
									System.out.println(strength.get(i));
						}*/
								for(int i=n-1;i>0;i--)
									for(int j=0;j<i;j++)
										if(amount.get(j)>amount.get(j+1))
										{
											int temp = amount.get(j);
											amount.set(j,amount.get(j+1));
											amount.set(j+1,temp);
											String temp1 = action.get(j);
											action.set(j,action.get(j+1));
											action.set(j+1,temp1);
											double temp2 = strength.get(j);
											strength.set(j,strength.get(j+1));
											strength.set(j+1,temp2);
										}
				//calculating average strength during rise..............
								k=0;
								total=0;
								total1=0;
								for(int m=n-1;m>-1;m--)
								{

									if(action.get(m).equals("Rise")&& amount.get(m)>10)
									{

										total +=strength.get(m);
										total1 +=amount.get(m);
										rise_amount.add(amount.get(m));
										rise_strength.add(strength.get(m));
										//System.out.println(rise_strength.get(k));
										k++;
									}
								}
								if(k!=0){
								average_rise = total/k;
								average_riseamount=total1/k;
								}
								else
								{
									average_rise =0;
									average_riseamount=0;
								}
								////calculating avergae strength during call............
								kk =0;
								total2= 0;
								total3=0;
								for(int m=n-1;m>-1;m--)
								{
									if(action.get(m).equals("Call"))
									{
										total2 +=strength.get(m);
										total3 +=amount.get(m);
										//System.out.println(amount.get(m));
										call_amount.add(amount.get(m));
										//System.out.println();
										call_strength.add(strength.get(m));
										kk++;
									}
								}
								if(kk!=0){
								average_call = total2/kk;
								average_callamount=total3/kk;
								}
								else{
									average_call = 0;
									average_callamount = 0;
								}
								//System.out.println(average_call);

					////writing this information to P5.txt.....
								hh[5].write("Preflop\r\n");
								hh[5].write("Maxstrength==>\t"+max_strength+"\r\n");
								hh[5].write("Minstrength==>\t"+min_strength+"\r\n");
								hh[5].write("AverageRiseStrength==>\t"+average_rise+"\r\n");
								hh[5].write("AverageRiseAmount==>\t"+average_riseamount+"\r\n");
								hh[5].write("AverageCallStrength==>\t"+average_call+"\r\n");
								hh[5].write("AverageCallAmount==>\t"+average_callamount+"\r\n");
								hh[5].write("RiseList\r\n");
								for(int c=0;c<rise_strength.size();c++){
									hh[5].write(rise_amount.get(c)+"\t");
									hh[5].write(rise_strength.get(c)+"\t\r\n");
									}
									hh[5].write("CallList\r\n");
									for(int c=0;c<call_strength.size();c++){
										hh[5].write(call_amount.get(c)+"\t");
										hh[5].write(call_strength.get(c)+"\t\r\n");
										}
								//hh[5].close();
		////////////////////////player 5 ends/////////
								/////player 6 begins//////writing to file P6.txt///////////


								hh[6] = new FileWriter(new File("P6.txt"),false);
								rr[6] = new RandomAccessFile(new File("Player6.txt"),"r");

								action = new ArrayList<String>();
								amount = new ArrayList<Integer>();
								strength = new ArrayList<Double>();
								rise_amount = new ArrayList<Integer>();
								rise_strength = new ArrayList<Double>();
								call_amount= new ArrayList<Integer>();
								call_strength = new ArrayList<Double>();

								for(int i=1;i<11;i++)
								{
									String t = "";
									while(! t.equals("Preflop"))
									{
										t = rr[6].readLine();
									}
									t = "";
									while(!(t.equals("End")||t.equals("-")))
									{
										//System.out.println("h");
										rr[6].seek(rr[6].getFilePointer()+1);
										t = rr[6].readLine();
										//System.out.println(t);
										if(!(t.equals("End")||t.equals("-")))
										{
											action.add(t);
											rr[6].seek(rr[6].getFilePointer()+1);
											int z = Integer.parseInt(rr[6].readLine());
											//System.out.println(z);
											amount.add(z);
											rr[6].seek(rr[6].getFilePointer()+1);
											rr[6].readLine();
											rr[6].seek(rr[6].getFilePointer()+1);
											Double x = Double.parseDouble(rr[6].readLine());
											strength.add(x);
											//System.out.println(x);
										}
									}
								}
								//three arryalist have been made now.
									n = strength.size();
									for(int i=n-1;i>0;i--)
										for(int j=0;j<i;j++)
											if(strength.get(j)>strength.get(j+1))
											{
												double temp = strength.get(j);
												strength.set(j,strength.get(j+1));
												strength.set(j+1,temp);
												String temp1 = action.get(j);
												action.set(j,action.get(j+1));
												action.set(j+1,temp1);
												int temp2 = amount.get(j);
												amount.set(j,amount.get(j+1));
												amount.set(j+1,temp2);
											}
									if(n>0){
									max_strength = strength.get(n-1);
									min_strength = strength.get(0);
									max_action = action.get(n-1);
									min_action = action.get(0);
									max_amount = amount.get(n-1);
									min_amount = amount.get(0);
									}
								/*	for(int i=0;i<n;i++){
										System.out.print(amount.get(i));
										System.out.print(action.get(i));
										System.out.println(strength.get(i));
							}*/
									for(int i=n-1;i>0;i--)
										for(int j=0;j<i;j++)
											if(amount.get(j)>amount.get(j+1))
											{
												int temp = amount.get(j);
												amount.set(j,amount.get(j+1));
												amount.set(j+1,temp);
												String temp1 = action.get(j);
												action.set(j,action.get(j+1));
												action.set(j+1,temp1);
												double temp2 = strength.get(j);
												strength.set(j,strength.get(j+1));
												strength.set(j+1,temp2);
											}
					//calculating average strength during rise..............
									k=0;
									total=0;
									total1=0;
									for(int m=n-1;m>-1;m--)
									{

										if(action.get(m).equals("Rise")&& amount.get(m)>10)
										{

											total +=strength.get(m);
											total +=amount.get(m);
											rise_amount.add(amount.get(m));
											rise_strength.add(strength.get(m));
											//System.out.println(rise_strength.get(k));
											k++;
										}
									}
									if(k!=0){
									average_rise = total/k;
									average_riseamount=total1/k;
									}
									else
									{
										average_rise =0;
										average_riseamount=0;
									}
									////calculating avergae strength during call............
									kk =0;
									total2= 0;
									total3=0;
									for(int m=n-1;m>-1;m--)
									{
										if(action.get(m).equals("Call"))
										{
											total2 +=strength.get(m);
											total3 +=amount.get(m);
											//System.out.println(amount.get(m));
											call_amount.add(amount.get(m));
											//System.out.println();
											call_strength.add(strength.get(m));
											kk++;
										}
									}
									if(kk!=0){
									average_call = total2/kk;
									average_callamount=total3/kk;
									}
									else{
										average_call = 0;
										average_callamount = 0;
									}
									//System.out.println(average_call);

						////writing this information to P4.txt.....
									hh[6].write("Preflop\r\n");
									hh[6].write("Maxstrength==>\t"+max_strength+"\r\n");
									hh[6].write("Minstrength==>\t"+min_strength+"\r\n");
									hh[6].write("AverageRiseStrength==>\t"+average_rise+"\r\n");
									hh[6].write("AverageRiseAmount==>\t"+average_riseamount+"\r\n");
									hh[6].write("AverageCallStrength==>\t"+average_call+"\r\n");
									hh[6].write("AverageCallAmount==>\t"+average_callamount+"\r\n");
									hh[6].write("RiseList\r\n");
									for(int c=0;c<rise_strength.size();c++){
										hh[6].write(rise_amount.get(c)+"\t");
										hh[6].write(rise_strength.get(c)+"\t\r\n");
										}
										hh[6].write("CallList\r\n");
										for(int c=0;c<call_strength.size();c++){
											hh[6].write(call_amount.get(c)+"\t");
											hh[6].write(call_strength.get(c)+"\t\r\n");
											}
									//hh[6].close();
									//

						///////////////////////////////////////////////////////////
						//////////////////////postflop/////////////////////////////
						///////////////////////////////////////////////////////////


								/////player 1 begins//////writing to file P	////////player 1 begins////////postflop begins/////////////
									//hh[1] = new FileWriter(new File("P1.txt"),false);
									rr[1] = new RandomAccessFile(new File("Player1.txt"),"r");
									action = new ArrayList<String>();
									amount = new ArrayList<Integer>();
									strength = new ArrayList<Double>();
									rise_amount = new ArrayList<Integer>();
									rise_strength = new ArrayList<Double>();
									call_amount= new ArrayList<Integer>();
									call_strength = new ArrayList<Double>();

									for(int i=1;i<11;i++)
									{
										String t = "";
										while(! t.equals("Flop"))
										{
											//System.out.println(t);
											t = rr[1].readLine();
										}
										t = "";
										while(!(t.equals("End")||t.equals("-")))
										{
											rr[1].seek(rr[1].getFilePointer()+1);
											t = rr[1].readLine();
											//System.out.println(t);
											if(!(t.equals("End")||t.equals("-")))
											{
												action.add(t);
												rr[1].seek(rr[1].getFilePointer()+1);
												int z = Integer.parseInt(rr[1].readLine());
												//System.out.println(z);
												amount.add(z);
												rr[1].seek(rr[1].getFilePointer()+1);
												rr[1].readLine();
												rr[1].seek(rr[1].getFilePointer()+1);
												Double x = Double.parseDouble(rr[1].readLine());
												strength.add(x);
												//System.out.println(x);
											}
										}
									}
									//three arryalist have been made now.
										n = strength.size();
										for(int i=n-1;i>0;i--)
											for(int j=0;j<i;j++)
												if(strength.get(j)>strength.get(j+1))
												{
													double temp = strength.get(j);
													strength.set(j,strength.get(j+1));
													strength.set(j+1,temp);
													String temp1 = action.get(j);
													action.set(j,action.get(j+1));
													action.set(j+1,temp1);
													int temp2 = amount.get(j);
													amount.set(j,amount.get(j+1));
													amount.set(j+1,temp2);
												}
										if(n>0){
										max_strength = strength.get(n-1);
										min_strength = strength.get(0);
										max_action = action.get(n-1);
										min_action = action.get(0);
										max_amount = amount.get(n-1);
										min_amount = amount.get(0);
										}
									/*	for(int i=0;i<n;i++){
											System.out.print(amount.get(i));
											System.out.print(action.get(i));
											System.out.println(strength.get(i));
								}*/
										for(int i=n-1;i>0;i--)
											for(int j=0;j<i;j++)
												if(amount.get(j)>amount.get(j+1))
												{
													int temp = amount.get(j);
													amount.set(j,amount.get(j+1));
													amount.set(j+1,temp);
													String temp1 = action.get(j);
													action.set(j,action.get(j+1));
													action.set(j+1,temp1);
													double temp2 = strength.get(j);
													strength.set(j,strength.get(j+1));
													strength.set(j+1,temp2);
												}
						//calculating average strength during rise..............
										k=0;
										total=0;
										total1=0;
										for(int m=n-1;m>-1;m--)
										{

											if(action.get(m).equals("Rise")&& amount.get(m)>10)
											{
												total1 +=amount.get(m);
												total +=strength.get(m);
												rise_amount.add(amount.get(m));
												rise_strength.add(strength.get(m));
												//System.out.println(rise_strength.get(k));
												k++;
											}
										}
										if(k!=0){
										average_rise = total/k;
										average_riseamount=total1/k;
										}
										else
										{
											average_rise =0;
											average_riseamount=0;
										}
										////calculating avergae strength during call............
										kk =0;
										total2= 0;
										total3=0;
										for(int m=n-1;m>-1;m--)
										{
											if(action.get(m).equals("Call"))
											{
												total3 +=amount.get(m);
												total2 +=strength.get(m);
												//System.out.println(amount.get(m));
												call_amount.add(amount.get(m));
												//System.out.println();
												call_strength.add(strength.get(m));
												kk++;
											}
										}
										if(kk!=0){
										average_call = total2/kk;
										average_callamount = total3/kk;
										}
										else
										{
											average_call = 0;
											average_callamount = 0;
										}
										//System.out.println(average_call);

							////writing this information to P2.txt.....
										hh[1].write("Flop\r\n");
										hh[1].write("Maxstrength==>\t"+max_strength+"\r\n");
										hh[1].write("Minstrength==>\t"+min_strength+"\r\n");
										hh[1].write("AverageRiseStrength==>\t"+average_rise+"\r\n");
										hh[1].write("AverageRiseAmount==>\t"+average_riseamount+"\r\n");
										hh[1].write("AverageCallStrength==>\t"+average_call+"\r\n");
										hh[1].write("AverageCallAmount==>\t"+average_callamount+"\r\n");
										hh[1].write("RiseList\r\n");
										for(int c=0;c<rise_strength.size();c++){
											hh[1].write(rise_amount.get(c)+"\t");
											hh[1].write(rise_strength.get(c)+"\t\r\n");
											}
											hh[1].write("CallList\r\n");
											for(int c=0;c<call_strength.size();c++){
												hh[1].write(call_amount.get(c)+"\t");
												hh[1].write(call_strength.get(c)+"\t\r\n");
												}
										////hh[1].close();
						////////////////////////player 1 ends/////////P1.txt///////////

										//hh[2] = new FileWriter(new File("P2.txt"),false);
									rr[2] = new RandomAccessFile(new File("Player2.txt"),"r");
									action = new ArrayList<String>();
									amount = new ArrayList<Integer>();
									strength = new ArrayList<Double>();
									rise_amount = new ArrayList<Integer>();
									rise_strength = new ArrayList<Double>();
									call_amount= new ArrayList<Integer>();
									call_strength = new ArrayList<Double>();

									for(int i=1;i<11;i++)
									{
										String t = "";
										while(! t.equals("Flop"))
										{
											//System.out.println(t);
											t = rr[2].readLine();
										}
										t = "";
										while(!(t.equals("End")||t.equals("-")))
										{
											//System.out.println("h");
											rr[2].seek(rr[2].getFilePointer()+1);
											t = rr[2].readLine();
											//System.out.println(t);
											if(!(t.equals("End")||t.equals("-")))
											{
												action.add(t);
												rr[2].seek(rr[2].getFilePointer()+1);
												int z = Integer.parseInt(rr[2].readLine());
												//System.out.println(z);
												amount.add(z);
												rr[2].seek(rr[2].getFilePointer()+1);
												rr[2].readLine();
												rr[2].seek(rr[2].getFilePointer()+1);
												Double x = Double.parseDouble(rr[2].readLine());
												strength.add(x);
												//System.out.println(x);
											}
										}
									}
									//three arryalist have been made now.
										n = strength.size();
										for(int i=n-1;i>0;i--)
											for(int j=0;j<i;j++)
												if(strength.get(j)>strength.get(j+1))
												{
													double temp = strength.get(j);
													strength.set(j,strength.get(j+1));
													strength.set(j+1,temp);
													String temp1 = action.get(j);
													action.set(j,action.get(j+1));
													action.set(j+1,temp1);
													int temp2 = amount.get(j);
													amount.set(j,amount.get(j+1));
													amount.set(j+1,temp2);
												}
										if(n>0){
										max_strength = strength.get(n-1);
										min_strength = strength.get(0);
										max_action = action.get(n-1);
										min_action = action.get(0);
										max_amount = amount.get(n-1);
										min_amount = amount.get(0);
										}
									/*	for(int i=0;i<n;i++){
											System.out.print(amount.get(i));
											System.out.print(action.get(i));
											System.out.println(strength.get(i));
								}*/
										for(int i=n-1;i>0;i--)
											for(int j=0;j<i;j++)
												if(amount.get(j)>amount.get(j+1))
												{
													int temp = amount.get(j);
													amount.set(j,amount.get(j+1));
													amount.set(j+1,temp);
													String temp1 = action.get(j);
													action.set(j,action.get(j+1));
													action.set(j+1,temp1);
													double temp2 = strength.get(j);
													strength.set(j,strength.get(j+1));
													strength.set(j+1,temp2);
												}
						//calculating average strength during rise..............
										k=0;
										total=0;
										total1=0;
										for(int m=n-1;m>-1;m--)
										{

											if(action.get(m).equals("Rise")&& amount.get(m)>10)
											{
												total1 +=amount.get(m);
												total +=strength.get(m);
												rise_amount.add(amount.get(m));
												rise_strength.add(strength.get(m));
												//System.out.println(rise_strength.get(k));
												k++;
											}
										}
										if(k!=0){
										average_rise = total/k;
										average_riseamount=total1/k;
										}
											else
											{
												average_rise =0;
												average_riseamount=0;
											}

										////calculating avergae strength during call............
										kk =0;
										total2= 0;
										total3=0;
										for(int m=n-1;m>-1;m--)
										{
											if(action.get(m).equals("Call"))
											{
												total3 +=amount.get(m);
												total2 +=strength.get(m);
												//System.out.println(amount.get(m));
												call_amount.add(amount.get(m));
												//System.out.println();
												call_strength.add(strength.get(m));
												kk++;
											}
										}
										if(kk!=0)
										{
										average_call = total2/kk;
										average_callamount=total3/kk;
										}
										else
										{
											average_call = 0;
											average_callamount = 0;
										}
										//System.out.println(average_call);

							////writing this information to P2.txt.....
										hh[2].write("Flop\r\n");
										hh[2].write("Maxstrength==>\t"+max_strength+"\r\n");
										hh[2].write("Minstrength==>\t"+min_strength+"\r\n");
										hh[2].write("AverageRiseStrength==>\t"+average_rise+"\r\n");
										hh[2].write("AverageRiseAmount==>\t"+average_riseamount+"\r\n");
										hh[2].write("AverageCallStrength==>\t"+average_call+"\r\n");
										hh[2].write("AverageCallAmount==>\t"+average_callamount+"\r\n");
										hh[2].write("RiseList\r\n");
										for(int c=0;c<rise_strength.size();c++){
											hh[2].write(rise_amount.get(c)+"\t");
											hh[2].write(rise_strength.get(c)+"\t\r\n");
											}
											hh[2].write("CallList\r\n");
											for(int c=0;c<call_strength.size();c++){
												hh[2].write(call_amount.get(c)+"\t");
												hh[2].write(call_strength.get(c)+"\t\r\n");
												}
										//hh[2].close();
						////////////////////////player 2 ends/////////
										//////////player 3 postflop begins/////////////////////////////
										//hh[3] = new FileWriter(new File("P3.txt"),false);
										rr[3] = new RandomAccessFile(new File("Player3.txt"),"r");
										action = new ArrayList<String>();
										amount = new ArrayList<Integer>();
										strength = new ArrayList<Double>();
										rise_amount = new ArrayList<Integer>();
										rise_strength = new ArrayList<Double>();
										call_amount= new ArrayList<Integer>();
										call_strength = new ArrayList<Double>();

										for(int i=1;i<11;i++)
										{
											String t = "";
											while(! t.equals("Flop"))
											{
												//System.out.println(t);
												t = rr[3].readLine();
											}
											t = "";
											while(!(t.equals("End")||t.equals("-")))
											{
												rr[3].seek(rr[3].getFilePointer()+1);
												t = rr[3].readLine();
												//System.out.println(t);
												if(!(t.equals("End")||t.equals("-")))
												{
													action.add(t);
													rr[3].seek(rr[3].getFilePointer()+1);
													int z = Integer.parseInt(rr[3].readLine());
													//System.out.println(z);
													amount.add(z);
													rr[3].seek(rr[3].getFilePointer()+1);
													rr[3].readLine();
													rr[3].seek(rr[3].getFilePointer()+1);
													Double x = Double.parseDouble(rr[3].readLine());
													strength.add(x);
													//System.out.println(x);
												}
											}
										}
										//three arryalist have been made now.
											n = strength.size();
											for(int i=n-1;i>0;i--)
												for(int j=0;j<i;j++)
													if(strength.get(j)>strength.get(j+1))
													{
														double temp = strength.get(j);
														strength.set(j,strength.get(j+1));
														strength.set(j+1,temp);
														String temp1 = action.get(j);
														action.set(j,action.get(j+1));
														action.set(j+1,temp1);
														int temp2 = amount.get(j);
														amount.set(j,amount.get(j+1));
														amount.set(j+1,temp2);
													}
											if(n>0){
											max_strength = strength.get(n-1);
											min_strength = strength.get(0);
											max_action = action.get(n-1);
											min_action = action.get(0);
											max_amount = amount.get(n-1);
											min_amount = amount.get(0);
											}
										/*	for(int i=0;i<n;i++){
												System.out.print(amount.get(i));
												System.out.print(action.get(i));
												System.out.println(strength.get(i));
									}*/
											for(int i=n-1;i>0;i--)
												for(int j=0;j<i;j++)
													if(amount.get(j)>amount.get(j+1))
													{
														int temp = amount.get(j);
														amount.set(j,amount.get(j+1));
														amount.set(j+1,temp);
														String temp1 = action.get(j);
														action.set(j,action.get(j+1));
														action.set(j+1,temp1);
														double temp2 = strength.get(j);
														strength.set(j,strength.get(j+1));
														strength.set(j+1,temp2);
													}
							//calculating average strength during rise..............
											k=0;
											total=0;
											total1=0;
											for(int m=n-1;m>-1;m--)
											{
												if(action.get(m).equals("Rise")&& amount.get(m)>10)
												{
													total1 +=amount.get(m);
													total +=strength.get(m);
													rise_amount.add(amount.get(m));
													rise_strength.add(strength.get(m));
													//System.out.println(rise_strength.get(k));
													k++;
												}
											}
											if(k!=0){
											average_rise = total/k;
											average_riseamount=total1/k;
											}
											else
											{
												average_rise =0;
												average_riseamount=0;
											}




											////calculating avergae strength during call............
											kk =0;
											total2= 0;
											total3=0;
											for(int m=n-1;m>-1;m--)
											{
												if(action.get(m).equals("Call"))
												{
													total3 +=amount.get(m);
													total2 +=strength.get(m);
													//System.out.println(amount.get(m));
													call_amount.add(amount.get(m));
													//System.out.println();
													call_strength.add(strength.get(m));
													kk++;
												}
											}
											if(kk!=0){
											average_call = total2/kk;
											average_callamount=total3/kk;
											}
											else{
												average_call = 0;
												average_callamount = 0;
											}
											//System.out.println(average_call);

								////writing this information to P2.txt.....
											hh[3].write("Flop\r\n");
											hh[3].write("Maxstrength==>\t"+max_strength+"\r\n");
											hh[3].write("Minstrength==>\t"+min_strength+"\r\n");
											hh[3].write("AverageRiseStrength==>\t"+average_rise+"\r\n");
											hh[3].write("AverageRiseAmount==>\t"+average_riseamount+"\r\n");
											hh[3].write("AverageCallStrength==>\t"+average_call+"\r\n");
											hh[3].write("AverageCallAmount==>\t"+average_callamount+"\r\n");
											hh[3].write("RiseList\r\n");
											for(int c=0;c<rise_strength.size();c++){
												hh[3].write(rise_amount.get(c)+"\t");
												hh[3].write(rise_strength.get(c)+"\t\r\n");
												}
												hh[3].write("CallList\r\n");
												for(int c=0;c<call_strength.size();c++){
													hh[3].write(call_amount.get(c)+"\t");
													hh[3].write(call_strength.get(c)+"\t\r\n");
													}
											//hh[3].close();
							////////////////////////player 3 ends/////////
											////////player 4 begins////////postflop begins/////////////
											//hh[4] = new FileWriter(new File("P4.txt"),false);
											rr[4] = new RandomAccessFile(new File("Player4.txt"),"r");
											action = new ArrayList<String>();
											amount = new ArrayList<Integer>();
											strength = new ArrayList<Double>();
											rise_amount = new ArrayList<Integer>();
											rise_strength = new ArrayList<Double>();
											call_amount= new ArrayList<Integer>();
											call_strength = new ArrayList<Double>();

											for(int i=1;i<11;i++)
											{
												String t = "";
												while(! t.equals("Flop"))
												{
													//System.out.println(t);
													t = rr[4].readLine();
												}
												t = "";
												while(!(t.equals("End")||t.equals("-")))
												{
													rr[4].seek(rr[4].getFilePointer()+1);
													t = rr[4].readLine();
													//System.out.println(t);
													if(!(t.equals("End")||t.equals("-")))
													{
														action.add(t);
														rr[4].seek(rr[4].getFilePointer()+1);
														int z = Integer.parseInt(rr[4].readLine());
														//System.out.println(z);
														amount.add(z);
														rr[4].seek(rr[4].getFilePointer()+1);
														rr[4].readLine();
														rr[4].seek(rr[4].getFilePointer()+1);
														Double x = Double.parseDouble(rr[4].readLine());
														strength.add(x);
														//System.out.println(x);
													}
												}
											}
											//three arryalist have been made now.
												n = strength.size();
												for(int i=n-1;i>0;i--)
													for(int j=0;j<i;j++)
														if(strength.get(j)>strength.get(j+1))
														{
															double temp = strength.get(j);
															strength.set(j,strength.get(j+1));
															strength.set(j+1,temp);
															String temp1 = action.get(j);
															action.set(j,action.get(j+1));
															action.set(j+1,temp1);
															int temp2 = amount.get(j);
															amount.set(j,amount.get(j+1));
															amount.set(j+1,temp2);
														}
												if(n>0){
												max_strength = strength.get(n-1);
												min_strength = strength.get(0);
												max_action = action.get(n-1);
												min_action = action.get(0);
												max_amount = amount.get(n-1);
												min_amount = amount.get(0);
												}
											/*	for(int i=0;i<n;i++){
													System.out.print(amount.get(i));
													System.out.print(action.get(i));
													System.out.println(strength.get(i));
										}*/
												for(int i=n-1;i>0;i--)
													for(int j=0;j<i;j++)
														if(amount.get(j)>amount.get(j+1))
														{
															int temp = amount.get(j);
															amount.set(j,amount.get(j+1));
															amount.set(j+1,temp);
															String temp1 = action.get(j);
															action.set(j,action.get(j+1));
															action.set(j+1,temp1);
															double temp2 = strength.get(j);
															strength.set(j,strength.get(j+1));
															strength.set(j+1,temp2);
														}
								//calculating average strength during rise..............
												k=0;
												total=0;
												total1=0;
												for(int m=n-1;m>-1;m--)
												{

													if(action.get(m).equals("Rise")&& amount.get(m)>10)
													{
														total1 +=amount.get(m);
														total +=strength.get(m);
														rise_amount.add(amount.get(m));
														rise_strength.add(strength.get(m));
														//System.out.println(rise_strength.get(k));
														k++;
													}
												}
												if(k!=0)
												{
												average_rise = total/k;
												average_riseamount=total1/k;
												}
												else
												{
													average_rise =0;
													average_riseamount=0;
												}

												////calculating avergae strength during call............
												kk =0;
												total2= 0;
												total3=0;
												for(int m=n-1;m>-1;m--)
												{
													if(action.get(m).equals("Call"))
													{
														total3 +=amount.get(m);
														total2 +=strength.get(m);
														//System.out.println(amount.get(m));
														call_amount.add(amount.get(m));
														//System.out.println();
														call_strength.add(strength.get(m));
														kk++;
													}
												}
												if(kk!=0){
												average_call = total2/kk;
												average_callamount=total3/kk;
												}
												else
												{
													average_call =0;
													average_callamount=0;
												}
												//System.out.println(average_call);

									////writing this information to P2.txt.....
												hh[4].write("Flop\r\n");
												hh[4].write("Maxstrength==>\t"+max_strength+"\r\n");
												hh[4].write("Minstrength==>\t"+min_strength+"\r\n");
												hh[4].write("AverageRiseStrength==>\t"+average_rise+"\r\n");
												hh[4].write("AverageRiseAmount==>\t"+average_riseamount+"\r\n");
												hh[4].write("AverageCallStrength==>\t"+average_call+"\r\n");
												hh[4].write("AverageCallAmount==>\t"+average_callamount+"\r\n");
												hh[4].write("RiseList\r\n");
												for(int c=0;c<rise_strength.size();c++){
													hh[4].write(rise_amount.get(c)+"\t");
													hh[4].write(rise_strength.get(c)+"\t\r\n");
													}
													hh[4].write("CallList\r\n");
													for(int c=0;c<call_strength.size();c++){
														hh[4].write(call_amount.get(c)+"\t");
														hh[4].write(call_strength.get(c)+"\t\r\n");
														}
												//hh[4].close();
								////////////////////////player 4 ends/////////
												////////player 4 begins////////postflop begins/////////////
												//hh[5] = new FileWriter(new File("P5.txt"),false);
												rr[5] = new RandomAccessFile(new File("Player5.txt"),"r");
												action = new ArrayList<String>();
												amount = new ArrayList<Integer>();
												strength = new ArrayList<Double>();
												rise_amount = new ArrayList<Integer>();
												rise_strength = new ArrayList<Double>();
												call_amount= new ArrayList<Integer>();
												call_strength = new ArrayList<Double>();

												for(int i=1;i<11;i++)
												{
													String t = "";
													while(! t.equals("Flop"))
													{
														//System.out.println(t);
														t = rr[5].readLine();
													}
													t = "";
													while(!(t.equals("End")||t.equals("-")))
													{
														rr[5].seek(rr[5].getFilePointer()+1);
														t = rr[5].readLine();
														if(!(t.equals("End")||t.equals("-")))
														{
															action.add(t);
															rr[5].seek(rr[5].getFilePointer()+1);
															int z = Integer.parseInt(rr[5].readLine());
															//System.out.println(z);
															amount.add(z);
															rr[5].seek(rr[5].getFilePointer()+1);
															rr[5].readLine();
															rr[5].seek(rr[5].getFilePointer()+1);
															Double x = Double.parseDouble(rr[5].readLine());
															strength.add(x);
															//System.out.println(x);
														}
													}
												}

												//three arryalist have been made now.
													n = strength.size();
													//System.out.println(n);
													for(int i=n-1;i>0;i--)
														for(int j=0;j<i;j++)
															if(strength.get(j)>strength.get(j+1))
															{
																double temp = strength.get(j);
																strength.set(j,strength.get(j+1));
																strength.set(j+1,temp);
																String temp1 = action.get(j);
																action.set(j,action.get(j+1));
																action.set(j+1,temp1);
																int temp2 = amount.get(j);
																amount.set(j,amount.get(j+1));
																amount.set(j+1,temp2);
															}
													if(n>0)
															{
														max_strength = strength.get(n-1);
														min_strength = strength.get(0);
														max_action = action.get(n-1);
														min_action = action.get(0);
														max_amount = amount.get(n-1);
														min_amount = amount.get(0);
															}
												/*	for(int i=0;i<n;i++){
														System.out.print(amount.get(i));
														System.out.print(action.get(i));
														System.out.println(strength.get(i));
											}*/
													for(int i=n-1;i>0;i--)
														for(int j=0;j<i;j++)
															if(amount.get(j)>amount.get(j+1))
															{
																int temp = amount.get(j);
																amount.set(j,amount.get(j+1));
																amount.set(j+1,temp);
																String temp1 = action.get(j);
																action.set(j,action.get(j+1));
																action.set(j+1,temp1);
																double temp2 = strength.get(j);
																strength.set(j,strength.get(j+1));
																strength.set(j+1,temp2);
															}
									//calculating average strength during rise..............
													k=0;
													total=0;
													total1=0;
													for(int m=n-1;m>-1;m--)
													{

														if(action.get(m).equals("Rise")&& amount.get(m)>10)
														{
															total1 +=amount.get(m);
															total +=strength.get(m);
															rise_amount.add(amount.get(m));
															rise_strength.add(strength.get(m));
															//System.out.println(rise_strength.get(k));
															k++;
														}
													}
													if(k!=0){
													average_rise = total/k;
													average_riseamount=total1/k;
													}
													else
													{
														average_rise =0;
														average_riseamount=0;
													}
													////calculating avergae strength during call............
													kk =0;
													total2= 0;
													total3=0;
													for(int m=n-1;m>-1;m--)
													{
														if(action.get(m).equals("Call"))
														{
															total3 +=amount.get(m);
															total2 +=strength.get(m);
															//System.out.println(amount.get(m));
															call_amount.add(amount.get(m));
															//System.out.println();
															call_strength.add(strength.get(m));
															kk++;
														}
													}
													if(kk!=0){
													average_call = total2/kk;
													average_callamount=total3/kk;
													}
													else
													{
														average_call =0;
														average_callamount=0;
													}
													//System.out.println(average_call);

										////writing this information to P2.txt.....
													hh[5].write("Flop\r\n");
													hh[5].write("Maxstrength==>\t"+max_strength+"\r\n");
													hh[5].write("Minstrength==>\t"+min_strength+"\r\n");
													hh[5].write("AverageRiseStrength==>\t"+average_rise+"\r\n");
													hh[5].write("AverageRiseAmount==>\t"+average_riseamount+"\r\n");
													hh[5].write("AverageCallStrength==>\t"+average_call+"\r\n");
													hh[5].write("AverageCallAmount==>\t"+average_callamount+"\r\n");
													hh[5].write("RiseList\r\n");
													for(int c=0;c<rise_strength.size();c++){
														hh[5].write(rise_amount.get(c)+"\t");
														hh[5].write(rise_strength.get(c)+"\t\r\n");
														}
														hh[5].write("CallList\r\n");
														for(int c=0;c<call_strength.size();c++){
															hh[5].write(call_amount.get(c)+"\t");
															hh[5].write(call_strength.get(c)+"\t\r\n");
															}
													//hh[5].close();
									////////////////////////player 5 ends/////////
													////////player 6 begins////////postflop begins/////////////
													//hh[6] = new FileWriter(new File("P6.txt"),false);
													rr[6] = new RandomAccessFile(new File("Player6.txt"),"r");
													action = new ArrayList<String>();
													amount = new ArrayList<Integer>();
													strength = new ArrayList<Double>();
													rise_amount = new ArrayList<Integer>();
													rise_strength = new ArrayList<Double>();
													call_amount= new ArrayList<Integer>();
													call_strength = new ArrayList<Double>();

													for(int i=1;i<11;i++)
													{
														String t = "";
														while(! t.equals("Flop"))
														{
															//System.out.println(t);
															t = rr[6].readLine();
														}
														t = "";
														while(!(t.equals("End")||(t.equals("-"))))
														{
															rr[6].seek(rr[6].getFilePointer()+1);
															t = rr[6].readLine();
															//System.out.println(t);
															if(!(t.equals("End")||t.equals("-")))
															{
																action.add(t);
																rr[6].seek(rr[6].getFilePointer()+1);
																int z = Integer.parseInt(rr[6].readLine());
																//System.out.println(z);
																amount.add(z);
																rr[6].seek(rr[6].getFilePointer()+1);
																rr[6].readLine();
																rr[6].seek(rr[6].getFilePointer()+1);
																Double x = Double.parseDouble(rr[6].readLine());
																strength.add(x);
																//System.out.println(x);
															}
														}
													}
													//three arryalist have been made now.
														n = strength.size();
														for(int i=n-1;i>0;i--)
															for(int j=0;j<i;j++)
																if(strength.get(j)>strength.get(j+1))
																{
																	double temp = strength.get(j);
																	strength.set(j,strength.get(j+1));
																	strength.set(j+1,temp);
																	String temp1 = action.get(j);
																	action.set(j,action.get(j+1));
																	action.set(j+1,temp1);
																	int temp2 = amount.get(j);
																	amount.set(j,amount.get(j+1));
																	amount.set(j+1,temp2);
																}
														if(n>0){
														max_strength = strength.get(n-1);
														min_strength = strength.get(0);
														max_action = action.get(n-1);
														min_action = action.get(0);
														max_amount = amount.get(n-1);
														min_amount = amount.get(0);
														}
													/*	for(int i=0;i<n;i++){
															System.out.print(amount.get(i));
															System.out.print(action.get(i));
															System.out.println(strength.get(i));
												}*/
														for(int i=n-1;i>0;i--)
															for(int j=0;j<i;j++)
																if(amount.get(j)>amount.get(j+1))
																{
																	int temp = amount.get(j);
																	amount.set(j,amount.get(j+1));
																	amount.set(j+1,temp);
																	String temp1 = action.get(j);
																	action.set(j,action.get(j+1));
																	action.set(j+1,temp1);
																	double temp2 = strength.get(j);
																	strength.set(j,strength.get(j+1));
																	strength.set(j+1,temp2);
																}
										//calculating average strength during rise..............
														k=0;
														total=0;
														total1=0;
														for(int m=n-1;m>-1;m--)
														{

															if(action.get(m).equals("Rise")&& amount.get(m)>10)
															{
																total1 +=amount.get(m);
																total +=strength.get(m);
																rise_amount.add(amount.get(m));
																rise_strength.add(strength.get(m));
																//System.out.println(rise_strength.get(k));
																k++;
															}
														}
														if(k!=0){
														average_rise = total/k;
														average_riseamount=total1/k;
														}
														else
														{
															average_rise =0;
															average_riseamount=0;
														}

														////calculating avergae strength during call............
														kk =0;
														total2= 0;
														total3=0;
														for(int m=n-1;m>-1;m--)
														{
															if(action.get(m).equals("Call"))
															{
																total3 +=amount.get(m);
																total2 +=strength.get(m);
																//System.out.println(amount.get(m));
																call_amount.add(amount.get(m));
																//System.out.println();
																call_strength.add(strength.get(m));
																kk++;
															}
														}
														if(kk!=0){
														average_call = total2/kk;
														average_callamount=total3/kk;
														}
														else
														{
															average_call =0;
															average_callamount=0;
														}
														//System.out.println(average_call);

											////writing this information to P2.txt.....
														hh[6].write("Flop\r\n");
														hh[6].write("Maxstrength==>\t"+max_strength+"\r\n");
														hh[6].write("Minstrength==>\t"+min_strength+"\r\n");
														hh[6].write("AverageRiseStrength==>\t"+average_rise+"\r\n");
														hh[6].write("AverageRiseAmount==>\t"+average_riseamount+"\r\n");
														hh[6].write("AverageCallStrength==>\t"+average_call+"\r\n");
														hh[6].write("AverageCallAmount==>\t"+average_callamount+"\r\n");
														hh[6].write("RiseList\r\n");
														for(int c=0;c<rise_strength.size();c++){
															hh[6].write(rise_amount.get(c)+"\t");
															hh[6].write(rise_strength.get(c)+"\t\r\n");
															}
															hh[6].write("CallList\r\n");
															for(int c=0;c<call_strength.size();c++){
																hh[6].write(call_amount.get(c)+"\t");
																hh[6].write(call_strength.get(c)+"\t\r\n");
																}
														//hh[6].close();
										////////////////////////player 6 ends/////////
														/////////////////post flop ends/////////////////
														///////4th street begins////////////////////


														/////player 1 begins//////writing to file P	////////player 1 begins////////4th begins/////////////
															//hh[1] = new FileWriter(new File("P1.txt"),false);
															rr[1] = new RandomAccessFile(new File("Player1.txt"),"r");
															action = new ArrayList<String>();
															amount = new ArrayList<Integer>();
															strength = new ArrayList<Double>();
															rise_amount = new ArrayList<Integer>();
															rise_strength = new ArrayList<Double>();
															call_amount= new ArrayList<Integer>();
															call_strength = new ArrayList<Double>();

															for(int i=1;i<11;i++)
															{
																String t = "";
																while(! t.equals("4th"))
																{
																	//System.out.println(t);
																	t = rr[1].readLine();
																}
																t = "";
																while(!(t.equals("End")||t.equals("-")))
																{
																	rr[1].seek(rr[1].getFilePointer()+1);
																	t = rr[1].readLine();
																	//System.out.println(t);
																	if(!(t.equals("End")||t.equals("-")))
																	{
																		action.add(t);
																		rr[1].seek(rr[1].getFilePointer()+1);
																		int z = Integer.parseInt(rr[1].readLine());
																		//System.out.println(z);
																		amount.add(z);
																		rr[1].seek(rr[1].getFilePointer()+1);
																		rr[1].readLine();
																		rr[1].seek(rr[1].getFilePointer()+1);
																		Double x = Double.parseDouble(rr[1].readLine());
																		strength.add(x);
																		//System.out.println(x);
																	}
																}
															}
															//three arryalist have been made now.
																n = strength.size();
																for(int i=n-1;i>0;i--)
																	for(int j=0;j<i;j++)
																		if(strength.get(j)>strength.get(j+1))
																		{
																			double temp = strength.get(j);
																			strength.set(j,strength.get(j+1));
																			strength.set(j+1,temp);
																			String temp1 = action.get(j);
																			action.set(j,action.get(j+1));
																			action.set(j+1,temp1);
																			int temp2 = amount.get(j);
																			amount.set(j,amount.get(j+1));
																			amount.set(j+1,temp2);
																		}
																if(n>0){
																max_strength = strength.get(n-1);
																min_strength = strength.get(0);
																max_action = action.get(n-1);
																min_action = action.get(0);
																max_amount = amount.get(n-1);
																min_amount = amount.get(0);
																}
															/*	for(int i=0;i<n;i++){
																	System.out.print(amount.get(i));
																	System.out.print(action.get(i));
																	System.out.println(strength.get(i));
														}*/
																for(int i=n-1;i>0;i--)
																	for(int j=0;j<i;j++)
																		if(amount.get(j)>amount.get(j+1))
																		{
																			int temp = amount.get(j);
																			amount.set(j,amount.get(j+1));
																			amount.set(j+1,temp);
																			String temp1 = action.get(j);
																			action.set(j,action.get(j+1));
																			action.set(j+1,temp1);
																			double temp2 = strength.get(j);
																			strength.set(j,strength.get(j+1));
																			strength.set(j+1,temp2);
																		}
												//calculating average strength during rise..............
																k=0;
																total=0;
																total1=0;;
																for(int m=n-1;m>-1;m--)
																{

																	if(action.get(m).equals("Rise")&& amount.get(m)>10)
																	{
																		total1 +=amount.get(m);
																		total +=strength.get(m);
																		rise_amount.add(amount.get(m));
																		rise_strength.add(strength.get(m));
																		//System.out.println(rise_strength.get(k));
																		k++;
																	}
																}
																if(k!=0){
																average_rise = total/k;
																average_riseamount=total1/k;
																}
																else
																{
																	average_rise =0;
																	average_riseamount=0;
																}

																////calculating avergae strength during call............
																kk =0;
																total2= 0;
																total3=0;
																for(int m=n-1;m>-1;m--)
																{
																	if(action.get(m).equals("Call"))
																	{
																		total3 +=amount.get(m);
																		total2 +=strength.get(m);
																		//System.out.println(amount.get(m));
																		call_amount.add(amount.get(m));
																		//System.out.println();
																		call_strength.add(strength.get(m));
																		kk++;
																	}
																}
																if(kk!=0){
																average_call = total2/kk;
																average_callamount=total3/kk;
																}
																else
																{
																	average_call =0;
																	average_callamount=0;
																}
																//System.out.println(average_call);

													////writing this information to P2.txt.....
																hh[1].write("4th Street\r\n");
																hh[1].write("Maxstrength==>\t"+max_strength+"\r\n");
																hh[1].write("Minstrength==>\t"+min_strength+"\r\n");
																hh[1].write("AverageRiseStrength==>\t"+average_rise+"\r\n");
																hh[1].write("AverageRiseAmount==>\t"+average_riseamount+"\r\n");
																hh[1].write("AverageCallStrength==>\t"+average_call+"\r\n");
																hh[1].write("AverageCallAmount==>\t"+average_callamount+"\r\n");
																hh[1].write("RiseList\r\n");
																for(int c=0;c<rise_strength.size();c++){
																	hh[1].write(rise_amount.get(c)+"\t");
																	hh[1].write(rise_strength.get(c)+"\t\r\n");
																	}
																	hh[1].write("CallList\r\n");
																	for(int c=0;c<call_strength.size();c++){
																		hh[1].write(call_amount.get(c)+"\t");
																		hh[1].write(call_strength.get(c)+"\t\r\n");
																		}
																//hh[1].close();
												////////////////////////player 1 ends/////////P1.txt///////////

																//hh[2] = new FileWriter(new File("P2.txt"),false);
															rr[2] = new RandomAccessFile(new File("Player2.txt"),"r");
															action = new ArrayList<String>();
															amount = new ArrayList<Integer>();
															strength = new ArrayList<Double>();
															rise_amount = new ArrayList<Integer>();
															rise_strength = new ArrayList<Double>();
															call_amount= new ArrayList<Integer>();
															call_strength = new ArrayList<Double>();

															for(int i=1;i<11;i++)
															{
																String t = "";
																while(! t.equals("4th"))
																{
																	//System.out.println(t);
																	t = rr[2].readLine();
																}
																t = "";
																while(!(t.equals("End")||t.equals("-")))
																{
																	//System.out.println("h");
																	rr[2].seek(rr[2].getFilePointer()+1);
																	t = rr[2].readLine();
																	//System.out.println(t);
																	if(!(t.equals("End")||t.equals("-")))
																	{
																		action.add(t);
																		rr[2].seek(rr[2].getFilePointer()+1);
																		int z = Integer.parseInt(rr[2].readLine());
																		//System.out.println(z);
																		amount.add(z);
																		rr[2].seek(rr[2].getFilePointer()+1);
																		rr[2].readLine();
																		rr[2].seek(rr[2].getFilePointer()+1);
																		Double x = Double.parseDouble(rr[2].readLine());
																		strength.add(x);
																		//System.out.println(x);
																	}
																}
															}
															//three arryalist have been made now.
																n = strength.size();

																for(int i=n-1;i>0;i--)
																	for(int j=0;j<i;j++)
																		if(strength.get(j)>strength.get(j+1))
																		{
																			double temp = strength.get(j);
																			strength.set(j,strength.get(j+1));
																			strength.set(j+1,temp);
																			String temp1 = action.get(j);
																			action.set(j,action.get(j+1));
																			action.set(j+1,temp1);
																			int temp2 = amount.get(j);
																			amount.set(j,amount.get(j+1));
																			amount.set(j+1,temp2);
																		}
																if(n>0){
																max_strength = strength.get(n-1);
																min_strength = strength.get(0);
																max_action = action.get(n-1);
																min_action = action.get(0);
																max_amount = amount.get(n-1);
																min_amount = amount.get(0);
																}

															/*	for(int i=0;i<n;i++){
																	System.out.print(amount.get(i));
																	System.out.print(action.get(i));
																	System.out.println(strength.get(i));
														}*/
																for(int i=n-1;i>0;i--)
																	for(int j=0;j<i;j++)
																		if(amount.get(j)>amount.get(j+1))
																		{
																			int temp = amount.get(j);
																			amount.set(j,amount.get(j+1));
																			amount.set(j+1,temp);
																			String temp1 = action.get(j);
																			action.set(j,action.get(j+1));
																			action.set(j+1,temp1);
																			double temp2 = strength.get(j);
																			strength.set(j,strength.get(j+1));
																			strength.set(j+1,temp2);
																		}
												//calculating average strength during rise..............
																k=0;
																total=0;
																total1=0;
																for(int m=n-1;m>-1;m--)
																{

																	if(action.get(m).equals("Rise")&& amount.get(m)>10)
																	{
																		total1 +=amount.get(m);
																		total +=strength.get(m);
																		rise_amount.add(amount.get(m));
																		rise_strength.add(strength.get(m));
																		//System.out.println(rise_strength.get(k));
																		k++;
																	}
																}
																if(k!=0){
																average_rise = total/k;
																average_riseamount=total1/k;
																}
																else
																{
																	average_rise =0;
																	average_riseamount=0;
																}

																////calculating avergae strength during call............
																kk =0;
																total2= 0;
																total3=0;
																for(int m=n-1;m>-1;m--)
																{
																	if(action.get(m).equals("Call"))
																	{
																		total3 +=amount.get(m);
																		total2 +=strength.get(m);
																		//System.out.println(amount.get(m));
																		call_amount.add(amount.get(m));
																		//System.out.println();
																		call_strength.add(strength.get(m));
																		kk++;
																	}
																}
																if(kk!=0){
																average_call = total2/kk;
																average_callamount=total3/kk;
																}
																else
																{
																	average_call =0;
																	average_callamount=0;
																}
																//System.out.println(average_call);

													////writing this information to P2.txt.....
																hh[2].write("4th Street\r\n");
																hh[2].write("Maxstrength==>\t"+max_strength+"\r\n");
																hh[2].write("Minstrength==>\t"+min_strength+"\r\n");
																hh[2].write("AverageRiseStrength==>\t"+average_rise+"\r\n");
																hh[2].write("AverageRiseAmount==>\t"+average_riseamount+"\r\n");
																hh[2].write("AverageCallStrength==>\t"+average_call+"\r\n");
																hh[2].write("AverageCallAmount==>\t"+average_callamount+"\r\n");
																hh[2].write("RiseList\r\n");
																for(int c=0;c<rise_strength.size();c++){
																	hh[2].write(rise_amount.get(c)+"\t");
																	hh[2].write(rise_strength.get(c)+"\t\r\n");
																	}
																	hh[2].write("CallList\r\n");
																	for(int c=0;c<call_strength.size();c++){
																		hh[2].write(call_amount.get(c)+"\t");
																		hh[2].write(call_strength.get(c)+"\t\r\n");
																		}
																//hh[2].close();
												////////////////////////player 2 ends/////////
																//////////player 3 postflop begins/////////////////////////////

																//hh[3] = new FileWriter(new File("P3.txt"),false);
																rr[3] = new RandomAccessFile(new File("Player3.txt"),"r");
																action = new ArrayList<String>();
																amount = new ArrayList<Integer>();
																strength = new ArrayList<Double>();
																rise_amount = new ArrayList<Integer>();
																rise_strength = new ArrayList<Double>();
																call_amount= new ArrayList<Integer>();
																call_strength = new ArrayList<Double>();

																for(int i=1;i<11;i++)
																{
																	String t = "";
																	while(! t.equals("4th"))
																	{
																		//System.out.println(t);
																		t = rr[3].readLine();
																	}
																	t = "";
																	while(!(t.equals("End")||t.equals("-")))
																	{
																		rr[3].seek(rr[3].getFilePointer()+1);
																		t = rr[3].readLine();
																		//System.out.println(t);
																		if(!(t.equals("End")||t.equals("-")))
																		{
																			action.add(t);
																			rr[3].seek(rr[3].getFilePointer()+1);
																			int z = Integer.parseInt(rr[3].readLine());
																			//System.out.println(z);
																			amount.add(z);
																			rr[3].seek(rr[3].getFilePointer()+1);
																			rr[3].readLine();
																			rr[3].seek(rr[3].getFilePointer()+1);
																			Double x = Double.parseDouble(rr[3].readLine());
																			strength.add(x);
																			//System.out.println(x);
																		}
																	}
																}
																//three arryalist have been made now.
																	n = strength.size();
																	for(int i=n-1;i>0;i--)
																		for(int j=0;j<i;j++)
																			if(strength.get(j)>strength.get(j+1))
																			{
																				double temp = strength.get(j);
																				strength.set(j,strength.get(j+1));
																				strength.set(j+1,temp);
																				String temp1 = action.get(j);
																				action.set(j,action.get(j+1));
																				action.set(j+1,temp1);
																				int temp2 = amount.get(j);
																				amount.set(j,amount.get(j+1));
																				amount.set(j+1,temp2);
																			}
																	if(n>0){
																	max_strength = strength.get(n-1);
																	min_strength = strength.get(0);
																	max_action = action.get(n-1);
																	min_action = action.get(0);
																	max_amount = amount.get(n-1);
																	min_amount = amount.get(0);
																	}
																/*	for(int i=0;i<n;i++){
																		System.out.print(amount.get(i));
																		System.out.print(action.get(i));
																		System.out.println(strength.get(i));
															}*/
																	for(int i=n-1;i>0;i--)
																		for(int j=0;j<i;j++)
																			if(amount.get(j)>amount.get(j+1))
																			{
																				int temp = amount.get(j);
																				amount.set(j,amount.get(j+1));
																				amount.set(j+1,temp);
																				String temp1 = action.get(j);
																				action.set(j,action.get(j+1));
																				action.set(j+1,temp1);
																				double temp2 = strength.get(j);
																				strength.set(j,strength.get(j+1));
																				strength.set(j+1,temp2);
																			}
													//calculating average strength during rise..............
																	k=0;
																	total=0;
																	total1=0;
																	for(int m=n-1;m>-1;m--)
																	{
																		if(action.get(m).equals("Rise")&& amount.get(m)>10)
																		{
																			total1 +=amount.get(m);
																			total +=strength.get(m);
																			rise_amount.add(amount.get(m));
																			rise_strength.add(strength.get(m));
																			//System.out.println(rise_strength.get(k));
																			k++;
																		}
																	}
																	if(k != 0){
																	average_rise = total/k;
																	average_riseamount=total1/k;
																	}
																	else
																	{
																		average_rise = 0;
																		average_riseamount=0;
																	}
																	//System.out.println(average_rise);

																	////calculating avergae strength during call............
																	kk =0;
																	total2= 0;
																	total3=0;
																	for(int m=n-1;m>-1;m--)
																	{
																		if(action.get(m).equals("Call"))
																		{
																			total3 +=amount.get(m);
																			total2 +=strength.get(m);
																			//System.out.println(amount.get(m));
																			call_amount.add(amount.get(m));
																			//System.out.println();
																			call_strength.add(strength.get(m));
																			kk++;
																		}
																	}
																	if(kk!=0){
																	average_call = total2/kk;
																	average_callamount=total3/kk;}
																	else
																	{
																		average_call =0;
																		average_callamount=0;
																	}
																//	System.out.println(average_call);

														////writing this information to P2.txt.....
																	hh[3].write("4th Street\r\n");
																	hh[3].write("Maxstrength==>\t"+max_strength+"\r\n");
																	hh[3].write("Minstrength==>\t"+min_strength+"\r\n");
																	hh[3].write("AverageRiseStrength==>\t"+average_rise+"\r\n");
																	hh[3].write("AverageRiseAmount==>\t"+average_riseamount+"\r\n");
																	hh[3].write("AverageCallStrength==>\t"+average_call+"\r\n");
																	hh[3].write("AverageCallAmount==>\t"+average_callamount+"\r\n");
																	hh[3].write("RiseList\r\n");
																	for(int c=0;c<rise_strength.size();c++){
																		hh[3].write(rise_amount.get(c)+"\t");
																		hh[3].write(rise_strength.get(c)+"\t\r\n");
																		}
																		hh[3].write("CallList\r\n");
																		for(int c=0;c<call_strength.size();c++){
																			hh[3].write(call_amount.get(c)+"\t");
																			hh[3].write(call_strength.get(c)+"\t\r\n");
																			}
																	//hh[3].close();

													////////////////////////player 3 ends/////////
																	////////player 4 begins////////postflop begins/////////////
																	//hh[4] = new FileWriter(new File("P4.txt"),false);
																	rr[4] = new RandomAccessFile(new File("Player4.txt"),"r");
																	action = new ArrayList<String>();
																	amount = new ArrayList<Integer>();
																	strength = new ArrayList<Double>();
																	rise_amount = new ArrayList<Integer>();
																	rise_strength = new ArrayList<Double>();
																	call_amount= new ArrayList<Integer>();
																	call_strength = new ArrayList<Double>();

																	for(int i=1;i<11;i++)
																	{
																		String t = "";
																		while(! t.equals("4th"))
																		{
																			//System.out.println(t);
																			t = rr[4].readLine();
																		}
																		t = "";
																		while(!(t.equals("End")||t.equals("-")))
																		{
																			rr[4].seek(rr[4].getFilePointer()+1);
																			t = rr[4].readLine();
																			//System.out.println(t);
																			if(!(t.equals("End")||t.equals("-")))
																			{
																				action.add(t);
																				rr[4].seek(rr[4].getFilePointer()+1);
																				int z = Integer.parseInt(rr[4].readLine());
																				//System.out.println(z);
																				amount.add(z);
																				rr[4].seek(rr[4].getFilePointer()+1);
																				rr[4].readLine();
																				rr[4].seek(rr[4].getFilePointer()+1);
																				Double x = Double.parseDouble(rr[4].readLine());
																				strength.add(x);
																				//System.out.println(x);
																			}
																		}
																	}
																	//three arryalist have been made now.
																		n = strength.size();
																		for(int i=n-1;i>0;i--)
																			for(int j=0;j<i;j++)
																				if(strength.get(j)>strength.get(j+1))
																				{
																					double temp = strength.get(j);
																					strength.set(j,strength.get(j+1));
																					strength.set(j+1,temp);
																					String temp1 = action.get(j);
																					action.set(j,action.get(j+1));
																					action.set(j+1,temp1);
																					int temp2 = amount.get(j);
																					amount.set(j,amount.get(j+1));
																					amount.set(j+1,temp2);
																				}
																		if(n>0){
																		max_strength = strength.get(n-1);
																		min_strength = strength.get(0);
																		max_action = action.get(n-1);
																		min_action = action.get(0);
																		max_amount = amount.get(n-1);
																		min_amount = amount.get(0);
																		}
																	/*	for(int i=0;i<n;i++){
																			System.out.print(amount.get(i));
																			System.out.print(action.get(i));
																			System.out.println(strength.get(i));
																}*/
																		for(int i=n-1;i>0;i--)
																			for(int j=0;j<i;j++)
																				if(amount.get(j)>amount.get(j+1))
																				{
																					int temp = amount.get(j);
																					amount.set(j,amount.get(j+1));
																					amount.set(j+1,temp);
																					String temp1 = action.get(j);
																					action.set(j,action.get(j+1));
																					action.set(j+1,temp1);
																					double temp2 = strength.get(j);
																					strength.set(j,strength.get(j+1));
																					strength.set(j+1,temp2);
																				}
														//calculating average strength during rise..............
																		k=0;
																		total=0;
																		total1=0;
																		for(int m=n-1;m>-1;m--)
																		{

																			if(action.get(m).equals("Rise")&& amount.get(m)>10)
																			{
																				total1 +=amount.get(m);
																				total +=strength.get(m);
																				rise_amount.add(amount.get(m));
																				rise_strength.add(strength.get(m));
																				//System.out.println(rise_strength.get(k));
																				k++;
																			}
																		}
																		if(k!=0){
																		average_riseamount=total1/k;
																		average_rise = total/k;
																		}
																		else
																		{
																			average_rise =0;
																			average_riseamount=0;
																		}
																		////calculating avergae strength during call............
																		kk =0;
																		total2= 0;
																		total3=0;
																		for(int m=n-1;m>-1;m--)
																		{
																			if(action.get(m).equals("Call"))
																			{
																				total3 +=amount.get(m);
																				total2 +=strength.get(m);
																				//System.out.println(amount.get(m));
																				call_amount.add(amount.get(m));
																				//System.out.println();
																				call_strength.add(strength.get(m));
																				kk++;
																			}
																		}
																		if(kk!=0){
																		average_call = total2/kk;
																		average_callamount=total3/kk;
																		}
																		else
																		{
																			average_call =0;
																			average_callamount=0;
																		}
																		//System.out.println(average_call);

															////writing this information to P2.txt.....
																		hh[4].write("4th Street\r\n");
																		hh[4].write("Maxstrength==>\t"+max_strength+"\r\n");
																		hh[4].write("Minstrength==>\t"+min_strength+"\r\n");
																		hh[4].write("AverageRiseStrength==>\t"+average_rise+"\r\n");
																		hh[4].write("AverageRiseAmount==>\t"+average_riseamount+"\r\n");
																		hh[4].write("AverageCallStrength==>\t"+average_call+"\r\n");
																		hh[4].write("AverageCallAmount==>\t"+average_callamount+"\r\n");
																		hh[4].write("RiseList\r\n");
																		for(int c=0;c<rise_strength.size();c++){
																			hh[4].write(rise_amount.get(c)+"\t");
																			hh[4].write(rise_strength.get(c)+"\t\r\n");
																			}
																			hh[4].write("CallList\r\n");
																			for(int c=0;c<call_strength.size();c++){
																				hh[4].write(call_amount.get(c)+"\t");
																				hh[4].write(call_strength.get(c)+"\t\r\n");
																				}
																		//hh[4].close();
														////////////////////////player 4 ends/////////
																		////////player 4 begins////////postflop begins/////////////
																		//hh[5] = new FileWriter(new File("P5.txt"),false);
																		rr[5] = new RandomAccessFile(new File("Player5.txt"),"r");
																		action = new ArrayList<String>();
																		amount = new ArrayList<Integer>();
																		strength = new ArrayList<Double>();
																		rise_amount = new ArrayList<Integer>();
																		rise_strength = new ArrayList<Double>();
																		call_amount= new ArrayList<Integer>();
																		call_strength = new ArrayList<Double>();

																		for(int i=1;i<11;i++)
																		{
																			String t = "";
																			while(! t.equals("4th"))
																			{
																				//System.out.println(t);
																				t = rr[5].readLine();
																			}
																			t = "";
																			while(!(t.equals("End")||t.equals("-")))
																			{
																				rr[5].seek(rr[5].getFilePointer()+1);
																				t = rr[5].readLine();
																				if(!(t.equals("End")||t.equals("-")))
																				{
																					action.add(t);
																					rr[5].seek(rr[5].getFilePointer()+1);
																					int z = Integer.parseInt(rr[5].readLine());
																					//System.out.println(z);
																					amount.add(z);
																					rr[5].seek(rr[5].getFilePointer()+1);
																					rr[5].readLine();
																					rr[5].seek(rr[5].getFilePointer()+1);
																					Double x = Double.parseDouble(rr[5].readLine());
																					strength.add(x);
																					//System.out.println(x);
																				}
																			}
																		}

																		//three arryalist have been made now.
																			n = strength.size();
																			//System.out.println(n);
																			for(int i=n-1;i>0;i--)
																				for(int j=0;j<i;j++)
																					if(strength.get(j)>strength.get(j+1))
																					{
																						double temp = strength.get(j);
																						strength.set(j,strength.get(j+1));
																						strength.set(j+1,temp);
																						String temp1 = action.get(j);
																						action.set(j,action.get(j+1));
																						action.set(j+1,temp1);
																						int temp2 = amount.get(j);
																						amount.set(j,amount.get(j+1));
																						amount.set(j+1,temp2);
																					}
																			if(n>0)
																					{
																				max_strength = strength.get(n-1);
																				min_strength = strength.get(0);
																				max_action = action.get(n-1);
																				min_action = action.get(0);
																				max_amount = amount.get(n-1);
																				min_amount = amount.get(0);
																					}
																		/*	for(int i=0;i<n;i++){
																				System.out.print(amount.get(i));
																				System.out.print(action.get(i));
																				System.out.println(strength.get(i));
																	}*/
																			for(int i=n-1;i>0;i--)
																				for(int j=0;j<i;j++)
																					if(amount.get(j)>amount.get(j+1))
																					{
																						int temp = amount.get(j);
																						amount.set(j,amount.get(j+1));
																						amount.set(j+1,temp);
																						String temp1 = action.get(j);
																						action.set(j,action.get(j+1));
																						action.set(j+1,temp1);
																						double temp2 = strength.get(j);
																						strength.set(j,strength.get(j+1));
																						strength.set(j+1,temp2);
																					}
															//calculating average strength during rise..............
																			k=0;
																			total=0;
																			total1=0;
																			for(int m=n-1;m>-1;m--)
																			{

																				if(action.get(m).equals("Rise")&& amount.get(m)>10)
																				{
																					total1 +=amount.get(m);
																					total +=strength.get(m);
																					rise_amount.add(amount.get(m));
																					rise_strength.add(strength.get(m));
																					//System.out.println(rise_strength.get(k));
																					k++;
																				}
																			}
																			if(k!=0){
																			average_rise = total/k;
																			average_riseamount=total1/k;
																			}
																			else
																			{
																				average_rise =0;
																				average_riseamount=0;
																			}

																			////calculating avergae strength during call............
																			kk =0;
																			total2= 0;
																			total3=0;
																			for(int m=n-1;m>-1;m--)
																			{
																				if(action.get(m).equals("Call"))
																				{
																					total3 +=amount.get(m);
																					total2 +=strength.get(m);
																					//System.out.println(amount.get(m));
																					call_amount.add(amount.get(m));
																					//System.out.println();
																					call_strength.add(strength.get(m));
																					kk++;
																				}
																			}
																			if(kk!=0){
																			average_call = total2/kk;
																			average_callamount=total3/kk;
																			}
																			else
																			{
																				average_call =0;
																				average_callamount=0;
																			}
																			//System.out.println(average_call);

																////writing this information to P2.txt.....
																			hh[5].write("4th Street\r\n");
																			hh[5].write("Maxstrength==>\t"+max_strength+"\r\n");
																			hh[5].write("Minstrength==>\t"+min_strength+"\r\n");
																			hh[5].write("AverageRiseStrength==>\t"+average_rise+"\r\n");
																			hh[5].write("AverageRiseAmount==>\t"+average_riseamount+"\r\n");
																			hh[5].write("AverageCallStrength==>\t"+average_call+"\r\n");
																			hh[5].write("AverageCallAmount==>\t"+average_callamount+"\r\n");
																			hh[5].write("RiseList\r\n");
																			for(int c=0;c<rise_strength.size();c++){
																				hh[5].write(rise_amount.get(c)+"\t");
																				hh[5].write(rise_strength.get(c)+"\t\r\n");
																				}
																				hh[5].write("CallList\r\n");
																				for(int c=0;c<call_strength.size();c++){
																					hh[5].write(call_amount.get(c)+"\t");
																					hh[5].write(call_strength.get(c)+"\t\r\n");
																					}
																			//hh[5].close();
															////////////////////////player 5 ends/////////
																			////////player 6 begins////////postflop begins/////////////
																			//hh[6] = new FileWriter(new File("P6.txt"),false);
																			rr[6] = new RandomAccessFile(new File("Player6.txt"),"r");
																			action = new ArrayList<String>();
																			amount = new ArrayList<Integer>();
																			strength = new ArrayList<Double>();
																			rise_amount = new ArrayList<Integer>();
																			rise_strength = new ArrayList<Double>();
																			call_amount= new ArrayList<Integer>();
																			call_strength = new ArrayList<Double>();

																			for(int i=1;i<11;i++)
																			{
																				String t = "";
																				while(! t.equals("4th"))
																				{
																					//System.out.println(t);
																					t = rr[6].readLine();
																				}
																				t = "";
																				while(!(t.equals("End")||(t.equals("-"))))
																				{
																					rr[6].seek(rr[6].getFilePointer()+1);
																					t = rr[6].readLine();
																					//System.out.println(t);
																					if(!(t.equals("End")||t.equals("-")))
																					{
																						action.add(t);
																						rr[6].seek(rr[6].getFilePointer()+1);
																						int z = Integer.parseInt(rr[6].readLine());
																						//System.out.println(z);
																						amount.add(z);
																						rr[6].seek(rr[6].getFilePointer()+1);
																						rr[6].readLine();
																						rr[6].seek(rr[6].getFilePointer()+1);
																						Double x = Double.parseDouble(rr[6].readLine());
																						strength.add(x);
																						//System.out.println(x);
																					}
																				}
																			}
																			//three arryalist have been made now.
																				n = strength.size();
																				for(int i=n-1;i>0;i--)
																					for(int j=0;j<i;j++)
																						if(strength.get(j)>strength.get(j+1))
																						{
																							double temp = strength.get(j);
																							strength.set(j,strength.get(j+1));
																							strength.set(j+1,temp);
																							String temp1 = action.get(j);
																							action.set(j,action.get(j+1));
																							action.set(j+1,temp1);
																							int temp2 = amount.get(j);
																							amount.set(j,amount.get(j+1));
																							amount.set(j+1,temp2);
																						}
																				if(n>0){
																				max_strength = strength.get(n-1);
																				min_strength = strength.get(0);
																				max_action = action.get(n-1);
																				min_action = action.get(0);
																				max_amount = amount.get(n-1);
																				min_amount = amount.get(0);
																				}
																			/*	for(int i=0;i<n;i++){
																					System.out.print(amount.get(i));
																					System.out.print(action.get(i));
																					System.out.println(strength.get(i));
																		}*/
																				for(int i=n-1;i>0;i--)
																					for(int j=0;j<i;j++)
																						if(amount.get(j)>amount.get(j+1))
																						{
																							int temp = amount.get(j);
																							amount.set(j,amount.get(j+1));
																							amount.set(j+1,temp);
																							String temp1 = action.get(j);
																							action.set(j,action.get(j+1));
																							action.set(j+1,temp1);
																							double temp2 = strength.get(j);
																							strength.set(j,strength.get(j+1));
																							strength.set(j+1,temp2);
																						}
																//calculating average strength during rise..............
																				k=0;
																				total=0;
																				total1 =0;
																				for(int m=n-1;m>-1;m--)
																				{

																					if(action.get(m).equals("Rise")&& amount.get(m)>10)
																					{
																						total1 +=amount.get(m);
																						total +=strength.get(m);
																						rise_amount.add(amount.get(m));
																						rise_strength.add(strength.get(m));
																						//System.out.println(rise_strength.get(k));
																						k++;
																					}
																				}
																				if(k!=0)
																				{
																				average_riseamount=total1/k;
																				average_rise = total/k;
																				}
																				else
																				{
																					average_rise =0;
																					average_riseamount=0;
																				}
																				////calculating avergae strength during call............
																				kk =0;
																				total2= 0;
																				total3=0;
																				for(int m=n-1;m>-1;m--)
																				{
																					if(action.get(m).equals("Call"))
																					{
																						total3 +=amount.get(m);
																						total2 +=strength.get(m);
																						//System.out.println(amount.get(m));
																						call_amount.add(amount.get(m));
																						//System.out.println();
																						call_strength.add(strength.get(m));
																						kk++;
																					}
																				}
																				if(kk!=0){
																				average_call = total2/kk;
																				average_callamount=total3/kk;
																				}
																				else
																				{
																					average_call =0;
																					average_callamount=0;
																				}
																				//System.out.println(average_call);

																	////writing this information to P2.txt.....
																				hh[6].write("4th Street\r\n");
																				hh[6].write("Maxstrength==>\t"+max_strength+"\r\n");
																				hh[6].write("Minstrength==>\t"+min_strength+"\r\n");
																				hh[6].write("AverageRiseStrength==>\t"+average_rise+"\r\n");
																				hh[6].write("AverageRiseAmount==>\t"+average_riseamount+"\r\n");
																				hh[6].write("AverageCallStrength==>\t"+average_call+"\r\n");
																				hh[6].write("AverageCallAmount==>\t"+average_callamount+"\r\n");
																				hh[6].write("RiseList\r\n");
																				for(int c=0;c<rise_strength.size();c++){
																					hh[6].write(rise_amount.get(c)+"\t");
																					hh[6].write(rise_strength.get(c)+"\t\r\n");
																					}
																					hh[6].write("CallList\r\n");
																					for(int c=0;c<call_strength.size();c++){
																						hh[6].write(call_amount.get(c)+"\t");
																						hh[6].write(call_strength.get(c)+"\t\r\n");
																						}
																				//hh[6].close();
																////////////////////////player 6 ends/////////
																				/////////////////4th street ends/////////////////
																				///////5th street begins////////////////////

																				/////player 1 begins//////writing to file P	////////player 1 begins////////postflop begins/////////////
																					//hh[1] = new FileWriter(new File("P1.txt"),false);
																					rr[1] = new RandomAccessFile(new File("Player1.txt"),"r");
																					action = new ArrayList<String>();
																					amount = new ArrayList<Integer>();
																					strength = new ArrayList<Double>();
																					rise_amount = new ArrayList<Integer>();
																					rise_strength = new ArrayList<Double>();
																					call_amount= new ArrayList<Integer>();
																					call_strength = new ArrayList<Double>();

																					for(int i=1;i<11;i++)
																					{
																						String t = "";
																						while(! t.equals("5th"))
																						{
																							//System.out.println(t);
																							t = rr[1].readLine();
																						}
																						t = "";
																						while(!(t.equals("End")||t.equals("-")))
																						{
																							rr[1].seek(rr[1].getFilePointer()+1);
																							t = rr[1].readLine();
																							//System.out.println(t);
																							if(!(t.equals("End")||t.equals("-")))
																							{
																								action.add(t);
																								rr[1].seek(rr[1].getFilePointer()+1);
																								int z = Integer.parseInt(rr[1].readLine());
																								//System.out.println(z);
																								amount.add(z);
																								rr[1].seek(rr[1].getFilePointer()+1);
																								rr[1].readLine();
																								rr[1].seek(rr[1].getFilePointer()+1);
																								Double x = Double.parseDouble(rr[1].readLine());
																								strength.add(x);
																								//System.out.println(x);
																							}
																						}
																					}
																					//three arryalist have been made now.
																						n = strength.size();
																						for(int i=n-1;i>0;i--)
																							for(int j=0;j<i;j++)
																								if(strength.get(j)>strength.get(j+1))
																								{
																									double temp = strength.get(j);
																									strength.set(j,strength.get(j+1));
																									strength.set(j+1,temp);
																									String temp1 = action.get(j);
																									action.set(j,action.get(j+1));
																									action.set(j+1,temp1);
																									int temp2 = amount.get(j);
																									amount.set(j,amount.get(j+1));
																									amount.set(j+1,temp2);
																								}
																						if(n>0){
																						max_strength = strength.get(n-1);
																						min_strength = strength.get(0);
																						max_action = action.get(n-1);
																						min_action = action.get(0);
																						max_amount = amount.get(n-1);
																						min_amount = amount.get(0);
																						}
																					/*	for(int i=0;i<n;i++){
																							System.out.print(amount.get(i));
																							System.out.print(action.get(i));
																							System.out.println(strength.get(i));
																				}*/
																						for(int i=n-1;i>0;i--)
																							for(int j=0;j<i;j++)
																								if(amount.get(j)>amount.get(j+1))
																								{
																									int temp = amount.get(j);
																									amount.set(j,amount.get(j+1));
																									amount.set(j+1,temp);
																									String temp1 = action.get(j);
																									action.set(j,action.get(j+1));
																									action.set(j+1,temp1);
																									double temp2 = strength.get(j);
																									strength.set(j,strength.get(j+1));
																									strength.set(j+1,temp2);
																								}
																		//calculating average strength during rise..............
																						k=0;
																						total=0;
																						total1=0;
																						for(int m=n-1;m>-1;m--)
																						{

																							if(action.get(m).equals("Rise")&& amount.get(m)>20)
																							{
																								total1 +=amount.get(m);
																								total +=strength.get(m);
																								rise_amount.add(amount.get(m));
																								rise_strength.add(strength.get(m));
																								//System.out.println(rise_strength.get(k));
																								k++;
																							}
																						}
																						if(k!=0){
																						average_rise = total/k;
																						average_riseamount=total1/k;
																						}
																						else
																						{
																							average_rise =0;
																							average_riseamount=0;
																						}
																						////calculating avergae strength during call............
																						kk =0;
																						total2= 0;
																						total3=0;
																						for(int m=n-1;m>-1;m--)
																						{
																							if(action.get(m).equals("Call"))
																							{
																								total3 +=amount.get(m);
																								total2 +=strength.get(m);
																								//System.out.println(amount.get(m));
																								call_amount.add(amount.get(m));
																								//System.out.println();
																								call_strength.add(strength.get(m));
																								kk++;
																							}
																						}
																						if(kk!=0){
																						average_call = total2/kk;
																						average_callamount=total3/kk;
																						}
																						else
																						{
																							average_call =0;
																							average_callamount=0;
																						}
																						//System.out.println(average_call);

																			////writing this information to P2.txt.....
																						hh[1].write("5th Street\r\n");
																						hh[1].write("Maxstrength==>\t"+max_strength+"\r\n");
																						hh[1].write("Minstrength==>\t"+min_strength+"\r\n");
																						hh[1].write("AverageRiseStrength==>\t"+average_rise+"\r\n");
																						hh[1].write("AverageRiseAmount==>\t"+average_riseamount+"\r\n");
																						hh[1].write("AverageCallStrength==>\t"+average_call+"\r\n");
																						hh[1].write("AverageCallAmount==>\t"+average_callamount+"\r\n");
																						hh[1].write("RiseList\r\n");
																						for(int c=0;c<rise_strength.size();c++){
																							hh[1].write(rise_amount.get(c)+"\t");
																							hh[1].write(rise_strength.get(c)+"\t\r\n");
																							}
																							hh[1].write("CallList\r\n");
																							for(int c=0;c<call_strength.size();c++){
																								hh[1].write(call_amount.get(c)+"\t");
																								hh[1].write(call_strength.get(c)+"\t\r\n");
																								}
																						//hh[1].close();
																		////////////////////////player 1 ends/////////P1.txt///////////

																					//hh[2] = new FileWriter(new File("P2.txt"),false);
																					rr[2] = new RandomAccessFile(new File("Player2.txt"),"r");
																					action = new ArrayList<String>();
																					amount = new ArrayList<Integer>();
																					strength = new ArrayList<Double>();
																					rise_amount = new ArrayList<Integer>();
																					rise_strength = new ArrayList<Double>();
																					call_amount= new ArrayList<Integer>();
																					call_strength = new ArrayList<Double>();

																					for(int i=1;i<11;i++)
																					{
																						String t = "";
																						while(! t.equals("5th"))
																						{
																							//System.out.println(t);
																							t = rr[2].readLine();
																						}
																						t = "";
																						while(!(t.equals("End")||t.equals("-")))
																						{
																							//System.out.println("h");
																							rr[2].seek(rr[2].getFilePointer()+1);
																							t = rr[2].readLine();
																							//System.out.println(t);
																							if(!(t.equals("End")||t.equals("-")))
																							{
																								action.add(t);
																								rr[2].seek(rr[2].getFilePointer()+1);
																								int z = Integer.parseInt(rr[2].readLine());
																								//System.out.println(z);
																								amount.add(z);
																								rr[2].seek(rr[2].getFilePointer()+1);
																								rr[2].readLine();
																								rr[2].seek(rr[2].getFilePointer()+1);
																								Double x = Double.parseDouble(rr[2].readLine());
																								strength.add(x);
																								//System.out.println(x);
																							}
																						}
																					}
																					//three arryalist have been made now.
																						n = strength.size();
																						for(int i=n-1;i>0;i--)
																							for(int j=0;j<i;j++)
																								if(strength.get(j)>strength.get(j+1))
																								{
																									double temp = strength.get(j);
																									strength.set(j,strength.get(j+1));
																									strength.set(j+1,temp);
																									String temp1 = action.get(j);
																									action.set(j,action.get(j+1));
																									action.set(j+1,temp1);
																									int temp2 = amount.get(j);
																									amount.set(j,amount.get(j+1));
																									amount.set(j+1,temp2);
																								}
																						if(n>0){
																						max_strength = strength.get(n-1);
																						min_strength = strength.get(0);
																						max_action = action.get(n-1);
																						min_action = action.get(0);
																						max_amount = amount.get(n-1);
																						min_amount = amount.get(0);
																						}

																					/*	for(int i=0;i<n;i++){
																							System.out.print(amount.get(i));
																							System.out.print(action.get(i));
																							System.out.println(strength.get(i));
																				}*/
																						for(int i=n-1;i>0;i--)
																							for(int j=0;j<i;j++)
																								if(amount.get(j)>amount.get(j+1))
																								{
																									int temp = amount.get(j);
																									amount.set(j,amount.get(j+1));
																									amount.set(j+1,temp);
																									String temp1 = action.get(j);
																									action.set(j,action.get(j+1));
																									action.set(j+1,temp1);
																									double temp2 = strength.get(j);
																									strength.set(j,strength.get(j+1));
																									strength.set(j+1,temp2);
																								}
																		//calculating average strength during rise..............
																						k=0;
																						total=0;
																						total1=0;
																						for(int m=n-1;m>-1;m--)
																						{

																							if(action.get(m).equals("Rise")&& amount.get(m)>20)
																							{
																								total1 +=amount.get(m);
																								total +=strength.get(m);
																								rise_amount.add(amount.get(m));
																								rise_strength.add(strength.get(m));
																								//System.out.println(rise_strength.get(k));
																								k++;
																							}
																						}
																						if(k!=0){
																						average_rise = total/k;
																						average_riseamount=total1/k;
																						}
																						else
																						{
																							average_rise =0;
																							average_riseamount=0;
																						}
																						////calculating avergae strength during call............
																						kk =0;
																						total2= 0;
																						total3=0;
																						for(int m=n-1;m>-1;m--)
																						{
																							if(action.get(m).equals("Call"))
																							{
																								total3 +=amount.get(m);
																								total2 +=strength.get(m);
																								//System.out.println(amount.get(m));
																								call_amount.add(amount.get(m));
																								//System.out.println();
																								call_strength.add(strength.get(m));
																								kk++;
																							}
																						}
																						if(kk!=0){
																						average_call = total2/kk;
																						average_callamount=total3/kk;
																						}
																						else
																						{
																							average_call =0;
																							average_callamount=0;
																						}
																						//System.out.println(average_call);

																			////writing this information to P2.txt.....
																						hh[2].write("5th Street\r\n");
																						hh[2].write("Maxstrength==>\t"+max_strength+"\r\n");
																						hh[2].write("Minstrength==>\t"+min_strength+"\r\n");
																						hh[2].write("AverageRiseStrength==>\t"+average_rise+"\r\n");
																						hh[2].write("AverageRiseAmount==>\t"+average_riseamount+"\r\n");
																						hh[2].write("AverageCallStrength==>\t"+average_call+"\r\n");
																						hh[2].write("AverageCallAmount==>\t"+average_callamount+"\r\n");
																						hh[2].write("RiseList\r\n");
																						for(int c=0;c<rise_strength.size();c++){
																							hh[2].write(rise_amount.get(c)+"\t");
																							hh[2].write(rise_strength.get(c)+"\t\r\n");
																							}
																							hh[2].write("CallList\r\n");
																							for(int c=0;c<call_strength.size();c++){
																								hh[2].write(call_amount.get(c)+"\t");
																								hh[2].write(call_strength.get(c)+"\t\r\n");
																								}
																						//hh[2].close();
																		////////////////////////player 2 ends/////////
																						//////////player 3 postflop begins/////////////////////////////
																						//hh[3] = new FileWriter(new File("P3.txt"),false);
																						rr[3] = new RandomAccessFile(new File("Player3.txt"),"r");
																						action = new ArrayList<String>();
																						amount = new ArrayList<Integer>();
																						strength = new ArrayList<Double>();
																						rise_amount = new ArrayList<Integer>();
																						rise_strength = new ArrayList<Double>();
																						call_amount= new ArrayList<Integer>();
																						call_strength = new ArrayList<Double>();

																						for(int i=1;i<11;i++)
																						{
																							String t = "";
																							while(! t.equals("5th"))
																							{
																								//System.out.println(t);
																								t = rr[3].readLine();
																							}
																							t = "";
																							while(!(t.equals("End")||t.equals("-")))
																							{
																								rr[3].seek(rr[3].getFilePointer()+1);
																								t = rr[3].readLine();
																								//System.out.println(t);
																								if(!(t.equals("End")||t.equals("-")))
																								{
																									action.add(t);
																									rr[3].seek(rr[3].getFilePointer()+1);
																									int z = Integer.parseInt(rr[3].readLine());
																									//System.out.println(z);
																									amount.add(z);
																									rr[3].seek(rr[3].getFilePointer()+1);
																									rr[3].readLine();
																									rr[3].seek(rr[3].getFilePointer()+1);
																									Double x = Double.parseDouble(rr[3].readLine());
																									strength.add(x);
																									//System.out.println(x);
																								}
																							}
																						}

																						//three arryalist have been made now.
																							n = strength.size();
																							for(int i=n-1;i>0;i--)
																								for(int j=0;j<i;j++)
																									if(strength.get(j)>strength.get(j+1))
																									{
																										double temp = strength.get(j);
																										strength.set(j,strength.get(j+1));
																										strength.set(j+1,temp);
																										String temp1 = action.get(j);
																										action.set(j,action.get(j+1));
																										action.set(j+1,temp1);
																										int temp2 = amount.get(j);
																										amount.set(j,amount.get(j+1));
																										amount.set(j+1,temp2);
																									}
																							if(n>0){
																							max_strength = strength.get(n-1);
																							min_strength = strength.get(0);
																							max_action = action.get(n-1);
																							min_action = action.get(0);
																							max_amount = amount.get(n-1);
																							min_amount = amount.get(0);
																							}
																						/*	for(int i=0;i<n;i++){
																								System.out.print(amount.get(i));
																								System.out.print(action.get(i));
																								System.out.println(strength.get(i));
																					}*/
																							for(int i=n-1;i>0;i--)
																								for(int j=0;j<i;j++)
																									if(amount.get(j)>amount.get(j+1))
																									{
																										int temp = amount.get(j);
																										amount.set(j,amount.get(j+1));
																										amount.set(j+1,temp);
																										String temp1 = action.get(j);
																										action.set(j,action.get(j+1));
																										action.set(j+1,temp1);
																										double temp2 = strength.get(j);
																										strength.set(j,strength.get(j+1));
																										strength.set(j+1,temp2);
																									}
																			//calculating average strength during rise..............
																							k=0;
																							total=0;
																							total1=0;
																							for(int m=n-1;m>-1;m--)
																							{
																								if(action.get(m).equals("Rise")&& amount.get(m)>20)
																								{
																									total1 +=amount.get(m);
																									total +=strength.get(m);
																									rise_amount.add(amount.get(m));
																									rise_strength.add(strength.get(m));
																									//System.out.println(rise_strength.get(k));
																									k++;
																								}
																							}
																							if(k!=0){
																							average_rise = total/k;
																							average_riseamount =total1/k;
																							}
																							else
																							{
																								average_rise =0;
																								average_riseamount=0;
																							}
																							////calculating avergae strength during call............
																							kk =0;
																							total2= 0;
																							total3=0;
																							for(int m=n-1;m>-1;m--)
																							{
																								if(action.get(m).equals("Call"))
																								{
																									total3 +=amount.get(m);
																									total2 +=strength.get(m);
																									//System.out.println(amount.get(m));
																									call_amount.add(amount.get(m));
																									//System.out.println();
																									call_strength.add(strength.get(m));
																									kk++;
																								}
																							}
																							if(kk!=0){
																							average_call = total2/kk;
																							average_callamount=total3/kk;
																							}
																							else
																							{
																								average_call =0;
																								average_callamount=0;
																							}

																							//System.out.println(average_call);

																				////writing this information to P2.txt.....
																							hh[3].write("5th Street\r\n");
																							hh[3].write("Maxstrength==>\t"+max_strength+"\r\n");
																							hh[3].write("Minstrength==>\t"+min_strength+"\r\n");
																							hh[3].write("AverageRiseStrength==>\t"+average_rise+"\r\n");
																							hh[3].write("AverageRiseAmount==>\t"+average_riseamount+"\r\n");
																							hh[3].write("AverageCallStrength==>\t"+average_call+"\r\n");
																							hh[3].write("AverageCallAmount==>\t"+average_callamount+"\r\n");
																							hh[3].write("RiseList\r\n");
																							for(int c=0;c<rise_strength.size();c++){
																								hh[3].write(rise_amount.get(c)+"\t");
																								hh[3].write(rise_strength.get(c)+"\t\r\n");
																								}
																								hh[3].write("CallList\r\n");
																								for(int c=0;c<call_strength.size();c++){
																									hh[3].write(call_amount.get(c)+"\t");
																									hh[3].write(call_strength.get(c)+"\t\r\n");
																									}
																							//hh[3].close();
																			////////////////////////player 3 ends/////////
																							////////player 4 begins////////postflop begins/////////////
																							//hh[4] = new FileWriter(new File("P4.txt"),false);
																							rr[4] = new RandomAccessFile(new File("Player4.txt"),"r");
																							action = new ArrayList<String>();
																							amount = new ArrayList<Integer>();
																							strength = new ArrayList<Double>();
																							rise_amount = new ArrayList<Integer>();
																							rise_strength = new ArrayList<Double>();
																							call_amount= new ArrayList<Integer>();
																							call_strength = new ArrayList<Double>();

																							for(int i=1;i<11;i++)
																							{
																								String t = "";
																								while(! t.equals("5th"))
																								{
																									//System.out.println(t);
																									t = rr[4].readLine();
																								}
																								t = "";
																								while(!(t.equals("End")||t.equals("-")))
																								{
																									rr[4].seek(rr[4].getFilePointer()+1);
																									t = rr[4].readLine();
																									//System.out.println(t);
																									if(!(t.equals("End")||t.equals("-")))
																									{
																										action.add(t);
																										rr[4].seek(rr[4].getFilePointer()+1);
																										int z = Integer.parseInt(rr[4].readLine());
																										//System.out.println(z);
																										amount.add(z);
																										rr[4].seek(rr[4].getFilePointer()+1);
																										rr[4].readLine();
																										rr[4].seek(rr[4].getFilePointer()+1);
																										Double x = Double.parseDouble(rr[4].readLine());
																										strength.add(x);
																										//System.out.println(x);
																									}
																								}
																							}
																							//three arryalist have been made now.
																								n = strength.size();
																								for(int i=n-1;i>0;i--)
																									for(int j=0;j<i;j++)
																										if(strength.get(j)>strength.get(j+1))
																										{
																											double temp = strength.get(j);
																											strength.set(j,strength.get(j+1));
																											strength.set(j+1,temp);
																											String temp1 = action.get(j);
																											action.set(j,action.get(j+1));
																											action.set(j+1,temp1);
																											int temp2 = amount.get(j);
																											amount.set(j,amount.get(j+1));
																											amount.set(j+1,temp2);
																										}
																								if(n>0){
																								max_strength = strength.get(n-1);
																								min_strength = strength.get(0);
																								max_action = action.get(n-1);
																								min_action = action.get(0);
																								max_amount = amount.get(n-1);
																								min_amount = amount.get(0);
																								}
																							/*	for(int i=0;i<n;i++){
																									System.out.print(amount.get(i));
																									System.out.print(action.get(i));
																									System.out.println(strength.get(i));
																						}*/
																								for(int i=n-1;i>0;i--)
																									for(int j=0;j<i;j++)
																										if(amount.get(j)>amount.get(j+1))
																										{
																											int temp = amount.get(j);
																											amount.set(j,amount.get(j+1));
																											amount.set(j+1,temp);
																											String temp1 = action.get(j);
																											action.set(j,action.get(j+1));
																											action.set(j+1,temp1);
																											double temp2 = strength.get(j);
																											strength.set(j,strength.get(j+1));
																											strength.set(j+1,temp2);
																										}
																				//calculating average strength during rise..............
																								k=0;
																								total=0;
																								total1=0;
																								for(int m=n-1;m>-1;m--)
																								{

																									if(action.get(m).equals("Rise")&& amount.get(m)>20)
																									{
																										total1 +=amount.get(m);
																										total +=strength.get(m);
																										rise_amount.add(amount.get(m));
																										rise_strength.add(strength.get(m));
																										//System.out.println(rise_strength.get(k));
																										k++;
																									}
																								}
																								if(k!=0)
																								{
																								average_rise = total/k;
																								average_riseamount=total/k;
																								}
																								else
																								{
																									average_rise =0;
																									average_riseamount=0;
																								}
																								////calculating avergae strength during call............
																								kk =0;
																								total2= 0;
																								total3 =0;
																								for(int m=n-1;m>-1;m--)
																								{
																									if(action.get(m).equals("Call"))
																									{
																										total2 +=strength.get(m);
																										total3 +=amount.get(m);
																										//System.out.println(amount.get(m));
																										call_amount.add(amount.get(m));
																										//System.out.println();
																										call_strength.add(strength.get(m));
																										kk++;
																									}
																								}
																								if(kk!=0){
																								average_call = total2/kk;
																								average_callamount = total3/kk;
																								}
																								else
																								{
																									average_call =0;
																									average_callamount=0;
																								}
																								//System.out.println(average_call);

																					////writing this information to P2.txt.....
																								hh[4].write("5th Street\r\n");
																								hh[4].write("Maxstrength==>\t"+max_strength+"\r\n");
																								hh[4].write("Minstrength==>\t"+min_strength+"\r\n");
																								hh[4].write("AverageRiseStrength==>\t"+average_rise+"\r\n");
																								hh[4].write("AverageRiseAmount==>\t"+average_riseamount+"\r\n");
																								hh[4].write("AverageCallStrength==>\t"+average_call+"\r\n");
																								hh[4].write("AverageCallAmount==>\t"+average_callamount+"\r\n");
																								hh[4].write("RiseList\r\n");
																								for(int c=0;c<rise_strength.size();c++){
																									hh[4].write(rise_amount.get(c)+"\t");
																									hh[4].write(rise_strength.get(c)+"\t\r\n");
																									}
																									hh[4].write("CallList\r\n");
																									for(int c=0;c<call_strength.size();c++){
																										hh[4].write(call_amount.get(c)+"\t");
																										hh[4].write(call_strength.get(c)+"\t\r\n");
																										}
																								//hh[4].close();
																				////////////////////////player 4 ends/////////
																								////////player 5 begins////////postflop begins/////////////
																								//hh[5] = new FileWriter(new File("P5.txt"),false);
																								rr[5] = new RandomAccessFile(new File("Player5.txt"),"r");
																								action = new ArrayList<String>();
																								amount = new ArrayList<Integer>();
																								strength = new ArrayList<Double>();
																								rise_amount = new ArrayList<Integer>();
																								rise_strength = new ArrayList<Double>();
																								call_amount= new ArrayList<Integer>();
																								call_strength = new ArrayList<Double>();

																								for(int i=1;i<11;i++)
																								{
																									String t = "";
																									while(! t.equals("5th"))
																									{
																										//System.out.println(t);
																										t = rr[5].readLine();
																									}
																									t = "";
																									while(!(t.equals("End")||t.equals("-")))
																									{
																										rr[5].seek(rr[5].getFilePointer()+1);
																										t = rr[5].readLine();
																										if(!(t.equals("End")||t.equals("-")))
																										{
																											action.add(t);
																											rr[5].seek(rr[5].getFilePointer()+1);
																											int z = Integer.parseInt(rr[5].readLine());
																											//System.out.println(z);
																											amount.add(z);
																											rr[5].seek(rr[5].getFilePointer()+1);
																											rr[5].readLine();
																											rr[5].seek(rr[5].getFilePointer()+1);
																											Double x = Double.parseDouble(rr[5].readLine());
																											strength.add(x);
																											//System.out.println(x);
																										}
																									}
																								}

																								//three arryalist have been made now.
																									n = strength.size();
																									//System.out.println(n);
																									for(int i=n-1;i>0;i--)
																										for(int j=0;j<i;j++)
																											if(strength.get(j)>strength.get(j+1))
																											{
																												double temp = strength.get(j);
																												strength.set(j,strength.get(j+1));
																												strength.set(j+1,temp);
																												String temp1 = action.get(j);
																												action.set(j,action.get(j+1));
																												action.set(j+1,temp1);
																												int temp2 = amount.get(j);
																												amount.set(j,amount.get(j+1));
																												amount.set(j+1,temp2);
																											}
																									if(n>0)
																											{
																										max_strength = strength.get(n-1);
																										min_strength = strength.get(0);
																										max_action = action.get(n-1);
																										min_action = action.get(0);
																										max_amount = amount.get(n-1);
																										min_amount = amount.get(0);
																											}
																								/*	for(int i=0;i<n;i++){
																										System.out.print(amount.get(i));
																										System.out.print(action.get(i));
																										System.out.println(strength.get(i));
																							}*/
																									for(int i=n-1;i>0;i--)
																										for(int j=0;j<i;j++)
																											if(amount.get(j)>amount.get(j+1))
																											{
																												int temp = amount.get(j);
																												amount.set(j,amount.get(j+1));
																												amount.set(j+1,temp);
																												String temp1 = action.get(j);
																												action.set(j,action.get(j+1));
																												action.set(j+1,temp1);
																												double temp2 = strength.get(j);
																												strength.set(j,strength.get(j+1));
																												strength.set(j+1,temp2);
																											}
																					//calculating average strength during rise..............
																									k=0;
																									total=0;
																									total1=0;
																									for(int m=n-1;m>-1;m--)
																									{

																										if(action.get(m).equals("Rise")&& amount.get(m)>20)
																										{
																											total1 +=amount.get(m);
																											total +=strength.get(m);
																											rise_amount.add(amount.get(m));
																											rise_strength.add(strength.get(m));
																											//System.out.println(rise_strength.get(k));
																											k++;
																										}
																									}
																									if(k!=0){
																									average_riseamount=total1/k;
																									average_rise = total/k;
																									}
																									else
																									{
																										average_rise =0;
																										average_riseamount=0;
																									}
																									////calculating avergae strength during call............
																									kk =0;
																									total2= 0;
																									total3=0;
																									for(int m=n-1;m>-1;m--)
																									{
																										if(action.get(m).equals("Call"))
																										{
																											total2 +=strength.get(m);
																											total3 +=amount.get(m);
																											//System.out.println(amount.get(m));
																											call_amount.add(amount.get(m));
																											//System.out.println();
																											call_strength.add(strength.get(m));
																											kk++;
																										}
																									}
																									if(kk!=0){
																									average_call = total2/kk;
																									average_callamount=total3/kk;
																									}
																									else
																									{
																										average_call =0;
																										average_callamount=0;
																									}
																									//System.out.println(average_call);

																						////writing this information to P2.txt.....
																									hh[5].write("5th Street\r\n");
																									hh[5].write("Maxstrength==>\t"+max_strength+"\r\n");
																									hh[5].write("Minstrength==>\t"+min_strength+"\r\n");
																									hh[5].write("AverageRiseStrength==>\t"+average_rise+"\r\n");
																									hh[5].write("AverageRiseAmount==>\t"+average_riseamount+"\r\n");
																									hh[5].write("AverageCallStrength==>\t"+average_call+"\r\n");
																									hh[5].write("AverageCallAmount==>\t"+average_callamount+"\r\n");
																									hh[5].write("RiseList\r\n");
																									for(int c=0;c<rise_strength.size();c++){
																									hh[5].write(rise_amount.get(c)+"\t");
																									hh[5].write(rise_strength.get(c)+"\t\r\n");
																									}
																									hh[5].write("CallList\r\n");
																									for(int c=0;c<call_strength.size();c++){
																										hh[5].write(call_amount.get(c)+"\t");
																										hh[5].write(call_strength.get(c)+"\t\r\n");
																										}
																									//hh[5].close();
																					////////////////////////player 5 ends/////////
																									////////player 6 begins////////postflop begins/////////////
																									//hh[6] = new FileWriter(new File("P6.txt"),false);
																									rr[6] = new RandomAccessFile(new File("Player6.txt"),"r");
																									action = new ArrayList<String>();
																									amount = new ArrayList<Integer>();
																									strength = new ArrayList<Double>();
																									rise_amount = new ArrayList<Integer>();
																									rise_strength = new ArrayList<Double>();
																									call_amount= new ArrayList<Integer>();
																									call_strength = new ArrayList<Double>();

																									for(int i=1;i<11;i++)
																									{
																										String t = "";
																										while(! t.equals("5th"))
																										{
																											//System.out.println(t);
																											t = rr[6].readLine();
																										}
																										t = "";
																										while(!(t.equals("End")||(t.equals("-"))))
																										{
																											rr[6].seek(rr[6].getFilePointer()+1);
																											t = rr[6].readLine();
																											//System.out.println(t);
																											if(!(t.equals("End")||t.equals("-")))
																											{
																												action.add(t);
																												rr[6].seek(rr[6].getFilePointer()+1);
																												int z = Integer.parseInt(rr[6].readLine());
																												//System.out.println(z);
																												amount.add(z);
																												rr[6].seek(rr[6].getFilePointer()+1);
																												rr[6].readLine();
																												rr[6].seek(rr[6].getFilePointer()+1);
																												Double x = Double.parseDouble(rr[6].readLine());
																												strength.add(x);
																												//System.out.println(x);
																											}
																										}
																									}
																									//three arryalist have been made now.
																										n = strength.size();
																										for(int i=n-1;i>0;i--)
																											for(int j=0;j<i;j++)
																												if(strength.get(j)>strength.get(j+1))
																												{
																													double temp = strength.get(j);
																													strength.set(j,strength.get(j+1));
																													strength.set(j+1,temp);
																													String temp1 = action.get(j);
																													action.set(j,action.get(j+1));
																													action.set(j+1,temp1);
																													int temp2 = amount.get(j);
																													amount.set(j,amount.get(j+1));
																													amount.set(j+1,temp2);
																												}
																										if(n>0){
																										max_strength = strength.get(n-1);
																										min_strength = strength.get(0);
																										max_action = action.get(n-1);
																										min_action = action.get(0);
																										max_amount = amount.get(n-1);
																										min_amount = amount.get(0);
																										}
																									/*	for(int i=0;i<n;i++){
																											System.out.print(amount.get(i));
																											System.out.print(action.get(i));
																											System.out.println(strength.get(i));
																								}*/
																										for(int i=n-1;i>0;i--)
																											for(int j=0;j<i;j++)
																												if(amount.get(j)>amount.get(j+1))
																												{
																													int temp = amount.get(j);
																													amount.set(j,amount.get(j+1));
																													amount.set(j+1,temp);
																													String temp1 = action.get(j);
																													action.set(j,action.get(j+1));
																													action.set(j+1,temp1);
																													double temp2 = strength.get(j);
																													strength.set(j,strength.get(j+1));
																													strength.set(j+1,temp2);
																												}
																						//calculating average strength during rise..............
																										k=0;
																										total=0;
																										total1=0;
																										for(int m=n-1;m>-1;m--)
																										{

																											if(action.get(m).equals("Rise")&& amount.get(m)>20)
																											{
																												total1 +=amount.get(m);
																												total +=strength.get(m);
																												rise_amount.add(amount.get(m));
																												rise_strength.add(strength.get(m));
																												//System.out.println(rise_strength.get(k));
																												k++;
																											}
																										}
																										if(k!=0){
																										average_riseamount=total1/k;
																										average_rise = total/k;
																										}
																										else
																										{
																											average_rise =0;
																											average_riseamount=0;
																										}
																										////calculating avergae strength during call............
																										kk =0;
																										total2= 0;
																										total3=0;
																										for(int m=n-1;m>-1;m--)
																										{
																											if(action.get(m).equals("Call"))
																											{
																												total3 +=amount.get(m);
																												total2 +=strength.get(m);
																												//System.out.println(amount.get(m));
																												call_amount.add(amount.get(m));
																												//System.out.println();
																												call_strength.add(strength.get(m));
																												kk++;
																											}
																										}
																										if(kk!=0)
																										{
																										average_call = total2/kk;
																										average_callamount=total3/kk;
																										}
																										else
																										{
																											average_call =0;
																											average_callamount=0;
																										}
																										//System.out.println(average_call);

																							////writing this information to P2.txt.....
																										hh[6].write("5th Street\r\n");
																										hh[6].write("Maxstrength==>\t"+max_strength+"\r\n");
																										hh[6].write("Minstrength==>\t"+min_strength+"\r\n");
																										hh[6].write("AverageRiseStrength==>\t"+average_rise+"\r\n");
																										hh[6].write("AverageRiseAmount==>\t"+average_riseamount+"\r\n");
																										hh[6].write("AverageCallStrength==>\t"+average_call+"\r\n");
																										hh[6].write("AverageCallAmount==>\t"+average_callamount+"\r\n");
																										hh[6].write("RiseList\r\n");
																										for(int c=0;c<rise_strength.size();c++){
																											hh[6].write(rise_amount.get(c)+"\t");
																											hh[6].write(rise_strength.get(c)+"\t\r\n");
																											}
																											hh[6].write("CallList\r\n");
																											for(int c=0;c<call_strength.size();c++){
																												hh[6].write(call_amount.get(c)+"\t");
																												hh[6].write(call_strength.get(c)+"\t\r\n");
																												}
																										//hh[6].close();

																						////////////////////////player 6 ends/////////

																											hh[1].close();
																											hh[2].close();
																											hh[3].close();
																											hh[4].close();
																											hh[5].close();
																											hh[6].close();

																										///////5th street ends////////////////////
		}
		catch(Exception e)
		{
			try{
				FileWriter out=new FileWriter(new File("outputf.txt"));
				out.write("-10");
				out.close();
				}
				catch(Exception e1){
		System.out.println("Error in making of P[i] files.......");}
		}
	}
}
/////////////////////////////////////////////////////////////////////////////////


class stages {
	//String stage,newentry=null;
	//int playerno,ourno,dealer,npp,action;
	//int balance;
	int action;




	// OPPONENT_STERNGTH=MAX STRENGTH OF ALL THE PLAYERS PLAYING IN THE GAME

	 public int finalflop_strategy(int opponent_rise,int opponent_balance,int our_balance,double opponent_strength,double our_strength,double handstrength)
	 {

				 double h=handstrength;
	if(opponent_balance>100)
	{
if(opponent_rise>.5*opponent_balance)

			if(opponent_rise<.1*our_balance)
				if(h>=.97)
					action=opponent_rise*2;

				else if(h>=.9)
						action=(int)(opponent_rise*2);

				else if(h>=.8)
						action=(int)(opponent_rise*2);

				else if(h>=.7)
					if(Math.random()<.5)
						action=opponent_rise*2;
					else action=-10;

				else if(Math.random()<.5)
					action=opponent_rise*2;
				else action=-10;


			else if(opponent_rise<.25*our_balance)
				if(h>=.97)
					action=opponent_rise*2;

				else if(h>=.9)
						action=0;

				else if(h>=.8)
					if(our_strength>=opponent_strength)
							action=0;
					else action=-10;
				else action=-10;


			else if(opponent_rise<.5*our_balance)
				if(h>=.97)
						action=0;

				else if(h>=.9)
					 action=0;

					else action=-10;



			else if(opponent_rise<.75*our_balance)
				if(h>=.97)
						action=0;

				else if(h>=.9)
						if(our_strength>=opponent_strength)
								action=0;
							else action=-10;
					else action=-10;

				else if(h>=.97)
						action=0;
					else action=-10;


	////////////////////////////////////////////////////////////////////////

else	if(opponent_rise>.25*opponent_balance)

	if(opponent_rise<.1*our_balance)
		if(h>=.97)
			action=opponent_rise*4;

		else if(h>=.9)
				action=opponent_rise*3;

		else if(h>=.8)
				action=(int)(opponent_rise*2);

		else if(Math.random()<.2)
			action=opponent_rise*2;
		else action=-10;

	else if(opponent_rise<.25*our_balance)
		if(h>=.97)
			action=opponent_rise*2;

		else if(h>=.9)
				action=(int)(opponent_rise*1.5);

		else if(h>=.8)
			if(our_strength>=opponent_strength)
					action=0;
			else action=-10;
		else action=-10;


	else if(opponent_rise<.5*our_balance)
		if(h>=.97)
				action=0;

		else if(h>=.9)
			if(our_strength>=opponent_strength)
			 action=0;
			else action=-10;
		else action=-10;



	else if(opponent_rise<.75*our_balance)
		if(h>=.97)
				action=0;

		else if(h>=.9)
				if(our_strength>=opponent_strength&&Math.random()<.5)

						action=0;
					else action=-10;
		else action=-10;

		else if(h>=.97)
				action=0;
			else action=-10;

//////////////////\\\\\\\\\\\\\\\\\\\\\\\\\


else	if(opponent_rise>.15*opponent_balance)

	if(opponent_rise<.1*our_balance)
		if(h>=.97)
		{
			double n=Math.random();
			if(n<.5)
				action=0;
			else if(n<.75)
				action=opponent_rise*4;
			else
				action=opponent_rise*2;
		}



		else if(h>=.9)
		{
			double n=Math.random();
			if(n<.5)
				action=0;
			else if(n<.75)
				action=(int)(opponent_rise*1.5);
			else
				action=opponent_rise*2;

		}


		else if(h>=.8)
		{
			if(our_strength>=opponent_strength)
		{
			double n=Math.random();
			if(n<.75)
				action=0;
			else
				action=(int)(opponent_rise*1.5);

		}
			else
			{
				double n=Math.random();
				if(n<.5)
					action=0;
				else
					action=-10;
			}
		}

		else if(h>=.7)
		{
			if(our_strength>=opponent_strength)
					action=0;
				else
				{
					double n=Math.random();
					if(n<.35)
						action=0;
					else
						action=-10;
				}
		}


		else if(h>=.5)
		{
			if(our_strength>=opponent_strength)
			{
				double n=Math.random();
				if(n<.75)
				action=0;
				else action=-10;
			}
				else
						action=-10;
		}


		else
		{
			if(our_strength>=opponent_strength)
			{
				double n=Math.random();
				if(n<.5)
				action=0;
				else action=-10;
			}
				else
						action=-10;
		}


	else if(opponent_rise<.25*our_balance)
		if(h>=.97)
		{
			double n=Math.random();
			if(n<.5)
				action=0;
			else if(n<.75)
				action=opponent_rise*3;
			else
				action=opponent_rise*2;
		}



		else if(h>=.9)
		{
			double n=Math.random();
			if(n<.5)
				action=0;
			else
				action=(int)(opponent_rise*1.5);

		}


		else if(h>=.8)
		{
			if(our_strength>=opponent_strength)
		{
				action=0;
		}
			else
			{
				double n=Math.random();
				if(n<.25)
					action=0;
				else
					action=-10;
			}
		}

		else if(h>=.7)
		{
			if(our_strength>=opponent_strength)
					{
						double n=Math.random();
						if(n<.75)
						action=0;
						else action=-10;
					}
				else
				{
					double n=Math.random();
					if(n<.25)
						action=0;
					else
						action=-10;
				}
		}


		else if(h>=.5)
		{
			if(our_strength>=opponent_strength)
			{
				double n=Math.random();
				if(n<.5)
				action=0;
				else action=-10;
			}
				else
						action=-10;
		}


		else
		{
			if(our_strength>=opponent_strength)
			{
				double n=Math.random();
				if(n<.25)
				action=0;
				else action=-10;
			}
				else
						action=-10;
		}


	else if(opponent_rise<.5*our_balance)
		if(h>=.97)
		{
			double n=Math.random();
			if(n<.5)
				action=0;
			else if(n<.75)
				action=opponent_rise*3;
			else
				action=opponent_rise*2;
		}



		else if(h>=.9)
		{
			double n=Math.random();
			if(n<.5)
				action=0;
			else
				action=(int)(opponent_rise*1.5);

		}


		else if(h>=.8)
		{
			if(our_strength>=opponent_strength)
		{
				action=0;
		}
			else
			{
				double n=Math.random();
				if(n<.25)
					action=0;
				else
					action=-10;
			}
		}

		else if(h>=.7)
		{
			if(our_strength>=opponent_strength)
					{
						double n=Math.random();
						if(n<.75)
						action=0;
						else action=-10;
					}
				else
				{
					double n=Math.random();
					if(n<.25)
						action=0;
					else
						action=-10;
				}
		}


		else if(h>=.5)
		{
			if(our_strength>=opponent_strength)
			{
				double n=Math.random();
				if(n<.5)
				action=0;
				else action=-10;
			}
				else
						action=-10;
		}


		else
		{
			if(our_strength>=opponent_strength)
			{
				double n=Math.random();
				if(n<.25)
				action=0;
				else action=-10;
			}
				else
						action=-10;
		}


	else if(opponent_rise<.75*our_balance)
		if(h>=.97)
		{
			double n=Math.random();
			if(n<.8)
				action=0;
			else
				action=our_balance;
		}



		else if(h>=.9)
		{
				action=0;
		}


		else if(h>=.8)
		{
			if(our_strength>=opponent_strength)
		{
				action=0;
		}
			else
			{
						action=-10;
			}
		}

		else if(h>=.7)
		{
			if(our_strength>=opponent_strength)
					{
						double n=Math.random();
						if(n<.5)
						action=0;
						else action=-10;
					}
				else
				{
					action=-10;
				}
		}


		else if(h>=.5)
		{
						action=-10;
		}


		else
		{

						action=-10;
		}


	else if(h>=.97)
			action=0;

		else action=-10;




/////\\\\\/\//\/\\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\


else
	if(opponent_rise<.1*our_balance)
		if(h>=.97)
		{
			double n=Math.random();
			if(n<.5)
				action=0;
			else if(n<.75)
				action=opponent_rise*4;
			else
				action=opponent_rise*2;

		}



		else if(h>=.9)
		{
			double n=Math.random();
			if(n<.5)
				action=0;
			else if(n<.75)
				action=(int)(opponent_rise*1.5);
			else
				action=opponent_rise*2;

		}


		else if(h>=.8)
		{
			if(our_strength>=opponent_strength)
		{
			double n=Math.random();
			if(n<.75)
				action=0;
			else
				action=(int)(opponent_rise*1.5);

		}
			else
			{
				double n=Math.random();
				if(n<.5)
					action=0;
				else
					action=-10;
			}
		}

		else if(h>=.7)
		{
			if(our_strength>=opponent_strength)
					action=0;
				else
				{
					double n=Math.random();
					if(n<.35)
						action=0;
					else
						action=-10;
				}
		}


		else if(h>=.5)
		{
			if(our_strength>=opponent_strength)
			{
				double n=Math.random();
				if(n<.5)
				action=0;
				else action=-10;
			}
				else
						action=-10;
		}


		else
		{
			if(our_strength>=opponent_strength)
			{
				double n=Math.random();
				if(n<.25)
				action=0;
				else action=-10;
			}
				else
						action=-10;
		}


	else if(opponent_rise<.25*our_balance)
		if(h>=.97)
		{
			double n=Math.random();
			if(n<.5)
				action=0;
			else if(n<.75)
				action=opponent_rise*3;
			else
				action=opponent_rise*2;

		}



		else if(h>=.9)
		{
			double n=Math.random();
			if(n<.5)
				action=0;
			else
				action=(int)(opponent_rise*1.5);

		}


		else if(h>=.8)
		{
			if(our_strength>=opponent_strength)
		{

				action=0;


		}
			else
			{
				double n=Math.random();
				if(n<.25)
					action=0;
				else
					action=-10;
			}
		}



		else if(h>=.7)
		{
			if(our_strength>=opponent_strength)
			{
				double n=Math.random();
				if(n<.5)
				action=0;
				else action=-10;
			}
				else
						action=-10;
		}


		else
		{
						action=-10;
		}



	else if(opponent_rise<.5*our_balance)
		if(h>=.97)
		{
			double n=Math.random();
			if(n<.5)
				action=0;
			else
				action=opponent_rise*2;

		}



		else if(h>=.9)
		{
			if(our_strength>=opponent_strength)
			{
			double n=Math.random();
			if(n<.5)
				action=0;
			else
				action=(int)(opponent_rise*1.5);
			}
			else action=0;

		}


		else if(h>=.8)
		{
			if(our_strength>=opponent_strength)
		{

				action=0;


		}
			else
			{
				double n=Math.random();
				if(n<.25)
					action=0;
				else
					action=-10;
			}
		}


		else if(h>=.7)
		{
			if(our_strength>=opponent_strength)
			{
				double n=Math.random();
				if(n<.25)
				action=0;
				else action=-10;
			}
				else
						action=-10;
		}


		else
		{
						action=-10;
		}





	else if(opponent_rise<.75*our_balance)
		if(h>=.97)
		{
			action=0;

		}



		else if(h>=.9)
		{
			if(our_strength>=opponent_strength)
			{
			double n=Math.random();
			if(n<.75)
				action=0;
			else
				 action=-10;
			}

		}

		else action=-10;





	else if(h>=.97)
			action=0;

		else action=-10;

	}
	//////
	else
	{
		if(h>=.97&&our_balance>=400)
		{
			double n=Math.random();
			if(n<.4)
				action=0;
			else if(n<.65)
				action=opponent_rise*4;
			else if(n<.9)
				action=opponent_rise*2;
			else
				action=our_balance;
		}

		else if(h>=.97&&our_balance>=200)
		{
			double n=Math.random();
			if(n<.4)
				action=0;
			else if(n<.65)
				action=(int)(opponent_rise*1.5);
			else if(n<.9)
				action=(int)(opponent_rise*1.2);
			else
				action=our_balance;
		}
		else if(h>=.97&&our_balance<200)
		{
			double n=Math.random();
			if(n<0.5||our_balance<opponent_rise)
				action=0;
			else action=our_balance;

		}
		else if(h>=.9&&our_balance>=400)
		{
			double n=Math.random();
			if(n<.5)
				action=0;
			else if(n<.75)
				action=(int)(opponent_rise*1.5);
			else
				action=opponent_rise*2;

		}
		else if(h>=.9&&our_balance>=200)
		{
			double n=Math.random();
			if(n<.5)
				action=0;
			else if(n<.75)
				action=(int)(opponent_rise*1.2);
			else
				action=(int)(opponent_rise*1.5);

		}
		else if(h>=.9&&our_balance<200)
		{
			double n=Math.random();
			if(n<0.75)
				action=0;
			else action=-10;
		}


		else if(h>=.8&&our_balance>=400)
		{
			double n=Math.random();
			if(n<.7)
				action=0;
			else
				action=opponent_rise*2;

		}
		else if(h>=.8&&our_balance>=200)
		{
			double n=Math.random();
			if(n<.7)
				action=0;
			else
				action=(int)(opponent_rise*1.5);
		}
		else if(h>=.8&&our_balance<200)
		{
			double n=Math.random();
			if(n<.25)
				action=0;
			else
				action=-10;
		}
		else if(h>=.7&&our_balance>=300)
		{
			if(our_strength>=opponent_strength)
		{
			double n=Math.random();
			if(n<.75)
				action=0;
			else
				action=(int)(opponent_rise*1.25);

		}
			else
			{
				double n=Math.random();
				if(n<.5)
					action=0;
				else
					action=-10;
			}
		}
		else if(h>=.7&&our_balance<300)
		{
			if(our_strength>=opponent_strength)
			{
				double n=Math.random();
				if(n<.75)
					action=0;
				else
					action=-10;

			}
				else
				{
					double n=Math.random();
					if(n<.1)
						action=0;
					else
						action=-10;
				}
		}

		else if(h>=.5&&our_balance>=400)
		{
			if(our_strength>=opponent_strength)
					action=0;
				else
				{
					double n=Math.random();
					if(n<.35)
						action=0;
					else
						action=-10;
				}
		}
		else if(h>=.5&&our_balance<400)
		{
			if(our_strength>=opponent_strength)
			{
				double n=Math.random();
				if(n<.6)
					action=0;
				else
					action=-10;
			}
			else
			{
				double n=Math.random();
				if(n<.1)
					action=0;
				else
					action=-10;
			}
		}


		else if(h>=.25&&our_balance>=500)
		{
			if(our_strength>=opponent_strength)
			{
				double n=Math.random();
				if(n<.75)
				action=0;
				else action=-10;
			}
				else
						action=-10;
		}
		else if(h>=.25&&our_balance<500)
		{
			action=-10;
		}


		else if(our_balance>=500&&h<.25)
		{
			if(our_strength>=opponent_strength)
			{
				double n=Math.random();
				if(n<.5)
				action=0;
				else action=-10;
			}
				else
						action=-10;
		}
		else
			action=-10;
	}
	return action;

}

	 public int postflop_strategy(int opponent_rise,int opponent_balance,int our_balance,double opponent_strength,double our_strength,double handstrength)
	 {
		 double h=handstrength;
	if(opponent_balance>100)
	{
if(opponent_rise>.5*opponent_balance)

			if(opponent_rise<.1*our_balance)
				if(h>=.97)
					action=opponent_rise*2;

				else if(h>=.9)
						action=opponent_rise*2;

				else if(h>=.8)
						action=(int)((int)(opponent_rise*1.5));

				else if(h>=.7)
						action=opponent_rise*2;

				else if(Math.random()<.2)
					action=opponent_rise*2;
				else action=-10;

			else if(opponent_rise<.25*our_balance)
				if(h>=.97)
					action=opponent_rise*2;

				else if(h>=.9)
						action=(int)(opponent_rise*1.5);

				else if(h>=.8)
						action=0;

				else if(h>=.7)
					if(our_strength>=opponent_strength)
							action=0;
					else action=-10;
				else action=-10;


			else if(opponent_rise<.5*our_balance)
				if(h>=.97)
						action=opponent_rise*2;

				else if(h>=.9)
					if(our_strength>=opponent_strength)
						action=(int)(opponent_rise*1.5);
					else action=0;

				else if(h>=.8)
					 action=0;
					else action=-10;



			else if(opponent_rise<.75*our_balance)
				if(h>=.97)
						action=our_balance;

				else if(h>=.9)
						if(our_strength>=opponent_strength)
								action=0;
							else action=-10;
				else action=-10;

				else if(h>=.97)
						action=0;
					else action=-10;


	////////////////////////////////////////////////////////////////////////

else	if(opponent_rise>.25*opponent_balance)

	if(opponent_rise<.1*our_balance)
		if(h>=.97)
			action=opponent_rise*4;

		else if(h>=.9)
				action=opponent_rise*3;

		else if(h>=.8)
				action=(int)(opponent_rise*1.5);

		else if(h>=.7)
				action=opponent_rise*4;

		else if(Math.random()<.2)
			action=opponent_rise*2;
		else action=-10;

	else if(opponent_rise<.25*our_balance)
		if(h>=.97)
			action=opponent_rise*3;

		else if(h>=.9)
				action=(int)(opponent_rise*1.5);

		else if(h>=.8)
				action=0;

		else if(h>=.7)
			if(our_strength>=opponent_strength)
					action=0;
			else action=-10;
		else action=-10;


	else if(opponent_rise<.5*our_balance)
		if(h>=.97)
				action=0;

		else if(h>=.9)
			if(our_strength>=opponent_strength)
				action=(int)(opponent_rise*1.25);
			else action=0;

		else if(h>=.8)
			if(Math.random()<.4)
			 action=0;
			else action=-10;
		else action=-10;



	else if(opponent_rise<.75*our_balance)
		if(h>=.97)
				action=our_balance;

		else if(h>=.9)
				if(our_strength>=opponent_strength)
						action=0;
					else action=-10;
		else action=-10;

		else if(h>=.97)
				action=0;
			else action=-10;

//////////////////\\\\\\\\\\\\\\\\\\\\\\\\\


else	if(opponent_rise>.15*opponent_balance)

	if(opponent_rise<.1*our_balance)
		if(h>=.97)
			action=opponent_rise*4;

		else if(h>=.9)
				action=opponent_rise*3;

		else if(h>=.8)
				action=(int)(opponent_rise*1.5);

		else if(h>=.7)
			if(our_strength>=opponent_strength)
				action=opponent_rise*3;
			else action=0;


		else if(Math.random()<.2)
			action=opponent_rise*5;

		else action=-10;

	else if(opponent_rise<.25*our_balance)
		if(h>=.97)
			action=opponent_rise*2;

		else if(h>=.9)
				action=(int)(opponent_rise*1.5);

		else if(h>=.8)
				action=0;

		else if(h>=.7)
			if(our_strength>=opponent_strength)
					action=0;
			else action=-10;
		else action=-10;


	else if(opponent_rise<.5*our_balance)
		if(h>=.97)
				action=0;

		else if(h>=.9)
			if(our_strength>=opponent_strength)
				action=(int)(opponent_rise*1.25);
			else action=0;

		else if(h>=.8)
			if(Math.random()<.5)
			 action=0;
			else action=-10;
		else action=-10;



	else if(opponent_rise<.75*our_balance)
		if(h>=.97)
				action=our_balance;

		else if(h>=.9)
						action=0;
		else action=-10;


		else if(h>=.97)
				action=0;

			else action=-10;


/////\\\\\/\//\/\\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\


else
	if(opponent_rise<.1*our_balance)
		if(h>=.97)
		{
			double n=Math.random();
			if(n<.4)
				action=0;
			else if(n<.65)
				action=opponent_rise*4;
			else if(n<.9)
				action=opponent_rise*2;
			else
				action=our_balance;
		}



		else if(h>=.9)
		{
			double n=Math.random();
			if(n<.5)
				action=0;
			else if(n<.75)
				action=(int)(opponent_rise*1.5);
			else
				action=opponent_rise*2;

		}

		else if(h>=.8)
		{
			double n=Math.random();
			if(n<.7)
				action=0;
			else
				action=opponent_rise*2;

		}
		else if(h>=.7)
		{
			if(our_strength>=opponent_strength)
		{
			double n=Math.random();
			if(n<.75)
				action=0;
			else
				action=(int)(opponent_rise*1.5);

		}
			else
			{
				double n=Math.random();
				if(n<.5)
					action=0;
				else
					action=-10;
			}
		}

		else if(h>=.5)
		{
			if(our_strength>=opponent_strength)
					action=0;
				else
				{
					double n=Math.random();
					if(n<.35)
						action=0;
					else
						action=-10;
				}
		}


		else if(h>=.25)
		{
			if(our_strength>=opponent_strength)
			{
				double n=Math.random();
				if(n<.75)
				action=0;
				else action=-10;
			}
				else
						action=-10;
		}


		else
		{
			if(our_strength>=opponent_strength)
			{
				double n=Math.random();
				if(n<.5)
				action=0;
				else action=-10;
			}
				else
						action=-10;
		}


	else if(opponent_rise<.25*our_balance)
		if(h>=.97)
		{
			double n=Math.random();
			if(n<.5)
				action=0;
			else if(n<.7)
				action=opponent_rise*4;
			else if(n<.9)
				action=opponent_rise*2;
			else
				action=our_balance;
		}

		else if(h>=.9)
		{
			double n=Math.random();
			if(n<.6)
				action=0;
			else if(n<.8)
				action=(int)(opponent_rise*1.5);
			else
				action=opponent_rise*2;

		}

		else if(h>=.8)
		{
			double n=Math.random();
			if(n<.75)
				action=0;
			else
				action=(int)(opponent_rise*1.75);

		}
		else if(h>=.7)
		{
			if(our_strength>=opponent_strength)
		{
			double n=Math.random();
			if(n<.8)
				action=0;
			else
				action=(int)(opponent_rise*1.5);

		}
			else
			{

				double n=Math.random();
				if(n<.4)
					action=0;
				else
					action=-10;
			}
		}

		else if(h>=.5)
		{
			if(our_strength>=opponent_strength)
					action=0;
				else
				{
					double n=Math.random();
					if(n<.25)
						action=0;
					else
						action=-10;
				}
		}


		else if(h>=.25)
		{
			if(our_strength>=opponent_strength)
			{
				double n=Math.random();
				if(n<.5)
				action=0;
				else action=-10;
			}
				else
						action=-10;
		}


		else
		{
			if(our_strength>=opponent_strength)
			{
				double n=Math.random();
				if(n<.2)
				action=0;
				else action=-10;
			}
				else
						action=-10;
		}



	else if(opponent_rise<.5*our_balance)
		if(h>=.97)
		{
			double n=Math.random();
			if(n<.65)
				action=0;
			else if(n<.75)
				action=opponent_rise*2;
			else if(n<.9)
				action=(int)(opponent_rise*1.5);
			else
				action=our_balance;
		}

		else if(h>=.9)
		{
			double n=Math.random();
			if(n<.7)
				action=0;
			else if(n<.85)
				action=(int)(opponent_rise*1.5);
			else
				action=opponent_rise*2;

		}

		else if(h>=.8)
		{
			double n=Math.random();
			if(n<.8)
				action=0;
			else
				action=(int)(opponent_rise*1.5);

		}
		else if(h>=.7)
		{
			if(our_strength>=opponent_strength)
		{
			double n=Math.random();
			if(n<.85)
				action=0;
			else
				action=(int)(opponent_rise*1.25);

		}
			else
			{

				double n=Math.random();
				if(n<.25)
					action=0;
				else
					action=-10;
			}
		}

		else if(h>=.5)
		{
			if(our_strength>=opponent_strength)
					action=0;
				else
				{
					double n=Math.random();
					if(n<.15)
						action=0;
					else
						action=-10;
				}
		}


		else if(h>=.25)
		{
			if(our_strength>=opponent_strength)
			{
				double n=Math.random();
				if(n<.35)
				action=0;
				else action=-10;
			}
				else
						action=-10;
		}


		else
		{
			if(our_strength>=opponent_strength)
			{
				double n=Math.random();
				if(n<.15)
				action=0;
				else action=-10;
			}
				else
						action=-10;
		}




	else if(opponent_rise<.75*our_balance)
		if(h>=.97)
		{
			double n=Math.random();
			if(n<.5)
				action=0;
			else
				action=our_balance;
		}

		else if(h>=.9)
		{
			double n=Math.random();
			if(n<.85)
				action=0;
			else
				action=our_balance;

		}

		else if(h>=.8)
		{
			if(our_strength>=opponent_strength)
		{
			double n=Math.random();
			if(n<.5)
				action=0;
			else
				action=-10;
		}
			else
				action=-10;

		}

		else
		{
			if(our_strength>=opponent_strength)
			{
				double n=Math.random();
				if(n<.15)
				action=0;
				else action=-10;
			}
				else
						action=-10;
		}


	else if(h>=.97)
			action=0;

		else action=-10;

	}//////
	else
	{
		if(h>=.97&&our_balance>=400)
		{
			double n=Math.random();
			if(n<.4)
				action=0;
			else if(n<.65)
				action=opponent_rise*4;
			else if(n<.9)
				action=opponent_rise*2;
			else
				action=our_balance;
		}

		else if(h>=.97&&our_balance>=200)
		{
			double n=Math.random();
			if(n<.4)
				action=0;
			else if(n<.65)
				action=(int)(opponent_rise*1.5);
			else if(n<.9)
				action=(int)(opponent_rise*1.2);
			else
				action=our_balance;
		}
		else if(h>=.97&&our_balance<200)
		{
			double n=Math.random();
			if(n<0.5||our_balance<opponent_rise)
				action=0;
			else action=our_balance;

		}
		else if(h>=.9&&our_balance>=400)
		{
			double n=Math.random();
			if(n<.5)
				action=0;
			else if(n<.75)
				action=(int)(opponent_rise*1.5);
			else
				action=opponent_rise*2;

		}
		else if(h>=.9&&our_balance>=200)
		{
			double n=Math.random();
			if(n<.5)
				action=0;
			else if(n<.75)
				action=(int)(opponent_rise*1.2);
			else
				action=(int)(opponent_rise*1.5);

		}
		else if(h>=.9&&our_balance<200)
		{
			double n=Math.random();
			if(n<0.75)
				action=0;
			else action=-10;
		}


		else if(h>=.8&&our_balance>=400)
		{
			double n=Math.random();
			if(n<.7)
				action=0;
			else
				action=opponent_rise*2;

		}
		else if(h>=.8&&our_balance>=200)
		{
			double n=Math.random();
			if(n<.7)
				action=0;
			else
				action=(int)(opponent_rise*1.5);
		}
		else if(h>=.8&&our_balance<200)
		{
			double n=Math.random();
			if(n<.25)
				action=0;
			else
				action=-10;
		}
		else if(h>=.7&&our_balance>=300)
		{
			if(our_strength>=opponent_strength)
		{
			double n=Math.random();
			if(n<.75)
				action=0;
			else
				action=(int)(opponent_rise*1.25);

		}
			else
			{
				double n=Math.random();
				if(n<.5)
					action=0;
				else
					action=-10;
			}
		}
		else if(h>=.7&&our_balance<300)
		{
			if(our_strength>=opponent_strength)
			{
				double n=Math.random();
				if(n<.75)
					action=0;
				else
					action=-10;

			}
				else
				{
					double n=Math.random();
					if(n<.1)
						action=0;
					else
						action=-10;
				}
		}

		else if(h>=.5&&our_balance>=400)
		{
			if(our_strength>=opponent_strength)
					action=0;
				else
				{
					double n=Math.random();
					if(n<.35)
						action=0;
					else
						action=-10;
				}
		}
		else if(h>=.5&&our_balance<400)
		{
			if(our_strength>=opponent_strength)
			{
				double n=Math.random();
				if(n<.6)
					action=0;
				else
					action=-10;
			}
			else
			{
				double n=Math.random();
				if(n<.1)
					action=0;
				else
					action=-10;
			}
		}


		else if(h>=.25&&our_balance>=500)
		{
			if(our_strength>=opponent_strength)
			{
				double n=Math.random();
				if(n<.75)
				action=0;
				else action=-10;
			}
				else
						action=-10;
		}
		else if(h>=.25&&our_balance<500)
		{
			action=-10;
		}


		else if(our_balance>=500&&h<.25)
		{
			if(our_strength>=opponent_strength)
			{
				double n=Math.random();
				if(n<.5)
				action=0;
				else action=-10;
			}
				else
						action=-10;
		}
		else
			action=-10;
	}
	return action;

}

	 public int preflop_strategy(int opponent_rise,int opponent_balance,int our_balance,double opponent_strength,double our_strength)
	 {

	        if(opponent_balance>100)
		{
	        if(opponent_rise>.5*opponent_balance)
	       {
				if(opponent_rise<=.1*our_balance)
	                        {
									if(our_strength>=9)
										action=opponent_rise*2;

	                                else if(our_strength==8)
	                                        {
	                                           if(our_strength>=opponent_strength)
	                                        {
	                                            action=0;
	                                        }
	                                           else
	                                               action=-10;
	                                        }
	                                else if(our_strength==7)
	                                {
	                                     if(our_strength>=opponent_strength)
	                                        {
	                                  			  if(Math.random()<.15)
													action=0;
	                                  			  else
	                                  			      action=-10;
	                                        }
	                                     else action=-10;

	                                }
	                             	   else
	                              	      action=-10;
	                      			  }



				else if(opponent_rise<.25*our_balance)
	                        {
					if(our_strength>=9)
						action=opponent_rise*2;

					else if(our_strength==8)
	                                {
	                                    if(our_strength>=opponent_strength)
	                                        action=0;
	                                    else
	                                        action=-10;
	                                }
	                                else if(our_strength==7)
	                                {
	                                    if(our_strength>=opponent_strength)
	                                    {
	                                        if(Math.random()<0.25)
	                                            action=0;
	                                        else
	                                            action=-10;
	                                    }
	                                    else
	                                        action=-10;
	                                }
	                                else
	                                    action=-10;
	                        }

				else if(opponent_rise<.5*our_balance)
	                        {
	                            if(our_strength==10)
						action=opponent_rise*2;
	                            else if(our_strength==9)
	                                action=0;
	                            else if(our_strength==8)
	                                {
	                                    if(our_strength>=opponent_strength)
	                                    {
	                                        if(Math.random()<0.25)
	                                        action=0;
	                                        else
	                                            action=-10;
	                                    }
	                                    else
	                                        action=-10;
	                                }
	                               else
	                                action=-10;
	                        }


				else if(opponent_rise<.75*our_balance)
	                        {
								if(our_strength==10)
										action=0;
	                            else if(our_strength==9)
	                            {
	                                if(our_strength>=opponent_strength)
	                                {
	                                    if(Math.random()<0.4)
	                                    {
	                                        action=0;
	                                    }
	                                    else
	                                        action=-10;
	                                }
	                                else
	                                    action=-10;
	                            }
	                            else
	                                action=-10;

	                        }
	                        else
	                            action=-10;
	        }
	        ////////////////////////////////////////////////////////////////////////
	        else	if(opponent_rise>.25*opponent_balance)
	{
		if(opponent_rise<=.1*our_balance)
	                        {
									if(our_strength>=9)
											action=opponent_rise*2;

	                                else if(our_strength==8)
	                                        {
	                                           if(our_strength>=opponent_strength)
	                                        {
	                                            action=0;
	                                        }
	                                           else
	                                               action=-10;
	                                        }
	                                else if(our_strength==7)
	                                {
	                                    if(our_strength>=opponent_strength)
	                                    {
	                                    if(Math.random()<.2)
	                                    		action=0;
	                                    else
	                                        action=-10;
	                                    }
	                                    else action=-10;
	                                }
	                                else
	                                    action=-10;
	                        }



				else if(opponent_rise<.25*our_balance)
	                        {
									if(our_strength>=9)
											action=opponent_rise*2;

									else if(our_strength==8)
	                                {
	                                    if(our_strength>=opponent_strength)
	                                        action=0;
	                                    else
	                                        action=-10;
	                                }
	                                else if(our_strength==7)
	                                {
	                                    if(our_strength>=opponent_strength)
	                                    {
	                                        if(Math.random()<0.15)
	                                            action=0;
	                                        else
	                                            action=-10;
	                                    }
	                                    else
	                                        action=-10;
	                                }
	                                else
	                                    action=-10;
	                        }

				else if(opponent_rise<.5*our_balance)
	                        {
	                            if(our_strength==10)
						action=0;
	                            else if(our_strength==9)
	                                action=0;
	                            else if(our_strength==8)
	                                {
	                                    if(our_strength>=opponent_strength)
	                                    {
	                                        if(Math.random()<0.25)
	                                        action=0;
	                                        else
	                                            action=-10;
	                                    }
	                                    else
	                                        action=-10;
	                                }
	                               else
	                                action=-10;
	                        }


				else if(opponent_rise<.75*our_balance)
	                        {
								if(our_strength==10)
									action=0;
	                            else if(our_strength==9)
	                            {
	                                if(our_strength>=opponent_strength)
	                                {
	                                    if(Math.random()<0.3)
	                                    {
	                                        action=0;
	                                    }
	                                    else
	                                        action=-10;
	                                }
	                                else action=-10;
	                            }
	                            else
	                                action=-10;
	                        }
	                     else
	                            action=-10;

	        }
	        ////////////////////
	        /////////
	       else	if(opponent_rise>.15*opponent_balance)
	{
		if(opponent_rise<=.1*our_balance)
	                        {
									if(our_strength>=9)
	                                {
	                                    if(Math.random()<0.3)
	                                    	action=opponent_rise*2;
	                                    else
	                                        action=0;
	                                }
	                                else if(our_strength==8)
	                                        {
	                                           if(our_strength>=opponent_strength)
	                                        {
	                                            action=0;
	                                        }
	                                           else if(Math.random()<0.6)
	                                               action=0;
	                                           else
	                                               action =-10;
	                                        }
	                                else if(our_strength==7)
	                                {
	                                    if(our_strength>=opponent_strength)
	                                    {
	                                    if(Math.random()<.4)
	                                    	action=0;
	                                    else
	                                        action=-10;
	                                    }
	                                    else
	                                        action=-10;
	                                }
	                                else
	                                    action=-10;
	                        }



				else if(opponent_rise<.25*our_balance)
	                        {
					if(our_strength>=9)
	                                {
	                                    if(Math.random()<0.5)
                                    		action=opponent_rise*2;
	                                    else
	                                        action=0;
	                                }
					else if(our_strength==8)
	                                {
	                                    if(our_strength>=opponent_strength)
	                                        action=0;
	                                    else
	                                    {
	                                        if(Math.random()<0.3)
	                                        action=0;
	                                        else
	                                            action=-10;
	                                    }
	                                }
                    else if(our_strength==7)
	                                {
	                                    if(our_strength>=opponent_strength)
	                                    {
	                                        if(Math.random()<0.2)
	                                            action=0;
	                                        else
	                                            action=-10;
	                                    }
	                                    else
	                                        action=-10;
	                                }
                              	  else
	                                    action=-10;
	                        }

				else if(opponent_rise<.5*our_balance)
	                        {
	                            if(our_strength==10)
	                            	action=0;
	                            else if(our_strength==9)
	                                action=0;
	                            else if(our_strength==8)
	                                {
	                                    if(our_strength>=opponent_strength)
	                                    {
	                                        if(Math.random()<0.1)
	                                        action=0;
	                                        else
	                                            action=-10;
	                                    }
	                                    else
	                                        action=-10;
	                                }
	                               else
	                                action=-10;
	                        }


				else if(opponent_rise<.75*our_balance)
	                        {
					   if(our_strength==10)
	                                   {
	                                       if(Math.random()<0.75)
	                                      action=0;
	                                       else
	                                           action=-10;
	                                   }

	                            else if(our_strength==9)
	                            {
	                                if(our_strength>=opponent_strength)
	                                {
	                                    if(Math.random()<0.4)
	                                    {
	                                        action=0;
	                                    }
	                                    else
	                                        action=-10;
	                                }
	                                else
	                                    action=-10;
	                            }
	                            else
	                                action=-10;

	                        }
	                    else
	                            action=-10;

	        }
	        /////////////////
	        ////////////////
	  else
	       {
	        if(opponent_rise<=.1*our_balance)
	                        {
	        	if(our_strength==10)
                {
	        		if(Math.random()<0.7)
	        			action=opponent_rise*2;
                    else
                        action=0;
                }
                else if(our_strength>=9)
	                                {
						if(Math.random()<0.4)
						action=opponent_rise*2;
	                                    else
	                                        action=0;
	                                }
	                                else if(our_strength==8)
	                                        {
	                                           if(our_strength>=opponent_strength)
	                                        {
	                                            action=0;
	                                        }
	                                           else if(Math.random()<0.7)
	                                               action=0;
	                                           else
	                                               action =-10;
	                                        }
	                                else if(our_strength==7)
	                                {
	                                    if(our_strength>=opponent_strength)
	                                    {
	                                    if(Math.random()<.4)
						action=0;
	                                    else
	                                        action=-10;
	                                    }
	                                    else
	                                        action=-10;
	                                }
	                                else
	                                    action=-10;
	                        }



				else if(opponent_rise<.25*our_balance)
	                        {
					if(our_strength>=9)
	                                {
	                                    if(Math.random()<0.3)
						action=opponent_rise*2;
	                                    else
	                                        action=0;
	                                }
					else if(our_strength==8)
	                                {
	                                    if(our_strength>=opponent_strength)
	                                        action=0;
	                                    else
	                                    {
	                                        if(Math.random()<0.4)
	                                        action=0;
	                                        else
	                                            action=-10;
	                                    }
	                                }
	                                else if(our_strength==7)
	                                {
	                                    if(our_strength>=opponent_strength)
	                                    {
	                                        if(Math.random()<0.4)
	                                            action=0;
	                                        else
	                                            action=-10;
	                                    }
	                                    else
	                                        action=-10;
	                                }
	                                else
	                                    action=-10;
	                        }

				else if(opponent_rise<.5*our_balance)
	                        {
	                            if(our_strength==10)
						action=0;
	                            else if(our_strength==9)
	                            {
	                                if(our_strength>=opponent_strength)
	                                {
	                                action=0;
	                                }
	                                else
	                                {
	                                    if(Math.random()<0.35)
	                                    {
	                                        action=0;
	                                    }
	                                    else
	                                        action=-10;
	                                }
	                            }
	                            else if(our_strength==8)
	                                {
	                                    if(our_strength>=opponent_strength)
	                                    {
	                                        if(Math.random()<0.3)
	                                        action=0;
	                                        else
	                                            action=-10;
	                                    }
	                                    else
	                                        action=-10;
	                                }
	                               else
	                                action=-10;
	                        }


				else if(opponent_rise<.75*our_balance)
	                        {
					   if(our_strength==10)
	                                   {
	                                       if(Math.random()<0.6)
	                                      action=0;
	                                       else
	                                           action=-10;
	                                   }

	                                    else if(our_strength==9)
	                                {
	                                if(our_strength>=opponent_strength)
	                                {
	                                    if(Math.random()<0.5)
	                                    {
	                                        action=0;
	                                    }
	                                    else
	                                        action=-10;
	                                }
	                                else
	                                    action=-10;
	                            }
	                            else
	                                action=-10;

	                        }
	                else
	                action=-10;

	        }
	        /////////////////
	        ////////////////


	}
	        else
	        {
	            if(our_strength==10&&our_balance>=400)
			{
				double n=Math.random();
				if(n<.5)
					action=0;
				else
					action=opponent_rise*2;

			}
			else if(our_strength==10&&our_balance<400)
			{
				action=0;

			}
			else if(our_strength==9&&our_balance>=400)
			{
	                        if(our_strength>=opponent_strength)
	                        {
				action=opponent_rise*2;
	                        }
	                        else if(Math.random()<0.7)
	                        {
	                            action=0;
	                        }
	                        else
	                            action=-10;
	                }
			else if(our_strength==9&&our_balance<400)
			{
				if(our_strength>=opponent_strength)
	                        {
	                            if(Math.random()<0.5)
	                            	action=0;
	                            else action=-10;
	                        }
	                        else
	                            action=-10;
			}


			else if(our_strength==8&&our_balance>=400)
			{
				if(our_strength>=opponent_strength)
	                        {
	                         action=0;
				}
	                        else
	                        {
	                            if(Math.random()<0.5)
	                            action=-10;
	                            else
	                                action=opponent_rise*2;
	                        }
	                }
			else if(our_strength==8&&our_balance<400)
			{
				if(our_strength>=opponent_strength)
	                        {
	                            action=0;
	                        }
	                        else
	                            action=-10;
	                }
			else if(our_strength==7&&our_balance>=400)
			{
	                    double n=Math.random();
	                    if(n<0.2)
				action=opponent_rise*2;
	                    else if(n<0.4)
	                        action=0;
	                    else
	                        action=-10;
			}
			else if(our_strength==7&&our_balance<400)
			{
				if(our_strength>=opponent_strength)
			{
				double n=Math.random();
				if(n<.2)
					action=0;
				else if(n<.4)
	                            action=opponent_rise*2;
	                        else
	                            action=-10;


			}
				else
				{
					if(Math.random()<0.2)
	                                    action=0;
	                                else
	                                    action=-10;
				}
			}
	                else
	                    action=-10;

	        }
	        return action;
	 }

	 public int checkornot(int our_balance,double handstrength)
	 {
		 double h=handstrength;
		 if(h>.97)
		 {
			double n=Math.random();
				 action=(int)(h*our_balance*n);
		 }

		 else if(h>.9)
		 {
			 double n=Math.random();
			 action=(int)(h*our_balance*n*.5);
		 }


		 else if(h>.75)
		 {
			 double n=Math.random();
			 action=(int)(h*our_balance*n*.05);
		 }

		 else{
			 double n=Math.random();
			 if(n<.2)
				 action=(int)(h*our_balance*n*.05);
			 else
				 action=0;
		 }
		 return action;
	 }
}
