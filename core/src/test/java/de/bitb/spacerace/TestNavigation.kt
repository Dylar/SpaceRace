package de.bitb.spacerace

import de.bitb.spacerace.TestNavigation.NavigationTarget.LOGIN

object TestNavigation {

    sealed class NavigationTarget(
            loginName: String = "",
            loginPW: String = ""
    ) {
        class LOGIN : NavigationTarget()
        class IDENTIFICATION : NavigationTarget()
    }

    private fun finish() {
//        BaristaSleepInteractions.sleep(200)
    }

    /**
     * Init test environment and navigate dynamically to subviews
     * used by many tests
     *
     * @param testEnvironment use your own TestEnvironment Setup
     * @param navigationTarget the target view you want to navigate to
     *
     */
    fun navigateToView(
            testEnvironment: SystelEnvironment = SystelEnvironment(),
            navigationTarget: NavigationTarget = LOGIN()
    ) {
//        testEnvironment.launch()

        if (navigationTarget is LOGIN) {
            finish()
            return
        }
//
//        login()
//
//        if (navigationTarget == NavigationTarget.CONNECT_PRINTER) {
//            finish()
//            return
//        }
//
//        connectPrinter()
//
//        if (navigationTarget == NavigationTarget.LOAD_TOURS) {
//            finish()
//            return
//        }
//
//        when (navigationTarget) {
//            NavigationTarget.TOURS_NOT_FOUND -> {
//                testEnvironment.setup.mockExpAnfrageSet(MOCK_EXP_ANFRAGE_SET_NOT_FOUND) //TODO what about you want another result ...
//                enterNumberAndNavigateToNotFound()
//            }
//            else -> {
//                testEnvironment.setup.mockExpAnfrageSet(MOCK_EXP_ANFRAGE_SET) //TODO what about you want another result ...
//                enterNumberAndNavigateToOverview()
//            }
//        }
//
//        if (navigationTarget == NavigationTarget.TOURS_OVERVIEW || navigationTarget == NavigationTarget.TOURS_NOT_FOUND) {
//            finish()
//            return
//        }
//
//        when (navigationTarget) {
//            TestNavigationUtil.NavigationTarget.TOUR_DETAIL -> {
//                navigateToDetail(deliveryIndex)
//            }
//            else -> {
//                navigateToTourStatus()
//            }
//        }
//
//        if (navigationTarget == NavigationTarget.TOUR_DETAIL || navigationTarget == NavigationTarget.TOUR_STATUS) {
//            finish()
//            return
//        }
    }
//
//    private fun connectPrinter() {
//        val validMacAddress = "123123123123"
//        BaristaEditTextInteractions.writeTo(R.id.printerAddress, validMacAddress)
//
//        BaristaClickInteractions.clickOn(R.id.printerConnect)
//
//        // wait for the snackbar to disappear
//        Thread.sleep(2000)
//    }
//
//    private fun login() {
//        val numberPlate = ThmTestEnvironment.validNumberPlate
//        val driver = ThmTestEnvironment.validDriverName
//
//        BaristaEditTextInteractions.writeTo(R.id.loginNumberPlate1, numberPlate.locationId)
//        BaristaEditTextInteractions.writeTo(R.id.loginNumberPlate2, numberPlate.charId)
//        BaristaEditTextInteractions.writeTo(R.id.loginNumberPlate3, numberPlate.numberId.toString())
//        BaristaEditTextInteractions.writeTo(R.id.signature_pad_name_et, driver)
//
//        // draw with touch on signature pad
//        SignaturePadUtils.drawOnPad(R.id.signature_pad)
//
//        // all inputs are valid and login button should be enabled
//        BaristaClickInteractions.clickOn(R.id.loginSave)
//        Thread.sleep(200)
//    }
//
//    private fun enterNumberAndNavigateToOverview() {
//        enterNumberAndLoad(validTourNumberMultipleReturns)
//    }
//
//    private fun enterNumberAndNavigateToNotFound() {
//        enterNumberAndLoad(validTourNumberNoReturns)
//    }
//
//    private fun enterNumberAndLoad(number: String) {
//        //enter tour number
//        BaristaEditTextInteractions.writeTo(R.id.eddiInputText, number)
//        BaristaKeyboardInteractions.closeKeyboard()
//        //add tour number
//        BaristaClickInteractions.clickOn(R.id.eddiInputActionButton)
//        //load tours
//        BaristaClickInteractions.clickOn(R.id.loadButton)
//    }
//
//    fun navigateToDetail(deliveryIndex: Int) {
//        BaristaSleepInteractions.sleep(200)
//        BaristaListInteractions.clickListItem(R.id.toursOverviewList, deliveryIndex)
//    }
//
//    fun navigateToTourStatus() {
//        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().targetContext)
//        BaristaClickInteractions.clickOn(mockContext.getString(R.string.tours_overview_tour_status))
//    }
}