package kr.ac.kau.learningmate.service

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import kr.ac.kau.learningmate.controller.dto.UserDto
import kr.ac.kau.learningmate.domain.User
import kr.ac.kau.learningmate.repository.UserRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    @Value("\${jwt.secret}") private val jwtSecret: String,
    @Value("\${jwt.issuer}") private val jwtIssuer: String
) {

    fun findById(id: Long): User {
        return userRepository.findByIdOrNull(id) ?: throw IllegalArgumentException("없는 사용자 입니다.")
    }

    fun createUserByName(): UserDto.Me {
        val lastUserId = userRepository.findFirstByOrderByIdDesc()?.id ?: 0
        val name = "사용자${lastUserId + 1}"

        val jwt = Jwts.builder()
            .setIssuer(jwtIssuer)
            .setSubject(name)
            .signWith(SignatureAlgorithm.HS256, jwtSecret)
            .compact()

        val user = User(
            name = name,
            authToken = jwt
        )

        val savedUser = userRepository.save(user)

        return UserDto.Me(
            name = savedUser.name,
            authToken = savedUser.authToken,
        )
    }
}
