package com.cernol.works

import com.cernol.works.service.StockItemServiceBean
import com.haulmont.cuba.core.global.AppBeans

/**
 * Created by skopp on 03/07/2017.
 */
class StockValuation {
    StockItemServiceBean mySIBean = AppBeans.get();
    def onHand = mySIBean.getCurrentQuantity()
}
