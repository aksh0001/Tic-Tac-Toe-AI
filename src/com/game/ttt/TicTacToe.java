package com.game.ttt;

public class TicTacToe {
    // Noughts and Crosses
    private int X = 0;
    private int O = 0;

    private int miniMax(int p) {
        int End = gameOver();
        if (End != 0)
            return End;

        int best_value = (p == 1) ? 512 : 2048;
        for (int b = 1; b <= 256; b = b << 1) {
            int move = (~(X | O) & b);
            if (move != 0) {
                put(p * move);
                int s = miniMax(-p);
                best_value = p * (s & 0xfffffe00) > p
                        * (best_value & 0xfffffe00) ? ((s & 0xfffffe00) | move)
                        : best_value;
                clear(move);
            }

        }
        return best_value;
    }

    public int generateMove(int Player) {
        return bitToPos((miniMax(Player) & 511));
    }

    public boolean move(int pos, int player) {
        int p = posToBit(pos);
        if (p != 0 && (player == 1 || player == -1) && ((X | O) & p) == 0) {
            put(p * player);
            return true;
        } else
            return false;
    }

    public int isGameOver() {
        return Check(X) ? 1 : Check(O) ? -1 : ((X | O) & 511) == 511 ? 2 : 0;
    }

    public void newGame() {
        X = O = 0;
    }

    public int getBoard() {
        return (X | O);
    }

    public int getO() {
        return O;
    }

    public int getX() {
        return X;
    }

    private int posToBit(int pos) {
        return pos >= 1 && pos <= 9 ? 1 << (pos - 1) : 0;
    }

    private int bitToPos(int bitNum) {
        int result = 1;
        while ((bitNum = bitNum >> 1) > 0) result++;
        return result;
    }

    private int gameOver() {
        return Check(X) ? 2048 : Check(O) ? 512 : ((X | O) & 511) == 511 ? 1024 : 0;
    }

    private boolean Check(int P) {
        return (P & 0x007) == 7 ||
                (P & 0x038) == 0x038 ||
                (P & 0x1C0) == 0x1C0 ||
                (P & 0x049) == 0x49 ||
                (P & 0x092) == 0x92 ||
                (P & 0x124) == 0x124 ||
                (P & 0x111) == 0x111 ||
                (P & 0x054) == 0x54;
    }

    private void put(int pos) {
        X = X | pos & -((pos >> 31) + 1) & ~O;
        O = O | -pos & (pos >> 31) & ~X;
    }

    private void clear(int pos) {
        X = X & ~pos;
        O = O & ~pos;
    }

    private boolean check(int P) {
        return (P & 0x007) == 7 ||
                (P & 0x038) == 0x038 ||
                (P & 0x1C0) == 0x1C0 ||
                (P & 0x049) == 0x49 ||
                (P & 0x092) == 0x92 ||
                (P & 0x124) == 0x124 ||
                (P & 0x111) == 0x111 ||
                (P & 0x054) == 0x54;
    }


}
