package com.ansar.autoPartsApp.features.tab

import com.ansar.autoPartsApp.base.utils.BaseScreenModel
import com.ansar.autoPartsApp.domain.manager.SessionManager
import org.koin.core.component.inject

class TabScreenModel : BaseScreenModel<Any, Any>(Any()) {

    val sessionManager: SessionManager by inject()
}