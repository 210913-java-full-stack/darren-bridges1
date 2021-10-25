package repos;


import Models.User;
import Util.ConnectionManager;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepo {


    private User extractUser(ResultSet rs) {
        try {
            int id = rs.getInt("UserId");
            String username = rs.getString("user_Name");
            String hash = rs.getString("password");

            int roleId = rs.getInt("user_roleId");
            return new User(id, username, hash, roleId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public String encryptPassword(String password) {
        StringBuffer sb = new StringBuffer();
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");//outputs a value that is 256 bits long//
            md.update(password.getBytes());
            byte[] byteData = md.digest();
            for (byte b : byteData) {
                sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));//sets result to the unsigned value.
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public String getToken(User user) {
        String query = "SELECT auth_token FROM authentications WHERE user_id = ?";
        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, user.getId());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getString("auth_token");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String generateToken(User user) {
        String query = "INSERT INTO authentications (userId, auth_token)"
                + " VALUES (?,md5(? || now())) RETURNING auth_token";
        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, user.getId());
            ps.setString(2, user.getUsername());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getString("auth_token");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

    public User getUserFromToken(String auth_token) {
        String query = "SELECT Users.* FROM Users LEFT JOIN authentications"
                + " ON Users.UserId = authentications.UserId WHERE"
                + " authentications.auth_token = ?";
        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, auth_token);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return extractUser(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User getUserFromId(int userId) {
        String query = "SELECT * FROM Users WHERE UserId = ?";
        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return extractUser(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}




