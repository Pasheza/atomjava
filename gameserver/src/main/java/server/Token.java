package server;

/**
 * Created by User on 23.10.2016.
 */
public class Token {
    private Long token;

    public Token(Long newtoken){
        this.token = newtoken;
    }

    @Override
    public int hashCode(){
        return Long.hashCode(this.token);
    }

    @Override
    public boolean equals(Object obj){
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Token other = (Token) obj;
        if (!this.token.equals(other.token))
            return false;
        return true;
    }
    public Long getToken(){
        return this.token;
    }
}
