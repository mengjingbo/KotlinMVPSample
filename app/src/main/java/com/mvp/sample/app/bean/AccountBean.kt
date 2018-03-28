package com.mvp.sample.app.bean

/**
 * 作者：蒙景博
 * 时间：2018/3/27
 * 描述：
 */
class AccountBean {

    var login: String? = null
    var id: Int = 0
    var avatar_url: String? = null
    var gravatar_id: String? = null
    var url: String? = null
    var html_url: String? = null
    var followers_url: String? = null
    var following_url: String? = null
    var gists_url: String? = null
    var starred_url: String? = null
    var subscriptions_url: String? = null
    var organizations_url: String? = null
    var repos_url: String? = null
    var events_url: String? = null
    var received_events_url: String? = null
    var type: String? = null
    var isSite_admin: Boolean = false
    var name: String? = null
    var company: String? = null
    var blog: String? = null
    var location: String? = null
    var email: String? = null
    var isHireable: Boolean = false
    var bio: String? = null
    var public_repos: Int = 0
    var public_gists: Int = 0
    var followers: Int = 0
    var following: Int = 0
    var created_at: String? = null
    var updated_at: String? = null

    fun accountToString(): String {
        return "帐户名:$login\nGitHub地址:$html_url\n昵称:$name\n$bio:$blog"
    }
}
