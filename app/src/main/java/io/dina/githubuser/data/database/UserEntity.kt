package io.dina.githubuser.data.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class UserEntity(

    @PrimaryKey
    @ColumnInfo(name = "login")
    val login: String,

    @ColumnInfo(name = "avatar_url")
    val avatarUrl: String,

    @ColumnInfo(name = "bio")
    val bio: String,

    @ColumnInfo(name = "blog")
    val blog: String,

    @ColumnInfo(name = "company")
    val company: String,

    @ColumnInfo(name = "email")
    val email: String,

    @ColumnInfo(name = "followers")
    val followers: Int,

    @ColumnInfo(name = "following")
    val following: Int,

    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "location")
    val location: String,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "public_gists")
    val publicGists: Int,

    @ColumnInfo(name = "public_repos")
    val publicRepos: Int
) : Parcelable