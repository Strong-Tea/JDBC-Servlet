package com.strong.tea.database;

import com.strong.tea.annotation.Column;
import com.strong.tea.annotation.Id;
import com.strong.tea.annotation.Table;
import com.strong.tea.database.DatabaseUtils;
import com.strong.tea.entity.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class DatabaseUtilsTest {

    @Test
    public void testGenerateSelectAllQuery_WithAnnotationEntity() {
        try {
            String query = DatabaseUtils.generateSelectAllQuery(TestEntityWithAnnotation.class);

            Assertions.assertEquals("SELECT * FROM TestEntity ORDER BY id ASC;", query);
        } catch (SQLException e) {
            Assertions.fail("Exception thrown: " + e.getMessage());
        }
    }

    @Test
    public void testGenerateSelectAllQuery_WithoutAnnotationEntity() {
        try {
            String query = DatabaseUtils.generateSelectAllQuery(TestEntityWithoutAnnotation.class);

            Assertions.assertEquals("SELECT * FROM TestEntityWithoutAnnotation ORDER BY id ASC;", query);
        } catch (SQLException e) {
            Assertions.fail("Exception thrown: " + e.getMessage());
        }
    }

    @Test
    public void testGenerateSelectById_WithEmptyEntity_Throws_SQLException() {
        try {
            Assertions.assertThrows(SQLException.class, () -> {
                DatabaseUtils.generateSelectById(TestEmptyEntity.class, 5);
            });
        } catch (Exception e) {
            Assertions.fail("Exception thrown: " + e.getMessage());
        }
    }

    @Test
    public void testGenerateSelectById_WithAnnotationEntity() {
        try {
            String query = DatabaseUtils.generateSelectById(TestEntityWithAnnotation.class, 5);

            Assertions.assertEquals("SELECT * FROM TestEntity WHERE id = 5", query);
        } catch (Exception e) {
            Assertions.fail("Exception thrown: " + e.getMessage());
        }
    }

    @Test
    public void testGenerateDeleteById_WithEmptyEntity_Throws_SQLException() {
        try {
            Assertions.assertThrows(SQLException.class, () -> {
                DatabaseUtils.generateDeleteById(TestEmptyEntity.class, 5);
            });
        } catch (Exception e) {
            Assertions.fail("Exception thrown: " + e.getMessage());
        }
    }

    @Test
    public void testGenerateDeleteById_WithAnnotationEntity() {
        try {
            String query = DatabaseUtils.generateDeleteById(TestEntityWithAnnotation.class, 5);

            Assertions.assertEquals("DELETE FROM TestEntity WHERE id = 5", query);
        } catch (Exception e) {
            Assertions.fail("Exception thrown: " + e.getMessage());
        }
    }


    @Test
    public void testGenerateUpdateQuery_WithAnnotationEntity() {
        try {
            TestEntityWithAnnotation entity = new TestEntityWithAnnotation();
            entity.setId(1);
            entity.setName("John Doe");
            entity.setAge(44);

            String query = DatabaseUtils.generateUpdateQuery(entity);

            Assertions.assertEquals("UPDATE TestEntity SET name = 'John Doe', age = 44 WHERE id = 1;", query);
        } catch (SQLException e) {
            Assertions.fail("Exception thrown: " + e.getMessage());
        }
    }

    @Test
    public void testGenerateUpdateQuery_WithoutAnnotationEntity_Throws_SQLException() {
        try {
            TestEntityWithoutAnnotation entity = new TestEntityWithoutAnnotation();
            entity.setId(1);
            entity.setName("John Doe");
            entity.setAge(44);

            Assertions.assertThrows(SQLException.class, () -> {
                DatabaseUtils.generateUpdateQuery(entity);
            });
        } catch (Exception e) {
            Assertions.fail("Exception thrown: " + e.getMessage());
        }
    }

    @Test
    public void testGenerateInsertQuery_WithAnnotationEntity() {
        try {
            TestEntityWithAnnotation entity = new TestEntityWithAnnotation();
            entity.setId(1);
            entity.setName("John Doe");
            entity.setAge(30);

            String query = DatabaseUtils.generateInsertQuery(entity, false);

            Assertions.assertEquals("INSERT INTO TestEntity (name, age)  VALUES ('John Doe', 30);", query);
        } catch (SQLException e) {
            Assertions.fail("Exception thrown: " + e.getMessage());
        }
    }

    @Test
    public void testGenerateInsertQuery_WithoutAnnotationEntity() {
        try {
            TestEntityWithoutAnnotation entity = new TestEntityWithoutAnnotation();
            entity.setId(1);
            entity.setName("John Doe");
            entity.setAge(30);

            String query = DatabaseUtils.generateInsertQuery(entity, true);

            Assertions.assertEquals("INSERT INTO TestEntityWithoutAnnotation ()  VALUES ();", query);
        } catch (SQLException e) {
            Assertions.fail("Exception thrown: " + e.getMessage());
        }
    }

    @Test
    public void testGenerateInsertQuery_WithoutEmptyEntity() {
        try {
            TestEmptyEntity entity = new TestEmptyEntity();

            String query = DatabaseUtils.generateInsertQuery(entity, false);

            Assertions.assertEquals("INSERT INTO TestEmptyEntity ()  VALUES ();", query);
        } catch (SQLException e) {
            Assertions.fail("Exception thrown: " + e.getMessage());
        }
    }

    @Test
    public void testMapResultSetToEntity_WithAnnotationEntity() {
        try {
            // Mock ResultSet
            ResultSet resultSetMock = Mockito.mock(ResultSet.class);
            ResultSetMetaData metaDataMock = Mockito.mock(ResultSetMetaData.class);
            Mockito.when(resultSetMock.getMetaData()).thenReturn(metaDataMock);
            Mockito.when(metaDataMock.getColumnCount()).thenReturn(3);
            Mockito.when(metaDataMock.getColumnName(1)).thenReturn("id");
            Mockito.when(metaDataMock.getColumnName(2)).thenReturn("name");
            Mockito.when(metaDataMock.getColumnName(3)).thenReturn("age");
            Mockito.when(resultSetMock.getObject(1)).thenReturn(1);
            Mockito.when(resultSetMock.getObject(2)).thenReturn("John Doe");
            Mockito.when(resultSetMock.getObject(3)).thenReturn(30);

            TestEntityWithAnnotation entity = DatabaseUtils.mapResultSetToEntity(resultSetMock, TestEntityWithAnnotation.class);

            Assertions.assertNotNull(entity);
            Assertions.assertEquals(1, entity.getId());
            Assertions.assertEquals("John Doe", entity.getName());
            Assertions.assertEquals(30, entity.getAge());
        } catch (SQLException e) {
            Assertions.fail("Exception thrown: " + e.getMessage());
        }
    }


    @Test
    public void testMapResultSetToEntity_WithoutAnnotationEntity() {
        try {
            // Mock ResultSet
            ResultSet resultSetMock = Mockito.mock(ResultSet.class);
            ResultSetMetaData metaDataMock = Mockito.mock(ResultSetMetaData.class);
            Mockito.when(resultSetMock.getMetaData()).thenReturn(metaDataMock);
            Mockito.when(metaDataMock.getColumnCount()).thenReturn(3);
            Mockito.when(metaDataMock.getColumnName(1)).thenReturn("id");
            Mockito.when(metaDataMock.getColumnName(2)).thenReturn("name");
            Mockito.when(metaDataMock.getColumnName(3)).thenReturn("age");
            Mockito.when(resultSetMock.getObject(1)).thenReturn(1);
            Mockito.when(resultSetMock.getObject(2)).thenReturn("John Doe");
            Mockito.when(resultSetMock.getObject(3)).thenReturn(30);

            TestEntityWithoutAnnotation entity = DatabaseUtils.mapResultSetToEntity(resultSetMock, TestEntityWithoutAnnotation.class);

            Assertions.assertNotNull(entity);
            Assertions.assertEquals(0, entity.getId());
            Assertions.assertEquals(null, entity.getName());
            Assertions.assertEquals(0, entity.getAge());
        } catch (SQLException e) {
            Assertions.fail("Exception thrown: " + e.getMessage());
        }
    }

    @Test
    public void testMapResultSetToEntity_WithEmptyResultSet() {
        try {
            // Mock an empty ResultSet
            ResultSet resultSetMock = Mockito.mock(ResultSet.class);
            ResultSetMetaData metaDataMock = Mockito.mock(ResultSetMetaData.class);
            Mockito.when(resultSetMock.getMetaData()).thenReturn(metaDataMock);
            Mockito.when(metaDataMock.getColumnCount()).thenReturn(0); // Empty ResultSet

            TestEmptyEntity entity = DatabaseUtils.mapResultSetToEntity(resultSetMock, TestEmptyEntity.class);
            Assertions.assertNotNull(entity);
        } catch (SQLException e) {
            Assertions.fail("Exception thrown: " + e.getMessage());
        }
    }
}
