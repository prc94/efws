package com.github.prc94.efws.security

import com.github.prc94.efws.data.entity.User
import com.github.prc94.efws.data.repository.UserRepository
import com.github.prc94.efws.util.CrudOperations
import org.springframework.security.access.PermissionEvaluator
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.io.Serializable
import kotlin.jvm.optionals.getOrNull

@Component
class PermissionEvaluator(private val userRepository: UserRepository) : PermissionEvaluator {
    override fun hasPermission(
        authentication: Authentication,
        targetDomainObject: Any?,
        permission: Any
    ): Boolean =
        TODO("Implement permissions on objects?")

    override fun hasPermission(
        authentication: Authentication,
        targetId: Serializable,
        targetType: String,
        permission: Any
    ): Boolean =
        userRepository.findById(targetId as Int)
            .getOrNull()
            ?.let { user ->
                if (user.isAdmin)
                    return@let true

                when(targetType) {
                    "user" -> evaluateOnUser(authentication, user, permission)
                    "user_keys", "user_files" -> evaluateOnUserBelongings(authentication, user, permission)
                    else -> false
                }
            } ?: false
    
    private fun evaluateOnUserBelongings(authentication: Authentication, user: User, permission: Any): Boolean {
        if (CrudOperations.of(permission) != null)
            return authentication.name == user.username

        return false
    }

    private fun evaluateOnUser(authentication: Authentication, user: User, permission: Any): Boolean {
        if (permission == "change_password" || CrudOperations.of(permission) == CrudOperations.READ)
            return authentication.name == user.username

        return false
    }
}