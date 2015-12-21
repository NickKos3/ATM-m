package myatm;

public class NoCardInserted extends Throwable {
        @Override
        public void printStackTrace() {
            super.printStackTrace();
            System.err.println("No Card Is Inserted");
        }

}
