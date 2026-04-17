import java.util.*;

public class TicTacToeAi {

    static final char HUMAN='X';
    static final char AI='O';

    static void print(char[][] b){
        System.out.println();
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                System.out.print((b[i][j]==' '?"-":b[i][j])+(j<2?" | ":""));
            }
            System.out.println();
        }
        System.out.println();
    }

    static String winner(char[][] b){
        for(int i=0;i<3;i++){
            if(b[i][0]!=' '&&b[i][0]==b[i][1]&&b[i][1]==b[i][2])
                return b[i][0]==AI?"AI":"HUMAN";
            if(b[0][i]!=' '&&b[0][i]==b[1][i]&&b[1][i]==b[2][i])
                return b[0][i]==AI?"AI":"HUMAN";
        }
        if(b[0][0]!=' '&&b[0][0]==b[1][1]&&b[1][1]==b[2][2])
            return b[0][0]==AI?"AI":"HUMAN";
        if(b[0][2]!=' '&&b[0][2]==b[1][1]&&b[1][1]==b[2][0])
            return b[0][2]==AI?"AI":"HUMAN";
        return null;
    }

    static boolean movesLeft(char[][] b){
        for(char[] row:b)
            for(char c:row)
                if(c==' ') return true;
        return false;
    }

    static int minimax(char[][] b,boolean isMax){
        String w=winner(b);
        if("AI".equals(w)) return 1;
        if("HUMAN".equals(w)) return -1;
        if(!movesLeft(b)) return 0;

        int best=isMax?Integer.MIN_VALUE:Integer.MAX_VALUE;

        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if(b[i][j]==' '){
                    b[i][j]=isMax?AI:HUMAN;
                    int val=minimax(b,!isMax);
                    b[i][j]=' ';
                    best=isMax?Math.max(best,val):Math.min(best,val);
                }
            }
        }
        return best;
    }

    static int[] bestMove(char[][] b){
        int bestVal=Integer.MIN_VALUE;
        int[] move={-1,-1};

        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if(b[i][j]==' '){
                    b[i][j]=AI;
                    int val=minimax(b,false);
                    b[i][j]=' ';
                    if(val>bestVal){
                        bestVal=val;
                        move[0]=i;
                        move[1]=j;
                    }
                }
            }
        }
        return move;
    }

    public static void main(String[] args){
        Scanner sc=new Scanner(System.in);
        char[][] board={{' ',' ',' '},{' ',' ',' '},{' ',' ',' '}};

        System.out.println("Tic Tac Toe ");

        while(true){
            print(board);

            int r,c;
            while(true){
                System.out.print("Row(0-2): ");
                r=sc.nextInt();
                System.out.print("Col(0-2): ");
                c=sc.nextInt();

                if(r>=0&&r<3&&c>=0&&c<3&&board[r][c]==' ') break;
                System.out.println("Invalid move, please 1try again.");
            }

            board[r][c]=HUMAN;

            if("HUMAN".equals(winner(board))){
                print(board);
                System.out.println("You win!!");
                break;
            }
            if(!movesLeft(board)){
                print(board);
                System.out.println("Draw!!");
                break;
            }

            int[] ai=bestMove(board);
            board[ai[0]][ai[1]]=AI;
            System.out.println("AI played: "+ai[0]+","+ai[1]);

            if("AI".equals(winner(board))){
                print(board);
                System.out.println("AI wins!!");
                break;
            }
            if(!movesLeft(board)){
                print(board);
                System.out.println("Draw!!");
                break;
            }
        }
        sc.close();
    }
}