package myatm;

public class NotEnoughMoneyInATMExeption extends Throwable{

        @Override
        public void printStackTrace() {
            super.printStackTrace();
            System.err.println("Not enough money in ATM.");
        }


}
