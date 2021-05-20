package com.devops3.dto;

import com.devops3.model.User;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Data {

   private List<UserDTO> users;

   public Data(){

   }

    public List<UserDTO> getUsers() {
        return users;
    }

    public void addUser(UserDTO user){
       if(users == null){
           users = new ArrayList<>();
       }
       users.add(user);
    }

    public void setUsers(List<UserDTO> users) {
        this.users = users;
    }


    public static final class DataBuilder {
        private Data data;

        private DataBuilder() {
            data = new Data();
        }

        public static DataBuilder aData() {
            return new DataBuilder();
        }

        public DataBuilder withUsers(List<UserDTO> users) {
            data.setUsers(users);
            return this;
        }

        public Data build() {
            return data;
        }
    }
}
