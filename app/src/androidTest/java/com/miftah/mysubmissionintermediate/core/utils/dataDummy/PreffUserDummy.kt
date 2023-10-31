package com.miftah.mysubmissionintermediate.utils.dataDummy

import com.miftah.mysubmissionintermediate.core.data.source.pref.model.UserModel

object PreffUserDummy {
    fun generateUser(isLogin : Boolean) : UserModel {
        return UserModel(
            username = "Miftah",
            userId = "user-yj5pc_LARC_AgK61",
            token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJ1c2VyLXlqNXBjX0xBUkNfQWdLNjEiLCJpYXQiOjE2NDE3OTk5NDl9.flEMaQ7zsdYkxuyGbiXjEDXO8kuDTcI__3UjCwt6R_I",
            isLogin = isLogin
        )
    }
}