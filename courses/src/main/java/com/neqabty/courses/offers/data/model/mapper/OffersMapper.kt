package com.neqabty.courses.offers.data.model.mapper

import com.neqabty.courses.home.data.model.mappers.toCourseEntity
import com.neqabty.courses.offers.data.model.*
import com.neqabty.courses.offers.domain.entity.*

fun OfferModel.toOfferEntity(): OfferEntity {
    return OfferEntity(
        address,
        appointments.map { it.toAppointmentEntity() },
        contact,
        course.toCourseEntity(),
        endDate,
        fullyBooked,
        id,
        isAvailable,
        maxNumOfTrainees,
        numOfTrainees,
        pricing.map { it.toPricingEntity() },
        reservations.map { it.toReservationEntity() },
        startDate,
        title
    )
}


fun Appointment.toAppointmentEntity(): AppointmentEntity {
    return AppointmentEntity(day, id, offer, timeFrom, timeTo)
}

fun Pricing.toPricingEntity(): PricingEntitty {
    return PricingEntitty(
        id, offer, price,
        StudentCategoryEntity(studentCategory.code, studentCategory.name)
    )
}

fun Reservation.toReservationEntity(): ReservationEntity {
    return ReservationEntity(
        cost,
        id,
        offer,
        paymentStatus,
        queueNumber,
        status,
        student.toStudentEntity()
    )

}

fun Student.toStudentEntity(): StudentEntity {
    return StudentEntity(
        fullName,
        id,
        membershipId,
        StudentCategoryEntity(stdCategory.code, stdCategory.name)
    )
}