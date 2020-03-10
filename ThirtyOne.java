import java.util.*;
public class ThirtyOne
{
    public static void main (String [] args)
    {
        Scanner key = new Scanner(System.in);
        Deck deck = new Deck();

        int playerLives=3;
        int computerLives=3;

        String computerBestSuit=" ";
        int computerBestValue=0;

        String playerBestSuit=" ";
        int playerBestValue=0;
        boolean roundOver=false;
        Card[] playerHand={deck.draw(),deck.draw(),deck.draw()};
        Card[] computerHand={deck.draw(),deck.draw(),deck.draw()};
        do{
            System.out.println("Your cards: ");
            for(int i =0;i<3;i++)
            {
                System.out.println("Card "+(i+1)+":  "+playerHand[i]);
            }
            computerBestSuit=bestSuit(computerHand, null);
            Card discard=deck.draw();
            System.out.println("\nDiscard Card:  "+discard);
            System.out.println("Would you like to (k)nock\npick up the (d)iscard\nor pick up a (n)ew card?");
            String ans=key.nextLine();
            while(!ans.equalsIgnoreCase("k")&&!ans.equalsIgnoreCase("d")&&!ans.equalsIgnoreCase("n"))
            {
                System.out.println("Please use the letters k, d, or n.\n"
                    +"Would you like to (k)nock\npick up the (d)iscard\nor pick up a (n)ew card?");
                ans=key.nextLine();
            }
            if(ans.equalsIgnoreCase("k"))
            {
                playerBestSuit=bestSuit(playerHand,null);
                playerBestValue=bestValue(playerHand,playerBestSuit);
                boolean pickUp=false;
                computerBestSuit=bestSuit(computerHand, null);
                if(computerBestSuit.equalsIgnoreCase(discard.getSuit()))
                {
                    for(int i=0; i<3; i++)
                    {
                        Card temp = computerHand[i];
                        if(!temp.getSuit().equalsIgnoreCase(computerBestSuit))
                        {
                            computerHand[i]=discard;
                            discard=temp;
                            pickUp=true;
                            break;
                        }
                    }
                    if(!pickUp)
                    {
                        Card smallest=getSmallestCard(computerHand);
                        int index=-1;
                        for(int i=0; i<3; i++)
                        {
                            if(smallest.getValue()==computerHand[i].getValue())
                            {
                                index=i;
                                break;
                            }
                        }
                        if(smallest.getValue()<discard.getValue())
                        {
                            computerHand[index]=discard;
                            discard=smallest;
                            pickUp=true;
                        }
                    }
                    if(!pickUp)
                    {
                        Card newTemp=deck.draw();
                        int currentBestValue=getSuitValue(computerHand,computerBestSuit);
                        if(newTemp.getValue() > currentBestValue)
                        {
                            discard=computerHand[0];
                            computerHand[0]=newTemp;
                            pickUp=true;
                        }
                        else if(newTemp.getSuit().equalsIgnoreCase(computerBestSuit))
                        {
                            for(int i = 0;i<3;i++)
                            {
                                if(!computerHand[i].getSuit().equalsIgnoreCase(computerBestSuit))
                                {   
                                    discard=computerHand[i];
                                    computerHand[i]=newTemp;
                                    pickUp=true;
                                }
                            }
                        }
                        else
                        {
                            discard=newTemp;
                        }
                    }
                }
                
                System.out.println("\nThe computer's final cards: ");
                for(int i =0;i<3;i++)
                {
                    System.out.println("Card "+(i+1)+":  "+computerHand[i]);
                }
                computerBestSuit=bestSuit(computerHand,null);
                computerBestValue=bestValue(computerHand,computerBestSuit);
                
                playerBestSuit=bestSuit(playerHand,null);
                playerBestValue=bestValue(playerHand,playerBestSuit);
                
                System.out.println("Your best suit is "+playerBestSuit+" and the computer's best suit is "+computerBestSuit);
                System.out.println("Your score is "+playerBestValue+" and the computer's score is "+computerBestValue);
                if(computerBestValue>playerBestValue)
                {
                    System.out.println("You lose!");
                    playerLives-=2;
                }
                else if(computerBestValue<playerBestValue)
                {
                    System.out.println("You win!");
                    computerLives-=1;
                }
                else if(computerBestValue==playerBestValue)
                {
                    System.out.println("You tie, so you both lose!");
                    computerLives-=1;
                    playerLives-=1;
                }
                roundOver=true;
            }
            else if(ans.equalsIgnoreCase("d"))
            {
                Card temp = discard;
                System.out.println("You picked up the "+temp+". Which card do you want to discard? 1, 2 or 3?");
                int ansDiscard= key.nextInt();

                while(ansDiscard!=1 && ansDiscard!=2 && ansDiscard!=3)
                {
                    System.out.println("Which card do you want to discard? 1, 2, or 3?");
                    ansDiscard= key.nextInt();
                }
                key.nextLine();
                if(ansDiscard==1)
                {
                    discard=playerHand[0];
                    playerHand[0]=temp;
                }
                else if(ansDiscard==2)
                {
                    discard=playerHand[1];
                    playerHand[1]=temp;
                }
                else if(ansDiscard==3)
                {
                    discard=playerHand[2];
                    playerHand[2]=temp;
                }
            }
            else if(ans.equalsIgnoreCase("n"))
            {
                Card temp = deck.draw();
                System.out.println("You picked up the "+temp+". Which card do you want to discard? 1, 2 or 3?"+
                    "\nOr, if you wish to discard the card you just selcted from the deck, press 4");
                int ansDiscard= key.nextInt();

                while(ansDiscard!=1 && ansDiscard!=2 && ansDiscard!=3 && ansDiscard!=4)
                {
                    System.out.println("Which card do you want to discard? 1, 2, or 3?"+
                        "\nOr, if you wish to discard the card you just selcted from the deck, press 4");
                    ansDiscard= key.nextInt();
                }
                key.nextLine();
                if(ansDiscard==1)
                {
                    discard=playerHand[0];
                    playerHand[0]=temp;
                }
                else if(ansDiscard==2)
                {
                    discard=playerHand[1];
                    playerHand[1]=temp;
                }
                else if(ansDiscard==3)
                {
                    discard=playerHand[2];
                    playerHand[2]=temp;
                }
                else if(ansDiscard==4)
                    discard=temp;
            }
        }while(!roundOver);
    }

    public static int getSuitValue(Card[] hand, String suit)
    {
        int value=0;
        for(int i = 0; i<3; i++)
        {
            if(hand[i].getSuit().equalsIgnoreCase(suit))
                value+=hand[i].getValue();
        }
        return value;
    }

    public static int bestValue(Card[] hand, String bestSuit)
    {
        int bestValue=0;
        for(Card temp: hand)
        {
            if(temp.getSuit().equals(bestSuit))
                bestValue+=temp.getValue();
        }
        return bestValue;
    }

    public static String bestSuit(Card[] hand, Card newCard)
    {
        int cD=0;
        int cH=0;
        int cC=0;
        int cS=0;

        for(int i = 0; i<3;i++)
        {
            if(hand[i].getSuit().equals("Diamonds"))
                cD+=hand[i].getValue();
            else if(hand[i].getSuit().equals("Hearts"))
                cH+=hand[i].getValue();
            else if(hand[i].getSuit().equals("Clubs"))
                cC+=hand[i].getValue();
            else if(hand[i].getSuit().equals("Spades"))
                cS+=hand[i].getValue();
        }

        String bestSuit=" ";

        if(cD>cH && cD>cC && cD>cS)
            bestSuit="Diamonds";
        else if(cH>cD && cH>cC && cH>cS)
            bestSuit="Hearts";
        else if(cC>cD && cC>cH && cC>cS)
            bestSuit="Clubs";
        else if(cS>cD && cS>cC && cS>cH)
            bestSuit="Spades";
        return bestSuit;
    }

    public static Card getSmallestCard(Card[] hand)
    {
        Card smallest=null;
        if(hand[2].getValue()<hand[1].getValue() && hand[2].getValue()<hand[0].getValue())
            smallest=hand[2];
        else if(hand[1].getValue()<hand[0].getValue() && hand[1].getValue()<hand[2].getValue())
            smallest=hand[1];  
        else if(hand[0].getValue()<hand[1].getValue() && hand[0].getValue()<hand[2].getValue())
            smallest=hand[0];    
        return smallest;
    }
}
