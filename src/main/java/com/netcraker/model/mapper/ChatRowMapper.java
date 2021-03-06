package com.netcraker.model.mapper;

import com.netcraker.model.Chat;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ChatRowMapper implements RowMapper<Chat> {

    @Override
    public Chat mapRow(ResultSet resultSet, int i) throws SQLException {
        Chat chat = new Chat();
        chat.setFriendId(resultSet.getInt("user_id"));
        chat.setUserCurrentId(resultSet.getInt("user2_id"));
        chat.setChatId(resultSet.getInt("chat_id"));
        return chat;
    }
}
