package code.model;

import lombok.Data;

import java.util.List;

/**
 * Created by Administrator on 2018/8/30.
 */
@Data
public class User {
    private int id;
    private String name;
    private String password;
    private String phone;
    private String status;
    private String createTime;
    private String updateTime;
    private List<String> hobby;
}
