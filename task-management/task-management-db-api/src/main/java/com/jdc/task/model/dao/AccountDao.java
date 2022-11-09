package com.jdc.task.model.dao;

import java.util.List;

import com.jdc.task.model.dto.Account;
import com.jdc.task.model.dto.Account.Role;
import com.jdc.task.model.dto.form.AccountForm;

/**
 * Data Access Object for Account Table
 * 
 * @author minlwin
 *
 */
public interface AccountDao {
	
	/**
	 * Insert Account Data
	 * 
	 * @param form
	 * @return Generated Primary Key Value
	 */
	int create(AccountForm form);
	
	/**
	 * Update Account Data
	 * 
	 * @param id Primary Key
	 * @param form Account Data
	 */
	void update(int id, AccountForm form);
	
	/**
	 * Find Account by primary key
	 * 
	 * @param id Primary Key
	 * @return Account Informations
	 */
	Account findById(int id);
	
	/**
	 * Find Account by email for Login User
	 * 
	 * @param email
	 * 
	 * @return Account Informations
	 */
	Account findByEmail(String email);

	/**
	 * Search Account Data
	 * 
	 * @param role Role of Account
	 * @param name Account Name with like search
	 * 
	 * @return
	 */
	List<Account> search(Role role, String name);
	
	/**
	 * Change Password Operation
	 * 
	 * @param memberId Account ID
	 * @param oldPass Old Password
	 * @param newPass New Password
	 */
	void changePass(int memberId, String oldPass, String newPass);
}
