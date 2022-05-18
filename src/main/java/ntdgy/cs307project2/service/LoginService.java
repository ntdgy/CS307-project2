package ntdgy.cs307project2.service;

import ntdgy.cs307project2.data.User;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.UUID;

public class LoginService {
    private static final HashMap<String, User> cookie = new HashMap<>();

    public static void login(String user, int level){
        String cookies = UUID.randomUUID().toString();
        cookie.put(user, new User(level, user, cookies));
    }

    public static String getCookie(String user){
        return cookie.get(user).getCookie();
    }

    public static int getLevel(String user){
        return cookie.get(user).getLevel();
    }

    public static int getLevel(HttpServletRequest request){
        var cookies = request.getCookies();
        String username = "";
        for(Cookie c: cookies){
            if(c.getName().equals("username")){
                username = c.getValue();
            }
        }
        return getLevel(username);
    }

    public static boolean check(String user, String uuid){
        if(!cookie.containsKey(user)) return false;
        return cookie.get(user).getCookie().equals(uuid);
    }

    public static boolean check(HttpServletRequest request){
        var cookies = request.getCookies();
        String username = "";
        String uuid = "";
        if(cookies == null) return false;
        for(Cookie c: cookies){
            if(c.getName().equals("username")){
                username = c.getValue();
            }else if(c.getName().equals("uuid")){
                uuid = c.getValue();
            }
        }
        return check(username, uuid);
    }
}
