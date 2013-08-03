/**
 * @(#)UserApiController.java, Jun 16, 2013. 
 * 
 */
package com.cloudstone.emenu.ctrl.api;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cloudstone.emenu.data.User;
import com.cloudstone.emenu.util.JsonUtils;

/**
 * @author xuhongfeng
 *
 */
@Controller
public class UserApiController extends BaseApiController {
    private static final Logger LOG = LoggerFactory.getLogger(UserApiController.class);
    
    //TODO encrypt password
    @RequestMapping(value="/api/login", method=RequestMethod.POST)
    public @ResponseBody User login(@RequestParam(value="name") String name,
            @RequestParam(value="password") String password,
            HttpServletRequest req, HttpServletResponse resp) {
        LOG.info("user login : " + name);
        
        User user = userLogic.login(name, password);
        if (user == null) {
            sendError(resp, HttpServletResponse.SC_UNAUTHORIZED);
            return null;
        } else {
            authHelper.onAuthSuccess(req, resp, user, true);
            return user;
        }
    }

    @RequestMapping(value="/api/users", method=RequestMethod.GET)
    public @ResponseBody List<User> getAll() {
        return userLogic.getAll();
    }
    
    @RequestMapping(value="/api/users/{id:[\\d]+}", method=RequestMethod.GET)
    public @ResponseBody User get(@PathVariable(value="id") int userId,
            HttpServletRequest req, HttpServletResponse response) {
        if (getLoginUser(req).getId() != userId) {
            sendSuccess(response, HttpServletResponse.SC_CREATED);
        }
        return userLogic.getUser(userId);
    }
    
    @RequestMapping(value="/api/users/{id:[\\d]+}", method=RequestMethod.DELETE)
    public void delete(@PathVariable(value="id") int userId, HttpServletResponse response) {
        userLogic.delete(userId);
    }
    
    @RequestMapping(value="/api/users/{id:[\\d]+}", method=RequestMethod.PUT)
    public @ResponseBody User update(@PathVariable(value="id") int userId,
            @RequestBody String body, HttpServletResponse response) {
        User user = JsonUtils.fromJson(body, User.class);
        if (user.getId() != userId) {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }
        return userLogic.update(user);
    }
    
    @RequestMapping(value="/api/users", method=RequestMethod.POST)
    public @ResponseBody User add(@RequestBody String body, HttpServletResponse response) {
        User user = JsonUtils.fromJson(body, User.class);
        //password is ignored by json mapper
        user.setPassword(JsonUtils.getString(body, "password"));
        
        user = userLogic.add(user);
        sendSuccess(response, HttpServletResponse.SC_CREATED);
        return user;
    }
    
    @RequestMapping(value="/api/users/{id:[\\d]+}/password", method=RequestMethod.PUT)
    public void password(@PathVariable(value="id") int userId,
            @RequestParam(value="oldPassword") String oldPassword,
            @RequestParam(value="newPassword") String newPassword,
            HttpServletRequest req, HttpServletResponse resp) {
        User loginUser = getLoginUser(req);
        if (loginUser==null
                || loginUser.getId()!=userId
                || userLogic.login(loginUser.getName(), oldPassword) == null) {
            sendError(resp, HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        userLogic.modifyPassword(userId, newPassword);
    }
}
