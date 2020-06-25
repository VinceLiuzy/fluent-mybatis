package cn.org.atool.fluent.mybatis.test.segment;

import cn.org.atool.fluent.mybatis.base.model.SqlOp;
import cn.org.atool.fluent.mybatis.demo.generate.mapper.UserMapper;
import cn.org.atool.fluent.mybatis.demo.generate.wrapper.UserQuery;
import cn.org.atool.fluent.mybatis.test.BaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * HavingTest
 *
 * @author darui.wu
 * @create 2020/6/21 6:50 下午
 */
public class HavingTest extends BaseTest {
    @Autowired
    private UserMapper mapper;

    @Test
    public void test_groupBy_having() throws Exception {
        UserQuery query = new UserQuery()
            .select(by -> by
                .id().get()
                .age().sum("avg"))
            .where.id().eq(24L).end()
            .groupBy.id()
            .end()
            .having
            .age().sum(SqlOp.BETWEEN, 2, 10)
            .apply("avg > ?", 10)
            .end();
        mapper.selectList(query);
        db.sqlList().wantFirstSql()
            .eq("SELECT id, SUM(age) AS avg FROM t_user WHERE id = ? " +
                "GROUP BY id HAVING SUM(age) BETWEEN ? AND ? AND avg > ?");
    }
}