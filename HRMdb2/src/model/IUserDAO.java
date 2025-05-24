package model;

import java.util.List;

import HRMnangcao.Human;

public interface IUserDAO {
	public List<Human> getAllUsers();
	public Human searchUserId(String code);
	public boolean addUser(Human user);
}
