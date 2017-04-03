package ru.javawebinar.topjava.model;

public class UsersEntity extends BaseEntity {
    protected Integer userId;

    public UsersEntity() {
    }

    protected UsersEntity(Integer id, int userId) {
        super(id);
        this.userId = userId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
