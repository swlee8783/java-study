package assignment;

public class Assignment01_Week01 {

    public static void main(String[] args) {
        final char name1 = '이';
        final char name2 = '시';
        final char name3 = '원';

        int charint1 = -1, charint2 = -1, charint3 = -1;

        for (int i = 0; i < Character.MAX_VALUE; i++){

            switch(i){
                case name1:
                    charint1 = i;
                    break;
                case name2:
                    charint2 = i;
                    break;
                case name3:
                    charint3 = i;
                    break;
            }

            if((charint1 != -1) && (charint2 != -1) && (charint3 != -1)){
                break;
            }
        }

        System.out.printf("0x%x, 0x%x, 0x%x", charint1, charint2, charint3);
    }
}
