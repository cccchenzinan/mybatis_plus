package com.czn.mybatis_plus;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.czn.mybatis_plus.entity.User;
import com.czn.mybatis_plus.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MybatisPlusApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testSelectList() {
        System.out.println(("----- selectAll method test ------"));
        //UserMapper 中的 selectList() 方法的参数为 MP 内置的条件封装器 Wrapper
        //所以不填写就是无任何条件
        List<User> users = userMapper.selectList(null);
        users.forEach(System.out::println);
    }

    @Test
    public void testInsert(){

        User user = new User();
        user.setName("lyylyylyy");
        user.setAge(48);
        user.setEmail("1232322@qq.com");

        int result = userMapper.insert(user);
        System.out.println(result); //影响的行数
        System.out.println(user); //id自动回填
    }

    @Test
    public void testUpdateById(){

        User user = new User();
        user.setId(1L);
        user.setAge(18);

        int result = userMapper.updateById(user);
        System.out.println(result);

    }

    /**
     * 测试 乐观锁插件
     */
    @Test
    public void testOptimisticLocker() {

        //查询
        User user = userMapper.selectById(1148897166719299585L);
        //修改数据
        user.setName("jyf");
        user.setEmail("jyfjyfjyf@qq.com");
        //执行更新
        userMapper.updateById(user);
//        userMapper.update(user,xxx);
    }

    //简单查询
    @Test
    public void testSelectById(){

        User user = userMapper.selectById(3L);
        System.out.println(user);
    }

    //通过多个id批量查询
    //完成了动态sql的foreach的功能
    @Test
    public void testSelectBatchIds(){

        List<User> users = userMapper.selectBatchIds(Arrays.asList(1, 2, 3));
        users.forEach(System.out::println);
    }



    //通过map封装查询条件
    //注意：map中的key对应数据库中的列名。如：数据库user_id，实体类是userId，这时map的key需要填写user_id
    @Test
    public void testSelectByMap(){

        HashMap<String, Object> map = new HashMap<>();
        map.put("name", "lyylyylyy");
        map.put("age", 48);
        List<User> users = userMapper.selectByMap(map);

        users.forEach(System.out::println);
    }

    //通过page对象获取到相关的数据
    @Test
    public void testSelectPage(){
        Page<User> page = new Page<>(1, 6);
        //控制台sql语句打印：
        //SELECT id,name,age,email,create_time,update_time FROM user LIMIT 0,6
        userMapper.selectPage(page,null);

        page.getRecords().forEach(System.out::println);
        System.out.println(page.getCurrent());
        System.out.println(page.getPages());
        System.out.println(page.getSize());
        System.out.println(page.getTotal());
        System.out.println(page.hasNext());
        System.out.println(page.hasPrevious());
    }

    //删除测试
    @Test
    public void testDeleteById(){

        int result = userMapper.deleteById(8L);
        System.out.println(result);
    }

    //批量删除
    @Test
    public void testDeleteBatchIds() {

        int result = userMapper.deleteBatchIds(Arrays.asList(8, 9, 10));
        System.out.println(result);
    }

    //条件查询删除
    @Test
    public void testDeleteByMap() {

        HashMap<String, Object> map = new HashMap<>();
        map.put("name", "Helen");
        map.put("age", 18);

        int result = userMapper.deleteByMap(map);
        System.out.println(result);
    }



    /**
     *物理删除：真实删除，将对应数据从数据库中删除，之后查询不到此条被删除数据
     *逻辑删除：假删除，将对应数据中代表是否被删除字段状态修改为“被删除状态”，之后在数据库中仍旧能看到此条数据记录
     *
     *
     * 测试 逻辑删除
     */
    @Test
    public void testLogicDelete() {

        int result = userMapper.deleteById(1148897166719299590L);
        System.out.println(result);
    }

    /**
     * 测试 逻辑删除后的查询：
     * 不包括被逻辑删除的记录
     */
    @Test
    public void testLogicDeleteSelect() {
        User user = new User();
        List<User> users = userMapper.selectList(null);
        users.forEach(System.out::println);
    }


    /**
     * 测试 性能分析插件
     */
    @Test
    public void testPerformance() {
        User user = new User();
        user.setName("jyfjyf");
        user.setEmail("yf_sdddd@sina.com");
        user.setAge(38);
        userMapper.insert(user);
    }

}
