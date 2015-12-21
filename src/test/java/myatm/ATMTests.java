package myatm;


import org.junit.Test;

import org.mockito.InOrder;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ATMTests {
    @Test(expected = IllegalArgumentException.class)
    public void testSetNegativeMoneyInATMThrownIllegalArgumentException()  {
        new ATM(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetNullIllegalArgumentException()  {
        new ATM(-1);
    }

    @Test
    public void testGetMoneyInATMExpectedEquals() {
        ATM atm = new ATM(101);
        double expectedResult = 101;
        assertEquals(atm.getMoneyInATM(), expectedResult, 0.0);
    }

    @Test (expected = NullPointerException.class)
    public void testValidateCardCardIsNullThrownIllegalArgumentException() {
        ATM atm = new ATM(111);
        atm.validateCard(null, 111);
    }

    @Test
    public void testValidateCardBlockedCard() {
        ATM atm = new ATM(121);
        Card card = mock(Card.class);
        when(card.isBlocked()).thenReturn(true);
        boolean result = atm.validateCard(card,5);
        assertFalse(result);
    }
    
    @Test
    public void testValidateCardCardAccepted() {
        ATM atm = new ATM(121);
        Card card = mock(Card.class);
        int pinCode = 0000;
        when(card.isBlocked()).thenReturn(false);
        when(card.checkPin(pinCode)).thenReturn(true);
        boolean result = atm.validateCard(card,pinCode);
        assertTrue(result);
    }

    @Test (expected = NoCardInserted.class)
    public void testCheckBalanceCardIsNullThrownNoCardInsertion() throws NoCardInserted{
        ATM atm = new ATM(100);
        Card card = mock(Card.class);
        when(card.isBlocked()).thenReturn(false);
        atm.checkBalance();
        /*i don't know*/
    }

    @Test
    public void testCheckBalanceExcpectedAllGood() throws NoCardInserted{
        ATM atm = new ATM(115);

        Card card = mock(Card.class);
        int pinCode = 1111;
        Account account = mock(Account.class);
        double actualValue = 1000;
        when(account.getBalance()).thenReturn(actualValue);
        when(card.getAccount()).thenReturn(account);
        when(card.isBlocked()).thenReturn(false);
        when(card.checkPin(pinCode)).thenReturn(true);
        atm.validateCard(card,pinCode);
        double expectedResult = 1000;
        assertEquals(atm.checkBalance(),expectedResult,0.0);

    }

    @Test (expected = NoCardInserted.class)
    public void testGetCashCardIsNullThrownNoCardInserted() throws NoCardInserted, NotEnoughMoneyInATMExeption, NotEnoughMoneyOnCardException{
        ATM atm = new ATM(1000);
        double amount =123;
        assertNull(atm.getCash(amount));

    }

    @Test (expected = NotEnoughMoneyOnCardException.class)
    public void testGetCashThronwNotEnoughMoneyInAccount() throws NoCardInserted, NotEnoughMoneyInATMExeption, NotEnoughMoneyOnCardException{

        double amount =1001;
        ATM atm = new ATM(5);

        Card card = mock(Card.class);
        int pinCode = 1111;
        Account account = mock(Account.class);
        double actualValue = 1000;
        when(account.getBalance()).thenReturn(actualValue);
        when(card.getAccount()).thenReturn(account);
        when(card.isBlocked()).thenReturn(false);
        when(card.checkPin(pinCode)).thenReturn(true);
        atm.validateCard(card,pinCode);
        atm.getCash(amount);
    }

    @Test (expected = NotEnoughMoneyInATMExeption.class)
    public void testGetCashThronwNotEnoughMoneyInATM() throws NoCardInserted, NotEnoughMoneyInATMExeption, NotEnoughMoneyOnCardException{

        double amount =1001;
        ATM atm = new ATM(5);

        Card card = mock(Card.class);
        int pinCode = 1111;
        Account account = mock(Account.class);
        double actualValue = 1005;
        when(account.getBalance()).thenReturn(actualValue);
        when(card.getAccount()).thenReturn(account);
        when(card.isBlocked()).thenReturn(false);
        when(card.checkPin(pinCode)).thenReturn(true);
        atm.validateCard(card,pinCode);
        atm.getCash(amount);
    }

    @Test
    public void testGetCashBalanceBalanceCheckedAtLeastOnce() throws NoCardInserted, NotEnoughMoneyInATMExeption, NotEnoughMoneyOnCardException{

        double amount =1000;
        ATM atm = new ATM(10000);
        Card card = mock(Card.class);
        int pinCode = 1111;
        Account account = mock(Account.class);
        double actualValue = 10000;
        when(account.getBalance()).thenReturn(actualValue);
        when(card.getAccount()).thenReturn(account);
        when(card.isBlocked()).thenReturn(false);
        when(card.checkPin(pinCode)).thenReturn(true);
        atm.validateCard(card,pinCode);
        atm.getCash(amount);
        verify(account, atLeastOnce()).getBalance();
    }

    @Test
    public void testGetCashBalanceBalanceOrderGetBalanceBeforeWithDraw() throws NoCardInserted, NotEnoughMoneyInATMExeption, NotEnoughMoneyOnCardException{

        double amount =1000;
        ATM atm = new ATM(10000);
        Card card = mock(Card.class);
        int pinCode = 1111;
        Account account = mock(Account.class);
        double actualValue = 10000;
        when(account.getBalance()).thenReturn(actualValue);
        when(card.getAccount()).thenReturn(account);
        when(card.isBlocked()).thenReturn(false);
        when(card.checkPin(pinCode)).thenReturn(true);
        atm.validateCard(card, pinCode);
        atm.getCash(amount);
        InOrder order = inOrder(account);
        order.verify(account).getBalance();
        order.verify(account).withdraw(amount);

    }

    @Test
    public void testGetCashAllOk() throws NoCardInserted, NotEnoughMoneyInATMExeption, NotEnoughMoneyOnCardException{

        double amount =1000;
        ATM atm = new ATM(1000);

        Card card = mock(Card.class);
        int pinCode = 1111;
        Account account = mock(Account.class);
        double actualValue = 1000;
        when(account.getBalance()).thenReturn(actualValue);
        when(card.getAccount()).thenReturn(account);
        when(card.isBlocked()).thenReturn(false);
        when(card.checkPin(pinCode)).thenReturn(true);
        atm.validateCard(card,pinCode);
        atm.getCash(amount);
    }

}