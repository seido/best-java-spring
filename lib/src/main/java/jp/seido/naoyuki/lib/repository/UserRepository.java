package jp.seido.naoyuki.lib.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jp.seido.naoyuki.lib.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
}
