package com.startnow.blog.repository;

import com.startnow.blog.entity.UserEntity;
import com.startnow.blog.exception.RepositoryException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Expression;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.Optional;

@Repository
@Slf4j
public class UserRepository implements IUserRepository {
    private final DynamoDbTable<UserEntity> userTable;

    public UserRepository(DynamoDbTable<UserEntity> userTable) {
        this.userTable = userTable;
    }

    @Override
    public UserEntity save(UserEntity article) {
        try {
            userTable.putItem(article);
            return article;
        } catch (Exception e) {
            log.error("Error saving article: {}", e.getMessage());
            throw new RepositoryException("Failed to save article", e);
        }
    }

    @Override
    public void delete(String id) {
        try {
            userTable.deleteItem(Key.builder().partitionValue(id).build());
        } catch (Exception e) {
            log.error("Error deleting user: {}", e.getMessage());
            throw new RepositoryException("Failed to delete user", e);
        }
    }

    @Override
    public Optional<UserEntity> findById(String id) {
        try {
            return Optional.ofNullable(userTable.getItem(Key.builder().partitionValue(id).build()));
        } catch (Exception e) {
            log.error("Error finding user by ID: {}", e.getMessage());
            throw new RepositoryException("Failed to find user by ID", e);
        }
    }

    @Override
    public UserEntity update(UserEntity user) {
        try {
            userTable.updateItem(user);
            return user;
        } catch (Exception e) {
            log.error("Error updating user: {}", e.getMessage());
            throw new RepositoryException("Failed to update user", e);
        }
    }

    @Override
    public Optional<UserEntity> findByEmail(String email) {
        try {
            return userTable
                    .scan(r -> r
                            .filterExpression(Expression.builder().expression("email = :email")
                                    .putExpressionValue(":email",
                                            AttributeValue.builder().s(email).build())
                                    .build()))
                    .items().stream().findFirst();
        } catch (Exception e) {
            log.error("Error finding user by email: {}", e.getMessage());
            throw new RepositoryException("Failed to find user by email", e);
        }
    }
}
