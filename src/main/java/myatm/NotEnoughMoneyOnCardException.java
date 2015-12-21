package myatm;

public class NotEnoughMoneyOnCardException extends
        Throwable{
        @Override
        public void printStackTrace() {
            super.printStackTrace();
            System.err.println("Not enough money in ATM.");
        }
}
