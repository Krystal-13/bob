package com.zerobase.bob.repository;

import com.zerobase.bob.entity.RecipeLink;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class RecipeLinkCustomRepositoryImpl implements RecipeLinkCustomRepository {

    private final JdbcTemplate jdbcTemplate;
    public RecipeLinkCustomRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    //TODO 최대 저장 리스트 크기
    @Override
    public void saveAll(List<RecipeLink> list) {

        String sql = "insert ignore into recipe_link (link, name, source)"
                + "values (?,?,?)";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                RecipeLink recipeLink = list.get(i);
                ps.setString(1, recipeLink.getLink());
                ps.setString(2, recipeLink.getName());
                ps.setString(3, recipeLink.getSource().name());
            }
            @Override
            public int getBatchSize() {
                return list.size();
            }
        });
    }
}
