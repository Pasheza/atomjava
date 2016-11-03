package server;

/**
 * Created by User on 23.10.2016.
 */
public class User {
    private String userName;

    public User(String newUser){
        this.userName = newUser;
    }

    @Override
    public int hashCode(){
        return this.userName.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
           return false;
        if (getClass() != obj.getClass())
            return false;
        User other = (User) obj;
        if (!this.userName.equals(other.userName))
            return false;
        return true;
    }
    @Override
    public String toString(){
        return this.userName;
    }
    public String getUserName(){
        return this.userName;
    }
}
