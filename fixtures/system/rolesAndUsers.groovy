import de.dewarim.goblin.Role
import de.dewarim.goblin.UserAccount
import de.dewarim.goblin.UserRole

fixture{
    adminRole(Role, name:'ROLE_ADMIN', description:'Administrator')
    admin(UserAccount, username:'admin', userRealName:'Admin', enabled:true, passwd:'admin')
    adminRoleForAdmin(UserRole, user:admin, role:adminRole)
    userRole(Role, name:'ROLE_USER', description:'User')
    userRoleForAdmin(UserRole, user:admin, role:userRole)

    anon(UserAccount, username:'anon', userRealName: 'Anonymous', 
            enabled:true, passwd:'anon', coins:100, email:'anon@example.com')
    userRoleForAnon(UserRole, user:anon, role:userRole)
}