package com.example.biljanasapp;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;
@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    List<User> getAllUsers();

    @Insert
    void insertAll(User... users);

    @Query("SELECT * FROM user WHERE id==:position")
    User getUser(int position);

    @Query("UPDATE user SET first_name=:newfirstname,last_name=:newlastname,phone_number=:newnumber WHERE id==:id")
    void updateUser(String newfirstname,String newlastname,String newnumber, int id);
    @Query("DELETE FROM user WHERE id==:id")
    void deleteUser(int id);
}
