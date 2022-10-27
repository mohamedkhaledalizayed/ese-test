package com.neqabty.courses.offers.data.model.mapper

import com.neqabty.courses.offers.data.model.*
import com.neqabty.courses.offers.domain.entity.*
import com.neqabty.courses.offers.domain.entity.Entity

fun OfferModel.toOfferEntity(): OfferEntity {
    return OfferEntity(
        address = address,
        appointmentEntities = appointments.map { it.toAppointementEntity() },
        authorEntity = author.toAuthorEntity(),
        contact = contact,
        courseInOfferEntity = courseInOffer.toCourseOfferEntity(),
        endDate = endDate,
        fullyBooked = fullyBooked,
        groupNumber = groupNumber,
        id = id,
        isAvailable = isAvailable,
        maxNumOfTrainees = maxNumOfTrainees,
        numOfTrainees = numOfTrainees,
        pricingEntities = pricings.map { it.toPricingEntity() },
        reservationEntities = reservations.map { it.toReservatioEntity() },
        startDate = startDate,
        title = title
    )
}

private fun Reservation.toReservatioEntity(): ReservationEntity {
    return ReservationEntity(
        cashUrl = cashUrl,
        createdAt = createdAt,
        id = id,
        offerInReservationEntity = offersInReservation.toOfferInReservationEntity(),
        paymentStatus = paymentStatus,
        queueNumber = queueNumber,
        status = status,
        studentEntity = student.toStudentEntity(),
        transactionId = transactionId,
        updatedAt = updatedAt
    )
}

private fun Student.toStudentEntity(): StudentEntity {
    return StudentEntity(
        createdAt = createdAt,
        department = department,
        email = email,
        graduateYear = graduateYear,
        id = id,
        identityCard = identityCard?:"",
        mobile = mobile,
        name = name,
        nequabtyId = nequabtyId?:"",
        stdCategory = stdCategory,
        studentStatus = studentStatus,
        university = university,
        updatedAt = updatedAt
    )
}

private fun OffersInReservation.toOfferInReservationEntity(): OfferInReservationEntity {
    return OfferInReservationEntity(
        address = address,
        author = author,
        contact = contact,
        course = course,
        createdAt = createdAt,
        endDate = endDate,
        groupNumber = groupNumber,
        id = id,
        isAvailable = isAvailable,
        isConfirmable = isConfirmable,
        maxNumOfTrainees = maxNumOfTrainees,
        startDate = startDate,
        title = title,
        updatedAt = updatedAt
    )
}

private fun Pricing.toPricingEntity(): PricingEntity {
    return PricingEntity(
        id = id,
        offer = offer,
        price = price,
        serviceActionCode = serviceActionCode,
        serviceCode = serviceCode,
        studentCategoryEntity = studentCategory.toEntity()
    )
}

private fun StudentCategory.toEntity(): StudentCategoryEntity {
    return StudentCategoryEntity(
        code = code,
        createdAt = createdAt,
        name = name,
        updatedAt = updatedAt
    )
}

private fun CourseInOffer.toCourseOfferEntity(): CourseInOfferEntity {
    return CourseInOfferEntity(
        id = id,
        image = image,
        labEntity = lab.toLabEntity(),
        numOfSessions = numOfSessions,
        prerequisites = prerequisites,
        syllabus = syllabus,
        title = title
    )
}

private fun Lab.toLabEntity(): LabEntity {
    return LabEntity(
        entity = Entity(entity.code,entity.name),
        id = id,
        image = image,
        name = name
    )
}

private fun Author.toAuthorEntity(): AuthorEntity {
    return AuthorEntity(
        email = email?:"",
        entityCode = entityCode,
        firstName = firstName,
        id = id,
        image = image?:"",
        lastName = lastName,
        mobile = mobile
    )
}

private fun Appointment.toAppointementEntity(): AppointmentEntity {
    return AppointmentEntity(
        day = day,
        id = id,
        offer = offer,
        timeFrom = timeFrom,
        dayName = dayName,
        timeTo = timeTo
    )
}
