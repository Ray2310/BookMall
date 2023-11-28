package com.bookmall.config;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
public class TokenUtils {

    // y
    public static String genToken(String userId, String username){
        // 通过uuid生成一个新的随机token
        String token = UUID.randomUUID() + ":" + userId + ":" + username;
        return token;
//        String token = JWT.create()
//                .withAudience(userId)
//                .sign(Algorithm.HMAC256(username));
//        return token;

    }

//
//    public static User getCurrentUser(){
////        try{
////            return UserHolder.getUser();
////        }catch (Exception e){
////            return null;
////        }
//    }
//    public static boolean validateLogin(){
//        try{
//            HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
//            String token = request.getHeader("token");
//            if(StringUtils.hasLength(token)){
//                JWT.decode(token).getAudience();
//                return true;
//            }else{
//                return false;
//            }
//        }catch (Exception e){
//            throw new ServiceException(Constants.CODE_401,"登录状态失效！");
//        }
//    }
//
//    public static boolean validateAuthority(){
//        try{
//            User user = getCurrentUser();
//            if(user.getRole().equals("admin")){
//                return true;
//            }
//        }catch (Exception e){
//            return false;
//        }
//        throw new ServiceException(Constants.CODE_403,"无权限！");
//    }

}
