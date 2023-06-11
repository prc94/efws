package com.github.prc94.efws.data.entity

import com.github.prc94.efws.util.IdHolder
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import jakarta.persistence.Transient
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.userdetails.UserDetails

@Entity
@Table(name = "USER_ACCOUNT")
class User(
    @Column(unique = true) var username: String,
    var password: String,
    var isAdmin: Boolean,
    var isEnabled: Boolean,
    @OneToMany(cascade = [CascadeType.ALL])
    val keys: MutableList<Key> = mutableListOf(),
    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "owner")
    val files: MutableList<FileEntity> = mutableListOf(),
    @Id @GeneratedValue val id: Int? = null
) {

    @Transient
    val userDetails: UserDetails = object : UserDetails, IdHolder {
        override val id: Int
            get() = this@User.id ?: throw IllegalStateException("User [$username]'s id is null!")

        override fun getUsername(): String = this@User.username

        override fun getPassword(): String = this@User.password

        override fun isEnabled(): Boolean = isEnabled

        override fun getAuthorities(): MutableCollection<out GrantedAuthority> =
            if (isAdmin)
                AuthorityUtils.createAuthorityList("ROLE_USER", "ROLE_ADMIN")
            else
                AuthorityUtils.createAuthorityList("ROLE_USER")

        override fun isAccountNonExpired(): Boolean = true

        override fun isAccountNonLocked(): Boolean = true

        override fun isCredentialsNonExpired(): Boolean = true
    }
}