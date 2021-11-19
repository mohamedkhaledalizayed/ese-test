package com.neqabty.courses.offers.data.model.mapper

import com.neqabty.courses.home.data.model.mappers.toCourseEntity
import com.neqabty.courses.offers.data.model.*
import com.neqabty.courses.offers.domain.entity.*

fun OfferModel.toOfferEntity(): OfferEntity {
    return OfferEntity(
        address ?: "",
        appointments.map { it.toAppointmentEntity() },
        contact ?: "",
        course.toCourseEntity(),
        endDate ?: "",
        fullyBooked ?: false,
        id,
        isAvailable ?: true,
        maxNumOfTrainees ?: 0,
        numOfTrainees ?: 0,
        pricing.map { it.toPricingEntity() },
        reservations.map { it.toReservationEntity() },
        startDate ?: "",
        title ?: ""
    )
}


fun Appointment.toAppointmentEntity(): AppointmentEntity {
    return AppointmentEntity(day ?: 0, id, offer ?: 0, timeFrom ?: "", timeTo ?: "")
}

fun Pricing.toPricingEntity(): PricingEntitty {
    return PricingEntitty(
        id, offer ?: 0, price ?: "",
        StudentCategoryEntity(studentCategory.code ?: "", studentCategory.name ?: "")
    )
}

fun Reservation.toReservationEntity(): ReservationEntity {
    return ReservationEntity(
        cost ?: "",
        id,
        offer ?: 0,
        paymentStatus,
        queueNumber ?: 0,
        status ?: "",
        student?.toStudentEntity() ?: StudentEntity("", 0, 0, StudentCategoryEntity("", ""))
    )

}

fun Student.toStudentEntity(): StudentEntity {
    return StudentEntity(
        fullName ?: "",
        id,
        membershipId ?: 0,
        StudentCategoryEntity(stdCategory.code ?: "", stdCategory.name ?: "")
    )
}