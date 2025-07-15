package com.ansar.autoPartsApp.features.profile

import com.ansar.autoPartsApp.base.utils.BaseScreenModel
import com.ansar.autoPartsApp.domain.manager.SessionManager
import org.koin.core.component.inject

class ProfileViewModel : BaseScreenModel<Any, Any>(Any()) {
    val sessionManager: SessionManager by inject()

}