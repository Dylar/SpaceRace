package de.bitb.spacerace.scenario

import de.bitb.spacerace.GameTest
import org.junit.Before
import org.junit.Test

class LoadingGame : GameTest() {

//    private val boxStore = Setup.createMockBoxStore()
//    private val tourDataSource = TourRepository(boxStore)
//
//    @Test
//    fun whenStoringANewManualDelivery_ANewDeliveryIsInDB() {
//
//        // given
//        val saveCustomerUseCase = SaveCustomerUseCase(tourDataSource, view)
//        val test = saveCustomerUseCase
//                .buildUseCaseObservable(customCustomer1234)
//                .test()
//
//        test.await()
//
//        // when
//        test.assertValueCount(1)
//                .assertValue {
//                    // then
//                    when (it) {
//                        is CustomCustomerResult.CustomerId -> true
//                        is CustomCustomerResult.CustomerError -> false
//                    }
//                }
//    }
//
//    @Test
//    fun whenOverridingADeliversWhereItsNotAllowed_ThrowError() {
//        // given
//        val customCustomer = customCustomer1234.copy()
//        SaveCustomerUseCase(tourDataSource, view)
//                .buildUseCaseObservable(customCustomer)
//                .test()
//                .await()
//
//        // when
//        val test = SaveCustomerUseCase(tourDataSource, view)
//                .buildUseCaseObservable(customCustomer)
//                .test()
//        test.awaitCount(1)
//        // when
//        test.assertValueCount(1)
//                .assertValue {
//                    // then
//                    when (it) {
//                        is CustomCustomerResult.CustomerId -> false
//                        is CustomCustomerResult.CustomerError -> true
//                    }
//                }
//                .assertValue {
//                    if (it is CustomCustomerResult.CustomerError) {
//                        val isError = it.errorFromSaveDelivery
//                        val isName = it.customerName == customCustomer.customerName
//                        val isNumber = it.customerNo == customCustomer.currentCustomerNo
//                        return@assertValue isError && isName && isNumber
//                    }
//                    false
//                }
//
//    }
//
//    @Test
//    fun whenOverridingADeliveryWhereItsAllowed_AssertValueStoredInDB() {
//        // given
//        val newCustomCustomer = customCustomer1234.copy(canUpdate = true)
//        SaveCustomerUseCase(tourDataSource, view)
//                .buildUseCaseObservable(newCustomCustomer)
//                .test()
//                .await()
//
//        // when
//        val updatedCustomCustomer =
//                newCustomCustomer.copy(
//                        newUpdatedNumber = 4321,
//                        currentCustomerNo = newCustomCustomer.newUpdatedNumber
//                )
//        val test = SaveCustomerUseCase(tourDataSource, view)
//                .buildUseCaseObservable(updatedCustomCustomer)
//                .test()
//        test.awaitCount(1)
//        // when
//        test.assertValueCount(1)
//                .assertValue {
//                    // then
//                    when (it) {
//                        is CustomCustomerResult.CustomerId -> true
//                        is CustomCustomerResult.CustomerError -> false
//                    }
//                }
//                .assertValue {
//                    if (it is CustomCustomerResult.CustomerId) {
//                        return@assertValue it.dataBaseId > 0
//                    }
//                    false
//                }
//
//    }
//
//    @Test
//    fun whenOverridingADeliveryWhereItsAllowedButNewDeliveryOverridesAnother_ShowError() {
//        // given
//        val customerNoOfNotOverrideable = 4321
//
//        val existingButOtherThanEditing =
//                customCustomer1234.copy(customerName = "Do not override me", newUpdatedNumber = customerNoOfNotOverrideable)
//        SaveCustomerUseCase(tourDataSource, view)
//                .buildUseCaseObservable(existingButOtherThanEditing)
//                .test()
//                .await()
//
//        val newCustomerToEditLater = customCustomer1234.copy(canUpdate = true)
//        SaveCustomerUseCase(tourDataSource, view)
//                .buildUseCaseObservable(newCustomerToEditLater)
//                .test()
//                .await()
//
//        val test = SaveCustomerUseCase(tourDataSource, view)
//                .buildUseCaseObservable(
//                        newCustomerToEditLater.copy(
//                                currentCustomerNo = newCustomerToEditLater.newUpdatedNumber,
//                                newUpdatedNumber = customerNoOfNotOverrideable,
//                                canUpdate = true
//                        )
//                )
//                .test()
//
//        // when
//        test.awaitCount(1)
//        // when
//        test.assertValueCount(1)
//                .assertValue {
//                    // then
//                    when (it) {
//                        is CustomCustomerResult.CustomerId -> false
//                        is CustomCustomerResult.CustomerError -> true
//                    }
//                }
//    }
//
//    @Test
//    fun whenJustChangingName_DeliveryMustBeUpdated() {
//        // given
//        val customCustomer = customCustomer1234.copy()
//        SaveCustomerUseCase(tourDataSource, view)
//                .buildUseCaseObservable(customCustomer)
//                .test()
//                .await()
//
//        // when
//        val customerName = "Other Name"
//        val updatedCustomer =
//                customCustomer.copy(
//                        customerName = customerName,
//                        currentCustomerNo = customCustomer1234.newUpdatedNumber,
//                        canUpdate = true
//                )
//        val test = SaveCustomerUseCase(tourDataSource, view)
//                .buildUseCaseObservable(updatedCustomer)
//                .test()
//        test.awaitCount(1)
//        // when
//        test.assertValueCount(1)
//                .assertValue {
//                    // then
//                    when (it) {
//                        is CustomCustomerResult.CustomerId -> true
//                        is CustomCustomerResult.CustomerError -> false
//                    }
//                }
//                .assertValue {
//                    if (it is CustomCustomerResult.CustomerId) {
//                        return@assertValue it.dataBaseId > 0
//                    }
//                    false
//                }
//
//    }
//
//    @Test
//    fun whenReallocationCustomerNumber_TheNumberMustBeAvailableForNewDeliveries() {
//
//        // given
//        val customCustomer = customCustomer1234.copy()
//        SaveCustomerUseCase(tourDataSource, view)
//                .buildUseCaseObservable(customCustomer)
//                .test()
//                .await()
//
//        // when
//        val updatedNumber = 321
//        val updatedCustomer =
//                customCustomer.copy(
//                        newUpdatedNumber = updatedNumber,
//                        currentCustomerNo = customCustomer1234.newUpdatedNumber,
//                        canUpdate = true
//                )
//        SaveCustomerUseCase(tourDataSource, view)
//                .buildUseCaseObservable(updatedCustomer)
//                .test()
//                .await()
//
//        val test = SaveCustomerUseCase(tourDataSource, view)
//                .buildUseCaseObservable(customCustomer)
//                .test()
//        test.await()
//
//        // then must be 2 deliveries in data base
//        val deliveryBox = boxStore.boxFor(Delivery::class.java)
//        val allDeliveries = deliveryBox.all
//        assert(allDeliveries.size == 2) {
//            "there must be 2 deliveries in box"
//        }
//    }

}