package com.dws.challenge.repository;

import com.dws.challenge.domain.Account;
import com.dws.challenge.domain.TransferAmount;
import com.dws.challenge.exception.DuplicateAccountIdException;
import com.dws.challenge.exception.LessAmountException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.dws.challenge.service.EmailNotificationService;
import com.dws.challenge.utils.ValidateAmount;


@Repository
public class AccountsRepositoryInMemory implements AccountsRepository {

    private final Map<String, Account> accounts = new ConcurrentHashMap<>();
    
    @Autowired
    EmailNotificationService notificationService;
    
    @Autowired
    ValidateAmount validateAmount;

    @Override
    public void createAccount(Account account) throws DuplicateAccountIdException {
        Account previousAccount = accounts.putIfAbsent(account.getAccountId(), account);
        if (previousAccount != null) {
            throw new DuplicateAccountIdException(
                    "Account id " + account.getAccountId() + " already exists!");
        }
    }

    @Override
    public Account getAccount(String accountId) {
        return accounts.get(accountId);
    }

    @Override
    public void clearAccounts() {
        accounts.clear();
    }

	@Override
	public void transferAmount(TransferAmount transferAmount) {
		Account fromAccount=transferAmount.getAccount();	
		
		if(transferAmount.getAmountToTransfer() == null || 
				validateAmount.checkIfAmountNegative(transferAmount.getAmountToTransfer())){
			throw new LessAmountException("Please enter valid amount");
		}
		
		if(fromAccount != null) {
			
			if(fromAccount.getBalance().compareTo(transferAmount.getAmountToTransfer()) == 0 || 
					fromAccount.getBalance().compareTo(transferAmount.getAmountToTransfer()) < 0 ) {
				throw new LessAmountException("Please maintain sufficient balance in your account");
			}
			
			notificationService.notifyAboutTransfer(fromAccount,transferAmount.getAmountToTransfer()+" transferred successfullly to "+transferAmount.getToAccountId());
		}
		
		// TODO Auto-generated method stub
		
	}

}
