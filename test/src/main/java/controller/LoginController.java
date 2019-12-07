//201802104058潘英祖
package controller;

import Service.UserService;
import com.alibaba.fastjson.JSONObject;
import domain.User;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;

@WebServlet("/login.ctl")
public class LoginController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        JSONObject message = new JSONObject();
        try {
            User loggedUser = UserService.getInstance().login(username,password);
            if (loggedUser != null){
                message.put("message","登陆成功");
                HttpSession session = request.getSession();
                session.setMaxInactiveInterval(10 * 60);
                session.setAttribute("currentUser",loggedUser);
                response.getWriter().println(message);
                return;
            }
        }catch (SQLException e){
        message.put("message", "数据库操作异常");
        e.printStackTrace();
        }catch(Exception e){
        message.put("message", "网络异常");
        e.printStackTrace();
        }
    }
}
